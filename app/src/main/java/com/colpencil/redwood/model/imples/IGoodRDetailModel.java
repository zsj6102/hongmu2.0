package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.GoodReply;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.Map;

import rx.Observer;


public interface IGoodRDetailModel extends ColpencilModel {
    void loadDetail(Map<String,String> map);

    void subDetail(Observer<ResultInfo<GoodReply>> observer);

    void addDiscuss(Map<String,String> map);

    void subAdd(Observer<ResultInfo<String>> observer);
}
