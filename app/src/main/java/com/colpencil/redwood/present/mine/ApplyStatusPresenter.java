package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.model.ApplyModel;
import com.colpencil.redwood.model.ApplyStatusModel;
import com.colpencil.redwood.model.imples.IApplyModel;
import com.colpencil.redwood.model.imples.IApplyStatusModel;
import com.colpencil.redwood.view.impl.IBuisnessView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Subscriber;


public class ApplyStatusPresenter extends ColpencilPresenter<IBuisnessView> {
    private IApplyStatusModel applyModel;
    public ApplyStatusPresenter(){
        applyModel = new ApplyStatusModel();
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
