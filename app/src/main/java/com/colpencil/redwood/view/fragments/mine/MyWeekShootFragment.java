package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MyWeekShootItem;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.utils.Pay.LhjalipayUtils;
import com.colpencil.redwood.function.utils.Pay.Wechat.WeChatPayForUtil;
import com.colpencil.redwood.function.utils.SpaceItemDecoration;
import com.colpencil.redwood.present.mine.MyWeekShootFragmentPresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.PaymentActivity;
import com.colpencil.redwood.view.activity.home.ProductdetailsActivity;
import com.colpencil.redwood.view.activity.home.WeekShootActivity;
import com.colpencil.redwood.view.adapters.MyWeekShootAdapter;
import com.colpencil.redwood.view.impl.IMyWeekShootFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.unionpay.UPPayAssistEx;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：我的竞拍
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 17 21
 */
@ActivityFragmentInject(
        contentViewId = R.layout.base_bgarefreshlayout
)
public class MyWeekShootFragment extends ColpencilFragment implements IMyWeekShootFragmentView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private String mTypeFlag;//处理类型的标识

    private MyWeekShootFragmentPresenter presenter;

    @Bind(R.id.bga_base)
    BGARefreshLayout bga_base;

    @Bind(R.id.rv_base)
    RecyclerView rv_base;

    private MyWeekShootAdapter mAdapter;
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

    public static MyWeekShootFragment getIntance(String typeFlag) {
        MyWeekShootFragment myWeekShootFragment = new MyWeekShootFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", typeFlag);
        myWeekShootFragment.setArguments(bundle);
        return myWeekShootFragment;
    }

    @Override
    protected void initViews(View view) {
        mTypeFlag = getArguments().getString("key");
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.padding_10dp);
        rv_base.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        bga_base.setDelegate(this);
        bga_base.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_base.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initData();
        handler.postDelayed(runnable,10000);
    }

    /**
     * 数据初始化
     */
    private void initData() {
        mAdapter = new MyWeekShootAdapter(rv_base, mTypeFlag);
        rv_base.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_base.setAdapter(mAdapter);
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
                if (msg.getType() == 19 && msg.getMsg().equals(mTypeFlag)) {//去竞拍
                    Intent intent = new Intent(getActivity(), WeekShootActivity.class);
                    startActivity(intent);
                } else if (msg.getType() == 18 && msg.getMsg().equals(mTypeFlag)) {//立即支付
                    Intent intent = new Intent(getActivity(), PaymentActivity.class);
                    intent.putExtra("goFrom", "MyWeekShootFragment");
                    intent.putExtra("product_id", msg.getProduct_id());
                    intent.putExtra("goods_id", msg.getGoods_id());
                    intent.putExtra("key", "订单确认");
                    startActivity(intent);
                } else if (msg.getType() == 45 || msg.getType() == 46 || msg.getType() == 47 || msg.getType() == 48) {//支付情况回调
                    if (mTypeFlag.equals("0")) {
                        showLoading(Constants.progressName);
                    }
                    mAdapter.clear();//清空数据
                    pageNo = 1;
                    loadData();
                } else if (msg.getType() == 65) {
                    presenter.payKeyInfor(msg.getOrder_id());
                }
            }
        };
        observable.subscribe(subscriber);
        if (mTypeFlag.equals("0")) {
            showLoading(Constants.progressName);
        }
        loadData();
        mAdapter.setListener(new MyWeekShootAdapter.ItemClickListener() {
            @Override
            public void itemClick(int position) {
                Intent intent = new Intent(getActivity(), ProductdetailsActivity.class);
                intent.putExtra("goodsId", mAdapter.getItem(position).getId());
                startActivity(intent);
            }

            @Override
            public void deleteClick(int position) {
                showLoading(Constants.progressName);
                presenter.deleteById(mAdapter.getItem(position).getLogs_id());
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MyWeekShootFragmentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(List<MyWeekShootItem> data) {
        hideLoading();
        mAdapter.addNewDatas(data);//添加最新数据
        mAdapter.notifyDataSetChanged();//适配器进行刷新操作
        //停止刷新
        bga_base.endRefreshing(data.size());
        //判断是否可以进行上拉加载更多操作
        isLoadMore(data.size());
    }

    @Override
    public void loadMore(List<MyWeekShootItem> data) {
        mAdapter.addMoreDatas(data);//增加新数据
        mAdapter.notifyDataSetChanged();
        //停止加载
        bga_base.endLoadingMore();
        isLoadMore(data.size());
    }

    @Override
    public void fail(int code, String msg) {
        hideLoading();
        bga_base.endLoadingMore();
        bga_base.endRefreshing(0);
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
    }

    @Override
    public void deleteResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 1) {
            pageNo = 1;
            presenter.getContent(pageNo, pageSize, mTypeFlag);
        }
        ToastTools.showShort(getActivity(), result.getMsg());
    }

    @Override
    public void payByWeChat(Map<String, String> map) {
        hideLoading();
        new WeChatPayForUtil(getActivity(), map);
    }

    @Override
    public void payByAlipay(String reStr) {
        hideLoading();
        LhjalipayUtils.getInstance(getActivity()).pay(reStr);
    }

    @Override
    public void payByUnion(String tn, String mode) {
        hideLoading();
        UPPayAssistEx.startPay(getActivity(), null, null, tn, mode);
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        showLoading(Constants.progressName);
        mAdapter.clear();//清空数据
        pageNo = 1;
        loadData();
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
            showLoading(Constants.progressName);
            loadData();
        }
        return false;
    }

    /**
     * 数据加载
     */
    private void loadData() {
        presenter.getContent(pageNo, pageSize, mTypeFlag);
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
        hideLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
        handler.removeCallbacks(runnable);
    }

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            pageNo = 1;
            presenter.getContent(pageNo, pageSize, mTypeFlag);
            handler.postDelayed(this, 10000);
        }
    };
}
