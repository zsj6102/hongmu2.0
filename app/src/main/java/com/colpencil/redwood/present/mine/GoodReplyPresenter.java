package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.GoodReply;
import com.colpencil.redwood.model.GoodRDetailModel;
import com.colpencil.redwood.model.imples.IGoodRDetailModel;
import com.colpencil.redwood.view.impl.IGoodReplyDetailView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.Map;

import rx.Observer;

import static com.colpencil.redwood.holder.HolderFactory.map;

public class GoodReplyPresenter extends ColpencilPresenter<IGoodReplyDetailView>{

    private IGoodRDetailModel model;

    public GoodReplyPresenter(){
        model = new GoodRDetailModel();
    }

    public void getDetail(final int pageNo, Map<String,String> map){
        model.loadDetail(map);
        Observer<ResultInfo<GoodReply>> observer = new Observer<ResultInfo<GoodReply>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<GoodReply> resultInfo) {
              if(mView!=null){
                  if(resultInfo.getCode() == 0){
                      if(pageNo == 1){
                          mView.refresh(resultInfo);
                      }else{
                          mView.loadMore(resultInfo);
                      }
                  }else{
                      mView.loadFail(resultInfo.getMessage());
                  }
              }
            }
        };
        model.subDetail(observer);
    }
    public void getDiscussResult(Map<String,String> map){
        model.addDiscuss(map);
        Observer<ResultInfo<String>> observer = new Observer<ResultInfo<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<String> resultInfo) {
              if(mView!=null){
                  mView.addResult(resultInfo);
              }
            }
        };
        model.subAdd(observer);
    }

}
