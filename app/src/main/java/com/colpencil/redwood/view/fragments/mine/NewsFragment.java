package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CycloParams;
import com.colpencil.redwood.bean.CyclopediaItem;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.widgets.swipe.SwipeListView;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenu;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuCreator;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuItem;
import com.colpencil.redwood.present.mine.CyclopediaFragmentPresenter;
import com.colpencil.redwood.view.activity.cyclopedia.CyclopediaDetailActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.CyclopediaFragmentAdapter;
import com.colpencil.redwood.view.impl.ICyclopediaFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DpAndPx;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

@ActivityFragmentInject(
        contentViewId = R.layout.base_delete_listview
)
public class NewsFragment extends ColpencilFragment implements ICyclopediaFragmentView, BGARefreshLayout.BGARefreshLayoutDelegate {

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

    private CyclopediaFragmentAdapter mAdapter;

    private CyclopediaFragmentPresenter presenter;

    private List<CyclopediaItem> mdatas = new ArrayList<>();

    private Observable<RxBusMsg> observable;

    private Subscriber subscriber;


    @Override
    protected void initViews(View view) {
        bga_basedelete.setDelegate(this);
        bga_basedelete.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_basedelete.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
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
                Intent intent = new Intent(getActivity(), CyclopediaDetailActivity.class);
                CycloParams params = new CycloParams();
                params.article_id = mdatas.get(position).getArticle_id();
                params.type = "news";
                params.title = mdatas.get(position).getTitle();
                params.content = mdatas.get(position).getPage_description();
                params.image = mdatas.get(position).getImage();
                params.time = mdatas.get(position).getCreatime();
                intent.putExtra("params", params);
                startActivity(intent);
            }
        });
        mAdapter = new CyclopediaFragmentAdapter(getActivity(), mdatas, R.layout.item_cyclopedia);
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
                if (msg.getType() == 64) {
                    if (getUserVisibleHint()) {
                        presenter.removeAllCollection(4);
                    }
                }
            }
        };
        observable.subscribe(subscriber);
        loadData();
    }


    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CyclopediaFragmentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(List<CyclopediaItem> data) {
        mdatas.clear();
        mdatas.addAll(data);
        mAdapter.notifyDataSetChanged();
        isLoadMore(data.size());
    }

    @Override
    public void loadMore(List<CyclopediaItem> data) {
        mdatas.addAll(data);
        mAdapter.notifyDataSetChanged();
        isLoadMore(data.size());
    }

    @Override
    public void removeAll(EntityResult<String> result) {
        if (result.getCode() == 0) {
            pageNo = 1;
            loadData();
            RxBusMsg msg = new RxBusMsg();
            msg.setType(26);
            RxBus.get().post("rxBusMsg", msg);
        }
    }

    @Override
    public void resultInfor(String code, String msg) {
        bga_basedelete.endLoadingMore();
        bga_basedelete.endRefreshing(0);
        hideLoading();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
        if (code.equals("3")) {//未登录
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void deleteResult(EntityResult<String> result) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), result.getMessage());
        if (result.getCode() == 3) {//未登录
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }


    /**
     * 数据加载
     */
    private void loadData() {
        presenter.loadNews(pageNo, pageSize);
        pageNo++;
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
        loadData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (flag == true) {
            showLoading(Constants.progressName);
            loadData();
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }
}
