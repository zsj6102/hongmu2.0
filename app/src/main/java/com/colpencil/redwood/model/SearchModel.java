package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.HotResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ISearchModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:搜索的model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class SearchModel implements ISearchModel {

    private Observable<HotResult> observable;

    @Override
    public void loadHot(int cat_id) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getHot(cat_id).subscribeOn(Schedulers.io())
                .map(new Func1<HotResult, HotResult>() {
                    @Override
                    public HotResult call(HotResult hotResult) {
                        return hotResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subHot(Observer<HotResult> observer) {
        observable.subscribe(observer);
    }

}
