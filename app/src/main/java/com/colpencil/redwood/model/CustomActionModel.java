package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.model.imples.ICustomActionModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：  定制
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/8 11 32
 */
public class CustomActionModel implements ICustomActionModel {

    private Observable<ListResult<BannerVo>> banner;
    private Observable<EntityResult<String>> submit;

    /**
     * 获取banner
     */
    @Override
    public void loadData(String acid) {
        banner = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadhomebanner(acid)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<BannerVo>, ListResult<BannerVo>>() {
                    @Override
                    public ListResult<BannerVo> call(ListResult<BannerVo> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subBanner(Observer<ListResult<BannerVo>> observer) {
        banner.subscribe(observer);
    }

    /**
     * 提交定制
     */
    @Override
    public void sumbitCustom(String contact, String mobile, String qq, String wechat, String description, List<File> files) {
        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("contact", RequestBody.create(null, contact));
        params.put("mobile", RequestBody.create(null, mobile));
        params.put("qq", RequestBody.create(null, qq));
        params.put("wechat", RequestBody.create(null, wechat));
        params.put("description", RequestBody.create(null, description));
        params.put("member_id", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""));
        params.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")));
        int img_number = 0;
        if (!ListUtils.listIsNullOrEmpty(files)) {
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                if (file.exists()) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
                    params.put("img" + (i + 1) + "\"; filename=\"avatar.jpg", body);
                    img_number++;
                }
            }
        }
        params.put("img_number", RequestBody.create(null, img_number + ""));
        submit = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .submitCustom(params)
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
    public void subCustom(Observer<EntityResult<String>> observer) {
        submit.subscribe(observer);
    }

}
