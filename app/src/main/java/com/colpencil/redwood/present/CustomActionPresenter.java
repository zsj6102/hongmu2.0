package com.colpencil.redwood.present;

import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.tools.FileUtils;
import com.colpencil.redwood.model.CustomActionModel;
import com.colpencil.redwood.model.imples.ICustomActionModel;
import com.colpencil.redwood.view.impl.ICustomActionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.io.File;
import java.util.List;

import rx.Observer;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/8 11 27
 */
public class CustomActionPresenter extends ColpencilPresenter<ICustomActionView> {

    private ICustomActionModel customActionModel;

    public CustomActionPresenter() {
        customActionModel = new CustomActionModel();
    }

    /**
     * 获取商品相关信息
     */
    public void loadData(String acid) {
        customActionModel.loadData(acid);
        Observer<ListResult<BannerVo>> observer = new Observer<ListResult<BannerVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<BannerVo> result) {
                if (result.getCode() == 0) {
                    mView.loadData(result.getResult());
                } else {
                    mView.loadBannerError();
                }
            }
        };
        customActionModel.subBanner(observer);
    }

    /**
     * 提交定制申请
     */
    public void sumbitCustom(String contact, String mobile, String qq, String wechat, String description, List<File> files) {
        customActionModel.sumbitCustom(contact, mobile, qq, wechat, description, files);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.sumbitCustomResult(result);
                FileUtils.deleteAllFile(Constants.sdCardPath);
            }
        };
        customActionModel.subCustom(observer);
    }
}
