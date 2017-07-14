package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.CustomGoodResult;
import com.colpencil.redwood.bean.result.CustomResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/7 17 16
 */
public interface ICustomView extends ColpencilBaseView {
    /**
     * @param result
     */
    void loadUrl(CustomResult result);

    void loadGoods(CustomGoodResult result);
}
