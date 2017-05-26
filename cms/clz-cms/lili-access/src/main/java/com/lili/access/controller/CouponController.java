package com.lili.access.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.cms.constant.LogConstant;
import com.lili.coupon.dto.CCondition;
import com.lili.coupon.dto.CStock;
import com.lili.coupon.dto.Coupon;
import com.lili.finance.model.CConditionBDTO;
import com.lili.finance.model.CouponBTO;
import com.lili.finance.model.StudentCouponBDTO;
import com.lili.finance.service.ICMSCouponService;
import com.lili.log.model.LogCommon;

@Controller
@Scope("prototype")
@RequestMapping("/coupon")
public class CouponController extends BaseController{
	
	 Logger logger = Logger.getLogger("CouponController");

	@Autowired
	ICMSCouponService cmsCouponService;
	
	/**
	 * 注销学员的优惠券
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cancle-stucoupon", method= RequestMethod.POST)
    @ResponseBody
    public String cancleStudentCoupon(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
//			LogCommon log = initLogParams(request, LogConstant.MENU_ID_COUPON, LogConstant.ACTION_UPDATE);
			return cmsCouponService.cancleStudentCoupon(getParamStr(request, "couponid")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 激活优惠券
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/active-coupon", method= RequestMethod.POST)
    @ResponseBody
    public String activeCoupon(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
//			LogCommon log = initLogParams(request, LogConstant.MENU_ID_COUPON, LogConstant.ACTION_UPDATE);
			return cmsCouponService.activeCoupon(getParamStr(request, "ids")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	

	/**
	 * 停发优惠券
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cancle-coupon", method= RequestMethod.POST)
    @ResponseBody
    public String cancleCoupon(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
//			LogCommon log = initLogParams(request, LogConstant.MENU_ID_COUPON, LogConstant.ACTION_UPDATE);
			access.info("|||couponids = " + getParamStr(request, "ids"));
			return cmsCouponService.cancleCoupon(getParamStr(request, "ids")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	

	/**
	 * 增加库存数量
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add-stock", method= RequestMethod.POST)
    @ResponseBody
    public String addStock(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			initLogParams(request, LogConstant.MENU_ID_COUPON, LogConstant.ACTION_UPDATE);
			return cmsCouponService.addToStock(getParamStr(request, "couponTmpId"), getParamInt(request, "addTotal")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 添加优惠券条件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add-cond", method=RequestMethod.POST)
    @ResponseBody
    public String addCond(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			CCondition cCondition = (CCondition) buildObject(request, CCondition.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_CONDITION, LogConstant.ACTION_ADD);
			return cmsCouponService.addCondition(log,cCondition).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	

	/**
	 * 添加优惠券
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add-coupon", method=RequestMethod.POST)
    @ResponseBody
    public String addCoupon(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			logger.info("************************************ " + request.getParameter("expireTime"));
			Coupon coupon = (Coupon) buildObject(request, Coupon.class);
			String expireTime =  request.getParameter("expireTime");
			if (expireTime != null && !"".equals(expireTime)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				coupon.setExpireTime(dateFormat.parse(expireTime));
			}
			CStock cStock = (CStock) buildObject(request, CStock.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_COUPON, LogConstant.ACTION_ADD);
			return cmsCouponService.addCoupon(log, coupon,cStock).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 获取优惠券条件列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cond-batch", method= RequestMethod.GET)
    @ResponseBody
    public String condBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			CConditionBDTO dto = (CConditionBDTO) buildObject(request, CConditionBDTO.class);
			return cmsCouponService.findConditionBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	/**
	 * 获取监听事件列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/eve-tip", method= RequestMethod.GET)
    @ResponseBody
    public String eveTip(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsCouponService.findStockList().build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 获取优惠券条件列表,用于新增时的条件列
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/use-list", method= RequestMethod.GET)
    @ResponseBody
    public String condList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			CConditionBDTO dto = (CConditionBDTO) buildObject(request, CConditionBDTO.class);
			return cmsCouponService.findConditionList(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	/**
	 * 获取库存详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stock", method= RequestMethod.GET)
    @ResponseBody
    public String stock(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsCouponService.findStock(getParamStr(request, "couponTempId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 更新优惠券信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update-coupon", method= RequestMethod.POST)
    @ResponseBody
    public String updateCoupon(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Coupon coupon = (Coupon) buildObject(request, Coupon.class);
			String expireTime =  request.getParameter("expireTime");
			if (expireTime != null && !"".equals(expireTime)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				coupon.setExpireTime(dateFormat.parse(expireTime));
			}
			CStock cStock = (CStock) buildObject(request, CStock.class);
			return cmsCouponService.updateCoupon(coupon, cStock).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 查询优惠券列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/coupon-batch", method= RequestMethod.GET)
    @ResponseBody
    public String couponBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			CouponBTO dto = (CouponBTO) buildObject(request, CouponBTO.class);
			return cmsCouponService.findCouponBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	/**
	 * 审核优惠券
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/audit-coupon", method= RequestMethod.POST)
    @ResponseBody
    public String auditCoupon(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsCouponService.auditCoupon(getParamStr(request, "couponTempIds")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	/**
	 * 给学员发送优惠券
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/student-coupon", method= RequestMethod.GET)
    @ResponseBody
    public String studentCoupon(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsCouponService.genStudentCouponAndNotify(getParamStr(request, "couponTempId")
					,getParamLong(request, "studentId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 审核不过优惠券
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/unaudit-coupon", method= RequestMethod.POST)
    @ResponseBody
    public String unAuditCoupon(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsCouponService.unAuditCoupon(getParamStr(request, "couponTempIds")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 获取已发优惠券列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stucoupon-batch", method= RequestMethod.GET)
    @ResponseBody
    public String stucouponBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentCouponBDTO dto = (StudentCouponBDTO) buildObject(request, StudentCouponBDTO.class);
			return cmsCouponService.findStudentCouponBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 获取优惠券模板详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/coupon-view", method=RequestMethod.GET)
    @ResponseBody
    public String couponView(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return  cmsCouponService.findCoupon(getParamStr(request, "coupontmpid")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 延长有效期
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/prolong", method=RequestMethod.POST)
	@ResponseBody
	public String couponProlong(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return  cmsCouponService.prolongCoupon(getParamStr(request, "couponTmpId"), getParamStr(request, "expireTime")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 分组
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/groupCoupon", method=RequestMethod.POST)
	@ResponseBody
	public String groupCoupon(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return  cmsCouponService.groupCoupon(getParamStr(request, "couponTmpId"), getParamStr(request, "groupType")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
}
