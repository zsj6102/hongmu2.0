package com.colpencil.redwood.present.login;

import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.model.RegisterModel;
import com.colpencil.redwood.model.imples.IRegisterModel;
import com.colpencil.redwood.view.impl.IPwdView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈 宝
 * @Description:注册
 * @Email 1041121352@qq.com
 * @date 2016/11/5
 */
public class RegisterPresent extends ColpencilPresenter<IPwdView> {

    private IRegisterModel model;

    public RegisterPresent() {
        model = new RegisterModel();
    }

    //获取验证码
    public void getCheckNum(String number) {
        model.getCheckNum(number);
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginBean bean) {
                mView.codeResult(bean);
            }
        };
        model.subCheckNum(observer);
    }

    //注册
    public void register(String number, String code, String password) {
        model.register(number, code, password);
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginBean bean) {
                mView.registerResult(bean);
            }
        };
        model.subRegister(observer);
    }

    //登录
    public void login(String number, String password) {
        model.login(number, password);
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginBean bean) {
                mView.loginResult(bean);
            }
        };
        model.subLogin(observer);
    }
}
