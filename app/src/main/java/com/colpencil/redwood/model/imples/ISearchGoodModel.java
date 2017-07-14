package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:搜索商品结果的model接口
 * @Email DramaScript@outlook.com
 * @date 2016/8/4
 */
public interface ISearchGoodModel extends ColpencilModel {

    void loadGoodList(String keyword, int page, int pageSize);

    void sub(Observer<ListResult<HomeGoodInfo>> observer);

}
