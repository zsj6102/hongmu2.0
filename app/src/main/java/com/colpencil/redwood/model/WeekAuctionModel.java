package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IWeekAuctionModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:周拍分类的Model
 * @Email DramaScript@outlook.com
 * @date 2016/8/2
 */
public class WeekAuctionModel implements IWeekAuctionModel {

    private Observable<ListResult<CategoryVo>> observable;
    private Observable<ListResult<CategoryVo>> weekTag;

    @Override
    public void loadTag() {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadAllWeekShootSort()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CategoryVo>, ListResult<CategoryVo>>() {
                    @Override
                    public ListResult<CategoryVo> call(ListResult<CategoryVo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ListResult<CategoryVo>> observer) {
        observable.subscribe(observer);
    }

    /**
     * 我的周拍
     */
    @Override
    public void loadWeekShoot() {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        weekTag = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadWeekShootSort(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CategoryVo>, ListResult<CategoryVo>>() {
                    @Override
                    public ListResult<CategoryVo> call(ListResult<CategoryVo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subWeekShoot(Observer<ListResult<CategoryVo>> observer) {
        weekTag.subscribe(observer);
    }
}
