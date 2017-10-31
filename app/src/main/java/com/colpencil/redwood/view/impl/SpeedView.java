package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.AdResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface SpeedView extends ColpencilBaseView {

    void loadSuccess();

    void loadFail(String message);

    void getAd(AdResult adResult);


}
