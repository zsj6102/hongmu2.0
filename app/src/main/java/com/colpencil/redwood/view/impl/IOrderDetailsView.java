package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.OrderDetailsReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.Map;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/22 10 39
 */
public interface IOrderDetailsView extends ColpencilBaseView {

    void result(OrderDetailsReturn orderDetailsReturn);

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
