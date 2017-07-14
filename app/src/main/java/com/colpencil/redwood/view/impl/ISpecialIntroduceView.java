package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.SpecialIntroduceResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface ISpecialIntroduceView extends ColpencilBaseView {

    void loadSuccess(SpecialIntroduceResult specialIntroduceResult);

    void loadFail(String message);


}
