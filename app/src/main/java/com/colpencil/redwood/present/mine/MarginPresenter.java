package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.Margin;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.Settled;
import com.colpencil.redwood.model.MarginModel;
import com.colpencil.redwood.model.imples.IMarginModel;
import com.colpencil.redwood.view.impl.IMarginView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;

import rx.Observer;

public class MarginPresenter extends ColpencilPresenter<IMarginView> {
    private IMarginModel model;
    public MarginPresenter(){
        model = new MarginModel();
    }

    public void getMargin(){
        model.loadInfo();
        Observer<ResultInfo<Margin>> observer = new Observer<ResultInfo<Margin>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<Margin> marginResultInfo) {
                 if(mView!=null){
                     mView.loadInfo(marginResultInfo);
                 }
            }
        };
        model.sub(observer);
    }

    public void getSettled(){
        model.loadSettled();
        Observer<ResultInfo<List<Settled>>> observer = new Observer<ResultInfo<List<Settled>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<Settled>> listResultInfo) {
                if(mView!=null){
                    mView.loadSettle(listResultInfo);
                }
            }
        };
        model.subSettled(observer);
    }
}
