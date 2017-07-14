package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.result.CustomDetailResult;
import com.colpencil.redwood.model.CustomDetailModel;
import com.colpencil.redwood.model.imples.ICustomDetailModel;
import com.colpencil.redwood.view.impl.ICustomDetailView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

public class CustomDetailPresenter extends ColpencilPresenter<ICustomDetailView> {

    private ICustomDetailModel model;

    public CustomDetailPresenter() {
        model = new CustomDetailModel();
    }

    public void loadContent(int id) {
        model.loadContent(id);
        Observer<CustomDetailResult> observer = new Observer<CustomDetailResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CustomDetailResult result) {
                mView.loadData(result);
            }
        };
        model.subContent(observer);
    }
}
