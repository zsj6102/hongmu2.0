package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:百科搜索结果View接口
 * @Email DramaScript@outlook.com
 * @date 2016/8/4
 */
public interface ISearchCycloView extends ColpencilBaseView {
    /**
     * 搜索百科结果
     * @param cyclopediaInfoVos
     */
    void loadSuccess(List<CyclopediaInfoVo> cyclopediaInfoVos);

    void loadError(String msg);
}
