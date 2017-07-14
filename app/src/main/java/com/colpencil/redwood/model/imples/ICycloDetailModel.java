package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CyclopediaContent;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:百科详情的model接口
 * @Email DramaScript@outlook.com
 * @date 2016/8/6
 */
public interface ICycloDetailModel extends ColpencilModel {

    /**
     * 获取详情的内容url
     */
    void loadUrl(String cat_id, String article_id);

    void subUrl(Observer<AnnounceResult> observer);

    /**
     * 获取评论
     */
    void loadComments(String ote_id, int page, int pageSize, String type);

    void subComments(Observer<PCommentResult> observer);

    /**
     * 提交评论
     */
    void submitComments(String article_id, String acomment, String type);

    void subSubmit(Observer<EntityResult<String>> observer);

    /**
     * 查看百科状态
     */
    void checkState(String article_id, int type);

    void subCheck(Observer<EntityResult<String>> observer);

    /**
     * 收藏百科
     */
    void keepCyclopedia(String article_id, int type);

    void subKeep(Observer<EntityResult<String>> observer);

    /**
     * 添加浏览记录
     */
    void browerComment(int catid, String articleid);

    void subBrower(Observer<EntityResult<String>> observer);

    /**
     * 获取分享内容
     *
     * @param cat_id
     * @param article_id
     */
    void loadShare(int cat_id, String article_id);

    void subShare(Observer<EntityResult<String>> observer);

    /**
     * 记录分享
     *
     * @param type
     * @param platform
     * @param id
     */
    void addUpShare(int type, String platform, String id);

    void subAddup(Observer<EntityResult<String>> observer);

    /**
     * 点赞
     *
     * @param type
     * @param id
     */
    void like(int type, String id);

    void subLike(Observer<EntityResult<String>> observer);

    /**
     * 点赞状态
     *
     * @param type
     * @param id
     */
    void likeState(int type, String id);

    void subLikeState(Observer<EntityResult<String>> observer);

    void loadContent(String id);

    void subContent(Observer<EntityResult<CyclopediaContent>> observer);
}
