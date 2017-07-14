package com.colpencil.redwood.present;

import com.colpencil.redwood.bean.result.WeekAuctionListResult;
import com.colpencil.redwood.model.WeekShootModel;
import com.colpencil.redwood.model.imples.IWeekShootModel;
import com.colpencil.redwood.view.impl.IWeekShootView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 10 47
 */
public class WeekShootPresenter extends ColpencilPresenter<IWeekShootView> {

    private IWeekShootModel weekShootModel;

    public WeekShootPresenter() {
        weekShootModel = new WeekShootModel();
    }

    public void getContent(String type_id, final int pageNo, int pageSize) {
        weekShootModel.loadData(type_id, pageNo, pageSize);
        Observer<WeekAuctionListResult> observer = new Observer<WeekAuctionListResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeekAuctionListResult result) {
                if (result.getCode().equals("1")) {
                    if (pageNo == 1) {
                        mView.refresh(result.getResult());
                    } else {
                        mView.loadMore(result.getResult());
                    }
                } else {
                    mView.loadError();
                }
            }
        };
        weekShootModel.sub(observer);
    }

}
