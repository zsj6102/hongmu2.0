package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.model.HomeGoodsModel;

import com.colpencil.redwood.model.imples.IHomeGoodsModel;

import com.colpencil.redwood.view.impl.IHomeGoodsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

public class HomeGoodsPresenter extends ColpencilPresenter<IHomeGoodsView> {

    private IHomeGoodsModel model;

    public HomeGoodsPresenter() {
        model = new HomeGoodsModel();
    }
    /**
     * 二期获取所有标签
     */
    public void loadAllGoodsTag(){
        model.loadGoodsAllTag();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CategoryVo> homeTagResult) {
                if (homeTagResult.getCode() == 0) {
                    mView.loadTag(homeTagResult.getCatListResult());
                } else {
                    mView.loadError(homeTagResult.getMessage());
                }
            }
        };
        model.subAllGoodsTag(observer);
    }
    /**
     * 获取我的商品标签
     */
    public void loadMyGoodsTag() {
        model.loadMyGoodSTag();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CategoryVo> result) {
                if (result.getCode() == 0) {
                    mView.loadTag(result.getMemberCatListResult());
                }
            }
        };
        model.subMyGoodsTag(observer);
    }
}
