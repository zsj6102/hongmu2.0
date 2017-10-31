package com.colpencil.redwood.present;

import com.colpencil.redwood.bean.PayReturn;
import com.colpencil.redwood.bean.PayType;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.model.PayModel;
import com.colpencil.redwood.model.imples.IPayModel;
import com.colpencil.redwood.view.impl.IPayView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;
import rx.Observer;

public class PayPresenter extends ColpencilPresenter<IPayView> {
    private IPayModel payModel;

    public PayPresenter() {
        payModel = new PayModel();
    }
    Observer<PayReturn>  payReturnObserver = new Observer<PayReturn>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mView.showLoading(e.getMessage());
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
    public void  getPayReturn(HashMap<String, RequestBody> params){
        payModel.payInfor(params);
        payModel.subPay(payReturnObserver);
    }

    public void getPayType(){
        payModel.payType();
        Observer<ResultInfo<List<PayType>>> observer = new Observer<ResultInfo<List<PayType>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<PayType>> listResultInfo) {
                  if(listResultInfo.getCode() == 0 && mView != null){
                      mView.payType(listResultInfo.getData());
                  }
            }
        };
        payModel.subType(observer);
    }

    public  void getCenterReturn(Map<String,String> map){
        payModel.centerPay(map);
        payModel.subCenter(payReturnObserver);
    }

    public  void getMarginPay(Map<String,String> map){
        payModel.marginPay(map);
        payModel.subMargin(payReturnObserver);
    }

    public void getSettledPay(Map<String,String> map){
        payModel.settledPay(map);
        payModel.subSettled(payReturnObserver);
    }


}
