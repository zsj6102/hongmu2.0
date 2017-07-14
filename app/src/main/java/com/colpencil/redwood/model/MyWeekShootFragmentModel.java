package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MyWeekShootReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMyWeekShootFragmentModel;
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
public class MyWeekShootFragmentModel implements IMyWeekShootFragmentModel {

    private Observable<MyWeekShootReturn> listObservable;
    private Observable<EntityResult<String>> deleteObservable;
    private Observable<PayKeyRetrun> payReturnObservable;

    @Override
    public void loadData(int pageNo, int pageSize, int type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("page", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("operationType", type + "");
        listObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .myweekpat(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<MyWeekShootReturn, MyWeekShootReturn>() {
                    @Override
                    public MyWeekShootReturn call(MyWeekShootReturn myWeekShootReturn) {
                        return myWeekShootReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<MyWeekShootReturn> subscriber) {
        listObservable.subscribe(subscriber);
    }

    @Override
    public void deleteById(int id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("id", id + "");
        deleteObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .deleteMyWeek(params)
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

    @Override
    public void payInfor(int ote_id) {
        payReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).immediatePayment(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"), ote_id)
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
    public void subPay(Observer<PayKeyRetrun> observer) {
        payReturnObservable.subscribe(observer);
    }
}
