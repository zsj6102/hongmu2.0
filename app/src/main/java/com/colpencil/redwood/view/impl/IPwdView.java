package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.LoginBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * 描述：注册
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/22 13 40
 */
public interface IPwdView extends ColpencilBaseView {

    /**
     * 获取验证码
     *
     * @param bean
     */
    void codeResult(LoginBean bean);

    /**
     * 绑定
     *
     * @param bean
     */
    void bindResult(LoginBean bean);

    /**
     * 修改密码
     *
     * @param bean
     */
    void changeResult(LoginBean bean);

    /**
     * 忘记密码
     *
     * @param bean
     */
    void forgetResult(LoginBean bean);

    /**
     * 注册
     *
     * @param bean
     */
    void registerResult(LoginBean bean);

    /**
     * 登录结果
     *
     * @param bean
     */
    void loginResult(LoginBean bean);
}
