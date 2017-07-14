package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.IntegralReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMyIntegralModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：商品
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 04
 */
public class MyIntegralModel implements IMyIntegralModel {
    private Observable<IntegralReturn> integralReturnObservable;
    @Override
    public void loadData(int pageNo, int pageSize) {
        integralReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).getMyPoint(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        ,SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        pageNo,pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<IntegralReturn, IntegralReturn>() {
                    @Override
                    public IntegralReturn call(IntegralReturn integralReturn) {
                        return integralReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<IntegralReturn> subscriber) {
        integralReturnObservable.subscribe(subscriber);
    }
}
