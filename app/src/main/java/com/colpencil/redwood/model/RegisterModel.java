package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IRegisterModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RegisterModel implements IRegisterModel {

    private Observable<LoginBean> register;
    private Observable<LoginBean> getCheckNum;
    private Observable<LoginBean> login;

    @Override
    public void register(String mobile, String code, String password) {
        register = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .phoneRegister(mobile, code, password)
                .subscribeOn(Schedulers.io())
                .map(new Func1<LoginBean, LoginBean>() {
                    @Override
                    public LoginBean call(LoginBean bean) {
                        return bean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRegister(Observer<LoginBean> subscriber) {
        register.subscribe(subscriber);
    }

    @Override
    public void getCheckNum(String mobile) {
        getCheckNum = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).getvalidateCode(mobile)
                .subscribeOn(Schedulers.io())
                .map(new Func1<LoginBean, LoginBean>() {
                    @Override
                    public LoginBean call(LoginBean loginBean) {
                        return loginBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCheckNum(Observer<LoginBean> subscriber) {
        getCheckNum.subscribe(subscriber);
    }

    @Override
    public void login(String mobile, String password) {
        login = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).login(mobile, password)
                .subscribeOn(Schedulers.io())
                .map(new Func1<LoginBean, LoginBean>() {
                    @Override
                    public LoginBean call(LoginBean loginBean) {
                        return loginBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLogin(Observer<LoginBean> subscriber) {
        login.subscribe(subscriber);
    }
}
