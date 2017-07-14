package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.result.CommonResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IPostView extends ColpencilBaseView {

    /**
     * 圈子的分类
     * @param categoryVos
     */
    void loadTag(List<CategoryVo> categoryVos);

    /**
     * 帖子的发布结果
     */
    void subResult(CommonResult result);

}
