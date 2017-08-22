package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatListBean;

import rx.Subscriber;


public interface IApplyModel {
    void loadRegion(int id);

    void subRegion(Subscriber<AddresBean> subscriber);

    void loadCatList(int id);

    void sub(Subscriber<CatListBean> subscriber);

}
