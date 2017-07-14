package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description: 音乐播放
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/30
 */
public class Music implements Serializable {
    /**
     * popularity
     */
    private String title;
    /**
     * 禅音图片路径
     */
    private String image;
    /**
     * 禅音id
     */
    private int voice_id;
    /**
     * 禅音文件路径
     */
    private String url;
    /**
     * 标签
     */
    private String tag;
    /**
     * 人气（推荐，火爆。。）
     */
    private String popularity;

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", voice_id=" + voice_id +
                ", url='" + url + '\'' +
                ", tag='" + tag + '\'' +
                ", popularity='" + popularity + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getVoice_id() {
        return voice_id;
    }

    public void setVoice_id(int voice_id) {
        this.voice_id = voice_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }
}
