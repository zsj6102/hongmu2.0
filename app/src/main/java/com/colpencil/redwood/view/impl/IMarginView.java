package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.Margin;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.Settled;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;


public interface IMarginView extends ColpencilBaseView {
    void loadInfo(ResultInfo<Margin> info);
    void loadSettle(ResultInfo<List<Settled>> info);
}
