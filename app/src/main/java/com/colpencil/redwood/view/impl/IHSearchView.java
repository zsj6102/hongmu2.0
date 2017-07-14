package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.Cat;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：纵向搜索
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/13 17 00
 */
public interface IHSearchView extends ColpencilBaseView {
    /**
     * 加载ListView 数据
     */
    void loadListViewData(List<Cat> datas);
    /**
     * 数据加载失败提示
     */
    void fail(String msg);

}
