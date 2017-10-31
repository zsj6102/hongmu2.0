package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.PayType;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;
import java.util.Map;

public interface IPayView extends ColpencilBaseView{
    void fail(int code, String msg);

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

    void payType(List<PayType> pays);
}
