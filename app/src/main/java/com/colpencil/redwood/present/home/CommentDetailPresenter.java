package com.colpencil.redwood.present.home;

import android.content.Context;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.colpencil.redwood.bean.result.PostsResult;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.model.CommentDetailModel;
import com.colpencil.redwood.model.imples.ICommentDetailModel;
import com.colpencil.redwood.view.impl.ICommentDetailView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:帖子详情的Presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CommentDetailPresenter extends ColpencilPresenter<ICommentDetailView> {

    private ICommentDetailModel model;

    public CommentDetailPresenter() {
        model = new CommentDetailModel();
    }

    /**
     * 帖子详情
     *
     * @param ote_id
     */
    public void loadImageList(String ote_id) {
        model.loadPosts(ote_id);
        Observer<PostsResult> observer = new Observer<PostsResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PostsResult postsResult) {
                mView.loadPosts(postsResult);
            }
        };
        model.subPosts(observer);
    }

    /**
     * 帖子的评论列表
     */
    public void loadComments(String ote_id, int page, int pageSize) {
        model.loadComments(ote_id, page, pageSize);
        Observer<PCommentResult> observer = new Observer<PCommentResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PCommentResult pCommentResult) {
                if (pCommentResult.getCode().equals("1")) {
                    mView.loadComment(pCommentResult);
                }
            }
        };
        model.subComments(observer);
    }

    /**
     * 提交评论
     */
    public void submitComments(String comContent, int ote_id) {
        model.submitComments(comContent, ote_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.submitResult(result);
            }
        };
        model.subSubmit(observer);
    }

    /**
     * 收藏帖子
     */
    public void keepComments(int ote_id) {
        model.keepComments(ote_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.keepResult(result);
            }
        };
        model.subKeep(observer);
    }

    /**
     * 点赞
     */
    public void likeComments(int ote_id) {
        model.likeComments(ote_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.likeResult(result);
            }
        };
        model.subLike(observer);
    }

    /**
     * 获取帖子收藏状态
     */
    public void loadKeepState(int ote_id) {
        model.loadKeepState(ote_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.stateResult(result);
            }
        };
        model.subLoad(observer);
    }

    /**
     * 点赞状态
     */
    public void loadLikeState(int ote_id) {
        model.loadLikeState(ote_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.likeState(result);
            }
        };
        model.subLikeState(observer);
    }

    /**
     * 添加浏览记录
     *
     * @param ote_id
     */
    public void browerGood(int ote_id) {
        model.browerComment(ote_id);
        Observer<CommonResult> observer = new Observer<CommonResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommonResult result) {

            }
        };
        model.subBrower(observer);
    }

    /**
     * 获取缓存的帖子内容
     *
     * @param ote_id
     * @param context
     */
    public void loadLocal(final int ote_id, final Context context) {
        Observable<PostsResult> observable = Observable.create(new Observable.OnSubscribe<PostsResult>() {
            @Override
            public void call(Subscriber<? super PostsResult> subscriber) {
                PostsResult result = DaoUtils.loadPostsById(ote_id, context);
                subscriber.onNext(result);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<PostsResult> observer = new Observer<PostsResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PostsResult postsResult) {
                mView.loadPosts(postsResult);
            }
        };
        observable.subscribe(observer);
    }

    /**
     * 获取分享链接
     *
     * @param ote_id
     */
    public void loadShareUrl(int ote_id) {
        model.loadShare(ote_id);
        Observer<CommonResult> observer = new Observer<CommonResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommonResult result) {
                mView.shareResult(result);
            }
        };
        model.subShare(observer);
    }

    /**
     * 统计分享
     *
     * @param type
     * @param platform
     * @param id
     */
    public void addUpSharenum(int type, String platform, int id) {
        model.addUpShare(type, platform, id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> stringEntityResult) {

            }
        };
        model.subAddup(observer);
    }

}
