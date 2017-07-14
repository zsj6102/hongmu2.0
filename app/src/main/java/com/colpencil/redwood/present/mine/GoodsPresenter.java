package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.GoodsItem;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.GoodsModel;
import com.colpencil.redwood.model.imples.IGoodsModel;
import com.colpencil.redwood.view.impl.IGoodsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import rx.Observer;

/**
 * 描述：商品收藏
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class GoodsPresenter extends ColpencilPresenter<IGoodsView> {

    private IGoodsModel goodsModel;

    public GoodsPresenter() {
        goodsModel = new GoodsModel();
    }

    public void getContent(final int pageNo, int pageSize) {
        goodsModel.loadData(pageNo, pageSize);
        Observer<ListResult<GoodsItem>> observer = new Observer<ListResult<GoodsItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<GoodsItem> result) {
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
        goodsModel.sub(observer);
    }

    /**
     * 根据收藏id，删除收藏信息
     *
     * @param favorite_id
     */
    public void removeById(int favorite_id) {
        goodsModel.removeById(favorite_id);
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
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(32);
                RxBus.get().post("rxBusMsg", rxBusMsg);
            }
        };
        goodsModel.subRemove(observer);
    }

    /**
     * 清除收藏记录
     */
    public void removeCollection() {
        goodsModel.removeAll();
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.deleteAll(result);
            }
        };
        goodsModel.subRemoveAll(observer);
    }
}

