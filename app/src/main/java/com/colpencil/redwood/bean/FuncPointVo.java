package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description:功能点的实体类
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public class FuncPointVo implements Serializable {

    private String funcode;
    private String funcname;
    private String iconurl;
    private int funcid;

    public void setFuncode(String funcode) {
        this.funcode = funcode;
    }

    public void setFuncname(String funcname) {
        this.funcname = funcname;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public void setFuncid(int funcid) {
        this.funcid = funcid;
    }

    public String getFuncode() {
        return funcode;
    }

    public String getFuncname() {
        return funcname;
    }

    public String getIconurl() {
        return iconurl;
    }

    public int getFuncid() {
        return funcid;
    }
}
