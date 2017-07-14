package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.ResultCodeInt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;

import rx.Observer;

/**
 * @author 曾 凤
 * @Description: 退款
 * @Email 20000263@qq.com
 * @date 2016/8/9
 */
public interface IRefundMoneyModel extends ColpencilModel {


    void loadReason();

    /**
     * 提交退款申请
     */

    void sumbit(HashMap<String, String> params);

    //注册观察者
    void subResult(Observer<ResultCodeInt> subscriber);

}