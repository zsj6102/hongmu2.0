package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.GoodInfoResult;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:商品详情
 * @Email DramaScript@outlook.com
 * @date 2016/8/8
 */
public interface IGoodDetailModel {

    /**
     * 加入购物车
     *
     * @param goodsId
     * @param productId
     * @param num
     */
    void addtocar(int goodsId, int productId, int num);

    void subAdd(Observer<EntityResult<String>> observer);

    /**
     * 获取商品收藏状态
     *
     * @param goods_id
     */
    void loadState(int goods_id);

    void subLoad(Observer<EntityResult<String>> observer);

    /**
     * 收藏商品
     */
    void keepGood(int goods_id);

    void subKeep(Observer<EntityResult<String>> observer);

    /**
     * 添加浏览记录
     */
    void browerGood(int goods_id);

    void subBrower(Observer<CommonResult> observer);

    /**
     * 获取分享内容
     *
     * @param good_id
     */
    void loadShare(int good_id);

    void subShare(Observer<CommonResult> observer);

    /**
     * 通过商品id 查询商品详细信息
     */
    void loadGoodInfo(int goodid);

    void subGoodInfo(Observer<GoodInfoResult> observer);

    /**
     * 记录分享
     *
     * @param type
     * @param platform
     * @param id
     */
    void addUpShare(int type, String platform, int id);

    void subAddup(Observer<EntityResult<String>> observer);
}
