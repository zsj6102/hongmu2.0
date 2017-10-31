package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.utils.StringFormatUtil;
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
                if(mView!=null){
                    mView.getStatusError(e.getMessage());
                }

            }

            @Override
            public void onNext(ApplyStatusReturn applyStatusReturn) {
                if(mView!=null){
                    mView.getStatusSucess(applyStatusReturn);
                }

            }
        };
        applyModel.subStauts(subscriber);
    }

    public void getHotLine(){
        applyModel.getHotLine();
        Subscriber<ResultInfo<String>> subscriber = new Subscriber<ResultInfo<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<String> stringResultInfo) {
                mView.getHotLine(stringResultInfo);
            }
        };
        applyModel.subHotLine(subscriber);
    }
}
