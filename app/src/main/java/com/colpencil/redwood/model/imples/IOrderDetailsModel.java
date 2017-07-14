package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.OrderDetailsReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：我的订单
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IOrderDetailsModel extends ColpencilModel {

    //获取数据
    void loadData(int order_id);

    void cancelOrderById(String order_id);

    void confirmRecept(int order_id);

    void cancelApplyForRefund(int order_id, int return_order_id);

    //获取数据
    void payInfor(int order_id);

    //提醒卖家发货
    void remindDelivery(String sn);


    //注册观察者
    void subPay(Observer<PayKeyRetrun> subscriber);

    void subResult(Observer<ResultCodeInt> subscriber);

    //注册观察者
    void sub(Observer<OrderDetailsReturn> subscriber);

}