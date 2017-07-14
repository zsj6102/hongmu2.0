package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.RefundReason;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/9 16 09
 */
public interface IAfterSalesView extends ColpencilBaseView {
    /**
     * 退款理由
     */
    void loadReason(List<RefundReason> refundReasons, int type);

    void fail(int code, String msg);

    void submitSuccess(String msg);
}
