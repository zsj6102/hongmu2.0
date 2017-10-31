package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;

public interface IMinePostModel extends ColpencilModel {
    void loadMinePost(Map<String,String> map);

    void subData(Observer<ResultInfo<List<MinePostItem>>> observer);

    /**
     * 发表评论
     *
     * @param comContent
     * @param ote_id
     */
    void submitComments(String comContent, int ote_id);

    void subSubmit(Observer<EntityResult<String>> observer);

    /**
     * 点赞
     *
     * @param ote_id
     */
    void like(int ote_id);

    void subLike(Observer<EntityResult<String>> observer);
}
