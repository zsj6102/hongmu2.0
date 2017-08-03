package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.OrderDtail;
import com.colpencil.redwood.bean.result.AllCartList;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface IOrderView extends ColpencilBaseView {
    /**
     * 新的购物车转订单
     */

    void loadNewOrder(OrderDtail result);

    void loadError(String message);

    void loadNewCartData(AllCartList data);
}
