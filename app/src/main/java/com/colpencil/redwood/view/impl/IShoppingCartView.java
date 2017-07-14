package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CartItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：购物车
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/14 11 20
 */
public interface IShoppingCartView extends ColpencilBaseView {
    /**
     * 购物车数据请求回调
     */
    void loadShoppingCartData(List<CartItem> datas);

    void reLoadData(String msg);

    void deletefail(String code, String msg);

    void loadFail(String code, String msg);
}
