package com.colpencil.redwood.present.login;

import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeRecommend;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.model.SplashModel;
import com.colpencil.redwood.model.imples.ISplashModel;
import com.colpencil.redwood.view.impl.ISplashView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observer;

/**
 * @author 陈 宝
 * @Description:启动页
 * @Email 1041121352@qq.com
 * @date 2016/9/28
 */
public class SplashPresenter extends ColpencilPresenter<ISplashView> {

    private ISplashModel model;

    public SplashPresenter() {
        model = new SplashModel();
    }

    public void login() {
        model.login();
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginBean loginBean) {
                if (loginBean.getCode() == 1) {
                    SharedPreferencesUtil.getInstance(App.getInstance()).setInt("member_id", loginBean.getMember_id());
                    SharedPreferencesUtil.getInstance(App.getInstance()).setString("token", loginBean.getToken());
                    SharedPreferencesUtil.getInstance(App.getInstance()).setBoolean(StringConfig.ISLOGIN, true);
                } else {
                    SharedPreferencesUtil.getInstance(App.getInstance()).setBoolean(StringConfig.ISLOGIN, false);
                }
            }
        };
        model.subLogin(observer);
    }

    public void loadRecommend() {
        model.loadHomeRecommend();
        Observer<EntityResult<HomeRecommend>> observer = new Observer<EntityResult<HomeRecommend>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<HomeRecommend> result) {
                mView.loadHomeConfig(result);
            }
        };
        model.subHomeRecommend(observer);
    }

}
