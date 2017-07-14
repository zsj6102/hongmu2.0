package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @Description: 我的积分
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/8
 */
public interface IMyCouponsModel extends ColpencilModel {

    /**
     * 根据id，删除收藏记录
     */
    void getCount();

    void sub(Observer<Result> subscriber);
}