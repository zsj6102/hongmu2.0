package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MyComment;
import com.colpencil.redwood.bean.result.CommonResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:藏友圈-我的子界面接口
 * @Email DramaScript@outlook.com
 * @date 2016/8/2
 */
public interface ICircleRightItemView extends ColpencilBaseView {
    /**
     * 我的评论列表
     */
    void refresh(List<MyComment> commentVos);

    /**
     * 加载更多
     */
    void loadMore(List<MyComment> commentVos);

    /**
     * 加载错误
     */
    void loadError(String msg);

    /**
     * 提交评论结果
     *
     * @param result
     */
    void submitResult(EntityResult<String> result);

    /**
     * 删除帖子结果
     */
    void deleteResult(CommonResult result);

    /**
     * 从缓存中获取数据
     */
    void loadLocal(List<MyComment> result);

    /**
     * 收藏
     *
     * @param result
     */
    void likeResult(EntityResult<String> result);

}
