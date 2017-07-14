package com.colpencil.redwood.view.activity.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.present.login.LoginPresent;
import com.colpencil.redwood.view.activity.HomeActivity;
import com.colpencil.redwood.view.impl.ILoginView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

import static com.colpencil.redwood.R.id.iv_sina;
import static com.colpencil.redwood.R.id.iv_weChat;

/**
 * 描述：登录
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/22 09 57
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_login
)
public class LoginActivity extends ColpencilActivity implements ILoginView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView leftTile;
    @Bind(R.id.et_loginPhone)
    EditText et_loginPhone;
    @Bind(R.id.et_loginPwd)
    EditText et_loginPwd;
    @Bind(R.id.iv_removePhoneLogin)
    ImageView iv_removePhoneLogin;

    private LoginPresent present;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private String typeFlag;//意图跳转标识
    private UMShareAPI mShareAPI;
    private String openId;//第三方登录
    private int requestCode;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        initData();
    }

    private void initData() {
        mShareAPI = UMShareAPI.get(this);
        typeFlag = getIntent().getStringExtra("key");
        requestCode = getIntent().getIntExtra(StringConfig.REQUEST_CODE, 101);  //默认值为100，表示请求登录
        tv_main_title.setText("用户登录");
        leftTile.setText("注册");
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
                if (msg.getType() == 3 || msg.getType() == 63) {
                    finish();
                }
            }
        };
        observable.subscribe(subscriber);
        et_loginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextStringUtils.isEmpty(et_loginPhone.getText().toString()) == false) {
                    iv_removePhoneLogin.setVisibility(View.VISIBLE);
                } else {
                    iv_removePhoneLogin.setVisibility(View.GONE);
                }
            }
        });
        et_loginPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!TextUtils.isEmpty(et_loginPhone.getText().toString())) {
                        iv_removePhoneLogin.setVisibility(View.VISIBLE);
                    } else {
                        iv_removePhoneLogin.setVisibility(View.GONE);
                    }
                } else {
                    iv_removePhoneLogin.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new LoginPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), loginBean.getMsg());
        if (loginBean.getCode() == 1) {//登录成功
            String password = et_loginPwd.getText().toString();
            SharedPreferencesUtil.getInstance(this).setString(StringConfig.MOBILEPHONE, et_loginPhone.getText().toString());
            SharedPreferencesUtil.getInstance(this).setString(StringConfig.PASSWORD, password);
            SharedPreferencesUtil.getInstance(this).setBoolean(StringConfig.ISLOGIN, true);
            gotoHome(loginBean);
        } else {
            SharedPreferencesUtil.getInstance(this).setBoolean(StringConfig.ISLOGIN, false);
        }
    }

    @Override
    public void thirstSuccess(LoginBean loginBean) {
        hideLoading();
        if (loginBean.getCode() == 1) {//直接进行登录
            Intent intent = new Intent(LoginActivity.this, PwdActivity.class);
            intent.putExtra("key", "bindPhone");
            intent.putExtra("openId", openId);
            startActivity(intent);
        } else if (loginBean.getCode() == 2) {//未使用登录，绑定手机号码
            Intent intent = new Intent(LoginActivity.this, PwdActivity.class);
            intent.putExtra("key", "bindPhone");
            intent.putExtra("openId", openId);
            startActivity(intent);
        }
    }

    /**
     * 进入主页
     */
    private void gotoHome(LoginBean loginBean) {
        SharedPreferencesUtil.getInstance(this).setInt("member_id", loginBean.getMember_id());
        SharedPreferencesUtil.getInstance(this).setString("token", loginBean.getToken());
        if (requestCode == Constants.REQUEST_LOGIN) {
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(4);
            RxBus.get().post("rxBusMsg", rxBusMsg);
            this.setResult(Constants.REQUEST_LOGIN);
            finish();
        } else if (typeFlag != null && typeFlag.equals("meFragment")) {
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(4);
            RxBus.get().post("rxBusMsg", rxBusMsg);
            finish();
        } else {
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(3);
            RxBus.get().post("rxBusMsg", rxBusMsg);
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }

    UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data != null) {
                if (platform.equals(SHARE_MEDIA.WEIXIN)) {
                    openId = data.get("openid");
                }
                if (platform.equals(SHARE_MEDIA.SINA)) {
                    openId = data.get("uid");
                }
                if (platform.equals(SHARE_MEDIA.QQ)) {
                    openId = data.get("openid");
                }
                showLoading(Constants.progressName);
                present.thirdState(openId);
            } else {
                ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "第三方登录失败");
            }
            /**
             * QQ开放平台没注册，调用友盟自带的key
             * 微信第三方登陆打不开：手机应用管理将微信的数据清除
             * 新浪要设置REDIRECT_URL，Auth2.0机制
             * debug的data数据不全，签名打包才完整！
             */
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "第三方登录失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "第三方登录失败");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        et_loginPwd.setText("");
        et_loginPhone.setText("");
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    /**
     * 忘记密码
     */
    @OnClick(R.id.tv_forgetPwd)
    void forgetPwd() {
        Intent intent = new Intent(LoginActivity.this, PwdActivity.class);
        intent.putExtra("key", "forgetPwd");
        startActivity(intent);
    }

    /**
     * 登录操作
     */
    @OnClick(R.id.tv_sumbitLogin)
    void loginClick() {
        if (TextUtils.isEmpty(et_loginPhone.getText().toString())) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "手机号不能为空");
            return;
        } else if (TextStringUtils.isMobileNO(et_loginPhone.getText().toString()) == false) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "手机号输入有误");
            return;
        } else if (TextUtils.isEmpty(et_loginPwd.getText().toString())) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "密码不能为空");
            return;
        } else if (TextStringUtils.IsPassword(et_loginPwd.getText().toString()) == false
                || et_loginPwd.getText().toString().length() < 6
                || et_loginPwd.getText().toString().length() > 14) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "密码输入有误");
            return;
        } else {//信息合法性判断结束
            showLoading(Constants.progressName);
            String pwd = et_loginPwd.getText().toString();
            present.sumbitLogin(et_loginPhone.getText().toString(),
                    Md5Utils.encode(pwd));
        }
    }

    /**
     * 注册
     */
    @OnClick(R.id.tv_shoppingCartFinish)
    void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra("key", "registered");
        startActivity(intent);
    }

    /**
     * QQ登录
     */
    @OnClick(R.id.iv_qq)
    void loginForQQ() {
        mShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
    }

    /**
     * 微信登录
     */
    @OnClick(iv_weChat)
    void loginForWechat() {
        mShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    /**
     * 新浪登录
     */
    @OnClick(iv_sina)
    void loginForSina() {
        mShareAPI.doOauthVerify(this, SHARE_MEDIA.SINA, umAuthListener);
    }

    /**
     * 清空输入框
     */
    @OnClick(R.id.iv_removePhoneLogin)
    void clearMobile() {
        et_loginPhone.setText("");
        et_loginPhone.requestFocus();
    }
}
