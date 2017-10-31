package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.OrderDtail;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.bean.result.OrderPayInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.io.Serializable;

public interface AllAuctionItemView extends ColpencilBaseView {

    void loadSuccess();

    void loadFail(String message);



    void loadNewOrder(OrderPayInfo result);

    void loadMore(AllGoodsResult result);

    void refresh(AllGoodsResult result);

    void addComment(AddResult result);

    void addLike(AddResult result);
}
