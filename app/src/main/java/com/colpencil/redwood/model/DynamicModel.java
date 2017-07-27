package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.bean.result.DynamicResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IDynamicModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.unionpay.mobile.android.global.a.F;

public class DynamicModel implements IDynamicModel {
    private Observable<DynamicResult> observable;
    private Observable<DynamicResult> collectionObservable;
    private Observable<DynamicResult> dynamicResultObserver;

    @Override
    public void getDynamic(HashMap<String, Integer> intparams, HashMap<String, RequestBody> strparams) {
        observable= RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getDynamic(strparams,intparams)
                .subscribeOn(Schedulers.io())
                .map(new Func1<DynamicResult, DynamicResult>() {
                    @Override
                    public DynamicResult call(DynamicResult dynamicResult) {
                        return dynamicResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGetDynamic(Observer<DynamicResult> observer) {
        observable.subscribe(observer);
    }

    /**
     * 品牌和名师我的收藏
     * @param params
     */
    @Override
    public void getCollection(HashMap<String, String> params) {
        collectionObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCollection(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<DynamicResult, DynamicResult>() {
                    @Override
                    public DynamicResult call(DynamicResult dynamicResult) {
                        return dynamicResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCollection(Observer<DynamicResult> observer) {
        collectionObservable.subscribe(observer);
    }

    /**
     * 品牌和名师店铺动态
     * @param params
     */
    @Override
    public void getStoreDynamic(HashMap<String, String> params) {
        dynamicResultObserver = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getStoreDynamics(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<DynamicResult, DynamicResult>() {
                    @Override
                    public DynamicResult call(DynamicResult dynamicResult) {
                        return dynamicResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subStoreDynamic(Observer<DynamicResult> observer) {
        dynamicResultObserver.subscribe(observer);
    }


}
