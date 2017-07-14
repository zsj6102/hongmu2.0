package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.SortOptionsReturn;
import com.colpencil.redwood.bean.result.HomeGoodResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：MeFragment
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 11 19
 */
public interface ICommoditySearchModel extends ColpencilModel {

    void loadSortInfor(int cat_id);//加载商品信息

    void subSortInfor(Observer<SortOptionsReturn> observer);

    /**
     * 获取商品信息
     */
    void loadGoodData(int cat_id, int sort_type, int page, int pageSize);

    /**
     * 获取商品信息
     */
    void loadGoodData(int cat_id, String sorts, String price_range, int page, int pageSize);

    void subGood(Observer<HomeGoodResult> observer);

    /**
     * 搜索商品
     *
     * @param cat_id
     * @param keyword
     * @param page
     * @param pageSize
     */
    void findGoodByKeywood(int cat_id, String keyword, int page, int pageSize);

    void subGoodByKeywood(Observer<ListResult<HomeGoodInfo>> observable);
}
