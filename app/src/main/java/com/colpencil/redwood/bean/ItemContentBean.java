package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * 描述：AnimatedExpandableListView的Item项
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/13 09 25
 */
public class ItemContentBean implements Serializable {
    /**
     * 标识是否是title内容
     */
    private boolean isTitle;
    /**
     * 标题名称
     */
    private String mainTitleName;
    /**
     * 标题右边信息
     */
    private String rightTitleName;
    /**
     * 标识是否是主内容
     */
    private boolean isContent;
    /**
     */
    private int groupPosition;
    /**
     * 主内容
     */
    private String mainContent;

    /**
     * 标识是否选中
     */
    private boolean isChoose;

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getMainTitleName() {
        return mainTitleName;
    }

    public void setMainTitleName(String mainTitleName) {
        this.mainTitleName = mainTitleName;
    }

    public String getRightTitleName() {
        return rightTitleName;
    }

    public void setRightTitleName(String rightTitleName) {
        this.rightTitleName = rightTitleName;
    }

    public boolean isContent() {
        return isContent;
    }

    public void setContent(boolean content) {
        isContent = content;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }
}
