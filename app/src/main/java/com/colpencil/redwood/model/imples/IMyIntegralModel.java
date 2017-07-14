package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.IntegralReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @Description: 我的积分
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/3
 */
public interface IMyIntegralModel extends ColpencilModel{

    //获取数据
    void loadData(int pageNo, int pageSize);

    //注册观察者
    void sub(Observer<IntegralReturn> subscriber);
}
