package com.colpencil.redwood.present.cyclopedia;

import android.util.Log;

import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.CyclopediaResult;
import com.colpencil.redwood.model.CyclopediaItemModel;
import com.colpencil.redwood.model.imples.ICyclopediaItemModel;
import com.colpencil.redwood.view.impl.ICyclopediaTagView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description: 首页-百科的Presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/7
 */
public class CycloPediaPresenter extends ColpencilPresenter<ICyclopediaTagView> {

    private ICyclopediaItemModel model;

    public CycloPediaPresenter() {
        model = new CyclopediaItemModel();
    }

    /**
     * 获取百科
     *
     * @param cat_id
     * @param child_cat_id
     * @param page
     * @param pageSize
     */
    public void loadTagItem(String cat_id, String child_cat_id, final int page, int pageSize, String iscommend) {
        model.loadTagItem(cat_id, child_cat_id, page, pageSize, iscommend);
        Observer<CyclopediaResult> observer = new Observer<CyclopediaResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", "服务器请求错误!");
            }

            @Override
            public void onNext(CyclopediaResult result) {
                if (result.getSuccess() == 0) {
                    if (page == 1) {
                        mView.refresh(result.getArticleListResult());
                    } else {
                        mView.loadmore(result.getArticleListResult());
                    }
                } else {
                    mView.loadError();
                }
            }
        };
        model.subTagItem(observer);
    }

    public void search(int cat_id, String keyword, final int page, int pageSize) {
        model.search(cat_id, keyword, page, pageSize);
        Observer<ListResult<CyclopediaInfoVo>> observer = new Observer<ListResult<CyclopediaInfoVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CyclopediaInfoVo> result) {
                if (result.getSuccess() == 0) {
                    if (page == 1) {
                        mView.refresh(result.getArticleListResult());
                    } else {
                        mView.loadmore(result.getArticleListResult());
                    }
                } else {
                    mView.loadError();
                }
            }
        };
        model.subSearch(observer);
    }
}
