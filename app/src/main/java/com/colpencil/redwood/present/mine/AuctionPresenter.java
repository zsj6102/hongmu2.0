package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.JiashangItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.model.AuctionModel;
import com.colpencil.redwood.model.imples.IAuctionModel;
import com.colpencil.redwood.view.impl.IAuctionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;
import java.util.Map;

import rx.Observer;

public class AuctionPresenter extends ColpencilPresenter<IAuctionView> {
    private IAuctionModel model;
    public AuctionPresenter(){
        model = new AuctionModel();
    };
    public void getAllJiashang(final int pageNo,Map<String,String> map){
        model.loadData(map);
        Observer<ResultInfo<List<JiashangItem>>> observer = new Observer<ResultInfo<List<JiashangItem>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<JiashangItem>> listResultInfo) {
              if(listResultInfo!=null && mView != null){
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
        model.subData(observer);
    }
}