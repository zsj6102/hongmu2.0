package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.StatisticResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICircleRightModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:我的帖子Model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CircleRightModel implements ICircleRightModel {

    private Observable<StatisticResult> observable;

    @Override
    public void loadStatic(String member_id, String token) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadStatic(member_id, token)
                .subscribeOn(Schedulers.io())
                .map(new Func1<StatisticResult, StatisticResult>() {
                    @Override
                    public StatisticResult call(StatisticResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<StatisticResult> observer) {
        observable.subscribe(observer);
    }
}
