package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.PlainRack;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.model.ShelfModel;
import com.colpencil.redwood.model.imples.IShelfModel;
import com.colpencil.redwood.view.impl.IShelfView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;
import java.util.Map;

import rx.Observer;


public class ShelfPresenter extends ColpencilPresenter<IShelfView> {
    private IShelfModel model;

    public ShelfPresenter() {
        model = new ShelfModel();
    }

    public void getPlainRack(final int pageNo, Map<String, String> map) {
        model.getNormalShelf(map);
        Observer<ResultInfo<List<PlainRack>>> observer = new Observer<ResultInfo<List<PlainRack>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadFail("服务器异常");
            }

            @Override
            public void onNext(ResultInfo<List<PlainRack>> listResultInfo) {
                if (listResultInfo != null && mView != null) {
                    if (listResultInfo.getCode() == 0) {
                        if (pageNo == 1) {
                            mView.refresh(listResultInfo);
                        } else {
                            mView.loadMore(listResultInfo);
                        }
                    } else {
                        mView.loadFail(listResultInfo.getMessage());
                    }
                }
            }
        };
        model.subNormal(observer);
    }
}
