package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.LoginBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.io.File;

import rx.Observer;

/**
 * 描述：个人信息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/26 09 21
 */
public interface IUserInformationModel extends ColpencilModel {

    void loadUserInfor();

    /**
     * 修改用户头像
     */
    void updateUserImg(File faceFile);

    /**
     *修改个人信息
     */
    void updateUserInfor(String modifyContent, int operationType);


    void sub(Observer<LoginBean> subscriber);
}
