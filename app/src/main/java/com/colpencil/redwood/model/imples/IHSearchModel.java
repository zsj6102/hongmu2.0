package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CatReturnData;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/13 17 04
 */
public interface IHSearchModel extends ColpencilModel{
    /**
     * 加载ListView 数据
     */
    void loadListViewData();

    void subListView(Observer<CatReturnData> subscriber);
}
