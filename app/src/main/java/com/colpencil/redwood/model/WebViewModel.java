package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IWebViewModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：商品
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 04
 */
public class WebViewModel implements IWebViewModel {

    private Observable<ResultCodeInt> resultCodeIntObservable;
    private Observable<EntityResult<String>> rule;
    private Observable<EntityResult<String>> service;
    private Observable<EntityResult<String>> info;

    @Override
    public void logisticsInfor(int orderId) {
        resultCodeIntObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).getCheckship(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        orderId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                    @Override
                    public ResultCodeInt call(ResultCodeInt resultCodeInt) {
                        return resultCodeInt;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ResultCodeInt> subscriber) {
        resultCodeIntObservable.subscribe(subscriber);
    }

    @Override
    public void getH5Url(int type) {
        if (type == 1) {//注册协议
            resultCodeIntObservable = RetrofitManager
                    .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                    .createApi(RedWoodApi.class).registered_agreement()
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                        @Override
                        public ResultCodeInt call(ResultCodeInt resultCodeInt) {
                            return resultCodeInt;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        } else if (type == 2) {//关于我们
            resultCodeIntObservable = RetrofitManager
                    .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                    .createApi(RedWoodApi.class).getAboutUs()
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                        @Override
                        public ResultCodeInt call(ResultCodeInt resultCodeInt) {
                            return resultCodeInt;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    @Override
    public void loadRule() {
        rule = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadRule()
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRule(Observer<EntityResult<String>> observer) {
        rule.subscribe(observer);
    }

    @Override
    public void loadService() {
        service = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadService(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subService(Observer<EntityResult<String>> observer) {
        service.subscribe(observer);
    }

    @Override
    public void loadInfo() {
        info = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadMemberInfo()
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subInfo(Observer<EntityResult<String>> observer) {
        info.subscribe(observer);
    }

}
