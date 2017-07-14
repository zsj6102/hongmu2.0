package com.colpencil.redwood.bean;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMyCollectionModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 我的收藏
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/8
 */
public class MyCollectionModel implements IMyCollectionModel{
    private Observable<Result> resultObservable;
    @Override
    public void removeAllCollection() {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).clearMyFavorite(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        ,SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result, Result>() {
                    @Override
                    public Result call(Result result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<Result> subscriber) {
        resultObservable.subscribe(subscriber);
    }
}
