package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.JiashangItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;

public interface IAuctionModel extends ColpencilModel {
    void loadData(Map<String,String> map);

    void subData(Observer<ResultInfo<List<JiashangItem>>> observer);

    /**
     * 获取评论结果
     */
    void getAddCommentResult(Map<String,String> params);

    void subAddResult(Observer<AddResult> observer);

    void getLikeResult(Map<String,String> params);

    void subLike(Observer<AddResult> observer);
}
