package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ArticalItem;
import com.colpencil.redwood.bean.JiashangItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class EncycloModel implements IEncycloModel {
    private Observable<ResultInfo<List<ArticalItem>>> observable;
    @Override
    public void getArticalList(Map<String, String> map) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getArticalList(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<ArticalItem>>, ResultInfo<List<ArticalItem>>>() {
                    @Override
                    public ResultInfo<List<ArticalItem>> call(ResultInfo<List<ArticalItem>> listResultInfo) {
                        return listResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subNormal(Observer<ResultInfo<List<ArticalItem>>> observer) {
        observable.subscribe(observer);
    }
}
