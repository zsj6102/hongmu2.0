package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.SizeColorInfo;
import com.colpencil.redwood.model.PublishModel;
import com.colpencil.redwood.model.imples.IPublishModel;
import com.colpencil.redwood.view.impl.PublishView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

public class PublishPresenter extends  ColpencilPresenter<PublishView>  {
    private IPublishModel model;
    public PublishPresenter(){
        model = new PublishModel();
    }
    public void loadSize(int id){
        model.loadSize(id);
        Subscriber<SizeColorInfo> subscriber = new Subscriber<SizeColorInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mView!=null){
                    mView.applyError(e.getMessage());
                }
            }

            @Override
            public void onNext(SizeColorInfo info) {
                if(mView!=null){
                    mView.loadSize(info);
                }
            }
        };
        model.subSize(subscriber);
    }
    public void loadCatList(int id){
        model.loadCatList(id);
        Subscriber<CatListBean> subscriber = new Subscriber<CatListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mView!=null){
                    mView.applyError(e.getMessage());
                }

            }

            @Override
            public void onNext(CatListBean catListBean) {
                if(mView!=null){
                    mView.loadCat(catListBean);
                }
            }
        };
        model.sub(subscriber);
    }
}
