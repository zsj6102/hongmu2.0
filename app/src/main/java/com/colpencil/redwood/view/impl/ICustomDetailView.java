package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.CustomDetailResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface ICustomDetailView extends ColpencilBaseView {

    void loadData(CustomDetailResult result);

}
