package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.GoodInfo;
import com.colpencil.redwood.bean.HomeGoodInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:首页商品返回的实体类
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public class HomeGoodResult implements Serializable {

    private String code;
    private String message;
    private List<HomeGoodInfo> CG_Result;
    private List<GoodInfo> result;

    @Override
    public String toString() {
        return "HomeGoodResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", CG_Result=" + CG_Result +
                ", result=" + result +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HomeGoodInfo> getCG_Result() {
        return CG_Result;
    }

    public void setCG_Result(List<HomeGoodInfo> CG_Result) {
        this.CG_Result = CG_Result;
    }

    public List<GoodInfo> getResult() {
        return result;
    }

    public void setResult(List<GoodInfo> result) {
        this.result = result;
    }
}
