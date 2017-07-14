package com.colpencil.redwood.present;

import com.colpencil.redwood.bean.result.CustomGoodResult;
import com.colpencil.redwood.bean.result.CustomResult;
import com.colpencil.redwood.model.CustomModel;
import com.colpencil.redwood.model.imples.ICustomModel;
import com.colpencil.redwood.view.impl.ICustomView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/7 17 21
 */
public class CustomPresenter extends ColpencilPresenter<ICustomView> {
    private ICustomModel customModel;

    public CustomPresenter() {
        customModel = new CustomModel();
    }

    public void getContent(int official_id) {
        customModel.loadData(official_id);
        Observer<CustomResult> observer = new Observer<CustomResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CustomResult result) {
                mView.loadUrl(result);
            }
        };
        customModel.sub(observer);
    }

    public void customGood(int officialId) {
        customModel.loadCustomGoods(officialId);
        Observer<CustomGoodResult> observer = new Observer<CustomGoodResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CustomGoodResult result) {
                mView.loadGoods(result);
            }
        };
        customModel.subCustom(observer);
    }

}
