package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.OrderCenterReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IOrderCenterFragmentModel;
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
public class OrderCenterFragmentModel implements IOrderCenterFragmentModel {

    private Observable<OrderCenterReturn> orderCenterReturnObservable;

    private Observable<ResultCodeInt> resultObservable;

    private Observable<PayKeyRetrun> payReturnObservable;

    private Observable<EntityResult<String>> deleteObservable;

    @Override
    public void loadData(int pageNo, int pageSize, int optType) {
        orderCenterReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).orderList(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"), optType,
                        pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<OrderCenterReturn, OrderCenterReturn>() {
                    @Override
                    public OrderCenterReturn call(OrderCenterReturn orderCenterReturn) {
                        return orderCenterReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<OrderCenterReturn> subscriber) {
        orderCenterReturnObservable.subscribe(subscriber);
    }

    /**
     * 取消订单操作
     */
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
     * 进行确认收货操作
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
     * 取消退款操作
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

    @Override
    public void deleteById(int order_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("order_id", order_id + "");
        deleteObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .deleteOrderById(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subDelete(Observer<EntityResult<String>> observer) {
        deleteObservable.subscribe(observer);
    }
}
