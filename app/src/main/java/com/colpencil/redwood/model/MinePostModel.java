package com.colpencil.redwood.model;

import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.model.imples.IMinePostModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MinePostModel implements IMinePostModel {
    private Observable<ResultInfo<List<MinePostItem>>> observable;
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
}
