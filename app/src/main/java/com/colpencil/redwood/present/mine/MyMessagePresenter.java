package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.MessageReturn;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.MyMessageModel;
import com.colpencil.redwood.model.imples.IMyMessageModel;
import com.colpencil.redwood.view.impl.IMessageView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import rx.Observer;

/**
 * 描述：我的消息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class MyMessagePresenter extends ColpencilPresenter<IMessageView> {

    private IMyMessageModel myMessageModel;

    public MyMessagePresenter() {
        myMessageModel = new MyMessageModel();
    }

    public void getContent(final int pageNo, int pageSize) {
        myMessageModel.loadData(pageNo, pageSize);
        Observer<MessageReturn> observer = new Observer<MessageReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showError(null, null);
            }

            @Override
            public void onNext(MessageReturn messageReturn) {
                if (messageReturn.getCode().equals("1")) {
                    mView.loadData(messageReturn.getResult());
                } else {
                    mView.resultInfor(messageReturn.getCode(), messageReturn.getMessage());
                }
            }
        };
        myMessageModel.sub(observer);
    }

    /**
     * 清除收藏记录
     */
    public void removeAllMsg() {
        myMessageModel.removeAllMsg();
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result result) {
                Log.e("返回值", "清除消息记录：" + result.toString());
                mView.resultInfor(result.getCode() + "", result.getMessage());
                if (result.getCode().equals("1")) {//删除成功
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(30);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                }
            }
        };
        myMessageModel.subMsg(observer);
    }

    /**
     * 清除收藏记录
     */
    public void removeById(int msgId) {
        myMessageModel.removeById(msgId);
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result result) {
                mView.delete(result);
            }
        };
        myMessageModel.subMsg(observer);
    }
}

