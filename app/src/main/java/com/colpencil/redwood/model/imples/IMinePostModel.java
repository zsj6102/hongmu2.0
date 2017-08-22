package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;

public interface IMinePostModel extends ColpencilModel {
    void loadMinePost(Map<String,String> map);

    void subData(Observer<ResultInfo<List<MinePostItem>>> observer);
}
