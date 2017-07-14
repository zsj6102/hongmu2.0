package com.colpencil.redwood.present;

import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.model.SpeedModel;
import com.colpencil.redwood.model.imples.ISpeedModel;
import com.colpencil.redwood.view.impl.SpeedView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

public class SpeedPresent extends ColpencilPresenter<SpeedView> {
    private ISpeedModel speedModel;
    public SpeedPresent(){
        speedModel=new SpeedModel();
    }
    public void getAd(String type){
        speedModel.getAd(type);
        Observer<AdResult> observer=new Observer<AdResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AdResult adResult) {

                if(adResult.getCode()==0){
                    mView.getAd(adResult);
                }else{
                    mView.loadFail(adResult.getMessage());
                }
            }
        };
        speedModel.subGetAd(observer);
    }
}
