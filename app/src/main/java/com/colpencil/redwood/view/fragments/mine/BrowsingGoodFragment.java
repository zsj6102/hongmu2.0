package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BrowsingGoodDate;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.present.mine.BrowsingGoodPresenter;
import com.colpencil.redwood.view.adapters.BrowsingGoodAdapter;
import com.colpencil.redwood.view.impl.IBrowsingGoodView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：商品浏览记录
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/3 09 19
 */
@ActivityFragmentInject(

        contentViewId = R.layout.fragment_browsing
)
public class BrowsingGoodFragment extends ColpencilFragment implements IBrowsingGoodView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.listview_browsing)
    BaseListView listview_browsing;

    @Bind(R.id.bga_browsing)
    BGARefreshLayout bga_browsing;

    /**
     * 分页请求页码
     */
    private long pageNo = 1;
    /**
     * 每页信息条数
     */
    private int pageSize = 10;
    /**
     * 是否可进行上拉加载操作
     */
    private boolean flag;
    private BrowsingGoodAdapter mAdapter;
    private BrowsingGoodPresenter presenter;
    private List<BrowsingGoodDate> mdatas = new ArrayList<>();
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        bga_browsing.setDelegate(this);
        bga_browsing.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_browsing.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initData();
    }

    private void initData() {
        mAdapter = new BrowsingGoodAdapter(getActivity(), mdatas, R.layout.item_browsing);
        listview_browsing.setAdapter(mAdapter);
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
                if (msg.getType() == 62) {//刷新浏览记录
                    showLoading("正在删除该记录...");
                    presenter.delete(msg.getFoot_id());
                } else if (msg.getType() == 40) {
                    pageNo = 1;
                    presenter.getContent(pageNo, pageSize);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new BrowsingGoodPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void refresh(List<BrowsingGoodDate> data) {
        hideLoading();
        mdatas.clear();
        mdatas.addAll(data);
        mAdapter.notifyDataSetChanged();//适配器进行刷新操作
        //判断是否可以进行上拉加载更多操作
        isLoadMore(data.size());
        if (data != null && data.size() != 0) {
            this.pageNo = data.get(data.size() - 1).getDate();
        }
    }

    @Override
    public void loadMore(List<BrowsingGoodDate> data) {
        mdatas.addAll(data);
        mAdapter.notifyDataSetChanged();
        bga_browsing.endRefreshing(0);
        isLoadMore(data.size());
        if (!ListUtils.listIsNullOrEmpty(data)) {
            this.pageNo = data.get(data.size() - 1).getDate();
        }
    }

    @Override
    public void fail(String msg) {
        bga_browsing.endLoadingMore();
        bga_browsing.endRefreshing(0);
        hideLoading();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
    }

    @Override
    public void delete(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 0) {
            pageNo = 1;
            presenter.getContent(pageNo, pageSize);
        }
    }


    /**
     * 数据加载
     */
//    private void loadData() {
//        presenter.getContent(pageNo, pageSize);
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
        bga_browsing.endLoadingMore();
        bga_browsing.endRefreshing(0);
        hideLoading();
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        pageNo = 1;
        presenter.getContent(pageNo, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (flag == true) {
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
