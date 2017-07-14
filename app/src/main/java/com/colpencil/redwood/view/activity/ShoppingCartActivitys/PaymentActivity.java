package com.colpencil.redwood.view.activity.ShoppingCartActivitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.Address;
import com.colpencil.redwood.bean.GoodOfOrder;
import com.colpencil.redwood.bean.KindOfPrice;
import com.colpencil.redwood.bean.MemberCoupon;
import com.colpencil.redwood.bean.PayForReturn;
import com.colpencil.redwood.bean.PayType;
import com.colpencil.redwood.bean.Postages;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.MemberCouponResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.Pay.LhjalipayUtils;
import com.colpencil.redwood.function.utils.Pay.Wechat.WeChatPayForUtil;
import com.colpencil.redwood.function.widgets.dialogs.CouponDialog;
import com.colpencil.redwood.function.widgets.dialogs.VoucherDialog;
import com.colpencil.redwood.present.mine.PaymentPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.OrderCenterActivity;
import com.colpencil.redwood.view.activity.mine.ReceiptAddressActivtiy;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.adapters.PayTypeAdapter;
import com.colpencil.redwood.view.adapters.PaymentGoodsAdapter;
import com.colpencil.redwood.view.adapters.PostagesAdapter;
import com.colpencil.redwood.view.impl.IPaymentView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.FormatUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseListView;
import com.unionpay.UPPayAssistEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：支付界面
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 16 16
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_payment
)
public class PaymentActivity extends ColpencilActivity implements IPaymentView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.payment_listView)
    BaseListView listView;
    @Bind(R.id.shoppingCart_buyCount)
    TextView tv_buyCount;
    @Bind(R.id.shoppingCart_buyPrice)
    TextView bt_buyPrice;

    private BodyHolder holder;
    private String mTypeFlag;
    private String cartIds = "";
    private PaymentPresenter presenter;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    //邮递方式
    private List<Postages> postagesList = new ArrayList<>();
    //支付方式
    private List<PayType> payTypeList = new ArrayList<>();
    //订单商品
    private List<GoodOfOrder> mGoodInfor = new ArrayList<>();
    //优惠券
    private List<MemberCoupon> couponList = new ArrayList<>();
    //代金券
    private List<MemberCoupon> voucherList = new ArrayList<>();
    private PostagesAdapter postagesAdapter;
    private PayTypeAdapter payTypeAdapter;
    private int selectPostages = -1;
    private int selectPay = -1;
    private Address mAddress;
    private String goFrom;
    private int voucherCount;   //代金券可选的数量
    private String moneyId;     //代金券id
    private String voucherids;
    private float couponsPrice;
    private float voucherPrice;
    private float postagePrice;
    private KindOfPrice mKind;

    @Override
    protected void initViews(View view) {
        initParams();
        initContent();
        initBody();
        initBus();
        loadData();
    }

    private void initParams() {
        mTypeFlag = getIntent().getStringExtra("key");
        goFrom = getIntent().getStringExtra("goFrom");
    }

    private void initContent() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_main_title.setText(mTypeFlag);
    }

    private void initBody() {
        View view = View.inflate(PaymentActivity.this, R.layout.activity_payment_body, null);
        holder = new BodyHolder(view);
        listView.addHeaderView(view);
        listView.setAdapter(new NullAdapter(this, new ArrayList<String>(), R.layout.item_null));
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
                if (msg.getType() == 41) {//更新选中的邮寄方式
                    selectPostages = msg.getPosition();
                    for (int i = 0; i < postagesList.size(); i++) {
                        if (i == msg.getPosition()) {
                            postagesList.get(i).setChooseState(true);
                            postagePrice = postagesList.get(i).getPostagePrice();
                        } else {
                            postagesList.get(i).setChooseState(false);
                        }
                    }
                    postagesAdapter.notifyDataSetChanged();
                    bt_buyPrice.setText("¥" + FormatUtils.formatDouble(mKind.getGoodsPrice() + postagePrice - couponsPrice - voucherPrice));
                } else if (msg.getType() == 42) {//更新选中的支付方式
                    selectPay = msg.getPosition();
                    for (int i = 0; i < payTypeList.size(); i++) {
                        if (i == msg.getPosition()) {
                            payTypeList.get(i).setChooseState(true);
                        } else {
                            payTypeList.get(i).setChooseState(false);
                        }
                    }
                    payTypeAdapter.notifyDataSetChanged();
                } else if (msg.getType() == 44) {//地址选择
                    holder.rl_haveAdress.setVisibility(View.VISIBLE);
                    holder.rl_noAdress.setVisibility(View.GONE);
                    mAddress = new Gson().fromJson(msg.getMsg(), new TypeToken<Address>() {
                    }.getType());
                    holder.tv_recipient.setText(mAddress.getName());
                    holder.tv_recipient_phone.setText(mAddress.getMobile());
                    holder.tv_receiptent_address.setText(mAddress.getAddress());
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

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PaymentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    private void sendBus() {
        if (goFrom.equals("ShoppingCartActivity")) {
            RxBusMsg msg = new RxBusMsg();
            msg.setType(57);
            RxBus.get().post("rxBusMsg", msg);
        }
    }

    @Override
    public void fail(int code, String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), msg);
        if (code == 3) {//未登录
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
        }
    }

    @Override
    public void payByWeChat(Map<String, String> map) {
        hideLoading();
        new WeChatPayForUtil(PaymentActivity.this, map);
    }

    @Override
    public void payByAlipay(String reStr) {
        hideLoading();
        LhjalipayUtils.getInstance(PaymentActivity.this).pay(reStr);
    }

    @Override
    public void payByUnion(String tn, String mode) {
        hideLoading();
        UPPayAssistEx.startPay(this, null, null, tn, mode);
    }

    @Override
    public void loadOrdersInfo(PayForReturn result) {
        hideLoading();
        if (result.getCode() == 1) {
            cartIds = result.getData().getCart_ids();
            presenter.loadCoupon(cartIds);
            loadGoodInfor(result.getData().getGoodsItem(), result.getData().getOrderPrice(), result.getData().getAddress());
            loadPostInfor(result.getData().getPostages());
            loadPayTypeInfor(result.getData().getPays());
        } else {
            fail(result.getCode(), result.getMsg());
        }
    }

    @Override
    public void loadCouponResult(MemberCouponResult result) {
        if (result.getCode() == 0) {
            couponList.addAll(result.getResult().getCashList());
            voucherList.addAll(result.getResult().getVoucherList());
            voucherCount = result.getResult().getVoucherCount();
        }
    }

    private void showMsg(String msg) {
        if (!goFrom.equals("MyCustomFragment")) {
            if (TextStringUtils.isEmpty(msg) == false) {
                ToastTools.showShort(this, msg);
            }
            if (goFrom.equals("ShoppingCartActivity")) {
                finish();
            }
        }
    }

    public void loadGoodInfor(List<GoodOfOrder> data, KindOfPrice kindOfPrice, Address address) {
        mGoodInfor.addAll(data);
        mKind = kindOfPrice;
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            count = count + data.get(i).getNum();
        }
        tv_buyCount.setText(count + "");
        bt_buyPrice.setText("¥" + kindOfPrice.getGoodsPrice());
        holder.tv_allGood_price.setText("+" + kindOfPrice.getOriginalPrice());
        holder.tv_discount.setText("-" + kindOfPrice.getDiscountPrice());
        holder.good_listView.setAdapter(new PaymentGoodsAdapter(PaymentActivity.this, data, R.layout.item_paygoodsinfor));
        if (address != null && address.getAddrId() != 0) {
            holder.rl_haveAdress.setVisibility(View.VISIBLE);
            holder.rl_noAdress.setVisibility(View.GONE);
            mAddress = address;
            holder.tv_recipient.setText(mAddress.getName());
            holder.tv_recipient_phone.setText(mAddress.getMobile());
            holder.tv_receiptent_address.setText(mAddress.getProvince() + "-"
                    + mAddress.getCity() + "-"
                    + mAddress.getRegion() + "-"
                    + mAddress.getAddress());
        } else {
            holder.rl_haveAdress.setVisibility(View.GONE);
            holder.rl_noAdress.setVisibility(View.VISIBLE);
        }
    }

    public void loadPostInfor(List<Postages> data) {
        postagesList.addAll(data);
        postagesAdapter = new PostagesAdapter(PaymentActivity.this, postagesList, R.layout.item_payment_other);
        holder.deleiver_listview.setAdapter(postagesAdapter);
    }

    public void loadPayTypeInfor(List<PayType> data) {
        payTypeList.addAll(data);
        payTypeAdapter = new PayTypeAdapter(PaymentActivity.this, data, R.layout.item_payment_other);
        holder.payType_listview.setAdapter(payTypeAdapter);
    }

    private void loadData() {
        showLoading(Constants.progressName);
        if (goFrom.equals("ShoppingCartActivity")) {
            presenter.loadPayFor(getIntent().getStringExtra("cartIds"));
        } else if (goFrom.equals("GoodDetailActivity") || goFrom.equals("CustomActivity")
                || goFrom.equals("MyCustomFragment") || goFrom.equals("MyWeekShootFragment")) {
            HashMap<String, String> map = new HashMap<>();
            map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            map.put("product_id", getIntent().getIntExtra("product_id", 0) + "");
            map.put("goods_id", getIntent().getIntExtra("goods_id", 0) + "");
            if (goFrom.equals("GoodDetailActivity")) {        //普通商品
                map.put("itemtype", 0 + "");//用普通商品:0,周拍:3,私人定制:4,官方定制:5,
                map.put("num", getIntent().getIntExtra("num", 0) + "");
            } else if (goFrom.equals("CustomActivity")) {
                map.put("itemtype", 5 + "");//用普通商品:0,周拍:3,私人定制:4,官方定制:5,
                map.put("num", 1 + "");
            } else if (goFrom.equals("MyWeekShootFragment")) {
                map.put("itemtype", 3 + "");//用普通商品:0,周拍:3,私人定制:4,官方定制:5,
                map.put("num", 1 + "");
            } else if (goFrom.equals("MyCustomFragment")) {
                map.put("itemtype", 4 + "");//用普通商品:0,周拍:3,私人定制:4,官方定制:5,
                map.put("custom_made_id", getIntent().getIntExtra("cm_id", 0) + "");
                map.put("num", 1 + "");
            } else {
                map.put("itemtype", 4 + "");//用普通商品:0,周拍:3,私人定制:4,官方定制:5,
                map.put("num", 1 + "");
            }
            map.put("addtype", 1 + "");
            presenter.loadOtherPayFor(map);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {       //处理登录结果
            if (resultCode == Constants.REQUEST_LOGIN) {
                loadData();
            } else {
                finish();
            }
        } else {
            if (data == null) {
                return;
            }
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                ToastTools.showShort(this, "支付成功");
                freshBus();
            } else {
                ToastTools.showShort(this, "支付失败");
            }
            intent();
        }
    }

    private void intent() {
        Intent intent = new Intent(this, OrderCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void freshBus() {
        if (goFrom.equalsIgnoreCase("MyCustomFragment")) {
            RxBusMsg msg = new RxBusMsg();
            msg.setType(50);
            RxBus.get().post("rxBusMsg", msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
        holder.unbind();
    }

    @OnClick(R.id.sumbit_pay)
    void submitClick() {
        sumbitApply();
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    private void sumbitApply() {
        if (mAddress == null) {//未填写收货地址
            ToastTools.showShort(this, "请选择收货地址");
            return;
        } else if (selectPostages == -1) {
            ToastTools.showShort(this, "请选择邮寄方式");
            return;
        } else if (selectPay == -1) {
            ToastTools.showShort(this, "请选择支付方式");
            return;
        } else {//提交支付申请
            showLoading(Constants.progressName);
            HashMap<String, String> map = new HashMap<>();
            map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            map.put("addr_id", mAddress.getAddrId() + "");
            if (!TextUtils.isEmpty(moneyId)) {
                map.put("moneyId", moneyId);
            }
            if (!TextUtils.isEmpty(voucherids)) {
                map.put("voucherids", voucherids);
            }
            map.put("type_id", postagesList.get(selectPostages).getPostageId());
            map.put("payment_id", payTypeList.get(selectPay).getPayId());
            map.put("goodsInfor", new Gson().toJson(mGoodInfor));
            if (TextStringUtils.isEmpty(getIntent().getStringExtra("cartIds"))) {
                map.put("cart_ids", cartIds);
            } else {
                map.put("cart_ids", getIntent().getStringExtra("cartIds"));
            }
            presenter.sumbitPayFor(map);
        }
    }

    private void selectAddress() {
        Intent intent = new Intent(PaymentActivity.this, ReceiptAddressActivtiy.class);
        intent.putExtra("key", "PaymentActivity");
        startActivity(intent);
    }

    class BodyHolder {

        @Bind(R.id.pay_goods)
        MosaicListView good_listView;
        @Bind(R.id.pay_post)
        MosaicListView deleiver_listview;
        @Bind(R.id.pay_type)
        MosaicListView payType_listview;
        @Bind(R.id.tv_GoodOriginalPrice)
        TextView tv_allGood_price;
        @Bind(R.id.tv_discountPrice)
        TextView tv_discount;
        @Bind(R.id.ll_haveAdress)
        RelativeLayout rl_haveAdress;
        @Bind(R.id.ll_noAdress)
        RelativeLayout rl_noAdress;
        @Bind(R.id.tv_receipt_name)
        TextView tv_recipient;
        @Bind(R.id.tv_receipt_phone)
        TextView tv_recipient_phone;
        @Bind(R.id.tv_receipt_context)
        TextView tv_receiptent_address;
        @Bind(R.id.tv_useCoupons)
        TextView tv_useCoupons;
        @Bind(R.id.tv_vouchers)
        TextView tv_vouchers;

        public BodyHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }

        @OnClick(R.id.relativeLayout_selectCoupons)
        void couponsClick() {
            final CouponDialog dialog = new CouponDialog(PaymentActivity.this);
            dialog.setListener(new CouponDialog.VoucherClickListener() {
                @Override
                public void sureClick() {
                    moneyId = "";
                    couponsPrice = 0.0f;
                    for (int i = 0; i < couponList.size(); i++) {
                        if (couponList.get(i).isChoose()) {
                            moneyId = couponList.get(i).getId() + "";
                            couponsPrice += couponList.get(i).getDiscount_price();
                        }
                    }
                    if (couponsPrice != 0.0f) {
                        holder.tv_useCoupons.setText(FormatUtils.formatDouble(couponsPrice) + "元");
                    } else {
                        holder.tv_useCoupons.setText("");
                    }
                    bt_buyPrice.setText("¥" + FormatUtils.formatDouble(mKind.getGoodsPrice() + postagePrice - couponsPrice - voucherPrice));
                    dialog.dismiss();
                }

                @Override
                public void cancleClick() {
                    moneyId = "";
                    voucherPrice = 0.0f;
                    for (int i = 0; i < couponList.size(); i++) {
                        if (couponList.get(i).isChoose()) {
                            moneyId = couponList.get(i).getId() + ",";
                            voucherPrice += couponList.get(i).getDiscount_price();
                        }
                    }
                    if (voucherPrice != 0.0f) {
                        holder.tv_useCoupons.setText(FormatUtils.formatDouble(voucherPrice) + "元");
                    } else {
                        holder.tv_useCoupons.setText("请选择优惠券");
                    }
                    bt_buyPrice.setText("¥" + FormatUtils.formatDouble(mKind.getGoodsPrice() + postagePrice - couponsPrice - voucherPrice));
                    dialog.dismiss();
                }
            });
            dialog.setData(couponList);
            dialog.show();
        }

        @OnClick(R.id.relativeLayout_vouchers)
        void vouchersClick() {
            final VoucherDialog dialog = new VoucherDialog(PaymentActivity.this);
            dialog.setListener(new VoucherDialog.VoucherClickListener() {
                @Override
                public void sureClick() {
                    voucherids = "";
                    voucherPrice = 0.0f;
                    for (int i = 0; i < voucherList.size(); i++) {
                        if (voucherList.get(i).isChoose()) {
                            voucherids = voucherList.get(i).getId() + ",";
                            voucherPrice += voucherList.get(i).getDiscount_price();
                        }
                    }
                    if (voucherPrice != 0.0f) {
                        holder.tv_vouchers.setText(FormatUtils.formatDouble(voucherPrice) + "元");
                    } else {
                        holder.tv_vouchers.setText("请选择代金券");
                    }
                    bt_buyPrice.setText("¥" + FormatUtils.formatDouble(mKind.getGoodsPrice() + postagePrice - couponsPrice - voucherPrice));
                    dialog.dismiss();
                }

                @Override
                public void cancleClick() {
                    voucherids = "";
                    voucherPrice = 0.0f;
                    for (int i = 0; i < voucherList.size(); i++) {
                        if (voucherList.get(i).isChoose()) {
                            voucherids = voucherList.get(i).getId() + ",";
                            voucherPrice += voucherList.get(i).getDiscount_price();
                        }
                    }
                    if (voucherPrice != 0.0f) {
                        holder.tv_useCoupons.setText(FormatUtils.formatDouble(voucherPrice) + "元");
                    } else {
                        holder.tv_useCoupons.setText("请选择优惠券");
                    }
                    bt_buyPrice.setText("¥" + FormatUtils.formatDouble(mKind.getGoodsPrice() + postagePrice - couponsPrice - voucherPrice));
                    dialog.dismiss();
                }
            });
            dialog.setData(voucherList);
            dialog.setCount(voucherCount);
            dialog.show();
        }

        @OnClick(R.id.ll_noAdress)
        void noAddressClick() {
            selectAddress();
        }

        @OnClick(R.id.ll_haveAdress)
        void hasAddressClick() {
            selectAddress();
        }
    }
}
