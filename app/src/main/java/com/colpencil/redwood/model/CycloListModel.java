package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.MyCyclopediaInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICycloListModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:百科界面的model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CycloListModel implements ICycloListModel {

    private Observable<ListResult<MyCyclopediaInfo>> observable;
    private Observable<ListResult<MyCyclopediaInfo>> newsObservable;
    private Observable<EntityResult<String>> cancleObservable;
    private Observable<EntityResult<String>> deleteObservable;

    @Override
    public void loadList(String operationType, int pageNo, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("status", operationType);
        params.put("cat_id", "3");
        params.put("page", pageNo + "");
        params.put("pageSize", pageSize + "");
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadmyclclo(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<MyCyclopediaInfo>, ListResult<MyCyclopediaInfo>>() {
                    @Override
                    public ListResult<MyCyclopediaInfo> call(ListResult<MyCyclopediaInfo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ListResult<MyCyclopediaInfo>> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void loadNews(String operationType, int pageNo, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("status", operationType);
        params.put("cat_id", "8");
        params.put("page", pageNo + "");
        params.put("pageSize", pageSize + "");
        newsObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadmyclclo(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<MyCyclopediaInfo>, ListResult<MyCyclopediaInfo>>() {
                    @Override
                    public ListResult<MyCyclopediaInfo> call(ListResult<MyCyclopediaInfo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subNews(Observer<ListResult<MyCyclopediaInfo>> observer) {
        newsObservable.subscribe(observer);
    }

    @Override
    public void cancleAudit(int article_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("article_id", article_id + "");
        cancleObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .cancleAudit(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCancle(Observer<EntityResult<String>> observer) {
        cancleObservable.subscribe(observer);
    }

    @Override
    public void delete(int article_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("article_id", article_id + "");
        deleteObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .deleteCyclopedia(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subDelete(Observer<EntityResult<String>> observer) {
        deleteObservable.subscribe(observer);
    }
}
