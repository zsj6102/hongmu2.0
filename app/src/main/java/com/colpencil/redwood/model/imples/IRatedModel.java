package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.RatedItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;

public interface IRatedModel extends ColpencilModel {
    void getRatedList(Map<String,String> map);

    void subNormal(Observer<ResultInfo<List<RatedItem>>> observer);
}
