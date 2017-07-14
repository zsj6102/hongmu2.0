package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.SignInResult;
import com.colpencil.redwood.model.SignInModel;
import com.colpencil.redwood.model.imples.ISignInModel;
import com.colpencil.redwood.view.impl.ISignInView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:签到界面的presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class SignInPresenter extends ColpencilPresenter<ISignInView> {

    private ISignInModel model;

    public SignInPresenter() {
        model = new SignInModel();
    }

    public void signinserver(String userid, String token) {

        model.signinServer(userid, token);
        Observer<SignInResult> observer = new Observer<SignInResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SignInResult result) {
                mView.signInSuccess(result);
            }
        };
        model.subsign(observer);
    }

    public void loadState() {
        model.loadState();
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.loadstate(result);
            }
        };
        model.subState(observer);
    }
}
