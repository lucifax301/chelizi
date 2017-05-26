package com.lili.finance.service.impl.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.DateUtil;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.TimeUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.dto.CCondition;
import com.lili.coupon.dto.CStock;
import com.lili.coupon.dto.Coupon;
import com.lili.coupon.dto.StudentCoupon;
import com.lili.coupon.service.CouponService;
import com.lili.finance.manager.common.ICMSCouponManager;
import com.lili.finance.model.CConditionBDTO;
import com.lili.finance.model.CouponBTO;
import com.lili.finance.model.StudentCouponBDTO;
import com.lili.finance.service.ICMSCouponService;
import com.lili.finance.vo.CouponVo;
import com.lili.finance.vo.StudentCouponVo;
import com.lili.log.model.LogCommon;

public class CMSCouponServiceImpl implements ICMSCouponService{
	 Logger logger = Logger.getLogger("CMSCouponServiceImpl");

	@Autowired
	ICMSCouponManager cmsCouponManager;
	@Autowired
	CouponService couponService;
	
	@Override	
	public ResponseMessage findConditionBatch(CConditionBDTO dto) throws Exception{
		PagedResult<CCondition> batch =  cmsCouponManager.findConditionBatch(dto);
		return new ResponseMessage().addPagedResult(batch);
	}

	@Override
	public ResponseMessage findCouponBatch(CouponBTO dto) throws Exception{
		
		if (dto.getCourse() != null) {
			if (dto.getCourse() == 2) {
				dto.setCourse2(1);
			}
			else if  (dto.getCourse() == 3) {
				dto.setCourse3(1);
			}
			else if  (dto.getCourse() == 4) {
				dto.setCourseDrive(1);
			}
			else if  (dto.getCourse() == 5) {
				dto.setCourseEnroll(1);
			}
		}
		
		PagedResult<CouponVo> batch =  cmsCouponManager.findCouponBatch(dto);
		return new ResponseMessage().addPagedResult(batch);
	}

	@Override
	public ResponseMessage findCoupon(String coupontmpid) throws Exception{
		CouponVo coupon = cmsCouponManager.findCoupon(coupontmpid);
		return new ResponseMessage().addResult("coupon", coupon);
	}

	@Override
	public ResponseMessage findStudentCouponBatch(StudentCouponBDTO dto) throws Exception{
		PagedResult<StudentCouponVo> batch =  cmsCouponManager.findStudentCouponBatch(dto);
		return new ResponseMessage().addPagedResult(batch);
	}

