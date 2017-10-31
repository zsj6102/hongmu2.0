package com.colpencil.redwood.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.FastStoreInfo;
import com.colpencil.redwood.bean.result.ApplyReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
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

/**
 * 修改商品
 */
public class ModifiyStoreService extends Service {
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

        FastStoreInfo info = (FastStoreInfo) intent.getSerializableExtra("data");
        HashMap<String, RequestBody> params = new HashMap<>();
        if (!ListUtils.listIsNullOrEmpty(info.getImage())) {
            for (int i = 0; i < info.getImage().size(); i++) {
                File file1 = info.getImage().get(i);
                if (file1.exists()) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), file1);
                    params.put("image\"; filename=\"avatar" + i + ".jpg", body);
                }
            }
        }
        params.put("goods_id", OkhttpUtils.toRequestBody(info.getGoods_id() + ""));
        params.put("store", OkhttpUtils.toRequestBody(info.getStore()));
        params.put("cat_id", OkhttpUtils.toRequestBody(info.getCat_id()));
        params.put("token", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getString("token")));
        params.put("member_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getInt("member_id") + ""));
        params.put("name", OkhttpUtils.toRequestBody(info.getName()));
        if (info.getPrice() != null) {
            params.put("price", OkhttpUtils.toRequestBody(info.getPrice()));
        }
        if (info.getCover() != null) {
            params.put("cover\";filename=\"1.png", RequestBody.create(MediaType.parse("image/png"), info.getCover()));
        }
        if (info.getIntro() != null) {
            params.put("intro", OkhttpUtils.toRequestBody(info.getIntro()));
        }
        if(info.getSpe_section_id()!=null){
            params.put("spe_section_id",OkhttpUtils.toRequestBody(info.getSpe_section_id()));
        }
        if(info.getMktprice() != null){
            params.put("mktprice", OkhttpUtils.toRequestBody(info.getMktprice()));
        }
        params.put("array_id", OkhttpUtils.toRequestBody(info.getArray_id()));

        RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST).createApi(RedWoodApi.class).getModifyStataus(params).subscribeOn(Schedulers.io()).map(new Func1<ApplyReturn, ApplyReturn>() {
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
                ToastTools.showShort(ModifiyStoreService.this, "系统出错");
                stopSelf();
            }

            @Override
            public void onNext(ApplyReturn applyReturn) {

                if (applyReturn.getCode() == 0) {
                    ToastTools.showShort(ModifiyStoreService.this, "成功");
                } else if (applyReturn.getCode() == 1) {
                    ToastTools.showShort(ModifiyStoreService.this, "系统异常");
                } else if (applyReturn.getCode() == 2) {
                    ToastTools.showShort(ModifiyStoreService.this, "失败");
                }
                stopSelf();//停掉服务
            }
        });
    }
}