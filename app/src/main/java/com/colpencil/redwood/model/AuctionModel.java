package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.JiashangItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IAuctionModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AuctionModel implements IAuctionModel {
    private Observable<ResultInfo<List<JiashangItem>>> observable;
    private Observable<AddResult> addResultObservable;
    private  Observable<AddResult> likeObservable;
    @Override
    public void loadData(Map<String, String> map) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getAllJiashang(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<JiashangItem>>, ResultInfo<List<JiashangItem>>>() {
                    @Override
                    public ResultInfo<List<JiashangItem>> call(ResultInfo<List<JiashangItem>> listResultInfo) {
                        return listResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subData(Observer<ResultInfo<List<JiashangItem>>> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void getAddCommentResult(Map<String, String> params) {
        addResultObservable = RetrofitManager.getInstance(1, App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getAdd(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AddResult, AddResult>() {
                    @Override
                    public AddResult call(AddResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAddResult(Observer<AddResult> observer) {
        addResultObservable.subscribe(observer);
    }


    @Override
    public void getLikeResult(Map<String, String> params) {
        likeObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getLike(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AddResult, AddResult>() {
                    @Override
                    public AddResult call(AddResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLike(Observer<AddResult> observer) {
        likeObservable.subscribe(observer);
    }
}
