package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.NewsInfoVo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.INewsListModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description:新闻动态的model
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public class NewsListModel implements INewsListModel {

    Observable<ListResult<NewsInfoVo>> observable;

    @Override
    public void loadNews(String cat_id, int page, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cat_id", cat_id);
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .loadNewsList(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ListResult<NewsInfoVo>, ListResult<NewsInfoVo>>() {
                    @Override
                    public ListResult<NewsInfoVo> call(ListResult<NewsInfoVo> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ListResult<NewsInfoVo>> observer) {
        observable.subscribe(observer);
    }
}
