package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.GoodComment;
import com.colpencil.redwood.bean.ListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:商品评论的Model的接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IGoodRightModel extends ColpencilModel {

    /**
     * 商品评论数量
     *
     * @param goodId
     * @param page
     * @param pageSize
     */
    void loadComment(String goodId, int page, int pageSize);

    void sub(Observer<ListResult<GoodComment>> observer);

    /**
     * 商品评论总数量
     *
     * @param goods_id
     */
    void loadCommentsNum(int goods_id);

    void subCommentsNum(Observer<EntityResult<String>> observer);
}
