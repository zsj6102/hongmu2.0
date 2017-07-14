package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.StatisticResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description:我的View接口
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICircleRightView extends ColpencilBaseView {

    /**
     * 帖子统计
     * @param result
     */
    void loadStatistics(StatisticResult result);
    void loadError();

}
