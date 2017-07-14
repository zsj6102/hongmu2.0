package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.LoginBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/22 13 42
 */
public interface IPwdModel extends ColpencilModel {

    /**
     * 绑定手机号
     *
     * @param mobile
     * @param code
     * @param password
     * @param openId
     */
    void bindNum(String mobile, String code, String password, String openId);

    void subBind(Observer<LoginBean> subscriber);

    /**
     * 忘记密码
     *
     * @param mobile
     * @param code
     * @param password
     */
    void forgetPwd(String mobile, String code, String password);

    void subForgetPwd(Observer<LoginBean> subscriber);

    /**
     * 修改密码
     *
     * @param mobile
     * @param code
     * @param password
     */
    void changePwd(String mobile, String code, String password);

    void subChangePwd(Observer<LoginBean> subscriber);

    /**
     * 忘记密码的获取验证码
     *
     * @param mobile
     */
    void getCodeForget(String mobile);

    void subCodeForget(Observer<LoginBean> subscriber);

    /**
     * 修改密码的获取验证码
     *
     * @param mobile
     */
    void getCodeChange(String mobile);

    void subCodeChange(Observer<LoginBean> subscriber);

    /**
     * 绑定手机号的获取验证码
     * @param mobile
     */
    void getCodeBind(String mobile);

    void subCodeBind(Observer<LoginBean> subscriber);

    /**
     * 登录
     *
     * @param mobile
     * @param password
     */
    void login(String mobile, String password);

    void subLogin(Observer<LoginBean> subscriber);

}
