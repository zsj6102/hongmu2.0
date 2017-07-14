package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.bean.result.GoodInfoResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:商品详情的Model的接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IGoodLeftModel extends ColpencilModel {
    /**
     * 通过商品id 查询商品详细信息
     */
    void loadGoodInfo(String goodid);

    void subGoodInfo(Observer<GoodInfoResult> observer);

    /**
     * 推荐商品
     *
     * @param tagid
     * @param page
     * @param pageSize
     */
    void loadRecommend(int tagid, int page, int pageSize);

    void subRecommend(Observer<ListResult<HomeGoodInfo>> observer);

    /**
     * 商品描述
     * @param goodsId
     */
    void loadGoodDetail(int goodsId);
    void subDetail(Observer<AnnounceResult> observer);

}
