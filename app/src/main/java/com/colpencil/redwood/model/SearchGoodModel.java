package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ISearchGoodModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:搜索商品
 * @Email DramaScript@outlook.com
 * @date 2016/8/19
 */
public class SearchGoodModel implements ISearchGoodModel {

    private Observable<ListResult<HomeGoodInfo>> observable;

    @Override
    public void loadGoodList(String keyword, int page, int pageSize) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodSearch(RequestBody.create(null, keyword),
                        RequestBody.create(null, page + ""),
                        RequestBody.create(null, pageSize + ""))
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<HomeGoodInfo>, ListResult<HomeGoodInfo>>() {
                    @Override
                    public ListResult<HomeGoodInfo> call(ListResult<HomeGoodInfo> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ListResult<HomeGoodInfo>> observer) {
        observable.subscribe(observer);
    }
}
