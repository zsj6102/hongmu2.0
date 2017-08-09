package com.colpencil.redwood.view.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.TimeCount;
import com.colpencil.redwood.present.login.PwdPresenter;
import com.colpencil.redwood.view.activity.HomeActivity;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.impl.IPwdView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述：注册
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/22 11 24
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_pwd
)
public class PwdActivity extends ColpencilActivity implements IPwdView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.etPwd_phone)
    EditText etPwd_phone;
    @Bind(R.id.etPwd_code)
    EditText etPwd_code;
    @Bind(R.id.etPwd_setPwd)
    EditText etPwd_setPwd;
    @Bind(R.id.etPwd_surePwd)
    EditText etPwd_surePwd;
    @Bind(R.id.check_protocol)
    CheckBox checkBox;
    @Bind(R.id.tv_getCode)
    TextView tv_getCode;
    @Bind(R.id.iv_removePhonePwd)
    ImageView iv_removePhonePwd;
    @Bind(R.id.tvPwd_setPwd)
    TextView tvPwd_setPwd;

    private PwdPresenter presenter;
    private String typeFlag;//操作标识
    private String openId;//第三方登录openId

    @Override
    protected void initViews(View view) {
        typeFlag = getIntent().getStringExtra("key");
        if (typeFlag.equals("forgetPwd")) {//忘记密码操作
            tv_main_title.setText("忘记密码");
            tvPwd_setPwd.setText("    新密码:");
            etPwd_setPwd.setHint("请输入新的密码");
        } else if (typeFlag.equals("updatePwd")) {//修改密码操作
            tv_main_title.setText("修改密码");
            tvPwd_setPwd.setText("    新密码:");
            etPwd_setPwd.setHint("请输入新的密码");
        } else if (typeFlag.equals("bindPhone")) {//绑定手机号码操作
            tv_main_title.setText("绑定手机号");
            openId = getIntent().getStringExtra("openId");
        }
        etPwd_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextStringUtils.isEmpty(etPwd_phone.getText().toString()) == false) {
                    iv_removePhonePwd.setVisibility(View.VISIBLE);
                } else {
                    iv_removePhonePwd.setVisibility(View.GONE);
                }
            }
        });
        etPwd_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!TextUtils.isEmpty(etPwd_phone.getText().toString())) {
                        iv_removePhonePwd.setVisibility(View.VISIBLE);
                    } else {
                        iv_removePhonePwd.setVisibility(View.GONE);
                    }
                } else {
                    iv_removePhonePwd.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new PwdPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loginResult(LoginBean loginBean) {
        SharedPreferencesUtil.getInstance(App.getInstance()).setString(StringConfig.MOBILEPHONE, etPwd_phone.getText().toString());
        SharedPreferencesUtil.getInstance(App.getInstance()).setString(StringConfig.PASSWORD, etPwd_setPwd.getText().toString());
        SharedPreferencesUtil.getInstance(App.getInstance()).setBoolean(StringConfig.ISLOGIN, true);
        intentToHome(loginBean);
    }

    @Override
    public void codeResult(LoginBean loginBean) {
        hideLoading();
        ToastTools.showShort(this, loginBean.getMsg());
        if (loginBean.getCode() == 1) {
            TimeCount time = new TimeCount(60000, 1000, tv_getCode);
            time.start();//开启倒计时
        }
    }

    @Override
    public void bindResult(LoginBean bean) {
        hideLoading();
        ToastTools.showShort(this, bean.getMsg());
        if (bean.getCode() == 1) {
            presenter.login(etPwd_phone.getText().toString(), Md5Utils.encode(etPwd_setPwd.getText().toString()));
        }
    }

    @Override
    public void changeResult(LoginBean bean) {
        hideLoading();
        ToastTools.showShort(this, bean.getMsg());
        if (bean.getCode() == 1) {
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(5);
            RxBus.get().post("rxBusMsg", rxBusMsg);
            finish();
        }
    }

    @Override
    public void forgetResult(LoginBean bean) {
        hideLoading();
        ToastTools.showShort(this, bean.getMsg());
        if (bean.getCode() == 1) {
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(5);
            RxBus.get().post("rxBusMsg", rxBusMsg);
            finish();
        }
    }

    @Override
    public void registerResult(LoginBean bean) {
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.tv_getCode)
    void getCheckNum() {
        if (tv_getCode.getText().toString().trim().equals("重新验证") || tv_getCode.getText().toString().trim().equals("获取验证码")) {
            if (TextUtils.isEmpty(etPwd_phone.getText().toString())) {
                ToastTools.showShort(this, "手机号码不能为空！");
                return;
            } else if (!TextStringUtils.isMobileNO(etPwd_phone.getText().toString())) {
                ToastTools.showShort(this, "请输入正确的手机号码");
                return;
            } else {
                showLoading(Constants.progressName);
                if (typeFlag.equals("bindPhone")) { //绑定手机号码
                    presenter.bindCode(etPwd_phone.getText().toString());
                } else if (typeFlag.equals("forgetPwd")) {   //忘记密码
                    presenter.forgetCode(etPwd_phone.getText().toString());
                } else {    //修改密码
                    presenter.changeCode(etPwd_phone.getText().toString());
                }
            }
        }
    }

    @OnClick(R.id.tv_seeAgreement)
    void protocal() {
        Intent intent = new Intent(PwdActivity.this, WebViewActivity.class);
        intent.putExtra("key", "PwdActivity");
        startActivity(intent);
    }

    @OnClick(R.id.iv_removePhonePwd)
    void clearEdit() {
        etPwd_phone.setText("");
    }

    @OnClick(R.id.tv_sumbitPwd)
    void submit() {
        String phoneNum = etPwd_phone.getText().toString();
        String checkNum = etPwd_code.getText().toString();
        String password = etPwd_setPwd.getText().toString();
        String surePwd = etPwd_surePwd.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastTools.showShort(this, "手机号码不能为空！");
            return;
        } else if (!TextStringUtils.isMobileNO(phoneNum)) {
            ToastTools.showShort(this, "请输入正确的手机号码");
            return;
        } else if (TextUtils.isEmpty(checkNum)) {
            ToastTools.showShort(this, "验证码不能为空！");
            return;
        } else if (TextUtils.isEmpty(password)) {
            ToastTools.showShort(this, "密码不能为空！");
            return;
        } else if (password.length() < 6 || password.length() > 15) {
            ToastTools.showShort(this, "密码长度必须在6位至15位之间！");
            return;
        } else if (TextUtils.isEmpty(surePwd)) {
            ToastTools.showShort(this, "确认密码不能为空！");
            return;
        } else if (surePwd.length() < 6 || surePwd.length() > 15) {
            ToastTools.showShort(this, "确认密码长度必须在6位至15位之间！");
            return;
        } else if (!password.equals(surePwd)) {
            ToastTools.showShort(this, "两次输入的密码不一致");
            return;
        } else if (!checkBox.isChecked()) {
            ToastTools.showShort(this, "注册之前请先同意《顶藏网服务协议》");
            return;
        } else {
            if (typeFlag.equals("bindPhone")) {
                presenter.binds(phoneNum, checkNum, Md5Utils.encode(password), openId);
            } else if (typeFlag.equals("forgetPwd")) {
                presenter.forget(phoneNum, checkNum, Md5Utils.encode(password));
            } else {
                presenter.change(phoneNum, checkNum, Md5Utils.encode(password));
            }
        }
    }

    /**
     * 跳转到主界面
     */

    private void intentToHome(LoginBean loginBean) {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(63);
        RxBus.get().post("rxBusMsg", rxBusMsg);
        SharedPreferencesUtil.getInstance(App.getInstance()).setInt("member_id", loginBean.getMember_id());
        SharedPreferencesUtil.getInstance(App.getInstance()).setString("token", loginBean.getToken());
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
