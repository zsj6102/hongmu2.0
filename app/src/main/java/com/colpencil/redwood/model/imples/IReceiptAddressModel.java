package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddressReturn;
import com.colpencil.redwood.bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IReceiptAddressModel extends ColpencilModel {

    //获取数据
    void loadData();

    //注册观察者
    void sub(Observer<AddressReturn> subscriber);

    void deleteById(int addrId);

    void subResult(Observer<Result> subscriber);
}