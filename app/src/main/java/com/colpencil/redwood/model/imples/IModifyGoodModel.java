package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.Info.ApplyGoodInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscriber;

public interface IModifyGoodModel extends ColpencilModel {
    void loadGood(int id);

    void subGood(Observer<ResultInfo<ApplyGoodInfo>> observer);
    void loadCatList(int id);

    void sub(Subscriber<CatListBean> subscriber);

    void loadPro(Map<String,String> map);

    void subPro(Subscriber<ResultInfo<String>> observer);


    void loadZcList(int id);

    void subZcList(Subscriber<ResultInfo<List<CoverSpecialItem>>> subscriber);
}
