package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.PlainRack;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IShelfView extends ColpencilBaseView {
    void refresh(ResultInfo<List<PlainRack>> result);

    void loadMore(ResultInfo<List<PlainRack>> result);

    void loadFail(String msg);
}
