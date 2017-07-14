package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.SignInResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 陈宝
 * @Description:签到的view接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ISignInView extends ColpencilBaseView {

    /**
     * 签到
     *
     * @param result
     */
    void signInSuccess(SignInResult result);

    /**
     * 获取签到状态
     *
     * @param result
     */
    void loadstate(EntityResult<String> result);
}
