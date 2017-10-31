package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.Goods_list;
import com.colpencil.redwood.bean.Goods_list_item;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.AdResult;

import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IHomeGoodsPageModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.unionpay.mobile.android.global.a.G;


public class HomeGoodsPageModel implements IHomeGoodsPageModel {
    private Observable<AdResult> adObservable;
    private Observable<ResultInfo<List<AllSpecialInfo>>> coverObservable;
    private Observable<ResultInfo<List<ItemStoreFans>>> hotfansObservable;
    private Observable<ResultInfo<List<Goods_list>>> goodsObservable;
    private Observable<ResultInfo<List<Goods_list_item>>> secondObservable;
    private Observable<CareReturn>  storeObservable;
    @Override
    public void getAdv(String type) {
        adObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
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
    public void subAdv(Observer<AdResult> observer) {
        adObservable.subscribe(observer);
    }

    @Override
    public void getCoverSpecial(Map<String, String> map) {
        coverObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCoverSpecial(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<AllSpecialInfo>>, ResultInfo<List<AllSpecialInfo>>>() {
                    @Override
                    public ResultInfo<List<AllSpecialInfo>> call(ResultInfo<List<AllSpecialInfo>> listResultInfo) {
                        return listResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCover(Observer<ResultInfo<List<AllSpecialInfo>>> observer) {
        coverObservable.subscribe(observer);
    }

    @Override
    public void getHotFans(Map<String, String> map) {
        hotfansObservable =  RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getHotFans(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<ItemStoreFans>>, ResultInfo<List<ItemStoreFans>>>() {
                    @Override
                    public ResultInfo<List<ItemStoreFans>> call(ResultInfo<List<ItemStoreFans>> listResultInfo) {
                        return listResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subHotFans(Observer<ResultInfo<List<ItemStoreFans>>> observer) {
        hotfansObservable.subscribe(observer);
    }

    @Override
    public void getContent(Map<String, String> map) {
        goodsObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getGoodsList(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<Goods_list>>, ResultInfo<List<Goods_list>>>() {
                    @Override
                    public ResultInfo<List<Goods_list>> call(ResultInfo<List<Goods_list>> listResultInfo) {
                        return listResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subContent(Observer<ResultInfo<List<Goods_list>>> observer) {
        goodsObservable.subscribe(observer);
    }

    @Override
    public void sbuSecond(Observer<ResultInfo<List<Goods_list_item>>> observer) {
        secondObservable.subscribe(observer);
    }

    @Override
    public void getSecondGoods(Map<String, String> map) {
        secondObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getSecondGoods(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<Goods_list_item>>, ResultInfo<List<Goods_list_item>>>() {
                    @Override
                    public ResultInfo<List<Goods_list_item>> call(ResultInfo<List<Goods_list_item>> listResultInfo) {
                        return listResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
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
