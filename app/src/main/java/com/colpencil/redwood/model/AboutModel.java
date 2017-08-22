package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.Info.StoreDetail;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IAboutModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AboutModel implements IAboutModel {
    private Observable<ResultInfo<StoreDetail>> observable;
    private Observable<ResultInfo<List<ItemStoreFans>>> observablelike;//关注
    private Observable<ResultInfo<List<ItemStoreFans>>> observablefans;//粉丝
    private Observable<CareReturn> careReturnObservable;
    private Observable<CareReturn>  uncareObservable;
    private Observable<CareReturn>  storeObservable;
    @Override
    public void care(Map<String, String> parms) {
        careReturnObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCareStatus(parms)
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
        careReturnObservable.subscribe(observer);
    }

    @Override
    public void unCare(Map<String, String> params) {
        uncareObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
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
    public void subUnCare(Observer<CareReturn> observer) {
        uncareObservable.subscribe(observer);
    }

    @Override
    public void loadinfo(Map<String, String> params) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST).createApi(RedWoodApi.class).getStoreDetail(params).subscribeOn(Schedulers.io()).map(new Func1<ResultInfo<StoreDetail>, ResultInfo<StoreDetail>>() {
            @Override
            public ResultInfo<StoreDetail> call(ResultInfo<StoreDetail> storeDetailResultInfo) {
                return storeDetailResultInfo;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subInfo(Observer<ResultInfo<StoreDetail>> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void lodaLike(Map<String, String> params) {
        observablelike = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST).createApi(RedWoodApi.class).getStoreFans(params).subscribeOn(Schedulers.io()).map(new Func1<ResultInfo<List<ItemStoreFans>>, ResultInfo<List<ItemStoreFans>>>() {
            @Override
            public ResultInfo<List<ItemStoreFans>> call(ResultInfo<List<ItemStoreFans>> listResultInfo) {
                return listResultInfo;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLike(Observer<ResultInfo<List<ItemStoreFans>>> observer) {
        observablelike.subscribe(observer);
    }

    @Override
    public void loadFans(Map<String, String> params) {
        observablefans = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST).createApi(RedWoodApi.class).getStoreFans(params).subscribeOn(Schedulers.io()).map(new Func1<ResultInfo<List<ItemStoreFans>>, ResultInfo<List<ItemStoreFans>>>() {
            @Override
            public ResultInfo<List<ItemStoreFans>> call(ResultInfo<List<ItemStoreFans>> listResultInfo) {
                return listResultInfo;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subFans(Observer<ResultInfo<List<ItemStoreFans>>> observer) {
        observablefans.subscribe(observer);
    }

    @Override
    public void storeCare(Map<String, String> params) {
        storeObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
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
    public void subUnstoreCare(Observer<CareReturn> observer) {
       storeObservable.subscribe(observer);
    }
}
