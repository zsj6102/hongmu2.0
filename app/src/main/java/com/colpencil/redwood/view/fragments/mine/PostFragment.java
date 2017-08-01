package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.PostItem;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.widgets.swipe.SwipeListView;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenu;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuCreator;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuItem;
import com.colpencil.redwood.present.mine.PostFragmentPresenter;
import com.colpencil.redwood.view.activity.home.CommentDetailActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.PostFragmentAdapter;
import com.colpencil.redwood.view.impl.IPostFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DpAndPx;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 曾 凤
 * @Description: 帖子收藏
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
@ActivityFragmentInject(
        contentViewId = R.layout.base_delete_listview
)
public class PostFragment extends ColpencilFragment implements IPostFragmentView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.base_swipeListView)
    SwipeListView base_swipeListView;

    @Bind(R.id.bga_basedelete)
    BGARefreshLayout bga_basedelete;

    /**
     * 分页请求页码
     */
    private int pageNo = 1;
    /**
     * 每页信息条数
     */
    private int pageSize = 10;
    /**
     * 是否可进行上拉加载操作
     */
    private boolean flag;

    private PostFragmentAdapter mAdapter;

    private PostFragmentPresenter presenter;

    private List<PostItem> mdatas = new ArrayList<>();

    private Observable<RxBusMsg> observable;

    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        bga_basedelete.setDelegate(this);
        bga_basedelete.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_basedelete.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        initData();
    }

    private void initData() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // TODO Auto-generated method stub
                SwipeMenuItem item1 = new SwipeMenuItem(getActivity());
                item1.setBackground(new ColorDrawable(getResources().getColor(R.color.main_red)));
                item1.setWidth(DpAndPx.dip2px(getActivity(), 50));
                item1.setTitle("删除");
                item1.setTitleColor(Color.WHITE);
                item1.setTitleSize(14);
                menu.addMenuItem(item1);
            }
        };
        base_swipeListView.setMenuCreator(creator);
        base_swipeListView.setOnMenuItemClickListener(new SwipeListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(31);
                RxBus.get().post("rxBusMsg", rxBusMsg);
                presenter.removeById(mdatas.get(position).getFavorite_id());

                return false;
            }
        });
        base_swipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CommentDetailActivity.class);
                intent.putExtra("commentid", mdatas.get(position).getOte_id());
                startActivity(intent);
            }
        });
        mAdapter = new PostFragmentAdapter(getActivity(), mdatas, R.layout.item_cyclopedia);
        base_swipeListView.setAdapter(mAdapter);
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 29) {//刷新数据
                    //个人中心需要重新请求数据
                    pageNo = 1;
                    presenter.getContent(pageNo, pageSize);
                } else if (msg.getType() == 64) {
                    if (getUserVisibleHint()) {
                        presenter.removeCollection();
                    }
                }
            }
        };
        observable.subscribe(subscriber);

    }


    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PostFragmentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void refresh(List<PostItem> data) {
        mdatas.clear();
        mdatas.addAll(data);
        mAdapter.notifyDataSetChanged();
        isLoadMore(data.size());
    }

    @Override
    public void loadMore(List<PostItem> data) {
        mdatas.addAll(data);
        mAdapter.notifyDataSetChanged();
        isLoadMore(data.size());
    }

    @Override
    public void resultInfor(String code, String msg) {
        hideLoading();
        bga_basedelete.endLoadingMore();
        bga_basedelete.endRefreshing(0);
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
        if (code.equals("3")) {//未登录
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void deleteResult(EntityResult<String> result) {
        hideLoading();
        ToastTools.showShort(getActivity(), result.getMessage());
    }

    @Override
    public void removeAll(EntityResult<String> result) {
        if (result.getCode() == 0) {
            pageNo = 1;
            presenter.getContent(pageNo, pageSize);
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(26);
            RxBus.get().post("rxBusMsg", rxBusMsg);
        }
    }


    /**
     * 数据加载
     */
//    private void loadData() {
//        presenter.getContent(pageNo, pageSize);
//        pageNo++;
//    }
    @Override
    public void loadData() {
        presenter.getContent(pageNo, pageSize);
    }
    /**
     * 设置是否可进行上拉加载操作
     */
    private void isLoadMore(int size) {
        if (size < pageSize) {
            flag = false;
        } else {
            flag = true;
        }
        bga_basedelete.endLoadingMore();
        bga_basedelete.endRefreshing(0);
        hideLoading();
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        showLoading(Constants.progressName);
        mdatas.clear();
        mAdapter.notifyDataSetChanged();
        pageNo = 1;
        presenter.getContent(pageNo, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (flag == true) {
            showLoading(Constants.progressName);
            pageNo++;
            presenter.getContent(pageNo, pageSize);
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }
}
