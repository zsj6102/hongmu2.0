package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IHotGoodModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

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
public class HotGoodModel implements IHotGoodModel {

    private Observable<ListResult<HomeGoodInfo>> firstobservable;


    @Override
    public void loadGoodInfor(int page, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("tagid", "4");//4：  分类-藏友热卖-热卖品
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        firstobservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodList(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<HomeGoodInfo>, ListResult<HomeGoodInfo>>() {
                    @Override
                    public ListResult<HomeGoodInfo> call(ListResult<HomeGoodInfo> homeGoodResult) {
                        return homeGoodResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGoodInfor(Observer<ListResult<HomeGoodInfo>> subscriber) {
        firstobservable.subscribe(subscriber);
    }

}
