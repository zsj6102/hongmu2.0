package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.Info.StoreInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IModifyStoreModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class ModifyStoreModel implements IModifyStoreModel {
    private Observable<ResultInfo<StoreInfo>> storeObservable;
    private Observable<ResultInfo<StoreInfo>> mdyObservable;
    private Observable<CatListBean> catObservable;
    private Observable<AddresBean> rgObservable;
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
    public void loadStoreInfo() {
        Map<String,String> map = new HashMap<>();
        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
        map.put("token",SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        storeObservable = RetrofitManager.getInstance(1,App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getStoreInfo(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<StoreInfo>, ResultInfo<StoreInfo>>() {
                    @Override
                    public ResultInfo<StoreInfo> call(ResultInfo<StoreInfo> storeInfoResultInfo) {
                        return storeInfoResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subStoreInfo(Observer<ResultInfo<StoreInfo>> observer) {
        storeObservable.subscribe(observer);
    }

    @Override
    public void subModify(Observer<ResultInfo<StoreInfo>> observer) {
        mdyObservable.subscribe(observer);
    }

    @Override
    public void loadModify(HashMap<String, RequestBody> params) {
        mdyObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getModifyStore(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<StoreInfo>, ResultInfo<StoreInfo>>() {
                    @Override
                    public ResultInfo<StoreInfo> call(ResultInfo<StoreInfo> storeInfoResultInfo) {
                        return storeInfoResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
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
