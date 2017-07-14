package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.BrowsingGoodDate;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.model.BrowsingGoodModel;
import com.colpencil.redwood.model.imples.IBrowsingGoodModel;
import com.colpencil.redwood.view.impl.IBrowsingGoodView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 描述：商品
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class BrowsingGoodPresenter extends ColpencilPresenter<IBrowsingGoodView> {

    private IBrowsingGoodModel model;

    public BrowsingGoodPresenter() {
        model = new BrowsingGoodModel();
    }

    public void getContent(final long pageNo, int pageSize) {
        model.loadData(pageNo, pageSize);
        Observer<ListResult<BrowsingGoodDate>> observer = new Observer<ListResult<BrowsingGoodDate>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "商品浏览记录:" + e.toString());
            }

            @Override
            public void onNext(ListResult<BrowsingGoodDate> result) {
                if (result.getCode() == 0) {
                    if (pageNo == 0) {
                        mView.refresh(result.getResult());
                    } else {
                        mView.loadMore(result.getResult());
                    }
                } else {
                    mView.fail(result.getMessage());
                }
            }
        };
        model.sub(observer);
    }

    public void delete(int foot_id) {
        model.delete(foot_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.delete(result);
            }
        };
        model.subDelete(observer);
    }
}

