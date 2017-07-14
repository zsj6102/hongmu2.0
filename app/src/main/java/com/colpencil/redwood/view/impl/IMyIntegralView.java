package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.Integral;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 我的积分
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/3
 */
public interface IMyIntegralView extends ColpencilBaseView{

    void loadIntegralData(List<Integral> data, int counts);

    void fail(String code, String msg);

}
