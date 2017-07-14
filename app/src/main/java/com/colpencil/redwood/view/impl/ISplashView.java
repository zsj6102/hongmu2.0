package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeRecommend;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 陈 宝
 * @Description:启动页
 * @Email 1041121352@qq.com
 * @date 2016/11/24
 */
public interface ISplashView extends ColpencilBaseView {

    void loadHomeConfig(EntityResult<HomeRecommend> result);

}
