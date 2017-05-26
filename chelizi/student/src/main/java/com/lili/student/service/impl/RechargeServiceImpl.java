package com.lili.student.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.redisson.RedissonClient;
import org.redisson.core.RLock;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.service.AuthcodeService;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RechargeConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.MobileUtil;
import com.lili.common.util.Page;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.dto.Coupon;
import com.lili.coupon.service.CouponService;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;
import com.lili.pay.vo.PurposeType;
import com.lili.school.dto.EnrollOrder;
import com.lili.school.manager.EnrollOrderManager;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.lili.student.service.RechargeGearService;
import com.lili.student.service.RechargePlanService;
import com.lili.student.service.RechargeRecordService;
import com.lili.student.service.RechargeService;
import com.lili.student.service.VipCompanyService;
import com.lili.student.service.VipCustomService;
import com.lili.student.vo.CouponList;
import com.lili.student.vo.RechargeGearQuery;
import com.lili.student.vo.RechargeGearVo;
import com.lili.student.vo.RechargePlanQuery;
import com.lili.student.vo.RechargePlanVo;
import com.lili.student.vo.RechargeRecordQuery;
import com.lili.student.vo.RechargeRecordVo;
import com.lili.student.vo.VipCompanyQuery;
import com.lili.student.vo.VipCompanyVo;
import com.lili.student.vo.VipCustomQuery;
import com.lili.student.vo.VipCustomVo;

public class RechargeServiceImpl implements RechargeService {

	private Logger log = Logger.getLogger(RechargeServiceImpl.class);

	@Autowired
	private RechargePlanService rechargePlanService;
	@Autowired
	private RechargeGearService rechargeGearService;
	@Autowired
	private VipCompanyService vipCompanyService;
	@Autowired
	private VipCustomService vipCustomService;
	@Autowired
	private RechargeRecordService rechargeRecordService;

	@Autowired
	private StudentManager studentManager;
	@Autowired
	private CoachManager coachManager;
	@Autowired
    RedisUtil redisUtil;
	//分布式锁
	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private MoneyManager moneyManager;
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	@Autowired
	private CouponService couponService;
	@Autowired
	AuthcodeService authcodeService;
	@Autowired
	EnrollOrderManager enrollOrderManager;
	
