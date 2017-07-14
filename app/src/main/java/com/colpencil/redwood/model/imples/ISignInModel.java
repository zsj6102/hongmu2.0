package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.SignInResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:签到的Model接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ISignInModel extends ColpencilModel {

    void signinServer(String userid, String token);

    void subsign(Observer<SignInResult> observer);

    /**
     * 获取签到状态
     */
    void loadState();

    void subState(Observer<EntityResult<String>> observer);
}
