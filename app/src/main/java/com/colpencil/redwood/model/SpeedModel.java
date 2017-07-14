package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ISpeedModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SpeedModel implements ISpeedModel {
    Observable<AdResult> observable;
    @Override
    public void getAd(String type) {
        observable= RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getAd(type)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AdResult, AdResult>() {
                    @Override
                    public AdResult call(AdResult adResult) {
                        return adResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGetAd(Observer<AdResult> observer) {
        observable.subscribe(observer);
    }
}
