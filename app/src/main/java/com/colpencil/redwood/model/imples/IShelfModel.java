package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.PlainRack;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;

public interface IShelfModel extends ColpencilModel {
    void getNormalShelf(Map<String,String> map);

    void subNormal(Observer<ResultInfo<List<PlainRack>>> observer);

}
