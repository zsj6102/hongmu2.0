package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.Info.StoreDetail;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;

import com.colpencil.redwood.bean.result.CareReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IAboutView extends ColpencilBaseView {


    void loadFail(String message);

    void getStoreDetail(ResultInfo<StoreDetail> goodsTypeResult);

    void getLike(ResultInfo<List<ItemStoreFans>> like);

    void getFans(ResultInfo<List<ItemStoreFans>> fans);

    void care(CareReturn result);

    void uncare(CareReturn reult);

    void operate(CareReturn result);
}
