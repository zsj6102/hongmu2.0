package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.BrowsingCyclopediaDate;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IBrowsingCyclopediaModel;
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
public class BrowsingCyclopediaModel implements IBrowsingCyclopediaModel {

    private Observable<ListResult<BrowsingCyclopediaDate>> observable;
    private Observable<EntityResult<String>> deletes;

    @Override
    public void loadData(long pageNo, int pageSize) {
        observable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getMemberFootForBaike(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                        pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<BrowsingCyclopediaDate>, ListResult<BrowsingCyclopediaDate>>() {
                    @Override
                    public ListResult<BrowsingCyclopediaDate> call(ListResult<BrowsingCyclopediaDate> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ListResult<BrowsingCyclopediaDate>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void delete(int foot_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("foot_id", foot_id + "");
        deletes = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .clearMemberFootById(params)
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
    public void subDelete(Observer<EntityResult<String>> observer) {
        deletes.subscribe(observer);
    }
}
