package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.FirstComment;
import com.colpencil.redwood.bean.NodeReplyItem;
import com.colpencil.redwood.bean.NumReturn;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.INodeReplyModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class NodeReplyModel implements INodeReplyModel {
    private Observable<ResultInfo<List<NodeReplyItem>>> observable;
    private Observable<FirstComment> goodNums;
    private Observable<AddResult> addResultObservable;
    private Observable<ResultInfo<String>> likeObservable;
    @Override
    public void getNodeReply(Map<String, String> map ) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST).createApi(RedWoodApi.class).getNodeReply(map).subscribeOn(Schedulers.io()).map(new Func1<ResultInfo<List<NodeReplyItem>>, ResultInfo<List<NodeReplyItem>>>() {
            @Override
            public ResultInfo<List<NodeReplyItem>> call(ResultInfo<List<NodeReplyItem>> listResultInfo) {
                return listResultInfo;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subReply(Observer<ResultInfo<List<NodeReplyItem>>> observer) {
        observable.subscribe(observer);
    }
    @Override
    public void loadCommentsNum(Map<String,String> map) {
        goodNums = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCommentNum(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<FirstComment, FirstComment>() {
                    @Override
                    public FirstComment call(FirstComment result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCommentsNum(Observer<FirstComment> observer) {
        goodNums.subscribe(observer);
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
    public void addLike(Map<String, String> map) {
        likeObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getNoteLike(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<String>, ResultInfo<String>>() {
                    @Override
                    public ResultInfo<String> call(ResultInfo<String> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLike(Observer<ResultInfo<String>> observer) {
        likeObservable.subscribe(observer);
    }
}
