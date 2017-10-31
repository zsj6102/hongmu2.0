package com.colpencil.redwood.model.imples;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.WeekAuctionList;
import com.colpencil.redwood.bean.result.WeekAuctionListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;

import rx.Observer;

/**
 * 描述： 周拍fragment
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 10 48
 */
public interface IWeekShootModel extends ColpencilModel {

    //获取数据
    void loadData(String type_id, int pageNo, int pageSize);

    //注册观察者
    void sub(Observer<WeekAuctionListResult> subscriber);

    void loadSearch(String keyword,int pageNo,int pageSize);

    void subSearch(Observer<WeekAuctionListResult> subscriber);
}
