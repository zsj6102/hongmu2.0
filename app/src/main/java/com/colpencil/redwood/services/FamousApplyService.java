package com.colpencil.redwood.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.FamousApplyInfo;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.result.ApplyReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.HashMap;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import static com.colpencil.redwood.view.activity.HomeActivity.result;
import static com.unionpay.mobile.android.global.a.F;


public class FamousApplyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        submit(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void submit(Intent intent) {
        FamousApplyInfo info = (FamousApplyInfo) intent.getSerializableExtra("data");
        HashMap<String, RequestBody> params = new HashMap<>();
        if (!ListUtils.listIsNullOrEmpty(info.getPics())) {
            for (int i = 0; i < info.getPics().size(); i++) {
                File file1 = info.getPics().get(i);
                if (file1.exists()) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), file1);
                    params.put("pics\"; filename=\"avatar" + i + ".jpg", body);
                }
            }
        }
        if (!ListUtils.listIsNullOrEmpty(info.getPrize_pics())) {
            for (int i = 0; i < info.getPrize_pics().size(); i++) {
                File file1 = info.getPrize_pics().get(i);
                if (file1.exists()) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), file1);
                    params.put("prize_pics\"; filename=\"avatar" + i + ".jpg", body);
                }
            }
        }
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
        params.put("referrer", OkhttpUtils.toRequestBody(info.getReferrer()));
        params.put("studio_name", OkhttpUtils.toRequestBody(info.getStudio_name()));
        params.put("studio_region_id", OkhttpUtils.toRequestBody(info.getStudio_region_id()));
        params.put("studio_address", OkhttpUtils.toRequestBody(info.getStudio_address()));
        params.put("ps_name", OkhttpUtils.toRequestBody(info.getPs_name()));
        params.put("ps_region_id", OkhttpUtils.toRequestBody(info.getPs_region_id()));
        params.put("ps_address", OkhttpUtils.toRequestBody(info.getPs_address()));
        params.put("store_name", OkhttpUtils.toRequestBody(info.getStore_name()));
        params.put("store_logo\";filename=\"3.png", RequestBody.create(MediaType.parse("image/png"), info.getStore_file()));
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
                ToastTools.showShort(FamousApplyService.this, "系统出错");
            }

            @Override
            public void onNext(ApplyReturn applyReturn) {

                if (applyReturn.getCode() == 0) {
                    ToastTools.showShort(FamousApplyService.this, "提交成功");
                    RefreshMsg msg = new RefreshMsg();
                    msg.setType(21);//名师 二期
                    RxBus.get().post("refreshmsg", msg);
                }else if(applyReturn.getCode() == 1){
                    ToastTools.showShort(FamousApplyService.this, "系统异常");
                }else if(applyReturn.getCode() == 2){
                    ToastTools.showShort(FamousApplyService.this, "提交失败");
                }
            }
        });
    }


}
