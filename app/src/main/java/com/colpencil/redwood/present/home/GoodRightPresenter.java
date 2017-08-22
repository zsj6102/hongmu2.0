package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.GoodComment;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.NumReturn;
import com.colpencil.redwood.model.GoodRightModel;
import com.colpencil.redwood.model.imples.IGoodRightModel;
import com.colpencil.redwood.view.impl.IGoodRightView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:商品评论的Presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class GoodRightPresenter extends ColpencilPresenter<IGoodRightView> {

    private IGoodRightModel model;

    public GoodRightPresenter() {
        model = new GoodRightModel();
    }

    /**
     * 商品评论
     *
     * @param goodsid
     */
    public void loadComment(String goodsid, final int page, int pageSize) {
        model.loadComment(goodsid, page, pageSize);
        Observer<ListResult<GoodComment>> observer = new Observer<ListResult<GoodComment>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<GoodComment> gCommentResult) {
                if (gCommentResult.getCode() == 1) {
                    if (page == 1) {
                        mView.refresh(gCommentResult.getCommentList());
                    } else {
                        mView.loadMore(gCommentResult.getCommentList());
                    }
                }
            }
        };
        model.sub(observer);
    }

    /**
     * 获取评论总数量
     *
     * @param goods_id
     */
    public void loadCommentsNum(int goods_id) {
        model.loadCommentsNum(goods_id);
        Observer<NumReturn> observer = new Observer<NumReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(NumReturn result) {
                mView.loadNums(result);
            }
        };
        model.subCommentsNum(observer);
    }
}
