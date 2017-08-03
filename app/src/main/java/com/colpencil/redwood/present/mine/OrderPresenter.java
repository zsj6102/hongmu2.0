package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.result.OrderPayInfo;
import com.colpencil.redwood.model.OrderModel;

import com.colpencil.redwood.model.imples.IOrderModel;
import com.colpencil.redwood.view.impl.IOrderView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.Map;

import rx.Observer;


public class OrderPresenter extends ColpencilPresenter<IOrderView> {
    private IOrderModel paymentModel;

    public OrderPresenter() {
        paymentModel = new OrderModel();
    }
    public void getOrderPay(Map<String,String> params){
        paymentModel.loadNewOrder(params);
        Observer<OrderPayInfo> observer = new Observer<OrderPayInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
              mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(OrderPayInfo orderPayInfo) {
                if(orderPayInfo!= null && orderPayInfo.getCode() == 0){
                    mView.loadNewOrder(orderPayInfo.getData());
                }

            }
        };
        paymentModel.subOrder(observer);
    }
}
