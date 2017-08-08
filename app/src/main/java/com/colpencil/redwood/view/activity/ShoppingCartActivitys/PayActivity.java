package com.colpencil.redwood.view.activity.ShoppingCartActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.IntenToPay;
import com.colpencil.redwood.bean.PayType;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.StroeOrderItem;
import com.colpencil.redwood.function.utils.Pay.LhjalipayUtils;
import com.colpencil.redwood.function.utils.Pay.Wechat.WeChatPayForUtil;
import com.colpencil.redwood.present.PayPresenter;
import com.colpencil.redwood.view.activity.mine.OrderCenterActivity;
import com.colpencil.redwood.view.adapters.PayTypeAdapter;
import com.colpencil.redwood.view.impl.IPayView;
import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

@ActivityFragmentInject(contentViewId = R.layout.pay_layout)
public class PayActivity extends ColpencilActivity implements IPayView {

    @Bind(R.id.pay_post)
    MosaicListView payPost;
    @Bind(R.id.tv_all)
    TextView tvfee;
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.text2)
    TextView text2;
    @Bind(R.id.text3)
    TextView text3;
    @Bind(R.id.text4)
    TextView text4;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    private int minute = 20;
    private int second = 0;
    private IntenToPay payinfo;
    private List<StroeOrderItem> orderItems = new ArrayList<>();
    private List<PayType> payTypes = new ArrayList<>();
    private int addid;
    private PayPresenter presenter;
    private PayTypeAdapter adapter;
    private int selectPay = -1;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private String goFrom;
    private TimerTask timerTask;
    private Timer timer;

    @Override
    protected void initViews(View view) {
        payinfo = (IntenToPay) getIntent().getSerializableExtra("payinfo");
        goFrom = getIntent().getStringExtra("goFrom");
        tvMainTitle.setText("订单支付");
        initData();
        initAdapter();
        initBus();
    }

    private void initData() {
        double price = 0;
        for (int i = 0; i < payinfo.getOrderArrays().size(); i++) {
            StroeOrderItem item = new StroeOrderItem();
            item.setCart_ids(payinfo.getOrderArrays().get(i).getStore_cart_ids());

            item.setStore_id(payinfo.getOrderArrays().get(i).getStoreId());
            if (payinfo.getOrderArrays().get(i).getDeliverid() != -1) {
                item.setType_id(payinfo.getOrderArrays().get(i).getDeliverid());
            }
            if (payinfo.getOrderArrays().get(i).getYouhuiid() != -1) {
                item.setMoneyId(payinfo.getOrderArrays().get(i).getDeliverid());
            }
            if (payinfo.getOrderArrays().get(i).getDaijinid() != null) {
                item.setVoucherids(payinfo.getOrderArrays().get(i).getDaijinid());
            }
            item.setLeave_message(payinfo.getOrderArrays().get(i).getSpec());
            price += payinfo.getOrderArrays().get(i).getNeedPay();
            orderItems.add(item);
        }
        tvfee.setText("￥" + price);
        payTypes = payinfo.getPays();
        addid = payinfo.getAddr_id();
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
    }

    private void initAdapter() {
        adapter = new PayTypeAdapter(PayActivity.this, payTypes, R.layout.item_payment_other);
        payPost.setAdapter(adapter);
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
                    sendBus();
                    freshBus();
                    intent();
                } else if (msg.getType() == 46) {//支付宝支付失败
                    showMsg("支付失败");
                    sendBus();
                    freshBus();
                    intent();
                } else if (msg.getType() == 47) {//微信支付成功
                    showMsg("支付成功");
                    sendBus();
                    freshBus();
                    intent();
                } else if (msg.getType() == 48) {//微信支付失败
                    showMsg("支付失败");
                    sendBus();
                    freshBus();
                    intent();
                } else if (msg.getType() == 49) {
                    showMsg("微信支付取消");
                    intent();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void sendBus() {
        if (goFrom.equals("OrderActivity")) {
            RxBusMsg msg = new RxBusMsg();
            msg.setType(57);
            RxBus.get().post("rxBusMsg", msg);
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PayPresenter();
        return presenter;
    }

    private void intent() {
        Intent intent = new Intent(this, OrderCenterActivity.class);
        startActivity(intent);
        finish();
    }

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

    @OnClick(R.id.iv_back)
    void back(){
        finish();
    }
    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void fail(int code, String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), msg);
    }

    @OnClick(R.id.btn_pay)
    void pay() {
        if (selectPay == -1) {
            ToastTools.showShort(this, "请选择支付方式");
            return;
        } else {
            String payid = String.valueOf(payTypes.get(selectPay).getPayId());
            HashMap<String, RequestBody> params = new HashMap<>();
            params.put("list", OkhttpUtils.toRequestBody(new Gson().toJson(orderItems)));
            params.put("token", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getString("token")));
            params.put("member_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getInt("member_id") + ""));
            params.put("payment_id", OkhttpUtils.toRequestBody(payid));
            params.put("addr_id", OkhttpUtils.toRequestBody(String.valueOf(addid)));
            presenter.getPayReturn(params);
        }
    }

    private void freshBus() {
        if (goFrom.equalsIgnoreCase("MyCustomFragment")) {
            RxBusMsg msg = new RxBusMsg();
            msg.setType(50);
            RxBus.get().post("rxBusMsg", msg);
        }
    }

    private void showMsg(String msg) {
        if (!goFrom.equals("MyCustomFragment")) {
            if (TextStringUtils.isEmpty(msg) == false) {
                ToastTools.showShort(this, msg);
            }
            if (goFrom.equals("OrderActivity")) {
                finish();
            }
        }
    }

    @Override
    public void payByWeChat(Map<String, String> map) {
        hideLoading();
        new WeChatPayForUtil(PayActivity.this, map);
    }

    @Override
    public void payByAlipay(String reStr) {
        hideLoading();
        LhjalipayUtils.getInstance(PayActivity.this).pay(reStr);
    }

    @Override
    public void payByUnion(String tn, String mode) {
        hideLoading();
        UPPayAssistEx.startPay(this, null, null, tn, mode);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
