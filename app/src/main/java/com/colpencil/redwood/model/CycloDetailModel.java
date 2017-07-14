package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CyclopediaContent;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICycloDetailModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:百科详情model
 * @Email DramaScript@outlook.com
 * @date 2016/8/8
 */
public class CycloDetailModel implements ICycloDetailModel {

    private Observable<AnnounceResult> announce;
    private Observable<PCommentResult> pcomment;
    private Observable<EntityResult<String>> submit;
    private Observable<EntityResult<String>> viewstate;
    private Observable<EntityResult<String>> keep;
    private Observable<EntityResult<String>> brower;
    private Observable<EntityResult<String>> share;
    private Observable<EntityResult<String>> addup;
    private Observable<EntityResult<String>> like;
    private Observable<EntityResult<String>> likeState;
    private Observable<EntityResult<CyclopediaContent>> content;

    @Override
    public void loadUrl(String cat_id, String article_id) {
        announce = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCycloUrl(cat_id, article_id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AnnounceResult, AnnounceResult>() {
                    @Override
                    public AnnounceResult call(AnnounceResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subUrl(Observer<AnnounceResult> observer) {
        announce.subscribe(observer);
    }

    /**
     * 评论
     *
     * @param ote_id
     * @param page
     * @param pageSize
     */
    @Override
    public void loadComments(String ote_id, int page, int pageSize, String type) {
        pcomment = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCycloComments(ote_id, page, pageSize, type)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PCommentResult, PCommentResult>() {
                    @Override
                    public PCommentResult call(PCommentResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subComments(Observer<PCommentResult> observer) {
        pcomment.subscribe(observer);
    }

    @Override
    public void submitComments(String article_id, String acomment, String type) {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("article_id", RequestBody.create(null, article_id));
        params.put("acomment", RequestBody.create(null, acomment));
        params.put("member_id", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""));
        params.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")));
        params.put("commenttype", RequestBody.create(null, type));
        submit = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .submitCycloComment(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSubmit(Observer<EntityResult<String>> observer) {
        submit.subscribe(observer);
    }

    @Override
    public void checkState(String article_id, int type) {
        viewstate = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .checkViewState(article_id,
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"), type)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCheck(Observer<EntityResult<String>> observer) {
        viewstate.subscribe(observer);
    }

    @Override
    public void keepCyclopedia(String article_id, int type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", article_id);
        params.put("type", type + "");
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        keep = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .collect(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subKeep(Observer<EntityResult<String>> observer) {
        keep.subscribe(observer);
    }

    /**
     * 添加收藏记录
     */
    @Override
    public void browerComment(int catid, String articleid) {
        brower = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .BrowerCyclopedia(catid, articleid,
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subBrower(Observer<EntityResult<String>> observer) {
        brower.subscribe(observer);
    }

    @Override
    public void loadShare(int cat_id, String article_id) {
        share = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .cycloShare(cat_id, article_id,
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subShare(Observer<EntityResult<String>> observer) {
        share.subscribe(observer);
    }

    @Override
    public void addUpShare(int type, String platform, String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("platform", platform);
        params.put("id", id);
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        addup = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .addUpShare(params)
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
    public void subAddup(Observer<EntityResult<String>> observer) {
        addup.subscribe(observer);
    }

    @Override
    public void like(int type, String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("type", type + "");
        params.put("id", id);
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
    public void likeState(int type, String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("type", type + "");
        params.put("id", id);
        likeState = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadLikeState(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLikeState(Observer<EntityResult<String>> observer) {
        likeState.subscribe(observer);
    }

    @Override
    public void loadContent(String id) {
        content = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCyclopediaContent(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<CyclopediaContent>, EntityResult<CyclopediaContent>>() {
                    @Override
                    public EntityResult<CyclopediaContent> call(EntityResult<CyclopediaContent> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subContent(Observer<EntityResult<CyclopediaContent>> observer) {
        content.subscribe(observer);
    }
}
