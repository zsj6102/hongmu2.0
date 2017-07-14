package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.result.StatisticResult;
import com.colpencil.redwood.model.CircleRightModel;
import com.colpencil.redwood.model.imples.ICircleRightModel;
import com.colpencil.redwood.view.impl.ICircleRightView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:我的帖子的Presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CircleRightPresenter extends ColpencilPresenter<ICircleRightView> {

    private ICircleRightModel model;

    public CircleRightPresenter() {
        model = new CircleRightModel();
    }

    /**
     * 圈子统计
     *
     * @param token
     * @param member_id
     */
    public void loadStatic(String token, String member_id) {
        model.loadStatic(member_id, token);
        Observer<StatisticResult> observer = new Observer<StatisticResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(StatisticResult result) {
                if (result.getCode().equals("1")) {
                    mView.loadStatistics(result);
                } else {
                    mView.loadError();
                }
            }
        };
        model.sub(observer);
    }

}
