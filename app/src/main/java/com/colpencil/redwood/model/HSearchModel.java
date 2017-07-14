package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CatReturnData;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IHSearchModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/13 17 06
 */
public class HSearchModel implements IHSearchModel {

    private Observable<CatReturnData> catObservable;

    @Override
    public void loadListViewData() {
        catObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).getCatList()
                .subscribeOn(Schedulers.io())
                .map(new Func1<CatReturnData, CatReturnData>() {
                    @Override
                    public CatReturnData call(CatReturnData catReturnData) {
                        return catReturnData;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subListView(Observer<CatReturnData> subscriber) {
        catObservable.subscribe(subscriber);
    }
}
