package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.CommonResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 浏览记录
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/11
 */
public interface IBrowsingHistoryView extends ColpencilBaseView{

    void resultInfor(String code, String msg);

    void shareResult(CommonResult result);
}
