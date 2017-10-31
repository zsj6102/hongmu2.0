package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.Goods_list;
import com.colpencil.redwood.bean.Goods_list_item;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.bean.result.CareReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;


public interface IHomeGoodsPageModel extends ColpencilModel {
    /**
     * 获取广告
     */
    void getAdv(String type);

    void subAdv(Observer<AdResult> observer);

    /**
     * 获取封面专场
     * @param map
     */
    void getCoverSpecial(Map<String,String> map);

    void subCover(Observer<ResultInfo<List<AllSpecialInfo>>> observer);

    /**
     * 获取热门关注列表
     */

    void getHotFans(Map<String,String> map);

    void subHotFans(Observer<ResultInfo<List<ItemStoreFans>>> observer);

    /**
     * 获取列表
     */
    void getContent(Map<String,String> map);

    void subContent(Observer<ResultInfo<List<Goods_list>>> observer);

    /**
     * 二级商品列表
     */

    void getSecondGoods(Map<String,String> map);

    void sbuSecond(Observer<ResultInfo<List<Goods_list_item>>> observer);

    void  storeCare(Map<String,String> params);

    void subUnstoreCare(Observer<CareReturn> observer);
}
