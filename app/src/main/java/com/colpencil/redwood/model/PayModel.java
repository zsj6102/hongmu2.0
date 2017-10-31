package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.PayReturn;
import com.colpencil.redwood.bean.PayType;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IPayModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.colpencil.redwood.holder.HolderFactory.map;

public class PayModel implements IPayModel {
    private Observable<PayReturn> payReturnObservable;
    private Observable<ResultInfo<List<PayType>>> observable;
    private Observable<PayReturn> centerObservable;
    private Observable<PayReturn> marginObservable;
    private Observable<PayReturn> settledObservable;
    @Override
    public void payInfor(HashMap<String, RequestBody> map) {
        payReturnObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getPayReturn(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PayReturn, PayReturn>() {
                    @Override
                    public PayReturn call(PayReturn payReturn) {
                        return payReturn;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subPay(Observer<PayReturn> subscriber) {
        payReturnObservable.subscribe(subscriber);
    }

    @Override
    public void payType() {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadPayType()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<PayType>>, ResultInfo<List<PayType>>>() {
                    @Override
                    public ResultInfo<List<PayType>> call(ResultInfo<List<PayType>> listResultInfo) {
                        return listResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subType(Observer<ResultInfo<List<PayType>>> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void centerPay(Map<String, String> map) {
        centerObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCenterPay(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PayReturn, PayReturn>() {
                    @Override
                    public PayReturn call(PayReturn payReturn) {
                        return payReturn;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCenter(Observer<PayReturn> subscriber) {
        centerObservable.subscribe(subscriber);
    }

    @Override
    public void marginPay(Map<String, String> map) {
        marginObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getMarginPay(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PayReturn, PayReturn>() {
                    @Override
                    public PayReturn call(PayReturn payReturn) {
                        return payReturn;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subMargin(Observer<PayReturn> subscriber) {
        marginObservable.subscribe(subscriber);
    }

    @Override
    public void subSettled(Observer<PayReturn> observer) {
        settledObservable.subscribe(observer);
    }

    @Override
    public void settledPay(Map<String, String> map) {
        settledObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getSettledPay(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PayReturn, PayReturn>() {
                    @Override
                    public PayReturn call(PayReturn payReturn) {
                        return payReturn;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }
}
