package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.ReplyToItem;

import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;

public interface IReplyDetailModel extends ColpencilModel {
    void getDetail(Map<String,String> map);

    void subDetail(Observer<ResultInfo<List<ReplyToItem>>> observer);


    void getAddReply(Map<String,String> params);

    void subAddResult(Observer<AddResult> observer);
}
