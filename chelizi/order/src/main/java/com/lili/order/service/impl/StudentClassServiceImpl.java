package com.lili.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.coach.dto.Car;
import com.lili.coach.dto.Coach;
import com.lili.coach.dto.Trfield;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.TrfieldManager;
import com.lili.coach.vo.CoachVo;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.Page;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.VersionUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.file.manager.FileManager;
import com.lili.file.vo.CoursenewVo;
import com.lili.location.service.LocationService;
import com.lili.order.dao.mapper.StudentBookPriceMapper;
import com.lili.order.dao.mapper.StudentClassMapper;
import com.lili.order.dao.mapper.StudentClassPoolMapper;
import com.lili.order.dto.StudentBookPrice;
import com.lili.order.dto.StudentBookPriceExample;
import com.lili.order.dto.StudentClass;
import com.lili.order.dto.StudentClassExample;
import com.lili.order.dto.StudentClassPool;
import com.lili.order.dto.StudentClassPoolExample;
import com.lili.order.dto.StudentClassPoolKey;
import com.lili.order.dto.StudentClassPoolVo;
import com.lili.order.dto.StudentClassVo;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.CoachCommentService;
import com.lili.order.service.OrderService;
import com.lili.order.service.StudentClassService;
import com.lili.order.service.thread.SearchCoachClass;
import com.lili.order.service.thread.SearchCoachProperty;
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.order.vo.SearchVo;
import com.lili.student.dto.Student;
import com.lili.student.dto.StudentAccount;
import com.lili.student.manager.StudentManager;

public class StudentClassServiceImpl implements StudentClassService {
	private static Logger log = LoggerFactory.getLogger(StudentClassServiceImpl.class);
	@Autowired
	private StudentClassMapper studentClassMapper;
	@Autowired
	private StudentClassPoolMapper studentClassPoolMapper;
	@Autowired
	private StudentBookPriceMapper studentBookPriceMapper;
    @Autowired
    private FileManager fileManager;
//	@Autowired
//	private RedisUtil redisUtil;
	@Autowired
	private LocationService locationService;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private CoachClassService coachClassService;
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	@Autowired
	private OrderService orderService;
    @Autowired
    private CarManager carManager;
	@Autowired
	private TrfieldManager trfieldManager;
	@Autowired
	private NoticeManager noticeManager;
	@Autowired
	private CoachCommentService coachCommentService;
	
	@Autowired
	private StudentManager studentManager;
	
