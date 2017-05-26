package com.lili.finance.manager.common;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.coupon.dto.CCondition;
import com.lili.coupon.dto.CStock;
import com.lili.finance.model.CConditionBDTO;
import com.lili.finance.model.CouponBTO;
import com.lili.finance.model.StudentCouponBDTO;
import com.lili.finance.vo.CouponVo;
import com.lili.finance.vo.StudentCouponVo;

public interface ICMSCouponManager {

    public PagedResult<CCondition> findConditionBatch(CConditionBDTO dto);
    
    public List<CCondition> findConditionList(CConditionBDTO dto);
    
    public CCondition queryCondition(CConditionBDTO dto);
    
    public PagedResult<CouponVo> findCouponBatch(CouponBTO dto);
    
    public PagedResult<StudentCouponVo> findStudentCouponBatch(StudentCouponBDTO dto);

    public List<StudentCouponVo> findStudentCoupon(String couponId);
    
    public CouponVo findCoupon(String coupontmpid);
    
    public CStock findStockByCouponTmpId(String couponTmpId);

	public List<StudentCouponVo> queryCouponList(StudentCouponVo coupon);
	
	public List<CStock> findStockList();

	public String queryCouponName(String couponId);

	public List<CouponVo> queryIsStatus(CouponBTO couponBTO);
}
