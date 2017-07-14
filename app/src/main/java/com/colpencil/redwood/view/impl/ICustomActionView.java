package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：定制相关信息填写
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/8 11 23
 */
public interface ICustomActionView extends ColpencilBaseView {
    /**
     * 加载商品信息回调
     */
    void loadData(List<BannerVo> bannerVos);

    /**
     * 提交定制信息回调
     */
    void sumbitCustomResult(EntityResult<String> result);

    /**
     * 获取banner失败
     */
    void loadBannerError();
}
