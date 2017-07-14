package com.colpencil.redwood.present;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.model.CollectionModel;
import com.colpencil.redwood.model.imples.ICollectionModel;
import com.colpencil.redwood.view.impl.ICollectionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import rx.Observer;

/**
 * Created by xiaobao on 2016/10/30.
 */
public class CollectionPresenter extends ColpencilPresenter<ICollectionView> {

    private ICollectionModel model;

    public CollectionPresenter() {
        model = new CollectionModel();
    }

    /**
     * 分享
     *
     * @param ote_id
     */
    public void share(int ote_id) {
        model.share(ote_id);
        Observer<CommonResult> observer = new Observer<CommonResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommonResult result) {
                mView.shareResult(result);
            }
        };
        model.subShare(observer);
    }

    /**
     * 记录分享
     *
     * @param ote_id
     */
    public void recordShare(int ote_id, String platform) {
        model.recordShare(ote_id, platform);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                RefreshMsg msg = new RefreshMsg();
                msg.setType(4);
                RxBus.get().post("refreshmsg", msg);
            }
        };
        model.subRecord(observer);
    }
}
