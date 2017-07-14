package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.AnnounceResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @Description:公告的model接口
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IAnnounceModel extends ColpencilModel{

    void loadUrl();
    void sub(Observer<AnnounceResult> observer);

    /**
     * 商品描述
     * @param goodsId
     */
    void loadGoodMiddle(int goodsId);
    void subGood(Observer<AnnounceResult> observer);

}
