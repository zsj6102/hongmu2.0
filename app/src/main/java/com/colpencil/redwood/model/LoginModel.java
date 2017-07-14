package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ILoginModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/22 11 16
 */
public class LoginModel implements ILoginModel {
    private Observable<LoginBean> observableLoginBean;

    @Override
    public void sumbitLogin(String mobile, String password) {
        observableLoginBean = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).login(mobile, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<LoginBean, LoginBean>() {
                    @Override
                    public LoginBean call(LoginBean loginBean) {
                        return loginBean;
                    }
                });
    }

    @Override
    public void sumbitThird(String openId) {
        observableLoginBean = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).thirdLogin(openId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<LoginBean, LoginBean>() {
                    @Override
                    public LoginBean call(LoginBean loginBean) {
                        return loginBean;
                    }
                });
    }

    @Override
    public void sub(Observer<LoginBean> subscriber) {
        observableLoginBean.subscribe(subscriber);
    }
}
