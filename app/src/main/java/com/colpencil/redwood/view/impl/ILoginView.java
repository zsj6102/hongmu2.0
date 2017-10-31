package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.LoginBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/22 11 09
 */
public interface ILoginView extends ColpencilBaseView{
    /**
     * 登录成功
     */
    void loginSuccess(LoginBean loginBean);
    /**
     * 第三方登录验证成功
     */
    void thirstSuccess(LoginBean loginBean);
//    /**
//     * 登录失败
//     */
    void  loginFail(String msg);
}
