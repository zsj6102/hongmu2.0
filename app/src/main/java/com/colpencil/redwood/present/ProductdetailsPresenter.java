package com.colpencil.redwood.present;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.WeekPersonList;
import com.colpencil.redwood.bean.result.BidderResult;
import com.colpencil.redwood.bean.result.WeekShootDetailResult;
import com.colpencil.redwood.model.ProductdetailsModle;
import com.colpencil.redwood.model.imples.IProductdetailsModle;
import com.colpencil.redwood.view.impl.IProductdetailsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 描述：商品详情 网络请求
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/11 11 49
 */
public class ProductdetailsPresenter extends ColpencilPresenter<IProductdetailsView> {

    private IProductdetailsModle model;

    public ProductdetailsPresenter() {
        model = new ProductdetailsModle();
    }

    /**
     * 获取商品头信息
     */
    public void loadGoodInfo(int id) {
        model.loadHearderData(id);
        Observer<WeekShootDetailResult> observer = new Observer<WeekShootDetailResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(WeekShootDetailResult result) {
                mView.loadHeaderData(result);
            }
        };
        model.sub(observer);
    }

    public void loadPersons(int id) {
        model.loadPersonList(id);
        Observer<ListResult<WeekPersonList>> observer = new Observer<ListResult<WeekPersonList>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<WeekPersonList> result) {
                if (result.getCode() == 0) {
                    mView.loadPersonList(result.getResult());
                }
            }
        };
        model.subPerson(observer);
    }

    public void loadBidder(int id) {
        model.loadBidders(id);
        Observer<BidderResult> observer = new Observer<BidderResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(BidderResult result) {
                mView.loadBidders(result.getResult());
            }
        };
        model.subBidders(observer);
    }

    /**
     * 提交购买申请
     */
    public void submitBid(String price, int id) {
        model.submitBid(price, id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.submitResult(result);
            }
        };
        model.subBid(observer);
    }

    public void loadUrl(int id) {
        model.loadUrl(id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.webUrl(result);
            }
        };
        model.subUrl(observer);
    }
}
