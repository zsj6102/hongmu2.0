package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MyWeekShootReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.model.MyWeekShootFragmentModel;
import com.colpencil.redwood.model.imples.IMyWeekShootFragmentModel;
import com.colpencil.redwood.view.impl.IMyWeekShootFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * 描述：售后中心
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class MyWeekShootFragmentPresenter extends ColpencilPresenter<IMyWeekShootFragmentView> {

    private IMyWeekShootFragmentModel model;

    public MyWeekShootFragmentPresenter() {
        model = new MyWeekShootFragmentModel();
    }

    public void getContent(final int pageNo, int pageSize, String type) {
        model.loadData(pageNo, pageSize, Integer.valueOf(type));
        Observer<MyWeekShootReturn> observer = new Observer<MyWeekShootReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MyWeekShootReturn myWeekShootReturn) {
                if (myWeekShootReturn.getCode().equals("1")) {//服务请求成功
                    if (pageNo == 1) {
                        mView.refresh(myWeekShootReturn.getResult());
                    } else {
                        mView.loadMore(myWeekShootReturn.getResult());
                    }
                } else {
                    mView.fail(Integer.valueOf(myWeekShootReturn.getCode()), myWeekShootReturn.getMsg());
                }
            }
        };
        model.sub(observer);
    }


    /**
     * 删除周拍
     *
     * @param id
     */
    public void deleteById(int id) {
        model.deleteById(id);
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
}

