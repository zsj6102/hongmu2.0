package com.colpencil.redwood.present.cyclopedia;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.model.HomeModel;
import com.colpencil.redwood.model.imples.IHomeModel;
import com.colpencil.redwood.view.impl.ITagView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

public class TagPresenter extends ColpencilPresenter<ITagView> {
    private IHomeModel model;

    public TagPresenter() {
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
                    mView.loadError(categoryResult.getMessage(), categoryResult.getCode());
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

}
