package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.CustomDetailResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICustomDetailModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CustomDetailModel implements ICustomDetailModel {

    private Observable<CustomDetailResult> observable;

    @Override
    public void loadContent(int id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id + "");
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        observable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCustomDetail(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CustomDetailResult, CustomDetailResult>() {
                    @Override
                    public CustomDetailResult call(CustomDetailResult result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subContent(Observer<CustomDetailResult> observer) {
        observable.subscribe(observer);
    }
}
