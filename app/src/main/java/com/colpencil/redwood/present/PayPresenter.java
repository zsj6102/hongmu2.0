package com.colpencil.redwood.present;

import com.colpencil.redwood.bean.PayReturn;
import com.colpencil.redwood.model.PayModel;
import com.colpencil.redwood.model.imples.IPayModel;
import com.colpencil.redwood.view.impl.IPayView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;
import rx.Observer;

public class PayPresenter extends ColpencilPresenter<IPayView> {
    private IPayModel payModel;

    public PayPresenter() {
        payModel = new PayModel();
    }

    public void  getPayReturn(HashMap<String, RequestBody> params){
        payModel.payInfor(params);
        Observer<PayReturn>  payReturnObserver = new Observer<PayReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.fail(2, "支付异常");
            }

            @Override
            public void onNext(PayReturn payReturn) {
                if(payReturn.getCode() == 0){
                    if(payReturn.getData().getType() == 0){
                        Map<String, String> map = new HashMap<>();
                        map.put("appid", payReturn.getData().getResult().getAppid());
                        map.put("partnerId", payReturn.getData().getResult().getPartnerid());
                        map.put("prepayid", payReturn.getData().getResult().getPrepayid());
                        map.put("noncestr", payReturn.getData().getResult().getNoncestr());
                        map.put("timestamp", payReturn.getData().getResult().getTimestamp() + "");
                        map.put("sign", payReturn.getData().getResult().getSign());
                        mView.payByWeChat(map);
                    }else if (payReturn.getData().getType() == 1) {   //支付宝
                        mView.payByAlipay(payReturn.getData().getResult().getReStr());
                    } else if (payReturn.getData().getType() == 2) {   //银联
                        mView.payByUnion(payReturn.getData().getTn(), "00");     //00为正式环境，01位测试环境
                    }
                }else{
                    mView.fail(payReturn.getCode(), payReturn.getMessage());
                }
            }
        };
        payModel.subPay(payReturnObserver);
    }
}
