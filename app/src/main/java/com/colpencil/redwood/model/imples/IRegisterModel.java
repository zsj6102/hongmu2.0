package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.LoginBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈 宝
 * @Description:注册
 * @Email 1041121352@qq.com
 * @date 2016/11/5
 */
public interface IRegisterModel extends ColpencilModel {

    /**
     * 注册
     *
     * @param mobile
     * @param code
     * @param password
     */
    void register(String mobile, String code, String password);

    void subRegister(Observer<LoginBean> subscriber);

    /**
     * 获取验证码
     *
     * @param mobile
     */
    void getCheckNum(String mobile);

    void subCheckNum(Observer<LoginBean> subscriber);

    /**
     * 登录
     *
     * @param mobile
     * @param password
     */
    void login(String mobile, String password);

    void subLogin(Observer<LoginBean> subscriber);
}
