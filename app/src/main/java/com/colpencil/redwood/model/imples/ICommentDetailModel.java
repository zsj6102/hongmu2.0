package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.NodeReplyItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.colpencil.redwood.bean.result.PostsResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:评论的model接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICommentDetailModel extends ColpencilModel {

    /**
     * 获取帖子详情
     *
     * @param ote_id
     */
    void loadPosts(String ote_id);

    void subPosts(Observer<PostsResult> observer);

    /**
     * 获取帖子评论
     *
     * @param commentid
     * @param page
     * @param pageSize
     */
    void loadComments(String commentid, int page, int pageSize);

    void subComments(Observer<PCommentResult> observer);

    /**
     * 发表评论
     *
     * @param comContent
     * @param ote_id
     */
    void submitComments(String comContent, int ote_id);

    void subSubmit(Observer<EntityResult<String>> observer);

    /**
     * 收藏帖子
     *
     * @param ote_id
     */
    void keepComments(int ote_id);

    void subKeep(Observer<EntityResult<String>> observer);

    /**
     * 收藏
     *
     * @param ote_id
     */
    void likeComments(int ote_id);

    void subLike(Observer<EntityResult<String>> observer);

    /**
     * 获取帖子点赞收藏状态
     */
    void loadKeepState(int ote_id);

    void subLoad(Observer<EntityResult<String>> observer);

    /**
     * 添加浏览记录
     */
    void browerComment(int ote_id);

    void subBrower(Observer<CommonResult> observer);

    /**
     * 获取分享内容
     *
     * @param ote_id
     */
    void loadShare(int ote_id);

    void subShare(Observer<CommonResult> observer);

    /**
     * 记录分享
     *
     * @param type
     * @param platform
     * @param id
     */
    void addUpShare(int type, String platform, int id);

    void subAddup(Observer<EntityResult<String>> observer);

    /**
     * 点赞的状态
     *
     * @param ote_id
     */
    void loadLikeState(int ote_id);

    void subLikeState(Observer<EntityResult<String>> observer);


    void addLike(Map<String,String> map);
    void subLikeCom(Observer<ResultInfo<String>> observer);

    void getNodeReply(Map<String,String>map);

    void subReply(Observer<ResultInfo<List<NodeReplyItem>>> observer);
}
