package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CategoryVo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IHomeGoodsView extends ColpencilBaseView {
    /**
     * 获取首页的分类
     *
     * @param taglist
     */
    void loadTag(List<CategoryVo> taglist);

    void loadError(String msg);
}
