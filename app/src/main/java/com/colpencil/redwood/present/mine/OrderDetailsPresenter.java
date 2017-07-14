package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.OrderDetailsReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.OrderDetailsModel;
import com.colpencil.redwood.model.imples.IOrderDetailsModel;
import com.colpencil.redwood.view.impl.IOrderDetailsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * @author 曾 凤
 * @Description: 订单详情
 * @Email 20000263@qq.com
 * @date 2016/8/22
 */
public class OrderDetailsPresenter extends ColpencilPresenter<IOrderDetailsView> {

    private IOrderDetailsModel orderDetailsModel;

    public OrderDetailsPresenter() {
        orderDetailsModel = new OrderDetailsModel();
    }

    public void getContent(int order_id) {
        orderDetailsModel.loadData(order_id);
        Observer<OrderDetailsReturn> observer = new Observer<OrderDetailsReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "订单详情异常：" + e.toString());
            }

            @Override
            public void onNext(OrderDetailsReturn orderDetailsReturn) {
                Log.e("返回值", "订单详情：" + orderDetailsReturn.toString());
                mView.result(orderDetailsReturn);
            }
        };
        orderDetailsModel.sub(observer);
    }

    /**
     * 进行取消订单操作
     */
    public void cancelOrderById(String order_id) {
        orderDetailsModel.cancelOrderById(order_id);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultCodeInt result) {
                if (result.getCode() == 1) {//订单取消成功
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(35);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                    mView.success(result.getMsg(), 1);
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        orderDetailsModel.subResult(observer);
    }

    /**
     * 立即支付，获取相关参数
     *
     * @param order_id
     */
    public void payKeyInfor(int order_id) {
        orderDetailsModel.payInfor(order_id);
        Observer<PayKeyRetrun> observer = new Observer<PayKeyRetrun>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(PayKeyRetrun payKeyRetrun) {
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
                    } else if (payKeyRetrun.getType() == 1) {//支付宝
                        mView.payByAlipay(payKeyRetrun.getResult().getReStr());
                    } else if (payKeyRetrun.getType() == 2) {
                        mView.payByUnion(payKeyRetrun.getTn(), "00");
                    }
                } else {
                    mView.fail(payKeyRetrun.getCode(), payKeyRetrun.getMsg());
                }
            }
        };
        orderDetailsModel.subPay(observer);
    }

    /**
     * 取消退款操作
     */
    public void cancelApplyForRefund(int order_id, int return_order_id) {
        orderDetailsModel.cancelApplyForRefund(order_id, return_order_id);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultCodeInt result) {
                if (result.getCode() == 1) {//取消退款申请成功
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(35);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                    mView.success(result.getMsg(), 2);
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        orderDetailsModel.subResult(observer);
    }

    /**
     * 提醒用户发货
     *
     * @param sn
     */
    public void remindDelivery(String sn) {
        orderDetailsModel.remindDelivery(sn);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultCodeInt result) {
                if (result.getCode() == 1) {//提醒卖家发货成功
                    mView.success(result.getMsg(), 3);
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        orderDetailsModel.subResult(observer);
    }

    /**
     * 进行确认收货操作
     */
    public void confirmRecept(int order_id) {
        orderDetailsModel.confirmRecept(order_id);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultCodeInt result) {
                if (result.getCode() == 1) {//确认收货成功
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(35);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                    mView.success(result.getMsg(), 4);
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        orderDetailsModel.subResult(observer);
    }
}

