package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.CommentResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICircleLeftModel;
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
 * @Description:圈子的Model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CircleLeftModel implements ICircleLeftModel {

    private Observable<ListResult<CategoryVo>> observable;
    private Observable<ListResult<BannerVo>> banner;
    private Observable<ListResult<CategoryVo>> myTag;
    private Observable<CommentResult> postList;
    private Observable<EntityResult<String>> submit;
    private Observable<EntityResult<String>> like;

    /**
     * 获取标签分类
     */
    @Override
    public void loadTag() {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadAllCircleSort()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CategoryVo>, ListResult<CategoryVo>>() {
                    @Override
                    public ListResult<CategoryVo> call(ListResult<CategoryVo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ListResult<CategoryVo>> observer) {
        observable.subscribe(observer);
    }

    /**
     * 获取banner
     *
     * @param acid
     */
    @Override
    public void loadImage(String acid) {
        banner = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadhomebanner(acid)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<BannerVo>, ListResult<BannerVo>>() {
                    @Override
                    public ListResult<BannerVo> call(ListResult<BannerVo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subImage(Observer<ListResult<BannerVo>> observer) {
        banner.subscribe(observer);
    }

    @Override
    public void loadMyTag() {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        myTag = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCircleSort(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CategoryVo>, ListResult<CategoryVo>>() {
                    @Override
                    public ListResult<CategoryVo> call(ListResult<CategoryVo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subMyTag(Observer<ListResult<CategoryVo>> observer) {
        myTag.subscribe(observer);
    }

    @Override
    public void loadComment(String sec_id, int pageNo, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("sec_id", sec_id);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        postList = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadPosts(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CommentResult, CommentResult>() {
                    @Override
                    public CommentResult call(CommentResult commentResult) {
                        return commentResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subComment(Observer<CommentResult> observer) {
        postList.subscribe(observer);
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
}
