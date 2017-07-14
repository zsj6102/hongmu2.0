package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.PostCollectionReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IPostFragmentModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：商品
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 04
 */
public class PostFragmentModel implements IPostFragmentModel {

    private Observable<PostCollectionReturn> postCollectionReturnObservable;

    private Observable<EntityResult<String>> resultObservable;

    private Observable<EntityResult<String>> removeAll;


    @Override
    public void removeById(int favorite_id) {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).clearMyFavoriteById(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        ,SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),favorite_id)
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
    public void subRemove(Observer<EntityResult<String>> subscriber) {
        resultObservable.subscribe(subscriber);
    }
    @Override
    public void loadData(int pageNo, int pageSize) {
        postCollectionReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).favoriteForNotes(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        ,SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        pageNo,pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PostCollectionReturn, PostCollectionReturn>() {
                    @Override
                    public PostCollectionReturn call(PostCollectionReturn postCollectionReturn) {
                        return postCollectionReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<PostCollectionReturn> subscriber) {
        postCollectionReturnObservable.subscribe(subscriber);
    }

    @Override
    public void removeAll() {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("favorite_type", "3");
        removeAll = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .clearCollection(params)
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
    public void subRemoveAll(Observer<EntityResult<String>> subscriber) {
        removeAll.subscribe(subscriber);
    }
}
