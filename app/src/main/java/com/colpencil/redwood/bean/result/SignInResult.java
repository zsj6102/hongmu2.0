package com.colpencil.redwood.bean.result;

/**
 * @author 陈宝
 * @Description:签到返回的实体
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class SignInResult {

    private String code;
    private String msg;
    private String point;
    private String totalPoint;
    private String result;
    private boolean flag;

    @Override
    public String toString() {
        return "SignInResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", point='" + point + '\'' +
                ", totalPoint='" + totalPoint + '\'' +
                ", result='" + result + '\'' +
                ", flag=" + flag +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(String totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
