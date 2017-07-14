package com.colpencil.redwood.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * @Description:发帖的实体类
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public class PostInfo implements Serializable {

    private String title;
    private String content;
    private String url;
    private String sec_id;
    private List<File> fileList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSec_id() {
        return sec_id;
    }

    public void setSec_id(String sec_id) {
        this.sec_id = sec_id;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
