package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.HomeRecommend;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:首页home的view接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IHomePageView extends ColpencilBaseView {

    /**
     * 获取首页的分类
     *
     * @param taglist
     */
    void loadTag(List<CategoryVo> taglist);

    void loadError(String msg);

    /**
     * 获取今日推荐
     *
     * @param result
     */
    void loadTodaysRecommend(EntityResult<HomeRecommend> result);

    /**
     * 刷新
     *
     * @param result
     */
    void refresh(List<HomeGoodInfo> result);

    /**
     * 加载更多
     */
    void loadMore(List<HomeGoodInfo> result);
}
