package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.HomeRecommend;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.CyclopediaResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IHomeModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:首页的model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class HomeModel implements IHomeModel {

    Observable<ListResult<CategoryVo>> observable;
    Observable<ListResult<CategoryVo>> myTag;
    Observable<EntityResult<HomeRecommend>> recommend;
    Observable<ListResult<HomeGoodInfo>> goods;
    private Observable<CyclopediaResult> cyObservable;
    private Observable<CyclopediaResult> search;

    @Override
    public void loadTag(int cat_type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cat_type", cat_type + "");
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
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
        observable.subscribe(observer);
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
    public void loadHomeRecommend() {
        recommend = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadHomeRecommend()
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<HomeRecommend>, EntityResult<HomeRecommend>>() {
                    @Override
                    public EntityResult<HomeRecommend> call(EntityResult<HomeRecommend> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subHomeRecommend(Observer<EntityResult<HomeRecommend>> observer) {
        recommend.subscribe(observer);
    }

    @Override
    public void loadGoods(String tagId, int page, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("tagid", tagId);
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        goods = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodList(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<HomeGoodInfo>, ListResult<HomeGoodInfo>>() {
                    @Override
                    public ListResult<HomeGoodInfo> call(ListResult<HomeGoodInfo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGood(Observer<ListResult<HomeGoodInfo>> observer) {
        goods.subscribe(observer);
    }

    @Override
    public void loadTagItem(String cat_id, String child_cat_id, int page, int pageSize, String iscommend) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cat_id", cat_id);
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        params.put("child_cat_id", child_cat_id);
        params.put("iscommend", iscommend);
        cyObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCycloTagItem(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CyclopediaResult, CyclopediaResult>() {
                    @Override
                    public CyclopediaResult call(CyclopediaResult cyclopediaResult) {
                        return cyclopediaResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subTagItem(Observer<CyclopediaResult> observer) {
        cyObservable.subscribe(observer);
    }

    @Override
    public void search(int cat_id, String keyword, int page, int pageSize) {
        search = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCycloSearch(cat_id, page, pageSize, RequestBody.create(null, keyword))
                .subscribeOn(Schedulers.io())
                .map(new Func1<CyclopediaResult, CyclopediaResult>() {
                    @Override
                    public CyclopediaResult call(CyclopediaResult result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSearch(Observer<CyclopediaResult> observer) {
        search.subscribe(observer);
    }
}
