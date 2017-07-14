package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.GoodsTypeResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface AllAuctionView extends ColpencilBaseView {

    void loadSuccess();

    void loadFail(String message);

    void getGoodsType(GoodsTypeResult goodsTypeResult);
}
