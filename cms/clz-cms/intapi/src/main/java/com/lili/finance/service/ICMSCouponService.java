package com.lili.finance.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.coupon.dto.CCondition;
import com.lili.coupon.dto.CStock;
import com.lili.coupon.dto.Coupon;
import com.lili.finance.model.CConditionBDTO;
import com.lili.finance.model.CouponBTO;
import com.lili.finance.model.StudentCouponBDTO;
import com.lili.finance.vo.StudentCouponVo;
import com.lili.log.model.LogCommon;

public interface ICMSCouponService {

	public ResponseMessage findConditionBatch(CConditionBDTO dto) throws Exception;

	public ResponseMessage findConditionList(CConditionBDTO dto) throws Exception;
	
	public ResponseMessage findCouponBatch(CouponBTO dto) throws Exception;
	
	public ResponseMessage findStudentCouponBatch(StudentCouponBDTO dto) throws Exception;

	public ResponseMessage findStock(String couponTmpId) throws Exception;
	
	public ResponseMessage findCoupon(String coupontmpid) throws Exception;

	public ResponseMessage addCondition(LogCommon logCommon,CCondition cCondition) throws Exception;

	public ResponseMessage addCoupon(LogCommon logCommon,Coupon coupon,CStock cStock) throws Exception;

	/**
	 * 激活优惠券
	 * @param coupon
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage activeCoupon(String ids) throws Exception;
	
	/**
	 * 停发优惠券
	 * @param coupon
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage cancleCoupon(String ids) throws Exception;
	
	/**
	 * 通过优惠券模板id增加库存数量
	 * @param couponTmpId
	 * @param addTotal
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage addToStock(String couponTmpId,Integer addTotal) throws Exception;
	
	/**
	 * 更新优惠券信息
	 * @param coupon
	 * @return
	 */
	public ResponseMessage updateCoupon(Coupon coupon,CStock cStock);
	


    /**
     * 审核不过 优惠券模板
     *
     * @param coupon
     * @return
     */
	public ResponseMessage unAuditCoupon(String couponTmpId);


    /**
     * 审核通过 优惠券模板
     *
     * @param coupon
     * @return
     */
	public ResponseMessage auditCoupon(String couponTmpId);
	
	
	/**
	 * 发券并通知学员
	 * @param cStock
	 * @param studentId
	 * @return
	 */
    public ResponseMessage genStudentCouponAndNotify(String couponTmpId, long studentId) throws Exception ;
    
    
    public ResponseMessage cancleStudentCoupon(String couponids) throws Exception;

	public List<StudentCouponVo> queryCouponList(StudentCouponVo coupon);
	

    /**
     * 主要是为了获取监听事件
     * @return
     */
	public ResponseMessage findStockList();

	public String queryCouponName(String couponId);

	public ResponseMessage prolongCoupon(String coupontmpid, String expireTime);

	public ResponseMessage groupCoupon(String couponTmpId, String groupType);
}
