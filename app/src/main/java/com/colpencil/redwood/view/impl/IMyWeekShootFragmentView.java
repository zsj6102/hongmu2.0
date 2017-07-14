package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MyWeekShootItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;
import java.util.Map;

/**
 * 描述：订单中心
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface IMyWeekShootFragmentView extends ColpencilBaseView {
    /**
     * 刷新
     *
     * @param data
     */
    void refresh(List<MyWeekShootItem> data);

    /**
     * 加载
     *
     * @param data
     */

    void loadMore(List<MyWeekShootItem> data);

    void fail(int code, String msg);

    void deleteResult(EntityResult<String> result);

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
}
