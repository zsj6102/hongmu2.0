package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.PayForReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.bean.result.MemberCouponResult;
import com.colpencil.redwood.model.PaymentModel;
import com.colpencil.redwood.model.imples.IPaymentModel;
import com.colpencil.redwood.view.impl.IPaymentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * 描述：我的消息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class PaymentPresenter extends ColpencilPresenter<IPaymentView> {

    private IPaymentModel paymentModel;

    public PaymentPresenter() {
        paymentModel = new PaymentModel();
    }

    public void loadPayFor(String cartIds) {
        paymentModel.loadPayFor(cartIds);
        Observer<PayForReturn> observer = new Observer<PayForReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "结算界面异常:" + e.getMessage());
            }

            @Override
            public void onNext(PayForReturn payForReturn) {
                mView.loadOrdersInfo(payForReturn);
            }
        };
        paymentModel.sub(observer);
    }

    public void loadOtherPayFor(HashMap<String, String> map) {
        paymentModel.loadOtherPayFor(map);
        Observer<PayForReturn> observer = new Observer<PayForReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "结算界面异常:" + e.toString());
            }

            @Override
            public void onNext(PayForReturn payForReturn) {
                mView.loadOrdersInfo(payForReturn);
            }
        };
        paymentModel.sub(observer);
    }

    public void sumbitPayFor(HashMap<String, String> map) {
        paymentModel.payInfor(map);
        Observer<PayKeyRetrun> observer = new Observer<PayKeyRetrun>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "支付参数结算界面异常:" + e.toString());
                mView.fail(2, "支付异常");
            }

            @Override
            public void onNext(PayKeyRetrun payKeyRetrun) {
                Log.e("返回值", "支付参数结算界面:" + payKeyRetrun.toString());
                if (payKeyRetrun.getCode() == 1) {//获取成功
                    if (payKeyRetrun.getType() == 0) {//微信
                        Map<String, String> map = new HashMap<>();
                        map.put("appid", payKeyRetrun.getResult().getAppid());
                        map.put("partnerId", payKeyRetrun.getResult().getPartnerid());
                        map.put("prepayid", payKeyRetrun.getResult().getPrepayid());
                        map.put("noncestr", payKeyRetrun.getResult().getNoncestr());
                        map.put("timestamp", payKeyRetrun.getResult().getTimestamp() + "");
                        map.put("sign", payKeyRetrun.getResult().getSign());
                        mView.payByWeChat(map);
                    } else if (payKeyRetrun.getType() == 1) {   //支付宝
                        mView.payByAlipay(payKeyRetrun.getResult().getReStr());
                    } else if (payKeyRetrun.getType() == 2) {   //银联
                        mView.payByUnion(payKeyRetrun.getTn(), "00");     //00为正式环境，01位测试环境
                    }
                } else {
                    mView.fail(payKeyRetrun.getCode(), payKeyRetrun.getMsg());
                }
            }
        };
        paymentModel.subPay(observer);
    }

    /**
     * 获取优惠券
     *
     * @param cartIds
     */
    public void loadCoupon(String cartIds) {
        paymentModel.loadCoupon(cartIds);
        Observer<MemberCouponResult> observer = new Observer<MemberCouponResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("eeeeeeeee", e.getMessage());
            }

            @Override
            public void onNext(MemberCouponResult result) {
                mView.loadCouponResult(result);
            }
        };
        paymentModel.subCoupon(observer);
    }
}

