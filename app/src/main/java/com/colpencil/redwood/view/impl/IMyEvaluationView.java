package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.Comment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface IMyEvaluationView extends ColpencilBaseView{
    /**
     * 刷新
     * @param data
     */
    void refresh(List<Comment> data);

    /**
     * 加载
     * @param data
     */

    void loadMore(List<Comment> data);

    void fail(String code, String msg);
}
