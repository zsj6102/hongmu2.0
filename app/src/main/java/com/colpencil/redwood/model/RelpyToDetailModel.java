package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.ReplyToItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IReplyDetailModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RelpyToDetailModel implements IReplyDetailModel {
    private Observable<ResultInfo<List<ReplyToItem>>>  observable;
    private Observable<AddResult> addResultObservable;
    @Override
    public void getDetail(Map<String, String> map) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getReplyDetail(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<List<ReplyToItem>>, ResultInfo<List<ReplyToItem>>>() {
                    @Override
                    public ResultInfo<List<ReplyToItem>> call(ResultInfo<List<ReplyToItem>> listResultInfo) {
                        return listResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subDetail(Observer<ResultInfo<List<ReplyToItem>>> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void getAddReply(Map<String, String> params) {
        addResultObservable = RetrofitManager.getInstance(1, App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getReplyAdd(params)
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
}
