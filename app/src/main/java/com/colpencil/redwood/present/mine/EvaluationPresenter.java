package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.tools.FileUtils;
import com.colpencil.redwood.model.EvaluationModel;
import com.colpencil.redwood.model.imples.IEvaluationModel;
import com.colpencil.redwood.view.impl.IEvaluationView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.io.File;
import java.util.List;

import rx.Observer;

public class EvaluationPresenter extends ColpencilPresenter<IEvaluationView> {

    private IEvaluationModel model;

    public EvaluationPresenter() {
        model = new EvaluationModel();
    }

    public void submitComment(int rate,int order_id, String goods_id, String content, List<File> files, int type) {
        model.submitComment(rate,order_id, goods_id, content, files, type);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultCodeInt result) {
                mView.submitResult(result);
                FileUtils.deleteAllFile(Constants.sdCardPath);
            }
        };
        model.sub(observer);
    }
}
