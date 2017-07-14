package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.SignInResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.widgets.dialogs.SigninDialog;
import com.colpencil.redwood.present.home.SignInPresenter;
import com.colpencil.redwood.view.impl.ISignInView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description:签到
 * @Email DramaScript@outlook.com
 * @date 2016/7/22
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_qiandao)
public class SignInActivity extends ColpencilActivity implements ISignInView {

    @Bind(R.id.sign_in_btn)
    Button button;
    @Bind(R.id.base_header_ll)
    LinearLayout ll_header;
    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.tv_integral)
    TextView tv_integral;

    private SigninDialog dialog;
    private SignInPresenter presenter;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        ll_header.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        tv_title.setText(getResources().getString(R.string.home_sign_in));
        showLoading("正在加载界面中...");
        presenter.loadState();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new SignInPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.sign_in_btn)
    void SigninClick() {
        showLoading("");
        presenter.signinserver(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "",
                SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @Override
    public void signInSuccess(SignInResult result) {
        hideLoading();
        dialog = new SigninDialog(this);
        if (result.getCode().equals("1")) {
            dialog.setTitle("签到成功");
            button.setBackgroundResource(R.drawable.gray_solid_shape);
            button.setClickable(false);
            dialog.setTips("本次签到获得" + result.getPoint() + "积分");
            dialog.show();
            dialog.setListener(new SigninDialog.ClickListener() {
                @Override
                public void sureClick() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
            tv_integral.setText(result.getTotalPoint() + " ");
            button.setText("今日已签到");
            RxBusMsg msg = new RxBusMsg();
            msg.setType(4);
            RxBus.get().post("rxBusMsg",msg);
        } else if (result.getCode().equals("3")) {
            ToastTools.showShort(this, result.getMsg());
        } else {
            if (result.getCode().equals("2")) {
                button.setText("今日已签到");
            }
            dialog.setTitle("签到失败");
            button.setBackgroundResource(R.drawable.gray_solid_shape);
            button.setClickable(false);
            dialog.setTips(result.getMsg());
            dialog.show();
            dialog.setListener(new SigninDialog.ClickListener() {
                @Override
                public void sureClick() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
            tv_integral.setText(result.getTotalPoint() + " ");
        }
    }

    @Override
    public void loadstate(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 2 || result.getCode() == 3) {
            button.setBackgroundResource(R.drawable.red_solid_shape);
            button.setClickable(true);
            tv_integral.setText(result.getTotalPoint() + " ");
        } else {
            if (result.getCode() == 1) {
                button.setText("今日已签到");
            }
            button.setBackgroundResource(R.drawable.gray_solid_shape);
            button.setClickable(false);
            tv_integral.setText(result.getTotalPoint() + " ");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            SigninClick();
        }
    }
}
