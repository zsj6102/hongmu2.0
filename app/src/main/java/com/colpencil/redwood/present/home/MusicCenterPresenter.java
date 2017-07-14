package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.MusicResourseReturn;
import com.colpencil.redwood.model.MusicCenterModel;
import com.colpencil.redwood.model.imples.IMusicCenterModel;
import com.colpencil.redwood.view.impl.IMusicCenterView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 描述：我的评价网络请求
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class MusicCenterPresenter extends ColpencilPresenter<IMusicCenterView> {

    private IMusicCenterModel musicCenterModel;

    public MusicCenterPresenter() {
        musicCenterModel = new MusicCenterModel();
    }

    public void getContent(final int pageNo, int pageSize) {
        musicCenterModel.loadData(pageNo, pageSize);
        Observer<MusicResourseReturn> observer = new Observer<MusicResourseReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showError(null, null);
            }

            @Override
            public void onNext(MusicResourseReturn musicResourseReturn) {
                if (musicResourseReturn.getCode().equals("0")) {//信息获取成功
                    if (pageNo == 1) {
                        mView.refresh(musicResourseReturn.getResult());
                    } else {
                        mView.loadMore(musicResourseReturn.getResult());
                    }
                }

            }
        };
        musicCenterModel.sub(observer);
    }

}

