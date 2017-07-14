package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.CustomDetailResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface ICustomDetailModel extends ColpencilModel {

    void loadContent(int id);

    void subContent(Observer<CustomDetailResult> observer);

}
