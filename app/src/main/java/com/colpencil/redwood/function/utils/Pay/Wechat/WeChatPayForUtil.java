package com.colpencil.redwood.function.utils.Pay.Wechat;

import android.content.Context;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * <p>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * 作者：ZengFeng
 * <p>
 * 描述：
 */
public class WeChatPayForUtil {
    private IWXAPI api;
    private Context mcontext;
    private Map<String, String> mMap;

    public WeChatPayForUtil(Context context, Map<String, String> map) {
        this.mcontext = context;
        this.mMap = map;
        api = WXAPIFactory.createWXAPI(context, map.get("appid"));
        sumbitApply(map.get("appid"));
    }

    private void sumbitApply(String appID) {
        if (!api.isWXAppInstalled()) {//未安装微信支付
            Toast.makeText(mcontext, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        PayReq req = new PayReq();
        req.appId = appID;
        req.partnerId = mMap.get("partnerId");
        req.prepayId = mMap.get("prepayid");
        req.nonceStr = mMap.get("noncestr");
        req.timeStamp = mMap.get("timestamp");
        req.packageValue = "Sign=WXPay";
        req.sign = mMap.get("sign");
        req.extData = "app data"; // optional
        api.sendReq(req);
    }
}
