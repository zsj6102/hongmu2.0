package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CommentList;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description:评论列表的view
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICommentListView extends ColpencilBaseView{

    /**
     * 获取评论列表
     * @param commentLists
     */
    void loadSuccess(List<CommentList> commentLists);
    void loadError();

}
