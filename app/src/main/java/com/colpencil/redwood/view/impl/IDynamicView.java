package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.DynamicResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;


public interface IDynamicView extends ColpencilBaseView {
    void loadSuccess();

    void loadFail(String message);

    void loadMore(DynamicResult dynamicResult);
    void refresh(DynamicResult dynamicResult);
}
