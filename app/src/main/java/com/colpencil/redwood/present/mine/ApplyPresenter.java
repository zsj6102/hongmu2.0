package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.SellApply;
import com.colpencil.redwood.bean.result.ApplyReturn;
import com.colpencil.redwood.bean.result.ResultInfo;
import com.colpencil.redwood.bean.result.ResultRegion;
import com.colpencil.redwood.model.ApplyModel;
import com.colpencil.redwood.model.imples.IApplyModel;
import com.colpencil.redwood.view.impl.ApplayView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Subscriber;

public class ApplyPresenter extends ColpencilPresenter<ApplayView> {
    private IApplyModel applyModel;
    public ApplyPresenter(){
        applyModel = new ApplyModel();
    }
    public void applySell(HashMap<String, RequestBody> params){
        applyModel.applySell(params);
        Subscriber<ApplyReturn> subscriber = new Subscriber<ApplyReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                     mView.applyError(e.getMessage());
            }

            @Override
            public void onNext(ApplyReturn sellApplyResultInfo) {
                     mView.applySell(sellApplyResultInfo);
            }
        };
        applyModel.subAppley(subscriber);
    }

    public void loadRegion(int id){
        applyModel.loadRegion(id);
        Subscriber<AddresBean> subscriber = new Subscriber<AddresBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
               mView.applyError(e.getMessage());
            }

            @Override
            public void onNext(AddresBean regionInfoResultInfo) {
                mView.load(regionInfoResultInfo);
            }
        };
        applyModel.subRegion(subscriber);
     }

    public void loadCatList(int id){
        applyModel.loadCatList(id);
        Subscriber<CatListBean> subscriber = new Subscriber<CatListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.applyError(e.getMessage());
            }

            @Override
            public void onNext(CatListBean catListBean) {
                mView.loadCat(catListBean);
            }
        };
        applyModel.sub(subscriber);
    }

    public void getApplyStatus(HashMap<String,String> params){
        applyModel.applyStatus(params);
        Subscriber<ApplyStatusReturn> subscriber = new Subscriber<ApplyStatusReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.getStatusError(e.getMessage());
            }

            @Override
            public void onNext(ApplyStatusReturn applyStatusReturn) {
                mView.getStatusSucess(applyStatusReturn);
            }
        };
        applyModel.subStauts(subscriber);
    }

}
