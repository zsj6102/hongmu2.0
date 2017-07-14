package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.AdResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface ISpeedModel extends ColpencilModel {
    /**
     * 获取广告
     */
    void getAd(String type);

    void subGetAd(Observer<AdResult> observer);
}
