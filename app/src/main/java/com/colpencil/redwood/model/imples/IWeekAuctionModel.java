package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.ListResult;

import rx.Observer;

public interface IWeekAuctionModel {

    void loadTag();
    void sub(Observer<ListResult<CategoryVo>> observer);

    /**
     * 周拍
     */
    void loadWeekShoot();

    void subWeekShoot(Observer<ListResult<CategoryVo>> observer);
}
