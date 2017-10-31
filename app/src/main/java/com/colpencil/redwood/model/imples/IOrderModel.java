package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.OrderPayInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.Map;

import rx.Observer;

public interface IOrderModel extends ColpencilModel {
    void loadNewOrder(Map<String,String> map);

    void subOrder(Observer<OrderPayInfo> observer);

    void loadDirectOrder(Map<String,String> map);

    void subDirectOrder(Observer<OrderPayInfo> observer);
}
