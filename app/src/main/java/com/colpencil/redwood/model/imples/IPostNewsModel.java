package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.io.File;
import java.util.List;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:发布百科
 * @Email DramaScript@outlook.com
 * @date 2016/8/17
 */
public interface IPostNewsModel extends ColpencilModel {

    /**
     * 获取分类
     */
    void loadTag(int cat_id);

    void subTag(Observer<ListResult<CategoryVo>> observer);

    /**
     * 发布百科
     */
    void postCyclopedia(int cat_id, File mFile, String title, String content, List<File> files, String child_cat_id, int game);

    void subCyclopedia(Observer<EntityResult<String>> observer);

    /**
     * 发布新闻
     */
    void postNews(int cat_id, File mFile, String title, String content, List<File> files);

    void subNews(Observer<EntityResult<String>> observer);
}
