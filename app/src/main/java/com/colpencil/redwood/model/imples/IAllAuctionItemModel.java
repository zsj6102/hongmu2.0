package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;

public interface IAllAuctionItemModel extends ColpencilModel{

    /**
     * 获取所有拍品接口
     */
    void getAllGoods(HashMap<String,RequestBody> strparams,HashMap<String,Integer> intparams);

    void subGetAllGoods(Observer<AllGoodsResult> observer);
}
