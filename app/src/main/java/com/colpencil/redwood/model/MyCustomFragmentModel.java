package com.colpencil.redwood.model;

import android.util.Log;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CustomReturn;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMyCustomFragmentModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：我的定制
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 04
 */
public class MyCustomFragmentModel implements IMyCustomFragmentModel {

    private Observable<CustomReturn> customReturnObservable;

    private Observable<PayKeyRetrun> payReturnObservable;

    private Observable<EntityResult<String>> deleteObservable;

    @Override
    public void loadData(int pageNo, int pageSize, int type) {
        customReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).myCustomMade(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"), SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        pageNo, pageSize, type)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CustomReturn, CustomReturn>() {
                    @Override
                    public CustomReturn call(CustomReturn customReturn) {
                        return customReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<CustomReturn> subscriber) {
        customReturnObservable.subscribe(subscriber);
    }

    @Override
    public void payInfor(int order_id) {
        Log.e("返回值", "payInfor");
        payReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).immediatePayment(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"), order_id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PayKeyRetrun, PayKeyRetrun>() {
                    @Override
                    public PayKeyRetrun call(PayKeyRetrun payKeyRetrun) {
                        return payKeyRetrun;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public void subPay(Observer<PayKeyRetrun> subscriber) {
        payReturnObservable.subscribe(subscriber);
    }

    @Override
    public void delete(int custom_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", custom_id + "");
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        deleteObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .deleteCustom(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subDelete(Observer<EntityResult<String>> observer) {
        deleteObservable.subscribe(observer);
    }
}
