package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.PayForReturn;
import com.colpencil.redwood.bean.PayKeyRetrun;
import com.colpencil.redwood.bean.result.MemberCouponResult;
import com.colpencil.redwood.bean.result.OrderPayInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;

/**
 * @author 曾 凤
 * @Description: 我的积分
 * @Email 20000263@qq.com
 * @date 2016/8/3
 */
public interface IPaymentModel extends ColpencilModel {

    //获取数据
    void loadPayFor(String cartIds);

    //获取数据
    void loadOtherPayFor(HashMap<String, String> map);

    //注册观察者
    void sub(Observer<PayForReturn> subscriber);

    //获取数据
    void payInfor(HashMap<String, String> map);


    //注册观察者
    void subPay(Observer<PayKeyRetrun> subscriber);

    void loadCoupon(String cartIds);

    void subCoupon(Observer<MemberCouponResult> observer);


}
