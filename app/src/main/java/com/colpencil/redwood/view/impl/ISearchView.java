package com.colpencil.redwood.view.impl;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:搜索界面的View接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ISearchView extends ColpencilBaseView {

    /**
     * 热门搜索
     *
     * @param list
     */
    void hot(List<String> list);

    void history(List<String> list);
}
