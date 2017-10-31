package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ImageSpan;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IGoodNoteModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GoodNoteModel implements IGoodNoteModel {
    private Observable<ResultInfo<ImageSpan>> observable;
    @Override
    public void loadGoodUrl(HashMap<String, RequestBody> map) {
        observable = RetrofitManager.getInstance(1, App.getInstance(), UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class)
                .getGoodUrl(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResultInfo<ImageSpan>, ResultInfo<ImageSpan>>() {
                    @Override
                    public ResultInfo<ImageSpan> call(ResultInfo<ImageSpan> resultInfo) {
                        return resultInfo;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subUrl(Observer<ResultInfo<ImageSpan>> observer) {
         observable.subscribe(observer);
    }
}
