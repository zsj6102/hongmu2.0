package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CouponsResult;
import com.colpencil.redwood.bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IGetCouponsModel extends ColpencilModel {

    //获取数据
    void loadData(int pageNo, int pageSize);

    //注册观察者
    void sub(Observer<CouponsResult> subscriber);

    void points(int point, String cpns_sn, int cpns_id);

    //注册观察者
    void subPoints(Observer<Result> subscriber);
}