package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.CyclopediaResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICyclopediaItemModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description: 首页-百科的Model
 * @Email DramaScript@outlook.com
 * @date 2016/7/7
 */
public class CyclopediaItemModel implements ICyclopediaItemModel {

    private Observable<CyclopediaResult> cyObservable;
    private Observable<ListResult<CyclopediaInfoVo>> search;

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
    public void subTagItem(Observer<CyclopediaResult> cyclolist) {
        cyObservable.subscribe(cyclolist);
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
