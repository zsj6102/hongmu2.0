package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AfterSalesCenterReturn;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;

import rx.Observer;

/**
 * 描述：我的订单
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IAfterSalesFragmentModel extends ColpencilModel {

    //获取数据
    void loadData(int pageNo, int pageSize, int operationType);

    void cancelAfterSale(HashMap<String, String> map);//取消售后申请

    void getPhoneNum();//获取客服电话

    //注册观察者
    void subResult(Observer<ResultCodeInt> subscriber);

    //注册观察者
    void sub(Observer<AfterSalesCenterReturn> subscriber);
}