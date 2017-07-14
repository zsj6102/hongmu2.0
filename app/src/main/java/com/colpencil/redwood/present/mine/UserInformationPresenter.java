package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.model.UserInformationModel;
import com.colpencil.redwood.model.imples.IUserInformationModel;
import com.colpencil.redwood.view.impl.UserInformationView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.io.File;

import rx.Observer;

/**
 * 描述：个人信息数据请求
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/26 09 20
 */
public class UserInformationPresenter extends ColpencilPresenter<UserInformationView> {

    private IUserInformationModel userInformationModel;

    public UserInformationPresenter() {
        userInformationModel = new UserInformationModel();
    }

    /**
     * 获取用户个人资料
     */
    public void loadUserInfor() {
        userInformationModel.loadUserInfor();
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(LoginBean loginBean) {
                Log.e("返回值", "个人中心：" + loginBean.toString());
                if (loginBean.getCode() == 1) {//信息获取成功
                    mView.loadUserInfor(loginBean);
                } else {
                    mView.fail(loginBean);
                }
            }
        };
        userInformationModel.sub(observer);
    }

    /**
     * 修改头像
     */
    public void updateImg(File faceFile) {
        userInformationModel.updateUserImg(faceFile);
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(LoginBean loginBean) {
                if (loginBean.getCode() == 1) {//信息获取成功
                    mView.updateSuccess(loginBean);
                } else {
                    mView.fail(loginBean);
                }
            }
        };
        userInformationModel.sub(observer);

    }

    /**
     * 用户修改个人信息
     */
    public void updateUserInfor(String modifyContent, int operationType) {
        userInformationModel.updateUserInfor(modifyContent, operationType);
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(LoginBean loginBean) {
                if (loginBean.getCode() == 1) {//信息获取成功
                    mView.updateSuccess(loginBean);
                } else {
                    mView.fail(loginBean);
                }
            }
        };
        userInformationModel.sub(observer);
    }
}
