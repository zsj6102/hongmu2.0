package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.OrderDetailReturn;
import com.colpencil.redwood.model.OrderDetailModel;
import com.colpencil.redwood.model.imples.IOrderDetailModel;
import com.colpencil.redwood.view.impl.IOrderDetailView;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import rx.Observer;

public class OrderDetailPresenter extends ColpencilPresenter<IOrderDetailView> {
    private IOrderDetailModel model;
    public OrderDetailPresenter(){
        model = new OrderDetailModel();
    }
    public void getOrderDetailReturn(int order_id){
        model.loadData(order_id);
        Observer<OrderDetailReturn> observer = new Observer<OrderDetailReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OrderDetailReturn orderDetailReturn) {
                mView.result(orderDetailReturn);
            }
        };
        model.sub(observer);
    }
}
