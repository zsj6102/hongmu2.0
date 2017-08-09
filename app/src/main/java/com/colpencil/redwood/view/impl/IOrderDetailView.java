package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.OrderDetailReturn;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.Map;

public interface IOrderDetailView extends ColpencilBaseView {
    void result(OrderDetailReturn orderDetailsReturn);

    void fail(int code, String msg);

    void success(String msg, int mState);

    /**
     * 通过微信进行支付
     */
    void payByWeChat(Map<String, String> map);

    /**
     * 支付宝进行支付
     */
    void payByAlipay(String reStr);

    /**
     * 银联支付
     */
    void payByUnion(String tn, String mode);
}
