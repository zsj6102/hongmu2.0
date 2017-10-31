package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.bean.result.GoodInfoResult;
import com.colpencil.redwood.model.GoodLeftModel;
import com.colpencil.redwood.model.imples.IGoodLeftModel;
import com.colpencil.redwood.view.impl.IGoodLeftView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.Map;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:商品的Presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class GoodLeftPresenter extends ColpencilPresenter<IGoodLeftView> {

    private IGoodLeftModel model;

    public GoodLeftPresenter() {
        model = new GoodLeftModel();
    }

    /**
     * 根据商品Id 查询商品详情信息
     */
    public void loadGoodInfo(String goodid) {
        model.loadGoodInfo(goodid);
        Observer<GoodInfoResult> observer = new Observer<GoodInfoResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GoodInfoResult goodInfoResult) {
                if (goodInfoResult.getCode().equals("1")) {
                    mView.loadGoodInfo(goodInfoResult.getGoodInfo());
                } else {
                    mView.loadGoodInfoError();
                }
            }
        };
        model.subGoodInfo(observer);
    }
    public void getCareReturn(Map<String, String> params) {
        model.care(params);
        Observer<CareReturn> observer = new Observer<CareReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadFail("服务器异常");
            }

            @Override
            public void onNext(CareReturn careReturn) {
                if (careReturn != null && mView != null) {
                    mView.care(careReturn);
                }
            }
        };
        model.subCare(observer);
    }

    public void getUncareReturn(Map<String, String> params) {
        model.unCare(params);
        Observer<CareReturn> observer = new Observer<CareReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadFail("服务器异常");
            }

            @Override
            public void onNext(CareReturn careReturn) {
                if (careReturn != null && mView != null) {
                    mView.uncare(careReturn);
                }
            }
        };
        model.subUnCare(observer);
    }
    /**
     * 商品推荐
     */
    public void loadRecommend(int tagid, int page, int pageSize) {
        model.loadRecommend(tagid, page, pageSize);
        Observer<ListResult<HomeGoodInfo>> observer = new Observer<ListResult<HomeGoodInfo>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<HomeGoodInfo> homeGoodResult) {
                if (homeGoodResult.getCode() == 0) {
                    mView.loadRecommend(homeGoodResult.getCG_Result());
                } else {
                    mView.loadRecommendError();
                }
            }
        };
        model.subRecommend(observer);
    }

    public void loadGoodDetail(int goodsId) {
        model.loadGoodDetail(goodsId);
        Observer<AnnounceResult> observer = new Observer<AnnounceResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AnnounceResult result) {
                mView.loadDetail(result);
            }
        };
        model.subDetail(observer);
    }
}
