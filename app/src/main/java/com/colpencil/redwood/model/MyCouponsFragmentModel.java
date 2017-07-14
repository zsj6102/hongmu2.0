package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CouponsResult;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMyCouponsFagmentModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 04
 */
public class MyCouponsFragmentModel implements IMyCouponsFagmentModel {

    private Observable<CouponsResult> couponsReturnObservable;
    private Observable<CouponsResult> getCoupons;
    private Observable<EntityResult<String>> changeObservable;

    @Override
    public void loadCoupon(int pageNo, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("page", pageNo + "");
        params.put("pageSize", pageSize + "");
        couponsReturnObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getMyCouponList(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CouponsResult, CouponsResult>() {
                    @Override
                    public CouponsResult call(CouponsResult couponsReturn) {
                        return couponsReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<CouponsResult> subscriber) {
        couponsReturnObservable.subscribe(subscriber);
    }

    @Override
    public void loadGetCoupon() {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        getCoupons = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCoupon(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CouponsResult, CouponsResult>() {
                    @Override
                    public CouponsResult call(CouponsResult couponsReturn) {
                        return couponsReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGet(Observer<CouponsResult> observer) {
        getCoupons.subscribe(observer);
    }

    @Override
    public void change(int point, String cpns_sn, int cpns_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("point", point + "");
        params.put("cpns_sn", cpns_sn);
        params.put("cpns_id", cpns_id + "");
        changeObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .changeVoucher(params)
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
    public void subChange(Observer<EntityResult<String>> observer) {
        changeObservable.subscribe(observer);
    }
}
