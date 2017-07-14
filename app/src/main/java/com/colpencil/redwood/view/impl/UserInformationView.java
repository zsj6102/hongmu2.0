package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.LoginBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/26 09 08
 */
public interface UserInformationView extends ColpencilBaseView{
    /**
     * 加載用戶信息
     */
    void loadUserInfor(LoginBean loginBean);

    void updateSuccess(LoginBean loginBean);

    void fail(LoginBean loginBean);
}
