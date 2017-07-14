package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CouponsResult;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface IMyCouponsFragmentView extends ColpencilBaseView {
    /**
     * 刷新
     *
     * @param result
     */
    void loadResult(CouponsResult result);

    void fail(String code, String msg);

    /**
     * 兑换结果
     *
     * @param result
     */
    void changeResult(EntityResult<String> result);
}
