package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.SpecialIntroduceResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ISpecialIntroduceModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SpecialIntroduceModel implements ISpecialIntroduceModel {
    private Observable<SpecialIntroduceResult> observable;
    @Override
    public void getSpecialIntroduce(int id) {
        observable= RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getSpecialIntroduce(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<SpecialIntroduceResult, SpecialIntroduceResult>() {
                    @Override
                    public SpecialIntroduceResult call(SpecialIntroduceResult specialIntroduceResult) {
                        return specialIntroduceResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void subGetSpecialIntroduce(Observer<SpecialIntroduceResult> observer) {
        observable.subscribe(observer);
    }
}
