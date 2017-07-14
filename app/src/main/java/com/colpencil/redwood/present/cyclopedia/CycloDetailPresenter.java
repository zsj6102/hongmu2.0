package com.colpencil.redwood.present.cyclopedia;

import com.colpencil.redwood.bean.CyclopediaContent;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.GoodBusMsg;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.model.CycloDetailModel;
import com.colpencil.redwood.model.imples.ICycloDetailModel;
import com.colpencil.redwood.view.impl.ICycloDetailView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:百科详情
 * @Email DramaScript@outlook.com
 * @date 2016/8/6
 */
public class CycloDetailPresenter extends ColpencilPresenter<ICycloDetailView> {

    private ICycloDetailModel model;

    public CycloDetailPresenter() {
        model = new CycloDetailModel();
    }

    /**
     * 获取百科url
     *
     * @param cat_id
     * @param article_id
     */
    public void loadUrl(String cat_id, String article_id) {
        model.loadUrl(cat_id, article_id);
        Observer<AnnounceResult> observer = new Observer<AnnounceResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AnnounceResult result) {
                mView.loadUrl(result.getUrl());
            }
        };
        model.subUrl(observer);
    }

    /**
     * 获取百科的评论
     */
    public void loadComments(String ote_id, int page, int pageSize, String type) {
        model.loadComments(ote_id, page, pageSize, type);
        Observer<PCommentResult> observer = new Observer<PCommentResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PCommentResult result) {
                mView.loadComments(result);
            }
        };
        model.subComments(observer);
    }

    /**
     * 发布评论
     */
    public void submitComment(String article_id, String acomment, String type) {
        model.submitComments(article_id, acomment, type);
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
     * 查看收藏状态
     */
    public void viewState(String article_id, int type) {
        model.checkState(article_id, type);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.checkStateResult(result);
            }
        };
        model.subCheck(observer);
    }

    /**
     * 收藏百科
     */
    public void keepCyclopedia(String article_id, int type) {
        model.keepCyclopedia(article_id, type);
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
     * 添加浏览记录
     */
    public void browerGood(int catid, String articleid) {
        model.browerComment(catid, articleid);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {

            }
        };
        model.subBrower(observer);
    }

    /**
     * 获取分享链接
     *
     * @param ote_id
     */
    public void loadShareUrl(int ote_id, String article_id) {
        model.loadShare(ote_id, article_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
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
    public void addUpSharenum(int type, String platform, String id) {
        model.addUpShare(type, platform, id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                if (result.getCode() == 1) {
                    GoodBusMsg msg = new GoodBusMsg();
                    msg.setType("refresh");
                    RxBus.get().post(StringConfig.GOODSBUS, msg);
                }
            }
        };
        model.subAddup(observer);
    }

    /**
     * 点赞
     *
     * @param type
     * @param id
     */
    public void like(int type, String id) {
        model.like(type, id);
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

    public void likeState(int type, String id) {
        model.likeState(type, id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.likeStateResult(result);
            }
        };
        model.subLikeState(observer);
    }

    public void loadContent(String article_id) {
        model.loadContent(article_id);
        Observer<EntityResult<CyclopediaContent>> observer = new Observer<EntityResult<CyclopediaContent>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<CyclopediaContent> result) {
                if (result.getCode() == 1) {
                }
            }
        };
        model.subContent(observer);
    }
}
