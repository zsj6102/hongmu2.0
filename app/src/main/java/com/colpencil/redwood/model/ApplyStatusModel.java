package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IApplyStatusModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ApplyStatusModel implements IApplyStatusModel {
    private Observable<ApplyStatusReturn> statusObservable;
    private Observable<ResultInfo<String>> hotObservable;
    @Override
    public void applyStatus(HashMap<String, String> params) {
        statusObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getApplyStatus(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ApplyStatusReturn, ApplyStatusReturn>() {
                    @Override
                    public ApplyStatusReturn call(ApplyStatusReturn applyStatusReturn) {
                        return applyStatusReturn;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subStauts(Subscriber<ApplyStatusReturn> subscriber) {
        statusObservable.subscribe(subscriber);
    }

    @Override
    public void getHotLine() {
        hotObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getHotLine()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<String>, ResultInfo<String>>() {
                    @Override
                    public ResultInfo<String> call(ResultInfo<String> stringResultInfo) {
                        return stringResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void subHotLine(Subscriber<ResultInfo<String>> subscriber) {
        hotObservable.subscribe(subscriber);
    }
}
