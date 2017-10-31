package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.Goods_list;
import com.colpencil.redwood.bean.Goods_list_item;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.model.HomeGoodsPageModel;
import com.colpencil.redwood.model.imples.IHomeGoodsPageModel;
import com.colpencil.redwood.view.impl.IHomeGoodsPageView;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;
import java.util.Map;

import rx.Observer;

public class HomeGoodsPagePresenter extends ColpencilPresenter<IHomeGoodsPageView> {
    private IHomeGoodsPageModel model;
    public HomeGoodsPagePresenter(){
        model = new HomeGoodsPageModel();
    }
    public void getAd(String type){
        model.getAdv(type);
        Observer<AdResult> observer = new Observer<AdResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                 mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(AdResult adResult) {
               if(adResult.getCode() == 0 && mView!=null){
                   mView.loadAdv(adResult);
               }else {
                   mView.loadError(adResult.getMessage());
               }
            }
        };
        model.subAdv(observer);
    }
    public void  getCoverSpecial(Map<String, String> map){
        model.getCoverSpecial(map);
        Observer<ResultInfo<List<AllSpecialInfo>>> observer = new Observer<ResultInfo<List<AllSpecialInfo>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<AllSpecialInfo>> listResultInfo) {
                if(listResultInfo!=null && mView!=null){
                    mView.loadZc(listResultInfo);
                }
            }
        };
        model.subCover(observer);
    }

    public void getHotFans(Map<String, String> map){
        model.getHotFans(map);
        Observer<ResultInfo<List<ItemStoreFans>>> observer = new Observer<ResultInfo<List<ItemStoreFans>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<ItemStoreFans>> listResultInfo) {
              if(listResultInfo.getCode() == 0 && mView !=null){
                  mView.loadFans(listResultInfo);
              }else{
                  mView.loadError(listResultInfo.getMessage());
              }
            }
        };
        model.subHotFans(observer);
    }

    public void getGoodsList(Map<String,String> map){
        model.getContent(map);
        Observer<ResultInfo<List<Goods_list>>> observer = new Observer<ResultInfo<List<Goods_list>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<Goods_list>> listResultInfo) {
                 mView.loadGoods(listResultInfo);
            }
        };
        model.subContent(observer);
    }


    public void getSecondGoods(final int pageNo,Map<String,String> map){
        model.getSecondGoods(map);
        Observer<ResultInfo<List<Goods_list_item>>>  observer = new Observer<ResultInfo<List<Goods_list_item>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<Goods_list_item>> listResultInfo) {
                if(listResultInfo.getCode() == 0){
                    if(pageNo == 1){
                        mView.refresh(listResultInfo);
                    }else{
                        mView.loadMore(listResultInfo);
                    }
                }else{
                    mView.loadError(listResultInfo.getMessage());
                }
            }
        };
        model.sbuSecond(observer);
    }
    public void getOperate(Map<String,String> params){
        model.storeCare(params);
        Observer<CareReturn> observer = new Observer<CareReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CareReturn careReturn) {
                if(careReturn!=null && mView!=null){
                    mView.operate(careReturn);
                }
            }
        };
        model.subUnstoreCare(observer);
    }
}
