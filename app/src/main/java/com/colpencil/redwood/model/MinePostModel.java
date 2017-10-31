package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMinePostModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MinePostModel implements IMinePostModel {
    private Observable<ResultInfo<List<MinePostItem>>> observable;
    private Observable<EntityResult<String>> submit;
    private Observable<EntityResult<String>> like;
    @Override
    public void loadMinePost(Map<String, String> map) {
          observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                  .createApi(RedWoodApi.class)
                  .getMinePost(map)
                  .subscribeOn(Schedulers.io())
                  .map(new Func1<ResultInfo<List<MinePostItem>>, ResultInfo<List<MinePostItem>>>() {
                      @Override
                      public ResultInfo<List<MinePostItem>> call(ResultInfo<List<MinePostItem>> listResultInfo) {
                          return listResultInfo;
                      }
                  }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subData(Observer<ResultInfo<List<MinePostItem>>> observer) {
       observable.subscribe(observer);
    }

    @Override
    public void submitComments(String comContent, int ote_id) {
        submit = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .submitComments(RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""),
                        RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")),
                        RequestBody.create(null, comContent),
                        RequestBody.create(null, ote_id + ""))
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSubmit(Observer<EntityResult<String>> observer) {
        submit.subscribe(observer);
    }

    @Override
    public void like(int ote_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("type", "1");
        params.put("id", ote_id + "");
        like = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .like(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLike(Observer<EntityResult<String>> observer) {
        like.subscribe(observer);
    }
}
