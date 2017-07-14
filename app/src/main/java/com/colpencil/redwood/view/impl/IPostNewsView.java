package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IPostNewsView extends ColpencilBaseView {

    /**
     * 提交结果
     */
    void submitResult(EntityResult<String> result);

    /**
     * 分类
     */
    void loadTag(List<CategoryVo> categoryVos);
}
