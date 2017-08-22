package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;

public interface IAllAuctionItemModel extends ColpencilModel{

    /**
     * 获取所有拍品接口
     */
    void getAllGoods(HashMap<String,RequestBody> strparams,HashMap<String,Integer> intparams);

    void subGetAllGoods(Observer<AllGoodsResult> observer);

    /**
     * 获取评论结果
     */
    void getAddCommentResult(Map<String,String> params);

    void subAddResult(Observer<AddResult> observer);

    void getLikeResult(Map<String,String> params);

    void subLike(Observer<AddResult> observer);
}
