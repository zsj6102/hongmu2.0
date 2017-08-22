package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;

import com.colpencil.redwood.bean.result.CareReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IFansAndLike extends ColpencilBaseView{

    void loadFail(String message);

    void loadMore(ResultInfo<List<ItemStoreFans>> info);

    void refresh(ResultInfo<List<ItemStoreFans>> info);

    void operate(CareReturn result);
}
