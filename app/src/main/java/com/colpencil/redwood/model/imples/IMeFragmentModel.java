package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;

import rx.Observer;
import rx.Subscriber;

/**
 * 描述：MeFragment
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 11 19
 */
public interface IMeFragmentModel extends ColpencilModel{

    void loadUserInfor();//加载用户个人信息

    void loadGoodInfor(int tagid, int page, int pageSize);//加载商品信息

    void subUserInfor(Observer<LoginBean> subscriber);

    void subGoodInfor(Observer<ListResult<HomeGoodInfo>> observer);

    void getPhoneNum();//获取客服电话

    //注册观察者
    void subResult(Observer<ResultCodeInt> subscriber);
    //获取申请状态
    void applyStatus(  HashMap<String,String> params);
    void subStauts(Subscriber<ApplyStatusReturn> subscriber);
}
