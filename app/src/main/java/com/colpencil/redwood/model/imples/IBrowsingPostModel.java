package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.BrowsingPostDate;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：帖子预览
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IBrowsingPostModel extends ColpencilModel {

    //获取数据
    void loadData(long pageNo, int pageSize);

    //注册观察者
    void sub(Observer<ListResult<BrowsingPostDate>> subscriber);

    //删除单个选项
    void delete(int foot_id);

    void subDelete(Observer<EntityResult<String>> observer);
}