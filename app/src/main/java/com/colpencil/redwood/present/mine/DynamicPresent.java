package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.result.DynamicResult;
import com.colpencil.redwood.model.DynamicModel;
import com.colpencil.redwood.model.imples.IDynamicModel;
import com.colpencil.redwood.view.impl.IDynamicView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;

public class DynamicPresent extends ColpencilPresenter<IDynamicView> {
    private IDynamicModel dynamicModel;
    public DynamicPresent (){
        dynamicModel=new DynamicModel();
    }

    public void getDynamic(final int pageNo, HashMap<String,Integer> intparams, HashMap<String, RequestBody> strparmas){
        dynamicModel.getDynamic(intparams,strparmas);
        Observer<DynamicResult> observer=new Observer<DynamicResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DynamicResult dynamicResult) {

                if(dynamicResult.getCode()==0){
                    if(pageNo==1){
                        mView.refresh(dynamicResult);
                    }else{
                        mView.loadMore(dynamicResult);
                    }
                }else{
                    mView.loadFail(dynamicResult.getMessage());
                }
            }
        };
        dynamicModel.subGetDynamic(observer);

    }
}
