package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommentResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.model.SearchPostsModel;
import com.colpencil.redwood.model.imples.ISearchPostsModel;
import com.colpencil.redwood.view.impl.ISearchPostsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:搜索帖子
 * @Email DramaScript@outlook.com
 * @date 2016/8/5
 */
public class SearchPostsPresenter extends ColpencilPresenter<ISearchPostsView> {

    private ISearchPostsModel model;

    public SearchPostsPresenter() {
        model = new SearchPostsModel();
    }

    /**
     * 搜索帖子
     *
     * @param keyword
     * @param page
     * @param pageSize
     */
    public void loadPosts(String keyword, final int page, int pageSize) {
        model.loadPosts(keyword, page, pageSize);
        Observer<CommentResult> observer = new Observer<CommentResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommentResult result) {
                if (result.getCode() == 0) {
                    if (page == 1) {
                        mView.refresh(result.getResult());
                    } else {
                        mView.loadMore(result.getResult());
                    }
                } else {
                    mView.loadError();
                }
            }
        };
        model.sub(observer);
    }

    /**
     * 发表评论
     *
     * @param comContent
     * @param ote_id
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

    public void like(int ote_id) {
        model.like(ote_id);
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
     * 分享
     *
     * @param ote_id
     */
    public void share(int ote_id) {
        model.share(ote_id);
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
     * 记录分享
     *
     * @param ote_id
     */
    public void recordShare(int ote_id, String platform) {
        model.recordShare(ote_id, platform);
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
        model.subRecord(observer);
    }

}
