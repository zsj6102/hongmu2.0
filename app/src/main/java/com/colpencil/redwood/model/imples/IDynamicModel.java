package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.DynamicResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;

public interface IDynamicModel extends ColpencilModel{

    /**
     * 获取品牌商品/名师名匠的新品
     */
    void getDynamic(HashMap<String,Integer> intparams, HashMap<String, RequestBody> strparams);

    void subGetDynamic(Observer<DynamicResult> observer);

    /**
     * 我的收藏(品牌和名师共用)
     */

    void getCollection(HashMap<String,String> params);

    void subCollection(Observer<DynamicResult> observer);


    /**
     * 品牌和名师店铺动态
     * @param params
     */
    void getStoreDynamic(HashMap<String,String> params);

    void subStoreDynamic(Observer<DynamicResult> observer);




}
