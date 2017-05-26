/**
 * 
 */
package com.lili.finance.mapper.dao.common;

import java.util.List;

import com.lili.coupon.dto.CCondition;
import com.lili.finance.model.CConditionBDTO;
import com.lili.finance.model.CouponBTO;
import com.lili.finance.model.StudentCouponBDTO;
import com.lili.finance.vo.CouponVo;
import com.lili.finance.vo.StudentCouponVo;

public interface CMSCouponDao {
    
    public List<CCondition> findConditionBatch(CConditionBDTO dto);

    public List<CouponVo> findCouponBatch(CouponBTO dto);

    public List<StudentCouponVo> findStudentCouponBatch(StudentCouponBDTO dto);
    
    public List<StudentCouponVo> findStudentCoupon(String couponId);

    public CouponVo findCoupon(String coupontmpid);
    
    public String queryCouponName(String coupontmpid);

	public List<StudentCouponVo> queryCouponList(StudentCouponVo coupon);

	public CCondition queryCondition(CConditionBDTO dto);

	public List<CouponVo> queryCouponVoList(CouponBTO couponBTO);
    
	
}
