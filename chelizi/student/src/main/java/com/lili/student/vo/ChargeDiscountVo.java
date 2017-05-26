package com.lili.student.vo;

import com.lili.student.dto.VipChargeDiscount;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZLong on 2016/五月/21.
 */
public class ChargeDiscountVo implements Serializable {

    private int type;//1=固定定额送,2=大于某值送

    private String desc;

    private List<VipChargeDiscount> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<VipChargeDiscount> getList() {
        return list;
    }

    public void setList(List<VipChargeDiscount> list) {
        this.list = list;
    }
}
