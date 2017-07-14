package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.OrderCenterReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.OrderCenterFragmentModel;
import com.colpencil.redwood.model.imples.IOrderCenterFragmentModel;
import com.colpencil.redwood.view.impl.IOrderCenterFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * 描述：订单中心
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class OrderCenterFragmentPresenter extends ColpencilPresenter<IOrderCenterFragmentView> {

    private IOrderCenterFragmentModel model;

    public OrderCenterFragmentPresenter() {
        model = new OrderCenterFragmentModel();
    }

    public void getContent(final int pageNo, int pageSize, int optType) {
        model.loadData(pageNo, pageSize, optType);
        Observer<OrderCenterReturn> observer = new Observer<OrderCenterReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OrderCenterReturn orderCenterReturn) {
                if (orderCenterReturn.getCode() == 1) {
                    if (pageNo == 1) {
                        mView.refresh(orderCenterReturn.getData());
                    } else {
                        mView.loadMore(orderCenterReturn.getData());
                    }
                } else {
                    mView.fail(orderCenterReturn.getCode(), orderCenterReturn.getMsg());
                }


            }
        };
        model.sub(observer);
    }

    /**
     * 进行取消订单操作
     */
    public void cancelOrderById(String order_id) {
        model.cancelOrderById(order_id);
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
                    mView.success(result.getMsg());
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        model.subResult(observer);
    }

    /**
     * 进行确认收货操作
     */
    public void confirmRecept(int order_id) {
        model.confirmRecept(order_id);
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
                    mView.success(result.getMsg());
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        model.subResult(observer);
    }

    /**
     * 取消退款操作
     */
    public void cancelApplyForRefund(int order_id, int return_order_id) {
        model.cancelApplyForRefund(order_id, return_order_id);
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
                    mView.success(result.getMsg());
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        model.subResult(observer);
    }

    /**
     * 立即支付，获取相关参数
     *
     * @param order_id
     */
    public void payKeyInfor(int order_id) {
        model.payInfor(order_id);
        Observer<PayKeyRetrun> observer = new Observer<PayKeyRetrun>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "支付参数结算界面异常:" + e.toString());
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
                    } else if (payKeyRetrun.getType() == 1) {//支付宝
                        mView.payByAlipay(payKeyRetrun.getResult().getReStr());
                    } else if (payKeyRetrun.getType() == 2) {   //银联支付
                        mView.payByUnion(payKeyRetrun.getTn(), "00");
                    }
                } else {
                    mView.fail(payKeyRetrun.getCode(), payKeyRetrun.getMsg());
                }
            }
        };
        model.subPay(observer);
    }

    /**
     * 提醒用户发货
     */
    public void remindDelivery(String sn) {
        model.remindDelivery(sn);
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
                    mView.success(result.getMsg());
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        model.subResult(observer);
    }


    public void deleteById(int order_id) {
        model.deleteById(order_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.deleteResult(result);
            }
        };
        model.subDelete(observer);
    }

}

