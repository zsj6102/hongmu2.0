package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.SellApply;
import com.colpencil.redwood.bean.result.ApplyReturn;
import com.colpencil.redwood.bean.result.ResultInfo;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Subscriber;


public interface IApplyModel {
    void loadRegion(int id);

    void subRegion(Subscriber<AddresBean> subscriber);

    void loadCatList(int id);

    void sub(Subscriber<CatListBean> subscriber);

}
