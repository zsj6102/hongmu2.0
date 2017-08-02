package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.bean.result.ZcAllCardInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface IZcFoumousView extends ColpencilBaseView{
    void refresh(ZcAllCardInfo info);
    void loadMore(ZcAllCardInfo info);
    void loadFail(String message);
    void operate(CareReturn result);
}
