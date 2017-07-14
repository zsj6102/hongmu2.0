package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.MessageReturn;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMyMessageModel;
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
public class MyMessageModel implements IMyMessageModel {

    private Observable<MessageReturn> messageReturnObservable;

    private Observable<Result> resultObservable;
    @Override
    public void removeAllMsg() {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).deleteMyMessage(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
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
    public void removeById(int msgId) {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).deleteMyMessageById(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        ,SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),msgId)
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
    public void subMsg(Observer<Result> subscriber) {
        resultObservable.subscribe(subscriber);
    }
    @Override
    public void loadData(int pageNo, int pageSize) {
        messageReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).getMyMessage(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        ,SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        pageNo,pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<MessageReturn, MessageReturn>() {
                    @Override
                    public MessageReturn call(MessageReturn messageReturn) {
                        return messageReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<MessageReturn> subscriber) {
        messageReturnObservable.subscribe(subscriber);
    }
}
