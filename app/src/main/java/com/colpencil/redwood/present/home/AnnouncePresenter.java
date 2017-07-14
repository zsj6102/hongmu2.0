package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.model.AnnounceModel;
import com.colpencil.redwood.model.imples.IAnnounceModel;
import com.colpencil.redwood.view.impl.IAnnounceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:公告的presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class AnnouncePresenter extends ColpencilPresenter<IAnnounceView> {

    private IAnnounceModel model;

    public AnnouncePresenter() {
        model = new AnnounceModel();
    }

    /**
     * 获取公告url
     */
    public void loadUrl() {
        model.loadUrl();
        Observer<AnnounceResult> observer = new Observer<AnnounceResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AnnounceResult result) {
                mView.loadSuccess(result);
            }
        };
        model.sub(observer);
    }

    public void loadGoodMiddle(int goodsId) {
        model.loadGoodMiddle(goodsId);
        Observer<AnnounceResult> observer = new Observer<AnnounceResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AnnounceResult result) {
                mView.loadSuccess(result);
            }
        };
        model.subGood(observer);
    }
}
