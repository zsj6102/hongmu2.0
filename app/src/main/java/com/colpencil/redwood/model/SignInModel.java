package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.SignInResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.ISignInModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:签到界面的Model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class SignInModel implements ISignInModel {

    private Observable<SignInResult> myobserver;
    private Observable<EntityResult<String>> state;

    @Override
    public void signinServer(String userid, String token) {
        myobserver = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .signinToServer(userid, token)
                .subscribeOn(Schedulers.io())
                .map(new Func1<SignInResult, SignInResult>() {
                    @Override
                    public SignInResult call(SignInResult signInResult) {
                        return signInResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subsign(Observer<SignInResult> observer) {
        myobserver.subscribe(observer);
    }

    @Override
    public void loadState() {
        state = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadSignState(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"),
                        SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subState(Observer<EntityResult<String>> observer) {
        state.subscribe(observer);
    }
}
