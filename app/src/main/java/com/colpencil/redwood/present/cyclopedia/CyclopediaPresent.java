package com.colpencil.redwood.present.cyclopedia;

import android.util.Log;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.model.CyclopediaModel;
import com.colpencil.redwood.model.imples.ICyclopediaModel;
import com.colpencil.redwood.view.impl.ICyclopediaView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

public class CyclopediaPresent extends ColpencilPresenter<ICyclopediaView> {

    private ICyclopediaModel model;

    public CyclopediaPresent() {
        model = new CyclopediaModel();
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
            public void onNext(ListResult<CategoryVo> result) {
                if (result.getCode() == 0) {
                    mView.loadTag(result.getCatListResult());
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
            public void onNext(ListResult<CategoryVo> result) {
                if (result.getCode() == 0) {
                    mView.loadTag(result.getMemberCatListResult());
                }
            }
        };
        model.subMyTag(observer);
    }

    /**
     * 获取百科
     *
     * @param cat_id
     * @param child_cat_id
     * @param page
     * @param pageSize
     */
    public void loadCyclopedia(String cat_id, String child_cat_id, final int page, int pageSize, String iscommend) {
        model.loadCyclopedia(cat_id, child_cat_id, page, pageSize, iscommend);
        Observer<ListResult<CyclopediaInfoVo>> observer = new Observer<ListResult<CyclopediaInfoVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.getMessage());
            }

            @Override
            public void onNext(ListResult<CyclopediaInfoVo> result) {
                if (result.getSuccess() == 0) {
                    if (page == 1) {
                        mView.refresh(result.getArticleListResult());
                    } else {
                        mView.loadmore(result.getArticleListResult());
                    }
                } else {
                    mView.loadError(result);
                }
            }
        };
        model.subLoad(observer);
    }

    public void search(int cat_id, String keyword, final int page, int pageSize) {
        model.search(cat_id, keyword, page, pageSize);
        Observer<ListResult<CyclopediaInfoVo>> observer = new Observer<ListResult<CyclopediaInfoVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CyclopediaInfoVo> result) {
                if (result.getSuccess() == 0) {
                    if (page == 1) {
                        mView.refresh(result.getArticleListResult());
                    } else {
                        mView.loadmore(result.getArticleListResult());
                    }
                } else {
                    mView.loadError(result);
                }
            }
        };
        model.subSearch(observer);
    }
}
