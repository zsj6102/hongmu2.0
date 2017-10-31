package com.colpencil.redwood.model.imples;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.SizeColorInfo;

import java.util.List;
import java.util.Map;

import rx.Subscriber;

public interface IPublishModel {
    void loadSize(int id);

    void subSize(Subscriber<SizeColorInfo> subscriber);

    void loadCatList(int id);

    void sub(Subscriber<CatListBean> subscriber);


    void loadZcList(int id);

    void subZcList(Subscriber<ResultInfo<List<CoverSpecialItem>>> subscriber);

    void loadPro(Map<String,String> map);

    void subPro(Subscriber<ResultInfo<String>> observer);
}
