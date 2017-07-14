package com.colpencil.redwood.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class CyclopediaInfo implements Serializable {

    private String title;
    private String content;
    private String sec_id;
    private File coverFile;
    private int type;
    private List<File> fileList;
    private int game;

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

    public String getSec_id() {
        return sec_id;
    }

    public void setSec_id(String sec_id) {
        this.sec_id = sec_id;
    }

    public File getCoverFile() {
        return coverFile;
    }

    public void setCoverFile(File coverFile) {
        this.coverFile = coverFile;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }
}
