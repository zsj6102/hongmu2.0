package com.colpencil.redwood.view.activity.ShoppingCartActivitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.PayType;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.utils.Pay.LhjalipayUtils;
import com.colpencil.redwood.function.utils.Pay.Wechat.WeChatPayForUtil;
import com.colpencil.redwood.present.PayPresenter;

import com.colpencil.redwood.view.adapters.PayTypeAdapter;
import com.colpencil.redwood.view.impl.IPayView;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;

import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;
import com.unionpay.UPPayAssistEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 订单中心转支付
 * 保证金支付
 * 入驻费缴纳
 *
 */
@ActivityFragmentInject(contentViewId = R.layout.pay_layout)
public class OrderCenterToPay extends ColpencilActivity implements IPayView {
    @Bind(R.id.pay_post)
    MosaicListView payPost;
    @Bind(R.id.pay_layout)
    LinearLayout layoutPay;
    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    private int minute = 20;
    private int second = 0;
    private PayPresenter presenter;
    private PayTypeAdapter adapter;
    private int selectPay = -1;
    private List<PayType> payTypes = new ArrayList<>();
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private TimerTask timerTask;
    private Timer timer;
    private String orderId;
    private String from;
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.text2)
    TextView text2;
    @Bind(R.id.text3)
    TextView text3;
    @Bind(R.id.text4)
    TextView text4;
    private int sec_id;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (minute == 0) {
                if (second == 0) {
                    text1.setText("0");
                    text2.setText("0");
                    text3.setText("0");
                    text4.setText("0");
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        text1.setText("0");
                        text2.setText("0");
                        text3.setText(String.valueOf(second).substring(0, 1));
                        text4.setText(String.valueOf(second).substring(1));
                    } else {
                        text1.setText("0");
                        text2.setText("0");
                        text3.setText("0");
                        text4.setText(String.valueOf(second));
                    }
                }
            } else {
                if (second == 0) {
                    second = 59;
                    minute--;
                    if (minute >= 10) {
                        text1.setText(String.valueOf(minute).substring(0, 1));
                        text2.setText(String.valueOf(minute).substring(1));
                        text3.setText(String.valueOf(second).substring(0, 1));
                        text4.setText(String.valueOf(second).substring(1));

                    } else {
                        text1.setText("0");
                        text2.setText(String.valueOf(minute));
                        text3.setText(String.valueOf(second).substring(0, 1));
                        text4.setText(String.valueOf(second).substring(1));
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        if (minute >= 10) {
                            text1.setText(String.valueOf(minute).substring(0, 1));
                            text2.setText(String.valueOf(minute).substring(1));
                            text3.setText(String.valueOf(second).substring(0, 1));
                            text4.setText(String.valueOf(second).substring(1));

                        } else {
                            text1.setText("0");
                            text2.setText(String.valueOf(minute));
                            text3.setText(String.valueOf(second).substring(0, 1));
                            text4.setText(String.valueOf(second).substring(1));
                        }
                    } else {
                        if (minute >= 10) {
                            text1.setText(String.valueOf(minute).substring(0, 1));
                            text2.setText(String.valueOf(minute).substring(1));
                            text3.setText("0");
                            text4.setText(String.valueOf(second));

                        } else {
                            text1.setText("0");
                            text2.setText(String.valueOf(minute));
                            text3.setText("0");
                            text4.setText(String.valueOf(second));
                        }
                    }
                }
            }
        }
    };
    @Override
    protected void initViews(View view) {
        tvMainTitle.setText("订单支付");
        from = getIntent().getStringExtra("from");
        if(from.equals("orderCenter")){
            orderId = getIntent().getStringExtra("orderId");
        }
        if(from.equals("settled")){
            sec_id = getIntent().getExtras().getInt("sec_id");
        }
        layoutPay.setVisibility(View.GONE);
        presenter.getPayType();
        adapter = new PayTypeAdapter(OrderCenterToPay.this, payTypes, R.layout.item_payment_other);
        payPost.setAdapter(adapter);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
        text1.setText("2");
        text2.setText("0");
        text3.setText("0");
        text4.setText("0");
        initBus();
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
                if (msg.getType() == 42) {//更新选中的支付方式
                    selectPay = msg.getPosition();
                    for (int i = 0; i < payTypes.size(); i++) {
                        if (i == msg.getPosition()) {
                            payTypes.get(i).setChooseState(true);
                        } else {
                            payTypes.get(i).setChooseState(false);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else if (msg.getType() == 45) {//支付宝支付成功
                    showMsg("支付成功");
                    intent();
                } else if (msg.getType() == 46) {//支付宝支付失败
                    showMsg("支付失败");
                    intent();
                } else if (msg.getType() == 47) {//微信支付成功
                    showMsg("支付成功");
                    intent();
                } else if (msg.getType() == 48) {//微信支付失败
                    showMsg("支付失败");
                    intent();
                } else if (msg.getType() == 49) {
                    showMsg("微信支付取消");
                    intent();
                }
            }
        };
        observable.subscribe(subscriber);
    }
    @OnClick(R.id.iv_back)
    void back(){
        finish();
    }


    @OnClick(R.id.btn_pay)
    void pay() {
        if (selectPay == -1) {
            ToastTools.showShort(this, "请选择支付方式");
            return;
        } else {

            String payid = String.valueOf(payTypes.get(selectPay).getPayId());
            Map<String, String> params = new HashMap<>();
            params.put("token",  SharedPreferencesUtil.getInstance(this).getString("token"));
            params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id") + "");
            params.put("payment_id",  payid);
            if(from.equals("orderCenter")){
                params.put("order_id",orderId);
                presenter.getCenterReturn(params);
            }else if(from.equals("settled")){
              params.put("sc_id",sec_id+"");
                presenter.getSettledPay(params);
            }else{
                presenter.getMarginPay(params);
            }

        }
    }
    private void showMsg(String msg){
        ToastTools.showShort(this,msg);
    }

    private void intent() {
        finish();
    }

    @Override
    public void fail(int code, String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), msg);
    }

    @Override
    public void payByWeChat(Map<String, String> map) {
        hideLoading();
        new WeChatPayForUtil(OrderCenterToPay.this, map);
    }

    @Override
    public void payByAlipay(String reStr) {
        hideLoading();
        LhjalipayUtils.getInstance(OrderCenterToPay.this).pay(reStr);
    }

    @Override
    public void payByUnion(String tn, String mode) {
        hideLoading();
        UPPayAssistEx.startPay(this, null, null, tn, mode);
    }

    @Override
    public void payType(List<PayType> pays) {
        payTypes.addAll(pays);
        adapter.notifyDataSetChanged();
    }



    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PayPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        minute = -1;
        second = -1;
        RxBus.get().unregister("rxBusMsg", observable);
    }
}
