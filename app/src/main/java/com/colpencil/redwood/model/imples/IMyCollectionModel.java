package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：商品
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IMyCollectionModel extends ColpencilModel {

    //获取数据
    void removeAllCollection();

    //注册观察者
    void sub(Observer<Result> subscriber);
}