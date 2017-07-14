package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.MyCollectionModel;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.imples.IMyCollectionModel;
import com.colpencil.redwood.view.impl.IMyCollectionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import rx.Observer;

/**
 * 描述：商品收藏
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class MyCollectionPresenter extends ColpencilPresenter<IMyCollectionView> {

    private IMyCollectionModel myCollectionModel;

    public MyCollectionPresenter() {
        myCollectionModel = new MyCollectionModel();
    }

    /**
     * 清除收藏记录
     */
    public void removeCollection() {
        myCollectionModel.removeAllCollection();
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result result) {
                mView.removeResult(result.getCode(), result.getMessage());
                if (result.getCode().equals("0")) {//删除成功
                    RxBusMsg rxBusMsg=new RxBusMsg();
                    rxBusMsg.setType(26);
                    RxBus.get().post("rxBusMsg",rxBusMsg);
                }
            }
        };
        myCollectionModel.sub(observer);
    }
}

