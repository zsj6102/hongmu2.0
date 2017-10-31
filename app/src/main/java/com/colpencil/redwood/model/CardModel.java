package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CardWallInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICardModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import java.util.HashMap;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CardModel implements ICardModel {
    private Observable<CardWallInfo> observableStore;//商品区名片墙
    private Observable<CardWallInfo> observableMine;//我的名片墙
    private Observable<CardWallInfo> observableMR;//名人堂
    private Observable<CardWallInfo> observableMy;//我的关注和粉丝列表
    private Observable<CareReturn> observable;
    @Override
    public void loadCardStore(HashMap<String, String> params) {
        observableStore = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCardStore(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CardWallInfo, CardWallInfo>() {
                    @Override
                    public CardWallInfo call(CardWallInfo cardWallInfo) {
                        return cardWallInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void subCardStore(Observer<CardWallInfo> observer) {
        observableStore.subscribe(observer);
    }

    @Override
    public void loadaCardMime(HashMap<String, String> params) {
        observableMine = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCardMine(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CardWallInfo, CardWallInfo>() {
                    @Override
                    public CardWallInfo call(CardWallInfo cardWallInfo) {
                        return cardWallInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCardMine(Observer<CardWallInfo> observer) {
        observableMine.subscribe(observer);
    }

    @Override
    public void getCareStatus(HashMap<String, String> params) {
        observable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
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
       observable.subscribe(observer);
    }

    @Override
    public void loadCardMR(HashMap<String, String> params) {
        observableMR = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getMRCar(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CardWallInfo, CardWallInfo>() {
                    @Override
                    public CardWallInfo call(CardWallInfo cardWallInfo) {
                        return cardWallInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCardMR(Observer<CardWallInfo> observer) {
        observableMR.subscribe(observer);
    }

    @Override
    public void loadCardMy(HashMap<String, String> params) {
        observableMy = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getMyFans(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CardWallInfo, CardWallInfo>() {
                    @Override
                    public CardWallInfo call(CardWallInfo cardWallInfo) {
                        return cardWallInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCardMy(Observer<CardWallInfo> observer) {
        observableMy.subscribe(observer);
    }
}
