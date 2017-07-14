package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddressReturn;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IReceiptAddressModel;
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
public class ReceiptAddressModel implements IReceiptAddressModel {

    private Observable<AddressReturn> addressReturnObservable;

    private Observable<Result> returnObservable;

    @Override
    public void loadData() {
        addressReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).listAddress(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<AddressReturn, AddressReturn>() {
                    @Override
                    public AddressReturn call(AddressReturn addressReturn) {
                        return addressReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<AddressReturn> subscriber) {
        addressReturnObservable.subscribe(subscriber);
    }

    @Override
    public void deleteById(int addrId) {
        returnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).deleteAddress(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"), addrId)
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
    public void subResult(Observer<Result> subscriber) {
        returnObservable.subscribe(subscriber);
    }
}
