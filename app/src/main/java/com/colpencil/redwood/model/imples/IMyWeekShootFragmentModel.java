package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MyWeekShootReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：我的订单
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IMyWeekShootFragmentModel extends ColpencilModel {

    //获取数据
    void loadData(int pageNo, int pageSize, int type);

    //注册观察者
    void sub(Observer<MyWeekShootReturn> subscriber);

    //删除记录
    void deleteById(int id);

    void subDelete(Observer<EntityResult<String>> observer);

    //立即付款
    void payInfor(int ote_id);

    void subPay(Observer<PayKeyRetrun> observer);
}