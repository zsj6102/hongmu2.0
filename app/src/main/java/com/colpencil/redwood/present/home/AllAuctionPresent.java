package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.result.GoodsTypeResult;
import com.colpencil.redwood.model.AllAuctionModel;
import com.colpencil.redwood.model.imples.IAllAuctionModel;
import com.colpencil.redwood.view.impl.AllAuctionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

public class AllAuctionPresent extends ColpencilPresenter<AllAuctionView>{
    private IAllAuctionModel allAuctionModel;
    public AllAuctionPresent (){
        allAuctionModel=new AllAuctionModel();
    }

    public void getGoodsType(){
        allAuctionModel.getGoodsType();
        Observer<GoodsTypeResult> observer=new Observer<GoodsTypeResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GoodsTypeResult goodsTypeResult) {

                if(goodsTypeResult.getCode()==0){
                    mView.getGoodsType(goodsTypeResult);
                }else{
                    mView.loadFail(goodsTypeResult.getMessage());
                }
            }
        };
        allAuctionModel.subGetGoodsType(observer);
    }

}
