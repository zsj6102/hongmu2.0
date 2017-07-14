package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.result.SpecialIntroduceResult;
import com.colpencil.redwood.model.SpecialIntroduceModel;
import com.colpencil.redwood.model.imples.ISpecialIntroduceModel;
import com.colpencil.redwood.view.fragments.mine.SpecialIntroduceFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

public class SpecialIntroducePresent extends ColpencilPresenter<SpecialIntroduceFragment> {

    private ISpecialIntroduceModel specialIntroduceModel;

    public SpecialIntroducePresent(){
        specialIntroduceModel=new SpecialIntroduceModel();
    }

    public void getSpecialIntroduce(int id){
        specialIntroduceModel.getSpecialIntroduce(id);
        Observer<SpecialIntroduceResult> observer=new Observer<SpecialIntroduceResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SpecialIntroduceResult specialIntroduceResult) {

                if(specialIntroduceResult.getCode()==0){
                    mView.loadSuccess(specialIntroduceResult);
                }else{
                    mView.loadFail(specialIntroduceResult.getMessage());
                }
            }
        };
        specialIntroduceModel.subGetSpecialIntroduce(observer);
    }

}
