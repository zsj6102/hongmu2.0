package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.bean.ListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface ICyclopediaView extends ColpencilBaseView {

    /**
     * 加载标签
     *
     * @param result
     */
    void loadTag(List<CategoryVo> result);

    /**
     * 加载第一页
     */
    void refresh(List<CyclopediaInfoVo> list);

    /**
     * 加载更多
     */
    void loadmore(List<CyclopediaInfoVo> list);

    /**
     * 加载错误
     *
     * @param result
     */
    void loadError(ListResult<CyclopediaInfoVo> result);

}
