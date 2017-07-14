package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.BrowsingPostDate;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.model.BrowsingPostModel;
import com.colpencil.redwood.model.imples.IBrowsingPostModel;
import com.colpencil.redwood.view.impl.IBrowsingPostView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 描述：商品
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class BrowsingPostPresenter extends ColpencilPresenter<IBrowsingPostView> {

    private IBrowsingPostModel model;

    public BrowsingPostPresenter() {
        model = new BrowsingPostModel();
    }

    public void getContent(final long pageNo, int pageSize) {
        model.loadData(pageNo, pageSize);
        Observer<ListResult<BrowsingPostDate>> observer = new Observer<ListResult<BrowsingPostDate>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "帖子浏览记录异常：" + e.toString());
            }

            @Override
            public void onNext(ListResult<BrowsingPostDate> result) {
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

