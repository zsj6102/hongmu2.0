package com.colpencil.redwood.present.cyclopedia;

import android.content.Context;

import com.colpencil.redwood.bean.result.HotResult;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.model.SearchModel;
import com.colpencil.redwood.model.imples.ISearchModel;
import com.colpencil.redwood.view.impl.ISearchView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:最热搜索的Presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class HotSearchPresenter extends ColpencilPresenter<ISearchView> {

    private ISearchModel model;

    public HotSearchPresenter() {
        model = new SearchModel();
    }

    /**
     * 获取热门搜索
     *
     * @param cat_id
     */
    public void loadHot(int cat_id) {
        model.loadHot(cat_id);
        Observer<HotResult> observer = new Observer<HotResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(HotResult hotResult) {
                if (hotResult.getCode().equals("0")) {
                    mView.hot(hotResult.getResult());
                }
            }
        };
        model.subHot(observer);
    }

    /**
     * 获取搜索历史
     *
     * @param cat_id
     * @param context
     */
    public void loadHistory(final int cat_id, final Context context) {
        Observable<List<String>> observable = Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(removeElements(DaoUtils.getHistory(cat_id, context)));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<List<String>> observer = new Observer<List<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<String> list) {
                mView.history(list);
            }
        };
        observable.subscribe(observer);
    }

    public List<String> removeElements(List<String> list) {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!mList.contains(list.get(i))) {
                mList.add(list.get(i));
            }
        }
        return mList;
    }
}
