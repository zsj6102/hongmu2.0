package com.colpencil.redwood.model;

import android.util.Log;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.PayForReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.bean.result.MemberCouponResult;
import com.colpencil.redwood.bean.result.OrderPayInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IPaymentModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;

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
public class PaymentModel implements IPaymentModel {

    private Observable<PayForReturn> payForReturnObservable;

    private Observable<PayKeyRetrun> payReturnObservable;

    private Observable<MemberCouponResult> couponObservable;


    @Override
    public void loadPayFor(String cartIds) {
        Log.e("返回值", "结算：" + cartIds);
        payForReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).orderPay(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        cartIds)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PayForReturn, PayForReturn>() {
                    @Override
                    public PayForReturn call(PayForReturn payForReturn) {
                        return payForReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void loadOtherPayFor(HashMap<String, String> map) {
        payForReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).otherOrderPay(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PayForReturn, PayForReturn>() {
                    @Override
                    public PayForReturn call(PayForReturn payForReturn) {
                        return payForReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<PayForReturn> subscriber) {
        payForReturnObservable.subscribe(subscriber);
    }

    @Override
    public void payInfor(HashMap<String, String> map) {
        payReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).orderCreate(map)
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
    public void loadCoupon(String cartIds) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("cart_ids", cartIds);
        couponObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCoupon(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<MemberCouponResult, MemberCouponResult>() {
                    @Override
                    public MemberCouponResult call(MemberCouponResult result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCoupon(Observer<MemberCouponResult> observer) {
        couponObservable.subscribe(observer);
    }
}
