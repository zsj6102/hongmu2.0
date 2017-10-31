package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.model.BrowsingHistoryModel;
import com.colpencil.redwood.model.imples.IBrowsingHistoryModel;
import com.colpencil.redwood.view.impl.IBrowsingHistoryView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import rx.Observer;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class BrowsingHistoryPresenter extends ColpencilPresenter<IBrowsingHistoryView> {

    private IBrowsingHistoryModel model;

    public BrowsingHistoryPresenter() {
        model = new BrowsingHistoryModel();
    }

    public void delet(int type, int foot_type) {
        model.delet(type, foot_type);
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Result result) {
                mView.resultInfor(result.getCode(), result.getMessage());
                if (result.getCode().equals("0")) {
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(40);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                }
            }
        };
        model.subResult(observer);
    }

    /**
     * 分享
     *
     * @param ote_id
     */
    public void share(int ote_id) {
        model.share(ote_id);
        Observer<CommonResult> observer = new Observer<CommonResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommonResult result) {
                mView.shareResult(result);
            }
        };
        model.subShare(observer);
    }

    /**
     * 记录分享
     *
     * @param ote_id
     */
    public void recordShare(int ote_id, String platform, int type) {
        model.recordShare(ote_id, platform, type);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
            }
        };
        model.subRecord(observer);
    }


    public void baikeShare(int ote_id,String articl_id){
        model.shareBaike(ote_id,articl_id);
        Observer<CommonResult> observer = new Observer<CommonResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommonResult result) {
                mView.shareBaike(result);
            }
        };
        model.subShareBike(observer);
    }

    public void goodsShare(String goodsid){
        model.shareGoods(goodsid);
        Observer<CommonResult> observer = new Observer<CommonResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommonResult result) {
                mView.shareGoods(result);
            }
        };
        model.subShareGoods(observer);
    }
}

