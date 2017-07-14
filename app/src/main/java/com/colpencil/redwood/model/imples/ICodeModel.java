package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CodeBean;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface ICodeModel extends ColpencilModel {

    void loadUrl();

    void sub(Observer<EntityResult<CodeBean>> observer);
}
