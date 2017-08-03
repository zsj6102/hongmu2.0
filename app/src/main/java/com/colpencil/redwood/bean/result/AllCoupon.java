package com.colpencil.redwood.bean.result;

import java.io.Serializable;

import java.util.List;

public class AllCoupon implements Serializable {
    /**
     * 可使用的代金券数
     */
     private int voucherCount;
    /**
     * 优惠券
     */
    private List<MemberCoupon> cashList;
    /**
     * 代金券
     */
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
