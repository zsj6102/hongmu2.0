package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.result.CyclopediaResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description: 首页-百科的Model层的接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/7
 */

public interface ICyclopediaItemModel extends ColpencilModel {

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

    void subSearch(Observer<ListResult<CyclopediaInfoVo>> observer);
}
