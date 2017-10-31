package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 陈宝
 * @Description:百科详情
 * @Email DramaScript@outlook.com
 * @date 2016/8/6
 */
public interface ICycloDetailView extends ColpencilBaseView {

    /**
     * 百科详情的url
     *
     * @param url
     */
    void loadUrl(String url);

    /**
     * 百科详情的评论
     */
    void loadComments(PCommentResult result);

    /**
     * 提交评论结果
     */
    void submitResult(EntityResult<String> result);

    /**
     * 查看百科收藏状态
     */
    void checkStateResult(EntityResult<String> result);

    /**
     * 收藏百科结果
     */
    void keepResult(EntityResult<String> result);

    /**
     * 获取分享链接
     *
     * @param result
     */
    void shareResult(CommonResult result);

    /**
     * 点赞
     */
    void likeResult(EntityResult<String> result);

    /**
     * 点赞状态
     *
     * @param result
     */
    void likeStateResult(EntityResult<String> result);

    void addLike(ResultInfo<String> resultInfo);
}
