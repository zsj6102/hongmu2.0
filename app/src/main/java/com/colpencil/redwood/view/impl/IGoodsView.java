package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.GoodsItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：商品
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface IGoodsView extends ColpencilBaseView {

    /**
     * 加载
     *
     * @param data
     */

    void refresh(List<GoodsItem> data);


    void resultInfor(String code, String msg);

    void deleteResult(EntityResult<String> result);

    void loadMore(List<GoodsItem> data);

    void deleteAll(EntityResult<String> result);
}
