package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.Address;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.widgets.dialogs.SelectPlaceDialog;
import com.colpencil.redwood.present.mine.AddAddressPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.impl.IAddAdressView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;

/**
 * 描述：增加新地址/修改地址操作
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/2 14 28
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_addadress
)
public class AddAdressActivity extends ColpencilActivity implements View.OnClickListener, IAddAdressView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.add_newAddress)
    TextView add_newAddress;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_sumbitAdd)
    TextView tv_sumbitAdd;
    @Bind(R.id.et_detailAddress)
    EditText et_detailAddress;
    @Bind(R.id.et_detailPeople)
    EditText et_detailPeople;
    @Bind(R.id.et_detailPhone)
    EditText et_detailPhone;
    @Bind(R.id.et_detailCode)
    EditText et_detailCode;
    @Bind(R.id.iv_def_addr)
    ImageView iv_def_addr;
    private boolean isSelectDef;
    private AddAddressPresenter presenter;
    private AddresBean bean;
    private int touch = 0;

    @Override
    protected void initViews(View view) {
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        presenter.loadRegion(0);
        tv_main_title.setText(getIntent().getStringExtra("key"));
        if (getIntent().getStringExtra("key").equals("修改收货地址")) {
            showLoading(Constants.progressName);
            String addressMsg = "";
            if (!TextUtils.isEmpty(getIntent().getStringExtra("province"))) {
                addressMsg = getIntent().getStringExtra("province");
                SharedPreferencesUtil.getInstance(App.getInstance()).setString("province", getIntent().getStringExtra("province"));
            } else {
                SharedPreferencesUtil.getInstance(App.getInstance()).setString("province", "");
            }
            if (!TextUtils.isEmpty(getIntent().getStringExtra("city"))) {
                addressMsg = addressMsg + getIntent().getStringExtra("city");
                SharedPreferencesUtil.getInstance(App.getInstance()).setString("city", getIntent().getStringExtra("city"));
            } else {
                SharedPreferencesUtil.getInstance(App.getInstance()).setString("city", "");
            }
            if (!TextUtils.isEmpty(getIntent().getStringExtra("region"))) {
                addressMsg = addressMsg + getIntent().getStringExtra("region");
                SharedPreferencesUtil.getInstance(App.getInstance()).setString("region", getIntent().getStringExtra("region"));
            } else {
                SharedPreferencesUtil.getInstance(App.getInstance()).setString("region", "");
            }
            add_newAddress.setText(addressMsg);
            et_detailAddress.setText(getIntent().getStringExtra("address"));
            et_detailPeople.setText(getIntent().getStringExtra("name"));
            et_detailPhone.setText(getIntent().getStringExtra("mobile"));
            et_detailCode.setText(getIntent().getStringExtra("zip"));
            if (getIntent().getIntExtra("def_addr", 0) == 1) {
                isSelectDef = true;
                iv_def_addr.setImageResource(R.mipmap.select_yes_red);
            }
            hideLoading();
        }
        iv_def_addr.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        add_newAddress.setOnClickListener(this);
        tv_sumbitAdd.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new AddAddressPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.add_newAddress://省市区选择
                if (bean != null && bean.getData().size() != 0) {
                    new SelectPlaceDialog(this, add_newAddress, bean,4).show();
                } else {
                    touch = 1;
                    showLoading("加载中");
                }
                break;
            case R.id.tv_sumbitAdd://新增地址
                judge();
                break;
            case R.id.iv_def_addr:
                if (isSelectDef == false) {
                    isSelectDef = true;
                    iv_def_addr.setImageResource(R.mipmap.select_yes_red);
                } else {
                    isSelectDef = false;
                    iv_def_addr.setImageResource(R.mipmap.select_no);
                }
                break;
        }
    }

    @Override
    public void load(AddresBean result) {
        hideLoading();
        bean = result;
        if (touch == 1 ) {
            new SelectPlaceDialog(this, add_newAddress, result,4).show();
        }
    }

    @Override
    public void applyError(String messagge) {
        hideLoading();
        Toast.makeText(this,messagge,Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断输入的内容是否符合要求
     */
    private void judge() {
        if (TextStringUtils.isEmpty(add_newAddress.getText().toString()) == true) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "请选择省市区");
        } else if (TextStringUtils.isEmpty(et_detailCode.getText().toString())) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "请输入邮政编码");
        } else if (TextStringUtils.isEmpty(et_detailAddress.getText().toString()) == true) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "请填写具体地址");
        } else if (TextStringUtils.isEmpty(et_detailPeople.getText().toString()) == true) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "请输入收货人姓名");
        } else if (TextStringUtils.isEmpty(et_detailPhone.getText().toString()) == true) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "请输入手机号码");
        } else if (TextStringUtils.isMobileNO(et_detailPhone.getText().toString()) == false) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "手机号码输入有误");
        } else {//提交数据
            showLoading(Constants.progressName);
            Address address = new Address();
            if (!getIntent().getStringExtra("key").equals("新增收货地址")) {
                address.setAddrId(getIntent().getIntExtra("addressId", 0));
            }
            address.setProvince(SharedPreferencesUtil.getInstance(this).getString("province"));
            address.setCity(SharedPreferencesUtil.getInstance(this).getString("city"));
            address.setRegion(SharedPreferencesUtil.getInstance(this).getString("region"));
            address.setAddress(et_detailAddress.getText().toString());
            address.setName(et_detailPeople.getText().toString());
            address.setMobile(et_detailPhone.getText().toString());
            address.setZip(et_detailCode.getText().toString());
            if (isSelectDef == true) {
                address.setDef_addr(1);
            } else {
                address.setDef_addr(0);
            }
            if (address.getAddrId() == 0) {
                presenter.addAddress(address);
            } else {
                presenter.updateAddress(address);
            }
        }
    }

    @Override
    public void result(EntityResult<String> result) {
        hideLoading();
        ToastTools.showShort(this, result.getMsg());
        if (result.getCode() == 3) {//未登录
            Intent intent = new Intent(AddAdressActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (result.getCode() == 1) {
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(34);
            RxBus.get().post("rxBusMsg", rxBusMsg);
            finish();
        }
    }
}
