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
import com.colpencil.redwood.bean.IntenToPay;
import com.colpencil.redwood.bean.OrderDtail;
import com.colpencil.redwood.bean.PayType;
import com.colpencil.redwood.bean.Postages;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.AllCartList;
import com.colpencil.redwood.bean.result.MemberCoupon;
import com.colpencil.redwood.bean.result.OrderArray;
import com.colpencil.redwood.function.widgets.dialogs.CouponDialog;
import com.colpencil.redwood.function.widgets.dialogs.SelectPostStyleDialog;
import com.colpencil.redwood.function.widgets.dialogs.VoucherDialog;
import com.colpencil.redwood.holder.adapter.OrderItemAdapter;
import com.colpencil.redwood.present.mine.OrderPresenter;
import com.colpencil.redwood.view.activity.mine.ReceiptAddressActivtiy;
import com.colpencil.redwood.view.impl.IOrderView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

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
 * 订单界面
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_order)
public class OrderActivity extends ColpencilActivity implements IOrderView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.order_listView)
    ListView listView;
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
    private List<OrderArray> mdata = new ArrayList<>();
    private OrderItemAdapter adapter;
    private List<PayType> pays = new ArrayList<>();
    Map<String, String> params = new HashMap<>();
    private  int goodsid;
    private int productid;
    private int num;
    @Override
    protected void initViews(View view) {
        initParams();
        initHolder();
        initBus();
    }

    private void initParams() {
        mTypeFlag = getIntent().getStringExtra("key");
        tv_main_title.setText(mTypeFlag);
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        goFrom = getIntent().getStringExtra("goFrom");
        if(goFrom.equals("ShoppingCartActivity")){//转订单购买
            cart_ids = getIntent().getStringExtra("cartIds");
            params.put("cart_ids", cart_ids);
            presenter.getOrderPay(params);
        }else{//直接购买
            goodsid = getIntent().getExtras().getInt("goods_id");
            productid = getIntent().getExtras().getInt("product_id");
            num = getIntent().getExtras().getInt("num");
            params.put("goods_id", goodsid+"");
            params.put("product_id",productid+"");
            params.put("num",num+"");
            presenter.getDirectOrder(params);
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }


    }

    private void initHolder() {
        View view = View.inflate(OrderActivity.this, R.layout.address_view_holder, null);
        addressHolder = new OrderActivity.AddressHolder(view);
        listView.addHeaderView(view);
        adapter = new OrderItemAdapter(this, mdata);
        initAdapter();
        listView.setAdapter(adapter);
    }

    public void saveEditData(int position, String str) {
        int m = 0;
        OrderArray orderArray;
        for (int k = 0; k < mdata.size(); k++) {
            if ((mdata.get(k).getGoodsItem().contains(adapter.getItem(position)))) {
                m = k;
            }
        }
        orderArray = mdata.get(m);
        orderArray.setSpec(str);
    }

    private void initAdapter() {


        adapter.setListener(new OrderItemAdapter.MyListener() {
            @Override
            public void deliverclick(int position) {
                final List<Postages> couponList = new ArrayList<>();
                final OrderArray orderArray;
                int m = 0;
                for (int k = 0; k < mdata.size(); k++) {
                    if ((mdata.get(k).getGoodsItem().contains(adapter.getItem(position)))) {
                        couponList.clear();
                        couponList.addAll(mdata.get(k).getPostages());
                        m = k;
                    }
                }
                orderArray = mdata.get(m);
                final SelectPostStyleDialog dialog = new SelectPostStyleDialog(OrderActivity.this, couponList);
                dialog.setListener(new SelectPostStyleDialog.PostClickListener() {
                    @Override
                    public void closeClick() {
                        int size = couponList.size();
                        for (int i = 0; i < couponList.size(); i++) {
                            if (couponList.get(i).isChooseState()) {
                                orderArray.setDeliver(couponList.get(i).getPostageName());
                                orderArray.setDeliverPrice(couponList.get(i).getPostagePrice());
                                orderArray.setDeliverid(couponList.get(i).getPostageId());
                                size--;
                                adapter.notifyDataSetChanged();
                            }
                        }
                        if (size == couponList.size()) {
                            orderArray.setDeliverPrice(0.0);
                            orderArray.setDeliver(null);
                            orderArray.setDeliverid(-1);
                            adapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void itemUnClick(int position) {
                        orderArray.setDeliverPrice(0.0);
                        orderArray.setDeliver(null);
                        orderArray.setDeliverid(-1);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void itemClick(int position) {
                        orderArray.setDeliver(couponList.get(position).getPostageName());
                        orderArray.setDeliverPrice(couponList.get(position).getPostagePrice());
                        orderArray.setDeliverid(couponList.get(position).getPostageId());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }

            @Override
            public void yhqClick(int position) {
                final List<MemberCoupon> couponList = new ArrayList<>();
                final OrderArray orderArray;
                int m = 0;
                for (int k = 0; k < mdata.size(); k++) {
                    if ((mdata.get(k).getGoodsItem().contains(adapter.getItem(position)))) {
                        couponList.clear();
                        couponList.addAll(mdata.get(k).getAllCoupon().getCashList());
                        m = k;
                    }
                }
                orderArray = mdata.get(m);
                final CouponDialog dialog = new CouponDialog(OrderActivity.this);
                dialog.setListener(new CouponDialog.VoucherClickListener() {
                    @Override
                    public void sureClick() {
                        int size = couponList.size();
                        for (int i = 0; i < couponList.size(); i++) {
                            if (couponList.get(i).isChoose()) {
                                orderArray.setYouhui(Double.parseDouble(couponList.get(i).getDiscount_price()));
                                orderArray.setYouhuiid(couponList.get(i).getId());
                                adapter.notifyDataSetChanged();
                                size--;
                            }
                        }
                        if (size == couponList.size()) {
                            orderArray.setYouhuiid(-1);
                            orderArray.setYouhui(0);
                            adapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void cancleClick() {
                        int size = couponList.size();
                        for (int i = 0; i < couponList.size(); i++) {
                            if (couponList.get(i).isChoose()) {
                                orderArray.setYouhui(Double.parseDouble(couponList.get(i).getDiscount_price()));
                                orderArray.setYouhuiid(couponList.get(i).getId());
                                adapter.notifyDataSetChanged();
                                size--;
                            }
                        }
                        if (size == couponList.size()) {
                            orderArray.setYouhuiid(-1);
                            orderArray.setYouhui(0);
                            adapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.setData(couponList);
                dialog.show();
            }

            @Override
            public void djqClick(int position) {
                final List<MemberCoupon> couponList = new ArrayList<>();
                final OrderArray orderArray;
                int m = 0;
                final VoucherDialog dialog = new VoucherDialog(OrderActivity.this);
                for (int k = 0; k < mdata.size(); k++) {
                    if ((mdata.get(k).getGoodsItem().contains(adapter.getItem(position)))) {
                        couponList.clear();
                        couponList.addAll(mdata.get(k).getAllCoupon().getVoucherList());
                        dialog.setCount(mdata.get(k).getAllCoupon().getVoucherCount());
                        m = k;
                    }
                }
                orderArray = mdata.get(m);
                dialog.setData(couponList);
                dialog.show();
                dialog.setListener(new VoucherDialog.VoucherClickListener() {
                    @Override
                    public void sureClick() {
                        int size = couponList.size();
                        String voucherids = "";
                        double voucherPrice = 0.0f;
                        for (int i = 0; i < couponList.size(); i++) {
                            if (couponList.get(i).isChoose()) {
                                voucherids = voucherids + couponList.get(i).getId()+",";
                                voucherPrice += Double.parseDouble(couponList.get(i).getDiscount_price());
                                size--;
                            }
                        }
                        if (size == couponList.size()) {
                            orderArray.setDaijinid(null);
                            orderArray.setDaijin(0);
                            adapter.notifyDataSetChanged();
                        }else{
                            orderArray.setDaijinid(voucherids.substring(0, voucherids.length() - 1));
                            orderArray.setDaijin(voucherPrice);
                            adapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void cancleClick() {
                        int size = couponList.size();
                        String voucherids = "";
                        double voucherPrice = 0.0f;
                        for (int i = 0; i < couponList.size(); i++) {
                            if (couponList.get(i).isChoose()) {
                                voucherids = voucherids + couponList.get(i).getId()+",";
                                voucherPrice += Double.parseDouble(couponList.get(i).getDiscount_price());
                                size--;
                            }
                        }
                        if (size == couponList.size()) {
                            orderArray.setDaijinid(null);
                            orderArray.setDaijin(0);
                            adapter.notifyDataSetChanged();
                        }else{
                            orderArray.setDaijinid(voucherids.substring(0, voucherids.length() - 1));
                            orderArray.setDaijin(voucherPrice);
                            adapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

    }
    @OnClick(R.id.sumbit_pay)
    void submit(){
        int m = 0;
        for(int i = 0; i < mdata.size();i++){
            if(mdata.get(i).getDeliverid() == -1){
                m++;
            }
        }
        if (mAddress == null) {//未填写收货地址
            ToastTools.showShort(this, "请选择收货地址");
            return;
        } else if (m == mdata.size()) {
            ToastTools.showShort(this, "请选择配送方式");
            return;
        }else{
            IntenToPay payinfo = new IntenToPay();
            payinfo.setAddr_id(mAddress.getAddrId());
            payinfo.setOrderArrays(mdata);
            payinfo.setPays(pays);
            Intent intent = new Intent(OrderActivity.this,PayActivity.class);
            intent.putExtra("payinfo",payinfo);
            intent.putExtra("goFrom","OrderActivity");
            startActivity(intent);
            finish();
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
                if (msg.getType() == 100) {
                    double price = 0;
                    for (int i = 0; i < mdata.size(); i++) {
                        price = price + mdata.get(i).getNeedPay();

                    }
                    bt_buyPrice.setText("¥" + price + "");
                }
//                if(msg.getType() == 57){
//                    presenter.getOrderPay(params);
//                }
            }
        };
        observable.subscribe(subscriber);
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
            addressHolder.tv_receiptent_address.setText(result.getAddress().getProvince() + "-" + result.getAddress().getCity() + "-" + result.getAddress().getRegion() + "-" + result.getAddress().getAddress());
        } else {
            addressHolder.rl_haveAdress.setVisibility(View.GONE);
            addressHolder.rl_noAdress.setVisibility(View.VISIBLE);
        }
        mdata.clear();
        for (int i = 0; i < result.getOrderArray().size(); i++) {
            result.getOrderArray().get(i).setNeedPay(result.getOrderArray().get(i).getOrderPrice().getOriginalPrice());
        }
        mdata.addAll(result.getOrderArray());
        int count = 0;
        double price = 0;
        pays.clear();
        pays.addAll(result.getPays());
        for (int i = 0; i < result.getOrderArray().size(); i++) {
            //            count = count + result.getOrderArray().get(i).getItemCount() - 1;
            for (int m = 0; m < result.getOrderArray().get(i).getGoodsItem().size(); m++) {
                count = count + result.getOrderArray().get(i).getGoodsItem().get(m).getNum();
            }
            price = price + result.getOrderArray().get(i).getNeedPay();

        }
        tv_buyCount.setText(count + "");
        bt_buyPrice.setText("¥" + price + "");
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void loadError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
        intent.putExtra("key", "OrderActivity");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
        addressHolder.unbind();
    }
}