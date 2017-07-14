package com.colpencil.redwood.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CyclopediaInfo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CyclopediaService extends Service {

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
        CyclopediaInfo info = (CyclopediaInfo) intent.getSerializableExtra("data");
        Map<String, RequestBody> params = new HashMap<>();
        if (info.getCoverFile().exists()) {
            RequestBody body = RequestBody.create(MediaType.parse("image/png"), info.getCoverFile());
            params.put("title_img\"; filename=\"avatar.jpg", body);
        }
        if (!ListUtils.listIsNullOrEmpty(info.getFileList())) {
            for (int i = 0; i < info.getFileList().size(); i++) {
                File file1 = info.getFileList().get(i);
                if (file1.exists()) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), file1);
                    params.put("image" + (i + 1) + "\"; filename=\"avatar" + (i + 1) + ".jpg", body);
                }
            }
        }
        params.put("cat_id", RequestBody.create(null, info.getType() + ""));
        params.put("title", RequestBody.create(null, info.getTitle()));
        params.put("content", RequestBody.create(null, info.getContent()));
        params.put("child_cat_id", RequestBody.create(null, info.getSec_id() + ""));
        params.put("game", RequestBody.create(null, info.getGame() + ""));
        params.put("member_id", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""));
        params.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")));
        RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .postCyclopedia(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EntityResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(EntityResult<String> result) {
                        ToastTools.showShort(CyclopediaService.this, result.getMsg());
                        if (result.getCode() == 0) {
                            RefreshMsg msg = new RefreshMsg();
                            msg.setType(10);
                            RxBus.get().post("refreshmsg", msg);
                        }
                    }
                });
    }
}
