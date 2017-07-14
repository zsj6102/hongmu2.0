package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IHelpModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:公告的model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class HelpModel implements IHelpModel {

    private Observable<AnnounceResult> observable;
    private Observable<AnnounceResult> help;

    @Override
    public void loadAboutus() {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadAboutUs()
                .subscribeOn(Schedulers.io())
                .map(new Func1<AnnounceResult, AnnounceResult>() {
                    @Override
                    public AnnounceResult call(AnnounceResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<AnnounceResult> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void loadHelp() {
        help = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadHelp()
                .subscribeOn(Schedulers.io())
                .map(new Func1<AnnounceResult, AnnounceResult>() {
                    @Override
                    public AnnounceResult call(AnnounceResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subhelp(Observer<AnnounceResult> observer) {
        help.subscribe(observer);
    }
}
