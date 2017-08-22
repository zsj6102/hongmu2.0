package com.colpencil.redwood.model;

import com.colpencil.redwood.bean.ArticalItem;

import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;

public interface IEncycloModel extends ColpencilModel{
    void getArticalList(Map<String,String> map);

    void subNormal(Observer<ResultInfo<List<ArticalItem>>> observer);
}
