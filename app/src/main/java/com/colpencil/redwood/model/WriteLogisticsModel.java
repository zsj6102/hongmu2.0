package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.LogisTicsBean;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IWriteLogisticsModel;
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
 * @Description:物流信息
 * @Email 20000263@qq.com
 * @date 2016/8/9
 */
public class WriteLogisticsModel implements IWriteLogisticsModel {

    private Observable<ResultCodeInt> resultObservable;
    private Observable<ListResult<LogisTicsBean>> observable;

    @Override
    public void sumbit(HashMap<String, String> map) {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).writeLogistics(map)
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

    @Override
    public void loadLogisTics(int item_id, String order_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("item_id", item_id + "");
        params.put("return_id", order_id);
        observable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadLogistic(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<LogisTicsBean>, ListResult<LogisTicsBean>>() {
                    @Override
                    public ListResult<LogisTicsBean> call(ListResult<LogisTicsBean> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLogistic(Observer<ListResult<LogisTicsBean>> observer) {
        observable.subscribe(observer);
    }
}
