package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;

import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.GoodReply;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IGoodRDetailModel;

import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GoodRDetailModel implements IGoodRDetailModel {
    private Observable<ResultInfo<GoodReply>> observable;
    private Observable<ResultInfo<String>> addObservable;
    @Override
    public void loadDetail(Map<String, String> map) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getGoodReplyDetail(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<GoodReply>, ResultInfo<GoodReply>>() {
                    @Override
                    public ResultInfo<GoodReply> call(ResultInfo<GoodReply> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subDetail(Observer<ResultInfo<GoodReply>> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void addDiscuss(Map<String, String> map) {
        addObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getDisscuss(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<String>, ResultInfo<String>>() {
                    @Override
                    public ResultInfo<String> call(ResultInfo<String> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAdd(Observer<ResultInfo<String>> observer) {
        addObservable.subscribe(observer);
    }
}
