package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.Info.StoreDetail;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.model.AboutModel;
import com.colpencil.redwood.model.imples.IAboutModel;
import com.colpencil.redwood.view.impl.IAboutView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;
import java.util.Map;
import rx.Observer;



public class StoreDetailPresenter extends ColpencilPresenter<IAboutView>{
    private IAboutModel model;

    public StoreDetailPresenter() {
        model = new AboutModel();
    }

    public void getStoreDetail(Map<String, String> params) {
        model.loadinfo(params);
        Observer<ResultInfo<StoreDetail>> observer = new Observer<ResultInfo<StoreDetail>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //                mView.resultError(e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<StoreDetail> storeDetailResultInfo) {
                if (storeDetailResultInfo!=null) {
                    if(mView!=null){
                        mView.getStoreDetail(storeDetailResultInfo);
                    }

                }
            }
        };
        model.subInfo(observer);
    }

    public void getStoreLike(Map<String,String> params){
        model.lodaLike(params);
        Observer<ResultInfo<List<ItemStoreFans>>> observer = new Observer<ResultInfo<List<ItemStoreFans>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<ItemStoreFans>> listResultInfo) {
                 if(listResultInfo != null && mView!=null){
                     mView.getLike(listResultInfo);
                 }
            }
        };
        model.subLike(observer);
    }

    public void getStoreFans(Map<String,String> params){
        model.loadFans(params);
        Observer<ResultInfo<List<ItemStoreFans>>> observer = new Observer<ResultInfo<List<ItemStoreFans>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<ItemStoreFans>> listResultInfo) {
                if(listResultInfo != null && mView != null){
                    mView.getFans(listResultInfo);
                }
            }
        };
        model.subFans(observer);
    }

    public void getCareReturn(Map<String,String> params){
        model.care(params);
        Observer<CareReturn> observer = new Observer<CareReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CareReturn careReturn) {
               if(careReturn!=null && mView!=null){
                   mView.care(careReturn);
               }
            }
        };
        model.subCare(observer);
    }

    public void getUncareReturn(Map<String,String> params){
        model.unCare(params);
        Observer<CareReturn> observer = new Observer<CareReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CareReturn careReturn) {
                if(careReturn!=null && mView!=null){
                    mView.uncare(careReturn);
                }
            }
        };
        model.subUnCare(observer);
    }

    public void getOperate(Map<String,String> params){
        model.storeCare(params);
        Observer<CareReturn> observer = new Observer<CareReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CareReturn careReturn) {
                if(careReturn!=null && mView!=null){
                    mView.operate(careReturn);
                }
            }
        };
        model.subUnstoreCare(observer);
    }
}
