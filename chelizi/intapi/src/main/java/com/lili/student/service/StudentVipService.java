package com.lili.student.service;

import com.lili.common.vo.ReqResult;
import com.lili.student.dto.StudentVipWithBLOBs;
import com.lili.student.dto.VipChargeDiscount;
import com.lili.student.dto.VipPackage;

import java.util.List;

public interface StudentVipService {


    /**
     * 添加大客户的学员信息
     *
     * @param mobile
     * @param codeInput
     * @param vipId
     * @return
     */
    ReqResult addVipStudent(String mobile, String name,String codeInput, Integer vipId);

    /**
     * 获取根据vipId大客户信息
     *
     * @param vipId
     * @return
     */
    StudentVipWithBLOBs getStudentVip(int vipId);

    /**
     * 获取根据studentId大客户信息
     *
     * @param studentId
     * @return
     */
    ReqResult getStudentVipByStudentId(long studentId);

    /**
     * 获取优惠套餐信息
     * @param vipPackageId
     * @return
     */
    VipPackage getVipPackage(String vipPackageId);

    /**
     * 获取大客户充值优惠的列表信息
     *
     * @param vipPackageId
     * @return
     */
    List<VipChargeDiscount> getChargeDiscountListByPackageId(String vipPackageId);

    /**
     * 获取大客户充值优惠的列表信息
     *
     * @param studentId 学员Id
     * @return
     */
    ReqResult getChargeDiscountListStudentId(long studentId);

    /**
     * 更新学员的大客户优惠套餐模板id
     *
     * @param mobile
     * @param vipPackageId
     * @return
     */
    ReqResult updateVipStudentVipPackageId(String mobile, String vipPackageId);
}
