package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Address;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.widgets.swipe.SwipeListView;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenu;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuCreator;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuItem;
import com.colpencil.redwood.present.mine.ReceiptAddressPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.ReceiptAddressAdapter;
import com.colpencil.redwood.view.impl.IReceiptAddressView;
import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DpAndPx;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：收货地址
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/1 17 16
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_receiptaddress
)
public class ReceiptAddressActivtiy extends ColpencilActivity implements IReceiptAddressView, View.OnClickListener {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.lv_receiptaddress)
    SwipeListView lv_receiptaddress;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_shoppingCartFinish;

    private ReceiptAddressAdapter mAdapter;
    private ReceiptAddressPresenter presenter;
    private List<Address> mdatas = new ArrayList<>();
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        initData();
    }

    private void initData() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // TODO Auto-generated method stub
                SwipeMenuItem item1 = new SwipeMenuItem(ReceiptAddressActivtiy.this);
                item1.setBackground(new ColorDrawable(getResources().getColor(R.color.main_red)));
                item1.setWidth(DpAndPx.dip2px(ReceiptAddressActivtiy.this, 50));
                item1.setTitle("删除");
                item1.setTitleColor(Color.WHITE);
                item1.setTitleSize(14);
                menu.addMenuItem(item1);
            }
        };
        lv_receiptaddress.setMenuCreator(creator);
        lv_receiptaddress.setOnMenuItemClickListener(new SwipeListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                showLoading(Constants.progressName);
                presenter.deleteById(mdatas.get(position).getAddrId());
                return false;
            }
        });
        tv_shoppingCartFinish.setText("新增");
        tv_shoppingCartFinish.setOnClickListener(this);


        lv_receiptaddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!getIntent().getStringExtra("key").equals("MeFragment")) {
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(44);
                    Address address = mdatas.get(position);
                    String addressMsg = "";
                    if (TextStringUtils.isEmpty(address.getProvince()) == false) {
                        addressMsg = address.getProvince();
                    }
                    if (TextStringUtils.isEmpty(address.getCity()) == false) {
                        addressMsg = addressMsg + "-" + address.getCity();
                    }
                    if (TextStringUtils.isEmpty(address.getRegion()) == false) {
                        addressMsg = addressMsg + "-" + address.getRegion();
                    }
                    address.setAddress(addressMsg + "-" + address.getAddress());
                    rxBusMsg.setMsg(new Gson().toJson(address));
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                    finish();
                }

            }
        });
        mAdapter = new ReceiptAddressAdapter(ReceiptAddressActivtiy.this, mdatas, R.layout.item_receiptaddress);
        lv_receiptaddress.setAdapter(mAdapter);
        tv_main_title.setText("收货地址");
        iv_back.setOnClickListener(this);
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
                if (msg.getType() == 34) {//刷新列表数据
                    showLoading(Constants.progressName);
                    mdatas.clear();
                    mAdapter.notifyDataSetChanged();
                    presenter.getContent();
                }
            }
        };
        observable.subscribe(subscriber);
        showLoading(Constants.progressName);
        presenter.getContent();
    }


    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ReceiptAddressPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadAddressInfor(List<Address> data) {
        mdatas.addAll(data);
        mAdapter.notifyDataSetChanged();//适配器进行刷新操作
        hideLoading();

    }


    @Override
    public void reslutInfor(String code, String msg) {
        hideLoading();
        if (TextStringUtils.isEmpty(msg) == false) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), msg);
        }
        if (code.equals("3")) {//未登录
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_shoppingCartFinish://新增收货地址
                Intent intent = new Intent(ReceiptAddressActivtiy.this, AddAdressActivity.class);
                intent.putExtra("key", "新增收货地址");
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }
}