	@Override
	public ResponseMessage addCondition(LogCommon logCommon,CCondition cCondition) throws Exception{
		cCondition.setCreateTime(DateUtil.now());
		ReqResult result =  couponService.addCondition(cCondition);
		if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){

			logCommon.setRelateId(String.valueOf(cCondition.getConditionid()));
			return new ResponseMessage();
		}
		return new ResponseMessage("新增失败");
	}

	@Override
	public ResponseMessage addCoupon(LogCommon logCommon, Coupon coupon,CStock cStock) throws Exception {
		coupon.setCreatetime(DateUtil.now());
		cStock.setCreatetime(DateUtil.now());
		coupon.setIsexist((byte)1);
		coupon.setVerify((byte)0);
		coupon.setGroupType(1);
		cStock.setHaveused(0);
		if(logCommon != null){
			cStock.setCreateuser(logCommon.getUserName());
			coupon.setCreateuser(logCommon.getUserName());
		}
		
		//过滤使用规则
		if (coupon.getUserule() != null) {
			String expression = coupon.getUserule();
			String toggle[] = expression.split("\\|");
            String conditions[] = toggle[1].split(",");
            CCondition cCondition = null;
            CConditionBDTO dto = new CConditionBDTO();
            for (int i = 0; i < conditions.length; i++) {
                Integer conditionid = Integer.parseInt(conditions[i]);
                dto.setConditionid(conditionid);
                cCondition = cmsCouponManager.queryCondition(dto); //根据券条件遍历ID查询详情
               
                if (cCondition.getType() == (byte)1) { //城市限制
                	coupon.setCityId(cCondition.getParam1());
                }
                else if (cCondition.getType() == (byte)2) { //科目限制
                	String course[] = cCondition.getParam1().split("\\|");
                	for (int j = 0; j < course.length; j++) {
                		if ("1".equals(course[j]) || "2".equals(course[j]) || "6".equals(course[j]) || "11".equals(course[j]) || "12".equals(course[j]) || "16".equals(course[j])) { //科目类型基本固定，不从数据表获取，加快速度
                			coupon.setCourse2(1);
                		}
                		else if ("3".equals(course[j]) || "4".equals(course[j]) || "7".equals(course[j]) || "13".equals(course[j]) || "14".equals(course[j]) || "17".equals(course[j])) {
                			coupon.setCourse3(1);
                		}
                		else if ("5".equals(course[j]) || "15".equals(course[j])) {
                			coupon.setCourseDrive(1);
                		}
                	}
                }
                else if (cCondition.getType() == (byte)3) { //限定获取次数
                	if (cCondition.getParam1() != null && Integer.parseInt(cCondition.getParam1()) > 0) {
                		coupon.setLimitTime(Integer.parseInt(cCondition.getParam1()));
                	}
                }
                else if (cCondition.getType() == (byte)5) { //报名使用
                	coupon.setCourseEnroll(1);
                }
            }
	            
		}
		
		ReqResult result =  couponService.addCoupon(coupon,cStock);
		if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){

			logCommon.setRelateId(String.valueOf(coupon.getCoupontmpid()));
			return new ResponseMessage();
		}
		return new ResponseMessage("新增失败");
	}

	@Override
	public ResponseMessage activeCoupon(String ids) throws Exception {
		ReqResult result =  couponService.updateStockBatch(ids.split(","),1);
		String[] id = ids.split(",");
		Coupon coupon;
		for (int i = 0; i < id.length; i++){
			coupon = couponService.getCouponById(id[i]);
			coupon.setIsexist((byte)1);
			couponService.updateCoupon(coupon);
		}
		if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
			return new ResponseMessage();
		}
		return new ResponseMessage("激活优惠券失败" + result.getResult());
	}

	@Override
	public ResponseMessage cancleCoupon(String ids) throws Exception {
		ReqResult result =  couponService.updateStockBatch(ids.split(","),0);
		if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
			return new ResponseMessage();
		}
		return new ResponseMessage("停用优惠券失败:" + result.getResult());
	}

	@Override
	public ResponseMessage addToStock(String couponTmpId, Integer addTotal)
			throws Exception {
		CStock cStock = cmsCouponManager.findStockByCouponTmpId(couponTmpId);
		cStock.setTotal(cStock.getTotal() + addTotal);
		ReqResult result = couponService.updateCouponStock(cStock);
		if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
			return new ResponseMessage();
		}
		return new ResponseMessage("新增失败");
	}

	@Override
	public ResponseMessage updateCoupon(Coupon coupon, CStock cStock) {
		CouponVo _coupon = null;
		try {
			_coupon = cmsCouponManager.findCoupon(coupon.getCoupontmpid());
			if(_coupon.getVerify() == (byte) 1) {
				return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
			}
			
			//过滤使用规则
			if (coupon.getUserule() != null) {
				String expression = coupon.getUserule();
				String toggle[] = expression.split("\\|");
	            String conditions[] = toggle[1].split(",");
	            CCondition cCondition = null;
	            CConditionBDTO dto = new CConditionBDTO();
	            for (int i = 0; i < conditions.length; i++) {
	                Integer conditionid = Integer.parseInt(conditions[i]);
	                dto.setConditionid(conditionid);
	                cCondition = cmsCouponManager.queryCondition(dto); //根据券条件遍历ID查询详情
	               
	                if (cCondition.getType() == (byte)1) { //城市限制
	                	coupon.setCityId(cCondition.getParam1());
	                }
	                else if (cCondition.getType() == (byte)2) { //科目限制
	                	String course[] = cCondition.getParam1().split("\\|");
	                	for (int j = 0; j < course.length; j++) {
	                		if ("1".equals(course[j]) || "2".equals(course[j]) || "6".equals(course[j]) || "11".equals(course[j]) || "12".equals(course[j]) || "16".equals(course[j])) { //科目类型基本固定，不从数据表获取，加快速度
	                			coupon.setCourse2(1);
	                		}
	                		else if ("3".equals(course[j]) || "4".equals(course[j]) || "7".equals(course[j]) || "13".equals(course[j]) || "14".equals(course[j]) || "17".equals(course[j])) {
	                			coupon.setCourse3(1);
	                		}
	                		else if ("5".equals(course[j]) || "15".equals(course[j])) {
	                			coupon.setCourseDrive(1);
	                		}
	                	}
	                }
	                else if (cCondition.getType() == (byte)3) { //限定获取次数
	                	if (cCondition.getParam1() != null && Integer.parseInt(cCondition.getParam1()) > 0) {
	                		coupon.setLimitTime(Integer.parseInt(cCondition.getParam1()));
	                	}
	                }
	                else if (cCondition.getType() == (byte)5) { //报名使用
	                	coupon.setCourseEnroll(1);
	                }
	            }
			}
		} catch (Exception e) {
			logger.error(e);
		}
		cStock.setCoupontempid(coupon.getCoupontmpid());
		if(_coupon != null)
			coupon.setCreatetime(_coupon.getCreatetime());
		ReqResult result =  couponService.updateCoupon(coupon,cStock);
		if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){

			return new ResponseMessage();
		}
		return new ResponseMessage("更新失败");
		
	}

	@Override
	public ResponseMessage unAuditCoupon(String couponTmpIds) {
		//审核不通过前判断是否已审核不通过的记录
		CouponBTO couponBTO = new CouponBTO();
		couponBTO.setCoupontmpIds(couponTmpIds);
		couponBTO.setVerify((byte)0);
		List<CouponVo>  couponList = cmsCouponManager.queryIsStatus(couponBTO);
		if (couponList.size() != couponTmpIds.split(",").length) {//如果未审核的记录不等于优惠券大小，返回
			return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
		}
				
		String[] idArray = couponTmpIds.split(",");
		if(idArray != null && idArray.length > 0){

			for(int i = 0;i < idArray.length;i ++){
				String couponTmpId = idArray[i];
				Coupon coupon = couponService.getCouponById(couponTmpId);
				coupon.setVerify((byte)2);
				ReqResult result =  couponService.updateCoupon(coupon);
				if(!result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
					return new ResponseMessage("审核不过失败");
				}
			}

		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage auditCoupon(String couponTmpIds) {
		//审核前判断是否已审核
		CouponBTO couponBTO = new CouponBTO();
		couponBTO.setCoupontmpIds(couponTmpIds);
		couponBTO.setVerify((byte)1);
		List<CouponVo>  couponList = cmsCouponManager.queryIsStatus(couponBTO);
		if (couponList.size() > 0) {//如果存在已审核的，返回
			return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
		}
		
		String[] idArray = couponTmpIds.split(",");
		if(idArray != null && idArray.length > 0){

			for(int i = 0;i < idArray.length;i ++){
				String couponTmpId = idArray[i];
				Coupon coupon = couponService.getCouponById(couponTmpId);
				coupon.setVerify((byte)1);
				ReqResult result =  couponService.updateCoupon(coupon);
				if(!result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
					return new ResponseMessage("审核失败");
				}
			}

		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage genStudentCouponAndNotify(String couponTmpId,
			long studentId) throws Exception{
		CStock cStock = cmsCouponManager.findStockByCouponTmpId(couponTmpId);
		ReqResult result =  couponService.genStudentCouponAndNotifyDirect(cStock, studentId);
		if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){

			return new ResponseMessage();
		}
		return new ResponseMessage("券发放失败,请检查券库存");
	}

	@Override
	public ResponseMessage cancleStudentCoupon(String couponids) throws Exception {
		String[] couponid = couponids.split(",");
		for (int i = 0; i < couponid.length; i++){
			List<StudentCouponVo> studentCoupons = cmsCouponManager.findStudentCoupon(couponid[i]);
			if(studentCoupons != null && studentCoupons.size() > 0){
				StudentCouponVo studentCoupon = studentCoupons.get(0);
				StudentCoupon coupon = new StudentCoupon();
				coupon = BeanCopy.copy2Null(studentCoupon, coupon);
				coupon.setIsValid((byte)0);
				ReqResult result =  couponService.updateStudentCoupon(coupon);
				if(!result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
	
					return new ResponseMessage("注销失败");
				}
			}
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage findStock(String couponTmpId) throws Exception {
		CStock cStock = cmsCouponManager.findStockByCouponTmpId(couponTmpId);
		return new ResponseMessage().addResult("pageData", cStock);
	}

	@Override
	public List<StudentCouponVo> queryCouponList(StudentCouponVo coupon) {
		return cmsCouponManager.queryCouponList(coupon);
	}

	@Override
	public ResponseMessage findConditionList(CConditionBDTO dto)
			throws Exception {
		return new ResponseMessage().addResult("pageData", cmsCouponManager.findConditionList(dto));
	}

	@Override
	public ResponseMessage findStockList() {
		return new ResponseMessage().addResult("pageData", cmsCouponManager.findStockList());
	}

	@Override
	public String queryCouponName(String couponId) {
		return cmsCouponManager.queryCouponName(couponId);
	}

	@Override
	public ResponseMessage prolongCoupon(String coupontmpid, String expireTime) {
		try {
			Coupon coupon = new Coupon();
			coupon = couponService.getCouponById(coupontmpid);
			coupon.setExpireTime(TimeUtil.parseDate(expireTime));
			couponService.updateCoupon(coupon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new ResponseMessage();
	}

	@Override
	public ResponseMessage groupCoupon(String couponTmpId, String groupType) {
		try {
			Coupon coupon = new Coupon();
			coupon = couponService.getCouponById(couponTmpId);
			coupon.setGroupType(Integer.parseInt(groupType));
			couponService.updateCoupon(coupon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new ResponseMessage();
	}



}
