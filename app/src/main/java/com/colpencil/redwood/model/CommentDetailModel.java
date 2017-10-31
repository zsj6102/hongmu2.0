package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.NodeReplyItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.colpencil.redwood.bean.result.PostsResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICommentDetailModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:我的评论的Model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CommentDetailModel implements ICommentDetailModel {

    private Observable<PostsResult> imgobser;
    private Observable<PCommentResult> comobser;
    private Observable<EntityResult<String>> submit;
    private Observable<EntityResult<String>> keep;
    private Observable<EntityResult<String>> like;
    private Observable<EntityResult<String>> state;
    private Observable<CommonResult> brower;
    private Observable<CommonResult> share;
    private Observable<EntityResult<String>> addup;
    private Observable<EntityResult<String>> likeState;
    private Observable<ResultInfo<String>> likeObservable;
    private Observable<ResultInfo<List<NodeReplyItem>>> observable;
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
    /**
     * 获取帖子详情
     *
     * @param ote_id
     */
    @Override
    public void loadPosts(String ote_id) {
        imgobser = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadPosts(ote_id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PostsResult, PostsResult>() {
                    @Override
                    public PostsResult call(PostsResult postsResult) {
                        return postsResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subPosts(Observer<PostsResult> imgobserver) {
        imgobser.subscribe(imgobserver);
    }

    /**
     * 获取评论
     */
    @Override
    public void loadComments(String ote_id, int page, int pageSize) {
        comobser = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCommentList(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),ote_id, page, pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PCommentResult, PCommentResult>() {
                    @Override
                    public PCommentResult call(PCommentResult pCommentResult) {
                        return pCommentResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subComments(Observer<PCommentResult> comobserver) {
        comobser.subscribe(comobserver);
    }

    /**
     * 提交评论
     *
     * @param comContent
     * @param ote_id
     */
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

    /**
     * 收藏帖子
     *
     * @param ote_id
     */
    @Override
    public void keepComments(int ote_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", ote_id + "");
        params.put("type", Constants.KEEP_POSTS + "");
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
     * 点赞帖子
     *
     * @param ote_id
     */
    @Override
    public void likeComments(int ote_id) {
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

    /**
     * 获取帖子的收藏点赞状态
     *
     * @param ote_id
     */
    @Override
    public void loadKeepState(int ote_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("type", "3");
        params.put("id", ote_id + "");
        state = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadState(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLoad(Observer<EntityResult<String>> observer) {
        state.subscribe(observer);
    }

    /**
     * 添加帖子浏览记录
     *
     * @param ote_id
     */
    @Override
    public void browerComment(int ote_id) {
        brower = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .BrowerPosts(ote_id + "",
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
    public void subBrower(Observer<CommonResult> observer) {
        brower.subscribe(observer);
    }

    @Override
    public void loadShare(int ote_id) {
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
    public void addUpShare(int type, String platform, int id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("platform", platform);
        params.put("id", id + "");
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
    public void loadLikeState(int ote_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", ote_id + "");
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("type", "1");
        likeState = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadLikeState(params)
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
    public void subLikeState(Observer<EntityResult<String>> observer) {
        likeState.subscribe(observer);
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
    public void subLikeCom(Observer<ResultInfo<String>> observer) {
        likeObservable.subscribe(observer);
    }
}
