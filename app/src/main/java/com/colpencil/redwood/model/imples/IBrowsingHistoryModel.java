package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.bean.result.CommonResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IBrowsingHistoryModel extends ColpencilModel {

    //删除数据
    void delet(int type, int foot_type);

    //注册观察者
    void subResult(Observer<Result> subscriber);

    /**
     * 分享
     *
     * @param ote_id
     */
    void share(int ote_id);

    void subShare(Observer<CommonResult> observer);

    /**
     * 记录分享
     *
     * @param ote_id
     */
    void recordShare(int ote_id, String platform, int type);

    void subRecord(Observer<EntityResult<String>> observer);

    void shareBaike(int ote_id,String article_id);

    void subShareBike(Observer<CommonResult> observer);

    void shareGoods( String goods_id);

    void subShareGoods(Observer<CommonResult> observer);
}