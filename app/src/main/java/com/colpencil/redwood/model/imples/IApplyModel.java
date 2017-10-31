package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.ResultInfo;

import java.util.Map;

import rx.Subscriber;


public interface IApplyModel {
    void loadRegion(int id);

    void subRegion(Subscriber<AddresBean> subscriber);

    void loadCatList(int id);

    void sub(Subscriber<CatListBean> subscriber);

    void loadPro(Map<String,String> map);

    void subPro(Subscriber<ResultInfo<String>> observer);
}
