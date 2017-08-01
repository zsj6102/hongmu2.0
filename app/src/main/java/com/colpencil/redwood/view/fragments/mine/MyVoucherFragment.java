package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Coupon;
import com.colpencil.redwood.bean.CouponsResult;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.present.mine.MyCouponsFragmentPresenter;
import com.colpencil.redwood.view.activity.mine.GetCouponsActivity;
import com.colpencil.redwood.view.adapters.MyCouponsFragmentAdapter;
import com.colpencil.redwood.view.impl.IMyCouponsFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：我的代金券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 17 21
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_voucher
)
public class MyVoucherFragment extends ColpencilFragment implements IMyCouponsFragmentView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.bga_base)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.rv_base)
    GridView gridView;
    @Bind(R.id.tv_canUse)
    TextView tv_useNum;

    private MyCouponsFragmentPresenter presenter;
    private MyCouponsFragmentAdapter mAdapter;
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
    private List<Coupon> mdata = new ArrayList<>();
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        mAdapter = new MyCouponsFragmentAdapter(getActivity(), mdata, R.layout.item_voucher, 0, 0);
        gridView.setAdapter(mAdapter);
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
                if (msg.getType() == 33) {//重新请求数据
                    showLoading(Constants.progressName);
                    pageNo = 1;
                    presenter.getContent(pageNo, pageSize);
                }
            }
        };
        observable.subscribe(subscriber);
        showLoading(Constants.progressName);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MyCouponsFragmentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadResult(CouponsResult result) {
        hideLoading();
        isLoadMore(result.getResult().getVoucherList().size());
        tv_useNum.setText(result.getResult().getVoucherListCount() + "");
        if (pageNo == 1) {
            mdata.clear();
            refreshLayout.endRefreshing(0);
        } else {
            refreshLayout.endLoadingMore();
        }
        mdata.addAll(result.getResult().getVoucherList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(String code, String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
    }

    @Override
    public void changeResult(EntityResult<String> result) {

    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        pageNo = 1;
        presenter.getContent(pageNo, pageSize);
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
            presenter.getContent(pageNo, pageSize);
        }
        return false;
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
    }

    @OnClick(R.id.getCoupons)
    void getCouponClick() {
        Intent intent = new Intent(getActivity(), GetCouponsActivity.class);
        intent.putExtra("type", 0);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }

}
