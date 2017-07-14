package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.model.HelpModel;
import com.colpencil.redwood.model.imples.IHelpModel;
import com.colpencil.redwood.view.impl.IAnnounceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:帮助与反馈的presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class HelpPresenter extends ColpencilPresenter<IAnnounceView> {

    private IHelpModel model;

    public HelpPresenter() {
        model = new HelpModel();
    }

    /**
     * 关于我们的url
     */
    public void loadAboutus() {
        model.loadAboutus();
        Observer<AnnounceResult> observer = new Observer<AnnounceResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AnnounceResult helpResult) {
                mView.loadSuccess(helpResult);
            }
        };
        model.sub(observer);
    }

    public void loadHelp() {
        model.loadHelp();
        Observer<AnnounceResult> observer = new Observer<AnnounceResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AnnounceResult helpResult) {
                mView.loadSuccess(helpResult);
            }
        };
        model.subhelp(observer);
    }
}
