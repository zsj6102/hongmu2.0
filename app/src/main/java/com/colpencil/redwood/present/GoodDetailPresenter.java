package com.colpencil.redwood.present;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.GoodInfoResult;
import com.colpencil.redwood.bean.result.OrderPayInfo;
import com.colpencil.redwood.model.GoodDetailModel;
import com.colpencil.redwood.model.imples.IGoodDetailModel;
import com.colpencil.redwood.view.impl.IGoodDetailView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.Map;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:商品详情
 * @Email DramaScript@outlook.com
 * @date 2016/8/8
 */
public class GoodDetailPresenter extends ColpencilPresenter<IGoodDetailView> {

    private IGoodDetailModel model;

    public GoodDetailPresenter() {
        model = new GoodDetailModel();
    }

    /**
     * 加入购物车
     */
    public void addtoCar(int goodsId, int productId, int num) {
        model.addtocar(goodsId, productId, num);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.addtocarsuccess(result);
            }
        };
        model.subAdd(observer);
    }

    /**
     * 获取商品收藏状态
     */
    public void loadState(int goods_id) {
        model.loadState(goods_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.checkState(result);
            }
        };
        model.subLoad(observer);
    }

    /**
     * 收藏商品
     *
     * @param good_id
     */
    public void keepGood(int good_id) {
        model.keepGood(good_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.keepGoodResult(result);
            }
        };
        model.subKeep(observer);
    }

    /**
     * 添加浏览记录
     *
     * @param good_id
     */
    public void browerGood(int good_id) {
        model.browerGood(good_id);
        Observer<CommonResult> observer = new Observer<CommonResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommonResult result) {

            }
        };
        model.subBrower(observer);
    }

    /**
     * 获取分享链接
     *
     * @param good_id
     */
    public void loadShareUrl(int good_id) {
        model.loadShare(good_id);
        Observer<CommonResult> observer = new Observer<CommonResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommonResult result) {
                mView.shareResult(result);
            }
        };
        model.subShare(observer);
    }

    /**
     * 根据商品Id 查询商品详情信息
     */
    public void loadGoodInfo(final int goodid) {
        model.loadGoodInfo(goodid);
        Observer<GoodInfoResult> observer = new Observer<GoodInfoResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GoodInfoResult goodInfoResult) {
                if (goodInfoResult.getCode().equals("1")) {
                    mView.loadGoods(goodInfoResult.getGoodInfo());
                }
            }
        };
        model.subGoodInfo(observer);
    }

    /**
     * 统计分享
     *
     * @param type
     * @param platform
     * @param id
     */
    public void addUpSharenum(int type, String platform, int id) {
        model.addUpShare(type, platform, id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> stringEntityResult) {

            }
        };
        model.subAddup(observer);
    }


    public void getDirectOrder(Map<String,String> params){
        model.loadDirectOrder(params);
        Observer<OrderPayInfo> observer = new Observer<OrderPayInfo>(){
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OrderPayInfo orderPayInfo) {
                //                if(orderPayInfo!= null && orderPayInfo.getCode() == 0){
                //                    mView.loadNewOrder(orderPayInfo.getData());
                //                }else{
                //                    mView.loadFail(orderPayInfo.getMessage());
                //                }
                mView.loadNewOrder(orderPayInfo);
            }
        };
        model.subDirectOrder(observer);
    }
}
