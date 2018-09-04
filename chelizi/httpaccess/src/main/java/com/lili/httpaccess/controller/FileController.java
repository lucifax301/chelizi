package com.lili.httpaccess.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.VersionUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.configfile.vo.ConfigFileVo;
import com.lili.file.service.FileService;
import com.lili.file.service.PosterService;
import com.lili.pay.service.ChargeService;
import com.lili.pay.service.IBankVerifyService;
import com.lili.pay.service.IDepositService;
import com.lili.pay.service.PayService;
import com.lili.pay.service.PayServiceNew;
import com.lili.pay.vo.ChargeVo;
import com.lili.pay.vo.PayVo;
import com.lili.school.manager.SchoolManager;
import com.lili.school.manager.WechatSchoolManager;
import com.lili.school.vo.WechatEnrollClass;
import com.lili.school.vo.WechatEnrollStudent;
import com.lili.school.vo.WechatStudent;
import com.lili.student.service.StudentService;
import com.lili.student.vo.RechargeGearVo;
import com.lili.student.vo.RechargePlanVo;

@Controller
@RequestMapping("/v1/files")
public class FileController {
	@Autowired
	private FileService fileService;
	@Autowired
	private PayService payService;
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private IBankVerifyService iBankVerifyService;
	@Autowired
	private IDepositService iDepositService;
	@Autowired
	private PayServiceNew payServiceNew;
	@Autowired
	private PosterService posterService;
	@Autowired
	private SchoolManager schoolManager;
	@Autowired
	 private StudentService studentService;
	
	
	@Autowired
	private WechatSchoolManager wechatSchoolManager;

	private Logger log = LoggerFactory.getLogger(FileController.class);

