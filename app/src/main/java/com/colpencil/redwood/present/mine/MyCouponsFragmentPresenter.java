package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.CouponsResult;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.model.MyCouponsFragmentModel;
import com.colpencil.redwood.model.imples.IMyCouponsFagmentModel;
import com.colpencil.redwood.view.impl.IMyCouponsFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class MyCouponsFragmentPresenter extends ColpencilPresenter<IMyCouponsFragmentView> {

    private IMyCouponsFagmentModel model;

    public MyCouponsFragmentPresenter() {
        model = new MyCouponsFragmentModel();
    }

    public void getContent(final int pageNo, int pageSize) {
        model.loadCoupon(pageNo, pageSize);
        Observer<CouponsResult> observer = new Observer<CouponsResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showError(null, null);
            }

            @Override
            public void onNext(CouponsResult result) {
                if (result.getCode() == 0) {
                    mView.loadResult(result);
                } else {
                    mView.fail(result.getCode() + "", result.getMessage());
                }
            }
        };
        model.sub(observer);
    }

    public void loadGetCoupon() {
        model.loadGetCoupon();
        Observer<CouponsResult> observer = new Observer<CouponsResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CouponsResult result) {
                if (result.getCode() == 0) {
                    mView.loadResult(result);
                } else {
                    mView.fail(result.getCode() + "", result.getMessage());
                }
            }
        };
        model.subGet(observer);
    }

    public void change(int point, String cpns_sn, int cpns_id) {
        model.change(point, cpns_sn, cpns_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.changeResult(result);
            }
        };
        model.subChange(observer);
    }
}

