package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.OfficialResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICustomListModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:官方定制列表
 * @Email DramaScript@outlook.com
 * @date 2016/9/1
 */
public class CustomListModel implements ICustomListModel {

    private Observable<OfficialResult> observable;

    @Override
    public void loadGoods() {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCustomList()
                .map(new Func1<OfficialResult, OfficialResult>() {
                    @Override
                    public OfficialResult call(OfficialResult officialResult) {
                        return officialResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<OfficialResult> observer) {
        observable.subscribe(observer);
    }
}
