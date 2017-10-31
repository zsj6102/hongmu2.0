package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.Info.StoreInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.widgets.dialogs.ModifyPlaceDialog;
import com.colpencil.redwood.present.mine.ModifyStorePresent;
import com.colpencil.redwood.view.impl.IModifyStoreView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

import static com.colpencil.redwood.R.id.et_detailPhone;

@ActivityFragmentInject(contentViewId = R.layout.modify_address_layout)
public class ModifyAddressActivity extends ColpencilActivity implements IModifyStoreView {


    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.et_detailAddress)
    EditText etDetailAddress;
    @Bind(R.id.et_detailPeople)
    EditText etDetailPeople;
    @Bind(R.id.layout_people)
    LinearLayout layoutPeople;
    @Bind(et_detailPhone)
    EditText etDetailPhone;
    @Bind(R.id.layout_phone)
    LinearLayout layoutPhone;
    @Bind(R.id.add_newAddress)
    TextView addNewAddress;
    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.line2)
    View line2;
    private AddresBean bean;

    private int touch = 0;
    private ModifyStorePresent present;
    private ModifyPlaceDialog dialog;
    private int addressId = -1;
    private String type;

    private String address;
    private String address_detail;
    private String return_man;
    private String return_phone;
    private int id;

    @Override
    protected void initViews(View view) {
        present.loadRegion(0);
        type = getIntent().getStringExtra("type");
        address = getIntent().getStringExtra("address");
        address_detail = getIntent().getStringExtra("address_detail");
        id = getIntent().getExtras().getInt("id");
        return_man = getIntent().getStringExtra("return_man");
        return_phone = getIntent().getStringExtra("return_phone");
        if (address != null) {
            addNewAddress.setText(address);
            addressId = id;
        }
        if (address_detail != null) {
            etDetailAddress.setText(address_detail);
        }
        if(return_man!=null){
            etDetailPeople.setText(return_man);
        }
        if(return_phone!=null){
            etDetailPhone.setText(return_phone);
        }
        if (type.equals("store")) {
            tvMainTitle.setText("店铺地址");
        } else {
            tvMainTitle.setText("退货地址");
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            layoutPeople.setVisibility(View.VISIBLE);
            layoutPhone.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.add_newAddress)
    void click() {
        if (bean != null && bean.getData().size() != 0) {
            placeDialog();
        } else {
            touch = 1;
            showLoading("加载中");
        }
    }

    @Override
    public void loadRegion(AddresBean regionInfo) {
        hideLoading();
        bean = regionInfo;
        if (touch == 1) {
            placeDialog();
        }
    }

    @Override
    public void modifyResult(ResultInfo<StoreInfo> info) {
        hideLoading();

        RxBusMsg msg = new RxBusMsg();
        if (type.equals("store")) {
            msg.setType(70);
        } else {
            msg.setType(71);
        }
        RxBus.get().post("rxBusMsg", msg);
        finish();
    }

    private void placeDialog() {
        if (dialog == null) {
            dialog = new ModifyPlaceDialog(this, bean);
        }
        dialog.setListener(new ModifyPlaceDialog.btnClick() {
            @Override
            public void sureClick(String s, int id) {
                addNewAddress.setText(s);
                addressId = id;
                dialog.dismiss();
            }

            @Override
            public void cancelClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.tv_sumbitAdd)
    void submit() {
        if (addressId == -1) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "请选择省市区");
            return;
        }
        if (type.equals("back")) {
            if (TextUtils.isEmpty(etDetailPeople.getText().toString())) {
                ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "请输入收货人姓名");
                return;
            }
            if (TextUtils.isEmpty(etDetailPhone.getText().toString())) {
                ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "请输入手机号码");
                return;
            }
            if (TextStringUtils.isMobileNO(etDetailPhone.getText().toString()) == false) {
                ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "手机号码输入有误");
                return;
            }
        }
        showLoading("保存中");
        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("token", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getString("token")));
        params.put("member_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getInt("member_id") + ""));
        if (type.equals("store")) {
            params.put("attr", OkhttpUtils.toRequestBody(etDetailAddress.getText().toString()));
            params.put("region_id", OkhttpUtils.toRequestBody(addressId + ""));
            params.put("upd_type", OkhttpUtils.toRequestBody("3"));
            present.getModifyStatus(params);
        } else {
            params.put("return_attr", OkhttpUtils.toRequestBody(etDetailAddress.getText().toString()));
            params.put("return_mobile", OkhttpUtils.toRequestBody(etDetailPhone.getText().toString()));
            params.put("return_man", OkhttpUtils.toRequestBody(etDetailPeople.getText().toString()));
            params.put("return_region", OkhttpUtils.toRequestBody(addressId + ""));
            params.put("upd_type", OkhttpUtils.toRequestBody("4"));
            present.getModifyStatus(params);
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ModifyStorePresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadFail(String msg) {

    }

    @Override
    public void loadStoreInfo(ResultInfo<StoreInfo> info) {

    }

    @Override
    public void loadCat(CatListBean catListBean) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
