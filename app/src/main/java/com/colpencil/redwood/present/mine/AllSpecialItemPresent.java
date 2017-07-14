package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.result.AllSpecialResult;
import com.colpencil.redwood.model.AllSpecialItemModel;
import com.colpencil.redwood.model.imples.IAllSpecialItemModel;
import com.colpencil.redwood.view.impl.AllSpecialItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;

public class AllSpecialItemPresent extends ColpencilPresenter<AllSpecialItemView> {

    private IAllSpecialItemModel allSpecialItemModel;

    public AllSpecialItemPresent(){
        allSpecialItemModel=new AllSpecialItemModel();
    }

    public void getSpecial(final int pageNo,HashMap<String, RequestBody> strparams,HashMap<String,Integer> intparams){
        allSpecialItemModel.getSpecial(intparams,strparams);
        Observer<AllSpecialResult> observable=new Observer<AllSpecialResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                Log.d("pretty",e.getMessage());
            }

            @Override
            public void onNext(AllSpecialResult allSpecialResult) {

                if(allSpecialResult.getCode()==0){
                    if(pageNo==1){
                        mView.refresh(allSpecialResult);
                    }else{
                        mView.loadMore(allSpecialResult);
                    }
                }else{
                    mView.loadFail(allSpecialResult.getMessage());
                }
            }
        };

        allSpecialItemModel.subGetSpecial(observable);
    }
}
