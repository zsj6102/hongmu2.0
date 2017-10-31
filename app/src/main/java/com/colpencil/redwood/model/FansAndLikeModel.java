package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IFansAndLikeModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FansAndLikeModel implements IFansAndLikeModel{
    private Observable<ResultInfo<List<ItemStoreFans>>> observablelike;
    private Observable<ResultInfo<List<ItemStoreFans>>> observablesearch;
    private Observable<CareReturn> observable;
    private Observable<ResultInfo<List<ItemStoreFans>>> hotfansObservable;
    @Override
    public void loadLikeAndFans(Map<String, String> map) {
        observablelike = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST).createApi(RedWoodApi.class).getStoreFans(map).subscribeOn(Schedulers.io()).map(new Func1<ResultInfo<List<ItemStoreFans>>, ResultInfo<List<ItemStoreFans>>>() {
            @Override
            public ResultInfo<List<ItemStoreFans>> call(ResultInfo<List<ItemStoreFans>> listResultInfo) {
                return listResultInfo;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subFansLike(Observer<ResultInfo<List<ItemStoreFans>>> observer) {
        observablelike.subscribe(observer);
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
    public void loadSearch(Map<String, String> map) {
        observablesearch = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getSearchStore(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<ItemStoreFans>>, ResultInfo<List<ItemStoreFans>>>() {
                    @Override
                    public ResultInfo<List<ItemStoreFans>> call(ResultInfo<List<ItemStoreFans>> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void subSearch(Observer<ResultInfo<List<ItemStoreFans>>> observer) {
        observablesearch.subscribe(observer);
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
}
