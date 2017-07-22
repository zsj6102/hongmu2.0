package com.colpencil.redwood.present.mine;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.model.ApplyModel;
import com.colpencil.redwood.model.imples.IApplyModel;
import com.colpencil.redwood.view.impl.ApplayView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

public class ApplyPresenter extends ColpencilPresenter<ApplayView> {
    private IApplyModel applyModel;
    public ApplyPresenter(){
        applyModel = new ApplyModel();
    }

    public void loadRegion(int id){
        applyModel.loadRegion(id);
        Subscriber<AddresBean> subscriber = new Subscriber<AddresBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mView!=null){
                    mView.applyError(e.getMessage());
                }

            }

            @Override
            public void onNext(AddresBean regionInfoResultInfo) {
                if(mView!=null){
                    mView.load(regionInfoResultInfo);
                }
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
                if(mView!=null){
                    mView.applyError(e.getMessage());
                }

            }

            @Override
            public void onNext(CatListBean catListBean) {
                if(mView!=null){
                    mView.loadCat(catListBean);
                }
            }
        };
        applyModel.sub(subscriber);
    }


}
