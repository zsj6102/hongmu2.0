package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.RefundMoneyModel;
import com.colpencil.redwood.model.imples.IRefundMoneyModel;
import com.colpencil.redwood.view.impl.IRefundMoneyView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.HashMap;

import rx.Observer;

/**
 * 描述：订单中心
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class RefundMoneyPresenter extends ColpencilPresenter<IRefundMoneyView> {

    private IRefundMoneyModel refundMoneyModel;

    public RefundMoneyPresenter() {
        refundMoneyModel = new RefundMoneyModel();
    }
    /**
     * 获取退款原因
     */
    public void loadReason() {
        refundMoneyModel.loadReason();
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultCodeInt result) {
                if (result.getCode() == 1) {//原因获取成功
                    mView.loadReason(result.getRefundResons());
                }else{
                    mView.fail(result.getCode(),result.getMsg());
                }
            }
        };
        refundMoneyModel.subResult(observer);
    }
    public void sumbtRefundMoney(HashMap<String,String> params){
        refundMoneyModel.sumbit(params);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultCodeInt result) {
                if (result.getCode() == 1) {//申请退款成功
                    RxBusMsg rxBusMsg=new RxBusMsg();
                    rxBusMsg.setType(35);
                    RxBus.get().post("rxBusMsg",rxBusMsg);
                    mView.submitSuccess(result.getMsg());
                }else{
                    mView.fail(result.getCode(),result.getMsg());
                }
            }
        };
        refundMoneyModel.subResult(observer);
    }

}

