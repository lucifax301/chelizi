package com.lili.student.manager;

import com.lili.student.dto.StudentVipExample;
import com.lili.student.dto.StudentVipWithBLOBs;
import com.lili.student.dto.VipCoupon;

import java.util.List;

public interface StudentVipManager {
    /**
     * 获取大客户信息，app调用
     *
     * @param vipId
     * @return
     */
    StudentVipWithBLOBs getStudentVip(int vipId);

    /**
     * 获取大客户信息，cms
     *
     * @param example
     * @return
     */
    List<StudentVipWithBLOBs> getStudentVip(StudentVipExample example);

    /**
     * 增加大客户
     *
     * @param record
     * @return
     */
    int postStudentVip(StudentVipWithBLOBs record);

    /**
     * 修改大客户
     *
     * @param record
     * @return
     */
    int putStudentVip(StudentVipWithBLOBs record);


    /**
     * 获取大客户赠送优惠的信息列表
     *
     * @param vipPackageId
     * @return
     */
    List<VipCoupon> selectValidByVipCouponByVipPackageId(String vipPackageId);
}
