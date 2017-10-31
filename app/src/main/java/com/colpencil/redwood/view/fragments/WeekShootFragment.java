package com.colpencil.redwood.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.WeekAuctionList;
import com.colpencil.redwood.present.WeekShootPresenter;
import com.colpencil.redwood.view.adapters.WeekShootAdapter;
import com.colpencil.redwood.view.impl.IWeekShootView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：周拍的Fragment
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 10 33
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_weekshoot
)
@SuppressLint("ValidFragment")
public class WeekShootFragment extends ColpencilFragment implements IWeekShootView, BGARefreshLayoutDelegate {
    /**
     * 处理的类型
     */
    private String mtype;

    private WeekShootPresenter weekShootPresenter;

    @Bind(R.id.bga_weekShoot)
    BGARefreshLayout bga_weekShoot;

    @Bind(R.id.rv_weekShoot)
    RecyclerView rv_weekShoot;

    private WeekShootAdapter mAdapter;
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
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;

    public static WeekShootFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        WeekShootFragment fragment = new WeekShootFragment();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initViews(View view) {
        this.mtype = getArguments().getString("type");
        bga_weekShoot.setDelegate(this);
        bga_weekShoot.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_weekShoot.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initData();
        initBus();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        mAdapter = new WeekShootAdapter(rv_weekShoot, getActivity());
        rv_weekShoot.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_weekShoot.setAdapter(mAdapter);

    }

    private void initBus() {
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
                if (msg.getType() == 65) {
                    pageNo = 1;
                    weekShootPresenter.getContent(mtype, pageNo, pageSize);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        weekShootPresenter = new WeekShootPresenter();
        return weekShootPresenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    /**
     * 数据加载
     */
//    private void loadData() {
//        weekShootPresenter.getContent(mtype, pageNo, pageSize);
//        pageNo++;
//    }
    @Override
    public void loadData() {
        weekShootPresenter.getContent(mtype, pageNo, pageSize);
    }
    @Override
    public void refresh(List<WeekAuctionList> data) {
        mAdapter.addNewDatas(data);//添加最新数据
        mAdapter.notifyDataSetChanged();//适配器进行刷新操作
        //停止刷新
        bga_weekShoot.endRefreshing(data.size());
        //判断是否可以进行上拉加载更多操作
        isLoadMore(data.size());
    }

    @Override
    public void loadMore(List<WeekAuctionList> data) {
        mAdapter.addMoreDatas(data);//增加新数据
        mAdapter.notifyDataSetChanged();
        //停止加载
        bga_weekShoot.endLoadingMore();
        isLoadMore(data.size());
    }

    @Override
    public void loadError(String msg) {
        hideLoading();
        ToastTools.showShort(getActivity(),msg);
        bga_weekShoot.endRefreshing(0);
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mAdapter.clear();//清空数据
        pageNo = 1;
        weekShootPresenter.getContent(mtype, pageNo, pageSize);
    }

    /**
     * 上拉加载
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (flag == true) {
            pageNo++;
            weekShootPresenter.getContent(mtype, pageNo, pageSize);
        }
        return false;
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
    }

    @OnClick(R.id.totop_iv)
    void totop() {
        rv_weekShoot.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }
}
