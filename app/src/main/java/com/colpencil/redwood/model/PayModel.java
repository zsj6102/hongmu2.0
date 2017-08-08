package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.PayReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IPayModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class PayModel implements IPayModel {
    private Observable<PayReturn> payReturnObservable;
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
}
