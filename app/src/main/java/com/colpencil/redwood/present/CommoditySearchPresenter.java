package com.colpencil.redwood.present;

import android.util.Log;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.SortOptionsReturn;
import com.colpencil.redwood.bean.result.HomeGoodResult;
import com.colpencil.redwood.model.CommoditySearchModel;
import com.colpencil.redwood.model.imples.ICommoditySearchModel;
import com.colpencil.redwood.view.impl.ICommoditySearchView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 描述：商品搜索
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 16 53
 */
public class CommoditySearchPresenter extends ColpencilPresenter<ICommoditySearchView> {

    private ICommoditySearchModel commoditySearchModel;

    public CommoditySearchPresenter() {
        commoditySearchModel = new CommoditySearchModel();
    }

    /**
     * 查找商品信息
     */
    public void loadGoodInforByType(int cat_id, int sort_type, final int page, int pageSize) {
        commoditySearchModel.loadGoodData(cat_id, sort_type, page, pageSize);
        Observer<HomeGoodResult> observer = new Observer<HomeGoodResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "分类异常：" + e.toString());
            }

            @Override
            public void onNext(HomeGoodResult homeGoodResult) {
                if (homeGoodResult.getCode().equals("0")) {//数据获取成功
                    if (page == 1) {
                        mView.refresh(homeGoodResult.getCG_Result());
                    } else {
                        mView.loadMore(homeGoodResult.getCG_Result());
                    }
                } else {
                    mView.fail(homeGoodResult.getMessage());
                }
            }
        };
        commoditySearchModel.subGood(observer);
    }

    /**
     * 加载商品属性分类信息
     */
    public void sortData(int cat_id) {
        commoditySearchModel.loadSortInfor(cat_id);
        Observer<SortOptionsReturn> observer = new Observer<SortOptionsReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "分类异常：" + e.toString());
//                mView.fail("服务请求失败");
            }

            @Override
            public void onNext(SortOptionsReturn sortOptionsReturn) {
                Log.e("返回值", "筛选" + sortOptionsReturn.toString());
                if (sortOptionsReturn.getCode().equals("0")) {//数据请求成功
                    mView.loadSort(sortOptionsReturn.getResult());
                } else {
                    mView.fail(sortOptionsReturn.getMessage());
                }
            }
        };
        commoditySearchModel.subSortInfor(observer);
    }

    /**
     * 查找商品信息
     */
    public void loadGoodInforByType(int cat_id, String sorts, String price_range, final int page, int pageSize) {
        commoditySearchModel.loadGoodData(cat_id, sorts, price_range, page, pageSize);
        Observer<HomeGoodResult> observer = new Observer<HomeGoodResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "分类异常：" + e.toString());
            }

            @Override
            public void onNext(HomeGoodResult homeGoodResult) {
                if (homeGoodResult.getCode().equals("0")) {//数据获取成功
                    if (page == 1) {
                        mView.refresh(homeGoodResult.getCG_Result());
                    } else {
                        mView.loadMore(homeGoodResult.getCG_Result());
                    }
                } else {
                    mView.fail(homeGoodResult.getMessage());
                }
            }
        };
        commoditySearchModel.subGood(observer);
    }

    public void findGoodsByKeyWord(int cat_id, String keyword, final int page, int pageSize) {
        commoditySearchModel.findGoodByKeywood(cat_id, keyword, page, pageSize);
        Observer<ListResult<HomeGoodInfo>> observer = new Observer<ListResult<HomeGoodInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<HomeGoodInfo> result) {
                if (result.getCode() == 0) {
                    if (page == 1) {
                        mView.refresh(result.getCG_Result());
                    } else {
                        mView.loadMore(result.getCG_Result());
                    }
                } else {

                }
            }
        };
        commoditySearchModel.subGoodByKeywood(observer);
    }
}

