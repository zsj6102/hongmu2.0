package com.colpencil.redwood.present.home;

import android.util.Log;

import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.NewsInfoVo;
import com.colpencil.redwood.model.NewsListModel;
import com.colpencil.redwood.model.imples.INewsListModel;
import com.colpencil.redwood.view.impl.INewsListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Observer;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:新闻列表的presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class NewsListPresenter extends ColpencilPresenter<INewsListView> {

    private INewsListModel model;

    public NewsListPresenter() {
        model = new NewsListModel();
    }

    /**
     * 新闻列表
     *
     * @param cat_id
     * @param page
     * @param pageSize
     */
    public void loadNewsList(String cat_id, final int page, int pageSize) {
        model.loadNews(cat_id, page, pageSize);
        Observer<ListResult<NewsInfoVo>> observer = new Observer<ListResult<NewsInfoVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("eeeeeee", e.getMessage());
            }

            @Override
            public void onNext(ListResult<NewsInfoVo> result) {
                if (result.getSuccess() == 0) {
                    if (page == 1) {
                        mView.refresh(result.getArticleListResult());
                    } else {
                        mView.loadMore(result.getArticleListResult());
                    }
                } else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        model.sub(observer);
    }

    public void getApplyStatus(HashMap<String,String> params){
        model.applyStatus(params);
        Subscriber<ApplyStatusReturn> subscriber = new Subscriber<ApplyStatusReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mView!=null){
                    mView.getStatusError(e.getMessage());
                }

            }

            @Override
            public void onNext(ApplyStatusReturn applyStatusReturn) {
                if(mView!=null){
                    mView.getStatusSucess(applyStatusReturn);
                }

            }
        };
        model.subStauts(subscriber);
    }
}
