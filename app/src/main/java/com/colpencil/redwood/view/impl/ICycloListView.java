package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MyCyclopediaInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:百科列表的view
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICycloListView extends ColpencilBaseView {

    /**
     * 百科列表
     *
     * @param list
     */
    void refresh(List<MyCyclopediaInfo> list);

    void loadMore(List<MyCyclopediaInfo> list);

    void loadError(String s);

    /**
     * 删除结果
     *
     * @param result
     */
    void delete(EntityResult<String> result);

    /**
     * 取消审核
     *
     * @param result
     */
    void cancle(EntityResult<String> result);
}
