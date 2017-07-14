package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface ICycloAwardView extends ColpencilBaseView{
    /**
     * 公告结果
     *
     * @param result
     */
    void loadSuccess(EntityResult<String> result);
}
