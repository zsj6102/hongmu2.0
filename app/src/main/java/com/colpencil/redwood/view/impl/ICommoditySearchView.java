package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.SortList;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：商品搜索
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 16 47
 */
public interface ICommoditySearchView extends ColpencilBaseView {
    /**
     * 刷新
     *
     * @param data
     */
    void refresh(List<HomeGoodInfo> data);

    /**
     * 加载
     *
     * @param data
     */

    void loadMore(List<HomeGoodInfo> data);

    /**
     * 获取筛选信息
     *
     * @param data
     */

    void loadSort(List<SortList> data);
    /**
     *请求失败
     */
    void fail(String msg);
}

