package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.RatedItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IRatedView extends ColpencilBaseView {
    void refresh(ResultInfo<List<RatedItem>> result);

    void loadMore(ResultInfo<List<RatedItem>> result);

    void loadFail(String msg);
}
