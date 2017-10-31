package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.Info.ApplyGoodInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IModifyGoodModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ModifyGoodModel implements IModifyGoodModel {
    private Observable<ResultInfo<ApplyGoodInfo>> good;
    private Observable<CatListBean> catObservable;
    private Observable<ResultInfo<String>> prob;
    private Observable<ResultInfo<List<CoverSpecialItem>>> zcObservable;
    @Override
    public void loadGood(int id) {
        good = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getApplyGoodInfo(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<ApplyGoodInfo>, ResultInfo<ApplyGoodInfo>>() {
                    @Override
                    public ResultInfo<ApplyGoodInfo> call(ResultInfo<ApplyGoodInfo> applyGoodInfoResultInfo) {
                        return applyGoodInfoResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGood(Observer<ResultInfo<ApplyGoodInfo>> observer) {
       good.subscribe(observer);
    }
    @Override
    public void loadCatList(int id) {
        catObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getFirstList(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CatListBean, CatListBean>() {
                    @Override
                    public CatListBean call(CatListBean catReturnData) {
                        return catReturnData;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<CatListBean> subscriber) {
        catObservable.subscribe(subscriber);
    }

    @Override
    public void loadPro(Map<String, String> map) {
        prob = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getProportion(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<String>, ResultInfo<String>>() {
                    @Override
                    public ResultInfo<String> call(ResultInfo<String> stringResultInfo) {
                        return stringResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subPro(Subscriber<ResultInfo<String>> observer) {
        prob.subscribe(observer);
    }
    @Override
    public void subZcList(Subscriber<ResultInfo<List<CoverSpecialItem>>> subscriber) {
        zcObservable.subscribe(subscriber);
    }

    @Override
    public void loadZcList(int id) {
        zcObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getPublishZc(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<CoverSpecialItem>>, ResultInfo<List<CoverSpecialItem>>>() {
                    @Override
                    public ResultInfo<List<CoverSpecialItem>> call(ResultInfo<List<CoverSpecialItem>> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }
}
