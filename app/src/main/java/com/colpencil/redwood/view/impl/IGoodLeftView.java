package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.GoodInfo;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:商品详情的Fragment的View接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IGoodLeftView extends ColpencilBaseView {

    /**
     * 获取商品信息
     *
     * @param info
     */
    void loadGoodInfo(GoodInfo info);

    void loadGoodInfoError();

    /**
     * 获取推荐商品
     *
     * @param goodlist
     */
    void loadRecommend(List<HomeGoodInfo> goodlist);

    void loadRecommendError();

    void loadDetail(AnnounceResult result);

}
