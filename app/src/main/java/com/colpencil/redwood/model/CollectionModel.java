package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ICollectionModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xiaobao on 2016/10/30.
 */
public class CollectionModel implements ICollectionModel {

    private Observable<CommonResult> share;
    private Observable<EntityResult<String>> record;

    @Override
    public void share(int ote_id) {
        share = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .postShare(ote_id,
                        SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<CommonResult, CommonResult>() {
                    @Override
                    public CommonResult call(CommonResult commonResult) {
                        return commonResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subShare(Observer<CommonResult> observer) {
        share.subscribe(observer);
    }

    @Override
    public void recordShare(int ote_id, String platform) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", 1 + "");
        params.put("platform", platform);
        params.put("id", ote_id + "");
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        record = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .addUpShare(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRecord(Observer<EntityResult<String>> observer) {
        record.subscribe(observer);
    }
}
