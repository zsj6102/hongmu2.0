package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.MyCyclopediaInfo;
import com.colpencil.redwood.model.CycloListModel;
import com.colpencil.redwood.model.imples.ICycloListModel;
import com.colpencil.redwood.view.impl.ICycloListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:我的百科Presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CycloListPresenter extends ColpencilPresenter<ICycloListView> {

    private ICycloListModel model;

    public CycloListPresenter() {
        model = new CycloListModel();
    }

    /**
     * 获取百科
     *
     * @param operationType
     * @param pageNo
     * @param pageSize
     */
    public void loadList(String operationType, final int pageNo, int pageSize) {
        model.loadList(operationType, pageNo, pageSize);
        Observer<ListResult<MyCyclopediaInfo>> observer = new Observer<ListResult<MyCyclopediaInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<MyCyclopediaInfo> result) {
                if (result.getCode() == 0) {
                    if (pageNo == 1) {
                        mView.refresh(result.getResult());
                    } else {
                        mView.loadMore(result.getResult());
                    }
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        model.sub(observer);
    }

    /**
     * 获取新闻
     *
     * @param operationType
     * @param pageNo
     * @param pageSize
     */
    public void loadNews(String operationType, final int pageNo, int pageSize) {
        model.loadNews(operationType, pageNo, pageSize);
        Observer<ListResult<MyCyclopediaInfo>> observer = new Observer<ListResult<MyCyclopediaInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<MyCyclopediaInfo> result) {
                if (result.getCode() == 0) {
                    if (pageNo == 1) {
                        mView.refresh(result.getResult());
                    } else {
                        mView.loadMore(result.getResult());
                    }
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        model.subNews(observer);
    }

    public void cancleAudit(int article_id) {
        model.cancleAudit(article_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.cancle(result);
            }
        };
        model.subCancle(observer);
    }

    public void delete(int article_id) {
        model.delete(article_id);
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
