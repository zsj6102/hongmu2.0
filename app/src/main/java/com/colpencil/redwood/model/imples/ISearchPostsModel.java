package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommentResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:搜索帖子的Model接口
 * @Email DramaScript@outlook.com
 * @date 2016/8/4
 */
public interface ISearchPostsModel extends ColpencilModel {

    void loadPosts(String keyword, int page, int pageSize);

    void sub(Observer<CommentResult> observer);

    /**
     * 发表评论
     *
     * @param comContent
     * @param ote_id
     */
    void submitComments(String comContent, int ote_id);

    void subSubmit(Observer<EntityResult<String>> observer);

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
