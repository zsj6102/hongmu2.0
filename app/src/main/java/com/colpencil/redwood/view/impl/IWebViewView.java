package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 曾 凤
 * @Description: H5请求
 * @Email 20000263@qq.com
 * @date 2016/8/16
 */
public interface IWebViewView extends ColpencilBaseView {
    /**
     * 请求失败
     *
     * @param code
     * @param msg
     */
    void fail(String code, String msg);

    /**
     * 成功获取H5链接
     *
     * @param url
     */
    void webUrl(String url);

    /**
     * 客服
     *
     * @param result
     */
    void serviceResult(EntityResult<String> result);
}
