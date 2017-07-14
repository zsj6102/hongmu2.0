package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.LoginBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：MeFragment
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 11 08
 */
public interface IMeFragmentView extends ColpencilBaseView{
    /**
     * 加载用户个人信息
     */
    void loadUserInfor(LoginBean loginBean);
    /**
     * 加载商品信息
     */
    void  loadGoodInfor(List<HomeGoodInfo> goodInfos);

    void callPhone(String phone);

    void fail(LoginBean loginBean);
}
