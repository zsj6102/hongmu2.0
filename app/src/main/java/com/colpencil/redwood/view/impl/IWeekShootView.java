package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.WeekAuctionList;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：周拍fragment
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 10 44
 */
public interface IWeekShootView extends ColpencilBaseView{
    /**
     * 刷新
     * @param data
     */
    void refresh(List<WeekAuctionList> data);

    /**
     * 加载
     * @param data
     */

    void loadMore(List<WeekAuctionList> data);

    void loadError();
}
