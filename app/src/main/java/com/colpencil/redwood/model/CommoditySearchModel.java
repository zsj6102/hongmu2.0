package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.SortOptionsReturn;
import com.colpencil.redwood.bean.result.HomeGoodResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICommoditySearchModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 11 21
 */
public class CommoditySearchModel implements ICommoditySearchModel {

    private Observable<SortOptionsReturn> sortOptionsobservable;

    private Observable<HomeGoodResult> goodObservable;

    private Observable<ListResult<HomeGoodInfo>> searchObservable;

    @Override
    public void loadSortInfor(int cat_id) {
        sortOptionsobservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getSortOptions(cat_id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<SortOptionsReturn, SortOptionsReturn>() {
                    @Override
                    public SortOptionsReturn call(SortOptionsReturn sortOptionsReturn) {
                        return sortOptionsReturn;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSortInfor(Observer<SortOptionsReturn> subscriber) {
        sortOptionsobservable.subscribe(subscriber);
    }

    @Override
    public void loadGoodData(int cat_id, int sort_type, int page, int pageSize) {
        goodObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .selectGoods(cat_id, sort_type, page, pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<HomeGoodResult, HomeGoodResult>() {
                    @Override
                    public HomeGoodResult call(HomeGoodResult homeGoodResult) {
                        return homeGoodResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void loadGoodData(int cat_id, String sorts, String price_range, int page, int pageSize) {
        goodObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .selectGoods(cat_id, sorts, price_range, page, pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<HomeGoodResult, HomeGoodResult>() {
                    @Override
                    public HomeGoodResult call(HomeGoodResult homeGoodResult) {
                        return homeGoodResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGood(Observer<HomeGoodResult> subscriber) {
        goodObservable.subscribe(subscriber);
    }

    @Override
    public void findGoodByKeywood(int cat_id, String keyword, int page, int pageSize) {
        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("cat_id", RequestBody.create(null, cat_id + ""));
        params.put("keyword", RequestBody.create(null, keyword));
        params.put("page", RequestBody.create(null, page + ""));
        params.put("pageSize", RequestBody.create(null, pageSize + ""));
        searchObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodByKeywood(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<HomeGoodInfo>, ListResult<HomeGoodInfo>>() {
                    @Override
                    public ListResult<HomeGoodInfo> call(ListResult<HomeGoodInfo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGoodByKeywood(Observer<ListResult<HomeGoodInfo>> observable) {
        searchObservable.subscribe(observable);
    }

}
