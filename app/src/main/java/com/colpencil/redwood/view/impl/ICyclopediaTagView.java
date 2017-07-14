package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:百科标签的view
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICyclopediaTagView extends ColpencilBaseView {
    /**
     * 加载第一页
     */
    void refresh(List<CyclopediaInfoVo> list);

    /**
     * 加载更多
     */
    void loadmore(List<CyclopediaInfoVo> list);

    void loadError();
}
