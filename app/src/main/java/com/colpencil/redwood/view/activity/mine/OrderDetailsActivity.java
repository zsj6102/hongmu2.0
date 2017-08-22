package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.OrderDetailsReturn;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.utils.Pay.LhjalipayUtils;
import com.colpencil.redwood.function.utils.Pay.Wechat.WeChatPayForUtil;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.OrderDetailsPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.BuyGoodsInforAdapter;
import com.colpencil.redwood.view.impl.IOrderDetailsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.FormatUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.WrapHeightListView;
import com.unionpay.UPPayAssistEx;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

import static com.colpencil.redwood.R.id.leave_message;

/**
 * @author 曾 凤
 * @Description: 订单详情
 * @Email 20000263@qq.com
 * @date 2016/8/22
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_orderdetails
)
public class OrderDetailsActivity extends ColpencilActivity implements IOrderDetailsView {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.orderdetails_No)
    TextView tv_orderNo;
    @Bind(R.id.orderdetails_State)
    TextView tv_orderState;
    @Bind(R.id.orderdetails_GoodInfor)
    WrapHeightListView listView;
    @Bind(R.id.orderdetails_goodsPrice)
    TextView tv_price;
    @Bind(R.id.orderdetails_goodsCoupons)
    TextView tv_coupons;
    @Bind(R.id.orderdetails_goodsVoucher)
    TextView tv_vouchers;
    @Bind(R.id.orderdetails_payName)
    TextView tv_payType;
    @Bind(R.id.orderdetails_goodsPay)
    TextView tv_payPrice;
    @Bind(R.id.orderdetails_address)
    TextView tv_address;
    @Bind(R.id.orderdetails_people)
    TextView tv_receiver;
    @Bind(R.id.order_btn1)
    TextView btn1;
    @Bind(R.id.order_btn2)
    TextView btn2;
    @Bind(R.id.order_btn3)
    TextView btn3;
    @Bind(R.id.orderdetails_discount)
    TextView tv_discount;
    @Bind(R.id.orderdetails_postage)
    TextView tv_postage;
    @Bind(R.id.orderdetails_time)
    TextView tv_time;
    @Bind(R.id.tv_storename)
    TextView tv_store;
    @Bind(R.id.leave_message)
    TextView tv_message;

    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private OrderDetailsPresenter presenter;
    private int status;
    private int order_id;
    private int statusName;
    private int return_order_id;

    @Override
    protected void initViews(View view) {
        initParams();
        initViews();
        initBus();
        initData();
    }

    private void initParams() {
        status = getIntent().getIntExtra("status", 0);
        order_id = getIntent().getIntExtra("order_id", 0);
        statusName = getIntent().getIntExtra("statusName", 0);
        return_order_id = getIntent().getIntExtra("return_order_id", 0);
    }

    private void initViews() {
        tv_title.setText("订单详情");

        if (status == 0) {      //待付款
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn2.setText("取消订单");
            btn3.setText("立即付款");
        } else if (status == 4) {       //已付款
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn2.setText("申请退款");
            btn3.setText("提醒发货");
            if (statusName == 12) {       //退款已拒绝
                btn2.setVisibility(View.GONE);
            }
        } else if (status == 5) {       //卖家已发货
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn1.setText("申请退款");
            btn2.setText("查看物流");
            btn3.setText("确认收货");
            if (statusName == 12) {       //退款已拒绝
                btn1.setVisibility(View.GONE);
            }
        } else if (status == -1) {      //退款
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.VISIBLE);
            btn3.setText("取消退款");
        } else {        //
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
        }
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
            public void onNext(final RxBusMsg msg) {
                if (msg.getType() == 45) {//支付宝支付成功
                    ToastTools.showShort(OrderDetailsActivity.this, "支付成功");
                    finish();
                } else if (msg.getType() == 46) {//支付宝支付失败
                    ToastTools.showShort(OrderDetailsActivity.this, "支付失败");
                    finish();
                } else if (msg.getType() == 47) {//微信支付成功
                    ToastTools.showShort(OrderDetailsActivity.this, "支付成功");
                    finish();
                } else if (msg.getType() == 48) {//微信支付失败
                    ToastTools.showShort(OrderDetailsActivity.this, "支付失败");
                    finish();
                } else if (msg.getType() == 35) {
                    presenter.getContent(order_id);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void initData() {
        presenter.getContent(order_id);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new OrderDetailsPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void result(OrderDetailsReturn result) {
        hideLoading();
        if (result.getCode() == 1) {//数据获取成功
            tv_orderNo.setText(result.getResult().getOrder_sn());
            tv_orderState.setText(result.getResult().getStateName());
            listView.setAdapter(new BuyGoodsInforAdapter(this, result.getResult().getOrderItemsList(),
                    R.layout.item_buygoodsinfor, "0", 0, 0, 0));
            tv_price.setText("+" + FormatUtils.formatDouble(result.getResult().getCountPrice()));
            tv_coupons.setText("-" + FormatUtils.formatDouble(result.getResult().getMoneydiscount()));
            tv_vouchers.setText("-" + FormatUtils.formatDouble(result.getResult().getVoucherids()));
            tv_postage.setText("+" + FormatUtils.formatDouble(result.getResult().getPostagePrice()));
            tv_payType.setText(result.getResult().getPayType());
            tv_payPrice.setText("+" + FormatUtils.formatDouble(result.getResult().getPayPrice()));
            tv_address.setText(result.getResult().getShip_area());
            tv_receiver.setText(result.getResult().getAddresscontact());
            tv_discount.setText("-" + FormatUtils.formatDouble(result.getResult().getDiscount()));
            tv_time.setText(result.getResult().getCreate_time());
            tv_store.setText(result.getResult().getStrore_name());
            tv_message.setText(result.getResult().getLeave_message());
            if (status != 7) {
                if (result.getResult().getStateFlag() == 0) {      //待付款
                    btn2.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
                    btn2.setText("取消订单");
                    btn3.setText("立即付款");
                } else if (result.getResult().getStateFlag() == 4) {       //已付款
                    btn2.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
                    btn2.setText("申请退款");
                    btn3.setText("提醒发货");
                    if (result.getResult().getOrderItemsList().get(0).getState() == 12) {       //退款已拒绝
                        btn2.setVisibility(View.GONE);
                    }
                } else if (result.getResult().getStateFlag() == 5) {       //卖家已发货
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
                    btn1.setText("申请退款");
                    btn2.setText("查看物流");
                    btn3.setText("确认收货");
                    if (result.getResult().getOrderItemsList().get(0).getState() == 12) {       //退款已拒绝
                        btn1.setVisibility(View.GONE);
                    }
                } else if (result.getResult().getStateFlag() == -1 || result.getResult().getStateFlag() == 7) {      //退款
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.VISIBLE);
                    btn3.setText("取消退款");
                } else {        //
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                }
                statusName = result.getResult().getOrderItemsList().get(0).getState();
                status = result.getResult().getStateFlag();
            }
        } else if (result.getCode() == 3) {//用户未登录
            ToastTools.showShort(this, result.getMsg());
            Intent intent = new Intent(OrderDetailsActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            ToastTools.showShort(this, result.getMsg());
        }
    }

    @Override
    public void fail(int code, String msg) {
        hideLoading();
        ToastTools.showShort(this, msg);
        if (code == 3) {//用户未登录
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void success(String msg, int mState) {
        hideLoading();
        ToastTools.showShort(this, msg);
        if (mState == 1) {  //取消订单成功
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            tv_orderState.setText("已取消");
        } else if (mState == 2) {   //取消退款成功
            status = 4;
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn2.setText("申请退款");
            btn3.setText("提醒发货");
            if (statusName == 12) {       //退款已拒绝
                btn2.setVisibility(View.GONE);
            }
            tv_orderState.setText("已付款待发货");
        } else if (mState == 3) {   //提醒发货成功，不修改状态
            status = 4;
            btn3.setVisibility(View.VISIBLE);
            btn3.setText("提醒发货");
        } else {    //确认收货成功
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            tv_orderState.setText("已完成");
        }
    }

    @Override
    public void payByWeChat(Map<String, String> map) {
        hideLoading();
        new WeChatPayForUtil(this, map);
    }

    @Override
    public void payByAlipay(String reStr) {
        hideLoading();
        LhjalipayUtils.getInstance(this).pay(reStr);
    }

    @Override
    public void payByUnion(String tn, String mode) {
        hideLoading();
        UPPayAssistEx.startPay(this, null, null, tn, mode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    //只有一个申请退款的状态
    @OnClick(R.id.order_btn1)
    void btn1Click() {
        Intent intent = new Intent(this, RefundMoneyActivity.class);
        intent.putExtra("order_id", order_id);
        startActivity(intent);
    }

    @OnClick(R.id.order_btn2)
    void btn2Click() {
        if (status == 0) {  //取消订单
            cancleOrder();
        } else if (status == 4) {   //申请退款
            Intent intent = new Intent(this, RefundMoneyActivity.class);
            intent.putExtra("order_id", order_id);
            startActivity(intent);
        } else if (status == 5) {   //查看物流
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("key", "OrderCenterFragmentPresenter");
            intent.putExtra("orderId", order_id);
            startActivity(intent);
        }
    }

    @OnClick(R.id.order_btn3)
    void btn3Click() {
        if (status == 0) {    //立即付款
            showLoading(Constants.progressName);
            presenter.payKeyInfor(order_id);
        } else if (status == 4) {   //提醒发货
            showLoading(Constants.progressName);
            presenter.remindDelivery(getIntent().getStringExtra("sn"));
        } else if (status == 5) {   //确认收货
            final CommonDialog dialog = new CommonDialog(this, "确认收货?", "确定", "取消");
            dialog.setListener(new DialogOnClickListener() {
                @Override
                public void sureOnClick() {
                    presenter.confirmRecept(getIntent().getIntExtra("order_id", 0));
                    dialog.dismiss();
                    showLoading(Constants.progressName);
                }

                @Override
                public void cancleOnClick() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else if (status == -1) {  //取消退款
            final CommonDialog dialog = new CommonDialog(this, "确认取消退款申请?", "确定", "取消");
            dialog.setListener(new DialogOnClickListener() {
                @Override
                public void sureOnClick() {
                    presenter.cancelApplyForRefund(order_id, return_order_id);
                    dialog.dismiss();
                    showLoading(Constants.progressName);
                }

                @Override
                public void cancleOnClick() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    //取消订单
    private void cancleOrder() {
        final CommonDialog dialog = new CommonDialog(this, "确认取消订单?", "确定", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                presenter.cancelOrderById(order_id + "");
                dialog.dismiss();
                showLoading(Constants.progressName);
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