	/**
	 * 获取上传照片的上传令牌
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/upToken", method = RequestMethod.GET)
	@ResponseBody
	public Object getUptoken(@RequestParam String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = new ReqResult();

		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = fileService.getUptoken(userId,userType,tokenId);

		} catch (Exception e) {
			log.error("controller: file get upToken failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 获取公共上传照片的上传令牌
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/upPublicToken", method = RequestMethod.GET)
	@ResponseBody
	public Object getUpPublicToken(@RequestParam String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = new ReqResult();
		
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = fileService.getUpPublicToken(userId,userType,tokenId);
			
		} catch (Exception e) {
			log.error("controller: file get upToken failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}

	@RequestMapping(value = "/downUrl", method = RequestMethod.GET)
	@ResponseBody
	public Object getDowntoken(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String picType, @RequestParam String timestamp,@RequestParam(required=false) String style,
			@RequestParam(required=false) String carId,
			@RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = fileService.getDownUrl(userId,userType, picType, style, carId, false);

		} catch (Exception e) {
			log.error("controller: file get downurl failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 根据文件名获取图片下载地址
	 * @param userId
	 * @param userType
	 * @param picName
	 * @param style	图片样式，如：imageView2/1/w/200/h/200
	 * @param timestamp
	 * @param sign
	 * @param v
	 * @return
	 */
	@RequestMapping(value = "/downUrlByKey", method = RequestMethod.GET)
	@ResponseBody
	public Object downUrlByKey(@RequestParam(required=false) String userId,@RequestParam(required=false) String userType,
			@RequestParam String picName, @RequestParam(required=false) String style,@RequestParam String timestamp,
			@RequestParam String sign, @RequestParam(required=false) String v) {
		ReqResult r = new ReqResult();
		try {
			r = fileService.getDownUrlByKey(userId, userType, picName, style);

		} catch (Exception e) {
			log.error("controller: file get downurl failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取大客户充值优惠的列表信息
	 * @param userId 用户id
	 * @param userType 用户类型
     *
     * @return
     *
     */
	@RequestMapping(value = "/getChargeDiscountList", method = RequestMethod.GET)
	@ResponseBody
    @Deprecated
	public Object getChargeDiscountList(@RequestParam String userId,
                                        @RequestParam String userType
    ) {
		return ReqResult.getSuccess();
	}

	/**
	 * 第三方接口：微信回调接口
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/wxPayCallBack", method = RequestMethod.POST)
	@ResponseBody
	public Object wxPayCallBack(@RequestParam String result) {
		ReqResult r = new ReqResult();
		try {
			r = payService.wxPayCallBack(result);

		} catch (Exception e) {
			log.error("controller: file post wxPayCallBack failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	/**
	 * 第三方接口：支付宝回调接口
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/zfbPayCallBack", method = RequestMethod.POST)
	@ResponseBody
	public Object zfbPayCallBack(Map<String, String> params) {
		ReqResult r = new ReqResult();
		try {
			r = payService.zfbPayCallBack(params);
			if(r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
				return "SUCCESS";
			}
		} catch (Exception e) {
			log.error("controller: file post zfbPayCallBack failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取城市信息
	 * @param userId
	 * @param userType
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	@ResponseBody
	public Object getRegion(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String level){
		ReqResult r = new ReqResult();
		try {
			r = fileService.getRegion(level);
		} catch (Exception e) {
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);

            log.error("controller: file post getRegion failed=" + e.getMessage(), e);
        }

		return r.getResult();
	}

	/**
	 * 添加客户端设备信息
	 * @return
	 */
	@RequestMapping(value = "/devices", method = RequestMethod.POST)
	@ResponseBody
	public Object addDevice(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String osType,@RequestParam(required=false) String osv,
			@RequestParam(required=false) String deviceType,@RequestParam(required=false) String imei,
			@RequestParam(required=false) String mac,@RequestParam(required=false) String imsi,
			@RequestParam(required=false) String mobile,@RequestParam(required=false) String ca,
			@RequestParam(required=false) String ac,@RequestParam(required=false) String lge,
			@RequestParam(required=false) String lae,@RequestParam(required=false) String appPackName,
			@RequestParam(required=false) String appVersion,@RequestParam(required=false) String appCode,
			@RequestParam String jpush,@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();
		try {
			r = fileService.addDevice(userId,userType,osType,osv,deviceType,imei,
					mac,imsi,mobile,ca,ac,lge,lae,appPackName,appVersion,appCode,jpush);

		} catch (Exception e) {
			log.error("controller: file post devices failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 客户端拉取基本数据
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/basic", method = RequestMethod.GET)
	@ResponseBody
	public Object getBasic(@RequestParam(required=false) String userId,
			@RequestParam(required=false) String userType, @RequestParam(required=false) String mtime,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = fileService.getConfigs(userId, userType,mtime);
		} catch (Exception e) {
			log.error("controller: file getBasic failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}


	// -------------------------------------APP的名称“喱喱训练场”-----
	//--------------------------------------专用接口，不需要登录-------

	/**
	 * 获取区域
	 * @param level
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/app2/regions", method = RequestMethod.GET)
	@ResponseBody
	public Object getRegions(@RequestParam String level,@RequestParam String timestamp,
			@RequestParam String sign){
		ReqResult r = new ReqResult();
		try {
			r = fileService.getRegion(level);
		} catch (Exception e) {
			log.error("controller: file getRegions failed=" + e.getMessage(), e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取训练场
	 * @param imei
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/app2/trfields", method = RequestMethod.GET)
	@ResponseBody
	public Object getTrfields(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String rid,
			@RequestParam String imei,@RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = new ReqResult();

		try {
			r = fileService.getTrfields(keyword,rid,imei);
		} catch (Exception e) {
			log.error("controller: file getTrfields failed=" + e.getMessage(), e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 标记训练场
	 * @param tid 训练场id
	 * @param lge
	 * @param lae
	 * @param osType
	 * @param osv
	 * @param deviceType
	 * @param imei
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/app2/trfields", method = RequestMethod.POST)
	@ResponseBody
	public Object postTrfields(@RequestParam String tid,@RequestParam String lge,
			@RequestParam String lae,@RequestParam(required=false) String posDesc,@RequestParam String osType,
			@RequestParam(required=false) String osv,@RequestParam(required=false) String deviceType,
			@RequestParam String imei,@RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = new ReqResult();

		try {
			r = fileService.postTrfields(tid,lge,lae,posDesc,osType,osv,deviceType,imei);
		} catch (Exception e) {
			log.error("controller: file postTrfields failed=" + e.getMessage(), e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 通用获取配置文件接口
	 * 缓存统一命名：config.file.1/2.fileName.type
	 * config.file. 统一开头，1-教练，2-学员，fileName-可以是城市ID，微课，type -1通用，2-微课
	 * @param cityId 城市
	 * @param userId
	 * @param userType
	 * @param menu 菜单
	 * @param type  1-广告；2-限时特惠；3-首页菜单；11-科目二自学技巧；12-科目二视频教程；13-科目三自学技巧；14-科目三视频教程
	 * @param channel 1-通用；2-广告
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/app2/configFile", method = RequestMethod.GET)
	@ResponseBody
	public Object getConfigFile(@RequestParam String cityId, @RequestParam(required=false) String userId, @RequestParam(required=false) String userType, 
			@RequestParam(required=false) String menu,@RequestParam (required=false) String type,@RequestParam (required=false) String channel,
			@RequestParam (required=false) String v,
			@RequestParam String timestamp,	@RequestParam String sign) {
		ReqResult r = new ReqResult();
		
		try {
			r = fileService.getConfigFile(cityId,userId,userType,menu,type, channel);
			
			// 20161026学员端 过滤低版本驾校点评
			if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "2.1.0") < 0)) && userType != null && "2".equals(userType)) {
				@SuppressWarnings("unchecked")
				List<ConfigFileVo> configFileList = (List<ConfigFileVo>) r.getResult().get(ResultCode.RESULTKEY.DATA);
				List<ConfigFileVo> configFileNewList = new ArrayList<ConfigFileVo>();
				for (ConfigFileVo configFileVo : configFileList) {
					if (configFileVo.getId() != null && "30667".equals(configFileVo.getId())) { //驾校点评的ID值固定为30667
						// do nothing
					}
					else {
						configFileNewList.add(configFileVo);
					}
				}
				r.setData(configFileNewList);
			}
			
		} 
		catch (Exception e) {
			log.error("controller: file getConfig failed=" + e.getMessage(), e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}

	/**
	 * 标记新训练场 app
	 * @param name
	 * @param address
	 * @param school
	 * @param province
	 * @param city
	 * @param district
	 * @param baiduaddr
	 * @param lge
	 * @param lae
	 * @param mobile
	 * @param imei
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/app3/trfields", method = RequestMethod.POST)
	@ResponseBody
	public Object post3Trfields(@RequestParam String name,@RequestParam String address,@RequestParam String school,
			@RequestParam String province,@RequestParam String city,@RequestParam String district,
			@RequestParam String baiduaddr,
			@RequestParam String lge,
			@RequestParam String lae,@RequestParam String mobile,
			@RequestParam String imei,@RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = new ReqResult();

		try {
			r = fileService.post3Trfields(name,address,school,province,city,district,baiduaddr,lge,lae,mobile,imei);
		} catch (Exception e) {
			log.error("controller: file postTrfields failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员/教练充值
	 * @param chargeValue
	 * @param chargeType
	 * @param bankCard
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/charge", method = RequestMethod.POST)
	@ResponseBody
	public Object charge(@RequestParam String chargeValue,@RequestParam String chargeType,@RequestParam String bankCard,
			@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();

		ChargeVo chargeVo = new ChargeVo();
		chargeVo.setBankCard(bankCard);
		chargeVo.setChargeId(StringUtil.getOrderId());
		chargeVo.setChargeType(chargeType);
		chargeVo.setChargeValue(Integer.parseInt(chargeValue));
		chargeVo.setUserId(Long.parseLong(userId));
		chargeVo.setUserType(Integer.parseInt(userType));

		try {
			r = chargeService.charge(chargeVo, null);
		} catch (Exception e) {
			log.error("controller: file charge failed=" + e.getMessage(), e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}


		return r.getResult();
	}

	/**
	 * 通用支付接口
	 * @param payValue
	 * @param payWay
	 * @param payOrderId
	 * @param payPurpose
	 * @param couponId
	 * @param clientVer
	 * @param userId
	 * @param userType
	 * @param remark
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ResponseBody
	public Object pay(@RequestParam String payValue,@RequestParam String payWay,
			@RequestParam String payOrderId,@RequestParam String payPurpose,
			@RequestParam String couponId,@RequestParam String clientVer,
			@RequestParam String userId,@RequestParam String userType,
			@RequestParam String remark,@RequestParam(required = false) String v,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestHeader HttpHeaders headers,@RequestParam(required = false) String insuranceId){
		ReqResult r = new ReqResult();
		try {
			String agent = headers.getFirst("User-Agent");
			PayVo pvo = new PayVo(
					Long.parseLong(userId),
					Integer.parseInt(userType),
					Integer.parseInt(payValue),
					payWay,
					payOrderId,
					Integer.parseInt(payPurpose),
					Long.parseLong(couponId),
					Integer.parseInt(clientVer),
					remark,insuranceId
			);
			
			/**
			 * 校验教练的版本是否低于2.0，教练端的微信手续费2%问题
			 */
//			if (Integer.parseInt(userType) == 1) {
//				if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "2.1.0") < 0))) { //版本低于2.1
//					r.setCode(ResultCode.ERRORCODE.EXCEPTION);
//					r.setMsgInfo("请更新到最新版本。");
//					return r.getResult();
//				}
//			}
//			
			if (Integer.parseInt(payPurpose) == 2){
				if (agent.contains("okhttp"))
					schoolManager.postEnrollSource("Android", payOrderId);
				else if (agent.contains("iPhone"))
					schoolManager.postEnrollSource("iPhone", payOrderId);
				else
					schoolManager.postEnrollSource("WeChat", payOrderId);
			}
			//20161102学员充值的时候判断下版本是否低版本，是否有充值送券的情况，提示升级版本
			//增加时间限制
			Date test=TimeUtil.parseDate("2016-11-11 00:00:00", "yyyy-MM-dd hh:mm:ss");
			Date date=new Date();
			if(date.after(test)) { //现在时间比开放时间大
				if (Integer.parseInt(payPurpose) == 1) { //条件1：充值
					if(Integer.parseInt(userType) == 2) {   //条件2: 学员
						ReqResult reqResult = studentService.getUserInfo(userId, null); //获取充值送方案
						RechargePlanVo rechargePlanVo = (RechargePlanVo) reqResult.getResult().get("RechargePlan");
						if (rechargePlanVo != null && rechargePlanVo.getVstate() != null && rechargePlanVo.getVstate() ==1) { //有充值送方案且审核通过
							if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "2.1.0") < 0))) { //版本低于2.1
								//判断是否有送券
								if (rechargePlanVo.getRechargeGearList() != null && rechargePlanVo.getRechargeGearList().size() > 0) {
									List<RechargeGearVo> rechargeGearList = rechargePlanVo.getRechargeGearList();
									for (RechargeGearVo rechargeGearVo : rechargeGearList) {
										if(rechargeGearVo.getCouponId() != null && !"".equals(rechargeGearVo.getCouponId()) ) {
											r.setCode(ResultCode.ERRORCODE.VERSION_IS_LOW);
											r.setMsgInfo(ResultCode.ERRORINFO.VERSION_IS_LOW);
											return r.getResult();
										}
									}
								}
							}
						}
					}
				}
			}
			r = payServiceNew.pay(pvo);
		} catch (Exception e) {
			log.error("controller: file pay failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}


		return r.getResult();
	}
	
	@RequestMapping(value = "/payWxCoach", method = RequestMethod.POST)
	@ResponseBody
	public Object payWxCoach(@RequestParam String payValue,@RequestParam String payWay,
			@RequestParam String payOrderId,@RequestParam String payPurpose,
			@RequestParam(required = false)  String couponId,@RequestParam String clientVer,
			@RequestParam String openId,@RequestParam String classId,@RequestParam String drtype,
			@RequestParam String remark,@RequestParam(required = false) String v,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestHeader HttpHeaders headers,@RequestParam(required = false) String insuranceId){
		ReqResult r = new ReqResult();
		try {
			String agent = headers.getFirst("User-Agent");
			WechatEnrollClass recordClass = new WechatEnrollClass();
			recordClass.setClassId(Integer.parseInt(classId));
			WechatEnrollClass wechatEnrollClass = wechatSchoolManager.queryWechatEnrollClass(recordClass );
			Integer payPrice  =0;
			if (wechatEnrollClass != null) {
				if (wechatEnrollClass.getDrtype() == Integer.parseInt(drtype)) {
					if (wechatEnrollClass.getPrePrice() != null && !"".equals(wechatEnrollClass.getPrePrice())) {
						payPrice = wechatEnrollClass.getPrePrice();
					}
					else {
						if (wechatEnrollClass.getPrice() != null && !"".equals(wechatEnrollClass.getPrice())) {
							payPrice = wechatEnrollClass.getPrice();
						}
						else {
							r.setCode(ResultCode.ERRORCODE.EXCEPTION);
							r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
							return r;
						}
					}
				}
				else if (wechatEnrollClass.getDrtype2() == Integer.parseInt(drtype)) {
					if (wechatEnrollClass.getPrePrice2() != null && !"".equals(wechatEnrollClass.getPrePrice2())) {
						payPrice = wechatEnrollClass.getPrePrice2();
					}
					else {
						if (wechatEnrollClass.getPrice2() != null && !"".equals(wechatEnrollClass.getPrice2())) {
							payPrice = wechatEnrollClass.getPrice2();
						}
						else {
							r.setCode(ResultCode.ERRORCODE.EXCEPTION);
							r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
							return r;
						}
					}
				}
				else {
					r.setCode(ResultCode.ERRORCODE.EXCEPTION);
					r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
					return r;
				}
			}
			else {
				r.setCode(ResultCode.ERRORCODE.EXCEPTION);
				r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
				return r;
			}
			
			WechatEnrollStudent record = new WechatEnrollStudent();
        	record.setOrderId(payOrderId);
        	record.setIsdel(0);
        	record.setDrtype(Integer.parseInt(drtype));
        	record.setPayPrice(payPrice);
        	record.setPrice(Integer.parseInt(payValue));
			wechatSchoolManager.updateWechatEnrollStudent(record);
			
			WechatStudent wechatStudent = new WechatStudent();
			wechatStudent.setOpenId(openId);
			WechatStudent studentInfo= wechatSchoolManager.queryWechatStudent(wechatStudent );
			if (studentInfo != null) {
				PayVo pvo = new PayVo(
						studentInfo.getStudentId(),
						2,
						payPrice,
						payWay,
						payOrderId,
						Integer.parseInt(payPurpose),
						Long.parseLong(couponId),
						Integer.parseInt(clientVer),
						remark,insuranceId
						);
				if (Integer.parseInt(payPurpose) == 2){
					if (agent.contains("okhttp"))
						schoolManager.postEnrollSource("Android", payOrderId);
					else if (agent.contains("iPhone"))
						schoolManager.postEnrollSource("iPhone", payOrderId);
					else
						schoolManager.postEnrollSource("WeChat", payOrderId);
				}
				r = payServiceNew.pay(pvo);
			}
		} catch (Exception e) {
			log.error("controller: file pay failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		
		return r.getResult();
	}
	
	/**
	 * 获取我的银行卡
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/banks", method = RequestMethod.GET)
	@ResponseBody
	public Object getBanks(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();

		try {
			r = iBankVerifyService.queryBoundBankCard(userId, userType, timestamp, sign);
		} catch (Exception e) {
			log.error("controller: file banks get failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 通过银行卡号获取卡片信息
	 * @param bankCard
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/bankinfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getBankInfo(@RequestParam String bankCard,
			@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();

		try {
			r = iBankVerifyService.getBankInfo(bankCard);
		} catch (Exception e) {
			log.error("controller: file banks failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 绑定银行卡
	 * @param bankName
	 * @param bankCard
	 * @param name
	 * @param mobile
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/banks", method = RequestMethod.POST)
	@ResponseBody
	public Object bindBank(@RequestParam String bankName,@RequestParam String bankCard,@RequestParam String name,
			@RequestParam String mobile,@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();

		try {
			r = iBankVerifyService.boundBankCard(bankName, bankCard, name, mobile, userId, userType, timestamp, sign);
		} catch (Exception e) {
			log.error("controller: file banks failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 是否已绑定该银行卡
	 * @param bankName
	 * @param bankCard
	 * @param name
	 * @param mobile
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/valbanks", method = RequestMethod.POST)
	@ResponseBody
	public Object validBindBank(@RequestParam String bankCard,@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();
		
		try {
			r = iBankVerifyService.validBindBank( bankCard, userId, userType);
		} catch (Exception e) {
			log.error("controller: file banks failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	/**
	 * 解除绑定银行卡
	 * @param pw
	 * @param bankCard
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/ubanks", method = RequestMethod.POST)
	@ResponseBody
	public Object unbindBank(@RequestParam String pw,@RequestParam String bankCard,
			@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();

		try {
			r = iBankVerifyService.removeBoundBankCard(pw, bankCard, userId, userType, timestamp, sign);
		} catch (Exception e) {
			log.error("controller: file ubanks failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 设置支付密码
	 * @param pw
	 * @param userId
	 * @param userType
	 * @param reqType  0-设置密码；1-修改密码
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/pw", method = RequestMethod.POST)
	@ResponseBody
	public Object payPass(@RequestParam String pw,
			@RequestParam String userId,@RequestParam String userType,
			@RequestParam String reqType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();

		try {
			r = iBankVerifyService.setPassword(pw, userId, userType,reqType, timestamp, sign);
		} catch (Exception e) {
			log.error("controller: file pw failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}

	/**
	 * 验证支付密码
	 * @param pw
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/pwVerify", method = RequestMethod.POST)
	@ResponseBody
	public Object pwVerify(@RequestParam String pw,
			@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();

		try {
			r = iBankVerifyService.validPassword(pw, userId, userType,timestamp,sign);
		} catch (Exception e) {
			log.error("controller: file pw failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}

	/**
	 * 提现
	 * @param pw
	 * @param money
	 * @param type 默认bank银行卡 wexin:微信 zhifubao:支付宝 qqwallet:QQ钱包
	 * @param bankCard
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/wd", method = RequestMethod.POST)
	@ResponseBody
	public Object withdraw(@RequestParam String pw,@RequestParam String money,
			@RequestParam String type,@RequestParam String bankCard,
			@RequestParam String userId,@RequestParam String userType,@RequestParam(required = false) String v,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();

		try {
			/**
			 * 校验教练的版本是否低于2.0，教练端的微信手续费2%问题
			 */
			if (Integer.parseInt(userType) == 1) {
				if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "2.1.0") < 0))) { //版本低于2.1
					r.setCode(ResultCode.ERRORCODE.EXCEPTION);
					r.setMsgInfo("请更新到最新版本。");
					return r.getResult();
				}
			}
			
			r = iDepositService.deposit(pw, money, type, bankCard, userId, userType, timestamp, sign);
		} catch (Exception e) {
			log.error("controller: file wd failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}

	/**
	 * 提现剩余次数
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/wdCount", method = RequestMethod.GET)
	@ResponseBody
	public Object withdrawCount(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();

		try {
			r = iDepositService.leftDepositCount(userId, userType, timestamp, sign);
		} catch (Exception e) {
			log.error("controller: file wdCount failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}

	@RequestMapping(value = "/wdDetail", method = RequestMethod.GET)
	@ResponseBody
	public Object withdrawDetail(@RequestParam String pageNo,@RequestParam String pageSize,
			@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();

		try {
			r = iDepositService.queryDepositList(pageNo, pageSize, userId, userType, timestamp, sign);
		} catch (Exception e) {
			log.error("controller: file wdDetail failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}

	/**
	 * 获取开机广告
	 * @param userType
	 * @return
	 */
	@RequestMapping(value = "/poster", method = RequestMethod.GET)
	@ResponseBody
	public Object getPoster(@RequestParam(required=false) String userType){
		ReqResult r = new ReqResult();

		try {
			r = posterService.getPoster(userType);
		} catch (Exception e) {
			log.error("controller: file getPoster failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	/**
	 * 设置添加开机广告
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
/*	@RequestMapping(value = "/poster", method = RequestMethod.POST)
	@ResponseBody
	public Object postPoster(@RequestParam String posterName,@RequestParam String posterPic,
			@RequestParam String posterPic2,@RequestParam String posterPic3,
			@RequestParam String posterUrl,@RequestParam String extra,
			@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = new ReqResult();
		PosterVo pos = new PosterVo();
		pos.setPostername(posterName);
		pos.setPosterpic(posterPic);
		pos.setPosterpic2(posterPic2);
		pos.setPosterpic3(posterPic3);
		pos.setPosterurl(posterUrl);
		pos.setExtra(extra);
		try {
			r = fileService.postPoster(pos);
		} catch (Exception e) {
			log.error("controller: file postPoster failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}*/


	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	@ResponseBody
	public Object postFeedback(@RequestParam String fbtitle,@RequestParam String fbcontent,
			@RequestParam String fbpicture,
			@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign){
		ReqResult r = ReqResult.getSuccess();

		try {
			r = fileService.postFeedback(fbtitle,fbcontent,fbpicture,userId,userType);
		} catch (Exception e) {
			log.error("controller: file postFeedback failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 根据分类ID获取分类标题列表
	 * @param typeId
	 * @param channel 渠道：1-教练；2-学员
	 * @param name 分类名
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/titel", method = RequestMethod.GET)
    @ResponseBody
    public Object getTitel(@RequestParam(required=false) String typeId,@RequestParam(required=false) String channel,@RequestParam(required=false) String name,
                              @RequestParam String timestamp,@RequestParam String sign, @RequestHeader HttpHeaders headers) {
        ReqResult r = ReqResult.getSuccess();
        try {
            r = fileService.getTitel(typeId, channel, name);

        } catch (Exception e) {
            log.error("controller: file  getTitel failed=" + e.getMessage(), e);
            e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return r.getResult();
    }
	
	/**
	 * 根据标题ID获取文章内容
	 * @param id 
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/content", method = RequestMethod.GET)
	@ResponseBody
	public Object getContent(@RequestParam String id,
			@RequestParam String timestamp,@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = fileService.getContent(id);
			
		} catch (Exception e) {
			log.error("controller: file getContent failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}

}
