package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.bean.result.GoodInfoResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IGoodLeftModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:商品的Model
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class GoodLeftModel implements IGoodLeftModel {

    private Observable<GoodInfoResult> obGood;
    private Observable<ListResult<HomeGoodInfo>> obGoodlist;
    private Observable<AnnounceResult> goodDetail;
    private Observable<CareReturn> careReturnObservable;
    private Observable<CareReturn>  uncareObservable;
    @Override
    public void loadGoodInfo(String goodid) {
        obGood = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodInfo(goodid, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"))
                .subscribeOn(Schedulers.io())
                .map(new Func1<GoodInfoResult, GoodInfoResult>() {
                    @Override
                    public GoodInfoResult call(GoodInfoResult goodInfoResult) {
                        return goodInfoResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }
    @Override
    public void care(Map<String, String> parms) {
        careReturnObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCareStatus(parms)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CareReturn, CareReturn>() {
                    @Override
                    public CareReturn call(CareReturn careReturn) {
                        return careReturn;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCare(Observer<CareReturn> observer) {
        careReturnObservable.subscribe(observer);
    }

    @Override
    public void unCare(Map<String, String> params) {
        uncareObservable = RetrofitManager.getInstance(1,App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getCareStatus(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CareReturn, CareReturn>() {
                    @Override
                    public CareReturn call(CareReturn careReturn) {
                        return careReturn;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subUnCare(Observer<CareReturn> observer) {
        uncareObservable.subscribe(observer);
    }
    @Override
    public void subGoodInfo(Observer<GoodInfoResult> observer) {
        obGood.subscribe(observer);
    }

    @Override
    public void loadRecommend(int tagid, int page, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("tagid", tagid + "");
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        obGoodlist = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodList(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<HomeGoodInfo>, ListResult<HomeGoodInfo>>() {
                    @Override
                    public ListResult<HomeGoodInfo> call(ListResult<HomeGoodInfo> homeGoodResult) {
                        return homeGoodResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRecommend(Observer<ListResult<HomeGoodInfo>> observer) {
        obGoodlist.subscribe(observer);
    }

    @Override
    public void loadGoodDetail(int goodsId) {
        goodDetail = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadGoodMiddle(goodsId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<AnnounceResult, AnnounceResult>() {
                    @Override
                    public AnnounceResult call(AnnounceResult result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subDetail(Observer<AnnounceResult> observer) {
        goodDetail.subscribe(observer);
    }


}
