package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.BrowsingPostDate;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * 描述：帖子预览
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 13 39
 */
public interface IBrowsingPostView extends ColpencilBaseView {
    /**
     * 刷新
     *
     * @param data
     */
    void refresh(List<BrowsingPostDate> data);

    /**
     * 加载
     *
     * @param data
     */

    void loadMore(List<BrowsingPostDate> data);

    void fail(String msg);

    /**
     * 删除结果
     * @param result
     */
    void delete(EntityResult<String> result);
}
