package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.AddressReturn;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.ReceiptAddressModel;
import com.colpencil.redwood.model.imples.IReceiptAddressModel;
import com.colpencil.redwood.view.impl.IReceiptAddressView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import rx.Observer;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class ReceiptAddressPresenter extends ColpencilPresenter<IReceiptAddressView> {

    private IReceiptAddressModel receiptAddressModel;

    public ReceiptAddressPresenter() {
        receiptAddressModel = new ReceiptAddressModel();
    }

    public void getContent() {
        receiptAddressModel.loadData();
        Observer<AddressReturn> observer = new Observer<AddressReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddressReturn addressReturn) {
                if (addressReturn.getCode().equals("1")) {
                    mView.loadAddressInfor(addressReturn.getAddressInfo());
                } else {
                    mView.reslutInfor(addressReturn.getCode(), addressReturn.getMsg());
                }
            }
        };
        receiptAddressModel.sub(observer);
    }

    /**
     * 删除地址
     */
    public void deleteById(int addrId) {
        receiptAddressModel.deleteById(addrId);
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result result) {
                mView.reslutInfor(result.getCode(), result.getMessage());
                if (result.getCode().equals("1")) {//地址删除成功
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(34);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                }
            }
        };
        receiptAddressModel.subResult(observer);
    }
}

