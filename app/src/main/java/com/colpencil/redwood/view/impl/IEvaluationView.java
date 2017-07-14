package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.ResultCodeInt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface IEvaluationView extends ColpencilBaseView {

    /**
     * 提交返回的结果
     */
    void submitResult(ResultCodeInt result);

}
