package com.colpencil.redwood.present.home;

import android.content.Context;

import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.CommentResult;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.model.CircleLeftModel;
import com.colpencil.redwood.model.imples.ICircleLeftModel;
import com.colpencil.redwood.view.impl.ICircleLeftView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:圈子的Presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CircleLeftPresenter extends ColpencilPresenter<ICircleLeftView> {

    private ICircleLeftModel model;

    public CircleLeftPresenter() {
        model = new CircleLeftModel();
    }

    /**
     * 圈子的分类
     */
    public void loadTag() {
        model.loadTag();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<CategoryVo> result) {
                mView.loadTag(result);
            }
        };
        model.sub(observer);
    }

    /**
     * 获取banner
     *
     * @param acid
     */
    public void loadImage(String acid) {
        model.loadImage(acid);
        Observer<ListResult<BannerVo>> observer = new Observer<ListResult<BannerVo>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<BannerVo> result) {
                mView.loadBanner(result);
            }
        };
        model.subImage(observer);
    }

    /**
     * 加载数据库的数据
     *
     * @param context
     */
    public void loadLocal(final Context context) {
        Observable<List<CategoryVo>> observable = Observable.create(new Observable.OnSubscribe<List<CategoryVo>>() {
            @Override
            public void call(Subscriber<? super List<CategoryVo>> subscriber) {
                subscriber.onNext(DaoUtils.getSort(context));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<List<CategoryVo>> observer = new Observer<List<CategoryVo>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<CategoryVo> categoryVos) {
                mView.loadLocal(categoryVos);
            }
        };
        observable.subscribe(observer);
    }

    /**
     * 圈子的分类
     */
    public void loadMyTag() {
        model.loadMyTag();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<CategoryVo> result) {
                mView.loadTag(result);
            }
        };
        model.subMyTag(observer);
    }

    /**
     * 圈子帖子列表
     *
     * @param sec_id
     * @param pageNo
     * @param pageSize
     */
    public void loadPost(String sec_id, final int pageNo, int pageSize) {
        model.loadComment(sec_id, pageNo, pageSize);
        Observer<CommentResult> observer = new Observer<CommentResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CommentResult result) {
                mView.loadPost(result);
            }
        };
        model.subComment(observer);
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
                mView.operate(result, 1);
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
                mView.operate(result, 2);
            }
        };
        model.subLike(observer);
    }
}
