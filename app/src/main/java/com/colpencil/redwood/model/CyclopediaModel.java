package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICyclopediaModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CyclopediaModel implements ICyclopediaModel {

    private Observable<ListResult<CategoryVo>> tags;
    private Observable<ListResult<CategoryVo>> myTag;
    private Observable<ListResult<CyclopediaInfoVo>> cyObservable;
    private Observable<ListResult<CyclopediaInfoVo>> search;

    @Override
    public void loadTag(int cat_type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cat_type", cat_type + "");
        tags = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadHomeTag(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CategoryVo>, ListResult<CategoryVo>>() {
                    @Override
                    public ListResult<CategoryVo> call(ListResult<CategoryVo> categoryResult) {
                        return categoryResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ListResult<CategoryVo>> observer) {
        tags.subscribe(observer);
    }

    @Override
    public void loadMyTag() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        myTag = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadMyTag(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CategoryVo>, ListResult<CategoryVo>>() {
                    @Override
                    public ListResult<CategoryVo> call(ListResult<CategoryVo> homeTagResult) {
                        return homeTagResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subMyTag(Observer<ListResult<CategoryVo>> observer) {
        myTag.subscribe(observer);
    }

    @Override
    public void loadCyclopedia(String cat_id, String child_cat_id, int page, int pageSize, String iscommend) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cat_id", cat_id);
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        params.put("child_cat_id", child_cat_id);
        params.put("iscommend", iscommend);
        cyObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCyclopedia(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CyclopediaInfoVo>, ListResult<CyclopediaInfoVo>>() {
                    @Override
                    public ListResult<CyclopediaInfoVo> call(ListResult<CyclopediaInfoVo> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLoad(Observer<ListResult<CyclopediaInfoVo>> observer) {
        cyObservable.subscribe(observer);
    }

    @Override
    public void search(int cat_id, String keyword, int page, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cat_id", cat_id + "");
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        params.put("title", keyword);
        search = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .searcyCyclopedia(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CyclopediaInfoVo>, ListResult<CyclopediaInfoVo>>() {
                    @Override
                    public ListResult<CyclopediaInfoVo> call(ListResult<CyclopediaInfoVo> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSearch(Observer<ListResult<CyclopediaInfoVo>> observer) {
        search.subscribe(observer);
    }
}
