package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CyclopediaItem;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：商品
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface ICyclopediaFragmentView extends ColpencilBaseView {
    /**
     * 加载
     *
     * @param data
     */
    void refresh(List<CyclopediaItem> data);


    void resultInfor(String code, String msg);

    /**
     * 删除结果
     *
     * @param result
     */
    void deleteResult(EntityResult<String> result);

    void loadMore(List<CyclopediaItem> data);

    /**
     * 清除结果
     * @param result
     */
    void removeAll(EntityResult<String> result);
}
