package com.colpencil.redwood.present.login;

import android.util.Log;

import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.model.LoginModel;
import com.colpencil.redwood.model.imples.ILoginModel;
import com.colpencil.redwood.view.impl.ILoginView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/22 11 12
 */
public class LoginPresent extends ColpencilPresenter<ILoginView> {

    private ILoginModel loginModel;

    public LoginPresent() {
        loginModel = new LoginModel();
    }

    /**
     * 进行登录操作
     *
     * @param mobile
     * @param password
     */
    public void sumbitLogin(String mobile, String password) {
        loginModel.sumbitLogin(mobile, password);
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(LoginBean loginBean) {
                mView.loginSuccess(loginBean);
            }
        };
        loginModel.sub(observer);
    }

    /**
     * 进行第三方登录判断
     */
    public void thirdState(String openId) {
        loginModel.sumbitThird(openId);
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", e.toString());
            }

            @Override
            public void onNext(LoginBean loginBean) {
                Log.e("返回值", loginBean.toString());
                mView.thirstSuccess(loginBean);
            }
        };
        loginModel.sub(observer);
    }
}
