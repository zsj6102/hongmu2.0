package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.GoodsTypeResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IAllAuctionModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AllAuctionModel implements IAllAuctionModel {
    Observable<GoodsTypeResult> observable;
    @Override
    public void getGoodsType() {
        observable= RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getGoodsType(1)
                .subscribeOn(Schedulers.io())
                .map(new Func1<GoodsTypeResult, GoodsTypeResult>() {
                    @Override
                    public GoodsTypeResult call(GoodsTypeResult goodsTypeResult) {
                        return goodsTypeResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGetGoodsType(Observer<GoodsTypeResult> observer) {
        observable.subscribe(observer);
    }
}
