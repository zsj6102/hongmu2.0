package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface ICategoryView extends ColpencilBaseView {

    /**
     * 移除我的
     */
    void operateResult(EntityResult<String> result);


    /**
     * 获取我的标签
     */
    void loadMyTag(List<CategoryVo> list);

    /**
     * 获取所有标签
     */
    void loadAllTag(List<CategoryVo> list);
}
