package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IAfterSalesModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 曾 凤
 * @Description: 售后
 * @Email 20000263@qq.com
 * @date 2016/8/10
 */
public class AfterSalesModel implements IAfterSalesModel {


    private Observable<ResultCodeInt> resultObservable;

    /**
     * 取消订单操作-获取退款理由
     */
    @Override
    public void loadReason(int type) {
        if (type == 1) {//退货理由
            resultObservable = RetrofitManager
                    .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                    .createApi(RedWoodApi.class).returnApplyReason(
                            SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                            , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                        @Override
                        public ResultCodeInt call(ResultCodeInt result) {
                            return result;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        } else if (type == 2) {//换货理由
            resultObservable = RetrofitManager
                    .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                    .createApi(RedWoodApi.class).exchangeApplyReason(
                            SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")
                            , SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                        @Override
                        public ResultCodeInt call(ResultCodeInt result) {
                            return result;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        }

    }

    @Override
    public void sumbit(HashMap<String, String> map, List<File> files) {
        Map<String, RequestBody> params = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            params.put(key, RequestBody.create(null, value));
        }
        for (int i = 1; i <= files.size(); i++) {
            RequestBody body = RequestBody.create(MediaType.parse("image/png"), files.get(i - 1));
            params.put("image_" + i + "\"; filename=\"avatar.jpg", body);
        }
        params.put("img_number", RequestBody.create(null, files.size() + ""));
        resultObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .applyForAfterSale(params)
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
