package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.Address;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;
import rx.Subscriber;

import static android.R.attr.id;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IAddAddressModel extends ColpencilModel {

    //获取数据
    void addAddress(Address address);

    //注册观察者
    void subAdd(Observer<EntityResult<String>> observer);

    void updateAddress(Address address);

    void subUpdate(Observer<EntityResult<String>> observer);

    void loadRegion(int id);

    void subRegion(Observer<AddresBean> observer);
}