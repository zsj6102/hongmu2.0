package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.LogisTicsBean;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.WriteLogisticsModel;
import com.colpencil.redwood.model.imples.IWriteLogisticsModel;
import com.colpencil.redwood.view.impl.IWriteLogisticsView;
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
public class WriteLogisticsPresenter extends ColpencilPresenter<IWriteLogisticsView> {

    private IWriteLogisticsModel model;

    public WriteLogisticsPresenter() {
        model = new WriteLogisticsModel();
    }

    /**
     * 提交物流信息
     */
    public void sumbitInfor(HashMap<String, String> map) {
        model.sumbit(map);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultCodeInt result) {
                if (result.getCode() == 1) {//物流信息提交成功
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(36);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                    mView.submitSuccess(result.getMsg());
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        model.subResult(observer);
    }

    public void loadLogistic(int item_id, String order_id) {
        model.loadLogisTics(item_id, order_id);
        Observer<ListResult<LogisTicsBean>> observer = new Observer<ListResult<LogisTicsBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<LogisTicsBean> result) {
                if (result.getCode() == 1) {
                    mView.loadGis(result.getLogi());
                }
            }
        };
        model.subLogistic(observer);
    }

}

