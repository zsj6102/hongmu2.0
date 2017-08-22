package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.ArticalItem;

import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IEncycloView extends ColpencilBaseView {
    void refresh(ResultInfo<List<ArticalItem>> result);

    void loadMore(ResultInfo<List<ArticalItem>> result);

    void loadFail(String msg);
}
