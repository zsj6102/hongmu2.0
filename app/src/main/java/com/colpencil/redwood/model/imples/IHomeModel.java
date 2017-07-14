package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.HomeRecommend;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.CyclopediaResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:首页的model接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IHomeModel extends ColpencilModel {

    /**
     * 获取标签
     *
     * @param cat_type
     */
    void loadTag(int cat_type);

    void sub(Observer<ListResult<CategoryVo>> observer);

    /**
     * 获取我的标签
     */
    void loadMyTag();

    void subMyTag(Observer<ListResult<CategoryVo>> observer);

    /**
     * 获取首页推荐模块
     */
    void loadHomeRecommend();

    void subHomeRecommend(Observer<EntityResult<HomeRecommend>> observer);

    /**
     * 获取首页商品
     */
    void loadGoods(String tagId, int page, int pageSize);

    void subGood(Observer<ListResult<HomeGoodInfo>> observer);

    /**
     * 获取百科列表
     *
     * @param cat_id
     * @param child_cat_id
     * @param page
     * @param pageSize
     * @param iscommend
     */
    void loadTagItem(String cat_id, String child_cat_id, int page, int pageSize, String iscommend);

    void subTagItem(Observer<CyclopediaResult> cyclolist);

    /**
     * 搜索百科
     *
     * @param cat_id
     * @param keyword
     * @param page
     * @param pageSize
     */
    void search(int cat_id, String keyword, int page, int pageSize);

    void subSearch(Observer<CyclopediaResult> observer);
}
