package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.GoodComment;
import com.colpencil.redwood.bean.NumReturn;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:商品评论的View接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IGoodRightView extends ColpencilBaseView {

    /**
     * 获取商品评论
     *
     * @param comments
     */
    void loadMore(List<GoodComment> comments);

    void refresh(List<GoodComment> comments);

    /**
     * 评论总数
     *
     * @param result
     */
    void loadNums(NumReturn result);

    void addComment(AddResult result);

    void addLike(ResultInfo<String> resultInfo);
}
