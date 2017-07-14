package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.tools.FileUtils;
import com.colpencil.redwood.model.AfterSalesModel;
import com.colpencil.redwood.model.imples.IAfterSalesModel;
import com.colpencil.redwood.view.impl.IAfterSalesView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import rx.Observer;

/**
 * @author 曾 凤
 * @Description: 售后申请
 * @Email 20000263@qq.com
 * @date 2016/8/10
 */
public class AfterSalesPresenter extends ColpencilPresenter<IAfterSalesView> {

    private IAfterSalesModel afterSalesModel;

    public AfterSalesPresenter() {
        afterSalesModel = new AfterSalesModel();
    }

    /**
     * 获取退款原因
     */
    public void loadReason(final int type) {
        afterSalesModel.loadReason(type);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showError(null, null);
            }

            @Override
            public void onNext(ResultCodeInt result) {
                Log.e("返回值", "售后原因：" + result.toString());
                if (result.getCode() == 1) {//原因获取成功
                    if (type == 1) {
                        mView.loadReason(result.getReturnResons(), type);
                    } else {
                        mView.loadReason(result.getExchangeResons(), type);
                    }
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        afterSalesModel.subResult(observer);
    }

    public void sumbtRefundMoney(HashMap<String, String> map, List<File> files) {
        afterSalesModel.sumbit(map, files);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "申请售后异常：" + e.toString());
            }

            @Override
            public void onNext(ResultCodeInt result) {
                Log.e("返回值", "申请售后：" + result.toString());
                if (result.getCode() == 1) {//原因获取成功
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(35);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                    mView.submitSuccess(result.getMsg());
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
                FileUtils.deleteAllFile(Constants.sdCardPath);
            }
        };
        afterSalesModel.subResult(observer);
    }

}

