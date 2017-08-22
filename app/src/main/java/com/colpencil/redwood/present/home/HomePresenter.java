package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.HomeRecommend;
import com.colpencil.redwood.bean.ListResult;

import com.colpencil.redwood.model.HomeModel;
import com.colpencil.redwood.model.imples.IHomeModel;
import com.colpencil.redwood.view.impl.IHomePageView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;



import rx.Observer;

/**
 * @author 陈宝
 * @Description:首页的Presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class HomePresenter extends ColpencilPresenter<IHomePageView> {

    private IHomeModel model;

    public HomePresenter() {
        model = new HomeModel();
    }

    /**
     * 首页分类
     *
     * @param cat_type
     */
    public void loadTag(int cat_type) {
        model.loadTag(cat_type);
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<CategoryVo> categoryResult) {
                if (categoryResult.getCode() == 0) {
                    mView.loadTag(categoryResult.getCatListResult());
                } else {
                    mView.loadError(categoryResult.getMessage());
                }
            }
        };
        model.sub(observer);
    }

    /**
     * 获取我的标签
     */
    public void loadMyTag() {
        model.loadMyTag();
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
                    mView.loadTag(homeTagResult.getMemberCatListResult());
                }
            }
        };
        model.subMyTag(observer);
    }

    public void loadRecommend() {
        model.loadHomeRecommend();
        Observer<EntityResult<HomeRecommend>> observer = new Observer<EntityResult<HomeRecommend>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<HomeRecommend> result) {
                mView.loadTodaysRecommend(result);
            }
        };
        model.subHomeRecommend(observer);
    }

    /**
     * 商品列表
     *
     * @param tagid
     * @param page
     * @param pageSize
     */
    public void loadGoods(final String tagid, final int page, int pageSize) {
        model.loadGoods(tagid, page, pageSize);
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
                }
            }
        };
        model.subGood(observer);
    }
}
