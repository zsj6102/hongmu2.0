package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.MusicResourseReturn;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMusicCenterModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：我的评价
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 04
 */
public class MusicCenterModel implements IMusicCenterModel {

    private Observable<MusicResourseReturn> songObservable;

    @Override
    public void loadData(int pageNo, int pageSize) {
        songObservable = RetrofitManager
                .getInstance(1, App.getInstance(),UrlConfig.PHILHARMONIC_HOST)
                .createApi(RedWoodApi.class).getMusicUrl(pageNo,pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<MusicResourseReturn,MusicResourseReturn>() {
                    @Override
                    public MusicResourseReturn call(MusicResourseReturn musicResourseReturn) {
                        return musicResourseReturn;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
    @Override
    public void sub(Observer<MusicResourseReturn> subscriber) {
        songObservable.subscribe(subscriber);
    }
}
