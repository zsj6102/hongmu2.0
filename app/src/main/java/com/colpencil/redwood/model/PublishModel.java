package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.SizeColorInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IPublishModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class PublishModel implements IPublishModel{
    private Observable<CatListBean> catObservable;
    private Observable<SizeColorInfo> sizeColorInfoObservable;
    @Override
    public void loadSize(int id) {
        sizeColorInfoObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getSize(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<SizeColorInfo, SizeColorInfo>() {
                    @Override
                    public SizeColorInfo call(SizeColorInfo info) {
                        return info;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSize(Subscriber<SizeColorInfo> subscriber) {
        sizeColorInfoObservable.subscribe(subscriber);
    }

    @Override
    public void loadCatList(int id) {
        catObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
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
}
