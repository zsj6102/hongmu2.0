package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IRefundMoneyModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 曾 凤
 * @Description:退款
 * @Email 20000263@qq.com
 * @date 2016/8/9
 */
public class RefundMoneyModel implements IRefundMoneyModel {

    private Observable<ResultCodeInt> resultObservable;

    /**
     * 取消订单操作-获取退款理由
     */
    @Override
    public void loadReason() {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .refundApplyReason(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                    @Override
                    public ResultCodeInt call(ResultCodeInt result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sumbit(HashMap<String, String> params) {
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .applyForRefund(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                    @Override
                    public ResultCodeInt call(ResultCodeInt result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subResult(Observer<ResultCodeInt> subscriber) {
        resultObservable.subscribe(subscriber);
    }
}
