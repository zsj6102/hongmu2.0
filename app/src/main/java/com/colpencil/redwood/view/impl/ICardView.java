package com.colpencil.redwood.view.impl;
import com.colpencil.redwood.bean.CardWallInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface ICardView  extends ColpencilBaseView {
    void loadSuccess();

    void loadFail(String message);

    void loadMore(CardWallInfo info);

    void refresh(CardWallInfo info);

    void operate(CareReturn result);
}
