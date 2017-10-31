package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.bean.result.OrderPayInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IAllAuctionItemModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class AllAuctionItemModel implements IAllAuctionItemModel {

    private Observable<AllGoodsResult> observable;
    private Observable<AddResult> addResultObservable;
    private  Observable<AddResult> likeObservable;
    private Observable<OrderPayInfo> directObservable;
    @Override
    public void getAllGoods(Map<String,String> map) {
        observable= RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getAllGoods(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AllGoodsResult, AllGoodsResult>() {
                    @Override
                    public AllGoodsResult call(AllGoodsResult allGoodsResult) {
                        return allGoodsResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGetAllGoods(Observer<AllGoodsResult> observer) {
        observable.subscribe(observer);
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
    @Override
    public void subDirectOrder(Observer<OrderPayInfo> observer) {
        directObservable.subscribe(observer);
    }

    @Override
    public void loadDirectOrder(Map<String, String> map) {
        directObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getDirectOrder(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<OrderPayInfo, OrderPayInfo>() {
                    @Override
                    public OrderPayInfo call(OrderPayInfo orderPayInfo) {
                        return orderPayInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }
}
