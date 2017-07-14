package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeRecommend;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ISplashModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈 宝
 * @Description:启动页
 * @Email 1041121352@qq.com
 * @date 2016/9/28
 */
public class SplashModel implements ISplashModel {

    private Observable<LoginBean> observable;
    private Observable<EntityResult<HomeRecommend>> recommend;

    @Override
    public void login() {
        String password = SharedPreferencesUtil.getInstance(App.getInstance()).getString(StringConfig.PASSWORD);
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .login(SharedPreferencesUtil.getInstance(App.getInstance()).getString(StringConfig.MOBILEPHONE),
                        Md5Utils.encode(password))
                .subscribeOn(Schedulers.io())
                .map(new Func1<LoginBean, LoginBean>() {
                    @Override
                    public LoginBean call(LoginBean loginBean) {
                        return loginBean;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLogin(Observer<LoginBean> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void loadHomeRecommend() {
        recommend = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadHomeRecommend()
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<HomeRecommend>, EntityResult<HomeRecommend>>() {
                    @Override
                    public EntityResult<HomeRecommend> call(EntityResult<HomeRecommend> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subHomeRecommend(Observer<EntityResult<HomeRecommend>> observer) {
        recommend.subscribe(observer);
    }
}
