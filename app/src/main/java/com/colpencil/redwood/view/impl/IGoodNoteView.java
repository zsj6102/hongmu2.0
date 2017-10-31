package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.ImageSpan;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import static com.unionpay.mobile.android.global.a.I;

public interface IGoodNoteView extends ColpencilBaseView {
    void loadFail(String msg);
    void loadUrl(ResultInfo<ImageSpan> info);
}
