package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.WeekPersonList;
import com.colpencil.redwood.bean.result.BidderResult;
import com.colpencil.redwood.bean.result.WeekShootDetailResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：商品详情
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/11 11 52
 */
public interface IProductdetailsModle extends ColpencilModel {
    /**
     * 加载商品头布局数据
     */
    void loadHearderData(int id);

    /**
     * 加载底布局数据
     */
//    void loadBottomData(int pageNo, int pageSize);

    void sub(Observer<WeekShootDetailResult> subscriber);

    /**
     * 获取竞拍人列表
     *
     * @param id
     */
    void loadPersonList(int id);

    void subPerson(Observer<ListResult<WeekPersonList>> observer);

    /**
     * 获取前三名竞拍人
     *
     * @param id
     */
    void loadBidders(int id);

    void subBidders(Observer<BidderResult> observer);

    /**
     * 我要出价
     */
    void submitBid(String price, int id);

    void subBid(Observer<EntityResult<String>> observer);

    /**
     * 获取url
     *
     * @param id
     */
    void loadUrl(int id);

    void subUrl(Observer<EntityResult<String>> observer);
}
