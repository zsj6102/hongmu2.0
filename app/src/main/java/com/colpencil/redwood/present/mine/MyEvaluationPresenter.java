package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.CommentReturn;
import com.colpencil.redwood.model.MyEvaluationModel;
import com.colpencil.redwood.model.imples.IMyEvaluationModel;
import com.colpencil.redwood.view.impl.IMyEvaluationView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 描述：我的评价网络请求
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class MyEvaluationPresenter extends ColpencilPresenter<IMyEvaluationView> {

    private IMyEvaluationModel myEvaluationModel;

    public MyEvaluationPresenter() {
        myEvaluationModel = new MyEvaluationModel();
    }

    public void getContent(final int pageNo, int pageSize) {
        myEvaluationModel.loadData(pageNo, pageSize);
        Observer<CommentReturn> observer = new Observer<CommentReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showError(null, null);
            }

            @Override
            public void onNext(CommentReturn commentReturn) {
                if (commentReturn.getCode().equals("0")) {
                    if (pageNo == 1) {
                        mView.refresh(commentReturn.getResult());
                    } else {
                        mView.loadMore(commentReturn.getResult());
                    }
                } else {
                    mView.fail(commentReturn.getCode(), commentReturn.getMessage());
                }

            }
        };
        myEvaluationModel.sub(observer);
    }

}

