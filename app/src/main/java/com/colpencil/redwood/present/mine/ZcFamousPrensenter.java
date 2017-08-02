package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.bean.result.ZcAllCardInfo;
import com.colpencil.redwood.model.ZcFamousModel;
import com.colpencil.redwood.model.imples.IZcFoumousModel;
import com.colpencil.redwood.view.impl.IZcFoumousView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;


public class ZcFamousPrensenter extends ColpencilPresenter<IZcFoumousView>{
    private IZcFoumousModel model;
    public ZcFamousPrensenter(){
        model = new ZcFamousModel();
    }
    public void getAllFamous(final int pageNo,Map<String,String> params){
        model.getAllFamous(params);
        Observer<ZcAllCardInfo> observer = new Observer<ZcAllCardInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
               mView.loadFail(e.getMessage());
            }

            @Override
            public void onNext(ZcAllCardInfo zcAllCardInfo) {
                if(zcAllCardInfo!=null){
                    if(zcAllCardInfo.getCode() == 0){
                        if(pageNo == 1){
                            mView.refresh(zcAllCardInfo);
                        }else{
                            mView.loadMore(zcAllCardInfo);
                        }
                    }else{
                        mView.loadFail(zcAllCardInfo.getMessage());
                    }
                }

            }
        };
        model.subfamous(observer);
    }
    public void getCareReturn(HashMap<String, String> params) {
        model.getCareStatus(params);
        Observer<CareReturn> observer = new Observer<CareReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CareReturn careReturn) {
                if (careReturn != null) {
                    mView.operate(careReturn);
                }
            }
        };
        model.subCare(observer);
    }

}
