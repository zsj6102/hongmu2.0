package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.ReplyToItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.model.RelpyToDetailModel;
import com.colpencil.redwood.model.imples.IReplyDetailModel;
import com.colpencil.redwood.view.impl.IReplyDetailView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;
import java.util.Map;

import rx.Observer;

public class ReplyDetailPresenter extends ColpencilPresenter<IReplyDetailView> {

    private IReplyDetailModel model;
    public ReplyDetailPresenter(){
        model = new RelpyToDetailModel();
    }
    public void getDetail(final int pageNo, Map<String,String> map){
        model.getDetail(map);

        Observer<ResultInfo<List<ReplyToItem>>> observer = new Observer<ResultInfo<List<ReplyToItem>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadFail(e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<List<ReplyToItem>> replyToDetailResultInfo) {
                if(replyToDetailResultInfo!=null && mView!=null){
                    if(replyToDetailResultInfo.getCode() == 0){
                        if(pageNo == 1){
                            mView.refresh(replyToDetailResultInfo);
                        }else{
                            mView.loadMore(replyToDetailResultInfo);
                        }
                    }else{
                        mView.loadFail(replyToDetailResultInfo.getMessage());
                    }
                }
            }
        };
        model.subDetail(observer);
    }

    public void getAddReply(Map<String,String> map){
        model.getAddReply(map);
        Observer<AddResult> observer = new Observer<AddResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddResult result) {
              if(result!=null && mView!=null){
                  mView.addReply(result);
              }
            }
        };
        model.subAddResult(observer);

    }
}
