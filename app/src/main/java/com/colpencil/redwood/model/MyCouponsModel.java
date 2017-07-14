package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMyCouponsModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 曾 凤
 * @Description: 我的积分
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class MyCouponsModel implements IMyCouponsModel {

    private Observable<Result> resultObservable;

    @Override
    public void getCount() {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).getCanUseCouponCount(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        ,SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result, Result>() {
                    @Override
                    public Result call(Result result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<Result> subscriber) {
        resultObservable.subscribe(subscriber);
    }
}
