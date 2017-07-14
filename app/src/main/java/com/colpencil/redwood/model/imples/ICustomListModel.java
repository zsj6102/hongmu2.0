package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.OfficialResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface ICustomListModel extends ColpencilModel {

    /**
     * 获取列表
     */
    void loadGoods();

    void sub(Observer<OfficialResult> observer);
}
