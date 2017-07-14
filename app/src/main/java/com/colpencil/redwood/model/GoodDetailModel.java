package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.GoodInfoResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IGoodDetailModel;
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
 * @Description:商品详情
 * @Email DramaScript@outlook.com
 * @date 2016/8/8
 */
public class GoodDetailModel implements IGoodDetailModel {

    private Observable<EntityResult<String>> addcar;
    private Observable<EntityResult<String>> state;
    private Observable<EntityResult<String>> keep;
    private Observable<CommonResult> brower;
    private Observable<CommonResult> share;
    private Observable<GoodInfoResult> obGood;
    private Observable<EntityResult<String>> addup;

    /**
     * 加入购物车
     *
     * @param goodsId
     * @param productId
     * @param num
     */
    @Override
    public void addtocar(int goodsId, int productId, int num) {
        HashMap<String, String> params = new HashMap<>();
        params.put("goodsId", goodsId + "");
        params.put("productId", productId + "");
        params.put("num", num + "");
        params.put("memberId", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        addcar = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .addToCar(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAdd(Observer<EntityResult<String>> observer) {
        addcar.subscribe(observer);
    }

    /**
     * 获取收藏状态
     *
     * @param goods_id
     */
    @Override
    public void loadState(int goods_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("type", "1");
        params.put("id", goods_id + "");
        state = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadState(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLoad(Observer<EntityResult<String>> observer) {
        state.subscribe(observer);
    }

    /**
     * 收藏商品
     *
     * @param goods_id
     */
    @Override
    public void keepGood(int goods_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", goods_id + "");
        params.put("type", Constants.KEEP_GOOD + "");
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        keep = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .collect(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subKeep(Observer<EntityResult<String>> observer) {
        keep.subscribe(observer);
    }

    /**
     * 添加浏览记录
     *
     * @param goods_id
     */
    @Override
    public void browerGood(int goods_id) {
        brower = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .BrowerGood(goods_id + "",
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
    public void subBrower(Observer<CommonResult> observer) {
        brower.subscribe(observer);
    }

    @Override
    public void loadShare(int good_id) {
        share = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .goodShare(good_id + "",
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
    public void loadGoodInfo(int goodid) {
        obGood = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodInfo(goodid + "")
                .subscribeOn(Schedulers.io())
                .map(new Func1<GoodInfoResult, GoodInfoResult>() {
                    @Override
                    public GoodInfoResult call(GoodInfoResult goodInfoResult) {
                        return goodInfoResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGoodInfo(Observer<GoodInfoResult> observer) {
        obGood.subscribe(observer);
    }

    @Override
    public void addUpShare(int type, String platform, int goodid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("platform", platform);
        params.put("id", goodid + "");
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        addup = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .addUpShare(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAddup(Observer<EntityResult<String>> observer) {
        addup.subscribe(observer);
    }
}
