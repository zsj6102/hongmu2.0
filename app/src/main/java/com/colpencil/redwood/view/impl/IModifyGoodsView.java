package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.Info.ApplyGoodInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IModifyGoodsView extends ColpencilBaseView {
    void loadGood(ResultInfo<ApplyGoodInfo> result);

    void loadError(String msg);


    void loadCat(CatListBean catListBean);


    void loadPro(ResultInfo<String> result);

    void loadZcList(ResultInfo<List<CoverSpecialItem>> resultInfo);
}
