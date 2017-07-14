package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.CommentVo;

import java.io.Serializable;
import java.util.List;

public class CommentResult implements Serializable {

    private List<CommentVo> notesList;
    private String msg;
    private int code;
    private List<CommentVo> result;

    public List<CommentVo> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<CommentVo> notesList) {
        this.notesList = notesList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CommentVo> getResult() {
        return result;
    }

    public void setResult(List<CommentVo> result) {
        this.result = result;
    }
}
