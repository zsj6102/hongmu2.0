package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CartItem;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.bean.ShoppingCartReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * 描述：购物车
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/14 14 14
 */
public interface IShoppingCartModel extends ColpencilModel {
    //获取网络数据
    void loadShoppingData();

    //修改购物车的数量
    void changeCount(CartItem cartItem);

    //删除购物车操作
    void deleteShoppingCart(String cartIds);

    //注册观察者
    void subResult(Observer<Result> subscriber);


    //注册观察者
    void sub(Observer<ShoppingCartReturn> subscriber);
}
