package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.SizeColorInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface PublishView extends ColpencilBaseView{
    void applyError(String message);

    void loadCat(CatListBean catListBean);

    void loadSize(SizeColorInfo info);
}
