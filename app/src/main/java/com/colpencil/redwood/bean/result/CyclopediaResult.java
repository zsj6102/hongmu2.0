package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.CyclopediaInfoVo;

import java.io.Serializable;
import java.util.List;

public class CyclopediaResult implements Serializable {

    private int success;
    private String message;
    private int page;
    private int pageSize;
    private int count;
    private List<CyclopediaInfoVo> articleListResult;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CyclopediaInfoVo> getArticleListResult() {
        return articleListResult;
    }

    public void setArticleListResult(List<CyclopediaInfoVo> articleListResult) {
        this.articleListResult = articleListResult;
    }
}
