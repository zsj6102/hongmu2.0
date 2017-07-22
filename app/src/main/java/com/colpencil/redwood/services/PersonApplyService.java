package com.colpencil.redwood.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.PersonApplyInfo;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.result.ApplyReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.HashMap;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
public class PersonApplyService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        submit(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void submit(Intent intent) {
        PersonApplyInfo info = (PersonApplyInfo) intent.getSerializableExtra("data");
        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("store_type", OkhttpUtils.toRequestBody(info.getStore_type()));
        params.put("token", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getString("token")));
        params.put("member_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getInt("member_id") + ""));
        params.put("realname", OkhttpUtils.toRequestBody(info.getReal_name()));
        params.put("id_number", OkhttpUtils.toRequestBody(info.getId_number()));
        params.put("id_img\";filename=\"1.png", RequestBody.create(MediaType.parse("image/png"), info.getId_img()));
        params.put("id_img_opposite\";filename=\"2.png", RequestBody.create(MediaType.parse("image/png"), info.getId_img_opposite()));
        params.put("region_id", OkhttpUtils.toRequestBody(info.getRegion_id()));
        params.put("address", OkhttpUtils.toRequestBody(info.getAddress()));
        params.put("biz_type", OkhttpUtils.toRequestBody(info.getBiz_type()));
        params.put("card", OkhttpUtils.toRequestBody(info.getCard()));
        params.put("card_type", OkhttpUtils.toRequestBody(info.getCard_type()));
        params.put("cardholder", OkhttpUtils.toRequestBody(info.getCardholder()));
        RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getApplyResult(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ApplyReturn, ApplyReturn>() {
                    @Override
                    public ApplyReturn call(ApplyReturn sellApplyResultInfo) {
                        return sellApplyResultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ApplyReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastTools.showShort(PersonApplyService.this, "系统出错");
            }

            @Override
            public void onNext(ApplyReturn applyReturn) {

                if (applyReturn.getCode() == 0) {
                    ToastTools.showShort(PersonApplyService.this, "提交成功");
                    RefreshMsg msg = new RefreshMsg();
                    msg.setType(22);//个人 二期
                    RxBus.get().post("refreshmsg", msg);
                }else if(applyReturn.getCode() == 1){
                    ToastTools.showShort(PersonApplyService.this, "系统异常");
                }else if(applyReturn.getCode() == 2){
                    ToastTools.showShort(PersonApplyService.this, "提交失败");
                }
            }
        });
    }
}
