package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CommentReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMyEvaluationModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：我的评价
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 04
 */
public class MyEvaluationModel implements IMyEvaluationModel {

    private Observable<CommentReturn> commentReturnObservable;

    @Override
    public void loadData(int pageNo, int pageSize) {
        commentReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).getMyCommentList(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"), SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"), pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CommentReturn, CommentReturn>() {
                    @Override
                    public CommentReturn call(CommentReturn commentReturn) {
                        return commentReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
    @Override
    public void sub(Observer<CommentReturn> subscriber) {
        commentReturnObservable.subscribe(subscriber);
    }
}