	/**
	 * 扫码部分： 获取大客户公司信息列表，用于用户选择自己的公司,可以根据城市等条件查询 
	 * 另外隐藏：我们需要先创建2个套餐，并将其id隐藏在页面中
	 * 注意：如果每个公司一个二维码，那么该接口也可以不使用，而将companyId隐藏其中
	 */
	public List<VipCompanyVo> getVipCompanyList(VipCompanyVo search,
			int pageSize, int pageIndex) {
		try {
			if (search == null) {
				search = new VipCompanyVo();
			}
			search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			search.setVstate(OrderConstant.VERIFY.PASS);
			VipCompanyQuery query = new VipCompanyQuery();
			query.setorderBy("order by ctime desc");
			query.setPageIndex(pageIndex);
			query.setPageSize(pageSize);
			return vipCompanyService
					.queryByObjectAnd(search, query);
		} catch (Exception e) {
			log.error(
					search + " getVipCompanyList exception " + e.getMessage(),
					e);
			return null;
		}
		
	}
	/**
	 * 计算大客户公司分页
	 * @param search
	 * @param pageSize
	 * @return
	 */
	public Page<VipCompanyVo> countVipCompany(VipCompanyVo search,int pageSize) {
		try {
			if (search == null) {
				search = new VipCompanyVo();
			}
			search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			search.setVstate(OrderConstant.VERIFY.PASS);
			VipCompanyQuery query = new VipCompanyQuery();
			int total= vipCompanyService.countByObject(search, query);
			Page<VipCompanyVo> page=new Page<VipCompanyVo>();
			page.setTotal(total);
			page.setPageSize(pageSize);
			page.setPages((total+pageSize-1)/pageSize);
			return page;
		} catch (Exception e) {
			log.error(
					search + " getVipCompanyList exception " + e.getMessage(),
					e);
			return null;
		}
	}
	/**
	 * 扫码部分：大客户录入，有几种情况： 1.已是大客户学员 2.是已注册学员，但非vip 3.是未注册学员
	 * canOld: 是否可以将老客户标记为大客户
	 */
	public String addVipCustom(VipCustomVo vipCustomVo,boolean canOld) throws Exception {
			// 1.参数判断
			if (vipCustomVo == null
							|| StringUtil.isNullOrEmpty(vipCustomVo.getMobile())) {
				log.error(vipCustomVo + " mobile is empty.");
				return ResultCode.ERRORCODE.RECHARGE_NEED_MOBILE;
			}
			//检查手机号码
			if (!MobileUtil.isMobile(vipCustomVo.getMobile()))
	        {	
				log.error(vipCustomVo + " mobile is incorrect.");
	            return ResultCode.ERRORCODE.MOBILE_NUMBER_ERROR;
	        }
			
			RechargePlanVo plan=null;
			//因为是候选优惠方案，因此这个可以不报错
			if (vipCustomVo.getRcid() == null) {
				log.error(vipCustomVo + " plan is empty.");
//				return ResultCode.ERRORCODE.RECHARGE_NEED_PLAN;
			//但如果有错误优惠方案就要报错
			} else {
				plan = rechargePlanService.queryRechargePlanById(vipCustomVo.getRcid());
				if (plan == null
						|| plan.getIsdel() != OrderConstant.ISDEL.NOTDELETE
						|| plan.getVstate() != OrderConstant.VERIFY.PASS
						|| plan.getActive() != OrderConstant.ACTIVE.ACTIVE) {
					log.error(vipCustomVo + " plan is not valide=" + plan);
					return ResultCode.ERRORCODE.RECHARGE_END_PLAN;
				}
				vipCustomVo.setRcname(plan.getRcname());
			}
			VipCompanyVo company=null;
			if(vipCustomVo.getCoid()==null){
				log.error(vipCustomVo + " company is empty.");
				return ResultCode.ERRORCODE.PARAMERROR;
			} else {
				company=vipCompanyService.queryVipCompanyById(vipCustomVo.getCoid());
				if(company==null) {
					log.error(vipCustomVo + " company is get empty.");
					return ResultCode.ERRORCODE.PARAMERROR;
				} else if(company.getVtype()==RechargeConstant.VIPTYPE.VIPRETAIL && plan==null){
					log.error(vipCustomVo + " plan is empty VIPRETAIL.");
					return ResultCode.ERRORCODE.RECHARGE_NEED_PLAN;
				} else if (company.getActive() != RechargeConstant.VSTATE.PASS){
					log.error(vipCustomVo + " company  VSTATE IS NOT PASS.");
					return ResultCode.ERRORCODE.RECHARGE_END_PLAN;
				}
			}
			// 2.用户判断
			Student s = studentManager.getStudentByPhoneNum(vipCustomVo
					.getMobile());
			List<VipCustomVo> vips =null;
			if(s!=null){
				vips = vipCustomService.queryByStudentId(s.getStudentId(),new VipCustomQuery());
			} else {
				vips = vipCustomService.queryByMobile(vipCustomVo.getMobile(), new VipCustomQuery());
			}
			
			vipCustomVo.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			vipCustomVo.setVstate(OrderConstant.VERIFY.WAIT);
			vipCustomVo.setCtime(new Date());
			vipCustomVo.setMtime(null);
			/*if (StringUtil.isNotNullAndNotEmpty(vipCustomVo.getInviteCode()) && company.getRemark() != null && company.getRemark().equals(vipCustomVo.getInviteCode())) { //校验邀请码是否正确
				vipCustomVo.setVstate(OrderConstant.VERIFY.PASS);
			}
			else {*/
				vipCustomVo.setVstate(OrderConstant.VERIFY.WAIT);
			//}
			
			if(s!=null) {
				vipCustomVo.setStudentId(s.getStudentId());
				vipCustomVo.setCuid(s.getStudentId());
				//如果是已报名不能使用的方案则报错
				if(plan!=null && plan.getRegUse()==RechargeConstant.ENROLLGUSE.CANNOTUSE && isRegister(s)){
					log.error(vipCustomVo + " plan cannot use after enroll="+plan+", with student="+s);
					return ResultCode.ERRORCODE.RECHARGE_ENROLL_CANNOTUSE;
				}
				
			}
			if (vips != null && !vips.isEmpty()) {
				// 1. 大客户已经是vip,不允许覆盖
				if(company.getVtype()==RechargeConstant.VIPTYPE.VIPBIG && vips.get(0).getIsdel()!=OrderConstant.ISDEL.DELETE&&vips.get(0).getVstate()==OrderConstant.VERIFY.PASS) {
					log.error(vipCustomVo + " is aready a vip=" + vips);
					return ResultCode.ERRORCODE.RECHARGE_AREAD_VIP;
				//普惠客户+大客户已删除或者未审核：方案覆盖
				} else {
					vipCustomVo.setCtime(vips.get(0).getCtime());
					vipCustomVo.setMobile(s.getPhoneNum());
				}
			}
			if (s != null) {
				//允许老用户注册大客户
//				if(canOld){
					//如果是驾校学员，需要报错退出大客户
				    //普惠则是所有学员享受
					if(company.getVtype()==RechargeConstant.VIPTYPE.VIPBIG && s.getIsImport() == (byte)1){ 
						log.error(vipCustomVo + " is not lili=" + s);
						return  ResultCode.ERRORCODE.RECHARGE_NOT_LILISTU;
					}
					vipCustomService.saveVipCustom(vipCustomVo);
//				} else {
//					log.error(s+" exists so can't be a vip:"+vipCustomVo);
//					return ResultCode.ERRORCODE.RECHARGE_AREAD_STU;
//				}
				// 3.既不是学员也不是vip，两边添加
			} else {
				// 添加学员
				s = new Student();
				s.setPhoneNum(vipCustomVo.getMobile());
				s.setPassword("");
				s.setAgreement((byte) 1);
				s.setName(vipCustomVo.getCname());
				studentManager.addStudent(s);
				long studentId = s.getStudentId();
				// 20160920添加大客户额外信息
				vipCustomVo.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				
				vipCustomVo.setStudentId(studentId);
				vipCustomVo.setCuid(studentId);
				vipCustomVo.setCtime(new Date());
				vipCustomService.addVipCustom(vipCustomVo);
			}
			String result=ResultCode.ERRORCODE.SUCCESS;
			//自动审核
			if((plan!=null && plan.getNeedVerify()==RechargeConstant.NEEDVERIFY.NEEDNOT)  || (StringUtil.isNotNullAndNotEmpty(vipCustomVo.getInviteCode()) && company.getRemark() != null && company.getRemark().equals(vipCustomVo.getInviteCode()))){
				List<Long> studentIds=new ArrayList<Long>();
				studentIds.add(s.getStudentId());
				result=this.passCustom(studentIds, s.getStudentId());
			}
			
			//20160922增加优惠券发放
			if (plan != null && StringUtil.isNotNullAndNotEmpty(plan.getCouponTemplate()) && StringUtil.isNotNullAndNotEmpty(plan.getCouponNumber())) {
				String [] templateId=plan.getCouponTemplate().split(",");
				String [] number=plan.getCouponNumber().split(",");
				for (int c=0;c<templateId.length;c++) {
					int no=Integer.valueOf(number[c]);
					String id=templateId[c];
					for (int k=0;k<no;k++) {
						boolean jpush=false;
						//一个学员发券只推送一次
						if((c==templateId.length-1) && (k==no-1)) {
							jpush=true;
						}
						VipCustomVo custom=vipCustomVo;
						//目前扫码发券直接在新增时发送，因此如果已经发券，则不再发送，，但是客户新增的还是在这里发
						if(custom.getCoupon()==null) {
						ReqResult resultCoupon=couponService.genStudentCouponAndNotify(id, custom.getStudentId(),jpush);
						if("0".equals(resultCoupon.getResult().get(ResultCode.RESULTKEY.CODE))){
							String coupon=custom.getCoupon();
							if(StringUtil.isNullOrEmpty(coupon)){
								coupon=String.valueOf(resultCoupon.getResult().get("couponId"));
							} else {
								coupon+=","+String.valueOf(resultCoupon.getResult().get("couponId"));
							}
							custom.setCoupon(coupon);
						} else {
							String couponLack=custom.getCouponLack();
							if(StringUtil.isNullOrEmpty(couponLack)){
								couponLack=id;
							} else {
								couponLack+=","+id;
							}
							custom.setCouponLack(couponLack);
							}
						}
					}
				}
				//保存发送的优惠券
				vipCustomService.saveVipCustom(vipCustomVo);
				//couponService.obtainCouponByPhone(vipCustomVo.getMobile(), vipCustomVo.getCouponTmpId());
			}
			return result;
	}
	/**
	 * 给大客户员工添加或更新套餐，注意已审核的不可变更
	 * 已经报名不再发券
	 * @param studentId
	 * @param rcid
	 * @return
	 * @throws Exception
	 */
	public String selectRechargePlan(String mobile,int rcid) throws Exception {
		Student s = studentManager.getStudentByPhoneNum(mobile);
		if(s==null){
			log.error(mobile+",rcid="+rcid+", with "+s+" is not exists student.");
			return ResultCode.ERRORCODE.PARAMERROR;
		}
		VipCustomVo v=vipCustomService.queryVipCustomById(s.getStudentId());
		if(v==null || v.getVstate()!=OrderConstant.VERIFY.WAIT){
			log.error(mobile+",rcid="+rcid+", with "+v+" is not WAIT.");
			return ResultCode.ERRORCODE.PARAMERROR;
		}
		
		RechargePlanVo plan = rechargePlanService.queryRechargePlanById(rcid);
		if(plan==null || plan.getIsdel()!=OrderConstant.ISDEL.NOTDELETE || plan.getVstate()!=OrderConstant.VERIFY.PASS || plan.getActive()!=OrderConstant.ACTIVE.ACTIVE){
			log.error(mobile+",rcid="+rcid+", with "+plan+" is not correct paln.");
			return ResultCode.ERRORCODE.RECHARGE_NEED_PLAN;
		}
		//如果是已报名不能使用的方案则报错
		if(plan!=null && plan.getRegUse()==RechargeConstant.ENROLLGUSE.CANNOTUSE && isRegister(s)){
			log.error(mobile + " plan cannot use after enroll="+plan+", with student="+s);
			return ResultCode.ERRORCODE.RECHARGE_ENROLL_CANNOTUSE;
		}
		studentManager.updateStudent(s);
		//添加套餐信息
		v.setRcid(rcid);
		v.setRcname(plan.getRcname());
		//选择方案的时候即发券
		if(StringUtil.isNotNullAndNotEmpty(plan.getCouponTemplate())){
			//已经报名的不发券
			if(isRegister(s)){
				log.info(s+" has register, so DONOT send "+plan.getCouponTemplate()+".");
			//未报名的进行发券
			} else {
				ReqResult result=couponService.genStudentCouponAndNotify(plan.getCouponTemplate(), v.getStudentId(),true);
				if("0".equals(result.getResult().get(ResultCode.RESULTKEY.CODE))){
					String coupon=v.getCoupon();
					if(StringUtil.isNullOrEmpty(coupon)){
						coupon=String.valueOf(result.getResult().get("couponId"));
					} else {
						coupon+=","+String.valueOf(result.getResult().get("couponId"));
					}
					v.setCoupon(coupon);
				} else {
					log.error(v+" send "+plan.getCouponTemplate()+" with result="+result);
					String couponLack=v.getCouponLack();
					if(StringUtil.isNullOrEmpty(couponLack)){
						couponLack=plan.getCouponTemplate();
					} else {
						couponLack+=","+plan.getCouponTemplate();
					}
					v.setCouponLack(couponLack);
				}
		   }
		}
		v.setMtime(null);
		vipCustomService.saveVipCustom(v);
		String result=ResultCode.ERRORCODE.SUCCESS;
		//自动审核
		if(plan!=null && plan.getNeedVerify()==RechargeConstant.NEEDVERIFY.NEEDNOT){
			List<Long> studentIds=new ArrayList<Long>();
			studentIds.add(s.getStudentId());
			result=this.passCustom(studentIds, s.getStudentId());
		}
		return result;
	}
	/**
	 * 客服新增不存在的大客户
	 * @param student
	 * @param cuid
	 * @return
	 */
	public String addVipCustom(Student student,long cuid) throws Exception{
			//检查手机号码
			if (!MobileUtil.isMobile(student.getPhoneNum()))
	        {	
				log.error(student + " mobile is incorrect.");
	            return ResultCode.ERRORCODE.MOBILE_NUMBER_ERROR;
	        }
		    // 1.参数判断
			if (student == null
					|| StringUtil.isNullOrEmpty(student.getPhoneNum())) {
				log.error(student + " mobile is empty.");
				return ResultCode.ERRORCODE.RECHARGE_NEED_MOBILE;
			}
			if (student.getVipPackageId() == null||student.getVipId()==null) {
				log.error(student + " plan is empty.");
				return ResultCode.ERRORCODE.RECHARGE_NEED_PLAN;
			}
			RechargePlanVo plan = rechargePlanService
					.queryRechargePlanById(Integer.valueOf(student.getVipPackageId()));
			if (plan == null
					|| plan.getIsdel() != OrderConstant.ISDEL.NOTDELETE
					|| plan.getVstate() != OrderConstant.VERIFY.PASS
					|| plan.getActive() != OrderConstant.ACTIVE.ACTIVE) {
				log.error(student + " plan is not valide=" + plan);
				return ResultCode.ERRORCODE.RECHARGE_NEED_PLAN;
			}
			//公司判断
			if(student.getVipId()==null){
				log.error(student + " company is empty.");
				return ResultCode.ERRORCODE.PARAMERROR;
			} else {
				VipCompanyVo company=vipCompanyService.queryVipCompanyById(student.getVipId());
				if(company==null) {
					log.error(student + " company is get empty.");
					return ResultCode.ERRORCODE.PARAMERROR;
				}  else if (company.getActive() != RechargeConstant.VSTATE.PASS){
					log.error(company + " company  VSTATE IS NOT PASS.");
					return ResultCode.ERRORCODE.RECHARGE_END_PLAN;
				}
			}
			// 2.用户判断
			Student s = studentManager.getStudentByPhoneNum(student.getPhoneNum());
			List<VipCustomVo> vips = vipCustomService.queryByMobile(
					student.getPhoneNum(), new VipCustomQuery());
			// 1. 已经是vip
			if (vips != null && !vips.isEmpty()) {
				log.error(student + " is aready a vip=" + vips);
				return ResultCode.ERRORCODE.RECHARGE_AREAD_VIP;
				// 2.已经是学员，根据产品要求，需要报错
			} else if (s != null) {
				//不允许老用户注册大客户
				log.error(s+" exists so can't be a vip:"+student);
				return ResultCode.ERRORCODE.RECHARGE_AREAD_STU;
				// 3.既不是学员也不是vip，两边添加
			} else {
				studentManager.addStudent(student);
				long studentId = student.getStudentId();
				VipCustomVo vipCustomVo=new VipCustomVo();
				vipCustomVo.setCoid(student.getVipId());
				vipCustomVo.setRcid(plan.getRcid());
				vipCustomVo.setCuid(cuid);
				vipCustomVo.setMobile(student.getPhoneNum());
				vipCustomVo.setCname(student.getName());
				vipCustomVo.setRcname(plan.getRcname());
				vipCustomVo.setCid(student.getCid());
				// 添加大客户额外信息
				vipCustomVo.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				vipCustomVo.setVstate(OrderConstant.VERIFY.WAIT);
				vipCustomVo.setStudentId(studentId);
				vipCustomVo.setCuid(studentId);
				vipCustomVo.setCtime(new Date());
				vipCustomService.addVipCustom(vipCustomVo);
			}
			return ResultCode.ERRORCODE.SUCCESS;
	}
	/**
	 * 新建大客户公司
	 * @param company
	 * @return
	 */
	public String createVipCompany(VipCompanyVo company,long cuid) throws Exception {
			//1. 参数判断,更多请产品确认
			if(company==null ||StringUtil.isNullOrEmpty(company.getCompany())||StringUtil.isNullOrEmpty(company.getMobile()) ){
				log.error(company+" createVipCompany PARAMERROR.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			List<VipCompanyVo> e=vipCompanyService.queryByCompany(company.getCompany(), new VipCompanyQuery());
			if(e!=null && !e.isEmpty()){
				log.error(
						company + " has exist same name."+e);
				return ResultCode.ERRORCODE.RECHARGE_COMPANY_EXIST;
			}
			//2. 默认状态：已审核
			company.setVstate(OrderConstant.VERIFY.PASS);
			//3.其他默认参数
			company.setVtype(RechargeConstant.VIPTYPE.VIPBIG);
			company.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			company.setCtime(new Date());
			company.setCuid(cuid);
			String remark = null;
			Date date = new Date(); 
	    	int number=(int)(Math.random()*100000);
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd"); 
	    	remark = dateFormat.format(date) + number;
			company.setRemark(remark);
			vipCompanyService.addVipCompany(company);
			return ResultCode.ERRORCODE.SUCCESS;
	}
	
	/**
	 * 获取大客户公司下的所有员工的一页
	 */
	public List<Student> getCustomList(VipCustomVo search,Boolean vipBig,int pageSize, int pageIndex) throws Exception {
		if(search==null){
			search=new VipCustomVo();
		}
		VipCustomQuery vipCustomQuery=new VipCustomQuery();
		vipCustomQuery.setorderBy("order by ctime desc");
		vipCustomQuery.setPageIndex(pageIndex);
		vipCustomQuery.setPageSize(pageSize);
		search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
		//未生效的特殊处理
		if(search.getVstate()!=null && search.getVstate()!=OrderConstant.VERIFY.PASS){
			search.setVstate(OrderConstant.VERIFY.WAIT);
		}
		List<VipCustomVo> customList=null;
		if(vipBig==null || vipBig) {
			customList=vipCustomService.queryByObjectAnd(search, vipCustomQuery);
		}
		List<Student> studentList=new ArrayList<Student>();
		if(customList!=null && !customList.isEmpty()){
			List<Long> stuIds=BeanCopy.getFieldList(customList, "studentId");
			List<Student> slist=studentManager.getStudentsByIds(stuIds);
			List<Integer> coids=BeanCopy.getFieldList(customList, "coid");
			List<VipCompanyVo> comList=vipCompanyService.queryVipCompanyByIds(coids, new VipCompanyQuery());
			Map<Integer,VipCompanyVo> comMap=new HashMap<Integer,VipCompanyVo>();
			for(int i=0;i<comList.size();i++){
				comMap.put(comList.get(i).getCoid(), comList.get(i));
			}
			BeanCopy.copyList(customList, slist, BeanCopy.COPY2NULL, "studentId");
			Map<Long,Student> smap=new HashMap<Long,Student>(); 
			//学员排序
			for(int i=0;i<slist.size();i++){
				smap.put(slist.get(i).getStudentId(), slist.get(i));
			}
			for(int i=0;i<stuIds.size();i++){
				studentList.add(smap.get(stuIds.get(i)));
			}
			//给学员添加公司名称
			for(int i=0;i<customList.size();i++){
				Long studentId=customList.get(i).getStudentId();
				Integer coid=customList.get(i).getCoid();
				smap.get(studentId).setCompany(comMap.get(coid).getCompany());
			}
			List<Integer> planIds=BeanCopy.getFieldList(customList, "rcid");
			List<RechargePlanVo> plans=rechargePlanService.queryRechargePlanByIds(planIds, new RechargePlanQuery());	
			//复制方案名称和方案过期状态至学员信息
			Map<String,RechargePlanVo> planMap=new HashMap<String,RechargePlanVo>();
			if(plans!=null && !plans.isEmpty()){
				for(int i=0;i<plans.size();i++){
					planMap.put(String.valueOf(plans.get(i).getRcid()), plans.get(i));
				}
				for(int i=0;i<studentList.size();i++){
					Student student=studentList.get(i);
					RechargePlanVo p=planMap.get(student.getVipPackageId());
					if(p!=null){
						student.setRcname(p.getRcname());
						RechargePlanVo ptmp=new RechargePlanVo();
						BeanCopy.copyAll(p, ptmp);
						EnrollOrder enroll=enrollOrderManager.getEnrollOrderByStudentId(student.getStudentId());
						if(enroll!=null) {
							student.setEnrollCity(enroll.getCityName());
						}
						ptmp=reState(ptmp, student,enroll);
					} else {
						log.error(p+" has empty on student:"+student);
					}
				}
			}
		} 
		//普惠方案追加普通学员
		List<Student> comlist=null;
				if(search!=null && search.getRcid()!=null&& (vipBig==null || !vipBig) ) {
					Student searchstu=new Student();
					searchstu.setName(search.getCname());
					searchstu.setPhoneNum(search.getMobile());
					int before=countVipBigList(search,vipBig);
					int start=pageSize*pageIndex - before;
					
					RechargePlanVo plan=rechargePlanService.queryRechargePlanById(search.getRcid());
					if(plan!=null && plan.getVtype()==RechargeConstant.VIPTYPE.VIPRETAIL&& plan.getCommon()==RechargeConstant.COMMON.FIRST && plan.getActive()==OrderConstant.ACTIVE.ACTIVE&& plan.getVstate()==OrderConstant.VERIFY.PASS && plan.getIsdel()==OrderConstant.ISDEL.NOTDELETE){
						if(start>0 && start<10){
							comlist=studentManager.queryByObjectAnd(searchstu, 0, start);
						} else if(start>=10){
							comlist=studentManager.queryByObjectAnd(searchstu, start-10, pageSize);
						}
						
					}
					//进行方案和状态复制
					for(int i=0;comlist!=null && i<comlist.size();i++) {
						Student s=comlist.get(i);
						s.setVstate(plan.getVstate());
						EnrollOrder enroll=enrollOrderManager.getEnrollOrderByStudentId(s.getStudentId());
						if(enroll!=null) {
							s.setEnrollCity(enroll.getCityName());
						}
						RechargePlanVo ptmp=new RechargePlanVo();
						BeanCopy.copyAll(plan, ptmp);
						ptmp=reState(ptmp,s,enroll);
						s.setRcname(ptmp.getRcname());
						s.setVstate(ptmp.getVstate());
						s.setCtime(ptmp.getCtime());
					}
					if(comlist!=null && !comlist.isEmpty()) {
						studentList.addAll(comlist);
					}
				}
		return studentList;
	}
	/**
	 * 获取大客户公司下员工的分页
	 * @param search
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page<Student> countCustomList(VipCustomVo search,Boolean vipBig,int pageSize) throws Exception {
		if(search==null){
			search=new VipCustomVo();
		}
		VipCustomQuery vipCustomQuery=new VipCustomQuery();
		//默认参数
		search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
		//未生效的特殊处理
		if(search.getVstate()!=null && search.getVstate()!=OrderConstant.VERIFY.PASS){
			search.setVstate(OrderConstant.VERIFY.WAIT);
			
		}
		int total=0;
		if(vipBig==null || vipBig) {
			total+=vipCustomService.countByObject(search, vipCustomQuery);
		}
		int common=0;
		//普惠方案追加普通学员
		if(search!=null && search.getRcid()!=null&&(vipBig==null || !vipBig)) {
			Student student=new Student();
			student.setName(search.getCname());
			student.setPhoneNum(search.getMobile());
			RechargePlanVo plan=rechargePlanService.queryRechargePlanById(search.getRcid());
			if(plan.getVtype()==RechargeConstant.VIPTYPE.VIPRETAIL&& plan.getCommon()==RechargeConstant.COMMON.FIRST && plan.getActive()==OrderConstant.ACTIVE.ACTIVE&& plan.getVstate()==OrderConstant.VERIFY.PASS && plan.getIsdel()==OrderConstant.ISDEL.NOTDELETE){
				common=studentManager.countByObjectAnd(student);	
			}
		}
		total=total+common;
		Page<Student> page=new Page<Student>();
		page.setTotal(total);
		page.setPageSize(pageSize);
		page.setPages((total+pageSize-1)/pageSize);
		return page;
	}
	public int countVipBigList(VipCustomVo search,Boolean vipBig) throws Exception {
		if(search==null){
			search=new VipCustomVo();
		}
		VipCustomQuery vipCustomQuery=new VipCustomQuery();
		//默认参数
		search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
		search.setVstate(null);
		int total=0;
		if(vipBig==null || vipBig) {
			total=vipCustomService.countByObject(search, vipCustomQuery);
		}
		return total;
	}
	/**
	 * 审核大客户员工通过
	 */
	public String passCustom(List<Long> studentIds,long muid) throws Exception{

			//参数判断
			if(studentIds==null || studentIds.isEmpty()) {
				log.error(
						studentIds + " passCustom param error.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			List<VipCustomVo> customList=vipCustomService.queryVipCustomByIds(studentIds, new VipCustomQuery());
			Map<Integer,List<VipCustomVo>> planMap=new HashMap<Integer,List<VipCustomVo>>();
			Map<Integer,Set<Long>> userMap=new HashMap<Integer,Set<Long>>();
			//获取所有充送方案
			Map<Integer,RechargePlanVo> plans=new HashMap<Integer,RechargePlanVo>();
			//记录数量不匹配
			if(customList==null || customList.size()<studentIds.size()){
				log.error(customList+" size not equal="+studentIds);
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			//判断记录有效性
			for(int i=0;i<customList.size();i++){
				VipCustomVo one=customList.get(i);
				one.setMtime(null);
				one.setMuid(muid);
				//防止对作废的记录进行审核
				if(one.getIsdel()!=OrderConstant.ISDEL.NOTDELETE) {
					log.error(one+" is delete when "+customList+" size equal="+studentIds);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//防止重复审核
				if(one.getVstate()==OrderConstant.VERIFY.PASS) {
					log.error(one+" is PASS when "+customList+" size equal="+studentIds);
					return ResultCode.ERRORCODE.RECHARGE_AREADY_VERIFY;
				}
				//检查套餐有效性
				if(one.getRcid()==null ){
					log.error(one+" is has none plan when "+customList);
					return ResultCode.ERRORCODE.RECHARGE_NEED_PLAN;
				}
				RechargePlanVo plan=plans.get(one.getRcid());
				if(plan==null) {
					plan=rechargePlanService.queryRechargePlanById(one.getRcid());
					plans.put(one.getRcid(), plan);
				}
				if(plan==null || plan.getActive()!=OrderConstant.ACTIVE.ACTIVE|| plan.getVstate()!=OrderConstant.VERIFY.PASS || plan.getIsdel()!=OrderConstant.ISDEL.NOTDELETE){
					log.error(one+" is has not correct plan when "+customList);
					return ResultCode.ERRORCODE.RECHARGE_NEED_PLAN;
				}
				List<VipCustomVo> custom=planMap.get(one.getRcid());
				if(custom==null){
					custom=new ArrayList<VipCustomVo>();
					planMap.put(one.getRcid(), custom);
				}
				custom.add(one);
				Set<Long> user=userMap.get(one.getRcid());
				if(user==null){
					user=new HashSet<Long>();
					userMap.put(one.getRcid(), user);
				}
				user.add(one.getStudentId());
				//审核通过，修改学员大客户标志
				if (plan!=null && plan.getVtype() == RechargeConstant.VIPTYPE.VIPBIG) {
					Student s = studentManager.getStudentInfo(one.getStudentId());
					s.setVipId(one.getCoid());
					s.setVipPackageId(String.valueOf(one.getRcid()));
					studentManager.updateStudent(s);
				}
			}
			
			//更新审核状态
			BeanCopy.setListField(customList, "vstate", OrderConstant.VERIFY.PASS);
			vipCustomService.saveVipCustomList(customList);
			
			//一方案一推送
			//发送消息，必须区分有优惠券与无优惠券
			Iterator<RechargePlanVo> it=plans.values().iterator();
			while(it.hasNext()){
				RechargePlanVo plan=it.next();
				List<VipCustomVo> customs=planMap.get(plan.getRcid());
				//1. 有优惠券：则发送优惠券
				//目前扫码发券直接在新增时发送，因此如果已经发券，则不再发送，但是客户新增的还是在这里发
				if(StringUtil.isNotNullAndNotEmpty(plan.getCouponTemplate())){
					String [] templateId=plan.getCouponTemplate().split(",");
					String [] number=plan.getCouponNumber().split(",");
					for(int c=0;c<templateId.length;c++){
						int no=Integer.valueOf(number[c]);
						String id=templateId[c];
						for (int k=0;k<no;k++) {
							boolean jpush=false;
							//一个学员发券只推送一次
							if((c==templateId.length-1) && (k==no-1)) {
								jpush=true;
							}
							for (int l=0;l<customs.size();l++){
								VipCustomVo custom=customs.get(l);
								//目前扫码发券直接在新增时发送，因此如果已经发券，则不再发送，，但是客户新增的还是在这里发
								if(custom.getCoupon()==null) {
								ReqResult result=couponService.genStudentCouponAndNotify(id, custom.getStudentId(),jpush);
								if("0".equals(result.getResult().get(ResultCode.RESULTKEY.CODE))){
									String coupon=custom.getCoupon();
									if(StringUtil.isNullOrEmpty(coupon)){
										coupon=String.valueOf(result.getResult().get("couponId"));
									} else {
										coupon+=","+String.valueOf(result.getResult().get("couponId"));
									}
									custom.setCoupon(coupon);
								} else {
									String couponLack=custom.getCouponLack();
									if(StringUtil.isNullOrEmpty(couponLack)){
										couponLack=id;
									} else {
										couponLack+=","+id;
									}
									custom.setCouponLack(couponLack);
									}
								}
							}
						}
					}
					//保存发送的优惠券
					vipCustomService.saveVipCustomList(customs);
				} 
				//2. 如有通知，则发送通知
				if(StringUtil.isNotNullAndNotEmpty(plan.getJpush())){
					JpushMsg jmsgStudent=new JpushMsg();
					jmsgStudent.setAlter(plan.getJpush());
					jmsgStudent.setUserIds(userMap.get(plan.getRcid()));
					jmsgStudent.setOperate(JpushConstant.OPERATE.VIPCUSTOM_VERIFY_PASS);
					jmsgStudent.getExtras().put("RECHARGEPLANT",String.valueOf(plan.getRcid()));

					Message jpush = new Message();
					jpush.setKeys("passCustom"+userMap.get(plan.getRcid()));
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
					jpush.setBody(SerializableUtil.serialize(jmsgStudent));
					jpushProducer.send(jpush);
				}
				//2. 如有短信，则发送短信
				if(StringUtil.isNotNullAndNotEmpty(plan.getEms())){
					Map<String,String> shortMsgs = new HashMap<String, String>();
					for (int j=0;j<customs.size();j++){
						authcodeService.sendMsg(Integer.valueOf(plan.getEms()), customs.get(j).getMobile(), shortMsgs);
					}
				}
			}
			return ResultCode.ERRORCODE.SUCCESS;
	}
	/**
	 * 审核大客户员工不通过
	 */
	public String refuseCustom(List<Long> studentIds,String reason,long muid){
		try {
			//参数判断
			if(studentIds==null || studentIds.isEmpty()|| reason==null) {
				log.error(
						studentIds + " refuse  with reason="+reason
								+" param error.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			List<VipCustomVo> customList=vipCustomService.queryVipCustomByIds(studentIds, new VipCustomQuery());
			//记录数量不匹配
			if(customList==null || customList.size()<studentIds.size()){
				log.error(customList+" size not equal="+studentIds);
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			//判断记录有效性
			for(int i=0;i<customList.size();i++){
				VipCustomVo one=customList.get(i);
				//防止对作废的记录进行审核
				if(one.getIsdel()!=OrderConstant.ISDEL.NOTDELETE) {
					log.error(one+" is delete when "+customList+" size equal="+studentIds);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//防止重复审核
				if(one.getVstate()!=OrderConstant.VERIFY.WAIT) {
					log.error(one+" is NOT WAIT when "+customList+" size equal="+studentIds);
					return ResultCode.ERRORCODE.RECHARGE_AREADY_VERIFY;
				}
				one.setMtime(null);
				one.setMuid(muid);
				one.setVstate(OrderConstant.VERIFY.REFUSE);
				one.setReason(reason);
			}
			vipCustomService.saveVipCustomList(customList);
			return ResultCode.ERRORCODE.SUCCESS;
		} catch(Exception e){
			log.error(
					studentIds + " refuse with reason="+reason
							+" Exception="+ e.getMessage(), e);
			return ResultCode.ERRORCODE.EXCEPTION;
		}
	}
	/**
	 * 查看充值送套餐详情
	 */
	public RechargePlanVo getRechargePlan(Integer rcid) throws Exception {
		RechargePlanVo plan = rechargePlanService.queryRechargePlanById(rcid);
		RechargeGearVo search=new RechargeGearVo();
		search.setRcid(plan.getRcid());
		search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
		search.setVstate(OrderConstant.VERIFY.PASS);
		RechargeGearQuery query=new RechargeGearQuery();
		query.setorderBy(" order by pmax desc ");
		List<RechargeGearVo> list=rechargeGearService.queryByObjectAnd(search,query);
		
		RechargeGearVo rechargeGearVo = null;
		List<CouponList> couponList = null;
		CouponList couponListInfo = null;
		for (int i =0; i < list.size(); i++) {
			rechargeGearVo = list.get(i);
			couponList = new ArrayList<CouponList>();
			if (rechargeGearVo.getCouponId() != null && !"".equals(rechargeGearVo.getCouponId())) {
				String[] couponId = rechargeGearVo.getCouponId().split("\\|");
				String[] couponName = rechargeGearVo.getCouponName().split("\\|");
				String[] couponNum = rechargeGearVo.getCouponNum().split("\\|");
				if (couponName.length == couponNum.length && couponId.length == couponNum.length) { //优惠券整体大小必须一致
					for (int j = 0; j < couponName.length; j ++) {
						couponListInfo = new CouponList();
						Coupon  coupon = couponService.getCouponById(couponId[j]);
						if (coupon != null) {
							couponListInfo.setDiscount(coupon.getDiscount());
							couponListInfo.setMoneyValue(coupon.getMoneyvalue());
						}
						if (couponName[j] != null) {
							couponListInfo.setCouponName(couponName[j]);
						}
						if (couponNum[j] != null) {
							couponListInfo.setCouponNum(couponNum[j]);
						}
						if (couponId[j] != null) {
							couponListInfo.setCouponId(couponId[j]);
						}
						couponList.add(couponListInfo);
					}
				}
				
				rechargeGearVo.setCouponList(couponList);
				list.set(i, rechargeGearVo);
			}
		}
		
		if (plan != null && plan.getCouponTemplate() != null && !"".equals(plan.getCouponTemplate())) { //增加报名优惠券详情
			Coupon coupon = couponService.getCouponById(plan.getCouponTemplate());
			plan.setName(coupon.getName());
			Date expireTime = null;
            if (coupon.getValidityperiod() == -1) {
                  //有效期为-1时表示永久有效
                  expireTime = TimeUtil.addDate(new Date(), 24 * 3600000 * 365 * 1000l);
            } else if (coupon.getValidityperiod() == 0) {
                  //使用优惠券的过期时间
                  if (null != coupon.getExpireTime()) {
                      expireTime = coupon.getExpireTime();
                  } else {
                      expireTime = new Date();
                  }
            } else {
                  //计算过期时间
                  expireTime = TimeUtil.addDate(new Date(), coupon.getValidityperiod() * 3600000l);
                  if (null != coupon.getExpireTime() && coupon.getExpireTime().getTime() < expireTime.getTime()) {
                      //当前优惠券有截止时间时,所发的优惠券过期时间不能超过截止时间
                      expireTime = coupon.getExpireTime();
                  }
            }
            plan.setExpiretime(expireTime);
            plan.setMoneyvalue(coupon.getMoneyvalue());
            plan.setDiscount(coupon.getDiscount());
				
		}
		
		plan.setRechargeGearList(list);
		return plan;
	}
	/**
	 * 添加套餐
	 * @param plan
	 * @return
	 */
	public String addRechargePlan(RechargePlanVo plan,long cuid) throws Exception {
			Date now=new Date();
			//1.参数判断
			if(plan==null||StringUtil.isNullOrEmpty(plan.getRcname())){
				log.error(
						plan + " has empty plan param error.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			List<RechargeGearVo> gear=plan.getRechargeGearList();
			if(gear==null||gear.isEmpty()){
				log.error(
						plan + " has empty gear param error.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			List<RechargePlanVo> e=rechargePlanService.queryByRcname(plan.getRcname(), new RechargePlanQuery());
			if(e!=null && !e.isEmpty()){
				log.error(
						plan + " has exist same name="+e);
				return ResultCode.ERRORCODE.RECHARGE_PLAN_EXIST;
			}
			//2.gear判断与初始化
			for(int i=0;i<gear.size();i++){
				RechargeGearVo one=gear.get(i);
				if (one.getPmax() == null || one.getPmin() == null
						|| one.getPmax() < one.getPmin()
						|| (one.getPercent() == null && one.getMoney() == null && one.getCouponId() == null)
						|| (one.getPercent() != null && one.getPercent() < 0)
						|| (one.getMoney() != null && one.getMoney() < 0)) {
					log.error(one+" gear is not incorrect on="+plan);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				if (one.getPmin() < 0 || one.getPmax() > 1000000) {
					log.error(one+" gear is not beyong on="+plan);
					return ResultCode.ERRORCODE.RECHARGE_GEAR_BEYOND;
				}
				one.setCtime(now);
				one.setCuid(plan.getCuid());
				one.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				one.setVstate(OrderConstant.VERIFY.PASS);
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				one.setMtime(formatter.format(new Date()));
			}
			plan.setCuid(cuid);
			//2.默认设置
			plan.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			plan.setVstate(OrderConstant.VERIFY.PASS);
			plan.setActive(OrderConstant.ACTIVE.INACTIVE);
			plan.setCtime(now);
			//目前默认都是大客户方案，后续可能有变
//			plan.setVtype(RechargeConstant.VIPTYPE.VIPBIG);
			
			//3.生成主键
			Integer pk=null;
			for (int i=0;i<10;i++){
				pk=getPlanPk(plan.getCtime());
				if(pk!=null){
					break;
				}
			}
			plan.setRcid(pk);
			rechargePlanService.addRechargePlan(plan);
			BeanCopy.setListField(gear, "rcid", pk);
			rechargeGearService.addRechargeGearList(gear);
			return ResultCode.ERRORCODE.SUCCESS;
	}
	public Integer getPlanPk(Date ctime){
		String key=TimeUtil.getDateFormat(ctime, "YYMMdd");
		RLock lock=null;
		Integer pk=null;
		try {
			//主键必须全局唯一.
			lock=redissonClient.getLock("rdlock_rechargeplanpk."+key);
			//1s等待，10s超时，防止死锁
			boolean hasLock=lock.tryLock(1,10,TimeUnit.SECONDS);
			//锁获取成功，则往下走
			if(hasLock) {
				pk=redisUtil.get("rechargeplanpk."+key);
				if(pk==null||pk==0){
					pk=Integer.parseInt(key+"000");
				}
				pk=pk+1;
				redisUtil.set("rechargeplanpk."+key, pk, 86400);
			//锁获取失败，返回错误
			} else {
				log.error(key+",getPlanPK  Lock ERROR.");
			}
		} catch (Exception e) {
			log.error(key+" getPlanPK  Lock Exception="+e.getMessage(),e);
		//最终释放锁
		} finally {
			if(lock!=null) {
				try {
					lock.unlock();
				} catch(Exception e){}
			}
		} 
		return pk;
	}
	/**
	 * 激活充送方案
	 */
	public String activeRechargePlan(int rcid,long muid) throws Exception{
			RechargePlanVo plan=rechargePlanService.queryRechargePlanById(rcid);
			if(plan==null || plan.getIsdel()!=OrderConstant.ISDEL.NOTDELETE||plan.getVstate()!=OrderConstant.VERIFY.PASS||plan.getActive()==OrderConstant.ACTIVE.ACTIVE){
				log.error(rcid+" is not incorrect="+plan);
				return ResultCode.ERRORCODE.PARAMERROR; 
			}
			//如果是散户方案，那么需要屏蔽其他方案为非优先方案
			/*if(plan.getVtype()==RechargeConstant.VIPTYPE.VIPRETAIL){
				RechargePlanVo search=new RechargePlanVo();
				search.setActive(OrderConstant.ACTIVE.ACTIVE);
				search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				search.setVstate(OrderConstant.VERIFY.PASS);
				search.setVtype(RechargeConstant.VIPTYPE.VIPRETAIL);
				search.setCommon(RechargeConstant.COMMON.FIRST);
				List<RechargePlanVo> all=rechargePlanService.queryByObjectAnd(search, new RechargePlanQuery());
				if(all!=null && !all.isEmpty()) {
					log.info(plan+" will active, so set other to second="+all);
					BeanCopy.setListField(all, "common", RechargeConstant.COMMON.SECOND);
					BeanCopy.setListField(all, "mtime", null);
					rechargePlanService.saveRechargePlanList(all);
				}
			}*/
			//自身设置为优先方案
			plan.setActive(OrderConstant.ACTIVE.ACTIVE);
			plan.setCommon(RechargeConstant.COMMON.FIRST);
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			plan.setMtime(formatter.format(new Date()));
			plan.setMuid(muid);
			rechargePlanService.saveRechargePlan(plan);
			return ResultCode.ERRORCODE.SUCCESS;
	}
	/**
	 * 停用充送方案
	 */
	public String inactiveRechargePlan(int rcid,long muid){
		try {
			RechargePlanVo plan=rechargePlanService.queryRechargePlanById(rcid);
			if(plan==null || plan.getIsdel()!=OrderConstant.ISDEL.NOTDELETE||plan.getVstate()!=OrderConstant.VERIFY.PASS||plan.getActive()!=OrderConstant.ACTIVE.ACTIVE){
				log.error(rcid+" is not incorrect="+plan);
				return ResultCode.ERRORCODE.PARAMERROR; 
			}
			plan.setActive(OrderConstant.ACTIVE.INACTIVE);
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			plan.setMtime(formatter.format(new Date()));
			plan.setMuid(muid);
			rechargePlanService.saveRechargePlan(plan);
			return ResultCode.ERRORCODE.SUCCESS;
		} catch(Exception e){
			log.error(
					rcid + " acticeRechargePlan Exception="+ e.getMessage(), e);
			return ResultCode.ERRORCODE.EXCEPTION;
		}
	}
	/**
	 * 停用充送方案
	 */
	public String inactiveRechargePlan(int rcid,Date tend,long muid){
		try {
			RechargePlanVo plan=rechargePlanService.queryRechargePlanById(rcid);
			if(plan==null || plan.getIsdel()!=OrderConstant.ISDEL.NOTDELETE||plan.getVstate()!=OrderConstant.VERIFY.PASS||plan.getActive()!=OrderConstant.ACTIVE.ACTIVE){
				log.error(rcid+" is not incorrect="+plan);
				return ResultCode.ERRORCODE.PARAMERROR; 
			}
			plan.setTend(tend);
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			plan.setMtime(formatter.format(new Date()));
			plan.setMuid(muid);
			rechargePlanService.updateByRcid(plan, plan.getRcid());
			return ResultCode.ERRORCODE.SUCCESS;
		} catch(Exception e){
			log.error(
					rcid + " acticeRechargePlan Exception="+ e.getMessage(), e);
			return ResultCode.ERRORCODE.EXCEPTION;
		}
	}
	/**
	 * 通用修改方案
	 * @param plan
	 * @return
	 */
	public String modifyRechargePlan(RechargePlanVo plan,long muid) throws Exception{
			//1.参数判断
			if(plan==null||plan.getRcid()==null){
				log.error(
						plan + " has empty rcid param error.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			RechargePlanVo old=rechargePlanService.queryRechargePlanById(plan.getRcid());
			if(old==null||old.getIsdel()!=OrderConstant.ISDEL.NOTDELETE){
				log.error(plan + " has not exists error.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			//2.已启用可以修改名称和日期
			if(old.getActive()==OrderConstant.ACTIVE.ACTIVE){
				if(plan.getRcname()!=null){
					old.setRcname(plan.getRcname());
				}
				old.setTend(plan.getTend());
				old.setTstart(plan.getTstart());
				plan.setMuid(muid);
			} else {
				plan.setMtime(null);
				plan.setMuid(muid);
				//3.清空不可修改项
				plan.setCtime(null);
				plan.setCuid(null);
				plan.setIsdel(null);
				plan.setMaxTimes(null);
				plan.setReason(null);
				plan.setVtype(null);
				//4.检查是否更新了gear
				List<RechargeGearVo> gears=plan.getRechargeGearList();
				//删除旧选项
				if(gears!=null&& !gears.isEmpty()){
					for(int i=0;i<gears.size();i++){
						RechargeGearVo one=gears.get(i);
						if (one.getPmax() == null || one.getPmin() == null
								|| one.getPmax() < one.getPmin()
								|| (one.getPercent() == null && one.getMoney() == null && one.getCouponId() == null)
								|| (one.getPercent() != null && one.getPercent() < 0)
								|| (one.getMoney() != null && one.getMoney() < 0)) {
							log.error(one+" gear is not incorrect on="+plan);
							return ResultCode.ERRORCODE.PARAMERROR;
						}
						if(one.getPmin()<0 || one.getPmax()>1000000){
							log.error(one+" gear is not beyong on="+plan);
							return ResultCode.ERRORCODE.RECHARGE_GEAR_BEYOND;
						}
						one.setCtime(new Date());
						one.setCuid(plan.getCuid());
						one.setIsdel(OrderConstant.ISDEL.NOTDELETE);
						one.setVstate(OrderConstant.VERIFY.PASS);
						one.setRcid(old.getRcid());
						SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						one.setMtime(formatter.format(new Date()));
					}
					RechargeGearVo search=new RechargeGearVo();
					search.setRcid(plan.getRcid());
					search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
					RechargeGearQuery query=new RechargeGearQuery();
					List<RechargeGearVo> list=rechargeGearService.queryByObjectAnd(search,query);
					//目前有效档位不为空
					if(list!=null && !list.isEmpty()) {
						BeanCopy.setListField(list, "isdel", OrderConstant.ISDEL.DELETE);
						rechargeGearService.saveRechargeGearList(list);
					}
				}
				//变更方案
				BeanCopy.copyNotNull(plan, old);
				//日期可以为空
				old.setTstart(plan.getTstart());
				old.setTend(plan.getTend());
				//增加新gear
				rechargeGearService.saveRechargeGearList(gears);
			}
			//修改后需要需要重新激活,暂免
//			plan.setActive(OrderConstant.ACTIVE.INACTIVE);
			BeanCopy.copyNotNull(plan, old);
			rechargePlanService.saveRechargePlan(old);
			return ResultCode.ERRORCODE.SUCCESS;
	}
	/**
	 * 返回优惠充值记录
	 * @param days
	 * @param date
	 * @param rcid
	 * @param mobile
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<RechargeRecordVo> getRechargeRecord(Integer days,Date dstart,Date dend,Integer rcid,String mobile,String rcname,String company,Integer vtype,Integer vstate,int pageIndex,int pageSize,boolean manual,Integer utype) throws Exception {
		String start=null;
		String end=null;
		if(days!=null && days>0){
			Date now=new Date();
			end=TimeUtil.getDateFormat(now);
			start=TimeUtil.getDateFormat(DateUtil.dateAfterMinute(now, -60*24*days));
		}
		if(dstart!=null && dend !=null){
			start=TimeUtil.getDateFormat(DateUtil.getDateStart(dstart));
			end=TimeUtil.getDateFormat(DateUtil.getDateEnd(dend));
		} else if (dstart!=null){
			start=TimeUtil.getDateFormat(DateUtil.getDateStart(dstart));
			end=TimeUtil.getDateFormat(DateUtil.getDateEnd(dstart));
		}
		RechargeRecordVo search=new RechargeRecordVo();
		search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
		search.setRcid(rcid);
		search.setMobile(mobile);
		search.setVtype(vtype);
		search.setVstate(vstate);
		search.setUtype(utype);
		RechargeRecordQuery query=new RechargeRecordQuery();
		String post=" ";
		if(start!=null && end !=null) {
			post+=" and pay_time between '"+start+"' and '"+end+"'";
		}
		if(manual){
			post +=" and rcid=0 ";
		} else {
			post +=" and rcid>0 ";
		}
		if(StringUtil.isNotNullAndNotEmpty(company)) {
			post +=" and company like '%"+company+"%'";
		}
		if(StringUtil.isNotNullAndNotEmpty(rcname)){
			post +=" and rcname like '%"+rcname+"%'";
		}
		
		query.setGroupBy(post);
		query.setorderBy(" order by ctime desc ");
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		return rechargeRecordService.queryByObjectAnd(search, query);
	}
	
	/**
	 * 返回充值送方案详情
	 */
	@Override
	public ReqResult  getRechargePlanByRcid(Integer rcid,String rcname,String company,Integer vtype,Integer vstate) throws Exception {
		ReqResult reqResult = new ReqResult();
		try {
			RechargePlanVo rechargePlanVo = new RechargePlanVo();
			rechargePlanVo.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			rechargePlanVo.setRcid(rcid);
			rechargePlanVo.setVtype(vtype);
			rechargePlanVo.setVstate(vstate);
			
			RechargePlanQuery rechargePlanQuery = new RechargePlanQuery();
			String post=" ";
			if(StringUtil.isNotNullAndNotEmpty(company)) {
				post +=" and company like '%"+company+"%'";
			}
			if(StringUtil.isNotNullAndNotEmpty(rcname)){
				post +=" and rcname like '%"+rcname+"%'";
			}
			rechargePlanQuery.setGroupBy(post);
			rechargePlanQuery.setorderBy(" order by ctime desc ");
			List<RechargePlanVo> rechargePlanVoList = rechargePlanService.queryByNew0(rechargePlanVo, rechargePlanQuery);
			reqResult.setData(rechargePlanVoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reqResult;
	}
	/**
	 * 计算优惠充值分页
	 * @param days
	 * @param date
	 * @param rcid
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public Page<RechargeRecordVo> countRechargeRecord(Integer days,Date dstart,Date dend,Integer rcid,String mobile,String rcname,String company,Integer vtype,Integer vstate,int pageSize,boolean manual,Integer utype) throws Exception {
		String start=null;
		String end=null;
		if(days!=null && days>0){
			Date now=new Date();
			end=TimeUtil.getDateFormat(now);
			start=TimeUtil.getDateFormat(DateUtil.dateAfterMinute(now, -60*24*days));
		}
		if(dstart!=null && dend !=null){
			start=TimeUtil.getDateFormat(DateUtil.getDateStart(dstart));
			end=TimeUtil.getDateFormat(DateUtil.getDateEnd(dend));
		} else if (dstart!=null){
			start=TimeUtil.getDateFormat(DateUtil.getDateStart(dstart));
			end=TimeUtil.getDateFormat(DateUtil.getDateEnd(dstart));
		}
		RechargeRecordVo search=new RechargeRecordVo();
		search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
		search.setRcid(rcid);
		search.setMobile(mobile);
		search.setVtype(vtype);
		search.setVstate(vstate);
		search.setUtype(utype);
		RechargeRecordQuery query=new RechargeRecordQuery();
		String post=" ";
		if(start!=null && end !=null) {
			post+=" and pay_time between '"+start+"' and '"+end+"'";
		}
		if(manual){
			post +=" and rcid=0 ";
		} else {
			post +=" and rcid>0 ";
		}
		if(StringUtil.isNotNullAndNotEmpty(company)) {
			post +=" and company like '%"+company+"%'";
		}
		if(StringUtil.isNotNullAndNotEmpty(rcname)){
			post +=" and rcname like '%"+rcname+"%'";
		}
		query.setGroupBy(post);
		int total=rechargeRecordService.countByObject(search, query);
		Page<RechargeRecordVo> page=new Page<RechargeRecordVo>();
		page.setTotal(total);
		page.setPageSize(pageSize);
		page.setPages((total+pageSize-1)/pageSize);
		return page;
	}
	/**
	 * 审核通过充值送记录
	 * @param rrids
	 * @return
	 * @throws Exception
	 */
	public String passRechargeRecord(List<Integer> rrids,long muid) throws Exception {
		RLock lock=null;
		try {
			//任何时候，只能有一人操作
			lock=redissonClient.getLock("rdlock_recharge.passRechargeRecord");
			//1s等待，10s超时，防止死锁
			boolean hasLock=lock.tryLock(1,10,TimeUnit.SECONDS);
			//锁获取成功，则往下走
			if(hasLock) {
				//1.参数校验
				if(rrids==null || rrids.isEmpty()){
					log.error(rrids+" is empty so cannot pass.");
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//2.有效性校验在资金中非常重要
				RechargeRecordQuery query=new RechargeRecordQuery();
				List<RechargeRecordVo> list=rechargeRecordService.queryRechargeRecordByIds(rrids, query);
				//记录数量不匹配
				if(list==null || list.size()<rrids.size()){
					log.error(list+" size not equal="+rrids);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//判断记录有效性
				for(int i=0;i<list.size();i++){
					RechargeRecordVo one=list.get(i);
					//防止对作废的记录进行送钱
					if(one.getIsdel()!=OrderConstant.ISDEL.NOTDELETE) {
						log.error(one+" is delete when "+list+" size equal="+rrids);
						return ResultCode.ERRORCODE.PARAMERROR;
					}
					//防止重复审核送钱
					if(one.getVstate()==OrderConstant.VERIFY.PASS) {
						log.error(one+" is PASS when "+list+" size equal="+rrids);
						return ResultCode.ERRORCODE.RECHARGE_AREADY_VERIFY;
					}
					//防止赠送金额为负数
					if(one.getRecharge()==null || one.getRecharge()<0) {
						log.error(one+" is not correct recharge when "+list+" size equal="+rrids);
						return ResultCode.ERRORCODE.PARAMERROR;
					}
					//防止手工调整混入
					if(one.getRcid()==0){
						log.error(one+" is a manual recharge when "+list+" size equal="+rrids);
						return ResultCode.ERRORCODE.PARAMERROR;
					}
					SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					one.setMtime(formatter.format(new Date()));
					one.setMuid(muid);
				}
				//获取充送方案
				List<Integer> planIds=BeanCopy.getFieldList(list, "rcid");
				List<RechargePlanVo> plans=rechargePlanService.queryRechargePlanByIds(planIds, new RechargePlanQuery());
				Map<Integer,RechargePlanVo> planMap=new HashMap<Integer,RechargePlanVo>();
				for(int i=0;i<plans.size();i++){
					planMap.put(plans.get(i).getRcid(), plans.get(i));
				}
				//4.学员正式送金额：
				//5.充送成功，记录到账时间
				/**
				 *   预计调用资金结算接口进行充值送
				 *   暂时先用支付接口替代
				 */
				String resultCode = null;
				for(int i=0;i<list.size();i++){
					RechargeRecordVo one=list.get(i);
					RechargePlanVo plan=planMap.get(one.getRcid());
					one.setMuid(muid);
					
					SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					one.setMtime(formatter.format(new Date()));
					//20160910增加送钱还是送券判断
					resultCode = rechargeInternal(one, plan,"赠送金额");	
				}
				if(!resultCode.equals(ResultCode.ERRORCODE.SUCCESS)){
					return resultCode;
				}
				//3. 记录数据开启充送
				BeanCopy.setListField(list, "vstate", OrderConstant.VERIFY.PASS);
				rechargeRecordService.saveRechargeRecordList(list);
				return ResultCode.ERRORCODE.SUCCESS;
			} else {
				log.error(rrids+" pass failed while lock not get."+muid);
				return ResultCode.ERRORCODE.FAILED;
			}
		//最终释放锁
		} finally {
			if(lock!=null) {
				try {
						lock.unlock();
				} catch(Exception e){}
			}
		} 
	}
	
	private String rechargeInternal(RechargeRecordVo record,RechargePlanVo plan,String display) throws Exception{
		Date now=new Date();
		String resultCode = null;
		//送券
		if (record.getCouponId() != null) {
			String[] couponTmpIdList = record.getCouponId().split("\\|");//优惠券集合
			String[] couponNumList = record.getCouponNum().split("\\|");//优惠券数量集合
			if (couponTmpIdList.length != couponNumList.length ) {
				log.error("****************************************** couponTmpIdList length not equal couponNumList length");
			}
			else {
				if (couponTmpIdList.length > 0) {
					for (int i = 0; i < couponTmpIdList.length; i++) {
						resultCode = couponService.handleCouponTmp(couponTmpIdList[i], couponNumList[i], record.getStudentId());
					}
				}
			}
		}
		if(resultCode != null && !resultCode.equals(ResultCode.ERRORCODE.SUCCESS)){
			return resultCode;
		}
		//学员正式送金额
		if (record.getRecharge() != null) { //20160910增加区分：送钱 或送钱送券一起，先送钱
			PayVo payVo=new PayVo(record.getStudentId(), OrderConstant.USETYPE.STUDENT, record.getRecharge(), PayWayType.SYSTEMPAY, record.getWaterId(), PurposeType.RECHARGE.getType(),0, 0,display);
			payVo.setDiscountMoney(record.getRecharge());
			moneyManager.handleAdjustStudent(payVo);
			record.setGetTime(now);
			record.setVstate(OrderConstant.VERIFY.PASS);
			//充送成功，记录到账时间
			rechargeRecordService.saveRechargeRecord(record);
		}
		
		//推送消息
		if(plan!=null &&StringUtil.isNotNullAndNotEmpty(plan.getRejpush())) {
			String content=plan.getRejpush();
			JpushMsg jmsgStudent=new JpushMsg();
			jmsgStudent.setAlter(content);
			jmsgStudent.addUser(record.getStudentId());
			jmsgStudent.setOperate(JpushConstant.OPERATE.RECHARGERECORD_VERIFY_PASS);
			jmsgStudent.getExtras().put("RECHARGEPLANT",String.valueOf(plan.getRcid()));
			jmsgStudent.getExtras().put("RECHARGERECORD",String.valueOf(record.getRrid()));
			Message jpush = new Message();
			jpush.setKeys("passRechargeRecord"+record.getStudentId());
			jpush.setTopic(jpushProducer.getCreateTopicKey());
			jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
			jpush.setBody(SerializableUtil.serialize(jmsgStudent));
			jpushProducer.send(jpush);
		}
		//发送短信
		if(plan!=null && StringUtil.isNotNullAndNotEmpty(plan.getReems())){
			String ems=plan.getReems();
			Map<String,String> shortMsgs = new HashMap<String, String>();
			authcodeService.sendMsg(Integer.valueOf(ems), record.getMobile(), shortMsgs);
		}
		if (resultCode != null) {
			return resultCode;
		}
		return ResultCode.ERRORCODE.SUCCESS;
	}
	/*
	 * 财务审核教练通过
	 */
	private String rechargeInternalCoach(RechargeRecordVo record,RechargePlanVo plan,String display) throws Exception{
		Date now=new Date();
		//学员正式送金额
		PayVo payVo=new PayVo(record.getStudentId(), OrderConstant.USETYPE.COACH, record.getRecharge(), PayWayType.SYSTEMPAY, record.getWaterId(), PurposeType.RECHARGE.getType(),0, 0,display);
		payVo.setDiscountMoney(record.getRecharge());
		moneyManager.handleAdjustCoach(payVo);
		record.setGetTime(now);
		record.setVstate(OrderConstant.VERIFY.PASS);
		//充送成功，记录到账时间
		rechargeRecordService.saveRechargeRecord(record);
		return ResultCode.ERRORCODE.SUCCESS;
	}
	/**
	 * 审核拒绝充值送记录
	 * @param rrids
	 * @param reason
	 * @return
	 * @throws Exception
	 */
	public String refuseRechargeRecord(List<Integer> rrids,String reason,long muid) throws Exception{
				//1.参数校验
				if(rrids==null || rrids.isEmpty()|| StringUtil.isNullOrEmpty(reason)){
					log.error(rrids+" is empty so cannot pass with reason="+reason);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//2.有效性校验在资金中非常重要
				RechargeRecordQuery query=new RechargeRecordQuery();
				List<RechargeRecordVo> list=rechargeRecordService.queryRechargeRecordByIds(rrids, query);
				//记录数量不匹配
				if(list==null || list.size()<rrids.size()){
					log.error(list+" refuse size not equal="+rrids);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//判断记录有效性
				for(int i=0;i<list.size();i++){
					RechargeRecordVo one=list.get(i);
					//防止对作废的记录进行拒绝
					if(one.getIsdel()!=OrderConstant.ISDEL.NOTDELETE) {
						log.error(one+" is delete when REFUSE "+list+" size equal="+rrids);
						return ResultCode.ERRORCODE.PARAMERROR;
					}
					//防止重复拒绝审核
					if(one.getVstate()!=OrderConstant.VERIFY.WAIT) {
						log.error(one+" is NOT WAIT when refuse "+list+" size equal="+rrids);
						return ResultCode.ERRORCODE.RECHARGE_AREADY_VERIFY;
					}
					SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					one.setMtime(formatter.format(new Date()));
					one.setMuid(muid);
					one.setVstate(OrderConstant.VERIFY.REFUSE);
					one.setReason(reason);
				}
				rechargeRecordService.saveRechargeRecordList(list);
				return ResultCode.ERRORCODE.SUCCESS;
	}
	
	/**
	 * 获取充送方案列表
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public List<RechargePlanVo> getRechargePlanList(RechargePlanVo search,int pageSize,int pageIndex) throws Exception {
		RechargePlanQuery query=new RechargePlanQuery();
		if(search==null){
			search=new RechargePlanVo();
		}
		search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setorderBy(" order by ctime desc ");
		return rechargePlanService.queryByObjectAnd(search, query);
	}
	/**
	 * 获取充送方案分页
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public Page<RechargePlanVo> countRechargePlanList(RechargePlanVo search,int pageSize) throws Exception {
		RechargePlanQuery query=new RechargePlanQuery();
		if(search==null){
			search=new RechargePlanVo();
		}
		search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
		int total=rechargePlanService.countByObject(search, query);
		Page<RechargePlanVo> page=new Page<RechargePlanVo>();
		page.setTotal(total);
		page.setPageSize(pageSize);
		page.setPages((total+pageSize-1)/pageSize);
		return page;
	}
	/**
	 * 激活大客户公司
	 * @param coid
	 * @return
	 */
	public String activeVipCompany(List<Integer> coids,long muid) throws Exception {
			VipCompanyQuery query=new VipCompanyQuery();
			query.setorderBy("order by ctime desc");
			List<VipCompanyVo> list=vipCompanyService.queryVipCompanyByIds(coids,query);
			//记录数量不匹配
			if(list==null || list.size()<coids.size()){
				log.error(list+" size not equal="+coids);
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			//判断记录有效性
			for(int i=0;i<list.size();i++){
				VipCompanyVo one=list.get(i);
				//防止对作废的记录进行激活 
				if(one.getIsdel()!=OrderConstant.ISDEL.NOTDELETE) {
					log.error(one+" is delete when "+list+" size equal="+coids);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//防止重复审核
				if(one.getVstate()!=OrderConstant.VERIFY.PASS) {
					log.error(one+" is NOT PASS when "+list+" size equal="+coids);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//防止对已激活的记录重复操作
				if(one.getActive()==OrderConstant.ACTIVE.ACTIVE) {
					log.error(one+" is aready active when "+list+" size equal="+coids);
					return ResultCode.ERRORCODE.RECHARGE_AREADY_VERIFY;
				}
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				one.setMtime(formatter.format(new Date()));
				one.setMuid(muid);
			}
			BeanCopy.setListField(list, "active", OrderConstant.ACTIVE.ACTIVE);
			vipCompanyService.saveVipCompanyList(list);
			return ResultCode.ERRORCODE.SUCCESS;
	}
	/**
	 * 停用大客户公司, 此时公司下已审核通过的员工如何处理？
	 * @param coid
	 * @return
	 */
	public String inactiveVipCompany(List<Integer> coids,long muid) {
		try {
			VipCompanyQuery query=new VipCompanyQuery();
			query.setorderBy("order by ctime desc");
			List<VipCompanyVo> list=vipCompanyService.queryVipCompanyByIds(coids,query);
			//记录数量不匹配
			if(list==null || list.size()<coids.size()){
				log.error(list+" size not equal="+coids);
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			//判断记录有效性
			for(int i=0;i<list.size();i++){
				VipCompanyVo one=list.get(i);
				//防止对作废的记录进行激活 
				if(one.getIsdel()!=OrderConstant.ISDEL.NOTDELETE) {
					log.error(one+" is delete when "+list+" size equal="+coids);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//防止重复审核
				if(one.getVstate()!=OrderConstant.VERIFY.PASS) {
					log.error(one+" is NOT PASS when "+list+" size equal="+coids);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//防止对已激活的记录重复操作
				if(one.getActive()==OrderConstant.ACTIVE.INACTIVE) {
					log.error(one+" is aready INactive when "+list+" size equal="+coids);
					return ResultCode.ERRORCODE.RECHARGE_AREADY_VERIFY;
				}
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				one.setMtime(formatter.format(new Date()));
				one.setMuid(muid);
				one.setActive(OrderConstant.ACTIVE.INACTIVE);
			}
			vipCompanyService.saveVipCompanyList(list);
			return ResultCode.ERRORCODE.SUCCESS;
		} catch(Exception e){
			log.error(coids+" inactive Exception:"+e.getMessage(),e);
			return ResultCode.ERRORCODE.EXCEPTION;
		}
	}
	/**
	 * 修改大客户信息，其中状态不能修改。
	 * @param vipCompany
	 * @return
	 */
	public String modifyVipCompany(VipCompanyVo vipCompany,long muid) throws Exception {
			//1.参数判断
			if(vipCompany.getCoid()==null) {
				log.error(vipCompany+" with not correct id.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			vipCompany.setMtime(null);
			vipCompany.setMuid(muid);
			//2.覆盖不可修改的部分
			vipCompany.setActive(null);
			vipCompany.setIsdel(null);
			vipCompany.setVstate(null);
			vipCompanyService.updateByCoid(vipCompany, vipCompany.getCoid());
			return ResultCode.ERRORCODE.SUCCESS;
	}
	
	/**
	 * APP：1. 当学员信息中vipPackageId不为空时，并且充值需要显示充送套餐情况，则获取该用户有效的充送套餐并显示。
	 *   2. 推送收到后也需要调用该接口,则如下处理：
	 *   APP接受推送310,然后用通过调用该接口拉取对应的有效充送方案详情,：
	 *    如果方案为空，则不显示任何页面
	 *    如果方案不为空，且其字段enroll为1，那么显示“立即报名”的弹窗。
	 *    如果方案不为空，且其字段enroll为0，那么显示“立即充值”的弹窗。
	 * 
	 * @param studentId
	 * @return
	 */
	public RechargePlanVo getRechargePlanByUserId(long studentId) throws Exception {
		RechargePlanVo plan = null;
		VipCustomVo custom=vipCustomService.queryVipCustomById(studentId);
		Student s=studentManager.getStudentInfo(studentId);
		EnrollOrder enroll=enrollOrderManager.getEnrollOrderByStudentId(studentId);
		//获取有效大客户方案
		if(custom!=null && custom.getIsdel() ==OrderConstant.ISDEL.NOTDELETE && custom.getVstate()==OrderConstant.VERIFY.PASS) {
			plan = rechargePlanService.queryRechargePlanById(custom.getRcid());
			s.setVstate(custom.getVstate());
			plan=reState(plan, s,enroll);
		}
		//无大客户方案，或者无有效大客户方案，采用普惠方案
		if(plan==null || plan.getVstate()!=OrderConstant.VERIFY.PASS) {
			plan=null;
			//非大客户，需要从方案中获取那个有效的普惠方案
			RechargePlanVo search=new RechargePlanVo();
			search.setActive(OrderConstant.ACTIVE.ACTIVE);
			search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			search.setVstate(OrderConstant.VERIFY.PASS);
			search.setVtype(RechargeConstant.VIPTYPE.VIPRETAIL);
			search.setCommon(RechargeConstant.COMMON.FIRST);
			RechargePlanQuery query=new RechargePlanQuery();
			query.setorderBy("order by mtime desc");
			List<RechargePlanVo> commons=rechargePlanService.queryByObjectAnd(search, query);
			if(commons!=null && !commons.isEmpty()){
				//已报名,则优先找到匹配城市的方案
				if(isRegister(s) && enroll!=null) {
					for(int i=0;i<commons.size();i++){
						String city=commons.get(i).getCityId();
						if(StringUtil.isNotNullAndNotEmpty(city) && city.indexOf(String.valueOf(enroll.getCityId()))>=0) {
							plan=commons.get(i);
							log.info(plan+" is plan most equal "+enroll);
							break;
						}
					}
				}
				//如果没有城市匹配方案，则取最新的那一个方案
				if(plan==null) {
					plan=commons.get(0);
				}
				log.info(studentId+" not a vip "+custom+", so use puhui="+plan);
			} else {
				log.error(s+" is a common,but has not plan "+studentId);
			}
		} 
		if(plan!=null) {
			s.setVstate(plan.getVstate());
		//没有充值方案的都认为是待审核状态
		} else {
			s.setVstate(OrderConstant.VERIFY.WAIT);
		}
		
		if(plan==null || plan.getActive()!=OrderConstant.ACTIVE.ACTIVE || plan.getIsdel()!=OrderConstant.ISDEL.NOTDELETE || plan.getVstate()!=OrderConstant.VERIFY.PASS){
			log.error(plan+" plan not valide with "+studentId);
			return null;
		}
		plan=reState(plan, s,enroll);
		if(custom!=null && custom.getCoid()!=null){
			VipCompanyVo company=vipCompanyService.queryVipCompanyById(custom.getCoid());
			if(company!=null){
				plan.setCompany(company.getCompany());
			}
		}
		RechargeGearVo search=new RechargeGearVo();
		search.setRcid(plan.getRcid());
		search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
		search.setVstate(OrderConstant.VERIFY.PASS);
		RechargeGearQuery query=new RechargeGearQuery();
		query.setorderBy(" order by pmax desc ");
		List<RechargeGearVo> list=rechargeGearService.queryByObjectAnd(search,query);
		
		//20161024 增加APP客户端显示优惠券详情
		RechargeGearVo rechargeGearVo = null;
		List<CouponList> couponList = null;
		CouponList couponListInfo = null;
		for (int i =0; i < list.size(); i++) {
			rechargeGearVo = list.get(i);
			couponList = new ArrayList<CouponList>();
			if (rechargeGearVo.getCouponId() != null && !"".equals(rechargeGearVo.getCouponId())) {
				String[] couponId = rechargeGearVo.getCouponId().split("\\|");
				String[] couponName = rechargeGearVo.getCouponName().split("\\|");
				String[] couponNum = rechargeGearVo.getCouponNum().split("\\|");
				if (couponName.length == couponNum.length && couponId.length == couponNum.length) { //优惠券整体大小必须一致
					for (int j = 0; j < couponName.length; j ++) {
						couponListInfo = new CouponList();
						Coupon  coupon = couponService.getCouponById(couponId[j]);
						if (coupon != null) {
							couponListInfo.setDiscount(coupon.getDiscount());
							couponListInfo.setMoneyValue(coupon.getMoneyvalue());
							couponListInfo.setType(coupon.getType().intValue());
							couponListInfo.setLimitValue(coupon.getLimitvalue());
						}
						if (couponName[j] != null) {
							couponListInfo.setCouponName(couponName[j]);
						}
						if (couponNum[j] != null) {
							couponListInfo.setCouponNum(couponNum[j]);
						}
						if (couponId[j] != null) {
							couponListInfo.setCouponId(couponId[j]);
						}
						couponList.add(couponListInfo);
					}
				}
				
				rechargeGearVo.setCouponList(couponList);
				list.set(i, rechargeGearVo);
			}
		}
		
		plan.setRechargeGearList(list);
		log.debug(studentId+" last plan is="+plan);
		return plan;
	}
	/**
	 * 学员充值金额成功后，计算赠送金额,并生成记录
	 * 对于自动赠送金额，则立马进行赠送，并更新记录到审核通过
	 * @param studentId
	 * @param charge
	 * @return
	 */
	public String recharge(RechargeRecordVo record) throws Exception{
		RLock lock=null;
		try {
			//主键必须全局唯一. 同一流水号只能充送一次
			lock=redissonClient.getLock("rdlock_rechargerecord."+record.getWaterId());
			//1s等待，10s超时，防止死锁
			boolean hasLock=lock.tryLock(1,10,TimeUnit.SECONDS);
			//锁获取成功，则往下走
			if(hasLock) {
				//0.参数判断
				if(record==null || record.getStudentId()<=0 || record.getCharge()<=0||StringUtil.isNullOrEmpty(record.getWaterId())){
					log.error(record+" with recharge parameter error.");
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//1.获取有效充送方案
				long studentId=record.getStudentId();
				int charge=record.getCharge();
				RechargePlanVo plan=getRechargePlanByUserId(studentId);
				if(plan==null||plan.getActive() !=OrderConstant.ACTIVE.ACTIVE||plan.getVstate()!=OrderConstant.VERIFY.PASS||plan.getIsdel()!=OrderConstant.ISDEL.NOTDELETE){
					log.info(record+" with  recharge has no plan or not valide="+plan);
					return ResultCode.ERRORCODE.SUCCESS;
				}
				//对信息进行冗余，对注册条件进行判断
				Student s=studentManager.getStudentInfo(record.getStudentId());
				record.setMobile(s.getPhoneNum());
				record.setName(s.getName());
				boolean register=((s.getApplyexam()==2 && s.getApplystate()==100 )||s.getApplyexam()>2);
				if(plan.getEnroll()==RechargeConstant.RECHARGEENROLL.REGISTER && !register ){
					log.error(s+" not register when need "+plan);
					return ResultCode.ERRORCODE.SUCCESS;
				}
				//查看同一个流水号是否已经存在,防止重复送钱
				List<RechargeRecordVo> old=rechargeRecordService.queryByWaterId(record.getWaterId(), new RechargeRecordQuery());
				if(old!=null && !old.isEmpty()) {
					log.error(record+" aready has recode="+old);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//2.计算并记录充送记录
				List<RechargeGearVo> gear=plan.getRechargeGearList();
				int recharge=0;
				String couponName = null;
				String couponId = null;
				String couponNum = null;
				for(int i=0;i<gear.size();i++){
					RechargeGearVo one=gear.get(i);
					//包含上限，但不包含下限
					if(charge<=one.getPmax() && charge>=one.getPmin() && one.getPercent()!=null && one.getPercent()>0) {
						recharge=charge*one.getPercent()/100;
						// 20160921增加送券
						if (one.getCouponName() != null && !"".equals(one.getCouponName())) {
							couponName = one.getCouponName();
						}
						if (one.getCouponId() != null && !"".equals(one.getCouponId())) {
							couponId = one.getCouponId();
						}
						if (one.getCouponNum() != null && !"".equals(one.getCouponNum())) {
							couponNum = one.getCouponNum();
						}
						break;
					} else if (charge<=one.getPmax() && charge>=one.getPmin() && one.getMoney()!=null && one.getMoney()>0) {
						recharge=one.getMoney();
						// 20160921增加送券
						if (one.getCouponName() != null && !"".equals(one.getCouponName())) {
							couponName = one.getCouponName();
						}
						if (one.getCouponId() != null && !"".equals(one.getCouponId())) {
							couponId = one.getCouponId();
						}
						if (one.getCouponNum() != null && !"".equals(one.getCouponNum())) {
							couponNum = one.getCouponNum();
						}
						break;
					} else if (charge<=one.getPmax() && charge>=one.getPmin() && one.getMoney() == null && one.getPercent() == null && one.getCouponName() != null) {
						if (one.getCouponName() != null && !"".equals(one.getCouponName())) {
							couponName = one.getCouponName();
						}
						if (one.getCouponId() != null && !"".equals(one.getCouponId())) {
							couponId = one.getCouponId();
						}
						if (one.getCouponNum() != null && !"".equals(one.getCouponNum())) {
							couponNum = one.getCouponNum();
						}
						break;
					} else if (one.getMoney() == null && one.getPercent() == null && one.getCouponName() == null) {
						log.error(record+" with recharge incorrect="+recharge);
						return ResultCode.ERRORCODE.FAILED;
					}
				}
				if(recharge<=0 && couponId == null){ //如果即不送钱也不送券
					log.error(record+" with recharge incorrect="+recharge);
					return ResultCode.ERRORCODE.FAILED;
				}
				//设置默认参数
				record.setRecharge(recharge);
				record.setCtime(new Date());
				record.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				record.setVstate(OrderConstant.VERIFY.WAIT);
				record.setRcid(plan.getRcid());
				record.setRcname(plan.getRcname());
				record.setVtype(plan.getVtype());
				// 20160921增加送券
				if (couponId != null) {
					record.setCouponId(couponId);
				}
				if (couponNum != null) {
					record.setCouponNum(couponNum);
				}
				if (couponName != null) {
					record.setCouponName(couponName);
				}
	//			record.setCompany(null);
				rechargeRecordService.addRechargeRecord(record);
				//3.如果自动充送的，那么发放充送金额,并通过审核，发推送和短信
				if(plan.getAuto()==RechargeConstant.AUTORECHARGE.AUTO) {				
					rechargeInternal(record, plan,"赠送金额");
				}
				//20160921增加判断是否有关联方案
				if (plan.getIsExitRercid() == 1) {
					//判断关联方案之前的方案是否是节点终点 -- 根据充值记录判断是否已有2次充值方案
					//判断次数跟已充值次数比较，如果相等或大于，则切换关联方案
					RechargeRecordQuery rechargerecordQuery=new RechargeRecordQuery();
					String post=" And isdel=0 And utype=1 And rcid!=0 group by rcid";
					rechargerecordQuery.setGroupBy(post);
					//已充值方案
					List<RechargeRecordVo> hasRechargeRcidNum = rechargeRecordService.queryByMobile(s.getPhoneNum(), rechargerecordQuery);
					if (hasRechargeRcidNum.size() < 2) {
						//判断次数跟已充值次数比较，如果相等或大于，则切换关联方案
						RechargeRecordVo search=new RechargeRecordVo();
						search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
						search.setRcid(plan.getRcid());
						search.setMobile(s.getPhoneNum());
						search.setUtype(1);
						RechargeRecordQuery query=new RechargeRecordQuery();
						//已充值次数
						Integer hasRechargeNum = rechargeRecordService.countByObject(search, query);
						if (hasRechargeNum >= plan.getRegNum()) {
							s.setVipPackageId(plan.getRercid().toString());
							studentManager.updateStudent(s); //更新学员信息
							//更新vip信息
							VipCustomVo vipCustomvo = new VipCustomVo();
							vipCustomvo.setRcid(plan.getRercid());
							RechargePlanVo newPlan = rechargePlanService.queryRechargePlanById(plan.getRercid());
							vipCustomvo.setRcname(newPlan.getRcname());
							vipCustomService.updateByStudentId(vipCustomvo , studentId);
						}
					}
					
				}
				
				return ResultCode.ERRORCODE.SUCCESS;
			}
			log.error(record+" add failed while lock not get.");
			return ResultCode.ERRORCODE.FAILED;
		} finally {
			if(lock!=null) {
				try {
					lock.unlock();
				} catch(Exception e){}
			}
		}
	}
	/**
	 * 增加手工调整学员余额的接口
	 * @param record
	 * @return
	 */
	public String addManualRechargeRecord(RechargeRecordVo record,long cuid) throws Exception {
			//1.参数判断
			if(record==null||record.getStudentId()==null ||record.getRecharge()==null || record.getUtype()==null || record.getRecharge()==0||StringUtil.isNullOrEmpty(record.getRcname())){
				log.error(record+" is not correct.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			//增加冗余信息
			//2.设置默认参数
			record.setCharge(0);
			record.setRcid(0);
			record.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			record.setVstate(OrderConstant.VERIFY.WAIT);
			record.setPayTime(new Date());
			record.setCtime(record.getPayTime());
			if(record.getUtype()==1){
				Student s=studentManager.getStudentInfo(record.getStudentId());
				record.setMobile(s.getPhoneNum());
				record.setName(s.getName());
				record.setPayWay("客服调整学员余额");
			}else if(record.getUtype()==2){
				Coach coach =coachManager.getCoachInfo(record.getStudentId());
				record.setMobile(coach.getPhoneNum());
				record.setName(coach.getName());
				record.setPayWay("客服调整教练余额");
			}
			rechargeRecordService.addRechargeRecord(record);
			return ResultCode.ERRORCODE.SUCCESS;
	}
	/**
	 * 财务通过审核
	 * @param rrids
	 * @param muid
	 * @return
	 */
	public String passManualRechargeRecord(List<Integer> rrids,long muid) throws Exception{
		RLock lock=null;
		try {
			//任何时候，只能有一人操作
			lock=redissonClient.getLock("rdlock_recharge.passRechargeRecord");
			//1s等待，10s超时，防止死锁
			boolean hasLock=lock.tryLock(1,10,TimeUnit.SECONDS);
			//锁获取成功，则往下走
			if(hasLock) {
				//1.参数校验
				if(rrids==null || rrids.isEmpty()){
					log.error(rrids+" is empty so cannot pass.");
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//2.有效性校验在资金中非常重要
				RechargeRecordQuery query=new RechargeRecordQuery();
				List<RechargeRecordVo> list=rechargeRecordService.queryRechargeRecordByIds(rrids, query);
				//记录数量不匹配
				if(list==null || list.size()<rrids.size()){
					log.error(list+" size not equal="+rrids);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//判断记录有效性
				for(int i=0;i<list.size();i++){
					RechargeRecordVo one=list.get(i);
					//防止对作废的记录进行送钱
					if(one.getIsdel()!=OrderConstant.ISDEL.NOTDELETE) {
						log.error(one+" is delete when "+list+" size equal="+rrids);
						return ResultCode.ERRORCODE.PARAMERROR;
					}
					//防止重复审核送钱
					if(one.getVstate()==OrderConstant.VERIFY.PASS) {
						log.error(one+" is PASS when "+list+" size equal="+rrids);
						return ResultCode.ERRORCODE.RECHARGE_AREADY_VERIFY;
					}
					//防止非手工调整混入
					if(one.getRcid()!=0){
						log.error(one+" is a NOT manual recharge when "+list+" size equal="+rrids);
						return ResultCode.ERRORCODE.PARAMERROR;
					}
					SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					one.setMtime(formatter.format(new Date()));
				}
				//3. 记录数据开启充送
				for(int i=0;i<list.size();i++){
					RechargeRecordVo record=list.get(i);
					record.setMuid(muid);
					if(record.getUtype()==2){
						rechargeInternalCoach(record,null,"调整金额");	
					}else{
						rechargeInternal(record, null,"调整金额");
					}
					
				}
				return ResultCode.ERRORCODE.SUCCESS;
			} else {
				log.error(rrids+" manual pass failed while lock not get="+muid);
				return ResultCode.ERRORCODE.FAILED;
			}
		//最终释放锁
		} finally {
			if(lock!=null) {
				try {
						lock.unlock();
				} catch(Exception e){}
			}
		} 
	}
	/**
	 * 财务审核拒绝
	 * @param rrids
	 * @param muid
	 * @return
	 */
	public String refuseManualRechargeRecord(List<Integer> rrids,String reason,long muid){
		try {
			//1.参数校验
			if(rrids==null || rrids.isEmpty()){
				log.error(rrids+" is empty so cannot pass.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			//2.有效性校验在资金中非常重要
			RechargeRecordQuery query=new RechargeRecordQuery();
			List<RechargeRecordVo> list=rechargeRecordService.queryRechargeRecordByIds(rrids, query);
			//记录数量不匹配
			if(list==null || list.size()<rrids.size()){
				log.error(list+" size not equal="+rrids);
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			//判断记录有效性
			for(int i=0;i<list.size();i++){
				RechargeRecordVo one=list.get(i);
				//防止对作废的记录进行送钱
				if(one.getIsdel()!=OrderConstant.ISDEL.NOTDELETE) {
					log.error(one+" is delete when "+list+" size equal="+rrids);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//防止重复审核送钱
				if(one.getVstate()!=OrderConstant.VERIFY.WAIT) {
					log.error(one+" is NOT WAIT when "+list+" size equal="+rrids);
					return ResultCode.ERRORCODE.RECHARGE_AREADY_VERIFY;
				}
				//防止非手工调整混入
				if(one.getRcid()!=0){
					log.error(one+" is a NOT manual recharge when "+list+" size equal="+rrids);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				one.setMuid(muid);
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				one.setMtime(formatter.format(new Date()));
			}
			BeanCopy.setListField(list, "vstate", OrderConstant.VERIFY.REFUSE);
			BeanCopy.setListField(list, "reason", reason);
			rechargeRecordService.saveRechargeRecordList(list);
			return ResultCode.ERRORCODE.SUCCESS;
		} catch(Exception e){
			log.error(rrids+" addManualRechargeRecord exception="+e.getMessage(),e);
			return ResultCode.ERRORCODE.EXCEPTION;
		}
	}
	/**
	 * 删除学员当前的优惠方案：仅包括大客户和非通用的普惠方案。
	 * 如果通用普惠方案需要删除，请停用对应的普惠方案即可。
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public String deleteRechargePlan(long studentId,long muid) throws Exception {
		VipCustomVo vip = vipCustomService.queryVipCustomById(studentId);
		if(vip!=null){
			vip.setIsdel(OrderConstant.ISDEL.DELETE);
			vip.setMuid(muid);
			vipCustomService.saveVipCustom(vip);
			return ResultCode.ERRORCODE.SUCCESS;
		}
		return ResultCode.ERRORCODE.FAILED;
	}
	private boolean isRegister(Student student){
		boolean register=((student.getApplyexam()==2 && student.getApplystate()==100 )||student.getApplyexam()>2);
		return register;
	}
	private RechargePlanVo reState(RechargePlanVo plan,Student s,EnrollOrder enroll) throws Exception{
		//需要注册
				if(plan.getEnroll()==RechargeConstant.RECHARGEENROLL.REGISTER) {
					//没有注册方案不生效
					if(!isRegister(s)){
						//只有已经审核的才会改变状态
						if(s.getVstate()==OrderConstant.VERIFY.PASS){
							log.info(plan+" is need register, but not register, so set none with "+s);
							plan.setVstate(OrderConstant.VERIFY.NOTIN);
						}
					//注册城市不对，也不生效
					} else if(StringUtil.isNotNullAndNotEmpty(plan.getCityId())) {
						boolean equal=(enroll!=null && plan.getCityId().indexOf(String.valueOf(enroll.getCityId()))>=0);
						//生效以后，就不再需要注册
						if(equal) {
							log.info(plan+" is need register, and has register and city equal, so set enroll=0 with "+s);
							plan.setEnroll(RechargeConstant.RECHARGEENROLL.NONE);
						//注册城市不对，也不生效
						//只有已经审核的才会改变状态
						} else if(s.getVstate()==OrderConstant.VERIFY.PASS){
							   log.info(plan+" is need register, has register but city not equal or null="+enroll+", so set vstate= "+OrderConstant.VERIFY.NOTIN+",s="+s);
								plan.setVstate(OrderConstant.VERIFY.NOTIN);
						}
					//生效以后，就不再需要注册
					} else {
						log.info(plan+" is need register, and has register, so set enroll=0 with "+s);
						plan.setEnroll(RechargeConstant.RECHARGEENROLL.NONE);
					}
				}
				//不在有效期的方案也不返回方案
				Date now=new Date();
				long start=0;
				long end=0;
				if(plan.getTstart()!=null ){
					start=TimeUtil.calcDistanceMillis(plan.getTstart(), now);
				}
				if(plan.getTend()!=null){
					end= TimeUtil.calcDistanceMillis(now,plan.getTend());
				}
				//未生效
				if(start<0){
					if(s.getVstate()==OrderConstant.VERIFY.PASS) {
						plan.setVstate(OrderConstant.VERIFY.NOTIN);
						log.error(plan+" is not in date "+now+" with start="+start+",end="+end+", so set="+OrderConstant.VERIFY.NOTIN);
					} else {
						log.error(plan+" is not in date "+now+" with start="+start+",end="+end);
					}
				}
				//已过期
				if(end<0){
					if(s.getVstate()==OrderConstant.VERIFY.PASS ) {
						plan.setVstate(OrderConstant.VERIFY.EXPIRE);
						log.error(plan+" is expire date "+now+" with start="+start+",end="+end+", so set="+OrderConstant.VERIFY.EXPIRE);
					} else {
						log.error(plan+" is expire date maybe plan is not pass "+now+" with start="+start+",end="+end);
					}
				}
				return plan;
	}
	@Override
	public Integer queryRechargeRecord(long userId) {
		int total= 0;
		try {
			RechargeRecordVo search=new RechargeRecordVo();
			search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			search.setVtype(2);
			search.setStudentId(userId);
			RechargeRecordQuery query=new RechargeRecordQuery();
			String post=" and vstate != 2 ";
			query.setGroupBy(post);
			total = 	rechargeRecordService.countByObject(search, query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}
	@Override
	public List<VipCompanyVo> queryByNew0(VipCompanyVo vipCompanyVo,  VipCompanyQuery vipCompanyQuery) {
		try {
			return vipCompanyService.queryByNew0(vipCompanyVo, vipCompanyQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String updateVipCustom(VipCustomVo vipCustomVo) throws Exception {
		  try {
			vipCustomService.updateByStudentId(vipCustomVo, vipCustomVo.getStudentId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return ResultCode.ERRORCODE.SUCCESS;
	}
	
}
