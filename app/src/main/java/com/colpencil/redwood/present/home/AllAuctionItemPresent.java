package com.colpencil.redwood.present.home;

import android.util.Log;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.bean.result.OrderPayInfo;
import com.colpencil.redwood.model.AllAuctionItemModel;
import com.colpencil.redwood.model.imples.IAllAuctionItemModel;
import com.colpencil.redwood.view.impl.AllAuctionItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;
import rx.exceptions.CompositeException;
import rx.plugins.RxJavaPlugins;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class AllAuctionItemPresent extends ColpencilPresenter<AllAuctionItemView> {
    private IAllAuctionItemModel allAuctionItemModel;
    public AllAuctionItemPresent(){
        allAuctionItemModel=new AllAuctionItemModel();
    }
    public void getAllGoods(final int page,Map<String,String> map){
        allAuctionItemModel.getAllGoods(map);
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
                    if(page == 1){
                        mView.refresh(allGoodsResult);
                    }else{
                        mView.loadMore(allGoodsResult);
                    }
                }else{
                    mView.loadFail(allGoodsResult.getMessage());
                }
            }
        };
        allAuctionItemModel.subGetAllGoods(observer);
    }

    public void getAddCommentResult(Map<String,String> map){
        allAuctionItemModel.getAddCommentResult(map);
        Observer<AddResult> observer = new Observer<AddResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddResult result) {
               if(result!=null && mView!=null){
                   mView.addComment(result);
               }
            }
        };
        allAuctionItemModel.subAddResult(observer);
    }

    public void getLike(Map<String,String> params){
        allAuctionItemModel.getLikeResult(params);
        Observer<AddResult> observer = new Observer<AddResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddResult result) {
                if(result!=null && mView!=null){
                    mView.addLike(result);
                }
            }
        };
        allAuctionItemModel.subLike(observer);
    }


    public void getDirectOrder(Map<String,String> params){
        allAuctionItemModel.loadDirectOrder(params);
        Observer<OrderPayInfo> observer = new Observer<OrderPayInfo>(){
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OrderPayInfo orderPayInfo) {
//                if(orderPayInfo!= null && orderPayInfo.getCode() == 0){
//                    mView.loadNewOrder(orderPayInfo.getData());
//                }else{
//                    mView.loadFail(orderPayInfo.getMessage());
//                }
                mView.loadNewOrder(orderPayInfo);
            }
        };
        allAuctionItemModel.subDirectOrder(observer);
    }
}
