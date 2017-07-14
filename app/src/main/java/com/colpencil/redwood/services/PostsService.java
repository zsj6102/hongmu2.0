package com.colpencil.redwood.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.PostInfo;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
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

public class PostsService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void submit(Intent intent) {
        PostInfo info = (PostInfo) intent.getSerializableExtra("data");
        HashMap<String, RequestBody> params = new HashMap<>();
        for (int i = 0; i < info.getFileList().size(); i++) {
            File file = info.getFileList().get(i);
            if (file != null) {
                RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
                params.put("image" + (i + 1) + "\"; filename=\"avatar.jpg", body);
            }
        }
        params.put("sec_id", RequestBody.create(null, info.getSec_id()));
        params.put("ote_title", RequestBody.create(null, info.getTitle()));
        params.put("ote_content", RequestBody.create(null, info.getContent()));
        params.put("url", RequestBody.create(null, info.getUrl() + ""));
        params.put("member_id", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""));
        params.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")));
        RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .submitToServer(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CommonResult, CommonResult>() {
                    @Override
                    public CommonResult call(CommonResult result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CommonResult commonResult) {
                        ToastTools.showShort(PostsService.this, commonResult.getMsg());
                        if (commonResult.getCode().equals("1")) {
                            RefreshMsg msg = new RefreshMsg();
                            msg.setType(3);
                            RxBus.get().post("refreshmsg", msg);
                        }
                        stopSelf();
                    }
                });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        submit(intent);
        return super.onStartCommand(intent, flags, startId);
    }
}
