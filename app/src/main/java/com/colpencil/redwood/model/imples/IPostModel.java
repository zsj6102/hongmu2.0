package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.HomeTagResult;

import java.io.File;
import java.util.List;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:发帖的Model接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IPostModel {

    /**
     * 获取标签
     */
    void loadTag();

    void subTag(Observer<HomeTagResult> observer);

    /**
     * 发布帖子
     *
     * @param sec_id
     * @param ote_title
     * @param ote_content
     * @param url
     * @param files
     */
    void submitToServer(String sec_id, String ote_title, String ote_content, String url, List<File> files);

    void sub(Observer<CommonResult> observer);
}
