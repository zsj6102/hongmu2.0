package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.OrderDetailReturn;

import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IOrderDetailModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class OrderDetailModel implements IOrderDetailModel {

    private Observable<OrderDetailReturn> orderDetailsReturnObservable;
    @Override
    public void loadData(int order_id) {
//        orderDetailsReturnObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
//                .createApi(RedWoodApi.class)
//                .getOrderDetail(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
//                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
//                        order_id)
//                .subscribeOn(Schedulers.io())
//                .map(new Func1<OrderDetailReturn, OrderDetailReturn>() {
//                    @Override
//                    public OrderDetailReturn call(OrderDetailReturn orderDetailReturn) {
//                        return orderDetailReturn;
//                    }
//                }).observeOn(AndroidSchedulers.mainThread());


    }

    @Override
    public void sub(Observer<OrderDetailReturn> subscriber) {
//        orderDetailsReturnObservable.subscribe(subscriber);
    }
}
