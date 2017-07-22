package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMeFragmentModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 11 21
 */
public class MeFragmentModel implements IMeFragmentModel {

    private Observable<LoginBean> observableLoginBean;

    private Observable<ListResult<HomeGoodInfo>> firstobservable;

    private Observable<ResultCodeInt> resultObservable;


    @Override
    public void loadUserInfor() {
        observableLoginBean = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).personIndex(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"), SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<LoginBean, LoginBean>() {
                    @Override
                    public LoginBean call(LoginBean loginBean) {
                        return loginBean;
                    }
                });
    }

    @Override
    public void loadGoodInfor(int tagid, int page, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("tagid", tagid+"");
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        firstobservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ListResult<HomeGoodInfo>, ListResult<HomeGoodInfo>>() {
                    @Override
                    public ListResult<HomeGoodInfo> call(ListResult<HomeGoodInfo> homeGoodResult) {
                        return homeGoodResult;
                    }
                });
    }

    @Override
    public void subUserInfor(Observer<LoginBean> subscriber) {
        observableLoginBean.subscribe(subscriber);
    }

    @Override
    public void subGoodInfor(Observer<ListResult<HomeGoodInfo>> subscriber) {
        firstobservable.subscribe(subscriber);
    }
    @Override
    public void getPhoneNum() {
        resultObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).customerService()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResultCodeInt, ResultCodeInt>() {
                    @Override
                    public ResultCodeInt call(ResultCodeInt result) {
                        return result;
                    }
                });
    }

    @Override
    public void subResult(Observer<ResultCodeInt> subscriber) {
        resultObservable.subscribe(subscriber);
    }
    private Observable<ApplyStatusReturn> statusObservable;
    @Override
    public void applyStatus(HashMap<String, String> params) {
        statusObservable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getApplyStatus(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ApplyStatusReturn, ApplyStatusReturn>() {
                    @Override
                    public ApplyStatusReturn call(ApplyStatusReturn applyStatusReturn) {
                        return applyStatusReturn;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subStauts(Subscriber<ApplyStatusReturn> subscriber) {
        statusObservable.subscribe(subscriber);
    }
}
