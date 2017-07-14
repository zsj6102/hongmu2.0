package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.AnnounceResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 陈宝
 * @Description:公告列表的view接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IAnnounceView extends ColpencilBaseView {

    /**
     * 公告结果
     *
     * @param result
     */
    void loadSuccess(AnnounceResult result);

}
