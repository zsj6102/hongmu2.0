package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.Margin;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.Settled;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMarginModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MarginModel implements IMarginModel {
    private Observable<ResultInfo<Margin>> observable;
   private Observable<ResultInfo<List<Settled>>> infoObservable;
    @Override
    public void loadInfo() {
        Map<String,String> map = new HashMap<>();
        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
        map.put("token",SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getMarginInfo(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<Margin>, ResultInfo<Margin>>() {
                    @Override
                    public ResultInfo<Margin> call(ResultInfo<Margin> marginResultInfo) {
                        return marginResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ResultInfo<Margin>> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void loadSettled() {
        infoObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadSettledList()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<Settled>>, ResultInfo<List<Settled>>>() {
                    @Override
                    public ResultInfo<List<Settled>> call(ResultInfo<List<Settled>> listResultInfo) {
                        return listResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSettled(Observer<ResultInfo<List<Settled>>> observer) {
        infoObservable.subscribe(observer);
    }
}
