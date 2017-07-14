package com.colpencil.redwood.present.home;

import android.util.Log;

import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.HomeTagResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.tools.FileUtils;
import com.colpencil.redwood.model.PostModel;
import com.colpencil.redwood.model.imples.IPostModel;
import com.colpencil.redwood.view.impl.IPostView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.io.File;
import java.util.List;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:发帖的presenter
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class PostPresenter extends ColpencilPresenter<IPostView> {

    private IPostModel model;

    public PostPresenter() {
        model = new PostModel();
    }

    public void submitToServer(String sec_id, String ote_title, String ote_content, String url, List<File> files) {
        model.submitToServer(sec_id, ote_title, ote_content, url, files);
        Observer<CommonResult> observer = new Observer<CommonResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.getMessage());
            }

            @Override
            public void onNext(CommonResult result) {
                mView.subResult(result);
                FileUtils.deleteAllFile(Constants.sdCardPath);
            }
        };
        model.sub(observer);
    }

    /**
     * 圈子的分类
     */
    public void loadTag() {
        model.loadTag();
        Observer<HomeTagResult> observer = new Observer<HomeTagResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(HomeTagResult homeTagResult) {
                if (homeTagResult.getCode().equals("0")) {
                    mView.loadTag(homeTagResult.getCatListResult());
                }
            }
        };
        model.subTag(observer);
    }
}
