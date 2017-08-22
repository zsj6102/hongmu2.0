package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.model.MinePostModel;
import com.colpencil.redwood.model.imples.IMinePostModel;
import com.colpencil.redwood.view.impl.IMinePostView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;
import java.util.Map;

import rx.Observer;


public class MinePostPresenter extends ColpencilPresenter<IMinePostView> {
    private IMinePostModel model;
    public MinePostPresenter(){
        model = new MinePostModel();
    }
    public void getMinePost(final int pageNo, Map<String,String> map){
        model.loadMinePost(map);
        Observer<ResultInfo<List<MinePostItem>>> observer = new Observer<ResultInfo<List<MinePostItem>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<MinePostItem>> listResultInfo) {
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
        model.subData(observer);
    }
}
