package com.colpencil.redwood.present;

import android.util.Log;

import com.colpencil.redwood.bean.result.OfficialResult;
import com.colpencil.redwood.model.CustomListModel;
import com.colpencil.redwood.model.imples.ICustomListModel;
import com.colpencil.redwood.view.impl.ICustomListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

public class CustomListPresenter extends ColpencilPresenter<ICustomListView> {

    private ICustomListModel model;

    public CustomListPresenter() {
        model = new CustomListModel();
    }


    public void loadGoods() {
        model.loadGoods();
        Observer<OfficialResult> observer = new Observer<OfficialResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.getMessage());
            }

            @Override
            public void onNext(OfficialResult result) {
                if (result.getCode().equals("1")) {
                    mView.loadGood(result.getResult());
                } else {
                    mView.loadError(result.getMsg());
                }
            }
        };
        model.sub(observer);
    }
}
