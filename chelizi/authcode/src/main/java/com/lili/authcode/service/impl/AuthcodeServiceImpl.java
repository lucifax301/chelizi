package com.lili.authcode.service.impl;

import io.rong.RongCloud;
import io.rong.models.TokenReslut;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyuncs.exceptions.ClientException;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.authcode.service.AuthcodeService;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.util.MobileUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.file.service.FileService;
import com.lili.smsmessage.service.impl.SMSMessageerviceImpl;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class AuthcodeServiceImpl implements AuthcodeService {
	private static Logger logger = LoggerFactory.getLogger(AuthcodeServiceImpl.class);
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	CoachManager coachManager;
	@Autowired
	StudentManager studentManager;
	@Autowired
	NoticeManager noticeManager;
//	@Value("${authcode.env}")
//	private String authcodeEnv = "";
	@Resource(name="authcodeProp")
	private Properties authcodeProp;
	@Autowired
	private FileService fileService;
	
	@Autowired
	SMSMessageerviceImpl sms;

	@Override
	public ReqResult getAuthcode(String mobile, String userType, String reqType)
			throws Exception {

		ReqResult r = ReqResult.getSuccess();
		String mo = mobile.trim();
		// 手机号码格式错误
		if (!MobileUtil.isMobile(mo)) {
			r.setCode(ResultCode.ERRORCODE.MOBILE_NUMBER_ERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.MOBILE_NUMBER_ERROR);
			return r;
		}

		boolean cExist = coachManager.isExist(mo);
		//boolean sExist = studentManager.isExist(mo);
		int ut = 0;
		int rt = 0;
		try {
			ut = Integer.parseInt(userType);
			rt = Integer.parseInt(reqType);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r;
		}
		if (rt == ReqConstants.REQ_TYPE_REGISTER) {
			if (ut == ReqConstants.USER_TYPE_COACH) {
				if (cExist) {
					r.setCode(ResultCode.ERRORCODE.MOBILE_EXIST);
					r.setMsgInfo(ResultCode.ERRORINFO.MOBILE_EXIST);
					return r;
				}
			} else if (ut == ReqConstants.USER_TYPE_STUDENT) {
//				if (sExist) {
//					r.setCode(ResultCode.ERRORCODE.MOBILE_EXIST);
//					r.setMsgInfo(ResultCode.ERRORINFO.MOBILE_EXIST);
//					return r;
//				}
			} else {
				// 参数错误
				r.setCode(ResultCode.ERRORCODE.PARAMERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
				return r;
			}

			// 2--密码找回流程验证码
		} else if (rt == ReqConstants.REQ_TYPE_FIND_PASSWORD) {
			if (ut == ReqConstants.USER_TYPE_COACH) {
				if (!cExist) {
					r.setCode(ResultCode.ERRORCODE.NO_USER);
					r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
					return r;
				}
			} else if (ut == ReqConstants.USER_TYPE_STUDENT) {
//				if (!sExist) {
//					r.setCode(ResultCode.ERRORCODE.NO_USER);
//					r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
//					return r;
//				}
			} else {
				// 参数错误
				r.setCode(ResultCode.ERRORCODE.PARAMERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
				return r;
			}
			// 3--通过手机短信验证登录
		} else if (rt == ReqConstants.REQ_TYPE_LOGIN) {
			if (ut == ReqConstants.USER_TYPE_COACH) {
				if (!cExist) {
					r.setCode(ResultCode.ERRORCODE.NO_USER);
					r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
					return r;
				}
			} else if (ut == ReqConstants.USER_TYPE_STUDENT) {
//				if (!sExist) {
//					r.setCode(ResultCode.ERRORCODE.NO_USER);
//					r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
//					return r;
//				}
			} else {
				// 参数错误
				r.setCode(ResultCode.ERRORCODE.PARAMERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
				return r;
			}
		} else if (rt == ReqConstants.REQ_TYPE_SUPER_LOGIN){
			//20160414超级登录短信。如果未注册则直接注册成用户，并返回登录短信；如果已注册则直接返回登录短信。目前只给学员端开放
			if (ut == ReqConstants.USER_TYPE_COACH) {
				r.setCode(ResultCode.ERRORCODE.PARAMERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
				return r;
			} else if (ut == ReqConstants.USER_TYPE_STUDENT) {
//				if (!sExist) {
//					//设置超级用户标示
//					redisUtil.setAll(REDISKEY.TAG_SUPER_STUDENT + mo, true,0);
//				}
			} else {
				// 参数错误
				r.setCode(ResultCode.ERRORCODE.PARAMERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
				return r;
			}
		} 
		
		else {
			// 参数错误
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r;
		}

		String code = MobileUtil.generateAuthCode().toString();
		//20160127添加演示账号，用于ios审核
		if("18589036282".equals(mo)){
			code = "123456";
		}
		
		// 存入redis//发送短信
		//SMSMessageerviceImpl sms = new SMSMessageerviceImpl();
		String[] codes = { code };

		//90128 【喱喱学车】轻松愉快学车，喱喱学车验证码为：{1}（30分钟有效）
		//90128 【喱喱学车】轻松愉快学车，喱喱学车更改信息验证码为：{1}（30分钟有效）
		if (ut == ReqConstants.USER_TYPE_COACH) {
			redisUtil.setAll(REDISKEY.COACH_AUTHCODE + mo, code,300);
			if (rt == ReqConstants.REQ_TYPE_REGISTER) {
				// sms.SendMessage("尊敬的教练，感谢您的注册，您的验证码为："+code, mo+";");
				//sms.SMSSendMessage(mo, "90128", codes);
				String message="{\"authcode\":\""+code+"\"}";
				try {
					SmsUtil.sendSms(mo, "SMS_116560357", message);
				} catch (ClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (rt == ReqConstants.REQ_TYPE_FIND_PASSWORD) {
				// sms.SendMessage("尊敬的教练，您正在找回密码，您的验证码为："+code, mo+";");
				//sms.SMSSendMessage(mo, "90128", codes);
				String message="{\"authcode\":\""+code+"\"}";
				try {
					SmsUtil.sendSms(mo, "SMS_122296186", message);
				} catch (ClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (ut == ReqConstants.USER_TYPE_STUDENT) {
			redisUtil.setAll(REDISKEY.STUDENT_AUTHCODE + mo, code,300);
			if (rt == ReqConstants.REQ_TYPE_REGISTER) {
				// sms.SendMessage("尊敬的学员，感谢您的注册，您的验证码为："+code, mo+";");
				sms.SMSSendMessage(mo, "90128", codes);
			} else if (rt == ReqConstants.REQ_TYPE_FIND_PASSWORD) {
				// sms.SendMessage("尊敬的学员，您正在找回密码，您的验证码为："+code, mo+";");
				sms.SMSSendMessage(mo, "90128", codes);
			} else if (rt == ReqConstants.REQ_TYPE_LOGIN) {
				// sms.SendMessage("尊敬的学员，您正在通过手机验证码进行登录，您的验证码为："+code, mo+";");
				sms.SMSSendMessage(mo, "90128", codes);
			} else if (rt == ReqConstants.REQ_TYPE_SUPER_LOGIN){
				sms.SMSSendMessage(mo, "90128", codes);
			}
		}

		// 临时返回给客户端，正常流程不需要
		Map<String, String> data = new HashMap<String, String>();
		String authcodeEnv = authcodeProp.getProperty("authcode.env");
		logger.debug("getAuthcode authcodeEnv: " + authcodeEnv);
		if(authcodeEnv.equals(ReqConstants.ENV_TEST)){
			data.put("authcode", code);
		}else {
			data.put("authcode", "123456");
		}
		r.setData(data);
		return r;
	}

	@Override
	public ReqResult isCodeExist(String code, String mobile, String userId,
			String userType) throws Exception {

		ReqResult r = ReqResult.getSuccess();

		int ut = Integer.parseInt(userType);

		String authcodes = "";
		if (ut == ReqConstants.USER_TYPE_COACH) {
			authcodes = redisUtil.get(REDISKEY.COACH_AUTHCODE + mobile);
			//redisUtil.delete(REDISKEY.COACH_AUTHCODE + mobile);
		} else if (ut == ReqConstants.USER_TYPE_STUDENT) {
			authcodes = redisUtil.get(REDISKEY.STUDENT_AUTHCODE + mobile);
			//redisUtil.delete(REDISKEY.STUDENT_AUTHCODE + mobile);
		} else if (ut == ReqConstants.USER_TYPE_SCHOOL) {
			authcodes = redisUtil.get(REDISKEY.SCHOOL_AUTHCODE + mobile);
			//redisUtil.delete(REDISKEY.SCHOOL_AUTHCODE + mobile);
		}else{
			return ReqResult.getFailed();
		}

		if (!code.equals(authcodes)) {
			r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
		}
		
		return r;
	}

	@Override
	public ReqResult getAuthcodeOne(String mobile, String userType, String userId)
			throws Exception {

		ReqResult r = ReqResult.getSuccess();
		String mo = mobile.trim();
		int ut = Integer.parseInt(userType);

		String code = MobileUtil.generateAuthCode().toString();
		//20160127添加演示账号，用于ios审核
		if("18589036282".equals(mo)){
			code = "123456";
		}
		
		// 存入redis//发送短信
		//SMSMessageerviceImpl sms = new SMSMessageerviceImpl();
		String[] codes = { code };

		if (ut == ReqConstants.USER_TYPE_COACH) {
			redisUtil.set(REDISKEY.COACH_AUTHCODE + mo, code, 300);
			sms.SMSSendMessage(mo, "90128", codes);
		} else if (ut == ReqConstants.USER_TYPE_STUDENT) {
			redisUtil.set(REDISKEY.STUDENT_AUTHCODE + mo, code, 300);
			sms.SMSSendMessage(mo, "90128", codes);
		} else if (ut == ReqConstants.USER_TYPE_SCHOOL) {
			redisUtil.set(REDISKEY.SCHOOL_AUTHCODE + mo, code, 300);
			sms.SMSSendMessage(mo, "90128", codes);
		}
		// 临时返回给客户端，正常流程不需要
		Map<String, String> data = new HashMap<String, String>();
		String authcodeEnv = authcodeProp.getProperty("authcode.env");
		logger.debug("getAuthcodeOne authcodeEnv: " + authcodeEnv);
		if(authcodeEnv.equals(ReqConstants.ENV_TEST)){
			data.put("authcode", code);
		}else {
			data.put("authcode", "123456");
		}
		r.setCode(ResultCode.ERRORCODE.SUCCESS);
		r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		r.setData(data);
		return r;
	}

	@Override
	public ReqResult sendMsg(int type, String mobile, Map<String,String> msgs)
			throws Exception {
		ReqResult r = ReqResult.getSuccess();
		String mo = mobile.trim();
		//SMSMessageerviceImpl sms = new SMSMessageerviceImpl();
		//可以发送不同类型的短信
		// 1--教练端收到的实时订单短信，对应的文案模板为--您收到了【180】元实时订单，【张三（13424390000）】【09:56】约了您【科目二】课程，请及时联系学员,详见喱喱教练APP，祝您上课愉快!
		if(type == ReqConstants.SHORT_MESSAGE_TYPE_INSTANT_ORDER){
			String[] codes = { msgs.get("money"),msgs.get("name"),msgs.get("mobile"),msgs.get("time"),msgs.get("course") };
			sms.SMSSendMessage(mo, "58830", codes);
		}else if(type == ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_PAYMENT_GIFT){
			// 5 -- 亲爱的用户，您于{1}已报名{2}地区驾考，并成功支付驾考报名费{3}元。交了资料送演唱会门票哦！
			String[] codes = { msgs.get("time"),msgs.get("city"),msgs.get("money") };
			try {
				//20160614活动根据时间段发不同短信
				String t1= authcodeProp.getProperty("hd.time.begin");
				String t2= authcodeProp.getProperty("hd.time.end");
				Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(t1);
				Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(t2);
				Date d3 = new Date();
				if(d3.after(d1) && d3.before(d2)){
					sms.SMSSendMessage(mo, "93100", codes);
				}else {
					sms.SMSSendMessage(mo, "66373", codes);
				}
			} catch (Exception e) {
				sms.SMSSendMessage(mo, "66373", codes);
				e.printStackTrace();
			}

		}
		else if(type == ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_PAYMENT){
			// 2 -- 【亲爱的用户，您于{1}已报名{2}地区驾考，并成功支付驾考报名费{3}元。
			String[] codes = { msgs.get("time"),msgs.get("city"),msgs.get("money") };
			sms.SMSSendMessage(mo, "66373", codes);
		}
		else if(type == ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_FEEDBACK){
			// 3 -- 【喱喱学车报名反馈】学员{1} {2}，于{3}已报名{4}地区驾考，并成功支付报名费{5}元。
			String[] codes = { msgs.get("name"),msgs.get("mobile"),msgs.get("time"),msgs.get("city"),msgs.get("money") };
			sms.SMSSendMessage(mo, "66375", codes);
		}else if(type == ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_COUPON){
			// 4 -- 【喱喱学车】亲爱的用户，您的{1报名学车抵扣券}已送达喱喱学车个人账户喽，请到{2钱包}查看！
			String[] codes = { msgs.get("name"),msgs.get("place") };
			sms.SMSSendMessage(mo, "82561", codes);
		}
		
		else if(type == ReqConstants.SHORT_MESSAGE_TYPE_THEORY_ON){
			// 6 -- 开理论课
			String[] codes = { msgs.get("classDate"),msgs.get("cardDesc"),
					msgs.get("classPlace"),msgs.get("className"),msgs.get("classTime"),msgs.get("contact") };
			sms.SMSSendMessage(mo, "94828", codes);
		}
		else if(type == ReqConstants.SHORT_MESSAGE_TYPE_THEORY_OFF){
			// 7 -- 关理论课
			String[] codes = { msgs.get("classDate"),msgs.get("className") };
			sms.SMSSendMessage(mo, "94868", codes);
		}
		else if(type == ReqConstants.SHORT_MESSAGE_TYPE_LONGTRAIN_ON){
			// 6 -- 开理论课
			String[] codes = { msgs.get("classDate"),msgs.get("carrys"),
					msgs.get("classPlace"),msgs.get("contact") };
			sms.SMSSendMessage(mo, "95377", codes);
		}
		else if(type == ReqConstants.SHORT_MESSAGE_TYPE_LONGTRAIN_OFF){
			// 7 -- 关理论课
			String[] codes = { msgs.get("classDate"),msgs.get("className") };
			sms.SMSSendMessage(mo, "94868", codes);
		}
		
		return r;
	}
	

	@Override
	public ReqResult sendMsgById(String msgId, String mobile,
			Map<Integer, String> msgs) throws Exception {
		
		ArrayList<String> codes = new ArrayList<String> ();
		if(null != msgs && msgs.size()>0){ //数据标号从1开始，与模板对应
			for(int i=0;i<msgs.size();i++){
				codes.add(msgs.get(i+1));
			}
		}
		
		sms.SMSSendMessage(mobile.trim(), msgId.trim(), (String[]) codes.toArray(new String[0]));
		return null;
	}

	@Override
	public String getMsgTemplate(Integer prog,String msgId) {
		if(prog == ReqConstants.PROG_STAGE_THEORY){
			//理论课状态：'状态：0-未确认；1-待上课；2-已上课；3-已取消'
			if(StringUtil.isEmptyOrWhitespaceOnly(msgId) || msgId.equals("1")){ 
				return authcodeProp.getProperty("msg.94828"); //默认获取理论课发送模板
			}else if(msgId.equals("3")) {
				return authcodeProp.getProperty("msg.94868");
			}			
		}else if(prog == ReqConstants.PROG_STAGE_SUBJECT_LONGTRAIN){
			//长考课状态：'状态：0-未确认；1-待上课；2-已上课；3-已取消'
			if(StringUtil.isEmptyOrWhitespaceOnly(msgId) || msgId.equals("1")){ 
				return authcodeProp.getProperty("msg.95377"); //默认获取理论课发送模板
			}else if(msgId.equals("3")) {
				return authcodeProp.getProperty("msg.94868");
			}	
		}

		return authcodeProp.getProperty(msgId);
	}
	
	
	@Override
	public ReqResult noticeAddClickNum(String noticeId){
		
		ReqResult r = ReqResult.getSuccess();
		noticeManager.noticeAddClickNum(noticeId);
		return r;
	}
	
	@Override
	public ReqResult getNoticeById(String noticeId){
		
		ReqResult r = ReqResult.getSuccess();
		Notice notice=noticeManager.getNoticeById(noticeId);
		r.setData(notice);
		return r;
	}

	@Override
	public ReqResult getRongToken(String userId, String userType) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			// 1.先检查是否已存在
			int ut = Integer.parseInt(userType);
			long uid = Long.parseLong(userId);
			String userName = "lili";
			String userImg = "";
			String rongUserId = "";
			if (ut == ReqConstants.USER_TYPE_COACH) {
				Coach coach = coachManager.getCoachInfo(uid);
				String token =coach.getRongToken();
				if(null != token && !"".equals(token)){
					r.setData(token);
					return r;
				}
				userName = coach.getName();
//				ReqResult temp = fileService.getDownUrlByKey(userId, userType, coach.getHeadIcon(), null);
//				userImg = (String) temp.getResult().get("downUrl");
				userImg = "http://o7d94lzvx.bkt.clouddn.com/leftbar_portrait_coach.png";
				rongUserId = userId + "-c";
			}else {
				Student student = studentManager.getStudentInfo(uid);
				String token =student.getRongToken();
				if(null != token && !"".equals(token)){
					r.setData(token);
					return r;
				}
				userName = student.getName();
//				ReqResult temp = fileService.getDownUrlByKey(userId, userType, student.getHeadIcon(), null);
//				userImg = (String) temp.getResult().get("downUrl");
				userImg = "http://o7d94lzvx.bkt.clouddn.com/portrait_students.png";
				rongUserId = userId;
			}
			
			// 2.不存在则获取并保存
			String appKey = "4z3hlwrv4zrjt";//替换成您的appkey
			String appSecret = "jsAaRK0iMa";//替换成匹配上面key的secret
			
			String authcodeEnv = authcodeProp.getProperty("authcode.env");
			if(authcodeEnv.equals(ReqConstants.ENV_PRODUCT)){
				appKey = authcodeProp.getProperty("rong.product.appkey");
				appSecret = authcodeProp.getProperty("rong.product.appsec");
			}else {
				appKey = authcodeProp.getProperty("rong.dev.appkey");
				appSecret = authcodeProp.getProperty("rong.dev.appsec");
			}
			
			RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
			// 获取 Token 方法 
			TokenReslut userGetTokenResult = rongCloud.user.getToken(rongUserId, userName, userImg);
			if(userGetTokenResult.getCode() == 200){
				if (ut == ReqConstants.USER_TYPE_COACH) {
					Coach coach = coachManager.getCoachInfo(uid);
					coach.setRongToken(userGetTokenResult.getToken());
					coachManager.updateCoach(coach);
				}else {
					Student student = studentManager.getStudentInfo(uid);
					student.setRongToken(userGetTokenResult.getToken());
					studentManager.updateStudent(student);
				}
				r.setData(userGetTokenResult.getToken());
				return r;
			}else {
				r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
				r.setMsgInfo(userGetTokenResult.getErrorMessage());
				return r;
			}
			
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	
	
	

}


































