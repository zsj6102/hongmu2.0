package com.colpencil.redwood.present.login;


import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.model.PwdModel;
import com.colpencil.redwood.model.imples.IPwdModel;
import com.colpencil.redwood.view.impl.IPwdView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/22 13 42
 */
public class PwdPresenter extends ColpencilPresenter<IPwdView> {

    private IPwdModel model;

    public PwdPresenter() {
        model = new PwdModel();
    }

    public void forget(String mobile, String code, String password) {
        model.forgetPwd(mobile, code, password);
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginBean bean) {
                mView.forgetResult(bean);
            }
        };
        model.subForgetPwd(observer);
    }

    public void change(String mobile, String code, String password) {
        model.changePwd(mobile, code, password);
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginBean bean) {
                mView.changeResult(bean);
            }
        };
        model.subChangePwd(observer);
    }

    public void binds(String mobile, String code, String password, String openId) {
        model.bindNum(mobile, code, password, openId);
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginBean bean) {
                mView.bindResult(bean);
            }
        };
        model.subBind(observer);
    }

    public void forgetCode(String mobile) {
        model.getCodeForget(mobile);
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
        model.subCodeForget(observer);
    }

    public void changeCode(String mobile) {
        model.getCodeChange(mobile);
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
        model.subCodeChange(observer);
    }

    public void bindCode(String mobile) {
        model.getCodeBind(mobile);
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
        model.subCodeBind(observer);
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
