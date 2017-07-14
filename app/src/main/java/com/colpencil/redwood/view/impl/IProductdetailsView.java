package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.BidderInfoVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.WeekPersonList;
import com.colpencil.redwood.bean.result.WeekShootDetailResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/11 09 27
 */
public interface IProductdetailsView extends ColpencilBaseView {
    /**
     * 加载头部内容
     */
    void loadHeaderData(WeekShootDetailResult result);

    /**
     * 竞拍者
     */
    void webUrl(EntityResult<String> result);

    /**
     * 获取竞拍人头像
     *
     * @param lists
     */
    void loadPersonList(List<WeekPersonList> lists);

    /**
     * 获取前三名竞拍人
     *
     * @param lists
     */
    void loadBidders(List<BidderInfoVo> lists);

    /**
     * 出价结果
     *
     * @param result
     */
    void submitResult(EntityResult<String> result);
}
