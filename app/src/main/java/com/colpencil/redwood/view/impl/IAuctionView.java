package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.JiashangItem;

import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IAuctionView extends ColpencilBaseView {

    void refresh(ResultInfo<List<JiashangItem>> data);

    void loadMore(ResultInfo<List<JiashangItem>> data);

    void loadFail(String msg);
}
