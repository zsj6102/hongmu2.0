package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CustomReturn;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：我的定制
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IMyCustomFragmentModel extends ColpencilModel {

    //获取数据
    void loadData(int pageNo, int pageSize, int type);

    //注册观察者
    void sub(Observer<CustomReturn> subscriber);

    //获取数据
    void payInfor(int order_id);


    //注册观察者
    void subPay(Observer<PayKeyRetrun> subscriber);

    //删除
    void delete(int custom_id);

    void subDelete(Observer<EntityResult<String>> observer);
}