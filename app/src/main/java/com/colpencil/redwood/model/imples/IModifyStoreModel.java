package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.Info.StoreInfo;
import com.colpencil.redwood.bean.ResultInfo;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscriber;

public interface IModifyStoreModel extends ColpencilModel{
    void loadStoreInfo();

    void subStoreInfo(Observer<ResultInfo<StoreInfo>> observer);

    void loadModify(HashMap<String, RequestBody> params);

    void subModify(Observer<ResultInfo<StoreInfo>> observer);

    void loadCatList(int id);

    void sub(Subscriber<CatListBean> subscriber);

    void loadRegion(int id);

    void subRegion(Subscriber<AddresBean> subscriber);
}
