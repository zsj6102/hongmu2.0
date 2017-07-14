package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.Massage;
import com.colpencil.redwood.bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：我的消息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface IMessageView extends ColpencilBaseView {

    void resultInfor(String code, String msg);

    /**
     * 加载
     *
     * @param data
     */

    void loadData(List<Massage> data);

    /**
     * 删除
     *
     * @param result
     */
    void delete(Result result);
}
