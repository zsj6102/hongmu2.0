package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.model.SupaiDynamicModel;
import com.colpencil.redwood.model.imples.ISupaiDynamicModel;
import com.colpencil.redwood.view.impl.ISupaiDynamic;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

public class SupaiPresenter extends ColpencilPresenter<ISupaiDynamic> {
    private ISupaiDynamicModel dynamicModel;
    public SupaiPresenter(){
        dynamicModel = new SupaiDynamicModel();
    }

    /**
     * 速拍我的收藏
     * @param pageNo
     * @param params
     */
    public void getSupaiCol(final int pageNo,HashMap<String,String> params){
        dynamicModel.getSupaiCol(params);
        Observer<AllGoodsResult> observer = new Observer<AllGoodsResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AllGoodsResult result) {
                if(result.getCode() == 0){
                    if(pageNo==1){
                        mView.refreshSp(result);
                    }else{
                        mView.loadMoreSp(result);
                    }
                }else{
                    mView.loadFail(result.getMessage());
                }

            }
        };
        dynamicModel.subSupaiCol(observer);
    }

    /**
     * 速拍拍品动态
     */
    public void getSupaiDynamic(final int pageNo,HashMap<String,String> params){
        dynamicModel.getSupaiDynamic(params);
        Observer<AllGoodsResult> observer = new Observer<AllGoodsResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AllGoodsResult result) {
                if(result.getCode() == 0){
                    if(pageNo==1){
                        mView.refreshSp(result);
                    }else{
                        mView.loadMoreSp(result);
                    }
                }else{
                    mView.loadFail(result.getMessage());
                }
            }
        };
        dynamicModel.subSupaiDynamic(observer);
    }
    public void getAddCommentResult(Map<String,String> map){
        dynamicModel.getAddCommentResult(map);
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
        dynamicModel.subAddResult(observer);
    }

    public void getLike(Map<String,String> params){
        dynamicModel.getLikeResult(params);
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
        dynamicModel.subLike(observer);
    }
}
