package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.CustomGoodResult;
import com.colpencil.redwood.bean.result.CustomResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述： 官方推荐定制
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/7 17 24
 */
public interface ICustomModel extends ColpencilModel {

    /**
     * 获取图文详情
     * @param official_id
     */
    void loadData(int official_id);

    void sub(Observer<CustomResult> subscriber);

    /**
     * 获取两个商品的信息
     * @param official_id
     */
    void loadCustomGoods(int official_id);

    void subCustom(Observer<CustomGoodResult> observer);
}
