package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.model.imples.IEvaluationModel;
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

public class EvaluationModel implements IEvaluationModel {

    private Observable<ResultCodeInt> submit;

    @Override
    public void submitComment(int rate,int order_id, String goods_id, String content, List<File> files, int type) {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("order_id", RequestBody.create(null, order_id + ""));
        params.put("goods_id", RequestBody.create(null, goods_id));
        params.put("grade",RequestBody.create(null,rate+""));
        params.put("content", RequestBody.create(null, content));
        params.put("type", RequestBody.create(null, type + ""));
        params.put("member_id", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""));
        params.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")));
        int img_number = 0;
        if (!ListUtils.listIsNullOrEmpty(files)) {
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                if (file != null) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
                    params.put("image_" + (i + 1) + "\"; filename=\"avatar.jpg", body);
                    img_number++;
                }
            }
        }
        params.put("img_number", RequestBody.create(null, img_number + ""));
        submit = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).submitGoodComment(params)
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
    public void sub(Observer<ResultCodeInt> observer) {
        submit.subscribe(observer);
    }
}
