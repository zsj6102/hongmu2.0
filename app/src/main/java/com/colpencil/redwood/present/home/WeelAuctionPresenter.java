package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.model.WeekAuctionModel;
import com.colpencil.redwood.model.imples.IWeekAuctionModel;
import com.colpencil.redwood.view.impl.IWeekShootActivityView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:周拍分类的Presenter
 * @Email DramaScript@outlook.com
 * @date 2016/8/2
 */
public class WeelAuctionPresenter extends ColpencilPresenter<IWeekShootActivityView> {

    private IWeekAuctionModel model;

    public WeelAuctionPresenter() {
        model = new WeekAuctionModel();
    }

    /**
     * 周拍分类
     */
    public void loadTag() {
        model.loadTag();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<CategoryVo> result) {
                if (result.getCode() == 1) {
                    mView.loadTagSuccess(result.getResult());
                } else {
                    mView.loadTagError();
                }
            }
        };
        model.sub(observer);
    }

    /**
     * 周拍
     */
    public void loadWeekShoot() {
        model.loadWeekShoot();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CategoryVo> result) {
                if (result.getCode() == 1) {
                    mView.loadTagSuccess(result.getResult());
                }
            }
        };
        model.subWeekShoot(observer);
    }

}
