package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.PostCollectionReturn;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.PostFragmentModel;
import com.colpencil.redwood.model.imples.IPostFragmentModel;
import com.colpencil.redwood.view.impl.IPostFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import rx.Observer;

/**
 * 描述：帖子收藏
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class PostFragmentPresenter extends ColpencilPresenter<IPostFragmentView> {

    private IPostFragmentModel postFragmentModel;

    public PostFragmentPresenter() {
        postFragmentModel = new PostFragmentModel();
    }

    public void getContent(final int pageNo, int pageSize) {
        postFragmentModel.loadData(pageNo, pageSize);
        Observer<PostCollectionReturn> observer = new Observer<PostCollectionReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "帖子收藏异常:" + e.toString());
            }

            @Override
            public void onNext(PostCollectionReturn postCollectionReturn) {
                if (postCollectionReturn.getCode().equals("0")) {
                    if (pageNo == 1) {
                        mView.refresh(postCollectionReturn.getResult());
                    } else {
                        mView.loadMore(postCollectionReturn.getResult());
                    }
                } else {
                    mView.resultInfor(postCollectionReturn.getCode(), postCollectionReturn.getMessage());
                }
            }
        };
        postFragmentModel.sub(observer);
    }

    /**
     * 根据收藏id，删除收藏信息
     *
     * @param favorite_id
     */
    public void removeById(int favorite_id) {
        postFragmentModel.removeById(favorite_id);
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
        postFragmentModel.subRemove(observer);
    }

    /**
     * 清除收藏记录
     */
    public void removeCollection() {
        postFragmentModel.removeAll();
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
        postFragmentModel.subRemoveAll(observer);
    }
}

