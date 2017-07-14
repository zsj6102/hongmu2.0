package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:搜索商品结果
 * @Email DramaScript@outlook.com
 * @date 2016/8/5
 */
public interface ISearchGoodView extends ColpencilBaseView {
    /**
     * 刷新
     *
     * @param list
     */
    void refresh(List<HomeGoodInfo> list);

    /**
     * 加载更多
     *
     * @param list
     */
    void loadMore(List<HomeGoodInfo> list);

    void loadError();

}
