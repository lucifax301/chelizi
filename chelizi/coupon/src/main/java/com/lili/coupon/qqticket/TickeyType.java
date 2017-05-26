package com.lili.coupon.qqticket;

/**
 * Created by ZLong on 2016/五月/24.
 */
public enum TickeyType {

    CLZ("01"), QQ("11");

    private String value;

    TickeyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
