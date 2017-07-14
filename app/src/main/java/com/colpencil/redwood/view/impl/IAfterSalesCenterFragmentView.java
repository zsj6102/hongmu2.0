package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.AfterSalesCenterItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：订单中心
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface IAfterSalesCenterFragmentView extends ColpencilBaseView {
    /**
     * 刷新
     *
     * @param data
     */
    void refresh(List<AfterSalesCenterItem> data);

    /**
     * 加载
     *
     * @param data
     */

    void loadMore(List<AfterSalesCenterItem> data);

    void callPhone(String phone);

    void success(String msg);

    void fail(int code, String msg);
}
