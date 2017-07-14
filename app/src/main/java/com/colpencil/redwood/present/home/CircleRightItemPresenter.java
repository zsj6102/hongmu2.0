package com.colpencil.redwood.present.home;

import android.content.Context;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MyComment;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.MyCommentResult;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.model.CircleLeftItemModel;
import com.colpencil.redwood.model.imples.ICircleLeftItemModel;
import com.colpencil.redwood.view.impl.ICircleRightItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:我的列表的presenter
 * @Email DramaScript@outlook.com
 * @date 2016/8/1
 */
public class CircleRightItemPresenter extends ColpencilPresenter<ICircleRightItemView> {

    private ICircleLeftItemModel model;

    public CircleRightItemPresenter() {
        model = new CircleLeftItemModel();
    }

    /**
     * 我的帖子列表
     *
     * @param type
     * @param pageNo
     * @param pageSize
     */
    public void loadList(String type, final int pageNo, int pageSize) {
        model.loadMyComment(type, pageNo, pageSize);
        Observer<MyCommentResult> observer = new Observer<MyCommentResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MyCommentResult myCommentResult) {
                if (myCommentResult.getCode().equals("1")) {
                    if (pageNo == 1) {
                        mView.refresh(myCommentResult.getMyNoteList());
                    } else {
                        mView.loadMore(myCommentResult.getMyNoteList());
                    }
                } else {
                    mView.loadError(myCommentResult.getMsg());
                }
            }
        };
        model.subMyComment(observer);
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

    /**
     * 删除帖子
     *
     * @param ote_id
     */
    public void deleteComment(String ote_id) {
        model.deleteComments(ote_id);
        Observer<CommonResult> observer = new Observer<CommonResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommonResult result) {
                mView.deleteResult(result);
            }
        };
        model.subDelete(observer);
    }

    /**
     * 加载本地缓存
     *
     * @param type
     */
    public void loadLocal(final int type, final Context context) {
        Observable<List<MyComment>> observable = Observable.create(new Observable.OnSubscribe<List<MyComment>>() {
            @Override
            public void call(Subscriber<? super List<MyComment>> subscriber) {
                List<MyComment> list = DaoUtils.getMyNotes(type, context);
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<List<MyComment>> observer = new Observer<List<MyComment>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<MyComment> comments) {
                mView.loadLocal(comments);
            }
        };
        observable.subscribe(observer);
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

}
