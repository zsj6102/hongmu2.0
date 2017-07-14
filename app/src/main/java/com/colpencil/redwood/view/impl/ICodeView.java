package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CodeBean;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface ICodeView extends ColpencilBaseView {

    void codeResult(EntityResult<CodeBean> result);

}
