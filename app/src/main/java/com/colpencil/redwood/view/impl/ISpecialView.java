package com.colpencil.redwood.view.impl;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author QFZ
 * @deprecated 专场的View接口
 * @date 2017/3/3.
 */

public interface ISpecialView extends ColpencilBaseView{

    void loadSuccess(List<?> list);

    void loadError(String message,int code);
}
