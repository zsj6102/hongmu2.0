package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.MemberCoupon;

import java.io.Serializable;
import java.util.List;

public class MemberCouponResult implements Serializable {

    private int code;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        private int voucherCount;
        private List<MemberCoupon> cashList;
        private List<MemberCoupon> voucherList;

        public int getVoucherCount() {
            return voucherCount;
        }

        public void setVoucherCount(int voucherCount) {
            this.voucherCount = voucherCount;
        }

        public List<MemberCoupon> getCashList() {
            return cashList;
        }

        public void setCashList(List<MemberCoupon> cashList) {
            this.cashList = cashList;
        }

        public List<MemberCoupon> getVoucherList() {
            return voucherList;
        }

        public void setVoucherList(List<MemberCoupon> voucherList) {
            this.voucherList = voucherList;
        }
    }
}
