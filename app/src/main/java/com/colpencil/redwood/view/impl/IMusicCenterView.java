package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.Music;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface IMusicCenterView extends ColpencilBaseView{
    /**
     * 刷新
     * @param data
     */
    void refresh(List<Music> data);

    /**
     * 加载
     * @param data
     */

    void loadMore(List<Music> data);

    void fail(String code, String msg);
}
