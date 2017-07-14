package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.PostCollectionReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：商品
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 03
 */
public interface IPostFragmentModel extends ColpencilModel {

    //获取数据
    void loadData(int pageNo, int pageSize);

    //注册观察者
    void sub(Observer<PostCollectionReturn> subscriber);

    /**
     * 根据id，删除收藏记录
     */
    void removeById(int favorite_id);

    void subRemove(Observer<EntityResult<String>> subscriber);

    //获取数据
    void removeAll();

    //注册观察者
    void subRemoveAll(Observer<EntityResult<String>> subscriber);
}