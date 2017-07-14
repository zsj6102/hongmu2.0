package com.colpencil.redwood.present.home;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.tools.FileUtils;
import com.colpencil.redwood.model.PostNewsModel;
import com.colpencil.redwood.model.imples.IPostNewsModel;
import com.colpencil.redwood.view.impl.IPostNewsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.io.File;
import java.util.List;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:发布百科
 * @Email DramaScript@outlook.com
 * @date 2016/8/17
 */
public class PostNewsPresenter extends ColpencilPresenter<IPostNewsView> {

    private IPostNewsModel model;

    public PostNewsPresenter() {
        model = new PostNewsModel();
    }

    /**
     * 发布百科
     */
    public void postCyclopedia(int cat_id, File mFile, String title, String content, List<File> files, String child_cat_id, int game) {
        model.postCyclopedia(cat_id, mFile, title, content, files, child_cat_id, game);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.submitResult(result);
                FileUtils.deleteAllFile(Constants.sdCardPath);
            }
        };
        model.subCyclopedia(observer);
    }

    /**
     * 发布新闻
     */
    public void postNews(int cat_id, File mFile, String title, String content, List<File> files) {
        model.postNews(cat_id, mFile, title, content, files);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.submitResult(result);
                FileUtils.deleteAllFile(Constants.sdCardPath);
            }
        };
        model.subNews(observer);
    }

    /**
     * 获取分类
     */
    public void loadTag(int cat_type) {
        model.loadTag(cat_type);
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CategoryVo> homeTagResult) {
                if (homeTagResult.getCode() == 0) {
                    mView.loadTag(homeTagResult.getCatListResult());
                }
            }
        };
        model.subTag(observer);
    }
}
