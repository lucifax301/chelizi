/**
 *
 */
package com.lili.coupon.manager.impl;

import java.util.List;

import com.lili.coupon.dto.CCondition;
import com.lili.coupon.dto.CStock;
import com.lili.coupon.dto.Coupon;
import com.lili.coupon.dto.StudentCoupon;

/**
 * @author linbo
 *         优惠券
 */
public interface CouponManager {
    /**
     * 添加优惠券模板
     *
     * @param coupon
     * @param stock
     * @return
     */
    boolean addCoupon(Coupon coupon, CStock stock);

    /**
     * 给学员添加优惠券
     *
     * @param studentCoupon
     * @return
     */
    long addStudentCoupon(StudentCoupon studentCoupon);

    /**
     * 增加优惠券的限制条件
     *
     * @param condition
     * @return
     */
    boolean addCondition(CCondition condition);

    /**
     * 取得对某个学员生效的所有库存
     *
     * @param eventType
     * @return
     */
    List<CStock> getAllSuitableStock(String eventType);

    /**
     * 根据模板id拿到优惠券模板
     *
     * @param couponTmpId
     * @return
     */
    Coupon getCouponById(String couponTmpId);
    
    /**
     * 根据extra拿到优惠券模板
     *
     * @param couponTmpId
     * @return
     */
    List<Coupon> getCouponByExtra(String extra);

    /**
     * 根据模板id拿到库存
     *
     * @param couponTmpId
     * @return
     */
    CStock getStockByCouponTmpId(String couponTmpId);

    /**
     * 取得学员所有的优惠券
     *
     * @param studentId
     * @param page
     * @param isUsed    是否已使用
     * @param isUsed    是否有效
     * @return
     */
    List<StudentCoupon> getStudentAllCouponByPage(long studentId, int page, boolean isUsed, boolean isValid);

    /**
     * 取得学员所有的优惠券，不分页
     *
     * @return
     */
    List<StudentCoupon> getStudentAllCouponsCanUse(long studentId);

    /**
     * 根据唯一ID取学员优惠券
     *
     * @param studentCouponId
     * @param studentId
     * @return
     */
    StudentCoupon getStudentCouponById(long studentCouponId, long studentId);

    /**
     * 取可用优惠券的数量
     *
     * @param studentId
     * @return
     */
    int getUseableCouponCount(long studentId);

    /**
     * 恢复优惠券
     *
     * @param coupon
     */
    void recoverCoupon(long coupon, long studentId);

    /**
     * 激活库存
     *
     * @param stock
     * @return
     */
    boolean activeStock(CStock stock, boolean isActive);

    /**
     * 恢复库存
     *
     * @param stockId
     */
    void recoverStock(int stockId);
    
    /**
     * 恢复多张库存
     *
     * @param stockId
     */
    void recoverMoreStock(int stockId,int num);

    /**
     * 更新优惠券模板
     *
     * @param coupon
     * @return
     */
    boolean updateCoupon(Coupon coupon, CStock stock);

    /**
     * 更新优惠券模板信息
     *
     * @param coupon
     * @return
     */
    boolean updateCoupon(Coupon coupon);


    /**
     * 批量更新优惠券库存,批量激活或者停止发放
     * @param ids 优惠券列表
     * @param isexist 0=不生效，1=生效
     * @return
     */
    boolean updateStockBatch(String[] ids,int isexist);

    /**
     * 更新学员的优惠券
     *
     * @param studentCoupon
     * @return
     */
    boolean updateStudentCoupon(StudentCoupon studentCoupon);

    /**
     * 消费一次库存
     *
     * @param cStock
     * @return
     */
    int useOneStock(CStock cStock);
    
    /**
     * 消费多次库存
     *
     * @param cStock
     * @return
     */
    int useMoreStock(CStock cStock,int num);
    
    /**
     * 更新库存信息
     * @param stock
     * @return
     */
    boolean updateCouponStock(CStock stock);
    
    /**
     * 根据学员ID，优惠券ID查询是否已存在该券
     * @param studentId
     * @param couponTmpId
     * @return
     */
    int selectIsExitCount(long studentId, String couponTmpId);
    
    /**
     * 根据优惠券ID查询已用多少张
     * @param couponTmpId
     * @return
     */
    int selectIsUseCount(String couponTmpId);
    

	long addStudentCouponList(List<StudentCoupon> studentCouponList);

	int updateCouponSelective(Coupon couponUp);
	
	 
    /**
     * 根据用户id 优惠券模板Id判断是否存在
     * @param couponTmpId
     * @return
     */
	public int checkExistCoupon(String userId,String couponTmpIds);
}
