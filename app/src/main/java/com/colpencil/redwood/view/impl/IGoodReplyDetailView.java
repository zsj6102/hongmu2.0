package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.GoodReply;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;


public interface IGoodReplyDetailView extends ColpencilBaseView {
    void loadFail(String msg);

    void addResult(ResultInfo<String> result);

    void loadMore(ResultInfo<GoodReply> resultInfo);

    void refresh(ResultInfo<GoodReply> resultInfo);

}
