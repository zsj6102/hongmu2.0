package com.colpencil.redwood.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;

import com.colpencil.redwood.bean.FastStoreInfo;
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
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
public class PublishStoreService extends Service {
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
        String type = String.valueOf(intent.getStringExtra("type"));
        FastStoreInfo info = (FastStoreInfo) intent.getSerializableExtra("data");
       HashMap<String, RequestBody> params = new HashMap<>();
        if (!ListUtils.listIsNullOrEmpty(info.getImages())) {
            for (int i = 0; i < info.getImages().size(); i++) {
                File file1 = info.getImages().get(i);
                if (file1.exists()) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), file1);
                    params.put("image\"; filename=\"avatar" + i + ".jpg", body);
                }
            }
        }
        if(info.getStore_id()!=null){
            params.put("store_id",OkhttpUtils.toRequestBody(info.getStore_id()));
        }

        params.put("cat_id", OkhttpUtils.toRequestBody(info.getCat_id()));
        params.put("token", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getString("token")));
        params.put("member_id", OkhttpUtils.toRequestBody(SharedPreferencesUtil.getInstance(this).getInt("member_id") + ""));
        params.put("name", OkhttpUtils.toRequestBody(info.getName()));
        if(info.getPrice()!=null){
            params.put("price", OkhttpUtils.toRequestBody(info.getPrice()));
        }
        if(info.getCover()!=null){
            params.put("cover\";filename=\"1.png", RequestBody.create(MediaType.parse("image/png"), info.getCover()));
        }
        if(info.getSpe_section_id()!= null){
            params.put("spe_section_id",OkhttpUtils.toRequestBody(info.getSpe_section_id()));
        }
//        if(type.equals("1")){
        if(info.getIntro()!=null){
            params.put("intro", OkhttpUtils.toRequestBody(info.getIntro()));
        }

//        }
//        if(!type.equals("1")){
            if(info.getMktprice()!=null){
                params.put("mktprice",OkhttpUtils.toRequestBody(info.getMktprice()));
            }
//        }
        params.put("store", OkhttpUtils.toRequestBody(info.getStore()));
        params.put("goods_type", OkhttpUtils.toRequestBody(info.getGoods_type()));
        params.put("warehouseOrshelves", OkhttpUtils.toRequestBody(info.getWarehouseOrshelves()));
        RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getPublishStataus(params)
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
                if(e instanceof ConnectException || e instanceof UnknownHostException || e instanceof SocketException || e instanceof  SocketTimeoutException ){
                    ToastTools.showShort(PublishStoreService.this, "网络连接出错");
                }else{
                    ToastTools.showShort(PublishStoreService.this, "系统出错");
                }

                stopSelf();
            }

            @Override
            public void onNext(ApplyReturn applyReturn) {

                if (applyReturn.getCode() == 0) {

                    ToastTools.showShort(PublishStoreService.this, "成功");
                    RefreshMsg msg = new RefreshMsg();
                    msg.setType(24);//发布速拍
                    RxBus.get().post("refreshmsg", msg);
                }else if(applyReturn.getCode() == 1){
                    ToastTools.showShort(PublishStoreService.this, "系统异常");
                }else if(applyReturn.getCode() == 2){
                    ToastTools.showShort(PublishStoreService.this, "失败");
                }
                stopSelf();//停掉服务
            }
        });
    }
}
