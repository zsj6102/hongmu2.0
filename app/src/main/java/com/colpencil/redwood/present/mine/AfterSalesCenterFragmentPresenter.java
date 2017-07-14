package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.AfterSalesCenterReturn;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.AfterSalesCenterFragmentModel;
import com.colpencil.redwood.model.imples.IAfterSalesFragmentModel;
import com.colpencil.redwood.view.impl.IAfterSalesCenterFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.HashMap;

import rx.Observer;

/**
 * 描述：售后中心
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class AfterSalesCenterFragmentPresenter extends ColpencilPresenter<IAfterSalesCenterFragmentView> {

    private IAfterSalesFragmentModel afterSalesFragmentModel;

    public AfterSalesCenterFragmentPresenter() {
        afterSalesFragmentModel = new AfterSalesCenterFragmentModel();
    }

    public void getContent(final int pageNo, int pageSize,int operationType) {
        afterSalesFragmentModel.loadData(pageNo, pageSize,operationType);
        Observer<AfterSalesCenterReturn> observer = new Observer<AfterSalesCenterReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值","售后中心异常："+e.toString());
            }

            @Override
            public void onNext(AfterSalesCenterReturn afterSalesCenterReturn) {
                Log.e("返回值","售后中心："+afterSalesCenterReturn.toString());
                if(afterSalesCenterReturn.getCode()==1){//数据请求成功
                    if (pageNo == 1) {
                        mView.refresh(afterSalesCenterReturn.getData());
                    } else {
                        mView.loadMore(afterSalesCenterReturn.getData());
                    }
                }else{
                    mView.fail(afterSalesCenterReturn.getCode(),afterSalesCenterReturn.getMsg());
                }

            }
        };
        afterSalesFragmentModel.sub(observer);
    }

    /**
     * 取消售后申请
     */
    public void cancelAfterSale(HashMap<String,String> map) {
        afterSalesFragmentModel.cancelAfterSale(map);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值","取消售后申请异常："+e.toString());
            }

            @Override
            public void onNext(ResultCodeInt result) {
                Log.e("返回值","取消售后申请："+result.toString());
                if (result.getCode() == 1) {//取消售后成功
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(36);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                    mView.success(result.getMsg());
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        afterSalesFragmentModel.subResult(observer);
    }

    /**
     * 获取客服电话
     */
    public void getPhoneNum() {
        afterSalesFragmentModel.getPhoneNum();
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
                Log.e("返回值", "获取售后电话：" + result.toString());
                if (result.getCode() == 1) {//成功获取售后电话
                    mView.callPhone(result.getServicePhone());
                } else {
                    mView.fail(result.getCode(), result.getMsg());
                }
            }
        };
        afterSalesFragmentModel.subResult(observer);
    }

}

