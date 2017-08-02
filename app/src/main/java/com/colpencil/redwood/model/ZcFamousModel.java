package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.bean.result.ZcAllCardInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IZcFoumousModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ZcFamousModel implements IZcFoumousModel {
    private Observable<ZcAllCardInfo> observable;
    private Observable<CareReturn> mobservable;
    @Override
    public void getAllFamous(Map<String, String> params) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getAllFamous(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ZcAllCardInfo, ZcAllCardInfo>() {
                    @Override
                    public ZcAllCardInfo call(ZcAllCardInfo zcAllCardInfo) {
                        return zcAllCardInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subfamous(Observer<ZcAllCardInfo> observer) {
       observable.subscribe(observer);
    }

    @Override
    public void getCareStatus(HashMap<String, String> params) {
        mobservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCareStatus(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CareReturn, CareReturn>() {
                    @Override
                    public CareReturn call(CareReturn careReturn) {
                        return careReturn;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCare(Observer<CareReturn> observer) {
        mobservable.subscribe(observer);
    }
}
