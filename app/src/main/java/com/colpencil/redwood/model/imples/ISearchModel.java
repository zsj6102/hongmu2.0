package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.HotResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @Description:搜索的Model接口
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ISearchModel extends ColpencilModel{

    void loadHot(int cat_id);
    void subHot(Observer<HotResult> observer);
}
