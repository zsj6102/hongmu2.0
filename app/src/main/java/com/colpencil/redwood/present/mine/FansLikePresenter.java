package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.model.FansAndLikeModel;
import com.colpencil.redwood.model.imples.IFansAndLikeModel;
import com.colpencil.redwood.view.impl.IFansAndLike;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;

public class FansLikePresenter extends ColpencilPresenter<IFansAndLike> {
    private IFansAndLikeModel model;
    public FansLikePresenter(){
        model = new FansAndLikeModel();
    }
    public void getData(final int pageNo, Map<String,String> map){
        model.loadLikeAndFans(map);
        Observer<ResultInfo<List<ItemStoreFans>>> observer = new Observer<ResultInfo<List<ItemStoreFans>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<ItemStoreFans>> listResultInfo) {
                if(listResultInfo != null && mView!=null){
                    if(pageNo == 1){
                        mView.refresh(listResultInfo);
                    }else{
                        mView.loadMore(listResultInfo);
                    }
                }else{
                    mView.loadFail(listResultInfo.getMessage());
                }
            }
        };
        model.subFansLike(observer);
    }
    public void getCareReturn(HashMap<String, String> params) {
        model.getCareStatus(params);
        Observer<CareReturn> observer = new Observer<CareReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CareReturn careReturn) {
                if (careReturn != null) {
                    mView.operate(careReturn);
                }
            }
        };
        model.subCare(observer);
    }

    public void getSearch(final int pageNo, Map<String,String> map){
        model.loadSearch(map);
        Observer<ResultInfo<List<ItemStoreFans>>> observer = new Observer<ResultInfo<List<ItemStoreFans>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<ItemStoreFans>> resultInfo) {
                if(resultInfo != null && mView!=null){
                    if(pageNo == 1){
                        mView.refresh(resultInfo);
                    }else{
                        mView.loadMore(resultInfo);
                    }
                }else{
                    mView.loadFail(resultInfo.getMessage());
                }
            }
        };
        model.subSearch(observer);
    }

    public void getHotFans(final int pageNo,Map<String, String> map){
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
                    if(pageNo == 1){
                        mView.refresh(listResultInfo);
                    }else{
                        mView.loadMore(listResultInfo);
                    }
                }else{
                    mView.loadFail(listResultInfo.getMessage());
                }
            }
        };
        model.subHotFans(observer);
    }
}
