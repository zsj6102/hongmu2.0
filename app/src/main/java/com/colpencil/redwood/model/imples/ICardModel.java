package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CardWallInfo;
import com.colpencil.redwood.bean.result.CareReturn;

import java.util.HashMap;
import rx.Observer;

public interface ICardModel {
    void loadCardStore(HashMap<String,String> params);

    void subCardStore(Observer<CardWallInfo> observer);

    void loadaCardMime(HashMap<String,String> params);

    void subCardMine(Observer<CardWallInfo> observer);

    void getCareStatus(HashMap<String,String> params);

    void subCare(Observer<CareReturn> observer);

    void loadCardMR(HashMap<String,String> params);

    void subCardMR(Observer<CardWallInfo> observer);
}
