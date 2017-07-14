package com.colpencil.redwood.present.cyclopedia;

import com.colpencil.redwood.bean.result.CyclopediaResult;
import com.colpencil.redwood.model.SearchCycloModel;
import com.colpencil.redwood.model.imples.ISearchCycloModel;
import com.colpencil.redwood.view.impl.ISearchCycloView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

public class SearchCycloPresenter extends ColpencilPresenter<ISearchCycloView> {

    private ISearchCycloModel model;

    public SearchCycloPresenter() {
        model = new SearchCycloModel();
    }

    /**
     * 搜索百科
     *
     * @param cat_id
     * @param keyword
     * @param page
     * @param pageSize
     */
    public void loadCycloList(int cat_id, String keyword, int page, int pageSize) {
        model.loadCycloList(cat_id, keyword, page, pageSize);
        Observer<CyclopediaResult> observer = new Observer<CyclopediaResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CyclopediaResult result) {
                if (result.getSuccess() == 0) {
                    mView.loadSuccess(result.getArticleListResult());
                } else {
                    mView.loadError();
                }
            }
        };
        model.sub(observer);
    }
}
