package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CyclopediaItem;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICyclopediaFragmentModel;
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
public class CyclopediaFragmentModel implements ICyclopediaFragmentModel {

    private Observable<ListResult<CyclopediaItem>> cyclopediaObservable;

    private Observable<EntityResult<String>> resultObservable;

    private Observable<ListResult<CyclopediaItem>> newsObservable;

    private Observable<EntityResult<String>> removeAll;

    @Override
    public void removeById(int favorite_id) {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).clearMyFavoriteById(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"), favorite_id)
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
    public void loadNews(int page, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        newsObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadMyNews(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CyclopediaItem>, ListResult<CyclopediaItem>>() {
                    @Override
                    public ListResult<CyclopediaItem> call(ListResult<CyclopediaItem> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subNews(Observer<ListResult<CyclopediaItem>> observer) {
        newsObservable.subscribe(observer);
    }

    @Override
    public void removeAll(int favorite_type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("favorite_type", favorite_type + "");
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

    @Override
    public void loadData(int pageNo, int pageSize) {
        cyclopediaObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).favoriteForBaike(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CyclopediaItem>, ListResult<CyclopediaItem>>() {
                    @Override
                    public ListResult<CyclopediaItem> call(ListResult<CyclopediaItem> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ListResult<CyclopediaItem>> subscriber) {
        cyclopediaObservable.subscribe(subscriber);
    }
}
