package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.CommentResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:圈子的View接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICircleLeftView extends ColpencilBaseView {

    /**
     * 圈子的分类
     *
     * @param result
     */
    void loadTag(ListResult<CategoryVo> result);

    /**
     * 圈子头部的banner
     *
     * @param result
     */
    void loadBanner(ListResult<BannerVo> result);

    /**
     * 加载本地
     *
     * @param categoryVos
     */
    void loadLocal(List<CategoryVo> categoryVos);

    /**
     * 帖子列表
     *
     * @param result
     */
    void loadPost(CommentResult result);

    void operate(EntityResult<String> result, int type);
}
