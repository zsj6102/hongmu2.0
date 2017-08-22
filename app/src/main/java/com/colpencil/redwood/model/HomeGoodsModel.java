package com.colpencil.redwood.model;


import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IHomeGoodsModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class HomeGoodsModel implements IHomeGoodsModel{
    Observable<ListResult<CategoryVo>> allGoodsTag;
    Observable<ListResult<CategoryVo>> myGoodsTag;
    @Override
    public void loadGoodsAllTag() {
        allGoodsTag = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodsAllTag()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CategoryVo>, ListResult<CategoryVo>>() {
                    @Override
                    public ListResult<CategoryVo> call(ListResult<CategoryVo> homeTagResult) {
                        return homeTagResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAllGoodsTag(Observer<ListResult<CategoryVo>> observer) {
        allGoodsTag.subscribe(observer);
    }

    @Override
    public void loadMyGoodSTag() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        myGoodsTag = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadMyGoodsTag(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CategoryVo>, ListResult<CategoryVo>>() {
                    @Override
                    public ListResult<CategoryVo> call(ListResult<CategoryVo> homeTagResult) {
                        return homeTagResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subMyGoodsTag(Observer<ListResult<CategoryVo>> observer) {
        myGoodsTag.subscribe(observer);
    }
}
