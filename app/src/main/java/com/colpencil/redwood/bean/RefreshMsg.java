package com.colpencil.redwood.bean;

public class RefreshMsg {

    /**
     * 处理类型标识    0 :商家申请界面返回主界面 获取申请状态   21:名师名匠申请提交成功     22: 个人商家申请提交成功 ;23:品牌商家申请提交成功
     *
     */
    public RefreshMsg() {
    }

    public RefreshMsg(int type) {
        this.type = type;
    }

    private int type;
    private int id;
    private String title;
    private String content;
    private int sort;
    private String image;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
