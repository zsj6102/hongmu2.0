package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IUserInformationModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：个人信息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/26 09 23
 */
public class UserInformationModel implements IUserInformationModel {

    private Observable<LoginBean> observableLoginBean;

    @Override
    public void loadUserInfor() {
        observableLoginBean = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).personCenter(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"), SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<LoginBean, LoginBean>() {
                    @Override
                    public LoginBean call(LoginBean loginBean) {
                        return loginBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void updateUserImg(File faceFile) {
        HashMap<String, RequestBody> params = new HashMap<>();
        RequestBody body = RequestBody.create(MediaType.parse("image/png"), faceFile);
        params.put("face" + "\"; filename=\"avatar.jpg", body);
        params.put("member_id", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""));
        params.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")));
        observableLoginBean = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .editFace(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<LoginBean, LoginBean>() {
                    @Override
                    public LoginBean call(LoginBean loginBean) {
                        return loginBean;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void updateUserInfor(String modifyContent, int operationType) {
        observableLoginBean = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).updateCenter(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"), operationType, modifyContent)
                .subscribeOn(Schedulers.io())
                .map(new Func1<LoginBean, LoginBean>() {
                    @Override
                    public LoginBean call(LoginBean loginBean) {
                        return loginBean;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<LoginBean> subscriber) {
        observableLoginBean.subscribe(subscriber);
    }
}
