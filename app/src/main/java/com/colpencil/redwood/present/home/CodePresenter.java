package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.CodeBean;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.model.CodeModel;
import com.colpencil.redwood.model.imples.ICodeModel;
import com.colpencil.redwood.view.impl.ICodeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

public class CodePresenter extends ColpencilPresenter<ICodeView> {

    private ICodeModel model;

    public CodePresenter() {
        model = new CodeModel();
    }

    public void loadUrl() {
        model.loadUrl();
        Observer<EntityResult<CodeBean>> observer = new Observer<EntityResult<CodeBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<CodeBean> result) {
                mView.codeResult(result);
            }
        };
        model.sub(observer);
    }
}
