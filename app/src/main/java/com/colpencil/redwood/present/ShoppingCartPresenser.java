package com.colpencil.redwood.present;

import android.util.Log;

import com.colpencil.redwood.bean.CartItem;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.bean.ShoppingCartReturn;
import com.colpencil.redwood.bean.result.AllCartList;
import com.colpencil.redwood.model.ShoppingCartModel;
import com.colpencil.redwood.model.imples.IShoppingCartModel;
import com.colpencil.redwood.view.impl.IShoppingCartView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.Map;

import rx.Observer;

/**
 * 描述：购物车
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/14 14 13
 */
public class ShoppingCartPresenser extends ColpencilPresenter<IShoppingCartView> {
    private IShoppingCartModel shoppingCartModel;

    public ShoppingCartPresenser() {
        shoppingCartModel = new ShoppingCartModel();
    }

    /**
     * 二期请求购物车新接口
     */
    public void loadAllcartList(Map<String, String> params) {
        shoppingCartModel.loadNewCartData(params);
        Observer<AllCartList> observer = new Observer<AllCartList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AllCartList allCartList) {
                if (allCartList != null) {
                    if (allCartList.getCode() == 0) {
                        mView.loadNewCartData(allCartList);
                    } else {
                        mView.loadFail(String.valueOf(allCartList.getCode()), allCartList.getMessage());
                    }
                }
            }
        };
        shoppingCartModel.subnew(observer);
    }

    /**
     * 请求网络数据
     */
    public void loadShoppingCartData() {
        shoppingCartModel.loadShoppingData();
        Observer<ShoppingCartReturn> observer = new Observer<ShoppingCartReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showError(null, null);
            }

            @Override
            public void onNext(ShoppingCartReturn shoppingCartReturn) {
                Log.e("返回值", "购物车请求数据：" + shoppingCartReturn.toString());
                if (shoppingCartReturn.getCode().equals("0")) {//数据获取成功
                    mView.loadShoppingCartData(shoppingCartReturn.getGoodsItemList());
                } else {
                    mView.loadFail(shoppingCartReturn.getCode(), shoppingCartReturn.getMsg());
                }
            }
        };
        shoppingCartModel.sub(observer);
    }

    /**
     * 修改购物车的商品数量信息
     */
    public void changeProductInfor(CartItem cartItem) {
        shoppingCartModel.changeCount(cartItem);
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "修改购物车数据异常：" + e.toString());
            }

            @Override
            public void onNext(Result result) {
                Log.e("返回值", "修改购物车数据：" + result.toString());
            }
        };
        shoppingCartModel.subResult(observer);
    }

    /**
     * 删除购物车操作
     */
    public void deleteShoppingCart(String cartIds) {
        shoppingCartModel.deleteShoppingCart(cartIds);
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "删除购物车数据异常：" + e.toString());
            }

            @Override
            public void onNext(Result result) {
                Log.e("返回值", "删除购物车数据：" + result.toString());
                if (result.getCode().equals("0")) {//删除成功
                    mView.reLoadData(result.getMessage());
                } else {
                    mView.deletefail(result.getCode(), result.getMessage());
                }
            }
        };
        shoppingCartModel.subResult(observer);
    }
}
