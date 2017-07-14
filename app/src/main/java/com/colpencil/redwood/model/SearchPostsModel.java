package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommentResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ISearchPostsModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:搜索帖子的Model
 * @Email DramaScript@outlook.com
 * @date 2016/8/4
 */
public class SearchPostsModel implements ISearchPostsModel {

    private Observable<CommentResult> observable;
    private Observable<EntityResult<String>> submit;
    private Observable<EntityResult<String>> like;
    private Observable<CommonResult> share;
    private Observable<EntityResult<String>> record;

    @Override
    public void loadPosts(String keyword, int page, int pageSize) {
        observable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).loadPostsSearch(RequestBody.create(null, keyword), page, pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CommentResult, CommentResult>() {
                    @Override
                    public CommentResult call(CommentResult commentResult) {
                        return commentResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<CommentResult> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void submitComments(String comContent, int ote_id) {
        submit = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .submitComments(RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""),
                        RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")),
                        RequestBody.create(null, comContent),
                        RequestBody.create(null, ote_id + ""))
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSubmit(Observer<EntityResult<String>> observer) {
        submit.subscribe(observer);
    }

    @Override
    public void like(int ote_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("type", "1");
        params.put("id", ote_id + "");
        like = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .like(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLike(Observer<EntityResult<String>> observer) {
        like.subscribe(observer);
    }

    @Override
    public void share(int ote_id) {
        share = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .postShare(ote_id,
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<CommonResult, CommonResult>() {
                    @Override
                    public CommonResult call(CommonResult commonResult) {
                        return commonResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subShare(Observer<CommonResult> observer) {
        share.subscribe(observer);
    }

    @Override
    public void recordShare(int ote_id, String platform) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", 1 + "");
        params.put("platform", platform);
        params.put("id", ote_id + "");
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        record = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .addUpShare(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRecord(Observer<EntityResult<String>> observer) {
        record.subscribe(observer);
    }
}
