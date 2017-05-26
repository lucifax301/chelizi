package com.lili.coach.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.authcode.service.AuthcodeService;
import com.lili.coach.dto.BrandCar;
import com.lili.coach.dto.Car;
import com.lili.coach.dto.CarCheck;
import com.lili.coach.dto.CarInfo;
import com.lili.coach.dto.Coach;
import com.lili.coach.dto.CoachAccount;
import com.lili.coach.dto.CoachCar;
import com.lili.coach.dto.CoachCheck;
import com.lili.coach.dto.ErrorAppeal;
import com.lili.coach.dto.ErrorAppealItem;
import com.lili.coach.dto.Region;
import com.lili.coach.dto.Trfield;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachErrorAppealManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.RegionManager;
import com.lili.coach.manager.TrfieldManager;
import com.lili.coach.service.CoachService;
import com.lili.coach.vo.CarVo;
import com.lili.coach.vo.CoachInfoVo;
import com.lili.coach.vo.CoachStatusRecord;
import com.lili.coach.vo.CoachVo;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.IDCard;
import com.lili.common.util.MobileUtil;
import com.lili.common.util.Page;
import com.lili.common.util.SecurityUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.event.vo.CoachCarInOutVo;
import com.lili.event.vo.CourseStatusEventVo;
import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.exam.manager.ExamPlaceOrderManager;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.OrderCancelService;
import com.lili.order.service.OrderService;
import com.lili.order.vo.CoachClassPriceVo;
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassTemp;
import com.lili.order.vo.CoachClassTempNameVo;
import com.lili.order.vo.CoachClassTempQuery;
import com.lili.order.vo.CoachClassTempVo;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.OrderCancelVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.pay.service.IDepositService;
import com.lili.pay.vo.DepositVo;
import com.lili.pay.vo.PerformanceVo;
import com.lili.pay.vo.UserMoneyVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.lili.student.vo.StudentInfoVo;

public class CoachServiceImpl implements CoachService
{
    private static Logger logger = LoggerFactory.getLogger(CoachServiceImpl.class);
    
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    CoachManager coachManager;
    @Autowired
    CarManager carManager;
    @Autowired
    NoticeManager noticeManager;
    
    @Resource(name="coachProducer")
    DefaultMQProducer coachProducer;
    @Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
    @Autowired
    TrfieldManager trfieldManager;
	@Autowired
	StudentManager studentManager;

	@Autowired
	CoachClassService coachClassService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderCancelService orderCancelService;
	@Autowired
	ExamPlaceOrderManager examPlaceOrderManager;
	@Autowired
	CoachErrorAppealManager coachErrorAppealManager;
	
	@Autowired
	RegionManager regionManager;
	
	@Autowired
	IDepositService depositService;
	
	@Autowired
	private AuthcodeService authcodeService;
    
	final String carNoRegex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$"; 
	
	private String passMsgId = "128930"; //注册教练审核通过2
	private String unPassMsgId = "128928"; //注册教练审核不通过2
	
    @Override
    public ReqResult addCoachAndLogin(String mobile, String codeInput, String password, String name)
            throws Exception
    {

        ReqResult r = ReqResult.getSuccess();
        String mo = mobile.trim();
        String authcode = redisUtil.get(REDISKEY.COACH_AUTHCODE + mo);

        if (authcode == null || !authcode.equals(codeInput.trim()))
        {	// 验证码错误
            r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
            return r;
        }
        else if (!MobileUtil.isMobile(mobile.trim()))
        {	// 手机号码错误
            r.setCode(ResultCode.ERRORCODE.MOBILE_NUMBER_ERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.MOBILE_NUMBER_ERROR);
            return r;
        }
        else if ("".equals(password.trim()))
        {	// 密码不能为空
            r.setCode(ResultCode.ERRORCODE.PASSWORD_EMPTY_ERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_EMPTY_ERROR);
            return r;
        }
        else
        {
            // redis中删除已保存的验证码信息
            redisUtil.delete(REDISKEY.COACH_AUTHCODE + mo);
            long coachId = 0L;
            // 查看手机号码是否存在，忘记密码重置密码流程中手机号码已存在，只需更新密码
            Coach cc = coachManager.getCoachByPhoneNum(mo);
            Date now = new Date();
            if (null != cc)
            {
                coachId = cc.getCoachId();
                cc.setPassword(password);
                if (cc.getFirstLogin() == null)
                	cc.setFirstLogin(now);
                cc.setLastLogin(now);
                coachManager.updateCoach(cc);
              //add by devil 20160912
                coachManager.addLoginCount(cc);
            }
            else
            {
                // 保存用户到MySQL中
                Coach c = new Coach();
                c.setPhoneNum(mobile);
                c.setName(name);
                c.setPassword(password);
                c.setAgreement(1);
                c.setStarLevel(OrderConstant.COACHSCORE);//设置默认星级
                c.setFirstLogin(now);
                c.setLastLogin(now);
                coachManager.addCoach(c);
                // 获取生成的用户id
                coachId = c.getCoachId();
            }
            

            // 生成token
            String token = SecurityUtil.getUUID();

            // 将token保存到redis中
            redisUtil.setAll(REDISKEY.COACH_TOKEN + coachId, token,0);

            //注册成功后，保存到用户消息中心
//            Notice notice = new Notice();
//            notice.setTitle("系统小秘书");
//            notice.setUserId(coachId);
//            notice.setUserType((byte)ReqConstants.USER_TYPE_COACH);
//            notice.setType((byte) 4);  
//            notice.setDigest("欢迎您成为我们的新用户！");  //content改为存html内容
//            notice.setContent("欢迎您成为我们的新用户！");
//            notice.setTime(new Date());
//            noticeManager.addNotice(notice);
            
            // 返回数据到客户端
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("coachId", coachId);
            data.put("token", token);
            r.setData(data);
            return r;
        }

    }

