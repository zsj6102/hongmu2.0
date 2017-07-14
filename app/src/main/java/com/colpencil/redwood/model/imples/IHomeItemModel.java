package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.FuncPointVo;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;

import rx.Observer;

/**
 * @Description:首页-推荐的Model接口
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IHomeItemModel {

    void loadBanner(String type);
    void subBanner(Observer<ListResult<BannerVo>> observer);
    void loadGoodFirst(String tagid, int page, int pageSize);
    void subFirst(Observer<ListResult<HomeGoodInfo>> observer);
    void loadGoodSecond(String tagid, int page, int pageSize);
    void subSecond(Observer<ListResult<HomeGoodInfo>> observer);
    void loadGoodThree(String tagid, int page, int pageSize);
    void subThree(Observer<ListResult<HomeGoodInfo>> observer);
    void loadFuncList();
    void subFunc(Observer<ListResult<FuncPointVo>> observer);

}
