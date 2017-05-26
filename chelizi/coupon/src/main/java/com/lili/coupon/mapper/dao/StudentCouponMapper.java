package com.lili.coupon.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.coupon.dto.StudentCoupon;

public interface StudentCouponMapper {
    int deleteByPrimaryKey(Long couponid);

    long insert(StudentCoupon record);

    int insertSelective(StudentCoupon record);

    StudentCoupon selectByPrimaryKey(Long couponid, long studentId);

    int updateByPrimaryKeySelective(StudentCoupon record);

    int updateByPrimaryKey(StudentCoupon record);
    
    int selectCurCount(@Param("studentId") long studentId, @Param("couponTmpId") String couponTmpId);
    
    int selectIsExitCount(@Param("studentId") long studentId, @Param("couponTmpId") String couponTmpId);

    List<StudentCoupon> getAllCouponsCanUse(@Param("studentId") long studentId);

    List<StudentCoupon> getStudentAllCouponByPage(CouponPage couponPage);

    int recoverCoupon(@Param("couponId") long couponId, @Param("studentId") long studentId);

    int getUseableCouponCount(@Param("studentId") long studentId);

	long insertList(List<StudentCoupon> studentCouponList);

	int selectIsUseCount(String couponTmpId);
	
	int checkExistCoupon(@Param("userId") String userId,@Param("couponTmpIds") String couponTmpIds);
}