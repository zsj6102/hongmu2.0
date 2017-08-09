package com.colpencil.redwood.model.imples;


import com.colpencil.redwood.bean.OrderDetailReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface IOrderDetailModel extends ColpencilModel {
    //获取数据
    void loadData(int order_id);


    //注册观察者
    void sub(Observer<OrderDetailReturn> subscriber);
}
