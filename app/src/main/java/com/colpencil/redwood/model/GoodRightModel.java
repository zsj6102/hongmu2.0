package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.GoodComment;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IGoodRightModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:商品评论的model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class GoodRightModel implements IGoodRightModel {

    Observable<ListResult<GoodComment>> observable;
    Observable<EntityResult<String>> goodNums;

    @Override
    public void loadComment(String goodId, int page, int pageSize) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodComment(goodId, page, pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<GoodComment>, ListResult<GoodComment>>() {
                    @Override
                    public ListResult<GoodComment> call(ListResult<GoodComment> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ListResult<GoodComment>> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void loadCommentsNum(int goods_id) {
        goodNums = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodCommentNum(goods_id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCommentsNum(Observer<EntityResult<String>> observer) {
        goodNums.subscribe(observer);
    }


}
