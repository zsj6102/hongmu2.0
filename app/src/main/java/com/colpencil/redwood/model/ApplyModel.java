package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.CatReturnData;
import com.colpencil.redwood.bean.SellApply;
import com.colpencil.redwood.bean.result.ApplyReturn;
import com.colpencil.redwood.bean.result.ResultInfo;
import com.colpencil.redwood.bean.result.ResultRegion;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IApplyModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.R.attr.id;

public class ApplyModel implements IApplyModel {
    private Observable<AddresBean> rgObservable;
    private Observable<CatListBean> catObservable;
    @Override
    public void loadRegion(int id) {
        rgObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getRegion(0)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AddresBean, AddresBean>() {
                    @Override
                    public AddresBean call(AddresBean sellApplyResultInfo) {
                        return sellApplyResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRegion(Subscriber<AddresBean> subscriber) {
         rgObservable.subscribe(subscriber);
    }

    @Override
    public void loadCatList(int id) {
        catObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getFirstList(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CatListBean, CatListBean>() {
                    @Override
                    public CatListBean call(CatListBean catReturnData) {
                        return catReturnData;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<CatListBean> subscriber) {
        catObservable.subscribe(subscriber);
    }


}
