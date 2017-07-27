package com.colpencil.redwood.model.imples;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.SizeColorInfo;

import rx.Subscriber;

public interface IPublishModel {
    void loadSize(int id);

    void subSize(Subscriber<SizeColorInfo> subscriber);

    void loadCatList(int id);

    void sub(Subscriber<CatListBean> subscriber);

}
