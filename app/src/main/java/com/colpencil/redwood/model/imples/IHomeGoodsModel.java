package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.ListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface IHomeGoodsModel extends ColpencilModel {
    /**
     * 获取二期商品所有标签
     */
    void loadGoodsAllTag();

    void subAllGoodsTag(Observer<ListResult<CategoryVo>> observer);
    /**
     * 获取二期商品我的所有标签
     */
    void loadMyGoodSTag();

    void subMyGoodsTag(Observer<ListResult<CategoryVo>> observer);
}