    @Override
    public ReqResult login(String mobile, String password)
    {

        ReqResult r = ReqResult.getSuccess();
        String mo = mobile.trim();

        if (!MobileUtil.isMobile(mo))
        {	// 手机号码错误
            r.setCode(ResultCode.ERRORCODE.MOBILE_NUMBER_ERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.MOBILE_NUMBER_ERROR);
            return r;
        }
        else
        {
            // 获取密码
            Coach c = coachManager.getCoachByPhoneNum(mo);
            if (c != null)
            {
            	Map<String, Object> data = new HashMap<String, Object>();
            	//20160526增加锁定功能
            	int state = c.getState();
            	if(state == ReqConstants.USER_STATE_LOCKED_FOREVER){
            		data.put("reason", c.getExtra());
                    r.setData(data);
                    r.setCode(ResultCode.ERRORCODE.USER_LOCKED);
                    r.setMsgInfo(ResultCode.ERRORINFO.USER_LOCKED);
                    return r;
            	}else if(state == ReqConstants.USER_STATE_LOCKED_TEMP){
            		Date reviveTime = c.getReviveTime();
            		Date now = new Date();
            		if(reviveTime.after(now)){
                		data.put("reason", c.getExtra());
                		data.put("reviveTime", reviveTime);
                        r.setData(data);
                        r.setCode(ResultCode.ERRORCODE.USER_LOCKED);
                        r.setMsgInfo(ResultCode.ERRORINFO.USER_LOCKED);
                        return r;
            		}else {
            			//用户复活时间已到，恢复状态为正常
            			c.setState(ReqConstants.USER_STATE_NORMAL);
            			coachManager.updateCoach(c);
            		}
            	}
            	
                String pass = c.getPassword();
                if (pass.equals(password))
                {
                    // 生成token
                    String token = SecurityUtil.getUUID();
                    long coachId = c.getCoachId();
                    // 将token保存到redis中
                    redisUtil.setAll(REDISKEY.COACH_TOKEN + coachId, token,0);
                    //设置登录时间
                    Date now = new Date(); 
                    if (c.getFirstLogin() == null)
                    	c.setFirstLogin(now);
                    c.setLastLogin(now);
                    coachManager.updateCoach(c);
                    //add by devil 20160912
                    coachManager.addLoginCount(c);
                    data.put("coachId", coachId);
                    data.put("token", token);
                    r.setData(data);
                    return r;
                }
                else
                {
                    r.setCode(ResultCode.ERRORCODE.PASSWORD_ERROR);
                    r.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_ERROR);
                    return r;
                }
            }
            else
            {
                r.setCode(ResultCode.ERRORCODE.NO_USER);
                r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
                return r;
            }

        }
    }

    @Override
    public ReqResult autoLogin(String coachId, String tokenId)
    {
        ReqResult r = ReqResult.getSuccess();

        if (redisUtil.isExist(REDISKEY.COACH_TOKEN + coachId, tokenId))
        {
            return r;
        }
        else
        {
            r.setCode(ResultCode.ERRORCODE.NO_USER);
            r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
            return r;
        }

    }

    @Override
    public ReqResult logout(String coachId, String tokenId)
    {
        ReqResult r = ReqResult.getSuccess();
        // redis中删除已保存的
        redisUtil.delete(REDISKEY.COACH_TOKEN + coachId);
        redisUtil.delete(REDISKEY.COACH + coachId);
        redisUtil.delete(REDISKEY.COACH_VO + coachId);
        redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId);
        r.setCode(ResultCode.ERRORCODE.SUCCESS);
        r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
        // 20160905 产品要求教练退出登录后设置为收车状态 //如果教练是等待上课状态会有问题
        //Coach coach = new Coach();
        //coach.setCoachId(Long.parseLong(coachId));
        //coach.setWstate(0);
        //coachManager.updateCoach(coach);
        return r;

    }

    /**
     * 获取教练基本信息
     */
    @Override
    public ReqResult getUserInfo(String coachId, String tokenId)
    {
        ReqResult r = ReqResult.getSuccess();
        //CoachInfoVo ci = coachManager.getCoachInfoVo(Long.parseLong(coachId));
        Coach c = coachManager.getCoachInfo(Long.parseLong(coachId));
		//查询带教人数
        CoachClassQuery coachclassQuery = new CoachClassQuery();
        coachclassQuery.setSqlPost(" and isdel=0 and ctype=3 and rend < NOW() ");
        Integer bookSum = coachClassService.queryBookNumByCoachId(c.getCoachId(), coachclassQuery);//预约
        CoachClassQuery coachclassQuery2 = new CoachClassQuery();
        coachclassQuery2.setSqlPost(" and isdel=0 and ctype=1");
        Integer bookSumOrder = coachClassService.queryBookNumByCoachId2(c.getCoachId(), coachclassQuery2);//现约
        c.setBookSum(bookSum + bookSumOrder);
        CoachInfoVo ci = new CoachInfoVo();
        try {
			ci = BeanCopy.copyNotNull(c, ci);
			Car car = carManager.getCarInfo(c.getCoachCarId());
			if(null != car){
				ci.setCarDriveNumber(car.getDriveNumber());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        // 返回数据到客户端
        r.setData(ci);
        return r;
    }

    @Override
    public ReqResult updatePass(String coachId, String password, String headIcon, String name, String sex, String age, String phoneNum,String codeInput
    		, String schoolName, String cityId, String cityName) {

        ReqResult r = ReqResult.getSuccess();
        // 是否已登录
        Coach c = new Coach();
        c.setCoachId(Long.parseLong(coachId));
        if(null != password && !"".equals(password.trim())){
        	if(!password.equalsIgnoreCase("D41D8CD98F00B204E9800998ECF8427E")){ //空字符串的md5加密字符串
        		c.setPassword(password.trim());
        	}
        }
        if(null != headIcon && !"".equals(headIcon)){
        	c.setHeadIcon(headIcon);
        }
        if(null != name && !"".equals(name)){
        	c.setName(name);
        }
        if(null != sex && !"".equals(sex)){
        	c.setSex(Integer.parseInt(sex));
        }
        if(null != age && !"".equals(age)){
        	c.setAge(Integer.parseInt(age));
        }
        if(null != schoolName && !"".equals(schoolName)){
        	c.setSchoolName(schoolName);
        }
        if(null != cityId && !"".equals(cityId)){
        	c.setCityId(Integer.parseInt(cityId));
        }
        if(null != cityName && !"".equals(cityName)){
        	c.setCityName(cityName);
        }
        if(null != phoneNum && !"".equals(phoneNum) && null!=codeInput && !"".equals(codeInput)){
        	String mo = phoneNum.trim();
        	//修改的手机号码需要验证是否已被注册  已在获取验证码时验证
        	/*boolean cExist = coachManager.isExist(mo);
			if(cExist){
				r.setCode(ResultCode.ERRORCODE.MOBILE_EXIST);
				r.setMsgInfo(ResultCode.ERRORINFO.MOBILE_EXIST);
				return r;
			}*/
			String authcode = redisUtil.get(REDISKEY.COACH_AUTHCODE + mo);

	        if (authcode == null || !authcode.equals(codeInput.trim()))
	        {	// 验证码错误
	            r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
	            r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
	            return r;
	        }
	        // redis中删除已保存的验证码信息
            redisUtil.delete(REDISKEY.COACH_AUTHCODE + mo);
        	c.setPhoneNum(mo);
        }
        
        coachManager.updateCoach(c);
        // 返回数据到客户端
        return r;

    }
    
    

    @Override
	public ReqResult updateHeadIcon(String userId, String picPath) {
        ReqResult r = ReqResult.getSuccess();
        Coach coach  = coachManager.getCoachInfo(Long.parseLong(userId));
        if (coach != null && coach.getIsImport() == 0) { //注册教练才能修改头像
        	Coach c = new Coach();
        	c.setCoachId(Long.parseLong(userId));
        	c.setHeadIcon(picPath);
        	coachManager.updateCoach(c);
        }
        else {
        	r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo("亲爱的教练，喱喱为您处理头像，该功能已经关闭");
        }
	
	    return r;

	}

	@Override
    public ReqResult addIdCardInfo(String coachId, String name, String idCard,
            String picPath1, String picPath2, String tokenId)
    {

        ReqResult r = ReqResult.getSuccess();

        if (!IDCard.IDCardValidate(idCard))
        {	// 身份证号码错误
            r.setCode(ResultCode.ERRORCODE.IDCARD_NUMBER_ERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.IDCARD_NUMBER_ERROR);
            return r;
        }
        //20160105 Issue #189 避免同一个身份注册多个账号
        if(null != idCard){
        	Coach c = new Coach();
            c.setIdNumber(idCard.trim());
            long count = coachManager.getCount(c);
            if(count>0){
                r.setCode(ResultCode.ERRORCODE.IDCARD_NUMBER_EXIST);
                r.setMsgInfo(ResultCode.ERRORINFO.IDCARD_NUMBER_EXIST);
                return r;
            }
        }
        // 保存身份证信息到教练表
        Coach coach = new Coach();
        if (StringUtils.isNotEmpty(coachId))
        {
            coach.setCoachId(Long.parseLong(coachId)); // 教练id
        }
        coach.setIdNumber(idCard);   // 教练身份证
        coach.setName(name);         // 教练名称
        coach.setIdPhotoFront(picPath1);  // 身份证正面
        coach.setIdPhotoBack(picPath2);   // 身份证方面
        coach.setCheckIdState(ReqConstants.CHECK_STATE_PROCESS);//20160331上传完成身份资料后，变更为审核中

        coachManager.updateCoach(coach);

        return r;
    }

    @Override
    public ReqResult addCoachCardInfo(String coachId, String coachCardId,
            String teachArea, String carSchool, String picPath1, String picPath2, String tokenId)
    {
        ReqResult r = ReqResult.getSuccess();

        if ("".equals(coachCardId.trim()))
        {	// 教练证号码错误
            r.setCode(ResultCode.ERRORCODE.COACH_CARD_ERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.COACH_CARD_ERROR);
            return r;
        }
        //20160105 Issue #189 避免同一个身份注册多个账号
        if(null != coachCardId){
        	Coach c = new Coach();
            c.setCoachCard(coachCardId.trim());
            long count = coachManager.getCount(c);
            if(count>0){
                r.setCode(ResultCode.ERRORCODE.COACH_CARD_EXIST);
                r.setMsgInfo(ResultCode.ERRORINFO.COACH_CARD_EXIST);
                return r;
            }
        }
        
        // 保存教练的教学信息
        Coach coach = new Coach();
        coach.setCoachId(Long.parseLong(coachId)); // 教练id
        if (StringUtils.isNotEmpty(coachCardId))
        {
            coach.setCoachCard(coachCardId.trim());
        }
        if (StringUtils.isNotEmpty(teachArea))
        {
            coach.setCityId(Integer.parseInt(teachArea));     // 教学城市id
        }
        if (StringUtils.isNotEmpty(carSchool))
        {
            coach.setSchoolId(Integer.parseInt(carSchool));   // 驾校id
        }
        coach.setCoachCardPhoto(picPath1);              // 教练证照片
        coach.setDrPhoto(picPath2);               // 教练驾驶证照片
        coach.setCheckDriveIdState(ReqConstants.CHECK_STATE_PROCESS);//20160331上传完成教练证资料后，变更为审核中
        coachManager.updateCoach(coach);

        return r;
    }

    @Override
    public ReqResult addCarInfo(String coachId, String carType, String licensePlateNumber,
            String drivingLicense, String picPath1, String drtype, String brandName,  String operateType, String carId) throws Exception
    {
        ReqResult r = ReqResult.getSuccess();
        
        // 保存车辆信息
    	Car car = new Car();
    	car.setDriveNumber(drivingLicense);   // 行驶证编号
    	car.setDrivePhoto(picPath1);    // 行驶证照片
    	car.setCarType(carType);        // 车类型
    	car.setCarNo(licensePlateNumber);            // 车牌号
    	if (null != drtype && !"".equals(drtype)) {
    		car.setDriveType(Byte.parseByte(drtype));
    	}
    	car.setBrandName(brandName);
    	
    	Long coachIdLong = null;
    	CarInfo carInfo = null;
    	if (StringUtils.isNotEmpty(coachId)) {
    		coachIdLong = Long.parseLong(coachId);
    	}
    	Coach coach = coachManager.getCoachInfo(coachIdLong);
    	
        //20161024增加、修改、删除车辆信息跟添加分开
    	//1. 添加车辆信息
        if (operateType == null || "".equals(operateType) || "1".equals(operateType)) { 
        	//20160105 Issue #189 避免同一个行驶证注册多个账号
        	if (null != drivingLicense && !"".equals(drivingLicense)) {
        		int count = carManager.getCountByDrivenumber(drivingLicense.trim());
        		if(count >0){
        			r.setCode(ResultCode.ERRORCODE.DRIVE_CARD_EXIST);
        			r.setMsgInfo(ResultCode.ERRORINFO.DRIVE_CARD_EXIST);
        			return r;
        		}
        	}
        	
        	//20160921 避免同一个车牌号注册多个账号并增加强校验
        	if (null != licensePlateNumber && !"".equals(licensePlateNumber)) {
        		licensePlateNumber = licensePlateNumber.toUpperCase();
        		if (!licensePlateNumber.matches(carNoRegex)) {
        			r.setCode(ResultCode.ERRORCODE.CARNO_IS_ILLEGAL_TWO);
        			r.setMsgInfo(ResultCode.ERRORINFO.CARNO_IS_ILLEGAL_TWO);
        			return r;
        		}
        		
        		carInfo = carManager.getCarInfoByNo(licensePlateNumber.trim());
        		/*List<Car> carlist = carManager.getCarByCoachId(coachIdLong);
        		if (carlist != null && carlist.size() > 5 && coach != null && coach.getIsImport() == 1) { //内部教练只能有五辆车
        			r.setCode(ResultCode.ERRORCODE.CAR_IS_LIMIT);
        			r.setMsgInfo(ResultCode.ERRORINFO.CAR_IS_LIMIT);
        			return r;
        		}*/
        		
        		//如果是注册教练，并且是新增我的车辆信息
        		if (coach.getIsImport() != null ) { 
        			//判断教练是否已绑定该车辆--审核车等
        			List<Car> cars = carManager.getCarByCoachId(coachIdLong);
        			if(cars != null && cars.size() > 0) {
        				for (Car carBing  :cars) {
        					if (licensePlateNumber.equals(carBing.getCarNo())) { //是否已绑定改车
        						r.setCode(ResultCode.ERRORCODE.CARNO_LIST_IS_EXIST);
        						r.setMsgInfo(ResultCode.ERRORINFO.CARNO_LIST_IS_EXIST);
        						return r;
        					}
        				}
        			}
        			CarCheck carCheck = new CarCheck();
        			carCheck.setCoachId(coachIdLong);
        			carCheck.setType(1);
					List<CarCheck> carCheckList = carManager.getCarCheckByCoachId(carCheck);//判断已绑定约考场车数量
					List<Car> carList = carManager.getCarByCoachId(coachIdLong); //历史数据
					CarCheck carCheckaAll = new CarCheck();
					carCheckaAll.setCoachId(coachIdLong);
					List<CarCheck> carCheckAllList = carManager.getCarCheckByCoachId(carCheckaAll);//所有审核表的车
					
					int carListSize = 0;
					int carCheckAllListSize = 0;
					int carCheckListSize = 0;
					if (carList != null && carList.size() > 0) {
						carListSize =  carList.size();
					}
					if (carCheckAllList != null && carCheckAllList.size() > 0) {
						carCheckAllListSize =  carCheckAllList.size();
					}
					if (carCheckList != null && carCheckList.size() > 0) {
						carCheckListSize =  carCheckList.size();
					}
					
					if (coach.getIsImport() == 0) {
						if (carCheckListSize +(carListSize- carCheckAllListSize) > 4 ) { //最多5辆非接单车
							r.setCode(ResultCode.ERRORCODE.CAR_IS_LIMIT);
							r.setMsgInfo(ResultCode.ERRORINFO.CAR_IS_LIMIT);
							return r;
						}
					}
					else if (coach.getIsImport() == 1){
						if (carCheckListSize  > 4 ) { //最多5辆非接单车
							r.setCode(ResultCode.ERRORCODE.CAR_IS_LIMIT);
							r.setMsgInfo(ResultCode.ERRORINFO.CAR_IS_LIMIT);
							return r;
						}
					}
        		}
        		boolean isAddCarCoach = false;
        		if (carInfo == null) { //不存在该车牌号的车辆信息
        			// 新增车辆信息
        			if (coach.getIsImport() != null && coach.getIsImport() == 0) { 
        				car.setSchoolId(0);
        			}
        			carManager.addCar(car, coachIdLong);
        			carInfo = carManager.getCarInfoByNo(licensePlateNumber.trim());
        		}
        		else { //已存在车辆信息，校验信息是否一致
        			isAddCarCoach = true;
        			if (carInfo.getDriveType() != null && !carInfo.getDriveType().toString().equals(drtype)) {
        				r.setCode(ResultCode.ERRORCODE.CAR_IS_NOT_CORRECT);
                		r.setMsgInfo(ResultCode.ERRORINFO.CAR_IS_NOT_CORRECT);
                		return r;
        			}
        		}
        		
    			CarCheck carCheck = new CarCheck();
    			carCheck.setCoachId(coachIdLong);
    			carCheck.setCarNo(licensePlateNumber);
    			carCheck.setCarId(carInfo.getCarId());
    			carCheck.setType(1); //约考场专用
    			carManager.addRegisterCoachCar(carCheck , coachIdLong, isAddCarCoach);
        		
        	} else {
        		r.setCode(ResultCode.ERRORCODE.CARNO_IS_ILLEGAL_TWO);
        		r.setMsgInfo(ResultCode.ERRORINFO.CARNO_IS_ILLEGAL_TWO);
        		return r;
        	}
        }
        
        //2.修改车辆信息 -- 没区分是注册还是内部教练，目前只有注册教练才有修改车辆信息
        else if (operateType != null && !"".equals(operateType) && "2".equals(operateType)){
        	if (null != licensePlateNumber && !"".equals(licensePlateNumber)) { 
        		licensePlateNumber = licensePlateNumber.toUpperCase();
        		if (!licensePlateNumber.matches(carNoRegex)){ //车牌号校验
        			r.setCode(ResultCode.ERRORCODE.CARNO_IS_ILLEGAL_TWO);
        			r.setMsgInfo(ResultCode.ERRORINFO.CARNO_IS_ILLEGAL_TWO);
        			return r;
        		}
        		//如果车牌号有更新，carId也会变
        		carInfo = carManager.getCarInfoByNo(licensePlateNumber.trim());
        		if (carInfo != null) { //判断修改的车辆是否存在，如果存在，则
        			//判断修改的信息跟原有信息是否一致
        			if (!drtype.equals(carInfo.getDriveType().toString())) {
        				r.setCode(ResultCode.ERRORCODE.CAR_DRIVE_ERROR);
            			r.setMsgInfo(ResultCode.ERRORINFO.CAR_DRIVE_ERROR);
            			return r;
        			}
        			car.setCarId(carInfo.getCarId());
        		}
        		else {
        			//单独增加车辆信息
        			carManager.addCarInfo(car);	
        			//插入后重新查carId
        			carInfo = carManager.getCarInfoByNo(licensePlateNumber.trim());
        		}
        		
        		//删除之前的绑定信息；重新插入绑定信息；更新绑定审核信息
        		CarCheck carCheckOld = new CarCheck();
        		carCheckOld.setCoachId(coachIdLong);
        		carCheckOld.setCarId(Integer.parseInt(carId));
        		CarCheck oldCcInfo = carManager.getCarCheckInfo(carCheckOld);
        		if (oldCcInfo != null) {
        			CarCheck carCheck = new CarCheck();
        			carCheck.setId(oldCcInfo.getId());
        			carCheck.setCoachId(coachIdLong);
        			carCheck.setCarNo(licensePlateNumber);
        			carCheck.setCarId(carInfo.getCarId());
        			carManager.updateCoachCarAndCheck(carCheck, carId, coachIdLong);
        		}
        	} 
        	else { //如果是更新车辆信息
        		//如果车牌号有更新，carId也会变
        		Car carVo = carManager.getCarInfo(Integer.parseInt(carId));
        		if (!drtype.equals(carVo.getDriveType().toString())) {
    				r.setCode(ResultCode.ERRORCODE.CAR_DRIVE_ERROR);
        			r.setMsgInfo(ResultCode.ERRORINFO.CAR_DRIVE_ERROR);
        			return r;
    			}
        	}
        	
        }
        
        //2.删除车辆信息
        else if (operateType != null && !"".equals(operateType) && "3".equals(operateType)){
        	if (StringUtils.isNotEmpty(coachId)) {
        		if (StringUtils.isNotEmpty(carId)) {
            		car.setCarId(Integer.parseInt(carId));
            	}
        		carManager.deleteCoachCarAndCheck(car, coachIdLong);
        	}
        }

        return r;
    }

    @Override
    public ReqResult getCarInfo(String coachId, String tokenId)
    {
        ReqResult r = ReqResult.getSuccess();

        List<Car> cars = carManager.getCarByCoachId(Long.parseLong(coachId));
        List<CarVo> cs = new ArrayList<CarVo>();
        try
        {
	        //20161026 增加注册教练返回车辆信息
	        Coach coach = coachManager.getCoachInfo(Long.parseLong(coachId));
	        //if (coach != null && coach.getIsImport() != null && coach.getIsImport() == 0) {
	        	CarCheck carCheck = null;
	        	CarCheck carCheckInfo = null;
	        	 List<Car> carsNew = new ArrayList<Car>();
	        	 if(cars != null && cars.size() > 0) {
	        		 for (Car car : cars) {
	        			 carCheck = new CarCheck();
	        			 carCheck.setCoachId(Long.parseLong(coachId));
	        			 carCheck.setCarId(car.getCarId());
	        			 carCheckInfo = carManager.getCarCheckInfo(carCheck);
	        			 if (carCheckInfo != null) {
	        				 car.setType(carCheckInfo.getType());
	        			 }
	        			 if (carCheckInfo != null && carCheckInfo.getType() ==2 && coach.getCheckDrState() != null) { //注册教练我的车辆仅限制审核通过的注册车
	        				 carsNew.add(car);
	        			 }
	        			 if (carCheckInfo != null && carCheckInfo.getType() !=2) {//非审核车直接加到我的车辆列表
	        				 car.setType(1); //历史数据处理，所有的注册教练的历史数据是约考场车
	        				 carsNew.add(car);
	        			 }
	        			 if(carCheckInfo == null) {
	        				if (coach.getIsImport() == 0) {
	        					car.setType(1); //历史数据处理，所有的注册教练的历史数据是约考场车
	        				}
	        				 carsNew.add(car);
	        			 }
	        		 }
	        		 cs = BeanCopy.copyList(carsNew, CarVo.class, BeanCopy.COPY2NULL);
	        	 }
	       /* }
	        else {
	        	cs = BeanCopy.copyList(cars, CarVo.class, BeanCopy.COPY2NULL);
	        }*/
       
            r.setData(cs);
            return r;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return r;
        }
    }

    /**
     * 教练出车、收车
     */
    @Override
    public ReqResult doStatus(String coachId, String carId, String courses, String status, String tokenId)
    {
        ReqResult r = ReqResult.getSuccess();

        byte st = 0;
        try
        {
            st = Byte.parseByte(status.trim());
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.PARAMERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return r;
        }
/*        Coach c = new Coach();
        c.setCoachId(Long.parseLong(coachId));
        //设置出车课程
        c.setCourses(courses);*/
        //设置出车id //收车时可用为空
        int nCarId = 0; 
        try {
            nCarId = Integer.parseInt(carId);
		} catch (NumberFormatException e) {
		    nCarId = 0;
		}

        if (st == ReqConstants.COACH_STATUS_ON_WORK)
        {
            // 教练状态更改为出车
            sendStatusMQ(Long.parseLong(coachId), nCarId, "out");
            //c.setWstate(Integer.valueOf(st));
            //coachManager.updateCoach(c);

        }
        else if (st == ReqConstants.COACH_STATUS_OFF_WORK)
        {
            // 教练状态更改为收车
            sendStatusMQ(Long.parseLong(coachId), nCarId, "in");
            //c.setWstate(Integer.valueOf(st));
            //coachManager.updateCoach(c);
        }
        else
        {
            r.setCode(ResultCode.ERRORCODE.PARAMERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return r;
        }
        
        /**
         * add by devil for 教练出收车记录
         */
//        try{
//	        CoachStatusRecord record =new CoachStatusRecord();
//	        record.setCoachId(Long.parseLong(coachId));
//	        record.setStatus(st);
//	        coachManager.addCoachStatusRecord(record);
//        }catch(Exception ex){
//        	logger.warn("add coach status record error,"+LogUtil.getStackMsg(ex));
//        }
        return r;
        
    }

    /**
     * 用于添加教练教龄 coachAge 喱喱教龄-单位小时
     * @param coachId
     * @param carId
     * @param tag
     */
    private void sendStatusMQ(long coachId, int carId, String tag)
    {
        CoachCarInOutVo carInOutVo = new CoachCarInOutVo();
        carInOutVo.setCarId(carId);
        carInOutVo.setCoachId(coachId);
        carInOutVo.setTime(new Date());
        carInOutVo.setStatus(tag);
        Message message = new Message(coachProducer.getCreateTopicKey(), tag, SerializableUtil.serialize(carInOutVo));
        try
        {
            coachProducer.send(message);
            logger.debug("CoachServiceImpl----->sendStatus:"+message);
        }
        catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e)
        {
            logger.error("coachId:" + coachId + "|carId:" + carId + "|tag:" + tag, e);
        }
    }
    /**
     * 教练上课、下课
     */
    @Override
    public ReqResult doCourseStatus(String coachId, String orderId, String studentId, String status, String tokenId)
    {
        ReqResult r = ReqResult.getSuccess();

        byte st = 0;
        try
        {
            st = Byte.parseByte(status.trim());
        }
        catch (NumberFormatException e)
        {
            r.setCode(ResultCode.ERRORCODE.PARAMERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            logger.error("doCourseStatus st paramerror:" + st, e);
            return r;
        }
        //Coach c = new Coach();
        //c.setCoachId(Long.parseLong(coachId));
        if (st == ReqConstants.COACH_STATUS_ON_CLASS)
        {
        	try {
				//上课学员时间过滤
				OrderVo stuBus = coachClassService.isStudentIdle(Long.parseLong(studentId), orderId, null, false);
				if(null != stuBus){
					r.setCode(ResultCode.ERRORCODE.ORDER_STU_BUZ);
					r.setMsgInfo(ResultCode.ERRORINFO.ORDER_STU_BUZ);
					logger.info("orderId:"+orderId+" doCourseStatus time filter: " + ResultCode.ERRORINFO.ORDER_STU_BUZ + " order: " + stuBus);
					return r;
				}
				//上课教练时间过滤
				CoachClassVo ccv = coachClassService.isCoachIdle(Long.parseLong(coachId), null, orderId, null, false);

				if(null != ccv){
					r.setCode(ResultCode.ERRORCODE.ORDER_COACH_BUZ);
					r.setMsgInfo(ResultCode.ERRORINFO.ORDER_COACH_BUZ);
					logger.info("orderId:"+orderId+"doCourseStatus time filter: " + ResultCode.ERRORINFO.ORDER_COACH_BUZ + " coachClass:" + ccv);
					return r;
				}
			} catch (Exception e) {
				e.printStackTrace();
				r.setCode(ResultCode.ERRORCODE.EXCEPTION);
	            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
	            logger.error("doCourseStatus exception:" + orderId, e);
	            return r;
			}
			
            // TODO 教练状态更改为上课中，同时MQ推送相关学员上课
            pushStatus(Long.parseLong(coachId), Long.parseLong(studentId), orderId, status, "start", 1);
            //c.setWstate(Integer.valueOf(st));
            //coachManager.updateCoach(c);

        }
        else if (st == ReqConstants.COACH_STATUS_PREPARE_CLASS) //下课的参数为3，暂用COACH_STATUS_PREPARE_CLASS标识
        {
            /*// TODO 教练状态更改为下课中，同时MQ推送相关学员下课
            pushStatus(Long.parseLong(coachId), Long.parseLong(studentId), orderId, status, "finish", 2);
            c.setWstate(Integer.valueOf(st));
            coachManager.updateCoach(c);*/
            
            //20151130 教练端不能主动下课，改为学员端可以主动下课
            r.setCode(ResultCode.ERRORCODE.PARAMERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return r;
            
        }
        else
        {
            r.setCode(ResultCode.ERRORCODE.PARAMERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return r;
        }

        return r;
    }

    private void pushStatus(long coachId, long studentId, String orderId, String status, String tag, int targetType)
    {
        CourseStatusEventVo statusEventVo = new CourseStatusEventVo();
        statusEventVo.setCoachId(coachId);
        statusEventVo.setOrderId(orderId);
        statusEventVo.setStatus(status);
        statusEventVo.setStudentId(studentId);
        statusEventVo.setTargetType(targetType);
        statusEventVo.setTime(new Date());

        Message message = new Message(coachProducer.getCreateTopicKey(), tag, SerializableUtil.serialize(statusEventVo));
        try
        {
            coachProducer.send(message);
            logger.debug("CoachServiceImpl----->pushStatus:"+message);
        }
        catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e)
        {
            logger.error("coachId:" + coachId + "studentId:" + studentId + "|orderId:" + orderId + "|tag:" + tag, e);
        }
    }
    
    @Override
    public Boolean isExist(String coachId, String token)
    {
        return redisUtil.isExist(REDISKEY.COACH_TOKEN + coachId, token);
    }


    @Override
    public ReqResult getWallet(String coachId)
    {
        ReqResult r = ReqResult.getSuccess();

        CoachAccount ca = coachManager.getCoachMoney(Long.parseLong(coachId));
        if(null == ca){
        	ca = new CoachAccount();
        	ca.setCoachId(Long.parseLong(coachId));
        	ca.setMoney(0);
        	ca.setPerformance(0);
        }
        r.setData(ca);
        return r;
    }

    @Override
    public ReqResult getBills(String coachId)
    {
    	ReqResult r = ReqResult.getSuccess();
    	List<UserMoneyVo> mm = new ArrayList<>();
    	UserMoneyVo mv = new UserMoneyVo();
    	mv.setChangevalue(1000);
    	mv.setLeftvalue(10000);
    	mv.setOperatetime(new Date());
    	mv.setOperatetype((byte) 1);//操作类型(0充值，1提现，2奖金，3补贴)
    	mv.setPayway(null);
    	mv.setRemark("提现");
    	mm.add(mv);
    	
    	r.setData(mm);
        return r;
    }
    
    @Override
	public ReqResult getRecords(String userId) {
    	ReqResult r = ReqResult.getSuccess();
    	List<PerformanceVo> pvs = new ArrayList<>();
    	PerformanceVo pv = new PerformanceVo();
    	pv.setCourse("科目二课时费");
    	pv.setDate(new Date());
    	pv.setPerformance(30000);
    	pv.setCoachId(123l);
    	pvs.add(pv);
    	r.setData(pvs);
        return r;
	}
    
    @Override
    public ReqResult getBroadcast(String userId, String tokenId)
    {
    	return this.getNotice(userId, null, null, null, new Date(), (byte)0);
    }

	@Override
    public ReqResult getMessages(String userId, String userType, String pageNum, String pageSize, String time, String type)
    {
		Date etime = null;
		if(null != time && !"".equals(time)){
			etime = new Date(Long.parseLong(time));
		}
		if (type == null || "".equals(type)){
			type = "1";
		}
		return this.getNotice(userId, userType, pageNum, pageSize, etime, Byte.parseByte(type));

    }
	
	@Override
    public ReqResult getNotices(String userId, String type, String pageNum, String pageSize)
    {
		ReqResult r = ReqResult.getSuccess();
        try {
			Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
			if(coach==null){
				 r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
		          r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
		          return r;
			}
			int pNo =1;
			int pSize = 100;
			if(StringUtil.isNotNullAndNotEmpty(pageNum) && StringUtil.isNotNullAndNotEmpty(pageSize)){
				pNo = Integer.parseInt(pageNum.trim());
				if(pNo <= 0){
					pNo =1;
				}
				pSize =	Integer.parseInt(pageSize.trim());
				if(pSize <= 0){
					pSize =100;
				}
			}
			Page<Notice> pagesData = noticeManager.getNoticesByCoachId(coach,Byte.parseByte(type), pNo, pSize);
			r.setData(pagesData.getDataList());
		} catch (Exception e) {
			e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;

    }
	
	@Override
    public ReqResult getNoticeIndex(String userId)
    {
		ReqResult r = ReqResult.getSuccess();
        try {
			Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
			if(coach==null){
				 r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
		          r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
		          return r;
			}
			List<Notice> list = noticeManager.getNoticeCoachIndex(coach);
			r.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;

    }
	
	private ReqResult getNotice(String userId, String userType, String pageNum, String pageSize,Date etime,byte type){
		ReqResult r = ReqResult.getSuccess();
		        try {
					Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
					int pNo =1;
					int pSize = 100;
					if(StringUtil.isNotNullAndNotEmpty(pageNum) && StringUtil.isNotNullAndNotEmpty(pageSize)){
						pNo = Integer.parseInt(pageNum.trim());
						if(pNo <= 0){
							pNo =1;
						}
						pSize =	Integer.parseInt(pageSize.trim());
						if(pSize <= 0){
							pSize =100;
						}
					}
					//byte type = 1;
					Boolean isVip = false;
					if(null != coach.getExtra()){
						if(coach.getExtra().equals("1")){
							isVip = true;
						}
					};
					Page<Notice> pagesData = noticeManager.getNoticesByCoachId(Long.parseLong(userId), coach.getSchoolId().intValue(), coach.getCityId(),
							type,isVip,etime, pNo, pSize);
					r.setData(pagesData.getDataList());
					return r;
				} catch (Exception e) {
					e.printStackTrace();
		            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
		            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		            return r;
				}
		
	}


    /**
     * 获取教练接单范围
     */
    @Override
    public ReqResult getScope(String coachId, String tokenId)
    {
        ReqResult r = ReqResult.getSuccess();

        if (redisUtil.isExist(REDISKEY.COACH_TOKEN + coachId, tokenId))
        {
            Coach c = coachManager.getCoachInfo(Long.parseLong(coachId));
            int scope = c.getAcceptOrderDis();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("distance", scope);

            r.setData(data);
            return r;
        }
        else
        {
            r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
            r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
            return r;
        }
    }


    /**
     * 教练设置接单范围
     */
    @Override
    public ReqResult setScope(String coachId, String distance, String tokenId)
    {
        ReqResult r = ReqResult.getSuccess();

        Coach c = new Coach();
        c.setCoachId(Long.parseLong(coachId));
        c.setAcceptOrderDis(Integer.parseInt(distance));
        coachManager.updateCoach(c);

        return r;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.coach.service.CoachService#addPerformance(java.lang.String, java.lang.String)
     */
    @Override
    public int addPerformanceAndMoney(String coachId, int performance, int money)
    {
        CoachAccount c = coachManager.getCoachMoney(Long.parseLong(coachId));
        if (c == null)
        {
            c = new CoachAccount();
            c.setCoachId(Long.parseLong(coachId));
            money = money > 0 ? money : 0;
            c.setMoney(money);
            performance = performance > 0 ? performance : 0;
            c.setPerformance(performance);
            c.setLastPerTime(new Date());
            coachManager.addCoachAccount(c);
            
            return money;
        }
        Date lastDate = c.getLastPerTime();
        Date curDate = new Date();
        int newPerformance = 0;
        if (TimeUtil.isSameMonth(lastDate, curDate))
        {
            newPerformance = performance + c.getPerformance();
        }
        else
        {
            newPerformance = performance;
        }
        newPerformance = newPerformance > 0 ? newPerformance : 0;
        int newMoney = money + c.getMoney();
        newMoney = newMoney > 0 ? newMoney : 0;
        c.setLastPerTime(new Date());
        c.setPerformance(newPerformance);
        c.setMoney(newMoney);
        coachManager.updateCoachAccount(c);

        return newMoney;

    }

    /* (non-Javadoc)
     * @see com.lili.coach.service.CoachService#getCoachsByIds(java.util.List)
     */
    @Override
    public ReqResult getCoachsByIds(List<Long> coachIds) 
    {
        ReqResult r = ReqResult.getSuccess();
        
        List<CoachVo> coachVos = coachManager.getCoachesByIds(coachIds);
        r.setData(coachVos);
        return r;
    }
    
    @SuppressWarnings("unused")
    private CoachVo genCoachVoById(Long coachId)
    {
        try
        {
            Coach coach = coachManager.getCoachInfo(coachId);
            CoachVo coachVo = null;
            if (coach != null)
            {
                coachVo = new CoachVo();
                coachVo = BeanCopy.copyNotNull(coach, coachVo);
            }
            //Car car = carManager.getCarLevelByCoachId(coachId);
            Car car = carManager.getCarInfo(coach.getCoachCarId());
            if (car != null && coachVo != null)
            {
                coachVo = BeanCopy.copyNotNull(car, coachVo);
            }
            return coachVo;
        }
        catch (Exception e)
        {
            logger.error("", e);
        }
        return null;
    }

	@Override
	public ReqResult getTrfields(String coachId, String keyword, String rid) {
        ReqResult r = ReqResult.getSuccess();

    	Trfield tr = new Trfield();
    	if(null != keyword && !"".equals(keyword.trim())){
    		tr.setName(keyword.trim());
    	}
    	if(null != rid && !"".equals(rid.trim())){
    		tr.setRegion(Integer.parseInt(rid));
    	}
    	Object o = trfieldManager.getTrfield(tr);
        r.setData(o);
        return r;

	}

	@Override
	public ReqResult getStudentOne(String coachId, String studentId,
			String tokenId) {
		ReqResult r = ReqResult.getSuccess();		
		StudentInfoVo si = new StudentInfoVo();
		Student s =	studentManager.getStudentInfo(Long.parseLong(studentId));
		try {
			si = BeanCopy.copy2Null(s, si);
			//20161201 增加学员上课总课时
			OrderVo search=new OrderVo();
			search.setCoachId(Long.parseLong(coachId));
			search.setStudentId(Long.parseLong(studentId));
			OrderQuery orderQuery=new OrderQuery();
			orderQuery.setorderBy(" and order_state in (4,5,6,7) order by course_id asc, rstart desc ");
			List<OrderVo> list = orderService.queryByObjectAnd(search, orderQuery);
			if (list != null && list.size() >0) {
				int classSum = 0;
				CoachClassVo cc = null;
				for (OrderVo orderVo : list) {
					cc = redisUtil.get(REDISKEY.COACH_CLASS_CCID_CACHE + orderVo.getCcid());
					if (cc == null) {
						cc = coachClassService.queryCoachClassById(orderVo.getCcid(), new CoachClassQuery());
						redisUtil.set(REDISKEY.COACH_CLASS_CCID_CACHE + orderVo.getCcid(), cc);
					}
					if (orderVo.getOtype() ==3 && "1".equals(orderVo.getClzNum()) || orderVo.getClzNum() ==1) { //如果只有一个人上课
						long diff = orderVo.getPend().getTime() - orderVo.getPstart().getTime();
						Long diffHours = diff / (60 * 60 * 1000) % 24;
						if (cc != null && null != cc.getBookNum()) {
							diffHours = diffHours/cc.getBookNum();
						}
						classSum += diffHours.intValue();
					}
					/*else if ( orderVo.getOtype() ==1){//现约人数永远都是1个人
						long diff = orderVo.getPend().getTime() - orderVo.getPstart().getTime();
						Long diffHours = diff / (60 * 60 * 1000) % 24;
						classSum += diffHours.intValue();
					}*/
					else {
						classSum += orderVo.getClzNum();
					}
					
				}
				si.setClassSum(classSum);
			}
			r.setData(si);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return r;
		}
	}
	
	
    /* (non-Javadoc)
     * @see com.lili.coach.service.CoachService#isOnline(java.lang.String)
     */
    @Override
    public boolean isOnline(String coachId)
    {
        if (coachId != null && !coachId.isEmpty())
        {
            String isOnline = redisUtil.get(REDISKEY.USER_ONLINE_STATE + coachId);
            if (isOnline != null)
            {
                return Boolean.parseBoolean(isOnline);
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.lili.coach.service.CoachService#setOnline(java.lang.String, boolean)
     */
    @Override
    public void setOnline(String coachId, boolean isOnline)
    {
        if (coachId != null && !coachId.isEmpty())
        {
            redisUtil.set(REDISKEY.USER_ONLINE_STATE + coachId, String.valueOf(isOnline), 2592000); //一个月超时
        }
    }

    /* (non-Javadoc)
     * @see com.lili.coach.service.CoachService#isOnlineByIds(java.util.List)
     */
    @Override
    public Map<Long, Boolean> isOnlineByIds(List<Long> coachIds)
    {
        long searchStart = System.currentTimeMillis();
        if (coachIds == null || coachIds.size() == 0)
        {
            return null;
        }
        List<String> onlineList = redisUtil.mget(REDISKEY.USER_ONLINE_STATE, coachIds);
        Map<Long, Boolean> onlineMaps = new HashMap<Long, Boolean>();
        int size = coachIds.size();
        for (int i = 0; i < size; i++)
        {
            String isOnline = onlineList.get(i);
            if (isOnline == null || isOnline.isEmpty() || isOnline.equals("null"))
            {
                onlineMaps.put(coachIds.get(i), false);
            }
            else {
                onlineMaps.put(coachIds.get(i), Boolean.parseBoolean(isOnline));
            }
        }
        long searchEnd = System.currentTimeMillis();
        logger.error("isOnlineByIds cost: " + (searchEnd - searchStart));
        return onlineMaps;
    }

	@Override
	public ReqResult postAgreement(String userId, String agreement) {
        ReqResult r = ReqResult.getSuccess();

	    Coach c = new Coach();
	    c.setCoachId(Long.parseLong(userId));
	    c.setAgreement(Integer.parseInt(agreement.trim()));
	    coachManager.updateCoach(c);
	
	    return r;
	}

	@Override
	public ReqResult getBrandCarInfo(String coachId, String seqNum,
			String isCommon, String tokenId) {
		
        ReqResult r = ReqResult.getSuccess();
    	List<BrandCar> list = coachManager.getBrandCar(isCommon, seqNum);
        r.setData(list);
        return r;
	}

	@Override
	public ReqResult checkpw(String userId, String userType, String passwd) {
        ReqResult r = ReqResult.getSuccess();
        Coach c = coachManager.getCoachInfo(Long.parseLong(userId));
        String pw = c.getPassword();
        if(!pw.equals(passwd)){
        	r.setCode(ResultCode.ERRORCODE.PASSWORD_ERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_ERROR);
        }
        return r;
	}

	@Override
	public ReqResult addCoachVerfyMaterial(String coachId, String userType,
			String coachCardId, String drType, String drPhoto, String drPhoto2,
			String coachCardPhoto) {
        ReqResult r = ReqResult.getSuccess();

        if ("".equals(coachCardId.trim()))
        {	// 教练证号码错误
            r.setCode(ResultCode.ERRORCODE.COACH_CARD_ERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.COACH_CARD_ERROR);
            return r;
        }
        //20160105 Issue #189 避免同一个身份注册多个账号
        if(null != coachCardId){
        	Coach c = new Coach();
            c.setCoachCard(coachCardId.trim());
            long count = coachManager.getCount(c);
            if(count>0){
                r.setCode(ResultCode.ERRORCODE.COACH_CARD_EXIST);
                r.setMsgInfo(ResultCode.ERRORINFO.COACH_CARD_EXIST);
                return r;
            }
        }
        // 保存教练的教学信息
        Coach coach = new Coach();
        coach.setCoachId(Long.parseLong(coachId));
        if (StringUtils.isNotEmpty(coachCardId))
        {
            coach.setCoachCard(coachCardId.trim());
        }
        if (StringUtils.isNotEmpty(drPhoto))
        {
            coach.setDrPhoto(drPhoto);
        }
        if (StringUtils.isNotEmpty(drPhoto2))
        {
        	coach.setDrPhoto2(drPhoto2);
        }
        if (StringUtils.isNotEmpty(coachCardPhoto))
        {
        	coach.setCoachCardPhoto(coachCardPhoto);
        }
        if(StringUtils.isNotEmpty(drType)){
        	Byte dt = Byte.parseByte(drType.trim());
        	if(dt +0 == ReqConstants.DRIVE_TYPE_C1 || dt+0 == ReqConstants.DRIVE_TYPE_C2 ){
        		coach.setDrType(dt);
        	}
        }
        coach.setCheckDriveIdState(ReqConstants.CHECK_STATE_PROCESS);//20160331上传完成教练证资料后，变更为审核中
        coachManager.updateCoach(coach);

        return r;
	}

	@Override
	public int updateCoach(Coach coach) {
		return coachManager.updateCoach(coach) == 0?0:1;
	}

	@Override
	public int lockCoach(long userId, int state, Date reviveTime, String note) {
		Coach c = coachManager.getCoachInfo(userId);
		c.setState(state);
		c.setWstate(0);
		if(null != reviveTime ){
			c.setReviveTime(reviveTime);
		}
		if(null != note){
			c.setExtra(note);
		}
		coachManager.updateCoach(c);
		try {
			//20161031根据操作判断是冻结还是解冻提现资金
			DepositVo depositVo = new DepositVo();
			depositVo.setUserId(userId);
			depositVo.setUserType(1);
			if (state == 0) { //解封
				//判断教练是否有资金冻结的提现记录
				depositVo.setCheckStatus(6);//资金冻结状态
				List<DepositVo> depList = depositService.queryHasDeposit(depositVo);
				if (depList != null && depList.size() > 0) {
					depositService.updateDepositList(depList, 0);
				}
				
			}
			else { //封号
				//判断教练是否有未审核的提现记录
				depositVo.setCheckStatus(0);//待审核
				List<DepositVo> depList = depositService.queryHasDeposit(depositVo);
				if (depList != null && depList.size() > 0) {
					depositService.updateDepositList(depList, 6);
				}
			}
			
//			CoachClassVo coachclassVo = new CoachClassVo();
//			coachclassVo.setIsdel(1);
//			coachClassService.updateByCoachId(coachclassVo, userId); //20161013
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			JpushMsg jmsg = new JpushMsg();
			jmsg.setAlter(note);
			jmsg.setUserId(c.getCoachId());
			jmsg.setOrderId(c.getCoachId()+JpushConstant.OPERATE.USER_LOCK);
			jmsg.setOperate(JpushConstant.OPERATE.USER_LOCK);
			Message jpush = new Message();
			jpush.setKeys(c.getCoachId()+JpushConstant.OPERATE.USER_LOCK);
			jpush.setTopic(jpushProducer.getCreateTopicKey());
			jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
			jpush.setBody(SerializableUtil.serialize(jmsg));
			jpushProducer.send(jpush);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//20161101 如果是封号，未开始的订单全部取消
		//如果用户锁定，则强制用户下线
		if(state != ReqConstants.USER_STATE_NORMAL){
			redisUtil.delete(REDISKEY.COACH_TOKEN + userId);
			try {
				String orderIdList = ""; //所有现约订单id
				OrderQuery orderQuery = new OrderQuery();
				orderQuery.setSqlPost("and order_state in (1, 2)");
				//取消订单
				List<OrderVo> orderVos = orderService.queryByCoachId(userId, orderQuery);
				if (orderVos != null && orderVos.size() > 0){
					OrderCancelVo oc = new OrderCancelVo();
					Student s = null;
					for (OrderVo orderVo : orderVos){
						orderVo.setOrderState(0);
						orderService.updateByOrderId(orderVo, orderVo.getOrderId());
						
				        oc.setCltime(new Date());
				        oc.setOrderId(orderVo.getOrderId());
				        oc.setPstate(0);
				        oc.setReseaon("教练被封号，强制取消所有未开始的订单");
				        oc.setRetype(99);
				        oc.setUcancel(3);
				        orderCancelService.addOrderCancel(oc);
				        if (orderVo.getOtype() == 1)
				        	orderIdList = orderIdList + ", '" + orderVo.getOrderId() + "'";
				        s = studentManager.getStudentInfo(orderVo.getStudentId());
				        if (s != null && ReqConstants.STUDNET_PREPARE_START_CLASS == (int)s.getEventState() && orderVo.getOrderId().equals(s.getEventDesc())){
				        	s.setEventState((byte)ReqConstants.STUDENT_STATUS_OFF_CLASS);
				        	studentManager.updateStudent(s);
				        }
					}
				}
				CoachClassQuery coachclassQuery = new CoachClassQuery(); 
				if ("".equals(orderIdList))
					orderIdList = ", ";
				coachclassQuery.setSqlPost("and cstart > current_timestamp() or order_Id in (" + orderIdList.substring(2) + ")");
				//取消排班
				List<CoachClassVo> coachClassVos = coachClassService.queryByCoachId(userId, coachclassQuery);
				if (coachClassVos != null && coachClassVos.size() > 0){
					for (CoachClassVo coachClassVo : coachClassVos){
						coachClassVo.setIsdel(1);
						coachClassService.updateByCcid(coachClassVo, coachClassVo.getCcid());
					}
				}
				//发送考场短信
				Page<ExamPlaceOrder> epoPage = examPlaceOrderManager.getMyExamPlaceOrder(Long.toString(userId), "", "1", "", "");
				List<ExamPlaceOrder> epoVos = epoPage.getDataList();
				if (epoVos != null && epoVos.size() > 0){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Map<Integer, String> msgs = new HashMap<>();
					for (ExamPlaceOrder epo : epoVos){
						msgs.put(1, epo.getPlaceName());
						msgs.put(2, sdf.format(epo.getPstart()));
						msgs.put(3, sdf.format(epo.getPend()).substring(11));
						authcodeService.sendMsgById("129411", c.getPhoneNum(), msgs);
						logger.info(" ************ test : 【喱喱教练】您的账号已被封禁，" + msgs.get(1) + msgs.get(2) + "-" + msgs.get(3) + "的预约订单仍然有效，请准时进场。");
						msgs.clear();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public ReqResult saveClassTemp(String handleType,List<CoachClassTempVo> classTempList, String tempId) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
        
		try {
			boolean isSuc = true;
			if("1".equals(handleType)){ //新建保存
				Date date = new Date(); 
		    	int number=(int)(Math.random()*1000000);
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd"); 
		    	Integer tempNo = Integer.valueOf(dateFormat.format(date) + number); //模板ID = 当前月日+随机6位
				BeanCopy.setListField(classTempList, "tempId", tempNo);
				isSuc = coachManager.saveClassTemp(classTempList);
			}
			else if("2".equals(handleType)){ //更新
				isSuc = coachManager.updateClassTemp(classTempList);
			}
			else if("3".equals(handleType)){ //关闭
				if(!"".equals(tempId) && tempId != null){
					isSuc = coachManager.closeClassTemp(Integer.valueOf(tempId));
				}
			}
			
			if(!isSuc){
				reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
	            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
				return reqResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return reqResult;
		}
		return reqResult;
	}

	@Override
	public ReqResult queryTemplate(String userId, String tempId, String tempName) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		List<CoachClassTempQuery> tempDetailList = new ArrayList<CoachClassTempQuery>();
		List<CoachClassTempNameVo> tempNameList = new ArrayList<CoachClassTempNameVo>();
		try {
			CoachClassTemp coachClassTempVo = new CoachClassTemp();
			coachClassTempVo.setCoachId(Long.parseLong(userId));
			if (!"".equals(tempId) && tempId != null) {
				coachClassTempVo.setTempId(Integer.parseInt(tempId));
			}
			if (!"".equals(tempName) && tempName != null) {
				coachClassTempVo.setTempName(tempName);
			}
			tempNameList  = coachManager.queryTemplateName(coachClassTempVo);
			tempDetailList  = coachManager.queryTemplate(coachClassTempVo);
			reqResult.setData("tempNameList", tempNameList);
			reqResult.setData("classTempList", tempDetailList);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return reqResult;
	}
	
	@Override
	public ReqResult addClassForTemp(Long userId, Date date, String tempId) {
		ReqResult reqResult = new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
        
		try {
			String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd") + " ";
			//根据模板ID查询排版模板
			CoachClassTemp coachClassTempVo = new CoachClassTemp();
			coachClassTempVo.setCoachId(userId);
			coachClassTempVo.setTempId(Integer.parseInt(tempId));
			List<CoachClassTempQuery> classTempList = coachManager.queryTemplate(coachClassTempVo);
			SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
			boolean isToday = false;
			if (classTempList != null ) {
				CoachClassVo coachClassVo = null;
				String arrMsg = null;
				
				//循环遍历2次，第一次校验排班时间是否冲突等
				Date endDate = null;
				Date nextDate = date;
				Date now = new Date();
				String enddate = null;
				for (CoachClassTempQuery classTempVo : classTempList){
					coachClassVo = new CoachClassVo();
					if (endDate != null) {
						long endTimeLong=TimeUtil.calcDistanceMillis(endDate,TimeUtil.parseDate(justdate + formatter.format(classTempVo.getCstart())));
						if(endTimeLong<0 || endTimeLong > 432000000) {
							logger.error(coachClassVo+" can't set because class now has start or out of 5day="+endTimeLong);
							reqResult.setCode(ResultCode.ERRORCODE.ORDER_COACH_ARRANGEFUL);
							reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_COACH_ARRANGEFUL);
							return reqResult;
						}
					}
					isToday = DateUtil.isSameDate(classTempVo.getCstart(), classTempVo.getCend());
					if (!isToday) { //如果两个日期不是同一天，结束日期+1
						nextDate = TimeUtil.addSpecialCurTime(date,Calendar.DATE, 1);
						enddate=TimeUtil.getDateFormat(nextDate, "yyyy-MM-dd") + " ";
						endDate = TimeUtil.parseDate(enddate + formatter.format(classTempVo.getCend()));
					}
					else {
						endDate = TimeUtil.parseDate(justdate + formatter.format(classTempVo.getCend()));
					}
					
					coachClassVo.setCoachId(userId);
					coachClassVo.setCtype(ReqConstants.BOOK_TYPE_FUTURE);// 排班类型 默认预约
					coachClassVo.setMaxNum(classTempVo.getMaxNum());
					coachClassVo.setCstart(TimeUtil.parseDate(justdate + formatter.format(classTempVo.getCstart())));
					coachClassVo.setCend(endDate);
					coachClassVo.setCarId(classTempVo.getCoachCarId());
					coachClassVo.setCourseId(classTempVo.getCourseId());
					coachClassVo.setPlaceId(classTempVo.getFieldId());
					coachClassVo.setPlaceName(classTempVo.getFieldName());
					coachClassVo.setLae(classTempVo.getLae());
					coachClassVo.setLge(classTempVo.getLge());
					coachClassVo.setDltype(classTempVo.getDrType());
					
					//2.起始时间判断
					long timeLong=TimeUtil.calcDistanceMillis(now,coachClassVo.getCstart());
					if(timeLong<=0 || timeLong>432000000) {
						logger.error(coachClassVo+" can't set because class now has start or out of 5day="+timeLong);
						reqResult.setCode(ResultCode.ERRORCODE.TIME_OUT_CLASS);
						reqResult.setMsgInfo(ResultCode.ERRORINFO.TIME_OUT_CLASS);
						return reqResult;
					}
					
					//非删除需要判断时间冲突
					CoachClassVo buz=coachClassService.isCoachIdle(coachClassVo.getCoachId(),coachClassVo.getCcid(), coachClassVo.getOrderId(), coachClassVo.getCstart(),coachClassVo.getCend(),true);
					if (buz != null) {
						logger.error(coachClassVo+" can't set because buz="+buz);
						reqResult.setCode(ResultCode.ERRORCODE.ORDER_COACH_ARRANGEFUL);
						reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_COACH_ARRANGEFUL);
						return reqResult;
					}
				}
				
				//当第一次遍历校验通过后，开排班
				Coach coachInfo = coachManager.getCoachInfo(userId);
				if(coachInfo.getCityId() == null && "".equals(coachInfo.getCityId())) {
					reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
				    reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
					return reqResult;
				}
				Integer maxNum;
				Integer duration;
				boolean isToday2 = false;
				String enddate2 = null;
				Date nextDate2 = date;
				for (CoachClassTempQuery classTempVo : classTempList){
					isToday2 = DateUtil.isSameDate(classTempVo.getCstart(), classTempVo.getCend());
					if (!isToday2) { //如果两个日期不是同一天，结束日期+1
						nextDate2 = TimeUtil.addSpecialCurTime(date,Calendar.DATE, 1);
						enddate2=TimeUtil.getDateFormat(nextDate2, "yyyy-MM-dd") + " ";
						endDate = TimeUtil.parseDate(enddate2 + formatter.format(classTempVo.getCend()));
					}
					else {
						endDate = TimeUtil.parseDate(justdate + formatter.format(classTempVo.getCend()));
					}
					
					coachClassVo = new CoachClassVo();
					coachClassVo.setCoachId(userId);
					coachClassVo.setCtype(ReqConstants.BOOK_TYPE_FUTURE);// 排班类型 默认预约
					coachClassVo.setMaxNum(classTempVo.getMaxNum());
					coachClassVo.setCstart(TimeUtil.parseDate(justdate + formatter.format(classTempVo.getCstart())));
					coachClassVo.setCend(endDate);
					coachClassVo.setCarId(classTempVo.getCoachCarId());
					coachClassVo.setCourseId(classTempVo.getCourseId());
					coachClassVo.setPlaceId(classTempVo.getFieldId());
					coachClassVo.setPlaceName(classTempVo.getFieldName());
					coachClassVo.setLae(classTempVo.getLae());
					coachClassVo.setLge(classTempVo.getLge());
					coachClassVo.setDltype(classTempVo.getDrType());
					//根据时间、城市、科目、C1查询价格
					List<CoachClassPriceVo> coachPriceList = coachClassService.queryCoachPrice(userId, date,  classTempVo.getCourseId(), null, String.valueOf(classTempVo.getDrType()),	 null,  null); 
					//根据人数跟时长遍历数组返回价格
					maxNum = classTempVo.getMaxNum();
					duration = TimeUtil.timeSpan(classTempVo.getCstart(),  classTempVo.getCend(),"hour");
					if (coachPriceList != null) {
						for (CoachClassPriceVo coachClassPriceVo : coachPriceList) {
							if (coachClassPriceVo.getMaxNum() == maxNum) {
								if (coachClassPriceVo.getDuration() == duration ) {
									coachClassVo.setPrice(coachClassPriceVo.getPrice());
									break;
								}
							}
						}
					}
					else {
						if (duration != null && duration != 0) {
							coachClassVo.setPrice(OrderConstant.defaultPrice * duration);//小时*180
						}
						else {
							coachClassVo.setPrice(OrderConstant.defaultPrice);//180
						}
					}
					
					arrMsg = coachClassService.arrangeClass(coachClassVo); //设置排班
					if(!ResultCode.ERRORCODE.SUCCESS.equals(arrMsg)){
						if ("3".equals(arrMsg)) {
							reqResult.setCode(ResultCode.ERRORCODE.TIME_OUT_CLASS);
							reqResult.setMsgInfo(ResultCode.ERRORINFO.TIME_OUT_CLASS);
						}
						else {
							reqResult.setCode(arrMsg);
							reqResult.setMsgInfo(ResultCode.getCodeInfo(arrMsg));
						}
						return reqResult;
					}
				}
			}
			else {
				reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
			    reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
				return reqResult;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqResult;
	}

	@Override
	public ReqResult getErrorAppealItems() {
		ReqResult reqResult = ReqResult.getSuccess();
		reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		try {
			List <ErrorAppealItem> itemList  = coachErrorAppealManager.getErrorAppealItem();
			reqResult.setData(itemList);
		} catch (NumberFormatException e) {
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return reqResult;
		}
		return reqResult;
	}

	@Override
	public ReqResult addErrorAppeal(ErrorAppeal errorAppeal) {
		ReqResult reqResult = ReqResult.getSuccess();
		reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		if (StringUtil.isNullOrEmpty(errorAppeal.getItemIds()) 
				&& StringUtil.isNullOrEmpty(errorAppeal.getAppealContent())) {
			reqResult.setCode(ResultCode.ERRORCODE.CONTENT_EMPTY_ERROR);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.CONTENT_EMPTY_ERROR);
			return reqResult;
		}
		try {
			errorAppeal.setCtime(new Date());
			coachErrorAppealManager.addErrorAppeal(errorAppeal);
		} catch (NumberFormatException e) {
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return reqResult;
		}
		return reqResult;
	}
	
	@Override
	public void doInOutCarStatus(CoachStatusRecord record) throws Exception{
		if(record.getStatus()==0){
			
        	
        	CoachStatusRecord latest=coachManager.getLatestStatusRecord(record);
        	logger.debug("find previous out car record***************************");
        	if(latest!=null&&latest.getStatus()==1){//最近一条是出车状态
        		int duration=(int)(System.currentTimeMillis()- latest.getCreateDate().getTime())/(1000*60);
        		record.setDuration(duration);
        	}else{
        		logger.warn("latest record is in car, something wrong");
        	}
        	coachManager.addCoachStatusRecord(record);
        	coachManager.addCoachInCount(record);
		}else{
			coachManager.addCoachStatusRecord(record);
        	coachManager.addCoachOutCount(record);
		}
	}

	@Override
	public ReqResult addRegCoach(String userId, String drPhoto, String drPhoto2, String isNewDrPhoto, String cityId,  List<CarCheck> carJsonList) {
		ReqResult reqResult = ReqResult.getSuccess();
		
		try {
			Long coachId = Long.parseLong(userId);
			Coach coach = new Coach();
			
			if (carJsonList != null && carJsonList.size() > 0) {
				if (carJsonList.size() > 5) { //一个教练最多5辆车
					reqResult.setCode(ResultCode.ERRORCODE.CAR_IS_LIMIT);
			        reqResult.setMsgInfo(ResultCode.ERRORINFO.CAR_IS_LIMIT);
					return reqResult;
				}
				//判断车牌是否有重复
				Set<String> carNoSet = new HashSet<String>();
				for (CarCheck carCheck : carJsonList) {
					carNoSet.add(carCheck.getCarNo());
					if (carCheck.getCarNo() != null && !carCheck.getCarNo().matches(carNoRegex)) { //校验车牌是否有效
						reqResult.setCode(ResultCode.ERRORCODE.CARNO_IS_ILLEGAL_TWO);
						reqResult.setMsgInfo(ResultCode.ERRORINFO.CARNO_IS_ILLEGAL_TWO);
	        			return reqResult;
	        		}
				}
				if (carJsonList.size() != carNoSet.size()) {
					reqResult.setCode(ResultCode.ERRORCODE.CARNO_LIST_IS_EXIST);
			        reqResult.setMsgInfo(ResultCode.ERRORINFO.CARNO_LIST_IS_EXIST);
					return reqResult;
				}
				
				//删除之前的所有审核绑定关系，重新绑定
				CarCheck carCheckOld= new CarCheck();
				carCheckOld.setCoachId(coachId);
				carCheckOld.setType(2);
				List<CarCheck> carCheckListOld = carManager.getCarCheckByCoachId(carCheckOld);
				//删除之前的审核绑定信息
				carManager.deleteCarCheck(carCheckOld);
				CoachCar coachCar = null;
				for (CarCheck carCheck : carCheckListOld) {
					coachCar = new CoachCar();
					coachCar.setCoachId(coachId);
					coachCar.setCarId(carCheck.getCarId());
					carManager.deleteCoachCar(coachCar); //删除绑定车辆信息
				}
				
				for (CarCheck carCheck : carJsonList) {
					carCheck.setType(2);//注册车是通用车
					carManager.addRegisterCoachCar(carCheck, coachId, true);
				}
			
				if(StringUtils.isNotEmpty(isNewDrPhoto)){
					coach.setIsNewDrPhoto(Integer.parseInt(isNewDrPhoto)); 
				}
				coach.setDrPhoto(drPhoto);
				coach.setDrPhoto2(drPhoto2);
				coach.setCoachId(coachId);
				coach.setCheckDrState(1);
				if (cityId != null && !"".equals(cityId)) {
					coach.setCityId(Integer.parseInt(cityId));
					Region region  = regionManager.getRegionInfo(Integer.parseInt(cityId));
					if(region !=null && region.getRegion() != null) {
						coach.setCityName(region.getRegion());
					}
				}
				coachManager.updateCoach(coach); 	//新增、修改信息，更新状态为待审核
			}
		} catch (Exception e) {
			e.printStackTrace();
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return reqResult;
		} 
		
		return reqResult;
	}

	@Override
	public ReqResult checkInfo(String userId) {
		ReqResult reqResult = ReqResult.getSuccess();
		try {
			CoachCheck coachCheck = new CoachCheck();
			Long coachId = Long.parseLong(userId);
			Coach coach = coachManager.getCoachInfo(coachId);
			if (coach != null) {
				BeanCopy.copyAll(coach, coachCheck);
			}
			CarCheck carCheck= new CarCheck();
			carCheck.setCoachId(coachId);
			carCheck.setType(2);//只显示审核车
			List<CarCheck> carCheckList = carManager.getCarCheckByCoachId(carCheck);
			coachCheck.setCarCheckList(carCheckList);
			reqResult.setData(coachCheck);
		} 
		catch (Exception e) {
			e.printStackTrace();
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return reqResult;
		}
		
		return reqResult;
	}

	@Override
	public ReqResult getRegistCity(String cityId, String cityName, String pid, String rlevel) {
		ReqResult reqResult = ReqResult.getSuccess();
		try {
			Region region = new Region();
			if (StringUtils.isNotEmpty(cityId)) {
				region.setRid(Integer.parseInt(cityId));
			}
			if (StringUtils.isNotEmpty(cityName)) {
				region.setRegion(cityName);
			}
			if (StringUtils.isNotEmpty(pid)) {
				region.setPid(Integer.parseInt(pid));
			}
			if (StringUtils.isNotEmpty(rlevel)) {
				region.setRlevel(Integer.parseInt(rlevel));
			}
			
			List<Region> regionList = regionManager.getRegistCity(region);
			//由于其他地方广州已下架，手工加上该城市
			Region e = new Region();
			e.setRegion("广州市");
			e.setRid(100102);
			e.setRlevel(2);
			e.setPid(0);
			e.setShield(1);
			regionList.add(e);
			reqResult.setData(regionList);
		} 
		catch (Exception e) {
			e.printStackTrace();
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return reqResult;
		}
		
		return reqResult;
	}

	@Override
	public ReqResult carCheckPass(Long coachId,Integer checkDrState, String verifier) {
		ReqResult reqResult = ReqResult.getSuccess();
		try {
			//判断教练下的车是否有存在驾校车，车类型是否一致，如不一致，则返回
			CarCheck carCheckIsSchool= new CarCheck();
			carCheckIsSchool.setCoachId(coachId);
			carCheckIsSchool.setType(2);//只显示审核车
			List<CarCheck> carCheckList = carManager.getCarCheckByCoachId(carCheckIsSchool);
			Car car = null;
			Integer coachCarId = 0;
			for (CarCheck carCheck : carCheckList) { 
				if (carCheck.getCarId() != null && !"".equals(carCheck.getCarId())) {
					car = carManager.getCarInfo(carCheck.getCarId());
					if (car!= null &&  car.getSchoolId()!= null && car.getSchoolId() == 1) {
						if (car.getDriveType()!= null &&  car.getDriveType().intValue() != carCheck.getDriveType()  && checkDrState != null && checkDrState == 2) {
							reqResult.setCode(ResultCode.ERRORCODE.CAR_CANNOT_CHECK);
							reqResult.setMsgInfo(ResultCode.ERRORINFO.CAR_CANNOT_CHECK);
							return reqResult;
						}
					}
					else if (car!= null &&  car.getSchoolId()!= null && car.getSchoolId() == 0) { //如果是注册车
						if (car.getDriveType()!= null &&  car.getDriveType().intValue() != carCheck.getDriveType()) {//如果注册车辆信息不一致
							car.setDriveType(carCheck.getDriveType().byteValue());
							carManager.updateCar(car, coachId);
						}
					}
					coachCarId = carCheck.getCarId();
				}
			}
			if (coachCarId != null && coachCarId != 0) {
				Coach coachUp = new Coach();
				coachUp.setCoachId(coachId);
				coachUp.setCoachCarId(coachCarId);
				coachManager.updateCoach(coachUp);//IOS增加默认出车
			}
			
			CarCheck carCheck= new CarCheck();
			carCheck.setCoachId(coachId);
			carCheck.setUpdater(verifier);
			carCheck.setIsNewCarDrive(0);
			carCheck.setIsNewCarImg(0);
			carCheck.setIsNewDrivePhoto(0);
			carManager.updateCarCheck(carCheck);
			
			//增加消息推送
			JpushMsg jmsg = new JpushMsg();
			Message jpush = new Message();
			String message = "";
			Coach coach = coachManager.getCoachInfo(coachId);
			Map<Integer, String> msgs = new HashMap<Integer, String>();
			msgs.put(1, coach.getName());
			if(checkDrState != null && checkDrState == 2) {//已验证
				message = "恭喜您，您提交的接单审核信息已通过审核。";
				jmsg.setOperate(JpushConstant.OPERATE.COACHCHECKPASS);
				jpush.setKeys(coachId+JpushConstant.OPERATE.COACHCHECKPASS);
				jmsg.setAlter(message);
				jmsg.setUserId(coachId);
				jpush.setTopic(jpushProducer.getCreateTopicKey());
				jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
				jpush.setBody(SerializableUtil.serialize(jmsg));
				jpushProducer.send(jpush);
				logger.info("*********************************** Send Jpush Message Info, CoachId : " +  coachId +", Message :"+ message);
				
				//短信通知
				authcodeService.sendMsgById(passMsgId, coach.getPhoneNum(), msgs);
				logger.info("*********************************** Send Pass Message Info, phoneNum : " + coach.getPhoneNum() +", Message :"+ msgs);
			}
			else if (checkDrState != null && checkDrState == 3) { //验证失败
				message = "您提交的接单认证资料未通过审核。";
				jmsg.setOperate(JpushConstant.OPERATE.COACHCHECKUNPASS);
				jpush.setKeys(coachId+JpushConstant.OPERATE.COACHCHECKUNPASS);
				jmsg.setAlter(message);
				jmsg.setUserId(coachId);
				jpush.setTopic(jpushProducer.getCreateTopicKey());
				jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
				jpush.setBody(SerializableUtil.serialize(jmsg));
				jpushProducer.send(jpush);
				logger.info("*********************************** Send Jpush Message Info, CoachId : " +  coachId +", Message :"+ message);
				
				
				//短信通知
				authcodeService.sendMsgById(unPassMsgId, coach.getPhoneNum(), msgs);
				logger.info("*********************************** Send UnPass Message Info, phoneNum : " + coach.getPhoneNum() +", Message :"+ msgs);
			}
			
			//推送成功后，保存到用户消息中心
			Notice notice = new Notice();
			notice.setTitle("系统消息");
			notice.setUserId(coachId);
			notice.setUserType((byte)ReqConstants.USER_TYPE_COACH);
			notice.setType((byte) 5);  //type=5系统通知
			notice.setDigest(message);  //content改为存html内容
			notice.setTime(new Date());
			notice.setIsdel((byte)0);
			noticeManager.addNotice(notice);			
		} 
		catch (Exception e) {
			e.printStackTrace();
			reqResult.setCode(ResultCode.ERRORCODE.EXCEPTION);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return reqResult;
		}
		
		return reqResult;
	}
}
