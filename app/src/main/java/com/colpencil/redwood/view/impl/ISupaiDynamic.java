package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface ISupaiDynamic extends ColpencilBaseView {

    void loadSuccess();

    void loadFail(String message);

    void loadMoreSp(AllGoodsResult result);

    void refreshSp(AllGoodsResult result);
}
