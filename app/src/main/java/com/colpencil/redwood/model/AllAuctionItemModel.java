package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IAllAuctionItemModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AllAuctionItemModel implements IAllAuctionItemModel {

    private Observable<AllGoodsResult> observable;

    @Override
    public void getAllGoods(HashMap<String, RequestBody> strparams, HashMap<String, Integer> intparams) {
        observable= RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getAllGoods(strparams,intparams)
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
}
