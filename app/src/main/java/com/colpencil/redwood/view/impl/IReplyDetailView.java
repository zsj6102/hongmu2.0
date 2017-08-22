package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.ReplyToItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IReplyDetailView extends ColpencilBaseView {
    void loadFail(String message);

    void loadMore(ResultInfo<List<ReplyToItem>> info);

    void refresh(ResultInfo<List<ReplyToItem>> info);

    void addReply(AddResult result);
}
