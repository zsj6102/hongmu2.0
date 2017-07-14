package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.AllSpecialResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import rx.Observer;

public interface AllSpecialItemView extends ColpencilBaseView {

    void loadSuccess();

    void loadFail(String message);
    void loadMore(AllSpecialResult allSpecialResult);
    void refresh(AllSpecialResult allSpecialResult);
}
