package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatListBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface ApplayView extends ColpencilBaseView {


    void applyError(String message);

    void load(AddresBean regionInfo);

    void loadCat(CatListBean catListBean);
}
