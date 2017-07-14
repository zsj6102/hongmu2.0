package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.model.HotGoodModel;
import com.colpencil.redwood.model.imples.IHotGoodModel;
import com.colpencil.redwood.view.impl.IHotGoodView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 描述：MeFragment 网络请求
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 11 18
 */
public class HotGoodPresenter extends ColpencilPresenter<IHotGoodView> {

    private IHotGoodModel hotGoodModel;

    public HotGoodPresenter() {
        hotGoodModel = new HotGoodModel();
    }


    /**
     * 加载商品信息
     */
    public void loadGoodInfor(int page, int pageSize) {
        hotGoodModel.loadGoodInfor(page, pageSize);
        Observer<ListResult<HomeGoodInfo>> observer = new Observer<ListResult<HomeGoodInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<HomeGoodInfo> homeGoodResult) {
                if (homeGoodResult.getCode() == 0) {
                    mView.loadGoodInfor(homeGoodResult.getCG_Result());
                } else {
                    LoginBean loginBean = new LoginBean();
                    loginBean.setMsg(homeGoodResult.getMessage());
                    mView.fail(loginBean);
                }
            }
        };
        hotGoodModel.subGoodInfor(observer);
    }
}
