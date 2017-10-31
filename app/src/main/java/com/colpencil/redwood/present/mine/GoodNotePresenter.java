package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.ImageSpan;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.model.GoodNoteModel;
import com.colpencil.redwood.model.imples.IGoodNoteModel;
import com.colpencil.redwood.view.impl.IGoodNoteView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;

public class GoodNotePresenter extends ColpencilPresenter<IGoodNoteView> {
    private IGoodNoteModel model;
    public GoodNotePresenter(){
        model = new GoodNoteModel();
    }
    public void getGoodUrl(HashMap<String, RequestBody> map){
        model.loadGoodUrl(map);
        Observer<ResultInfo<ImageSpan>> observer = new Observer<ResultInfo<ImageSpan>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadFail(e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<ImageSpan> resultInfo) {
                  if(mView!=null){
                      mView.loadUrl(resultInfo);
                  }
            }
        };
        model.subUrl(observer);
    }
}
