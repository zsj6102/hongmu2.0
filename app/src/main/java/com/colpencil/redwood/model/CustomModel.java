package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.CustomGoodResult;
import com.colpencil.redwood.bean.result.CustomResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICustomModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述： 官方推荐定制
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/7 17 33
 */
public class CustomModel implements ICustomModel {

    private Observable<CustomResult> listObservable;
    private Observable<CustomGoodResult> goodObservable;

    @Override
    public void loadData(int official_id) {
        listObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).loadCustomUrl(official_id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CustomResult, CustomResult>() {
                    @Override
                    public CustomResult call(CustomResult customResult) {
                        return customResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<CustomResult> subscriber) {
        listObservable.subscribe(subscriber);
    }

    @Override
    public void loadCustomGoods(int official_id) {
        goodObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).loadCustomGoods(official_id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CustomGoodResult, CustomGoodResult>() {
                    @Override
                    public CustomGoodResult call(CustomGoodResult customGoodResult) {
                        return customGoodResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCustom(Observer<CustomGoodResult> observer) {
        goodObservable.subscribe(observer);
    }
}
