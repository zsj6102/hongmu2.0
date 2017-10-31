package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.RatedItem;
import com.colpencil.redwood.bean.ResultInfo;

import com.colpencil.redwood.model.RatedModel;
import com.colpencil.redwood.model.imples.IRatedModel;
import com.colpencil.redwood.view.impl.IRatedView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;
import java.util.Map;

import rx.Observer;

public class RatedPresenter extends ColpencilPresenter<IRatedView> {
    private IRatedModel model;
    public RatedPresenter(){
        model = new RatedModel();
    }
    public void getRatedList(final int pageNo, Map<String,String> map){
        model.getRatedList(map);
        Observer<ResultInfo<List<RatedItem>>> observer = new Observer<ResultInfo<List<RatedItem>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadFail("服务器异常");
            }

            @Override
            public void onNext(ResultInfo<List<RatedItem>> listResultInfo) {
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
