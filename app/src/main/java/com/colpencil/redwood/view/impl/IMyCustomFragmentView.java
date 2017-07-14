package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.Custom;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;
import java.util.Map;

/**
 * 描述：我的定制
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface IMyCustomFragmentView extends ColpencilBaseView {
    /**
     * 刷新
     *
     * @param data
     */
    void refresh(List<Custom> data);

    /**
     * 加载
     *
     * @param data
     */

    void loadMore(List<Custom> data);

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
     *
     * @param tn
     * @param mode
     */
    void payByUnion(String tn, String mode);

    void delete(EntityResult<String> result);
}
