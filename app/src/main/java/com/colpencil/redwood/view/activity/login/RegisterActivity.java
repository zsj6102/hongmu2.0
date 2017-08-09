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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.TimeCount;
import com.colpencil.redwood.present.login.RegisterPresent;
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
 * @author 陈 宝
 * @Description:注册
 * @Email 1041121352@qq.com
 * @date 2016/11/5
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_pwd
)
public class RegisterActivity extends ColpencilActivity implements IPwdView {

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
    @Bind(R.id.ll_seeAgreement)
    LinearLayout ll_seeAgreement;
    @Bind(R.id.check_protocol)
    CheckBox checkBox;
    @Bind(R.id.tv_getCode)
    TextView tv_getCode;
    @Bind(R.id.iv_removePhonePwd)
    ImageView iv_removePhonePwd;
    @Bind(R.id.tv_sumbitPwd)
    TextView tv_sumbitPwd;

    private RegisterPresent present;

    @Override
    protected void initViews(View view) {
        ll_seeAgreement.setVisibility(View.VISIBLE);
        tv_main_title.setText("注册");
        tv_sumbitPwd.setText("注册");
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
        present = new RegisterPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void codeResult(LoginBean bean) {
        hideLoading();
        ToastTools.showShort(this, bean.getMsg());
        if (bean.getCode() == 1) {
            TimeCount time = new TimeCount(60000, 1000, tv_getCode);
            time.start();//开启倒计时
        }
    }

    @Override
    public void bindResult(LoginBean bean) {
    }

    @Override
    public void changeResult(LoginBean bean) {
    }

    @Override
    public void forgetResult(LoginBean bean) {
    }

    @Override
    public void registerResult(LoginBean bean) {
        hideLoading();
        if (bean.getCode() == 1) {
            present.login(etPwd_phone.getText().toString(),
                    Md5Utils.encode(etPwd_setPwd.getText().toString()));
        }
        ToastTools.showShort(this, bean.getMsg());
    }

    @Override
    public void loginResult(LoginBean bean) {
        hideLoading();
        if (bean.getCode() == 1) {
            SharedPreferencesUtil.getInstance(App.getInstance()).setString(StringConfig.MOBILEPHONE, etPwd_phone.getText().toString());
            SharedPreferencesUtil.getInstance(App.getInstance()).setString(StringConfig.PASSWORD, etPwd_setPwd.getText().toString());
            SharedPreferencesUtil.getInstance(App.getInstance()).setBoolean(StringConfig.ISLOGIN, true);
            SharedPreferencesUtil.getInstance(App.getInstance()).setInt("member_id", bean.getMember_id());
            SharedPreferencesUtil.getInstance(App.getInstance()).setString("token", bean.getToken());
            intentToHome();
        }
        ToastTools.showShort(this, bean.getMsg());
    }

    @OnClick(R.id.tv_seeAgreement)
    void protocol() {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("key", "PwdActivity");
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
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
            ToastTools.showShort(this, "注册之前请先同意《顶藏网务协议》");
            return;
        } else {
            present.register(phoneNum, checkNum, Md5Utils.encode(password));
        }
    }

    @OnClick(R.id.tv_getCode)
    void getCheckNum() {
        if (tv_getCode.getText().toString().trim().equals("重新验证")
                || tv_getCode.getText().toString().trim().equals("获取验证码")) {
            if (TextUtils.isEmpty(etPwd_phone.getText().toString())) {
                ToastTools.showShort(this, "手机号码不能为空！");
                return;
            } else if (!TextStringUtils.isMobileNO(etPwd_phone.getText().toString())) {
                ToastTools.showShort(this, "请输入正确的手机号码");
                return;
            }
            showLoading(Constants.progressName);
            present.getCheckNum(etPwd_phone.getText().toString());
        }
    }

    private void intentToHome() {
        //刷新个人中心
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(63);
        RxBus.get().post("rxBusMsg", rxBusMsg);
        //跳转首页
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
