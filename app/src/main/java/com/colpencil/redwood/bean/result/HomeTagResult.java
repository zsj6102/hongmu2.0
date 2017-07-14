package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.CategoryVo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:首页标签返回的实体类
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public class HomeTagResult implements Serializable {

    private String code;
    private List<CategoryVo> catListResult;
    private String message;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<CategoryVo> getCatListResult() {
        return catListResult;
    }

    public void setCatListResult(List<CategoryVo> catListResult) {
        this.catListResult = catListResult;
    }
}
