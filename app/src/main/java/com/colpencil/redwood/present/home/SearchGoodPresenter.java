package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.model.SearchGoodModel;
import com.colpencil.redwood.model.imples.ISearchGoodModel;
import com.colpencil.redwood.view.impl.ISearchGoodView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:搜索商品结果的presenter
 * @Email DramaScript@outlook.com
 * @date 2016/8/4
 */
public class SearchGoodPresenter extends ColpencilPresenter<ISearchGoodView> {

    private ISearchGoodModel model;

    public SearchGoodPresenter() {
        model = new SearchGoodModel();
    }

    /**
     * 搜索商品
     *
     * @param keyword
     */
    public void loadGood(String keyword, final int page, int pageSize) {
        model.loadGoodList(keyword, page, pageSize);
        Observer<ListResult<HomeGoodInfo>> observer = new Observer<ListResult<HomeGoodInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadError();
            }

            @Override
            public void onNext(ListResult<HomeGoodInfo> homeGoodResult) {
                if (homeGoodResult.getCode() == 0) {
                    if (page == 1) {
                        mView.refresh(homeGoodResult.getCG_Result());
                    } else {
                        mView.loadMore(homeGoodResult.getCG_Result());
                    }
                } else {
                    mView.loadError();
                }
            }
        };
        model.sub(observer);
    }
}
