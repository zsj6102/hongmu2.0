package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.Address;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IAddAddressModel;
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
public class AddAddressModel implements IAddAddressModel {

    private Observable<EntityResult<String>> add;
    private Observable<EntityResult<String>> update;

    @Override
    public void addAddress(Address address) {
        HashMap<String, String> map = new HashMap<>();
        if (address.getAddrId() != 0) {
            map.put("addrId", address.getAddrId() + "");
        }
        map.put("province", address.getProvince());
        map.put("city", address.getCity());
        map.put("region", address.getRegion());
        map.put("address", address.getAddress());
        map.put("name", address.getName());
        map.put("mobile", address.getMobile());
        map.put("def_addr", address.getDef_addr() + "");
        map.put("zip", address.getZip());
        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        add = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .addAddress(map)
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
    public void subAdd(Observer<EntityResult<String>> observer) {
        add.subscribe(observer);
    }

    @Override
    public void updateAddress(Address address) {
        HashMap<String, String> map = new HashMap<>();
        if (address.getAddrId() != 0) {
            map.put("addrId", address.getAddrId() + "");
        }
        map.put("province", address.getProvince());
        map.put("city", address.getCity());
        map.put("region", address.getRegion());
        map.put("address", address.getAddress());
        map.put("name", address.getName());
        map.put("mobile", address.getMobile());
        map.put("def_addr", address.getDef_addr() + "");
        map.put("zip", address.getZip());
        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        update = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .updateAddress(map)
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
    public void subUpdate(Observer<EntityResult<String>> observer) {
        update.subscribe(observer);
    }
}
