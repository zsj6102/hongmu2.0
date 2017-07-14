package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AfterSalesCenterReturn;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IAfterSalesFragmentModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 04
 */
public class AfterSalesCenterFragmentModel implements IAfterSalesFragmentModel {

    private Observable<AfterSalesCenterReturn> afterSalesReturnObservable;

    private Observable<ResultCodeInt> resultObservable;

    @Override
    public void loadData(int pageNo, int pageSize,int operationType) {
        afterSalesReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).afterSale(  SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),pageNo, pageSize,operationType)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AfterSalesCenterReturn,AfterSalesCenterReturn>() {
                    @Override
                    public AfterSalesCenterReturn call(AfterSalesCenterReturn afterSalesCenterReturn) {
                        return afterSalesCenterReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 取消售后操作
     */
    @Override
    public void cancelAfterSale(HashMap<String,String> map) {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).cancelAfterSale(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                    @Override
                    public ResultCodeInt call(ResultCodeInt result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void getPhoneNum() {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).customerService()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                    @Override
                    public ResultCodeInt call(ResultCodeInt result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subResult(Observer<ResultCodeInt> subscriber) {
        resultObservable.subscribe(subscriber);
    }

    @Override
    public void sub(Observer<AfterSalesCenterReturn> subscriber) {
        afterSalesReturnObservable.subscribe(subscriber);
    }
}
