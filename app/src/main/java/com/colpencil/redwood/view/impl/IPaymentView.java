package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.PayForReturn;
import com.colpencil.redwood.bean.result.MemberCouponResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.Map;

/**
 * @author 曾 凤
 * @Description: 订单确认
 * @Email 20000263@qq.com
 * @date 2016/8/3
 */
public interface IPaymentView extends ColpencilBaseView {

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

    /**
     * 获取订单详情
     *
     * @param payForReturn
     */
    void loadOrdersInfo(PayForReturn payForReturn);

    /**
     * 优惠券结果
     */
    void loadCouponResult(MemberCouponResult result);
}
