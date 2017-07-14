package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CommentVo;
import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:圈子的子界面View接口
 * @Email DramaScript@outlook.com
 * @date 2016/8/2
 */
public interface ICircleLeftItemView extends ColpencilBaseView {
    /**
     * 刷新
     *
     * @param result
     */
    void refresh(List<CommentVo> result);

    /**
     * 加载更多
     */
    void loadMore(List<CommentVo> result);

    /**
     *
     */
    void loadError(String msg);

    /**
     * 提交评论结果
     *
     * @param commonResult
     */
    void submitResult(EntityResult<String> commonResult);

    /**
     * 加载缓存的数据
     */
    void loadLocal(List<CommentVo> result);

    /**
     * 点赞结果
     */
    void likeResult(EntityResult<String> result);

}
