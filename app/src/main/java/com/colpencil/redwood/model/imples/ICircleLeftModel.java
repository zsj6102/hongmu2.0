package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.CommentResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:圈子的Model接口类
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICircleLeftModel extends ColpencilModel {

    /**
     * 获取标签分类
     */
    void loadTag();

    void sub(Observer<ListResult<CategoryVo>> observer);

    /**
     * 获取banner
     *
     * @param acid
     */
    void loadImage(String acid);

    void subImage(Observer<ListResult<BannerVo>> observer);

    /**
     * 获取我的分类
     */
    void loadMyTag();

    void subMyTag(Observer<ListResult<CategoryVo>> observer);

    /**
     * 获取帖子
     *
     * @param sec_id
     * @param pageNo
     * @param pageSize
     */
    void loadComment(String sec_id, int pageNo, int pageSize);

    void subComment(Observer<CommentResult> observer);

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
}
