package com.colpencil.redwood.present.mine;


import com.colpencil.redwood.bean.CardWallInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.model.CardModel;
import com.colpencil.redwood.model.imples.ICardModel;
import com.colpencil.redwood.view.impl.ICardView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Observer;

public class CardPresenter extends ColpencilPresenter<ICardView> {
    private ICardModel cardModel;
    public CardPresenter(){
        cardModel = new CardModel();
    }
    public void getCardStore(final int pageNo, HashMap<String,String> params){
        cardModel.loadCardStore(params);
        Observer<CardWallInfo> observer = new Observer<CardWallInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadFail(e.getMessage());
            }

            @Override
            public void onNext(CardWallInfo cardWallInfo) {
                if(cardWallInfo.getCode()==0){
                    if(pageNo==1){
                        mView.refresh(cardWallInfo);
                    }else{
                        mView.loadMore(cardWallInfo);
                    }
                }else{
                    mView.loadFail(cardWallInfo.getMessage());
                }
            }
        };
        cardModel.subCardStore(observer);
    }

    public void getCardMine(final int pageNo,HashMap<String,String> params){
        cardModel.loadaCardMime(params);
        Observer<CardWallInfo> observer = new Observer<CardWallInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CardWallInfo cardWallInfo) {
                if(cardWallInfo.getCode()==0){
                    if(pageNo==1){
                        mView.refresh(cardWallInfo);
                    }else{
                        mView.loadMore(cardWallInfo);
                    }
                }else{
                    mView.loadFail(cardWallInfo.getMessage());
                }
            }
        };
        cardModel.subCardMine(observer);
    }

    public void getCareReturn(HashMap<String,String> params){
        cardModel.getCareStatus(params);
        Observer<CareReturn> observer = new Observer<CareReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CareReturn careReturn) {
                 mView.operate(careReturn);
            }
        };
        cardModel.subCare(observer);
    }

}
