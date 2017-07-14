package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.AllSpecialResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IAllSpecialItemModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class AllSpecialItemModel implements IAllSpecialItemModel {
    private Observable<AllSpecialResult> observable;
    @Override
    public void getSpecial(HashMap<String, Integer> intparams, HashMap<String, RequestBody> strparams) {
        observable= RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getSpecial(strparams,intparams)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AllSpecialResult, AllSpecialResult>() {
                    @Override
                    public AllSpecialResult call(AllSpecialResult allSpecialResult) {
                        return allSpecialResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGetSpecial(Observer<AllSpecialResult> observer) {
        observable.subscribe(observer);
    }
}
