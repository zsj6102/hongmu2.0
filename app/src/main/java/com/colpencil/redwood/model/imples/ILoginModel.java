package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.LoginBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/22 11 13
 */
public interface ILoginModel extends ColpencilModel{

    void sumbitLogin(String mobile, String password);

    void sumbitThird(String openId);

    void sub(Observer<LoginBean> subscriber);
}
