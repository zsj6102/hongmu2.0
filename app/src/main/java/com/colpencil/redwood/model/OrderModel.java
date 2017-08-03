package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.OrderPayInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IOrderModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class OrderModel implements IOrderModel {
    private Observable<OrderPayInfo> orderObserverable;

    @Override
    public void loadNewOrder(Map<String, String> map) {
        orderObserverable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getOrderPay(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<OrderPayInfo, OrderPayInfo>() {
                    @Override
                    public OrderPayInfo call(OrderPayInfo orderPayInfo) {
                        return orderPayInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subOrder(Observer<OrderPayInfo> observer) {
        orderObserverable.subscribe(observer);
    }

}
