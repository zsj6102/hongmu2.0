package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CodeBean;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICodeModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CodeModel implements ICodeModel {

    private Observable<EntityResult<CodeBean>> observable;

    @Override
    public void loadUrl() {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCodeUrl()
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<CodeBean>, EntityResult<CodeBean>>() {
                    @Override
                    public EntityResult<CodeBean> call(EntityResult<CodeBean> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<EntityResult<CodeBean>> observer) {
        observable.subscribe(observer);
    }
}
