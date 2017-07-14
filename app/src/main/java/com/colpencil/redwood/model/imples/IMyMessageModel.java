package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.MessageReturn;
import com.colpencil.redwood.bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：我的消息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IMyMessageModel extends ColpencilModel {

    //获取数据
    void loadData(int pageNo, int pageSize);

    //注册观察者
    void sub(Observer<MessageReturn> subscriber);

    //删除数据
    void removeAllMsg();

    //获取数据
    void removeById(int msgId);

    //注册观察者
    void subMsg(Observer<Result> subscriber);
}