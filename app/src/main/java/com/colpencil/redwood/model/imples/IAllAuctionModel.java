package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.GoodsTypeResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface IAllAuctionModel extends ColpencilModel {

    //获取商品类型
    void getGoodsType();

    void subGetGoodsType(Observer<GoodsTypeResult> observer);

}
