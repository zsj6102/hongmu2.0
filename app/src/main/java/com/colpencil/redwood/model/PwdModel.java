package com.colpencil.redwood.model;


import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IPwdModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/22 13 43
 */
public class PwdModel implements IPwdModel {

    private Observable<LoginBean> changeCode;
    private Observable<LoginBean> change;
    private Observable<LoginBean> bindCode;
    private Observable<LoginBean> binds;
    private Observable<LoginBean> forgetCode;
    private Observable<LoginBean> forget;
    private Observable<LoginBean> login;

    //绑定手机号
    @Override
    public void bindNum(String mobile, String code, String password, String openId) {
        binds = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .thirdAdd(mobile, code, password, openId)
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
    public void subBind(Observer<LoginBean> subscriber) {
        binds.subscribe(subscriber);
    }

    //忘记密码
    @Override
    public void forgetPwd(String mobile, String code, String password) {
        forget = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .retrievePassword(mobile, code, password)
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
    public void subForgetPwd(Observer<LoginBean> subscriber) {
        forget.subscribe(subscriber);
    }

    //修改密码
    @Override
    public void changePwd(String mobile, String code, String password) {
        change = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).editPassword(mobile,
                        code,
                        password,
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
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
    public void subChangePwd(Observer<LoginBean> subscriber) {
        change.subscribe(subscriber);
    }

    //获取验证码--忘记密码
    @Override
    public void getCodeForget(String mobile) {
        forgetCode = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).retrieveYzm(mobile)
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
    public void subCodeForget(Observer<LoginBean> subscriber) {
        forgetCode.subscribe(subscriber);
    }

    //获取验证码--修改密码
    @Override
    public void getCodeChange(String mobile) {
        changeCode = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getUpdateYzm(mobile,
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"))
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
    public void subCodeChange(Observer<LoginBean> subscriber) {
        changeCode.subscribe(subscriber);
    }

    //获取验证码--绑定手机号
    @Override
    public void getCodeBind(String mobile) {
        bindCode = RetrofitManager
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
    public void subCodeBind(Observer<LoginBean> subscriber) {
        bindCode.subscribe(subscriber);
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
