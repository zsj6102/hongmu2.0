package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.model.CycloAwardModel;
import com.colpencil.redwood.model.imples.ICycloAwardModel;
import com.colpencil.redwood.view.impl.ICycloAwardView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:百科奖励的presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CycloAwardPresenter extends ColpencilPresenter<ICycloAwardView> {

    private ICycloAwardModel model;

    public CycloAwardPresenter() {
        model = new CycloAwardModel();
    }

    public void loadUrl() {
        model.loadUrl();
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> awardResult) {
                mView.loadSuccess(awardResult);
            }
        };
        model.sub(observer);
    }
}
