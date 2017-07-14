package com.colpencil.redwood.model;

import android.util.Log;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CouponsResult;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IGetCouponsModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 04
 */
public class GetCouponsModel implements IGetCouponsModel {
    private Observable<CouponsResult> couponsReturnObservable;

    private Observable<Result> resultObservable;
    @Override
    public void loadData(int pageNo, int pageSize) {
        couponsReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).getCanChangeCouponList(
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        ,SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        pageNo,pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CouponsResult, CouponsResult>() {
                    @Override
                    public CouponsResult call(CouponsResult couponsReturn) {
                        return couponsReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<CouponsResult> subscriber) {
        couponsReturnObservable.subscribe(subscriber);
    }

    @Override
    public void points(int point, String cpns_sn, int cpns_id) {
        Log.e("返回值","point:"+point+"cpns_sn:"+cpns_sn+"cpns_id:"+cpns_id);
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).getCouponByPoint(
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        ,SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        point,cpns_sn,cpns_id)
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
    public void subPoints(Observer<Result> subscriber) {
        resultObservable.subscribe(subscriber);
    }
}