	ExecutorService threadPool = Executors.newCachedThreadPool();
	
	
	@Override
	public ReqResult getClassPrice(String userId, String userType,
			String courseId, String cStart, String clznum, String cityId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			StudentClass sc = new StudentClass();
			int regionId = 100100; //默认深圳
			if(null != cityId && !"".equals(cityId.trim())){
				regionId = Integer.parseInt(cityId.trim());
			}
			sc.setRegionId(regionId);
			int courseTmpId = 1;
			if(null != courseId && !"".equals(courseId)){
				courseTmpId = Integer.parseInt(courseId);
			}
			sc.setCourseId(courseTmpId);
			Date date = new Date(Long.parseLong(cStart) * 1000);
			sc.setCstart(date);
			int num = 1;
			if(null != clznum && !"".equals(clznum)){
				num = Integer.parseInt(clznum);
			}
			sc.setClznum((byte)num);
			int price = this.getClassPrice(sc);
			r.setData(price);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public int getClassPrice(StudentClass studentClass) {
		int price =20000; //默认值
		
		//20170103 增加2017节假日价格，固定写死，改动小
		List<Date> dateList = new ArrayList<Date>();
    	dateList.add(TimeUtil.parseDate("2017-01-27", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-01-30", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-02-01", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-02-02", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-04-03", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-04-04", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-05-01", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-05-29", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-05-30", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-10-02", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-10-03", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-10-04", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-10-05", "yyyy-MM-dd"));
    	dateList.add(TimeUtil.parseDate("2017-10-06", "yyyy-MM-dd"));
    	Date today = TimeUtil.parseDate(TimeUtil.getDateFormat(studentClass.getCstart(), "yyyy-MM-dd"), "yyyy-MM-dd");
    	int week = 1;
    	if (dateList.contains(today)) {
    		week = 6;
    	}
    	else {
    		week = DateUtil.getWeek(studentClass.getCstart());
    	}
		
		StudentBookPriceExample example = new StudentBookPriceExample();
		example.createCriteria()
		.andRegionIdEqualTo(studentClass.getRegionId())
		.andCourseTmpIdEqualTo(studentClass.getCourseId())
		.andDateRuleLike("%"+week+"%");
		
		List<StudentBookPrice> sbps =studentBookPriceMapper.selectByExample(example);
		if(null != sbps && sbps.size()>0){
			price = sbps.get(0).getPrice();
		}else {
			log.error("StudentClassServiceImpl-->getClassPrice-->cStart="+studentClass.getCstart() + ";week="+week);
		}
		int num = studentClass.getClznum();

		return price * num;
		
	}

	@Override
	public ReqResult addStudentClass(StudentClass studentClass,String v) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// 前置检查，学员是否可以排班
			// （1）约束：可下3小时后到未来两天的订单，全天24小时可约
			long currentTime = System.currentTimeMillis();
			Date d1 = new Date(currentTime + 3*60*60*1000); //三个小时后
			//Date d2 = new Date(currentTime + 48*60*60*1000); //两天后
			// 两天后的凌晨截止
	    	Calendar gdate = Calendar.getInstance(); 
	    	gdate.add(gdate.DATE, 4);
	    	String aa = new SimpleDateFormat("yyyy-MM-dd").format(gdate.getTime()) + " 23:59:59";
	    	Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(aa);
			
			Date cstart = studentClass.getCstart();
			if(cstart.before(d1) || cstart.after(d2)){
			    r.setCode(ResultCode.ERRORCODE.CLASS_PLAN_TIME_ERROR);
			    r.setMsgInfo(ResultCode.ERRORINFO.CLASS_PLAN_TIME_ERROR);
			    return r;
			}
			// （2）最多可以选择4课时
			byte clzNum = studentClass.getClznum();
			if(clzNum>= 1 && clzNum <= 4){
				Date cend = new Date(cstart.getTime() + clzNum*60*60*1000);
				studentClass.setCend(cend);
			}else {
			    r.setCode(ResultCode.ERRORCODE.ORDER_CLASSTIMEOUT);
			    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_CLASSTIMEOUT);
			    return r;
			}
			
			
			// （3）如果已经有未完成的自主预约订单，不能排班
			StudentClassExample example = new StudentClassExample();
			example.createCriteria().andStudentIdEqualTo(studentClass.getStudentId())
			.andStateEqualTo((byte)0) ;//'排班状态：0-正常；1-已取消；2-已成功；3-已超时；' 并且不是定向预约的订单
			List<StudentClass> nowOrder = studentClassMapper.selectByExample(example);
			if(null != nowOrder && nowOrder.size()>0){
			    r.setCode(ResultCode.ERRORCODE.ORDER_NO_GIVE);
			    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_NO_GIVE);
			    return r;
			}
			
			
			// （4）上课时间与已有订单时间冲突，不能排班
			OrderVo buz= coachClassService.isStudentIdle(studentClass.getStudentId(), null, studentClass.getCstart(), studentClass.getCend(), true);
			if(null != buz){
			    r.setCode(ResultCode.ERRORCODE.ORDER_STU_BUZ);
			    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_STU_BUZ);
			    return r;
			}
			//	（4.1）非整点不能下单。为啥要有这个限制呢？
			
			// （5）是否有未支付订单
			OrderQuery query=new OrderQuery();
			query.setGroupBy("and pay_state=0 and order_state !=0");//未付款、未取消
			List<OrderVo> list=orderService.queryByStudentId(studentClass.getStudentId(), query);
			if(list != null && list.size() > 0){
				r.setCode(ResultCode.ERRORCODE.ORDER_IS_NOT_PAY);
				r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_NOT_PAY);
				return r;
			}
			
			//	（6）添加课程信息
			CoursenewVo course = fileManager.getCoursenewBycourseid(studentClass.getCourseId());
			if (null != course) {
				studentClass.setCourseName(course.getCoursenewname());
			}else {
				return ReqResult.getParamError();
			}
			//	（7）获取订单价格
			int price = this.getClassPrice(studentClass);
			studentClass.setPrice(price);
			
			 // 20161116如果余额不足，不能约课
			Student student = studentManager.getStudentInfo(studentClass.getStudentId());
			if(student.getIsImport() == (byte)0) {
				StudentAccount sa = studentManager.getStudentMoney(studentClass.getStudentId());
				int money = 0;
				if (null != sa) {
					money = sa.getMoney();
				}
				if (money <  price) {
					r.setCode(ResultCode.ERRORCODE.BALANCE_IS_INSUFFICIENT);
					r.setMsgInfo(ResultCode.ERRORINFO.BALANCE_IS_INSUFFICIENT);
					return r;
				}
			}
			
			// 保存学员排班信息
			studentClass.setOrderId(StringUtil.getOrderId());
			studentClass.setDataVersion(v);
			studentClassMapper.insertSelective(studentClass);
			
			//20160831 预约时间在22点后到7点，提醒可能匹配不到教练
	        Calendar nowCalendar = Calendar.getInstance();
	        nowCalendar.setTime(new Date(currentTime));
	        int nowHour = nowCalendar.get(Calendar.HOUR_OF_DAY);
	        if(nowHour >= 22 || nowHour <= 6){
	        	r.setData("该时段内预约教练可能在休息，如匹配不到教练，请您在正常工作时间段内进行预约");
	        }
			
			// 异步查找附近符合条件的教练并推送
			this.findCoachAndNotice(studentClass);
			if(log.isDebugEnabled()){
				log.debug("StudentClassServiceImpl-->addStudentClass-->time cost="+(System.currentTimeMillis()-currentTime));
			}
		} catch (Exception e) {
			log.error("StudentClassServiceImpl-->addStudentClass-->"+e);
			e.printStackTrace();
		}
		return r;
	}
	
	@Override
	public ReqResult addStudentClass(StudentClass studentClass,long coachId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// 前置检查，学员是否可以排班
			// （1）约束：可下3小时后到未来两天的订单，全天24小时可约
			long currentTime = System.currentTimeMillis();
			Date d1 = new Date(currentTime + 3*60*60*1000); //三个小时后
			//Date d2 = new Date(currentTime + 48*60*60*1000); //两天后
			// 两天后的凌晨截止
	    	Calendar gdate = Calendar.getInstance(); 
	    	gdate.add(gdate.DATE, 4);
	    	String aa = new SimpleDateFormat("yyyy-MM-dd").format(gdate.getTime()) + " 23:59:59";
	    	Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(aa);
			
			Date cstart = studentClass.getCstart();
			//log.info("****************** "+cstart+" "+d1+" "+d2);
			if(cstart.before(d1) || cstart.after(d2)){
			    r.setCode(ResultCode.ERRORCODE.CLASS_PLAN_TIME_ERROR);
			    r.setMsgInfo(ResultCode.ERRORINFO.CLASS_PLAN_TIME_ERROR);
			    return r;
			}
			// （2）最多可以选择4课时
			byte clzNum = studentClass.getClznum();
			if(clzNum>= 1 && clzNum <= 4){
				Date cend = new Date(cstart.getTime() + clzNum*60*60*1000);
				studentClass.setCend(cend);
			}else {
			    r.setCode(ResultCode.ERRORCODE.ORDER_CLASSTIMEOUT);
			    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_CLASSTIMEOUT);
			    return r;
			}
			
			Coach coach = coachManager.getCoachInfo(coachId);
			
			if(coach.getIsImport()!=1&&coach.getCheckDrState()!=2){//注册教练没有认证
				r.setCode(ResultCode.ERRORCODE.COACH_NO_AUTH);
			    r.setMsgInfo(ResultCode.ERRORINFO.COACH_NO_AUTH);
			    return r;
			}
			
			//20161124 教练车判断改为查库，接单的时候可以选择车辆，兼容有多辆车以及多辆C1或C2车的情况
			List<Car> carList = carManager.getCarByCoachId(coachId);
			if(carList != null && !carList.isEmpty()){
				boolean carC1 = false, carC2 = false;
				for (Car car : carList) {
					if (car.getDriveType() == (byte) 1)
						carC1 = true;
					else if (car.getDriveType() == (byte) 2)
						carC1 = true;
				}
				int courseId = studentClass.getCourseId();
				if (!((courseId < 10 && carC1) || (courseId > 10 && carC2))) {
					r.setCode(ResultCode.ERRORCODE.NO_APPROPRIATE_CAR);
				    r.setMsgInfo(ResultCode.ERRORINFO.NO_APPROPRIATE_CAR);
				    return r;
				}
			} else {
				r.setCode(ResultCode.ERRORCODE.NO_APPROPRIATE_CAR);
			    r.setMsgInfo(ResultCode.ERRORINFO.NO_APPROPRIATE_CAR);
			    return r;
			}
			
			if(studentClass.getCourseId()!=5&&studentClass.getCourseId()!=15&&studentClass.getCourseId()!=2&&studentClass.getCourseId()!=12&&studentClass.getCourseId()!=4&&studentClass.getCourseId()!=14){//不是陪驾
				if(coach.getIsImport()!=1){//新注册教练
					r.setCode(ResultCode.ERRORCODE.ORDER_NO_PRIVILEGE);
				    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_NO_PRIVILEGE);
				    return r;
				}
			}
			
			// （3）如果已经有未完成的自主预约订单，不能排班
			StudentClassExample example = new StudentClassExample();
			example.createCriteria().andStudentIdEqualTo(studentClass.getStudentId())
			.andStateEqualTo((byte)0) ;//'排班状态：0-正常；1-已取消；2-已成功；3-已超时；' 并且不是定向预约的订单
			List<StudentClass> nowOrder = studentClassMapper.selectByExample(example);
			if(null != nowOrder && nowOrder.size()>0){
			    r.setCode(ResultCode.ERRORCODE.ORDER_NO_GIVE);
			    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_NO_GIVE);
			    return r;
			}

			// （4）上课时间与已有订单时间冲突，不能排班
			OrderVo buz= coachClassService.isStudentIdle(studentClass.getStudentId(), null, studentClass.getCstart(), studentClass.getCend(), true);
			if(null != buz){
			    r.setCode(ResultCode.ERRORCODE.ORDER_STU_BUZ);
			    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_STU_BUZ);
			    return r;
			}
			
			//判断教练排班是否冲突
			List<Long> coachIds=new ArrayList();
			coachIds.add(coachId);
			//获取教练所有排班，不管是否有价格
			Map<Long,List<CoachClassVo>> ccmap=coachClassService.queryByCoachDateWitNoPrice(studentClass.getCstart(), coachIds);
			for(Long oneId:coachIds){
				CoachClassVo cbuz=coachClassService.isCoachIdle(ccmap.get(oneId), null,null,studentClass.getCstart(), studentClass.getCend(),false);
				if(cbuz!=null){//教练忙
					log.debug("StudentClassServiceImpl-->coache"+oneId+"  has class count-->");
					r.setCode(ResultCode.ERRORCODE.ORDER_COACH_BUZ);
				    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_COACH_BUZ);
				    return r;
				}
			}
			//	（4.1）非整点不能下单。为啥要有这个限制呢？
			
			// （5）是否有未支付订单
			OrderQuery query=new OrderQuery();
			query.setGroupBy("and pay_state=0 and order_state !=0");//未付款、未取消
			List<OrderVo> list=orderService.queryByStudentId(studentClass.getStudentId(), query);
			if(list != null && list.size() > 0){
				r.setCode(ResultCode.ERRORCODE.ORDER_IS_NOT_PAY);
				r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_NOT_PAY);
				return r;
			}
			
			//	（6）添加课程信息
			CoursenewVo course = fileManager.getCoursenewBycourseid(studentClass.getCourseId());
			if (null != course) {
				studentClass.setCourseName(course.getCoursenewname());
			}else {
				return ReqResult.getParamError();
			}
			//	（7）获取订单价格
			int price = this.getClassPrice(studentClass);
			studentClass.setPrice(price);
			
			 // 20161116如果余额不足，不能约课
			Student student = studentManager.getStudentInfo(studentClass.getStudentId());
			if(student.getIsImport() == (byte)0) {
				StudentAccount sa = studentManager.getStudentMoney(studentClass.getStudentId());
				int money = 0;
				if (null != sa) {
					money = sa.getMoney();
				}
				if (money <  price) {
					r.setCode(ResultCode.ERRORCODE.BALANCE_IS_INSUFFICIENT);
					r.setMsgInfo(ResultCode.ERRORINFO.BALANCE_IS_INSUFFICIENT);
					return r;
				}
			}
			
			// 保存学员排班信息
			studentClass.setOrderId(StringUtil.getOrderId());
			studentClass.setDataVersion("2.1.0");
			studentClassMapper.insertSelective(studentClass);
			
			//20160831 预约时间在22点后到7点，提醒可能匹配不到教练
	        Calendar nowCalendar = Calendar.getInstance();
	        nowCalendar.setTime(new Date(currentTime));
	        int nowHour = nowCalendar.get(Calendar.HOUR_OF_DAY);
	        if(nowHour >= 22 || nowHour <= 6){
	        	r.setData("该时段内预约教练可能在休息，如匹配不到教练，请您在正常工作时间段内进行预约");
	        }
			
	        doFindCoachAndNotice(studentClass,coachId);
	        
			if(log.isDebugEnabled()){
				log.debug("StudentClassServiceImpl-->addStudentClass-->time cost="+(System.currentTimeMillis()-currentTime));
			}
		} catch (Exception e) {
			log.error("StudentClassServiceImpl-->addStudentClass-->"+e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.ORDER_SYSTEM_ERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.ORDER_SYSTEM_ERROR);
			
		}
		return r;
	}

	private void findCoachAndNotice(final StudentClass sc) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				doFindCoachAndNotice(sc,0);
				
			}

		});
	}

	private void doFindCoachAndNotice(StudentClass sc,long onecoachId) {
		try {
			long allStart=System.currentTimeMillis();
			List<Long> coachValid = new ArrayList<Long>();
			//（1）获取所有附近教练
			SearchVo search = new SearchVo();
			search.setLae(sc.getLae());
			search.setLge(sc.getLge());
			search.setPageSkip(0);
			search.setPageLimit(15*7);//分页大小*查询次数
			search.setDistance(7d);//教练查询的最大距离
			//coachId=1000004500,lge=113.949519, lae=22.556247, dir=-1.0, distance=2.571399154429503,
			List<CoachVo> coachs = locationService.getNearBy(search);
			if(onecoachId==0){//自主排班，广播教练
				
				if(log.isDebugEnabled()){
					log.debug("StudentClassServiceImpl-->doFindCoachAndNotice-->location find coaches count-->"+coachs.size());
					log.debug("StudentClassServiceImpl-->doFindCoachAndNotice-->coaches-->, "+BeanCopy.getFieldList(coachs, "coachId")
							+";search time cost="+(System.currentTimeMillis()-allStart)+";with lge="+sc.getLge()+",lae="+sc.getLae()+",distance="+"7"+",limit="+search.getPageLimit());
				}
				
				for(int i=coachs.size()-1;i>=0;i--){
					CoachVo vo=coachs.get(i);
					Coach coach = coachManager.getCoachInfo(vo.getCoachId());
					if(coach==null){
						log.warn("coach:"+vo.getCoachId()+" is null");
						coachs.remove(i);
						continue;
					}
					if(coach.getIsImport()==null&&coach.getCheckDrState()==null){
						log.warn("coach:"+coach.getCoachId()+" isimport is null or checkdrstate is null");
						coachs.remove(i);
						continue;
					}
					if(coach.getIsImport()!=1&&coach.getCheckDrState()!=2){//注册没有认证
						coachs.remove(i);
						continue;
					}
					
					if(sc.getCourseId()!=5&&sc.getCourseId()!=15&&sc.getCourseId()!=2&&sc.getCourseId()!=12&&sc.getCourseId()!=4&&sc.getCourseId()!=14){//不是陪驾
						if(coach.getIsImport()!=1){//新注册教练
							coachs.remove(i);//不推送给此教练
						}
					}
					
				}
				
				//（2）根据条件对教练进行过滤
				
				if(null != coachs & coachs.size()>0){
					//Map<Integer,FutureTask<List<CoachVo>>> map=new HashMap<Integer,FutureTask<List<CoachVo>>>();
					List<Long> coachIds=BeanCopy.getFieldList(coachs,"coachId");
					// 教练状态过滤：接单距离、准驾车型、课程（包括了准驾车型）；
					SearchCoachProperty searchCoachProperty = new SearchCoachProperty(sc,coachIds,coachManager); 
					FutureTask<List<CoachVo>> coachFuture = new FutureTask<List<CoachVo>>(searchCoachProperty);
					threadPool.execute(coachFuture);
					// 系统按照排班、上课中订单、待上课订单等条件筛选可接单的教练
					SearchCoachClass searchCoachClass = new SearchCoachClass(sc,coachIds,coachClassService);
					FutureTask<List<Long>> classFuture = new FutureTask<List<Long>>(searchCoachClass);
					threadPool.execute(classFuture);
					//最后结果整理
					List<CoachVo> coachResult=coachFuture.get();
					log.debug("StudentClassServiceImpl-->doFindCoachAndNotice-->SearchCoachProperty find coaches count-->"+coachResult.size());
					List<Long> classResult=classFuture.get();
					log.debug("StudentClassServiceImpl-->doFindCoachAndNotice-->SearchCoachClass find coaches count-->"+classResult.size());
					if(null != coachResult && coachResult.size()>0){
						for(CoachVo one:coachResult){
							long id=one.getCoachId().longValue();
							//排除重复
							if(classResult.contains(id)&& !coachValid.contains(id)){
								coachValid.add(id);
							}
						}
					}else{
						log.debug("StudentClassServiceImpl-->doFindCoachAndNotice-->coachValid is null! SearchCoachProperty coaches-->"
					+BeanCopy.getFieldList(coachs, "coachId"));//+";SearchCoachClass coaches-->"+coachResult//log is too big!
					}
					
				}
			}else{
				List<Long> coachIds=new ArrayList();
				coachIds.add(onecoachId);
				// 系统按照排班、上课中订单、待上课订单等条件筛选可接单的教练
				SearchCoachClass searchCoachClass = new SearchCoachClass(sc,coachIds,coachClassService);
				FutureTask<List<Long>> classFuture = new FutureTask<List<Long>>(searchCoachClass);
				threadPool.execute(classFuture);
				//最后结果整理
				
				
				List<Long> classResult=classFuture.get();
				log.debug("StudentClassServiceImpl-->doFindCoachAndNotice-->SearchCoachClass find coaches count-->"+classResult.size());
				if(classResult.contains(onecoachId)){
					coachValid.add(onecoachId);
				}
				
			}
			// （3）通知推送教练可以有单可接啦
			log.debug("StudentClassServiceImpl-->doFindCoachAndNotice-->coachValid count-->"+coachValid.size());
			if(null != coachValid && coachValid.size() > 0){
				//	1.保存到接单教练资源池中
				// 获取符合条件的教练信息
				List<CoachVo> coachVoValid=coachManager.getCoachesByIds(coachValid);
				for(CoachVo one:coachVoValid){
					List<Car> cars= carManager.getCarByCoachId(one.getCoachId());
					StudentClassPool scp = new StudentClassPool();
					long coachId = one.getCoachId().longValue();
					scp.setCoachId(coachId);
					scp.setCoachImg(one.getHeadIcon());
					scp.setCoachMobile(one.getPhoneNum());
					scp.setCoachName(one.getName());
					scp.setCtime(new Date());
					scp.setOrderId(sc.getOrderId());
					scp.setPlaceName(sc.getPlaceInfo());
					scp.setPlaceLae(sc.getLae());
					scp.setPlaceLge(sc.getLge());
					scp.setStudentId(sc.getStudentId());
					scp.setSchoolName(one.getImportSrc());
					scp.setStarLevel(one.getStarLevel());
					boolean flag=false;
					if(onecoachId>0&&cars!=null){
					
						for(Car car:cars){
							
							if(car.getDriveType()!=null&& car.getDriveType().intValue()==sc.getDltype().intValue()){
								scp.setCarId(car.getCarId().longValue());
								scp.setCarNo(car.getCarNo());
								flag=true;
								break;
							}
						}
					}
					if(!flag){
						scp.setCarId(one.getCarId().longValue());
						scp.setCarNo(one.getCarNo());
					}
					for(CoachVo cv:coachs){
						if(cv.getCoachId().longValue() == coachId){
							scp.setDistance(cv.getDistance());
							break;
						}
					}
					studentClassPoolMapper.insertSelective(scp);
					
				}
				
				//	2.推送给教练
				for(Long coachId:coachValid){
					String content = "您收到学员自主排班课程，是否接受订单？";
					if(onecoachId>0){
						content = "您收到学员定向预约课程，是否接受订单？";
					}
					JpushMsg jmsg = new JpushMsg();
				    Map<String,String> extras=new HashMap<String,String>();
				    extras.put("orderId", sc.getOrderId());
				    extras.put("courseName", sc.getCourseName());
				    extras.put("clznum", sc.getClznum().toString());
				    extras.put("cStart", sc.getCstart().toString());
				    extras.put("price", sc.getPrice().toString());
				    jmsg.setExtras(extras);
					jmsg.setAlter(content);
					jmsg.setUserId(coachId);
					jmsg.setOrderId(sc.getOrderId());
				    jmsg.setOperate(JpushConstant.OPERATE.STU_CLASS);//如果有extras，则operate要放在extra之后
				    jmsg.setSound("sound102.wav"); //声音文件更改
					Message jpush = new Message();
					jpush.setKeys(sc.getOrderId());
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpushProducer.send(jpush);
					
					//消息中心，订单通知
					Notice notice = new Notice();
					Map<String, String> extra = new HashMap<>();
					notice.setTitle("接单啦~");
					notice.setDigest("有新的订单，快来接单吧。");
					notice.setType((byte)2);
					notice.setTime(new Date());
					notice.setUserType((byte)ReqConstants.USER_TYPE_COACH);
					notice.setUserId(coachId);
					notice.setOrderId(sc.getOrderId());
					extra.put("\"orderId\"", "\"\"");
					extra.put("\"type\"", "\"2\"");
					notice.setExtra(extra.toString().replace("=", ":"));
					noticeManager.addNotice(notice);
				}
				
			}else {
				//	1.该排班没有匹配到合适的教练，提示学员重新设置排班
				String content = "对不起，附近没有匹配到合适的教练，请重新设置排班";
				JpushMsg jmsg = new JpushMsg();
				jmsg.setAlter(content);
				jmsg.setUserId(sc.getStudentId());
				jmsg.setOperate(JpushConstant.OPERATE.STU_CLASS_NO_COACH);
				jmsg.setOrderId(sc.getOrderId());
				Message jpush = new Message();
				jpush.setKeys(sc.getOrderId());
				jpush.setTopic(jpushProducer.getCreateTopicKey());
				jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
				jpush.setBody(SerializableUtil.serialize(jmsg));
				jpushProducer.send(jpush);
				//	2.并将学员排班设置为取消状态 TODO
				//this.setStudentClassOvertime(sc);
			}


			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ReqResult getClassRecommend(String userId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// 根据最近一次的用户排班进行推荐，排班取消的也算
			StudentClassExample example = new StudentClassExample();
			example.createCriteria()
			.andStudentIdEqualTo(Long.parseLong(userId));
			example.setOrderByClause("id desc limit 1");
			List<StudentClass> scs = studentClassMapper.selectByExample(example);
			if(null != scs && scs.size()>0){
				StudentClass sc = scs.get(0);
				int courseId = sc.getCourseId();//课程
				int max = 5;//每次推送5个班次即可
				//1.课程、准驾车型
				//2.时间符合优先推送；再次推送上课时间差距小于1小时的班次
				//3.优先推送1对1，没有再推送多人班次
				//4.每次推送5个排班
				CoachClassVo search=new CoachClassVo();
				search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				search.setCtype(OrderConstant.OTYPE.BOOKORDER); //预约排班
				search.setCourseId(String.valueOf(courseId));
				CoachClassQuery query = new CoachClassQuery();
				//query.setGroupBy("and max_num > book_num "); //只查询未约满的排班
				query.setGroupBy("and max_num > book_num and cstart >= " + "\'"+TimeUtil.getDateFormat(sc.getCstart(),"yyyy-MM-dd HH:mm:ss")+ "\'");
				query.setorderBy("order by cstart ASC ");
				List<CoachClassVo> list=coachClassService.queryByObjectAnd(search, query);
				if(null != list && list.size()>0){
					//20160918 多人排班时需要处理价格
					for(CoachClassVo cls:list){
						cls.setPrice(cls.getPrice()/cls.getMaxNum().intValue());
					}
					if(list.size() <= max){ //不足最大排班直接返回数据
						r.setData(list);
					}else {
						List<CoachClassVo> listOne = new ArrayList<CoachClassVo>();
						List<CoachClassVo> listTwo = new ArrayList<CoachClassVo>();
						for(CoachClassVo ccv:list){
							if(ccv.getMaxNum() == 1){ //1对1排班
								listOne.add(ccv);
							}else {
								listTwo.add(ccv);
							}
						}
						if(listOne.size()>= max){
							r.setData(listOne.subList(0, max -1)); //3.优先推送1对1，没有再推送多人班次
						}else {
							int size = listOne.size();
							for(int i=0;i<max-size;i++){
								listOne.add(listTwo.get(i));
							}
							r.setData(listOne);
						}

					}
					
				}
				//因为APP页面制作的不通用，这个地方还需要把教练信息传过去。后面可以优化
/*				List<CoachClassVo> ccvs = (List<CoachClassVo>) r.getResult().get(ResultCode.RESULTKEY.DATA);
				if(null != ccvs && ccvs.size()>0){
					List<Long> coachIds=BeanCopy.getFieldList(ccvs,"coachId");
					List<CoachVo> coachList=coachManager.getCoachesByIds(coachIds);
					r.setData("mycoachesVo", coachList);
				}*/
				
				
			}else {
				//没有排过班的同学不给推荐
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return r;
	}
	
	

	@Override
	public ReqResult getMyClass(String userId, String userType) {
		ReqResult r = ReqResult.getSuccess();
		try {
			StudentClassExample example = new StudentClassExample();
			example.createCriteria()
			.andStudentIdEqualTo(Long.parseLong(userId))
			.andStateEqualTo((byte)0);//'排班状态：0-正常；1-已取消；2-已成功；3-已超时；'
			example.setOrderByClause("id desc limit 1");
			List<StudentClass> scs = studentClassMapper.selectByExample(example);
			if(null != scs && scs.size()>0){
				
				StudentClassPoolExample pexample = new StudentClassPoolExample();
				pexample.createCriteria().andOrderIdEqualTo(scs.get(0).getOrderId()).andStudentIdEqualTo(Long.parseLong(userId));
				List<StudentClassPool> scps = studentClassPoolMapper.selectByExample(pexample);
				if(scs.get(0).getDirect()!=null&&scs.get(0).getDirect()==1){
					if(scps!=null&&scps.size()>0){
						scs.get(0).setDirectCoachId(scps.get(0).getCoachId());
						scs.get(0).setCarNo(scps.get(0).getCarNo());
					}
				}
				
				//检查订单是否已超时
				Date cstart = scs.get(0).getCstart();
				int hour = TimeUtil.timeSpan(new Date(), cstart, "hour");
				if(hour >0){
					if(hour <=1){ //>0,是还没有到订单开始时间，如果相差小于1个小时，则认为超时；
						//做超时处理
						this.setStudentClassOvertime(scs.get(0).getOrderId(), scs.get(0).getStudentId());
					}else {
						r.setData(scs.get(0));
					}
				}else{
					//<0,是已经过了订单开始时间，肯定已经超时了；
					this.setStudentClassOvertime(scs.get(0).getOrderId(), scs.get(0).getStudentId());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	@Override
	public ReqResult getMyClassNew(String userId, String userType,boolean both) {
		ReqResult r = ReqResult.getSuccess();
		try {
			StudentClassExample example = new StudentClassExample();
			List<Byte> values=new ArrayList();
			values.add((byte)0);
			values.add((byte)1);
			
			if(both){
				example.createCriteria()
				.andStudentIdEqualTo(Long.parseLong(userId)).andStateIn(values);
			}else{
				example.createCriteria()
				.andStudentIdEqualTo(Long.parseLong(userId)).andStateIn(values).andOrderDirectEqualTo(0);
			}
			
			
			////'排班状态：：0-正常；1-已成功；2-已取消；3-已超时；
			example.setOrderByClause("id desc limit 1");
			List<StudentClass> scs = studentClassMapper.selectByExample(example);
			if(null != scs && scs.size()>0){
				if(scs.get(0).getState().intValue()==0){//成功发布
					//检查订单是否已超时
					Date cstart = scs.get(0).getCstart();
					int hour = TimeUtil.timeSpan(new Date(), cstart, "hour");
					if(hour >0){
						if(hour <=1){ //>0,是还没有到订单开始时间，如果相差小于1个小时，则认为超时；
							//做超时处理
							this.setStudentClassOvertime(scs.get(0).getOrderId(), scs.get(0).getStudentId());
						}else {
							r.setData(scs.get(0));
						}
					}else{
						//<0,是已经过了订单开始时间，肯定已经超时了；
						this.setStudentClassOvertime(scs.get(0).getOrderId(), scs.get(0).getStudentId());
					}
				}else{//成单
					OrderVo order= orderService.queryOrderById(scs.get(0).getOrderId(), new OrderQuery());
					if(order.getOrderState()!=0&&order.getOrderState()!=9){
						if(order!=null&&order.getPayState()==1){
							scs.get(0).setPayState(1);
						}
						r.setData(scs.get(0));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public StudentClass getStudentClass(String orderid){
		
		try {
			StudentClassExample example = new StudentClassExample();
			
			example.createCriteria().andOrderIdEqualTo(orderid);
			
			List<StudentClass> scs = studentClassMapper.selectByExample(example);
			if(null != scs && scs.size()>0){
				return scs.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public StudentClass getStudentClass(String userId,String orderid){
		
		try {
			StudentClassExample example = new StudentClassExample();
			
			example.createCriteria()
			.andStudentIdEqualTo(Long.parseLong(userId)).andOrderIdEqualTo(orderid);
			
			List<StudentClass> scs = studentClassMapper.selectByExample(example);
			if(null != scs && scs.size()>0){
				return scs.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<StudentClassPool> getStudentClassPool(String userId,String orderid){
		StudentClassPoolExample example = new StudentClassPoolExample();
		example.createCriteria().andOrderIdEqualTo(orderid).andStudentIdEqualTo(Long.parseLong(userId));
		
		List<StudentClassPool> scps = studentClassPoolMapper.selectByExample(example);
		return scps;
	}
	
	/**
	 * 设置学员排班超时
	 * @param sc
	 * @return
	 */
	public int setStudentClassOvertime(String orderId,Long studentId){
		StudentClass record = new StudentClass();
		Byte scState = 3;	//排班状态：0-正常；1-已成功；2-已取消；3-已超时；',
		record.setState(scState); 
		StudentClassExample example = new StudentClassExample();
		example.createCriteria()
		.andOrderIdEqualTo(orderId)
		.andStudentIdEqualTo(studentId);
		return studentClassMapper.updateByExampleSelective(record,example);
	}
	
	
	
	/**
	 * userid 教练id
	 */
	@Override
	public ReqResult getStudentsClass(String userId, String userType) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<Byte> state = new ArrayList<Byte>();
			state.add((byte) 0);
			state.add((byte) 1);//状态：0-已推送教练；1-教练已接单；2-教练已取消；
			StudentClassPoolExample example = new StudentClassPoolExample();
			example.createCriteria()
			.andCoachIdEqualTo(Long.parseLong(userId))
			.andStateIn(state);
			List<StudentClassPool> scps = studentClassPoolMapper.selectByExample(example);
			if(null != scps && scps.size() >0){
				//List<String> orders = BeanCopy.getFieldList(scps,"orderId");//注意这里不能这样使用！因为orderId是继承父类的属性
				List<String> orders = new ArrayList<String>();
				for(StudentClassPool scp:scps){
					orders.add(scp.getOrderId());
				}
				List<Byte> scState = new ArrayList<Byte>();
				scState.add((byte) 0);
				//scState.add((byte) 1); //排班状态：0-正常；1-已成功；2-已取消；3-已超时；//成功后不再返回给教练端
				StudentClassExample scExample = new StudentClassExample();
				scExample.createCriteria()
				.andOrderIdIn(orders)
				.andStateIn(scState);

				List<StudentClass> scs = studentClassMapper.selectByExample(scExample);
				//检查订单是否已超时
				if(null != scs && scs.size()>0){
					for(int i=0;i<scs.size();i++){
						Date cstart = scs.get(i).getCstart();
						int hour = TimeUtil.timeSpan(new Date(), cstart, "hour");
						if(hour >0){
							if(hour <=1){ //>0,是还没有到订单开始时间，如果相差小于1个小时，则认为超时；
								//做超时处理
								this.setStudentClassOvertime(scs.get(i).getOrderId(), scs.get(i).getStudentId());
								scs.remove(i);
								i--;
							}else {
								continue;
							}
						}else{
							//<0,是已经过了订单开始时间，肯定已经超时了；
							this.setStudentClassOvertime(scs.get(i).getOrderId(), scs.get(i).getStudentId());
							scs.remove(i);
							i--;
						}
					}
				}
				
				if(null != scs && scs.size()>0){
					for(StudentClass sc:scs){
						for(StudentClassPool scp:scps){
							if(scp.getOrderId().equals(sc.getOrderId())){
								sc.setExtra(scp.getState().toString()); //将教练的操作状态添加到学员订单的extra字段中；教练端综合state和extra来确定当前订单状态
								if(scp.getPlaceName()!=null)
								sc.setPlaceInfo(scp.getPlaceName()); //教练端需要看到自己接单时的位置，临时放在这个地方
							}
						}
					}
				}else {
					return r;
				}
				r.setData(scs);
				List directdata=new ArrayList();
				List cdata=new ArrayList();
				for(StudentClass sc:scs){
					if(sc.getDirect()==1){
						directdata.add(sc);
					}else{
						cdata.add(sc);
					}
				}
				r.setData("ddata", directdata);
				r.setData("cdata", cdata);
			}else {
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	
	public ReqResult getPageStudentsClass(String userId, String userType,List<Byte> states) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<Byte> scState = new ArrayList<Byte>();
			scState.add((byte) 0);
			//scState.add((byte) 1); //排班状态：0-正常；1-已成功；2-已取消；3-已超时；//成功后不再返回给教练端
			StudentClassExample scExample = new StudentClassExample();
			scExample.setOrderByClause(" mtime desc ");
			scExample.createCriteria().andStudentIdEqualTo(Long.parseLong(userId)).andStateIn(states);
			

			List<StudentClass> scs = studentClassMapper.selectByExample(scExample);
			r.setData(scs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult setStudentsClassState(String userId, String userType,
			String orderId, String state, String placeId,String placeName,String placeLge,String placeLae,String v) {
		ReqResult r = ReqResult.getSuccess();
		try {
			//前置检查，如果学员已经取消了这个订单或者确认了这个订单，则不允许教练再做更改
			StudentClassExample scExample = new StudentClassExample();
			scExample.createCriteria()
			.andOrderIdEqualTo(orderId);
			List<StudentClass> scs = studentClassMapper.selectByExample(scExample);
			StudentClass esc=null;
			if(null != scs && scs.size()>0){
				byte scState = scs.get(0).getState();//排班状态：0-正常；1-已成功；2-已取消；3-已超时；
				esc=scs.get(0);
				if(scState != 0){
//				    r.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
//				    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOTEXIST);
					r.setCode(ResultCode.ERRORCODE.ORDER_HAS_ACCEPT);
				    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_HAS_ACCEPT);
				    return r;
				}
			}else {
			    r.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
			    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOTEXIST);
			    return r;
			}
			
			byte bState = Byte.parseByte(state); //状态：0-已推送教练；1-教练已接单；2-教练已取消；
			StudentClassPool record = new StudentClassPool();
			record.setCoachId(Long.parseLong(userId));
			record.setOrderId(orderId);
			record.setState(bState);
			if(placeName!=null)
				record.setPlaceName(placeName.trim());
			if(null != placeId && !"".equals(placeId)){
				record.setPlaceId(Integer.parseInt(placeId.trim()));
			}
			if(null != placeLge && !"".equals(placeLge.trim()) && Double.parseDouble(placeLge.trim())!= 0){
				record.setPlaceLge(Double.parseDouble(placeLge.trim()));
				record.setPlaceLae(Double.parseDouble(placeLae.trim()));
			}else {
				if(null != placeId && !"".equals(placeId)){
					int place = Integer.parseInt(placeId.trim());
					if(place != 0){
						//经纬度为空，说明传的是可能是训练场信息
						Trfield tr = trfieldManager.getTrfieldInfo(Integer.parseInt(placeId.trim()));
						record.setPlaceLge(tr.getLge());
						record.setPlaceLae(tr.getLae());
					}else {
						//取消的时候，可能不传位置信息，不需要更新
					}

				}
				
			}
			if(bState == 1){
				if(esc.getDataVersion()!=null&&VersionUtil.compareVersion(esc.getDataVersion(), "2.1.0")>=0){//学员端2.1之后
					if(!(v!=null&& VersionUtil.compareVersion(v, "2.1.0") >= 0)){//教练端2.1之前,
						r.setCode(ResultCode.ERRORCODE.VERSION_COACH_IS_LOW);
					    r.setMsgInfo(ResultCode.ERRORINFO.VERSION_COACH_IS_LOW);
					    return r;
					}
				}
			}
			//更新 t_student_class_pool状态，
			studentClassPoolMapper.updateByPrimaryKeySelective(record);
			
			
			
			//	（1）如果是接单，则推送给学员
			if(bState == 1){
				
				
				StudentClassPoolKey key = new StudentClassPoolKey();
				key.setCoachId(Long.parseLong(userId));
				key.setOrderId(orderId);
				StudentClassPool scp = studentClassPoolMapper.selectByPrimaryKey(key);
				
				ReqResult r2=check(String.valueOf(scp.getStudentId()),userType,orderId,state,userId);
				if(!ResultCode.ERRORCODE.SUCCESS.equals(r2.getResult().get(ResultCode.RESULTKEY.CODE))){
					return r2;
				}
				
				Coach coach = coachManager.getCoachInfo(scp.getCoachId().longValue());
				if(coach.getIsImport()!=1&&coach.getCheckDrState()!=2){//注册教练没认证
					r.setCode(ResultCode.ERRORCODE.COACH_NO_AUTH);
				    r.setMsgInfo(ResultCode.ERRORINFO.COACH_NO_AUTH);
				    return r;
				}
				
				//String content = "您的自主排班课程已被教练接单，是否选择该教练？";
				String content = "您的自主排班课程已被教练["+scp.getCoachName()+"]接单。";
				if(scs.get(0).getDirect()==1){
					content = "您的定向预约课程已被教练["+scp.getCoachName()+"]接单。";
				}
				JpushMsg jmsg = new JpushMsg();
			    Map<String,String> extras=new HashMap<String,String>();
			    extras.put("orderId", scp.getOrderId());
			    extras.put("coachId", scp.getCoachId().toString());
			    extras.put("coachName", scp.getCoachName().toString());
			    extras.put("coachImg", scp.getCoachImg());
			    extras.put("coachMobile", scp.getCoachMobile());
			    extras.put("starLevel", scp.getStarLevel().toString());
			    if(scp.getDistance()!=null)
			    	extras.put("distance", scp.getDistance().toString());
			    else{
			    	CoachVo cv=locationService.getCoach(scp.getCoachId());
			    	if(cv!=null){
			    		extras.put("distance", cv.getDistance()+"");
			    	}else{
			    		extras.put("distance", "0");
			    	}
			    }
			    extras.put("placeName", scp.getPlaceName());
			    extras.put("schoolName", scp.getSchoolName());
			    jmsg.setExtras(extras);
				jmsg.setAlter(content);
				jmsg.setUserId(scp.getStudentId());
				jmsg.setOperate(JpushConstant.OPERATE.STU_CLASS_ACCEPT);
				jmsg.setOrderId(scp.getOrderId());
				Message jpush = new Message();
				jpush.setKeys(scp.getOrderId());
				jpush.setTopic(jpushProducer.getCreateTopicKey());
				jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
				jpush.setBody(SerializableUtil.serialize(jmsg));
				jpushProducer.send(jpush);
				
				//消息中心，订单通知 推送教练消息
				Notice notice = new Notice();
				Map<String, String> extra = new HashMap<>();
				notice.setTitle("恭喜您，接单成功");
				notice.setDigest("您已成功拿下" + scs.get(0).getStuName() + "的约课订单，正在等待该学员确认，请耐心等候。");
				notice.setContent("您已成功拿下" + scs.get(0).getStuName() + "的约课订单，正在等待该学员确认，请耐心等候。");
				notice.setType((byte)2);
				notice.setTime(new Date());
				notice.setUserType((byte)ReqConstants.USER_TYPE_COACH);
				notice.setUserId(scp.getCoachId());
				notice.setOrderId(scp.getOrderId());
				extra.put("\"orderId\"", "\"\"");
				extra.put("\"type\"", "\"2\"");
				notice.setExtra(extra.toString().replace("=", ":"));
				Notice exticeNotice=noticeManager.getNoticeByOrderId(scp.getOrderId(),(byte) 1);
				if(exticeNotice!=null){
					notice.setNoticeId(exticeNotice.getNoticeId());
					noticeManager.updateNotice(notice);
				}else{
					noticeManager.addNotice(notice);
				}
				
				//推送学员消息
				Notice noticeStudent = new Notice();
				noticeStudent.setTitle("订单消息");
				//noticeStudent.setDigest("您的自主排班课程已被教练["+scp.getCoachName()+"]接单。");
				noticeStudent.setDigest(content);
				noticeStudent.setType((byte)2);
				noticeStudent.setTime(new Date());
				noticeStudent.setUserType((byte)ReqConstants.USER_TYPE_STUDENT);
				noticeStudent.setUserId(scp.getStudentId());
				noticeStudent.setOrderId(scp.getOrderId());
				Notice exticeNoticeStduent=noticeManager.getNoticeByOrderId(scp.getOrderId(),(byte)ReqConstants.USER_TYPE_STUDENT);
				if(exticeNoticeStduent!=null){
					noticeStudent.setNoticeId(exticeNoticeStduent.getNoticeId());
					noticeManager.updateNotice(noticeStudent);
				}else{
					noticeManager.addNotice(noticeStudent);
				}
				
				
				if(v!=null&& VersionUtil.compareVersion(v, "2.1.0") >= 0){//接单客户端是2.1
					//System.out.println(VersionUtil.compareVersion(esc.getDataVersion(), "2.1.0"));
					if(esc.getDataVersion()!=null&& VersionUtil.compareVersion(esc.getDataVersion(),"2.1.0" ) >= 0){//发送端也是2.1.0
						
					//把学员确认教练接单流程挪到这里，自动确认
					setClassState(String.valueOf(scp.getStudentId()),userType,orderId,state,userId);
					}else{//发送端是2.0
						
					}
				}else{//接单是2.0
					if(esc.getDataVersion()!=null&& VersionUtil.compareVersion(v, esc.getDataVersion()) >= 0){//发送端是2.1.0
						//不处理
					}else{
						//不处理
					}
				}
			}else{//拒绝接单
				/**
				 * 如果是定向预约单，教练据单要把学员排班也改状态
				 */
				for(StudentClass sc:scs){
					if(sc.getDirect()!=null&&sc.getDirect()==1){//2.1 版本后才有direct=1,
						byte scState = scs.get(0).getState();
						if(scState == 0){
							StudentClass srecord = new StudentClass();
							srecord.setState((byte)4); //排班状态：0-正常；1-已成功；2-已取消；3-已超时；4 教练据单取消',
							StudentClassExample example = new StudentClassExample();
							example.createCriteria()
							.andOrderIdEqualTo(orderId)
							.andStudentIdEqualTo(scs.get(0).getStudentId());
							studentClassMapper.updateByExampleSelective(srecord,example);
							
							StudentClassPoolKey key = new StudentClassPoolKey();
							key.setCoachId(Long.parseLong(userId));
							key.setOrderId(orderId);
							StudentClassPool scp = studentClassPoolMapper.selectByPrimaryKey(key);
							String content = "您的定向预约课程已被教练["+scp.getCoachName()+"]拒绝。";
							JpushMsg jmsg = new JpushMsg();
						    Map<String,String> extras=new HashMap<String,String>();
						    extras.put("orderId", scp.getOrderId());
						    extras.put("coachId", scp.getCoachId().toString());
						    extras.put("coachName", scp.getCoachName().toString());
						    extras.put("coachImg", scp.getCoachImg());
						    extras.put("coachMobile", scp.getCoachMobile());
						    extras.put("starLevel", scp.getStarLevel().toString());
						    if(scp.getDistance()!=null)
						    	extras.put("distance", scp.getDistance().toString());
						    else
						    	extras.put("distance", "");
						    extras.put("placeName", scp.getPlaceName());
						    extras.put("schoolName", scp.getSchoolName());
						    jmsg.setExtras(extras);
							jmsg.setAlter(content);
							jmsg.setUserId(scp.getStudentId());
							jmsg.setOperate(JpushConstant.OPERATE.STU_CLASS_REJECT);
							jmsg.setOrderId(scp.getOrderId());
							Message jpush = new Message();
							jpush.setKeys(scp.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							
							//推送学员消息
							Notice noticeStudent = new Notice();
							noticeStudent.setTitle("订单消息");
							noticeStudent.setDigest(content);
							noticeStudent.setType((byte)2);
							noticeStudent.setTime(new Date());
							noticeStudent.setUserType((byte)ReqConstants.USER_TYPE_STUDENT);
							noticeStudent.setUserId(scp.getStudentId());
							noticeStudent.setOrderId(scp.getOrderId());
							Notice exticeNoticeStduent=noticeManager.getNoticeByOrderId(scp.getOrderId(),(byte)ReqConstants.USER_TYPE_STUDENT);
							if(exticeNoticeStduent!=null){
								noticeStudent.setNoticeId(exticeNoticeStduent.getNoticeId());
								noticeManager.updateNotice(noticeStudent);
							}else{
								noticeManager.addNotice(noticeStudent);
							}
							
							break;
						}
					}
				}
			}

			
			
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
		    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_ERROR);
			e.printStackTrace();
		}
		return r;
	}
	
	private ReqResult check(String userId, String userType,
			String orderId, String state, String coachId) throws Exception{
		ReqResult r = ReqResult.getSuccess();
		
		StudentClassPoolKey key = new StudentClassPoolKey();
		key.setCoachId(Long.parseLong(coachId));
		key.setOrderId(orderId);
		StudentClassPool scp =  studentClassPoolMapper.selectByPrimaryKey(key);
		//	（1）可能会有冲突，再次检查资源池状态
		if(scp.getState().byteValue() != 1){ //状态：0-已推送教练；1-教练已接单；2-教练已取消；
		    r.setCode(ResultCode.ERRORCODE.ORDER_NOT_OPERATE);
		    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOT_OPERATE);
		    return r;
		}
		//	（3）查询自主排班信息
		StudentClassExample scexample = new StudentClassExample();
		scexample.createCriteria()
		.andOrderIdEqualTo(orderId)
		.andStudentIdEqualTo(Long.parseLong(userId));
		scexample.setOrderByClause("id desc limit 1");
		List<StudentClass> StudentClasses = studentClassMapper.selectByExample(scexample);
		StudentClass sc = StudentClasses.get(0); //可以使用orderId作为表的主键来避免查询的麻烦，但这样没有了编号不方便
		
		//	（1.1）20160829 存在教练同时接了多个时间冲突的预约单的情况，学员在确认教练时，需要再次确认教练排班是否冲突问题。
		//上课教练时间过滤
		CoachClassVo ccvold = coachClassService.isCoachIdle(Long.parseLong(coachId), null, null, sc.getCstart(),sc.getCend(), false);
		if(null != ccvold){
			//排班冲突后，应该将该教练从资源池删除 TODO
			r.setCode(ResultCode.ERRORCODE.ORDER_COACH_BUZ);
			r.setMsgInfo(ResultCode.ERRORINFO.ORDER_COACH_BUZ);
			log.debug("setClassState-->coachId="+coachId+",coach has class="+ccvold.getCcid());
			return r;
		}
		// （5）是否有未支付订单 20161027 andy
		OrderQuery query=new OrderQuery();
		query.setGroupBy("and pay_state=0 and order_state !=0");//未付款、未取消
		List<OrderVo> busy=orderService.queryByStudentId(Long.parseLong(userId), query);
		if(busy != null && busy.size() > 0){
			//有未支付，取消当前自主预约订单
			StudentClass record = new StudentClass();
			record.setState((byte) 2); //排班状态：0-正常；1-已成功；2-已取消；3-已超时；',
			record.setExtra("系统检查到有未支付订单，自动取消！");
			StudentClassExample example = new StudentClassExample();
			example.createCriteria()
			.andOrderIdEqualTo(orderId)
			.andStudentIdEqualTo(Long.parseLong(userId));
			studentClassMapper.updateByExampleSelective(record,example);
			//
			r.setCode(ResultCode.ERRORCODE.ORDER_IS_NOT_PAY);
			r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_NOT_PAY);
			return r;
		}
		
		return r;
	}

	@Override
	public ReqResult getClassCoaches(String userId, String userType,
			String orderId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			StudentClassPoolExample example = new StudentClassPoolExample();
			example.createCriteria()
			.andStudentIdEqualTo(Long.parseLong(userId))
			.andOrderIdEqualTo(orderId)
			.andStateEqualTo((byte) 1); //状态：0-已推送教练；1-教练已接单；2-教练已取消；
			List<StudentClassPool> scps = studentClassPoolMapper.selectByExample(example);
			List<Long> coachIds=new ArrayList();
			if(null != scps && scps.size()>0){
				for(StudentClassPool scp:scps){
			        OrderQuery query = new OrderQuery();
			        OrderVo search = new OrderVo();
			        search.setCoachId(scp.getCoachId());
			        search.setStudentId(scp.getStudentId());
			        List<OrderVo> list = orderService.queryByObjectAnd(search, query);
			        if(null != list & list.size()>0){
			        	scp.setExtra("1");	//代表学员上过课的教练
			        }else {
			        	scp.setExtra("0");
			        }
			        coachIds.add(scp.getCoachId());
				}
			}
			
			List<CoachVo> coaches=coachManager.getCoachesByIds(coachIds);
			for(StudentClassPool scp:scps){
				for(CoachVo coach:coaches){
					if(scp.getCoachId().longValue()==coach.getCoachId().longValue()){
						scp.setAge(coach.getAge());
						scp.setCoachLevel(coach.getCoachLevel());
						if(coach.getExtra()!=null&&coach.getExtra().equals("1")){
							scp.setTeyue("1");
						}else{
							scp.setTeyue("0");
						}
						scp.setSchoolName(coach.getSchoolName());
						int count= coachCommentService.countByCoachId(coach.getCoachId());
						scp.setComment(count);
						/**
						 * 获取带教学员
						 */
						Coach c = coachManager.getCoachInfo(coach.getCoachId());
						//查询带教人数
//				        CoachClassQuery coachclassQuery = new CoachClassQuery();
//				        coachclassQuery.setSqlPost(" and isdel=0 and ctype=3 and rend < NOW() ");
//				        Integer bookSum = coachClassService.queryBookNumByCoachId(c.getCoachId(), coachclassQuery);//预约
//				        CoachClassQuery coachclassQuery2 = new CoachClassQuery();
//				        coachclassQuery2.setSqlPost(" and isdel=0 and ctype=1");
//				        Integer bookSumOrder = coachClassService.queryBookNumByCoachId2(c.getCoachId(), coachclassQuery2);//现约
//				        scp.setBookSum(bookSum + bookSumOrder);
				        scp.setBookSum(c.getTeachStudent());
					}
				}
			}
			r.setData(scps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult setClassState(String userId, String userType,
			String orderId, String state, String coachId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			byte scState = Byte.parseByte(state); //排班状态：0-正常；1-已成功；2-已取消；3-已超时；
			//	如果是成功接单，表示学员选择了某个教练。需要生成真实预约订单。
			if(scState == 1){
				StudentClassPoolKey key = new StudentClassPoolKey();
				key.setCoachId(Long.parseLong(coachId));
				key.setOrderId(orderId);
				StudentClassPool scp =  studentClassPoolMapper.selectByPrimaryKey(key);
				//	（1）可能会有冲突，再次检查资源池状态
				if(scp.getState().byteValue() != 1){ //状态：0-已推送教练；1-教练已接单；2-教练已取消；
				    r.setCode(ResultCode.ERRORCODE.ORDER_NOT_OPERATE);
				    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOT_OPERATE);
				    return r;
				}
				//	（3）查询自主排班信息
				StudentClassExample scexample = new StudentClassExample();
				scexample.createCriteria()
				.andOrderIdEqualTo(orderId)
				.andStudentIdEqualTo(Long.parseLong(userId));
				scexample.setOrderByClause("id desc limit 1");
				List<StudentClass> StudentClasses = studentClassMapper.selectByExample(scexample);
				StudentClass sc = StudentClasses.get(0); //可以使用orderId作为表的主键来避免查询的麻烦，但这样没有了编号不方便
				
				//	（1.1）20160829 存在教练同时接了多个时间冲突的预约单的情况，学员在确认教练时，需要再次确认教练排班是否冲突问题。
				//上课教练时间过滤
				CoachClassVo ccvold = coachClassService.isCoachIdle(Long.parseLong(coachId), null, null, sc.getCstart(),sc.getCend(), false);
				if(null != ccvold){
					//排班冲突后，应该将该教练从资源池删除 TODO
					r.setCode(ResultCode.ERRORCODE.ORDER_COACH_BUZ);
					r.setMsgInfo(ResultCode.ERRORINFO.ORDER_COACH_BUZ);
					log.debug("setClassState-->coachId="+coachId+",coach has class="+ccvold.getCcid());
					return r;
				}
				// （5）是否有未支付订单 20161027 andy
				OrderQuery query=new OrderQuery();
				query.setGroupBy("and pay_state=0 and order_state !=0");//未付款、未取消
				List<OrderVo> busy=orderService.queryByStudentId(Long.parseLong(userId), query);
				if(busy != null && busy.size() > 0){
					//有未支付，取消当前自主预约订单
					StudentClass record = new StudentClass();
					record.setState((byte) 2); //排班状态：0-正常；1-已成功；2-已取消；3-已超时；',
					record.setExtra("系统检查到有未支付订单，自动取消！");
					StudentClassExample example = new StudentClassExample();
					example.createCriteria()
					.andOrderIdEqualTo(orderId)
					.andStudentIdEqualTo(Long.parseLong(userId));
					studentClassMapper.updateByExampleSelective(record,example);
					//
					r.setCode(ResultCode.ERRORCODE.ORDER_IS_NOT_PAY);
					r.setMsgInfo(ResultCode.ERRORINFO.ORDER_IS_NOT_PAY);
					return r;
				}
				//	（2）更新学员排班状态
				StudentClass record = new StudentClass();
				record.setState(scState); //排班状态：0-正常；1-已成功；2-已取消；3-已超时；',
				StudentClassExample example = new StudentClassExample();
				example.createCriteria()
				.andOrderIdEqualTo(orderId)
				.andStudentIdEqualTo(Long.parseLong(userId));
				studentClassMapper.updateByExampleSelective(record,example);

				
				Car car = carManager.getCarInfo(scp.getCarId().intValue());	//获取车辆信息
				// （4）为教练添加排班
				//saveCoachClass(coachClassVo);
				CoachClassVo ccv = new CoachClassVo();
				ccv.setCtype(ReqConstants.BOOK_TYPE_FUTURE);
				ccv.setCoachId(scp.getCoachId());
				ccv.setCstart(sc.getCstart());
				ccv.setCend(sc.getCend());
				ccv.setRstart(sc.getCstart());
				ccv.setRend(sc.getCend());
				ccv.setOrderId(sc.getOrderId());
				ccv.setMaxNum(1);
				ccv.setBookNum(1);
				ccv.setCarId(car.getCarId());
				ccv.setCarName(car.getCarType());
				ccv.setCarImg(car.getCarImg());
				ccv.setCarNo(car.getCarNo());
				ccv.setCourseId(sc.getCourseId()+"");
				ccv.setCourseName(sc.getCourseName());
				ccv.setPlaceId(scp.getPlaceId());
				ccv.setPlaceName(scp.getPlaceName());
				ccv.setPrice(sc.getPrice());
				ccv.setDltype(sc.getDltype()+0);
				ccv.setTid(null);//排班时段主键
				ccv.setLge(scp.getPlaceLge());
				ccv.setLae(scp.getPlaceLae());
				coachClassService.saveCoachClass(ccv);
				
				OrderVo ov = new OrderVo();
				ov.setOrderId(sc.getOrderId());
				ov.setCoachId(scp.getCoachId());
				ov.setCourseId(sc.getCourseId().toString());
				ov.setPrice(sc.getPrice());
				ov.setLearnAddr(scp.getPlaceName());
				ov.setStudentId(sc.getStudentId());
				ov.setDltype(sc.getDltype().intValue());
				ov.setLge(sc.getLge());	//学员上车的经纬度
				ov.setLae(sc.getLae());
				ov.setStuAddr(sc.getPlaceInfo());
				ov.setPstart(sc.getCstart());
				ov.setPend(sc.getCend());
				ov.setClzNum(sc.getClznum().intValue());
				ov.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);	//设置为已接单状态
				ov.setRstart(sc.getCstart());	//这个实际上下课时间比较奇怪，需要先用计划时间初始化
				ov.setRend(sc.getCend());
				ov.setCstart(sc.getCstart());	//教练出发时间	这个时间比较奇怪，暂时没有用到。
				ov.setCend(sc.getCend());		//教练回场时间
				ov.setPayState(OrderConstant.PAYSTATE.WAITPAY);	//设置为未支付状态
				ov.setNeedTrans(0);	//设置未不需要接送
				ov.setGtime(sc.getCtime());		//设置下单时间
				ov.setAtime(scp.getMtime());	//设置接单时间
				ov.setOtype(OrderConstant.OTYPE.BOOKORDER); 	//设置未预约订单。是否需要新增订单类型？FIXME
				
				
				// （5）获取排班id，这里太挫了。。。因为海云哥生成的mapper层代码不好用，懒得改
				CoachClassVo search=new CoachClassVo();
				search.setOrderId(ccv.getOrderId());
				search.setCoachId(ccv.getCoachId());
				search.setCtype(ccv.getCtype());
				List<CoachClassVo> list=coachClassService.queryByObjectAnd(search, new CoachClassQuery());
				int ccid = list.get(0).getCcid();
				ov.setCcid(ccid);// 排班主键 
				ov.setPlaceId(scp.getPlaceId());
				
				ov.setCoachName(scp.getCoachName());
				ov.setCoachImg(scp.getCoachImg());
				ov.setCoachMobile(scp.getCoachMobile());
				ov.setCoachStar(scp.getStarLevel());
				ov.setStuName(sc.getStuName());
				ov.setStuImg(sc.getStuImg());
				ov.setStuMobile(sc.getStuMobile());
				ov.setCourseName(sc.getCourseName());
				ov.setPriceTotal(sc.getPrice());
				ov.setPayTotal(sc.getPrice());
				
				ov.setPlaceLge(scp.getPlaceLge());//上课地点的经纬度
				ov.setPlaceLae(scp.getPlaceLae());
				ov.setCityId(sc.getRegionId());
				ov.setSchoolId(null); //订单所属驾校	FIXME 从教练资源池获取
				
				ov.setAllowance(5000); //教练补贴;如果学员在不允许的时间内取消了订单，则需要给教练支付这个补贴费用。20160823
		        
		        if (car != null) {
		            //前端没有传取出后需要保存
		            ov.setCarId(car.getCarId());
		            ov.setCarName(car.getCarType());
		            ov.setCarImg(String.valueOf(car.getCarLevel()));
		            ov.setCarNo(car.getCarNo());
		        }
				
				r = orderService.bookStudentOrder(ov); //生成订单时已有推送
				
			}else if(scState == 2){
				//	（1）未接单之前是可以取消的
				StudentClassExample scexample = new StudentClassExample();
				scexample.createCriteria()
				.andOrderIdEqualTo(orderId)
				.andStudentIdEqualTo(Long.parseLong(userId));
				scexample.setOrderByClause("id desc limit 1");
				List<StudentClass> studentClasses = studentClassMapper.selectByExample(scexample);
				
				
				for(StudentClass sc:studentClasses){
					if(sc.getState().intValue()==0){
						
						
						
//						（2）更新学员排班状态
						StudentClass record = new StudentClass();
						record.setState(scState); //排班状态：0-正常；1-已成功；2-已取消；3-已超时；',
						StudentClassExample example = new StudentClassExample();
						example.createCriteria()
						.andOrderIdEqualTo(orderId)
						.andStudentIdEqualTo(Long.parseLong(userId));
						int id = studentClassMapper.updateByExampleSelective(record,example);
						if(sc.getDirect()==1){//2.1后才有
							
							StudentClassPoolExample key = new StudentClassPoolExample();
							key.createCriteria().andOrderIdEqualTo(orderId);
							
							List<StudentClassPool> scps =  studentClassPoolMapper.selectByExample(key);
							
							if(scps!=null&&scps.size()>0){
							String content = "学员取消了订单";
							JpushMsg jmsg = new JpushMsg();
						    Map<String,String> extras=new HashMap<String,String>();
						    extras.put("orderId", sc.getOrderId());
						    extras.put("courseName", sc.getCourseName());
						    extras.put("clznum", sc.getClznum().toString());
						    extras.put("cStart", sc.getCstart().toString());
						    extras.put("price", sc.getPrice().toString());
						    jmsg.setExtras(extras);
							jmsg.setAlter(content);
							jmsg.setUserId(scps.get(0).getCoachId());
							jmsg.setOrderId(sc.getOrderId());
						    jmsg.setOperate(JpushConstant.OPERATE.STU_CLASS);//如果有extras，则operate要放在extra之后
						    jmsg.setSound("sound102.wav"); //声音文件更改
							Message jpush = new Message();
							jpush.setKeys(sc.getOrderId());
							jpush.setTopic(jpushProducer.getCreateTopicKey());
							jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
							jpush.setBody(SerializableUtil.serialize(jmsg));
							jpushProducer.send(jpush);
							
							//消息中心，订单通知
	//						Notice notice = new Notice();
	//						Map<String, String> extra = new HashMap<>();
	//						notice.setTitle("接单啦~");
	//						notice.setDigest("有新的订单，快来接单吧。");
	//						notice.setType((byte)2);
	//						notice.setTime(new Date());
	//						notice.setUserType((byte)ReqConstants.USER_TYPE_COACH);
	//						notice.setUserId(coachId);
	//						notice.setOrderId(sc.getOrderId());
	//						extra.put("\"orderId\"", "\"\"");
	//						extra.put("\"type\"", "\"2\"");
	//						notice.setExtra(extra.toString().replace("=", ":"));
	//						noticeManager.addNotice(notice);
							}
						}
					}
				}
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return r;
	}
	

	@Override
	public Page<StudentClassVo> getScheduleOrders(String stime,String etime,
			String state, String lastMinutes, String orderId, String stuName,
			String stuMobile, String pageNo, String pageSize) {
		
		try {
			StudentClassExample example = new StudentClassExample();
			StudentClassExample.Criteria criteria = example.createCriteria();
			if(StringUtil.isNotNullAndNotEmpty(stime) && StringUtil.isNotNullAndNotEmpty(etime)){
				Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(stime);
				Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(etime);
				criteria.andCtimeBetween(d1, d2);
			}
			if(StringUtil.isNotNullAndNotEmpty(state)){
				byte stateInt = Byte.parseByte(state.trim());
				if(stateInt != -1){
					criteria.andStateEqualTo(stateInt);
				}
			}
			if(StringUtil.isNotNullAndNotEmpty(orderId)){
				criteria.andOrderIdEqualTo(orderId);
			}
			if(StringUtil.isNotNullAndNotEmpty(stuName)){
				criteria.andStuNameLike(stuName);
			}
			if(StringUtil.isNotNullAndNotEmpty(stuMobile)){
				criteria.andStuMobileLike(stuMobile);
			}
			if(StringUtil.isNotNullAndNotEmpty(lastMinutes)){
				int lastMin = Integer.parseInt(lastMinutes);
				criteria.andLastMinutesGreaterThanOrEqualTo(lastMin);
			}
			
			int total = studentClassMapper.countByExample(example);
			
			example.setOrderByClause("state,ctime desc");
			int pNo =1;
			int pSize = 100;
			if(StringUtil.isNotNullAndNotEmpty(pageNo) && StringUtil.isNotNullAndNotEmpty(pageSize)){
				pNo = Integer.parseInt(pageNo.trim());
				if(pNo <= 0){
					pNo =1;
				}
				pSize =	Integer.parseInt(pageSize.trim());
				if(pSize <= 0){
					pSize =100;
				}
			}
			RowBounds rowBounds = new RowBounds((pNo -1)*pSize, pSize);//(offset,limit)
			List<StudentClassVo> scvs = studentClassMapper.selectVoByExample(example,rowBounds);
			return new Page<StudentClassVo>(scvs,pNo,pSize,total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public Integer getScheduleNoticeCount() {
		int count =0;
		try {
			StudentClassExample example = new StudentClassExample();
			StudentClassExample.Criteria criteria = example.createCriteria();
			criteria.andStateEqualTo((byte) 0)  //'排班状态：0-正常；1-已成功；2-已取消；3-已超时；',
			.andAcceptNumEqualTo(0);
			count = studentClassMapper.countVoByExample(example);
			if(count != 0){
				//如果待调度的订单数目不是0，则检查订单是否已超时。
				RowBounds rowBounds = new RowBounds(0, count);//(offset,limit)
				List<StudentClassVo> scvs = studentClassMapper.selectVoByExample(example,rowBounds);
				for(StudentClassVo cv:scvs){
					//检查订单是否已超时
					Date cstart = cv.getCstart();
					int hour = TimeUtil.timeSpan(new Date(), cstart, "hour");
					if(hour >0){
						if(hour <=1){ //>0,是还没有到订单开始时间，如果相差小于1个小时，则认为超时；
							//做超时处理
							this.setStudentClassOvertime(cv.getOrderId(), cv.getStudentId());
							count --;
						}
					}else{
						//<0,是已经过了订单开始时间，肯定已经超时了；
						this.setStudentClassOvertime(cv.getOrderId(), cv.getStudentId());
						count --;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@Override
	public Page<StudentClassPoolVo> getScheduleCoaches(String orderId, String dltype, String isVip, String pageNo, String pageSize) {
		try {
			int pNo =1;
			int pSize = 100;
			if(StringUtil.isNotNullAndNotEmpty(pageNo) && StringUtil.isNotNullAndNotEmpty(pageSize)){
				pNo = Integer.parseInt(pageNo.trim());
				if(pNo <= 0){
					pNo =1;
				}
				pSize =	Integer.parseInt(pageSize.trim());
				if(pSize <= 0){
					pSize =100;
				}
			}
			// RowBounds rowBounds = new RowBounds((pNo -1)*pSize, pSize);//(offset,limit)
			
			StudentClassPoolExample example = new StudentClassPoolExample();
			example.createCriteria()
			.andOrderIdEqualTo(orderId);
			example.setOrderByClause("distance asc");
			//.andStateEqualTo((byte) 1); //状态：0-已推送教练；1-教练已接单；2-教练已取消；
			
			// int total = studentClassPoolMapper.countByExample(example);
			
			// List<StudentClassPool> scps = studentClassPoolMapper.selectByExampleWithRowbounds(example,rowBounds);
			List<StudentClassPool> scps = studentClassPoolMapper.selectByExample(example);
			List<StudentClassPoolVo> scpvs = new ArrayList<StudentClassPoolVo>();
			//scpvs=BeanCopy.copyList(scps,StudentClassPoolVo.class,BeanCopy.COPYNOTNULL);
			List<Long> coachIds = new ArrayList<Long>();
			for(int i=0;i<scps.size();i++){
				StudentClassPoolVo scpv= new StudentClassPoolVo();
				StudentClassPool scp = scps.get(i);
				//BeanCopy.copyNotNull(scp, scpv);
				scpv.setCoachId(scp.getCoachId());
				scpv.setOrderId(scp.getOrderId());
				scpv.setCoachImg(scp.getCoachImg());
				scpv.setCoachMobile(scp.getCoachMobile());
				scpv.setCoachName(scp.getCoachName());
				scpv.setCtime(scp.getCtime());
				scpv.setDistance(scp.getDistance());
				scpv.setExtra(scp.getExtra());
				scpv.setMtime(scp.getMtime());
				scpv.setPlaceId(scp.getPlaceId());
				scpv.setPlaceLae(scp.getPlaceLae());
				scpv.setPlaceLge(scp.getPlaceLge());
				scpv.setPlaceName(scp.getPlaceName());
				scpv.setSchoolName(scp.getSchoolName());
				scpv.setStarLevel(scp.getStarLevel());
				scpv.setState(scp.getState());
				scpv.setCarId(scp.getCarId());
				scpv.setCarNo(scp.getCarNo());
				scpvs.add(scpv);
				coachIds.add(scp.getCoachId());
			}
			//List<Long> coachIds=BeanCopy.getFieldList(scps,"coachId");
			List<CoachVo> coachList=coachManager.getCoachesByIds(coachIds);
			for(int i=0;i<scpvs.size();i++){
				StudentClassPoolVo scpv = scpvs.get(i);
				long coachId = scpv.getCoachId();
				for(CoachVo cv:coachList){
					if(cv.getCoachId() == coachId){
						scpv.setGender(cv.getSex());
						scpv.setDltype(cv.getDriveType());
						scpv.setWstate(cv.getWstate());
						scpv.setIsVip(cv.getExtra().equals("1")?1:0);
						break;
					}else {
						continue;
					}
				}
				if(null != dltype && !"".equals(dltype)){
					if(!scpv.getDltype().toString().equals(dltype)){
						scpvs.remove(i);
						i--;
						continue;
					}
				}
				if(null != isVip && !"".equals(isVip)){
					if(!scpv.getIsVip().toString().equals(isVip)){
						scpvs.remove(i);
						i--;
						continue;
					}
				}
			}
			
			int total = scpvs.size();
			List<StudentClassPoolVo> scpvsSub = new ArrayList<StudentClassPoolVo>(); //内存分页
			if(pNo *pSize <= total){
				scpvsSub = scpvs.subList((pNo-1) *pSize, pNo *pSize);
			}else {
				scpvsSub = scpvs.subList((pNo-1) *pSize, total);
			}
			return new Page<StudentClassPoolVo>(scpvsSub,pNo,pSize,total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReqResult addScheduleCoach(String orderId, String coachId, String placeId,
			String placeName, String placeLge, String placeLae) {
		return this.setStudentsClassState(coachId, null, orderId, "1", placeId, placeName, placeLge, placeLae,null);
	}
	
	
	

}













































