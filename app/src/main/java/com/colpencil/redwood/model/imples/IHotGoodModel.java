package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：MeFragment
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 11 19
 */
public interface IHotGoodModel extends ColpencilModel{


    void loadGoodInfor(int page, int pageSize);//加载商品信息

    void subGoodInfor(Observer<ListResult<HomeGoodInfo>> observer);
}
