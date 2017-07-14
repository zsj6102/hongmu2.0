package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.model.WebViewModel;
import com.colpencil.redwood.model.imples.IWebViewModel;
import com.colpencil.redwood.view.impl.IWebViewView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * @author 曾 凤
 * @Description: H5请求
 * @Email 20000263@qq.com
 * @date 2016/8/16
 */
public class WebViewPresenter extends ColpencilPresenter<IWebViewView> {

    private IWebViewModel webViewModel;

    public WebViewPresenter() {
        webViewModel = new WebViewModel();
    }

    public void logisticsInfor(int orderId) {
        webViewModel.logisticsInfor(orderId);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "查看物流异常:" + e.toString());
            }

            @Override
            public void onNext(ResultCodeInt resultCodeInt) {
                Log.e("返回值", "查看物流:" + resultCodeInt.toString());
                if (resultCodeInt.getCode() == 1) {
                    mView.webUrl(resultCodeInt.getUrl());
                } else {
                    mView.fail(resultCodeInt.getCode() + "", resultCodeInt.getMsg());
                }
            }
        };
        webViewModel.sub(observer);
    }

    /**
     * H5链接请求
     */
    public void getH5Url(int type) {
        webViewModel.getH5Url(type);
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("返回值", "其他H5异常:" + e.toString());
            }

            @Override
            public void onNext(ResultCodeInt resultCodeInt) {
                Log.e("返回值", "其他H5:" + resultCodeInt.toString());
                if (resultCodeInt.getCode() == 1) {
                    mView.webUrl(resultCodeInt.getUrl());
                } else {
                    mView.fail(resultCodeInt.getCode() + "", resultCodeInt.getMsg());
                }
            }
        };
        webViewModel.sub(observer);
    }

    /**
     * 相关规则
     */
    public void loadRule() {
        webViewModel.loadRule();
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                if (result.getCode() == 1) {
                    mView.webUrl(result.getUrl());
                } else {
                    mView.fail(result.getCode() + "", result.getMsg());
                }
            }
        };
        webViewModel.subRule(observer);
    }

    /**
     * 客服
     */
    public void loadService() {
        webViewModel.loadService();
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.serviceResult(result);
            }
        };
        webViewModel.subService(observer);
    }

    /**
     * 会员信息
     */
    public void loadInfo() {
        webViewModel.loadInfo();
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                if (result.getCode() == 1) {
                    mView.webUrl(result.getUrl());
                } else {
                    mView.fail(result.getCode() + "", result.getMsg());
                }
            }
        };
        webViewModel.subInfo(observer);
    }

}

