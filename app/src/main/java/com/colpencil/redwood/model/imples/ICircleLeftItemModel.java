package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommentResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.MyCommentResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface ICircleLeftItemModel extends ColpencilModel {

    /**
     * 获取帖子
     *
     * @param sec_id
     * @param pageNo
     * @param pageSize
     */
    void loadComment(String sec_id, int pageNo, int pageSize);

    void sub(Observer<CommentResult> observer);

    /**
     * 获取我的帖子
     *
     * @param type
     * @param pageNo
     * @param pageSize
     */
    void loadMyComment(String type, int pageNo, int pageSize);

    void subMyComment(Observer<MyCommentResult> observer);

    /**
     * 发表评论
     *
     * @param comContent
     * @param ote_id
     */
    void submitComments(String comContent, int ote_id);

    void subSubmit(Observer<EntityResult<String>> observer);

    /**
     * 删除帖子
     */
    void deleteComments(String ote_id);

    void subDelete(Observer<CommonResult> observer);

    /**
     * 点赞
     *
     * @param ote_id
     */
    void like(int ote_id);

    void subLike(Observer<EntityResult<String>> observer);

    /**
     * 分享
     *
     * @param ote_id
     */
    void share(int ote_id);

    void subShare(Observer<CommonResult> observer);

    /**
     * 记录分享
     *
     * @param ote_id
     */
    void recordShare(int ote_id, String platform);

    void subRecord(Observer<EntityResult<String>> observer);
}
