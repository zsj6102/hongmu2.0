package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.ResultCodeInt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.io.File;
import java.util.List;

import rx.Observer;

public interface IEvaluationModel extends ColpencilModel {

    /**
     * 提交商品评论
     */
    void submitComment(int rate,int order_id, String goods_id, String content, List<File> files, int type);

    void sub(Observer<ResultCodeInt> observer);
}
