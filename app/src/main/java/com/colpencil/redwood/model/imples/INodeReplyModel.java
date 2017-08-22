package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.FirstComment;
import com.colpencil.redwood.bean.NodeReplyItem;

import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;

public interface INodeReplyModel extends ColpencilModel {
    void getNodeReply(Map<String,String>map);

    void subReply(Observer<ResultInfo<List<NodeReplyItem>>> observer);

    void loadCommentsNum(Map<String,String> map);

    void subCommentsNum(Observer<FirstComment> observer);

    void getAddCommentResult(Map<String,String> params);

    void subAddResult(Observer<AddResult> observer);
}
