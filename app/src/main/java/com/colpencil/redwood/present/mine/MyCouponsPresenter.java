package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.model.MyCouponsModel;
import com.colpencil.redwood.model.imples.IMyCouponsModel;
import com.colpencil.redwood.view.impl.IMyCouponsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 曾 凤
 * @Description:我的积分
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class MyCouponsPresenter extends ColpencilPresenter<IMyCouponsView> {

    private IMyCouponsModel myCouponsModel;

    public MyCouponsPresenter() {
        myCouponsModel = new MyCouponsModel();
    }

    public void getContent() {
        myCouponsModel.getCount();
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result result) {
                mView.loadData(result);
            }
        };
        myCouponsModel.sub(observer);
    }


}

