package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.CyclopediaItem;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.CyclopediaFragmentModel;
import com.colpencil.redwood.model.imples.ICyclopediaFragmentModel;
import com.colpencil.redwood.view.impl.ICyclopediaFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import rx.Observer;

/**
 * 描述：商品
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class CyclopediaFragmentPresenter extends ColpencilPresenter<ICyclopediaFragmentView> {

    private ICyclopediaFragmentModel model;

    public CyclopediaFragmentPresenter() {
        model = new CyclopediaFragmentModel();
    }

    public void getContent(final int pageNo, int pageSize) {
        model.loadData(pageNo, pageSize);
        Observer<ListResult<CyclopediaItem>> observer = new Observer<ListResult<CyclopediaItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<CyclopediaItem> result) {
                if (result.getCode() == 0) {
                    if (pageNo == 1) {
                        mView.refresh(result.getResult());
                    } else {
                        mView.loadMore(result.getResult());
                    }
                } else {
                    mView.resultInfor(result.getCode() + "", result.getMessage());
                }
            }
        };
        model.sub(observer);
    }

    /**
     * 根据收藏id，删除收藏信息
     *
     * @param favorite_id
     */
    public void removeById(int favorite_id) {
        model.removeById(favorite_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.deleteResult(result);
                RxBusMsg msg = new RxBusMsg();
                msg.setType(32);
                RxBus.get().post("rxBusMsg", msg);
            }
        };
        model.subRemove(observer);
    }

    /**
     * 新闻
     *
     * @param page
     * @param pageSize
     */
    public void loadNews(final int page, int pageSize) {
        model.loadNews(page, pageSize);
        Observer<ListResult<CyclopediaItem>> observer = new Observer<ListResult<CyclopediaItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CyclopediaItem> result) {
                if (result.getCode() == 0) {
                    if (page == 1) {
                        mView.refresh(result.getResult());
                    } else {
                        mView.loadMore(result.getResult());
                    }
                } else {
                    mView.resultInfor(result.getCode() + "", result.getMessage());
                }
            }
        };
        model.subNews(observer);
    }

    /**
     * 清除所有收藏记录
     */
    public void removeAllCollection(int favorite_type) {
        model.removeAll(favorite_type);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.removeAll(result);
            }
        };
        model.subRemoveAll(observer);
    }

}

