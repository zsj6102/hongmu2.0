package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CommentVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:搜索帖子
 * @Email DramaScript@outlook.com
 * @date 2016/8/5
 */
public interface ISearchPostsView extends ColpencilBaseView {
    /**
     * 搜索帖子
     *
     * @param commentVos
     */
    void refresh(List<CommentVo> commentVos);

    void loadMore(List<CommentVo> commentVos);

    void loadError();

    /**
     * 提交评论结果
     *
     * @param commonResult
     */
    void submitResult(EntityResult<String> commonResult);

    void likeResult(EntityResult<String> result);

    /**
     * 分享链接
     *
     * @param result
     */
    void shareResult(CommonResult result);
}
