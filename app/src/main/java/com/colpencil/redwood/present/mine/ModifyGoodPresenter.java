package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.Info.ApplyGoodInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.model.ModifyGoodModel;
import com.colpencil.redwood.model.imples.IModifyGoodModel;
import com.colpencil.redwood.view.impl.IModifyGoodsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscriber;

public class ModifyGoodPresenter extends ColpencilPresenter<IModifyGoodsView> {

    private IModifyGoodModel model;
    public ModifyGoodPresenter(){
        model = new ModifyGoodModel();
    }
    public void getGoodInfo(int id){
        model.loadGood(id);
        Observer<ResultInfo<ApplyGoodInfo>> observer = new Observer<ResultInfo<ApplyGoodInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadError("服务器异常");
            }

            @Override
            public void onNext(ResultInfo<ApplyGoodInfo> applyGoodInfoResultInfo) {
                if(applyGoodInfoResultInfo.getCode() == 0 && mView != null){
                    mView.loadGood(applyGoodInfoResultInfo);
                }else{
                    mView.loadError(applyGoodInfoResultInfo.getMessage());
                }
            }
        };
        model.subGood(observer);
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
                    mView.loadError(e.getMessage());
                }

            }

            @Override
            public void onNext(CatListBean catListBean) {
                if(mView!=null && catListBean.getCode() == 0){
                    mView.loadCat(catListBean);
                }else{
                    mView.loadError(catListBean.getMessage());
                }
            }
        };
        model.sub(subscriber);
    }

    public void loadPro(Map<String,String> map){
        model.loadPro(map);
        Subscriber<ResultInfo<String>> subscriber = new Subscriber<ResultInfo<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<String> stringResultInfo) {
                if(mView!=null){
                    mView.loadPro(stringResultInfo);
                }
            }
        };
        model.subPro(subscriber);
    }
    public void loadZcList(int id){
        model.loadZcList(id);
        Subscriber<ResultInfo<List<CoverSpecialItem>>> subscriber = new Subscriber<ResultInfo<List<CoverSpecialItem>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<CoverSpecialItem>> resultInfo) {
                mView.loadZcList(resultInfo);
            }
        };
        model.subZcList(subscriber);
    }

}
