package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.CyclopediaResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ISearchCycloModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:搜索百科的Model
 * @Email DramaScript@outlook.com
 * @date 2016/8/4
 */
public class SearchCycloModel implements ISearchCycloModel {

    private Observable<CyclopediaResult> observable;

    @Override
    public void loadCycloList(int cat_id, String keyword, int page, int pageSize) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
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
    public void sub(Observer<CyclopediaResult> observer) {
        observable.subscribe(observer);
    }
}
