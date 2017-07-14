package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class ListResult<T> implements Serializable {

    public int code;
    private String msg;
    private String message;
    private String acid;
    private  int success;
    private int count;

    private List<T> logi;
    private List<T> result;
    private List<T> menu_List;
    private List<T> catListResult;
    private List<T> CG_Result;
    private List<T> commentList;
    private List<T> memberCatListResult;
    private List<T> articleListResult;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public List<T> getMenu_List() {
        return menu_List;
    }

    public void setMenu_List(List<T> menu_List) {
        this.menu_List = menu_List;
    }

    public List<T> getCatListResult() {
        return catListResult;
    }

    public void setCatListResult(List<T> catListResult) {
        this.catListResult = catListResult;
    }

    public List<T> getCG_Result() {
        return CG_Result;
    }

    public void setCG_Result(List<T> CG_Result) {
        this.CG_Result = CG_Result;
    }

    public List<T> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<T> commentList) {
        this.commentList = commentList;
    }

    public List<T> getMemberCatListResult() {
        return memberCatListResult;
    }

    public void setMemberCatListResult(List<T> memberCatListResult) {
        this.memberCatListResult = memberCatListResult;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<T> getArticleListResult() {
        return articleListResult;
    }

    public void setArticleListResult(List<T> articleListResult) {
        this.articleListResult = articleListResult;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getLogi() {
        return logi;
    }

    public void setLogi(List<T> logi) {
        this.logi = logi;
    }
}
