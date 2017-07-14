package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.bean.ListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface ICyclopediaModel extends ColpencilModel {

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
     * 获取百科列表
     *
     * @param cat_id
     * @param child_cat_id
     * @param page
     * @param pageSize
     * @param iscommend
     */
    void loadCyclopedia(String cat_id, String child_cat_id, int page, int pageSize, String iscommend);

    void subLoad(Observer<ListResult<CyclopediaInfoVo>> cyclolist);

    /**
     * 搜索百科
     *
     * @param cat_id
     * @param keyword
     * @param page
     * @param pageSize
     */
    void search(int cat_id, String keyword, int page, int pageSize);

    void subSearch(Observer<ListResult<CyclopediaInfoVo>> observer);

}
