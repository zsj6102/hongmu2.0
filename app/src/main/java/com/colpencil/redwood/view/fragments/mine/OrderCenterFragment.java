package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.OrderItem;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.utils.Pay.LhjalipayUtils;
import com.colpencil.redwood.function.utils.Pay.Wechat.WeChatPayForUtil;
import com.colpencil.redwood.function.utils.SpaceItemDecoration;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.OrderCenterFragmentPresenter;
import com.colpencil.redwood.view.activity.mine.AfterSalesActivity;
import com.colpencil.redwood.view.activity.mine.EvaluationActivity;
import com.colpencil.redwood.view.activity.mine.OrderDetailActivity;
import com.colpencil.redwood.view.activity.mine.OrderDetailsActivity;
import com.colpencil.redwood.view.activity.mine.RefundMoneyActivity;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.adapters.OrderCenterAdapter;
import com.colpencil.redwood.view.impl.IOrderCenterFragmentView;
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
 * 描述：订单中心
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 09
 */
@ActivityFragmentInject(
        contentViewId = R.layout.base_bgarefreshlayout
)
public class OrderCenterFragment extends ColpencilFragment implements IOrderCenterFragmentView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private String mTypeFlag;//处理类型的标识

    private OrderCenterFragmentPresenter presenter;

    @Bind(R.id.bga_base)
    BGARefreshLayout bga_base;

    @Bind(R.id.rv_base)
    RecyclerView rv_base;

    private OrderCenterAdapter mAdapter;
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

    private CommonDialog serviceDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mTypeFlag = args.getString("key");
        }
    }

    public static OrderCenterFragment getIntance(String typeFlag) {
        OrderCenterFragment orderCenterFragment = new OrderCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", typeFlag);
        orderCenterFragment.setArguments(bundle);
        return orderCenterFragment;
    }

    @Override
    protected void initViews(View view) {
        if (mTypeFlag.equals("0")) {
            showLoading(Constants.progressName);
        }
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.padding_10dp);
        rv_base.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        bga_base.setDelegate(this);
        bga_base.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_base.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        mAdapter = new OrderCenterAdapter(rv_base, mTypeFlag);
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
            public void onNext(final RxBusMsg msg) {
                if (msg.getType() == 7 && msg.getMsg().equals(mTypeFlag)) {//处理订单操作
                    serviceDialog = new CommonDialog(getActivity(), "确认取消订单?", "确定", "取消");
                    serviceDialog.setListener(new DialogOnClickListener() {
                        @Override
                        public void sureOnClick() {
                            presenter.cancelOrderById(msg.getOrderNum() + "");
                            serviceDialog.dismiss();
                            showLoading(Constants.progressName);
                        }

                        @Override
                        public void cancleOnClick() {
                            serviceDialog.dismiss();
                        }
                    });
                    serviceDialog.show();
                } else if (msg.getType() == 8 && msg.getMsg().equals(mTypeFlag)) {//立即支付
                    showLoading(Constants.progressName);
                    presenter.payKeyInfor(Integer.valueOf(msg.getOrderNum()));
                } else if (msg.getType() == 9 && msg.getMsg().equals(mTypeFlag)) {//申请退款
                    Intent intent = new Intent(getActivity(), RefundMoneyActivity.class);
                    intent.putExtra("order_id", Integer.valueOf(msg.getOrderNum()));
                    startActivity(intent);
                } else if (msg.getType() == 10 && msg.getMsg().equals(mTypeFlag)) {//查看物流
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("key", "OrderCenterFragmentPresenter");
                    intent.putExtra("orderId", Integer.valueOf(msg.getOrderNum()));
                    startActivity(intent);
                } else if (msg.getType() == 11 && msg.getMsg().equals(mTypeFlag)) {//确认收货
                    serviceDialog = new CommonDialog(getActivity(), "确认收货?", "确定", "取消");
                    serviceDialog.setListener(new DialogOnClickListener() {
                        @Override
                        public void sureOnClick() {
                            serviceDialog.dismiss();
                            showLoading(Constants.progressName);
                            presenter.confirmRecept(Integer.valueOf(msg.getOrderNum()));
                        }

                        @Override
                        public void cancleOnClick() {
                            serviceDialog.dismiss();
                        }
                    });
                    serviceDialog.show();
                } else if (msg.getType() == 12 && msg.getMsg().equals(mTypeFlag)) {//确认取消退款申请
                    serviceDialog = new CommonDialog(getActivity(), "确认取消退款申请?", "确定", "取消");
                    serviceDialog.setListener(new DialogOnClickListener() {
                        @Override
                        public void sureOnClick() {
                            presenter.cancelApplyForRefund(Integer.valueOf(msg.getOrderNum()), msg.getReturn_order_id());
                            serviceDialog.dismiss();
                            showLoading(Constants.progressName);
                        }

                        @Override
                        public void cancleOnClick() {
                            serviceDialog.dismiss();
                        }
                    });
                    serviceDialog.show();
                } else if (msg.getType() == 13 && msg.getMsg().equals(mTypeFlag)) {//申请售后
                    Intent intent = new Intent(getActivity(), AfterSalesActivity.class);
                    intent.putExtra("order_id", Integer.valueOf(msg.getOrderNum()));
                    intent.putExtra("item_id", msg.getItem_id());
                    startActivity(intent);
                } else if (msg.getType() == 14 && msg.getMsg().equals(mTypeFlag)) {//进行评价
                    Intent intent = new Intent(getActivity(), EvaluationActivity.class);
                    intent.putExtra("order_id", Integer.valueOf(msg.getOrderNum()));
                    intent.putExtra("goods_id", msg.getGoods_id());
                    intent.putExtra("goodName", msg.getName());
                    startActivity(intent);
                } else if (msg.getType() == 35) {//刷新订单界面
                    mAdapter.clear();//清空数据
                    pageNo = 1;
                    if (mTypeFlag.equals("0")) {
                        showLoading(Constants.progressName);
                    }
                    presenter.getContent(pageNo, pageSize, Integer.valueOf(mTypeFlag));
                } else if (msg.getType() == 51 && msg.getMsg().equals(mTypeFlag)) {//提醒卖家发货
                    presenter.remindDelivery(msg.getSn());
                    showLoading(Constants.progressName);
                } else if (msg.getType() == 52 && msg.getMsg().equals(mTypeFlag)) {//进入订单详情
                    Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                    intent.putExtra("order_id", Integer.valueOf(msg.getOrderNum()));
                    intent.putExtra("mTypeFlag", mTypeFlag);
                    intent.putExtra("status", msg.getStatus());
                    intent.putExtra("return_order_id", msg.getReturn_order_id());
                    intent.putExtra("sn", msg.getSn());
                    intent.putExtra("statusName", msg.getStateName());
                    startActivity(intent);
                } else if (msg.getType() == 64 && msg.getMsg().equals(mTypeFlag)) {
                    showLoading(Constants.progressName);
                    presenter.deleteById(mAdapter.getItem(msg.getPosition()).getOrder_id());
                }
            }
        };
        observable.subscribe(subscriber);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new OrderCenterFragmentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

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

    @Override
    public void deleteResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 1) {
            pageNo = 1;
            presenter.getContent(pageNo, pageSize, Integer.valueOf(mTypeFlag));
        }
        ToastTools.showShort(getActivity(), result.getMsg());
    }

    @Override
    public void refresh(List<OrderItem> data) {
        mAdapter.clear();
        mAdapter.addNewDatas(data);//添加最新数据
        mAdapter.notifyDataSetChanged();//适配器进行刷新操作
        //停止刷新
        bga_base.endRefreshing(data.size());
        //判断是否可以进行上拉加载更多操作
        isLoadMore(data.size());
    }

    @Override
    public void loadMore(List<OrderItem> data) {
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
    public void success(String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
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
        presenter.getContent(pageNo, pageSize, Integer.valueOf(mTypeFlag));
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
            pageNo++;
            presenter.getContent(pageNo, pageSize, Integer.valueOf(mTypeFlag));
        }
        return false;
    }

    /**
     * 数据加载
     */
//    private void loadData() {
//        presenter.getContent(pageNo, pageSize, Integer.valueOf(mTypeFlag));
//        pageNo++;
//    }
    @Override
    public void loadData() {
        presenter.getContent(pageNo, pageSize, Integer.valueOf(mTypeFlag));
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);      //处理银联返回结果
        if (data == null) {
            return;
        }
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            ToastTools.showShort(getActivity(), "银联支付成功");
        } else {
            ToastTools.showShort(getActivity(), "银联支付失败");
        }
    }
}
