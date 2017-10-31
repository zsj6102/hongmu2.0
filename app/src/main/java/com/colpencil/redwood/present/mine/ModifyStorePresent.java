package com.colpencil.redwood.present.mine;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.Info.StoreInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.model.ModifyStoreModel;
import com.colpencil.redwood.model.imples.IModifyStoreModel;
import com.colpencil.redwood.view.impl.IModifyStoreView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscriber;

public class ModifyStorePresent extends ColpencilPresenter<IModifyStoreView> {
    private IModifyStoreModel model;
    public ModifyStorePresent(){
        model = new ModifyStoreModel();
    }
    public void getStoreInfo(){
        model.loadStoreInfo();
        Observer<ResultInfo<StoreInfo>> observer = new Observer<ResultInfo<StoreInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadFail("服务器异常");
            }

            @Override
            public void onNext(ResultInfo<StoreInfo> storeInfoResultInfo) {
              if(storeInfoResultInfo.getCode() == 0 && mView != null){
                  mView.loadStoreInfo(storeInfoResultInfo);
              }else{
                  mView.loadFail(storeInfoResultInfo.getMessage());
              }
            }
        };
        model.subStoreInfo(observer);
    }

    public void getModifyStatus(HashMap<String, RequestBody> params){
        model.loadModify(params);
        Observer<ResultInfo<StoreInfo>> observer = new Observer<ResultInfo<StoreInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadFail(e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<StoreInfo> storeInfoResultInfo) {
                if(storeInfoResultInfo.getCode() == 0 && mView != null){
                    mView.modifyResult(storeInfoResultInfo);
                }else{
                    mView.loadFail(storeInfoResultInfo.getMessage());
                }
            }
        };
        model.subModify(observer);
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
                    mView.loadFail("服务器异常");
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
    public void loadRegion(int id){
        model.loadRegion(id);
        Subscriber<AddresBean> subscriber = new Subscriber<AddresBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mView!=null){
                    mView.loadFail("服务器异常");
                }

            }

            @Override
            public void onNext(AddresBean regionInfoResultInfo) {
                if(mView!=null){
                    mView.loadRegion(regionInfoResultInfo);
                }
            }
        };
        model.subRegion(subscriber);
    }
}
