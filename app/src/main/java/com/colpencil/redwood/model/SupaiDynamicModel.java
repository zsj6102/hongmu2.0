package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ISupaiDynamicModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SupaiDynamicModel implements ISupaiDynamicModel {
    private Observable<AllGoodsResult> colsupaiObservable;
    private Observable<AllGoodsResult> supaiDynamic;
    private Observable<AddResult> addResultObservable;
    private  Observable<AddResult> likeObservable;
    /**
     *  我的收藏(速拍)
     * @param params
     */
    @Override
    public void getSupaiCol(HashMap<String, String> params) {
        colsupaiObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCollectionSupai(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AllGoodsResult, AllGoodsResult>() {
                    @Override
                    public AllGoodsResult call(AllGoodsResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSupaiCol(Observer<AllGoodsResult> observer) {
        colsupaiObservable.subscribe(observer);
    }

    /**
     * 我的拍品动态(速拍)
     * @param params
     */
    @Override
    public void getSupaiDynamic(HashMap<String, String> params) {
        supaiDynamic = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getSupaiDynamic(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AllGoodsResult, AllGoodsResult>() {
                    @Override
                    public AllGoodsResult call(AllGoodsResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSupaiDynamic(Observer<AllGoodsResult> observer) {
        supaiDynamic.subscribe(observer);
    }
    @Override
    public void getAddCommentResult(Map<String, String> params) {
        addResultObservable = RetrofitManager.getInstance(1, App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getAdd(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AddResult, AddResult>() {
                    @Override
                    public AddResult call(AddResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAddResult(Observer<AddResult> observer) {
        addResultObservable.subscribe(observer);
    }


    @Override
    public void getLikeResult(Map<String, String> params) {
        likeObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getLike(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AddResult, AddResult>() {
                    @Override
                    public AddResult call(AddResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLike(Observer<AddResult> observer) {
        likeObservable.subscribe(observer);
    }
}
