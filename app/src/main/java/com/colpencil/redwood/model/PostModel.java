package com.colpencil.redwood.model;


import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.HomeTagResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IPostModel;
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
 * @author 陈宝
 * @Description:发帖的model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class PostModel implements IPostModel {

    Observable<CommonResult> observable;
    Observable<HomeTagResult> home;

    @Override
    public void loadTag() {
        home = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadCircleTag()
                .subscribeOn(Schedulers.io())
                .map(new Func1<HomeTagResult, HomeTagResult>() {
                    @Override
                    public HomeTagResult call(HomeTagResult homeTagResult) {
                        return homeTagResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subTag(Observer<HomeTagResult> observer) {
        home.subscribe(observer);
    }

    @Override
    public void submitToServer(String sec_id, String ote_title, String ote_content, String url, List<File> files) {
        Map<String, RequestBody> params = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (file != null) {
                RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
                params.put("image" + (i + 1) + "\"; filename=\"avatar.jpg", body);
            }
        }
        params.put("sec_id", RequestBody.create(null, sec_id));
        params.put("ote_title", RequestBody.create(null, ote_title + ""));
        params.put("ote_content", RequestBody.create(null, ote_content + ""));
        params.put("url", RequestBody.create(null, url + ""));
        params.put("member_id", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""));
        params.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")));
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .submitToServer(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CommonResult, CommonResult>() {
                    @Override
                    public CommonResult call(CommonResult result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<CommonResult> observer) {
        observable.subscribe(observer);
    }
}
