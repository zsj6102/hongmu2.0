package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.Goods_list;
import com.colpencil.redwood.bean.Goods_list_item;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.bean.result.CareReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IHomeGoodsPageView extends ColpencilBaseView {
    void loadError(String message);
    void loadAdv(AdResult result);

    void loadFans(ResultInfo<List<ItemStoreFans>> resultInfo);

    void loadZc(ResultInfo<List<AllSpecialInfo>> resultInfo);

    void loadGoods(ResultInfo<List<Goods_list>> resultInfo);

    void refresh(ResultInfo<List<Goods_list_item>> resultInfo);

    void loadMore(ResultInfo<List<Goods_list_item>> resultInfo);


    void operate(CareReturn result);
}
