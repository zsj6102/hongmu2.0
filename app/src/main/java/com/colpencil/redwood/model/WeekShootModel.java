package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.WeekAuctionList;
import com.colpencil.redwood.bean.result.WeekAuctionListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IWeekShootModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**描述： 周拍
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 10 48
 */
public class WeekShootModel implements IWeekShootModel{

    private Observable<WeekAuctionListResult> listObservable;
    private Observable<WeekAuctionListResult> searchObservable;
    @Override
    public void loadData(String type_id, int pageNo, int pageSize) {
        HashMap<String,String> params = new HashMap<>();
        params.put("type_id",type_id);
        params.put("page",pageNo+"");
        params.put("pageSize",pageSize+"");
        listObservable = RetrofitManager
                .getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).loadWeekShoot(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeekAuctionListResult, WeekAuctionListResult>() {
                    @Override
                    public WeekAuctionListResult call(WeekAuctionListResult weekAuctionListResult) {
                        return weekAuctionListResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<WeekAuctionListResult> subscriber) {
        listObservable.subscribe(subscriber);
    }

    @Override
    public void loadSearch(String keyword, int pageNo, int pageSize) {
        HashMap<String,String> params = new HashMap<>();
        params.put("keyword",keyword);
        params.put("page",pageNo+"");
        params.put("pageSize",pageSize+"");
        searchObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getSearchAuction(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeekAuctionListResult, WeekAuctionListResult>() {
                    @Override
                    public WeekAuctionListResult call(WeekAuctionListResult weekAuctionListResult) {
                        return weekAuctionListResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSearch(Observer<WeekAuctionListResult> subscriber) {
        searchObservable.subscribe(subscriber);
    }
}
