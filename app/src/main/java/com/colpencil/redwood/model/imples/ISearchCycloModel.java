package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.CyclopediaResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @Description:搜索百科结果的model接口
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/8/4
 */
public interface ISearchCycloModel extends ColpencilModel{

    void loadCycloList(int cat_id, String keyword, int page, int pageSize);
    void sub(Observer<CyclopediaResult> observer);

}
