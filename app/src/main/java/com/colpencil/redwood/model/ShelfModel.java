package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.PlainRack;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IShelfModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ShelfModel implements IShelfModel {
    private Observable<ResultInfo<List<PlainRack>>> observable;
    @Override
    public void getNormalShelf(Map<String, String> map) {
            observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                    .createApi(RedWoodApi.class)
                    .getPlainRack(map)
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<ResultInfo<List<PlainRack>>, ResultInfo<List<PlainRack>>>() {
                        @Override
                        public ResultInfo<List<PlainRack>> call(ResultInfo<List<PlainRack>> listResultInfo) {
                            return listResultInfo;
                        }
                    }).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void subNormal(Observer<ResultInfo<List<PlainRack>>> observer) {
            observable.subscribe(observer);
    }
}
