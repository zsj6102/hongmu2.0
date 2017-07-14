package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.BrowsingCyclopediaDate;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：百科浏览记录
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface IBrowsingCyclopediaView extends ColpencilBaseView {
    /**
     * 刷新
     *
     * @param data
     */
    void refresh(List<BrowsingCyclopediaDate> data);

    /**
     * 加载
     *
     * @param data
     */

    void loadMore(List<BrowsingCyclopediaDate> data);

    void failed(String code, String msg);

    /**
     * 删除
     *
     * @param result
     */
    void delete(EntityResult<String> result);
}
