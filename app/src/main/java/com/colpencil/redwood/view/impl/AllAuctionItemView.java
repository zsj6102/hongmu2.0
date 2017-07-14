package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.io.Serializable;

public interface AllAuctionItemView extends ColpencilBaseView {

    void loadSuccess();

    void loadFail(String message);

    void getAllGoods(AllGoodsResult allGoodsResult);
}
