package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BrowsingCyclopediaDate;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.present.mine.BrowsingCyclopediaPresenter;
import com.colpencil.redwood.view.adapters.BrowsingCyclopediaAdapter;
import com.colpencil.redwood.view.impl.IBrowsingCyclopediaView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：浏览百科记录
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/3 09 19
 */
@ActivityFragmentInject(

        contentViewId = R.layout.fragment_browsing
)
public class BrowsingCyclopediaFragment extends ColpencilFragment implements IBrowsingCyclopediaView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.listview_browsing)
    BaseListView listview_browsing;

    @Bind(R.id.bga_browsing)
    BGARefreshLayout bga_browsing;

    /**
     * 分页请求页码
     */
    private long pageNo = 0;
    /**
     * 每页信息条数
     */
    private int pageSize = 10;
    /**
     * 是否可进行上拉加载操作
     */
    private boolean flag;
    private BrowsingCyclopediaAdapter mAdapter;
    private BrowsingCyclopediaPresenter presenter;
    private List<BrowsingCyclopediaDate> mdatas = new ArrayList<>();
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
        mAdapter = new BrowsingCyclopediaAdapter(getActivity(), mdatas, R.layout.item_browsing);
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
                if (msg.getType() == 60) {//刷新浏览记录
                    showLoading("正在删除...");
                    presenter.delete(msg.getFoot_id());
                } else if (msg.getType() == 40) {
                    pageNo = 0;
                    presenter.getContent(pageNo, pageSize);
                }
            }
        };
        observable.subscribe(subscriber);
        showLoading(Constants.progressName);
        loadData();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new BrowsingCyclopediaPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void refresh(List<BrowsingCyclopediaDate> data) {
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
    public void loadMore(List<BrowsingCyclopediaDate> data) {
        mdatas.addAll(data);
        mAdapter.notifyDataSetChanged();
        isLoadMore(data.size());
        if (data != null && data.size() != 0) {
            this.pageNo = data.get(data.size() - 1).getDate();
        }
    }

    @Override
    public void failed(String code, String msg) {
        bga_browsing.endLoadingMore();
        bga_browsing.endRefreshing(0);
        hideLoading();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
    }

    @Override
    public void delete(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 0) {
            pageNo = 0;
            presenter.getContent(pageNo, pageSize);
        }
        ToastTools.showShort(getActivity(), result.getMessage());
    }


    /**
     * 数据加载
     */
    private void loadData() {
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
        showLoading(Constants.progressName);
        mdatas.clear();
        mAdapter.notifyDataSetChanged();
        pageNo = 0;
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
