package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface IBuisnessView extends ColpencilBaseView {
    void getStatusError(String message);

    void getStatusSucess(ApplyStatusReturn applyStatusReturn);
}
