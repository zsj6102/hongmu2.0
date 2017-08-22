package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.ArticalItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.model.EncycloModel;
import com.colpencil.redwood.model.IEncycloModel;
import com.colpencil.redwood.view.impl.IEncycloView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;
import java.util.Map;

import rx.Observer;

public class EncycloPresenter extends ColpencilPresenter<IEncycloView> {
    private IEncycloModel model;
    public EncycloPresenter(){
        model = new EncycloModel();
    }
    public void getArticalList(final int pageNo, Map<String,String> map){
        model.getArticalList(map);
        Observer<ResultInfo<List<ArticalItem>>> observer = new Observer<ResultInfo<List<ArticalItem>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
               mView.loadFail(e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<List<ArticalItem>> listResultInfo) {
                if(listResultInfo!=null && mView!=null){
                    if(listResultInfo.getCode() == 0){
                        if(pageNo == 1){
                            mView.refresh(listResultInfo);
                        }else{
                            mView.loadMore(listResultInfo);
                        }
                    }else{
                        mView.loadFail(listResultInfo.getMessage());
                    }
                }
            }
        };
        model.subNormal(observer);
    }
}
