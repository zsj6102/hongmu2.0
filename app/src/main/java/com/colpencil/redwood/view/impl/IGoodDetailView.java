package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.GoodInfo;
import com.colpencil.redwood.bean.result.CommonResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface IGoodDetailView extends ColpencilBaseView {

    /**
     * 加入购物车
     *
     * @param result
     */
    void addtocarsuccess(EntityResult<String> result);

    /**
     * 查看商品收藏状态
     */
    void checkState(EntityResult<String> result);

    /**
     * 收藏商品
     */
    void keepGoodResult(EntityResult<String> result);

    /**
     * 获取分享链接
     *
     * @param result
     */
    void shareResult(CommonResult result);

    /**
     * 商品详情
     *
     * @param result
     */
    void loadGoods(GoodInfo result);
}
