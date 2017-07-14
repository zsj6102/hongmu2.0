package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.OrderDetailsReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IOrderDetailsModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

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
public class OrderDetailsModel implements IOrderDetailsModel {

    private Observable<OrderDetailsReturn> orderDetailsReturnObservable;

    private Observable<ResultCodeInt> resultObservable;

    private Observable<PayKeyRetrun> payReturnObservable;

    @Override
    public void loadData(int order_id) {
        orderDetailsReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .orderDetail(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        order_id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<OrderDetailsReturn, OrderDetailsReturn>() {
                    @Override
                    public OrderDetailsReturn call(OrderDetailsReturn orderDetailsReturn) {
                        return orderDetailsReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void cancelOrderById(String order_id) {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).cancelOrder(
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        order_id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                    @Override
                    public ResultCodeInt call(ResultCodeInt result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 确认收货
     * @param order_id
     */
    @Override
    public void confirmRecept(int order_id) {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).confirmRecept(
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        order_id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                    @Override
                    public ResultCodeInt call(ResultCodeInt result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 取消退款申请操作
     * @param order_id
     * @param return_order_id
     */
    @Override
    public void cancelApplyForRefund(int order_id, int return_order_id) {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).cancelApplyForRefund(
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        order_id, return_order_id)
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
    public void sub(Observer<OrderDetailsReturn> subscriber) {
        orderDetailsReturnObservable.subscribe(subscriber);
    }
    @Override
    public void payInfor(int order_id) {
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
    public void remindDelivery(String sn) {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).remindDelivery(
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        sn)
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
    public void subPay(Observer<PayKeyRetrun> subscriber) {
        payReturnObservable.subscribe(subscriber);
    }
}
