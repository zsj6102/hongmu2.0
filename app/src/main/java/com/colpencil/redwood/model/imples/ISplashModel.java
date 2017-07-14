package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeRecommend;
import com.colpencil.redwood.bean.LoginBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface ISplashModel extends ColpencilModel {

    /**
     * 登录
     */
    void login();

    void subLogin(Observer<LoginBean> observer);

    /**
     * 获取首页推荐模块
     */
    void loadHomeRecommend();

    void subHomeRecommend(Observer<EntityResult<HomeRecommend>> observer);
}
