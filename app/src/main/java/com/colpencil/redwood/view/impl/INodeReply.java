package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.FirstComment;
import com.colpencil.redwood.bean.NodeReplyItem;

import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface INodeReply extends ColpencilBaseView{
    void loadFail(String message);

    void loadMore(ResultInfo<List<NodeReplyItem>> info);

    void refresh(ResultInfo<List<NodeReplyItem>> info);

    /**
     * 评论总数
     *
     * @param result
     */
    void loadNums(FirstComment result);


    void addComment(AddResult result);
}
