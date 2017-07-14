package com.colpencil.redwood.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e("WX:", "" + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //支付回调
            switch (resp.errCode) {
                case 0:
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(47);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                    finish();
                    break;
                case -1:
                    Log.e("微信支付", "微信支付失败");
                    RxBusMsg rxBusMsgFail = new RxBusMsg();
                    rxBusMsgFail.setType(48);
                    RxBus.get().post("rxBusMsg", rxBusMsgFail);
                    finish();
                    break;
                case -2:
                    RxBusMsg rxBusMsgCancel = new RxBusMsg();
                    rxBusMsgCancel.setType(49);
                    RxBus.get().post("rxBusMsg", rxBusMsgCancel);
                    finish();
                    break;
            }
        }
    }
}