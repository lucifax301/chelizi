package com.lili.httpaccess.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lili.authcode.service.AuthcodeService;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.service.CouponService;
import com.lili.share.service.GiftService;
import com.lili.share.service.IChannelService;
import com.lili.share.service.IShareService;
import com.lili.share.service.IShareUserService;
import com.lili.share.service.WechatService;
import com.lili.student.service.RechargeService;
import com.lili.student.vo.RechargePlanVo;
import com.lili.student.vo.VipCompanyQuery;
import com.lili.student.vo.VipCompanyVo;
import com.lili.student.vo.VipCustomVo;

@Controller
@RequestMapping("/")
public class ShareController {
	Logger log = Logger.getLogger(ShareController.class);
	
	@Autowired
	private IShareService shareService;
	
	@Autowired
	private WechatService wechatService;
	
	@Autowired
	private IShareUserService shareUserService;
	
	@Autowired
	private IChannelService channelService;
	@Autowired
	private GiftService giftService;
	
	@Autowired
	private   RechargeService rechargeService;
	
    @Autowired
    private AuthcodeService authcodeService;
    
    @Autowired
    private CouponService couponService;
    
	
	/**
	 * 获得链接对象，关键是suid和sendType及userId
	 * @param userId
	 * @param userType
	 * @return 核心数据是ShareUserVo,对于教练分享需添加userId
	 * 通过该对象，最终构建是http://www.lilixc.com/invite/invite.html?suid=XX&userType=sentType&sendUser=userID
	 */
	@RequestMapping(value = "/v1/share/shareUser", method = RequestMethod.GET)
	public  Object shareUser(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = shareUserService.getShareUser(userId, userType);
		} 
		catch (Exception e) {
			log.error("controller: Share2User  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 查询自己的分享列表
	 * @param userId
	 * @param userType：自己的用户类型
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/v1/share/shareList", method = RequestMethod.GET)
	public  Object shareList(@RequestParam String userId,@RequestParam String userType,@RequestParam String pageSize,@RequestParam String pageIndex,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = shareService.getMineShareList(userId, userType, pageSize, pageIndex);
		} 
		catch (Exception e) {
			log.error("controller: Share2User  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 添加一条share记录，
	 * 首先需要判断sendType,如果>100,直接写入channel
	 *               否则写入share表，这就首先需要表判断该接收的手机号码是否存在，其次需要判断该手机号码是否已经注册
	 * @return
	 */
	@RequestMapping(value = "/open/share/share2User", method = RequestMethod.POST)
	public  Object share2User(@RequestParam(required=false) String userId, @RequestParam(required=false)  String recevieName, @RequestParam String sendType, @RequestParam String suid, @RequestParam String receviePhone, @RequestParam(required=false) String userType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = channelService.addChannelInfo(userId, recevieName, sendType, suid, receviePhone, userType);
		} 
		catch (Exception e) {
			log.error("controller: Share2User  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 校园代理拉取分享链接
	 * 1. 判断suid是否存在，
	 * 2.如suid存在，判断手机号是否已存在，
	 * 				如手机号存在，则校验手机号，如手机号正确，则拉取分享链接
	 * 									如手机号不正确return
	 * 				如手机号不存在，则更新手机号、更新审核状态为2审核通过,拉取分享链接
	 * 3.如suid不存在，则return；
	 * @param suid
	 * @param sendPhone
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/open/share/schoolShareUser", method = RequestMethod.GET)
	public  Object schoolShareUser(@RequestParam String suid, @RequestParam String sendPhone, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = shareUserService.getSchoolShareUser(suid, sendPhone);
		} 
		catch (Exception e) {
			log.error("controller: Share2User  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	@RequestMapping(value = "/open/share/bigCustomer", method = RequestMethod.POST)
	public  Object bigCustomer(@RequestParam String name, @RequestParam String phone, @RequestParam String companyName, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = channelService.addBigCustomer(name, phone, companyName);
		} 
		catch (Exception e) {
			log.error("controller: bigCustomer  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 获取门票已领个数，剩余可领个数
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/open/gift/count", method = RequestMethod.GET)
	public  Object getGiftCount(@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = giftService.getGiftCount();
		} 
		catch (Exception e) {
			log.error("controller: gift count failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	/**
	 * 最近领券情况
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/open/gift/recent", method = RequestMethod.GET)
	public  Object getGiftRecent(@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = giftService.getGiftRecent();
		} 
		catch (Exception e) {
			log.error("controller: gift count failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	/**
	 * 提交礼券申请
	 * @param name
	 * @param mobile
	 * @param couponId
	 * @param address
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/open/gift", method = RequestMethod.POST)
	public  Object getGiftRecent(@RequestParam String name, @RequestParam String mobile,
			@RequestParam String couponId, @RequestParam String address,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = giftService.postGift(name,mobile,couponId,address);
		} 
		catch (Exception e) {
			log.error("controller: gift count failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	
	/*********************************微信渠道统计接口**************************************/
	//	<xml><ToUserName><![CDATA[toUser]]></ToUserName>
	//	<FromUserName><![CDATA[FromUser]]></FromUserName>
	//	<CreateTime>123456789</CreateTime>
	//	<MsgType><![CDATA[event]]></MsgType>
	//	<Event><![CDATA[subscribe]]></Event>
	//	<EventKey><![CDATA[qrscene_123123]]></EventKey>
	//	<Ticket><![CDATA[TICKET]]></Ticket>
	//	</xml>
	@RequestMapping(value = "/open/wechat", method = RequestMethod.POST)
	public Object wechat(@RequestBody String result) {
        // 将要提交给API的数据对象转换成XML格式数据Post给API
		ReqResult r = new ReqResult();
		try {
			r = wechatService.wechatCallback(result);
		} 
		catch (Exception e) {
			log.error("controller: wechat callback failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION); 
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	
	/**
	 *  扫码部分：大客户录入，有几种情况： 1.已是大客户学员 2.是已注册学员，但非vip 3.是未注册学员
	 * 		canOld: 是否可以将老客户标记为大客户扫码的接口 canold要传false
	 * @return
	 */
	@RequestMapping(value = "/open/share/addVipCustom", method = RequestMethod.POST)
	public  Object addVipCustom(@RequestParam(required=false) String studentId, @RequestParam(required=false) String rcid,
			@RequestParam String mobile, @RequestParam String cname, @RequestParam String codeInput, @RequestParam(required=false) String inviteCode, 
			@RequestParam String cid, @RequestParam(required=false) String coid, @RequestParam(required=false) String couponTmpId, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			//验证手机、验证码
			ReqResult ar = authcodeService.isCodeExist(codeInput, mobile, null, "2");
			if(ar.isSuccess()){
				//如果验证成功
				VipCustomVo vipCustomVo = new VipCustomVo();
				if(studentId != null && !"".equals(studentId)) {
					vipCustomVo.setStudentId(Long.parseLong(studentId));
				}
				if(coid != null && !"".equals(coid)) {
					vipCustomVo.setCoid(Integer.parseInt(coid));
				}
				if(rcid != null && !"".equals(rcid)) {
					vipCustomVo.setRcid(Integer.parseInt(rcid));
				}
				if(couponTmpId != null && !"".equals(couponTmpId)) {
					vipCustomVo.setCouponTmpId(couponTmpId);
				}
				if(inviteCode != null && !"".equals(inviteCode)) {
					vipCustomVo.setInviteCode(inviteCode);
				}
				vipCustomVo.setMobile(mobile);
				vipCustomVo.setCname(cname);
				vipCustomVo.setCid(cid);
				String code = rechargeService.addVipCustom(vipCustomVo , false);
				if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
					r.setCode(ResultCode.ERRORCODE.SUCCESS);
					r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
				}
				else {
					r.setCode(code);
					r.setMsgInfo(ResultCode.getCodeInfo(code));
				}
			}
			else{
				r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);//验证码错误
			}
			

		} 
		catch (Exception e) {
			log.error("controller: addVipCustom  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 给大客户员工添加或更新套餐，注意已审核的不可变更
	 * @return
	 */
	@RequestMapping(value = "/open/share/selectRechargePlan", method = RequestMethod.POST)
	public  Object selectRechargePlan(@RequestParam String mobile, @RequestParam  String rcid,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			String code =  rechargeService.selectRechargePlan(mobile, Integer.parseInt(rcid));
			if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
				r.setCode(ResultCode.ERRORCODE.SUCCESS);
				r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			}
			else {
				r.setCode(code);
				r.setMsgInfo(ResultCode.getCodeInfo(code));
			}
		} 
		catch (Exception e) {
			log.error("controller: selectRechargePlan  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 返回充值送方案详情
	 * @return
	 */
	@RequestMapping(value = "/open/share/rechargeRecordInfo", method = RequestMethod.GET)
	public  Object rechargeRecordInfo(@RequestParam  String rcid,@RequestParam(required=false) String vtype, @RequestParam(required=false) String vstate,
			@RequestParam(required=false) String rcname, @RequestParam(required=false) String company,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			RechargePlanVo rechargePlanVo =  rechargeService.getRechargePlan(Integer.parseInt(rcid));
			r.setData(rechargePlanVo);
		} 
		catch (Exception e) {
			log.error("controller: selectRechargePlan  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 返回充值送方案列表
	 * @return
	 */
	@RequestMapping(value = "/open/share/rechargePlanList", method = RequestMethod.GET)
	public  Object getRechargePlanList(@RequestParam String pageSize,
			@RequestParam String pageNo, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			RechargePlanVo search = new RechargePlanVo();
			List<RechargePlanVo> rechargerPlanList=  rechargeService.getRechargePlanList(search, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
			r.setData(rechargerPlanList);
		} 
		catch (Exception e) {
			log.error("controller: selectRechargePlan  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 返回大客户公司信息
	 * @return
	 */
	@RequestMapping(value = "/open/share/getCompayList", method = RequestMethod.GET)
	public  Object getCompayList(@RequestParam(required=false) String coid, @RequestParam(required=false) String pageSize,
			@RequestParam(required=false) String pageIndex, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			VipCompanyQuery vipCompanyQuery = new VipCompanyQuery();
			VipCompanyVo vipCompanyVo = new VipCompanyVo();
			if (coid != null && !"".equals(coid)) {
				vipCompanyVo.setCoid(Integer.parseInt(coid));
			}
			if (pageSize != null && !"".equals(pageSize)) {
				vipCompanyQuery.setPageSize(Integer.parseInt(pageSize));
			}
			if (pageIndex != null && !"".equals(pageIndex)) {
				vipCompanyQuery.setPageIndex(Integer.parseInt(pageIndex));
			}
			List<VipCompanyVo> vipCustomVoList =  rechargeService.queryByNew0(vipCompanyVo, vipCompanyQuery);
			r.setData(vipCustomVoList);
		} 
		catch (Exception e) {
			log.error("controller: getCompayList  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	
	
}
