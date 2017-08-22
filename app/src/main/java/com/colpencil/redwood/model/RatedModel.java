package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.*;
import com.colpencil.redwood.bean.RatedItem;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IRatedModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RatedModel implements IRatedModel {
    private Observable<ResultInfo<List<RatedItem>>> observable;



    @Override
    public void getRatedList(Map<String, String> map) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getRatedList(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<RatedItem>>, ResultInfo<List<RatedItem>>>() {
                    @Override
                    public ResultInfo<List<RatedItem>> call(ResultInfo<List<RatedItem>> listResultInfo) {
                        return listResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subNormal(Observer<ResultInfo<List<RatedItem>>> observer) {
        observable.subscribe(observer);
    }
}
