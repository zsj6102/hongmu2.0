package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.WeekPersonList;
import com.colpencil.redwood.bean.result.BidderResult;
import com.colpencil.redwood.bean.result.WeekShootDetailResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IProductdetailsModle;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/11 11 53
 */
public class ProductdetailsModle implements IProductdetailsModle {

    private Observable<WeekShootDetailResult> listObservable;
    private Observable<ListResult<WeekPersonList>> personList;
    private Observable<BidderResult> bidder;
    private Observable<EntityResult<String>> submit;
    private Observable<EntityResult<String>> webUrl;

    @Override
    public void loadHearderData(int id) {
        listObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).loadWeekDetail(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeekShootDetailResult, WeekShootDetailResult>() {
                    @Override
                    public WeekShootDetailResult call(WeekShootDetailResult weekShootDetailResult) {
                        return weekShootDetailResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<WeekShootDetailResult> subscriber) {
        listObservable.subscribe(subscriber);
    }

    @Override
    public void loadPersonList(int id) {
        personList = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).loadWeekPersons(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<WeekPersonList>, ListResult<WeekPersonList>>() {
                    @Override
                    public ListResult<WeekPersonList> call(ListResult<WeekPersonList> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subPerson(Observer<ListResult<WeekPersonList>> observer) {
        personList.subscribe(observer);
    }

    @Override
    public void loadBidders(int id) {
        bidder = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).loadBidders(id)
                .subscribeOn(Schedulers.io())
                .map(new Func1<BidderResult, BidderResult>() {
                    @Override
                    public BidderResult call(BidderResult bidderResult) {
                        return bidderResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subBidders(Observer<BidderResult> observer) {
        bidder.subscribe(observer);
    }

    @Override
    public void submitBid(String price, int id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("offer", price);
        params.put("id", id + "");
        submit = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).submitBid(params)
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
    public void subBid(Observer<EntityResult<String>> observer) {
        submit.subscribe(observer);
    }

    @Override
    public void loadUrl(int id) {
        webUrl = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).loadWeekShootUrl(id)
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
    public void subUrl(Observer<EntityResult<String>> observer) {
        webUrl.subscribe(observer);
    }
}
