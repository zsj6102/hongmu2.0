package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.Margin;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.Settled;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;

import rx.Observer;

public interface IMarginModel extends ColpencilModel {
    void loadInfo();
    void sub(Observer<ResultInfo<Margin>> observer);

    void loadSettled();

    void subSettled(Observer<ResultInfo<List<Settled>>> observer);
}
