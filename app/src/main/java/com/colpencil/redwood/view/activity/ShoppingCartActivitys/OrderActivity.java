package com.colpencil.redwood.view.activity.ShoppingCartActivitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.Address;
import com.colpencil.redwood.bean.OrderDtail;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.AllCartList;
import com.colpencil.redwood.holder.home.OrderViewHolder;
import com.colpencil.redwood.present.mine.OrderPresenter;
import com.colpencil.redwood.view.activity.mine.ReceiptAddressActivtiy;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.impl.IOrderView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

@ActivityFragmentInject(contentViewId = R.layout.activity_order)
public class OrderActivity extends ColpencilActivity implements IOrderView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.payment_listView)
    BaseListView listView;
    @Bind(R.id.shoppingCart_buyCount)
    TextView tv_buyCount;
    @Bind(R.id.shoppingCart_buyPrice)
    TextView bt_buyPrice;
    private AddressHolder addressHolder;
    private OrderPresenter presenter;
    private String mTypeFlag;
    private String goFrom;
    private String cart_ids;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private Address mAddress;
    private OrderViewHolder orderView;
    @Override
    protected void initViews(View view) {
        initParams();
        initHolder();
        initBus();
    }

    private void initParams() {
        mTypeFlag = getIntent().getStringExtra("key");
        goFrom = getIntent().getStringExtra("goFrom");
        cart_ids = getIntent().getStringExtra("cartIds");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_main_title.setText(mTypeFlag);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("cart_ids", cart_ids);

        presenter.getOrderPay(params);
    }

    private void initHolder() {
        View view = View.inflate(OrderActivity.this, R.layout.address_view_holder, null);
        addressHolder = new OrderActivity.AddressHolder(view);
        orderView = new OrderViewHolder(0,this);
        listView.addHeaderView(view);
        listView.addHeaderView(orderView.getContentView());
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
                if (msg.getType() == 44) {//地址选择
                    addressHolder.rl_haveAdress.setVisibility(View.VISIBLE);
                    addressHolder.rl_noAdress.setVisibility(View.GONE);
                    mAddress = new Gson().fromJson(msg.getMsg(), new TypeToken<Address>() {
                    }.getType());
                    addressHolder.tv_recipient.setText(mAddress.getName());
                    addressHolder.tv_recipient_phone.setText(mAddress.getMobile());
                    addressHolder.tv_receiptent_address.setText(mAddress.getAddress());
                }
            }
        };
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new OrderPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadNewOrder(OrderDtail result) {

        if (result.getAddress() != null && result.getAddress().getAddrId() != 0) {
            addressHolder.rl_haveAdress.setVisibility(View.VISIBLE);
            addressHolder.rl_noAdress.setVisibility(View.GONE);
             mAddress = result.getAddress();
            addressHolder.tv_recipient.setText(result.getAddress().getName());
            addressHolder.tv_recipient_phone.setText(result.getAddress().getMobile());
            addressHolder.tv_receiptent_address.setText(result.getAddress().getProvince() + "-"
                    + result.getAddress().getCity() + "-"
                    + result.getAddress().getRegion() + "-"
                    + result.getAddress().getAddress());
        } else {
            addressHolder.rl_haveAdress.setVisibility(View.GONE);
            addressHolder.rl_noAdress.setVisibility(View.VISIBLE);
        }
        orderView.setData(result.getOrderArray());
    }

    @Override
    public void loadError(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadNewCartData(AllCartList data) {

    }

    class AddressHolder {
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

        public AddressHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void unbind() {
            ButterKnife.unbind(this);
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

    private void selectAddress() {
        Intent intent = new Intent(OrderActivity.this, ReceiptAddressActivtiy.class);
        intent.putExtra("key", "PaymentActivity");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        RxBus.get().unregister("rxBusMsg", observable);
        addressHolder.unbind();
    }
}
