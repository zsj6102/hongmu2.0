package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.model.imples.IPostNewsModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class PostNewsModel implements IPostNewsModel {

    private Observable<ListResult<CategoryVo>> tag;
    private Observable<EntityResult<String>> cyclopedia;
    private Observable<EntityResult<String>> news;

    @Override
    public void loadTag(int cat_type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cat_type", cat_type + "");
        tag = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadHomeTag(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<CategoryVo>, ListResult<CategoryVo>>() {
                    @Override
                    public ListResult<CategoryVo> call(ListResult<CategoryVo> homeTagResult) {
                        return homeTagResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subTag(Observer<ListResult<CategoryVo>> observer) {
        tag.subscribe(observer);
    }

    @Override
    public void postCyclopedia(int cat_id, File mFile, String title, String content, List<File> files, String child_cat_id, int game) {
        Map<String, RequestBody> params = new HashMap<>();
        if (mFile.exists()) {
            RequestBody body = RequestBody.create(MediaType.parse("image/png"), mFile);
            params.put("title_img\"; filename=\"avatar.jpg", body);
        }
        if (!ListUtils.listIsNullOrEmpty(files)) {
            for (int i = 0; i < files.size(); i++) {
                File file1 = files.get(i);
                if (file1.exists()) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), file1);
                    params.put("image" + (i + 1) + "\"; filename=\"avatar" + (i + 1) + ".jpg", body);
                }
            }
        }
        params.put("cat_id", RequestBody.create(null, cat_id + ""));
        params.put("title", RequestBody.create(null, title));
        params.put("content", RequestBody.create(null, content));
        params.put("child_cat_id", RequestBody.create(null, child_cat_id + ""));
        params.put("game", RequestBody.create(null, game + ""));
        params.put("member_id", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""));
        params.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")));
        cyclopedia = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .postCyclopedia(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCyclopedia(Observer<EntityResult<String>> observer) {
        cyclopedia.subscribe(observer);
    }

    @Override
    public void postNews(int cat_id, File mFile, String title, String content, List<File> files) {
        Map<String, RequestBody> params = new HashMap<>();
        if (mFile.exists()) {
            RequestBody body = RequestBody.create(MediaType.parse("image/png"), mFile);
            params.put("title_img\"; filename=\"avatar.jpg", body);
        }
        if (!ListUtils.listIsNullOrEmpty(files)) {
            for (int i = 0; i < files.size(); i++) {
                File file1 = files.get(i);
                if (file1.exists()) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/png"), file1);
                    params.put("image" + (i + 1) + "\"; filename=\"avatar.jpg", body);
                }
            }
        }
        params.put("cat_id", RequestBody.create(null, cat_id + ""));
        params.put("title", RequestBody.create(null, title));
        params.put("content", RequestBody.create(null, content));
        params.put("member_id", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + ""));
        params.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")));
        news = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .postCyclopedia(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subNews(Observer<EntityResult<String>> observer) {
        news.subscribe(observer);
    }
}
