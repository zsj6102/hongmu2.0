package com.colpencil.redwood.present.home;

import android.content.Context;
import android.util.Log;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommentResult;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.model.CircleLeftItemModel;
import com.colpencil.redwood.model.imples.ICircleLeftItemModel;
import com.colpencil.redwood.view.impl.ICircleLeftItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:圈子列表的presenter
 * @Email DramaScript@outlook.com
 * @date 2016/8/1
 */
public class CircleLeftItemPresenter extends ColpencilPresenter<ICircleLeftItemView> {

    private ICircleLeftItemModel model;

    public CircleLeftItemPresenter() {
        model = new CircleLeftItemModel();
    }

    /**
     * 圈子帖子列表
     *
     * @param sec_id
     * @param pageNo
     * @param pageSize
     */
    public void loadList(String sec_id, final int pageNo, int pageSize) {
        model.loadComment(sec_id, pageNo, pageSize);
        Observer<CommentResult> observer = new Observer<CommentResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CommentResult commentResult) {
                if (commentResult.getCode() == 1) {
                    if (1 == pageNo) {
                        mView.refresh(commentResult.getNotesList());
                    } else {
                        mView.loadMore(commentResult.getNotesList());
                    }
                } else {
                    mView.loadError(commentResult.getMsg());
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

    public void loadLocal(final int sec_id, final Context context) {
        Observable<CommentResult> observable = Observable.create(new Observable.OnSubscribe<CommentResult>() {
            @Override
            public void call(final Subscriber<? super CommentResult> subscriber) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CommentResult result = new CommentResult();
                        result.setCode(1);
                        result.setMsg("获取数据成功");
                        Log.e("note", DaoUtils.getNotes(sec_id, context).toString());
                        result.setNotesList(DaoUtils.getNotes(sec_id, context));
                        subscriber.onNext(result);
                    }
                }).start();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<CommentResult> observer = new Observer<CommentResult>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CommentResult result) {
                mView.loadLocal(result.getNotesList());
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
