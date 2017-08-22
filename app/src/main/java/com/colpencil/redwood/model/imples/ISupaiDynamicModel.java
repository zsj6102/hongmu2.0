package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

public interface ISupaiDynamicModel extends ColpencilModel {
    /**
     * 我的收藏(速拍)
     */

    void getSupaiCol(HashMap<String,String> params);

    void subSupaiCol(Observer<AllGoodsResult> observer);

    /**
     * 速拍我的拍品动态
     * @param params
     */
    void getSupaiDynamic(HashMap<String,String> params);

    void subSupaiDynamic(Observer<AllGoodsResult> observer);

    /**
     * 获取评论结果
     */
    void getAddCommentResult(Map<String,String> params);

    void subAddResult(Observer<AddResult> observer);

    void getLikeResult(Map<String,String> params);

    void subLike(Observer<AddResult> observer);
}
