package com.colpencil.redwood.present.home;

import android.util.Log;

import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.model.AllAuctionItemModel;
import com.colpencil.redwood.model.imples.IAllAuctionItemModel;
import com.colpencil.redwood.view.impl.AllAuctionItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;

public class AllAuctionItemPresent extends ColpencilPresenter<AllAuctionItemView> {
    private IAllAuctionItemModel allAuctionItemModel;
    public AllAuctionItemPresent(){
        allAuctionItemModel=new AllAuctionItemModel();
    }
    public void getAllGoods(HashMap<String, RequestBody> strparams, HashMap<String, Integer> intparams){
        allAuctionItemModel.getAllGoods(strparams,intparams);
        Observer<AllGoodsResult> observer=new Observer<AllGoodsResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("pretty",e.getMessage());
            }

            @Override
            public void onNext(AllGoodsResult allGoodsResult) {

                if(allGoodsResult.getCode()==0){
                    mView.getAllGoods(allGoodsResult);
                }else{
                    mView.loadFail(allGoodsResult.getMessage());
                }
            }
        };
        allAuctionItemModel.subGetAllGoods(observer);
    }

}
