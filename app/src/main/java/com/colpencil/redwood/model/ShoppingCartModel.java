package com.colpencil.redwood.model;

import android.util.Log;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CartItem;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.bean.ShoppingCartReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IShoppingCartModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：购物车
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/14 14 15
 */
public class ShoppingCartModel implements IShoppingCartModel {

    private Observable<ShoppingCartReturn> shoppingCartReturnObservable;

    private Observable<Result> resultObservable;

    /**
     * 获取购车信息
     */
    @Override
    public void loadShoppingData() {
        shoppingCartReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).shoppingCartInfor(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                        , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<ShoppingCartReturn, ShoppingCartReturn>() {
                    @Override
                    public ShoppingCartReturn call(ShoppingCartReturn shoppingCartReturn) {
                        return shoppingCartReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void changeCount(CartItem cartItem) {
        Log.e("返回值", "购物车修改请求：" + cartItem.toString());
        HashMap<String, String> map = new HashMap<>();
        map.put("memberId", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        map.put("cartId", cartItem.getCatid() + "");
        map.put("num", cartItem.getNum() + "");
        map.put("productId", cartItem.getProduct_id() + "");
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).updateShoppingCart(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result, Result>() {
                    @Override
                    public Result call(Result result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void deleteShoppingCart(String cartIds) {
        Log.e("返回值", "删除购物车请求：" + cartIds);
        HashMap<String, String> map = new HashMap<>();
        map.put("memberId", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        map.put("cartIds", cartIds);
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).deleteShoppingCart(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Result, Result>() {
                    @Override
                    public Result call(Result result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void subResult(Observer<Result> subscriber) {
        resultObservable.subscribe(subscriber);
    }

    @Override
    public void sub(Observer<ShoppingCartReturn> subscriber) {
        shoppingCartReturnObservable.subscribe(subscriber);
    }
}
