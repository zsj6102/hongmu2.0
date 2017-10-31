package com.colpencil.redwood.present.mine;

import android.util.Log;

import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.colpencil.redwood.model.MeFragmentModel;
import com.colpencil.redwood.model.imples.IMeFragmentModel;
import com.colpencil.redwood.view.impl.IMeFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.HashMap;

import rx.Observer;
import rx.Subscriber;

/**
 * 描述：MeFragment 网络请求
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 11 18
 */
public class MeFragmentPresenter extends ColpencilPresenter<IMeFragmentView> {

    private IMeFragmentModel meFragmentModel;

    public MeFragmentPresenter() {
        meFragmentModel = new MeFragmentModel();
    }

    /**
     * 用户个人信息加载
     */
    public void loadUserInfor() {
        meFragmentModel.loadUserInfor();
        Observer<LoginBean> observer = new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoginBean loginBean = new LoginBean();
                loginBean.setMsg("服务请求失败");
                mView.fail(loginBean);
            }

            @Override
            public void onNext(LoginBean loginBean) {
                Log.e("返回值", loginBean.toString());
                if (loginBean.getCode() == 1) {//信息获取成功
                    mView.loadUserInfor(loginBean);
                }
            }
        };
        meFragmentModel.subUserInfor(observer);
    }

    /**
     * 加载商品信息
     */
    public void loadGoodInfor(int tagid, int page, int pageSize) {
        meFragmentModel.loadGoodInfor(tagid, page, pageSize);
        Observer<ListResult<HomeGoodInfo>> observer = new Observer<ListResult<HomeGoodInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ListResult<HomeGoodInfo> homeGoodResult) {
                if (homeGoodResult.getCode() == 0) {
                    mView.loadGoodInfor(homeGoodResult.getCG_Result());
                } else {
                    LoginBean loginBean = new LoginBean();
                    loginBean.setMsg(homeGoodResult.getMessage());
                    mView.fail(loginBean);
                }
            }
        };
        meFragmentModel.subGoodInfor(observer);
    }
    public void getApplyStatus(HashMap<String,String> params){
        meFragmentModel.applyStatus(params);
        Subscriber<ApplyStatusReturn> subscriber = new Subscriber<ApplyStatusReturn>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mView!=null){
                    mView.getStatusError(e.getMessage());
                }

            }

            @Override
            public void onNext(ApplyStatusReturn applyStatusReturn) {
                if(mView!=null){
                    mView.getStatusSucess(applyStatusReturn);
                }

            }
        };
        meFragmentModel.subStauts(subscriber);
    }
    /**
     * 获取客服电话
     */
    public void getPhoneNum() {
        meFragmentModel.getPhoneNum();
        Observer<ResultCodeInt> observer = new Observer<ResultCodeInt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showError(null, null);
            }

            @Override
            public void onNext(ResultCodeInt result) {
                if (result.getCode() == 1) {//成功获取售后电话
                    mView.callPhone(result.getServicePhone());
                } else {
                    LoginBean login = new LoginBean();
                    login.setCode(result.getCode());
                    login.setMsg(result.getMsg());
                    mView.fail(login);
                }
            }
        };
        meFragmentModel.subResult(observer);
    }
}
