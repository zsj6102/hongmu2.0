package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.IntegralReturn;
import com.colpencil.redwood.model.MyIntegralModel;
import com.colpencil.redwood.model.imples.IMyIntegralModel;
import com.colpencil.redwood.view.impl.IMyIntegralView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 描述：我的积分
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class MyIntegralPresenter extends ColpencilPresenter<IMyIntegralView> {

    private IMyIntegralModel myIntegralModel;

    public MyIntegralPresenter() {
        myIntegralModel = new MyIntegralModel();
    }

    public void getContent(final int pageNo, int pageSize) {
        myIntegralModel.loadData(pageNo, pageSize);
        Observer<IntegralReturn> observer = new Observer<IntegralReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(IntegralReturn integralReturn) {

                if (integralReturn.getCode().equals("0")) {
                    mView.loadIntegralData(integralReturn.getPointList(), integralReturn.getMyPoint());
                } else {
                    mView.fail(integralReturn.getCode(), integralReturn.getMessage());
                }
            }
        };
        myIntegralModel.sub(observer);
    }

}

