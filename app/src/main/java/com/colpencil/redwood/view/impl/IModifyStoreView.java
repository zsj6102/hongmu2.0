package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.Info.StoreInfo;
import com.colpencil.redwood.bean.ResultInfo;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;


public interface IModifyStoreView extends ColpencilBaseView {
   void loadFail(String msg);

    void loadStoreInfo(ResultInfo<StoreInfo> info);

    void modifyResult(ResultInfo<StoreInfo> info);

    void loadCat(CatListBean catListBean);

    void loadRegion(AddresBean bean);
}
