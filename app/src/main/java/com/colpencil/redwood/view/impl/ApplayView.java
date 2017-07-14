package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.SellApply;
import com.colpencil.redwood.bean.result.ApplyReturn;
import com.colpencil.redwood.bean.result.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface ApplayView extends ColpencilBaseView {
    void applySell(ApplyReturn resultInfo);

    void applyError(String message);

    void load(AddresBean regionInfo);

    void loadCat(CatListBean catListBean);

    void getStatusError(String message);

    void getStatusSucess(ApplyStatusReturn applyStatusReturn);

}
