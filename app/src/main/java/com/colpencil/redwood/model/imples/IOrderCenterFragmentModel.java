package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.OrderCenterReturn;
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
public interface IOrderCenterFragmentModel extends ColpencilModel {

    //获取数据
    void loadData(int pageNo, int pageSize, int optType);

    //注册观察者
    void sub(Observer<OrderCenterReturn> subscriber);

    void cancelOrderById(String order_id);

    void confirmRecept(int order_id);

    void cancelApplyForRefund(int order_id, int return_order_id);

    //注册观察者
    void subResult(Observer<ResultCodeInt> subscriber);


    //获取数据
    void payInfor(int order_id);

    //提醒卖家发货
    void remindDelivery(String sn);


    //注册观察者
    void subPay(Observer<PayKeyRetrun> subscriber);

    //删除订单
    void deleteById(int order_id);

    void subDelete(Observer<EntityResult<String>> observer);
}