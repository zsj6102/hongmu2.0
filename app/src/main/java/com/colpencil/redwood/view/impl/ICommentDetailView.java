package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.NodeReplyItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.colpencil.redwood.bean.result.PostsResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:评论详情的view接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICommentDetailView extends ColpencilBaseView {
    void getNoteList(ResultInfo<List<NodeReplyItem>> result);
    /**
     * 获取帖子详情
     *
     * @param result
     */
    void loadPosts(PostsResult result);

    /**
     * 获取帖子列表
     *
     * @param result
     */
    void loadComment(PCommentResult result);

    /**
     * 提交评论结果
     */
    void submitResult(EntityResult<String> result);

    /**
     * 收藏帖子
     */
    void keepResult(EntityResult<String> result);

    /**
     * 点赞
     */
    void likeResult(EntityResult<String> result);

    /**
     * 帖子状态
     */
    void stateResult(EntityResult<String> result);

    /**
     * 获取分享链接
     *
     * @param result
     */
    void shareResult(CommonResult result);

    /**
     * 获取点赞状态
     */
    void likeState(EntityResult<String> result);

    void addLike(ResultInfo<String> resultInfo);

}
