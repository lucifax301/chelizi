package com.lili.access.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.LogConstant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.LogUtil;
import com.lili.finance.vo.UserMoneyVo;
import com.lili.log.model.LogCommon;
import com.lili.school.dto.EnrollTheory;
import com.lili.school.model.Region;
import com.lili.school.model.School;
import com.lili.school.model.SchoolAccountApply;
import com.lili.school.model.SchoolBDTO;
import com.lili.school.model.SchoolExtend;
import com.lili.school.model.WechatEnrollPackage;
import com.lili.school.model.WechatEnrollPackageBDTO;
import com.lili.school.service.CMSRegionService;
import com.lili.school.service.CMSSchoolService;
import com.lili.user.model.User;


/**
 * 驾校
 * @author lzb
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/school")
public class SchoolController extends BaseController{
	Logger logger = Logger.getLogger(SchoolController.class);
	
	@Autowired
	CMSSchoolService cmsSchoolService;
	
	@Autowired
	CMSRegionService cmsRegionService;
	
	/**
	 * 获取城市列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryCity", method = RequestMethod.GET)
	@ResponseBody
	public String queryCity(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			Region dto = (Region) buildObject(request, Region.class);
			return  cmsRegionService.findCityList(dto).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}

	
	/**
	 * 驾校分页查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySchool", method = RequestMethod.GET)
	@ResponseBody
	public String querySchool(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resp = null;
		try {
			SchoolBDTO dto = (SchoolBDTO) buildObject(request, SchoolBDTO.class);
			resp =  cmsSchoolService.findSchoolList(dto).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
		return resp;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			SchoolBDTO dto = (SchoolBDTO) buildObject(request, SchoolBDTO.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_CAR, LogConstant.ACTION_ADD);
			return cmsSchoolService.addSchool(log,dto).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			SchoolBDTO dto = (SchoolBDTO) buildObject(request, SchoolBDTO.class);
			String schoolNo = request.getParameter("schoolNo");
			dto.setSchoolId(Long.parseLong(schoolNo));
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_CAR, LogConstant.ACTION_ADD);
			return cmsSchoolService.updateSchool(log,dto).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 获取驾校-下拉框
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public String query(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			SchoolBDTO dto = (SchoolBDTO) buildObject(request, SchoolBDTO.class);
			return  cmsSchoolService.findSchoolArray(dto).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 驾校钱包
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryBurse", method = RequestMethod.GET)
	@ResponseBody
	public String queryBurse(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			SchoolExtend dto = (SchoolExtend) buildObject(request, SchoolExtend.class);
			return  cmsSchoolService.queryBurse(dto).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 审核通过
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkPass", method = RequestMethod.POST)
	@ResponseBody
	public String checkPass(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			String remark  = request.getParameter("checkRemark");
			String id = request.getParameter("idList");
			String schoolNos = request.getParameter("schoolNos");
			return  cmsSchoolService.checkPass(checker, remark, id, schoolNos).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 同意变更
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/agreeChange", method = RequestMethod.POST)
	@ResponseBody
	public String agreeChange(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			String remark  = request.getParameter("checkRemark");
			String id = request.getParameter("idList");
			String schoolNos = request.getParameter("schoolNos");
			
			return  cmsSchoolService.agreeChange(checker, remark, id, schoolNos).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 审核不通过
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkReject", method = RequestMethod.POST)
	@ResponseBody
	public String checkReject(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			String remark  = request.getParameter("checkRemark");
			String id = request.getParameter("idList");
			
			return  cmsSchoolService.checkReject(checker, remark, id).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 拒接变更
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/refuseChange", method = RequestMethod.POST)
	@ResponseBody
	public String refuseChange(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			String remark  = request.getParameter("checkRemark");
			String id = request.getParameter("idList");
			
			return  cmsSchoolService.refuseChange(checker, remark, id).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 查询驾校账户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAccount", method = RequestMethod.GET)
	@ResponseBody
	public String queryAccount(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			String ymDate = request.getParameter("ymDate");
			String schoolId = request.getParameter("schoolId");
			SchoolExtend schoolExtend = (SchoolExtend)buildObject(request, SchoolExtend.class);
			schoolExtend.setSchoolId(Long.parseLong(schoolId));
			return  cmsSchoolService.queryAccount(schoolExtend, ymDate);
		} 
		catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 查询驾校账户余额明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/touchBalance", method = RequestMethod.GET)
	@ResponseBody
	public String touchBalance(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			String schoolId = request.getParameter("schoolId");
			UserMoneyVo userMoneyVo = (UserMoneyVo) buildObject(request, UserMoneyVo.class);
			String operateType = request.getParameter("operateType");
			logger.info("********************************** operateType" + operateType);
			if(!"".equals(operateType) && operateType!=null){
				userMoneyVo.setOperateType(Integer.valueOf(operateType));
			}
			userMoneyVo.setUserType(3);
			userMoneyVo.setUserId(Long.parseLong(schoolId));
			return  cmsSchoolService.touchBalance(userMoneyVo).build();
		} 
		catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 查询驾校账户收入明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/accountBalance", method = RequestMethod.GET)
	@ResponseBody
	public String accountBalance(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			String schoolId = request.getParameter("schoolId");
			UserMoneyVo userMoneyVo = (UserMoneyVo) buildObject(request, UserMoneyVo.class);
			String operateType = request.getParameter("operateType");
			logger.info("********************************** operateType" + operateType);
			if(!"".equals(operateType) && operateType!=null){
				userMoneyVo.setOperateType(Integer.valueOf(operateType));
			}
			userMoneyVo.setUserType(3);
			userMoneyVo.setUserId(Long.parseLong(schoolId));
			return  cmsSchoolService.accountBalance(userMoneyVo).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 查询驾校账户费用明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/costDetail", method = RequestMethod.GET)
	@ResponseBody
	public String costDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			String schoolId = request.getParameter("schoolId");
			UserMoneyVo userMoneyVo = (UserMoneyVo) buildObject(request, UserMoneyVo.class);
			String operateType = request.getParameter("operateType");
			logger.info("********************************** operateType" + operateType);
			if(!"".equals(operateType) && operateType!=null){
				userMoneyVo.setOperateType(Integer.valueOf(operateType));
			}
			userMoneyVo.setUserType(3);
			userMoneyVo.setUserId(Long.parseLong(schoolId));
			return  cmsSchoolService.costDetail(userMoneyVo).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**************************品牌露出驾校 班级*******************************/
	
	/**
	 * 微信端驾校分页查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDriveSchool", method = RequestMethod.GET)
	@ResponseBody
	public String queryDriveSchool(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resp = null;
		try {
			SchoolBDTO school = (SchoolBDTO) buildObject(request, SchoolBDTO.class);
			resp =  cmsSchoolService.findWxSchoolList(school).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
		return resp;
	}
	
	
	/**
	 * 微信端驾校班级分页查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPackage", method = RequestMethod.GET)
	@ResponseBody
	public String queryPackage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resp = null;
		try {
	    	WechatEnrollPackageBDTO wBdto = (WechatEnrollPackageBDTO) buildObject(request, WechatEnrollPackageBDTO.class);
			resp =  cmsSchoolService.findPackageList(wBdto).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
		return resp;
	}

	/**
	 * 微信端通过id查找驾校班级
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPackageById", method = RequestMethod.GET)
	@ResponseBody
	public String queryPackageById(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resp = null;
		List<WechatEnrollPackage> list=new ArrayList<>();
		try {
			String ttids=request.getParameter("ttids");
			String arg[]=ttids.split(",");
			
			for(int i=0;i<arg.length;i++){
				String ttid=arg[i];
				if(ttid==null || ttid.equals("")){
					continue;
				}
			 WechatEnrollPackage wPackage=cmsSchoolService.findPackageById(ttid);
			 if(wPackage!=null){
				  list.add(wPackage);	
			 }
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
		resp=new ResponseMessage().addResult("pageData", list).build();
		return resp;
	}
	/**
	 * 微信端班级添加
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addPackage", method=RequestMethod.GET)
    @ResponseBody
    public String addPackage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			WechatEnrollPackageBDTO dto = (WechatEnrollPackageBDTO) buildObject(request, WechatEnrollPackageBDTO.class);
			String cType=request.getParameter("cType")==null?"1":request.getParameter("cType");
			dto.setcType(Integer.parseInt(cType));
			//非空字段 isdel是否删除  ctime创建时间 mtime修改时间
			dto.setIsdel((byte) 0);
			dto.setCtime(new Date());
			dto.setMtime(new Date());
			dto.setCstate(1);
			dto.setOstate(1);
			dto.setPriceDetail(dto.getPriceDetail()==null?"":dto.getPriceDetail());
			dto.setDescribtion(dto.getDescribtion()==null?"":dto.getDescribtion());
			dto.setTest_condition(dto.getTest_condition()==null?"":dto.getTest_condition());
			return cmsSchoolService.addPackage(dto).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	/**
	 * 微信端班级修改
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePackage", method=RequestMethod.GET)
    @ResponseBody
    public String updatePackage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			WechatEnrollPackageBDTO dto = (WechatEnrollPackageBDTO) buildObject(request, WechatEnrollPackageBDTO.class);
			String ttNo = request.getParameter("ttNo");
			String cType=request.getParameter("cType")==null?"1":request.getParameter("cType");
			dto.setcType(Integer.parseInt(cType));
			dto.setTtid(Integer.parseInt(ttNo));
			//非空字段 isdel是否删除   mtime修改时间
			dto.setIsdel((byte) 0);
			dto.setMtime(new Date());
			return cmsSchoolService.updatePackage(dto).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	/**
	 * 微信端班级状态修改
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePackageState", method=RequestMethod.GET)
    @ResponseBody
    public String updatePackageState(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			WechatEnrollPackageBDTO dto = (WechatEnrollPackageBDTO) buildObject(request, WechatEnrollPackageBDTO.class);
			String ttids = request.getParameter("ttids");
			//mtime修改时间
			dto.setMtime(new Date());
			return cmsSchoolService.updatePackageState(dto,ttids).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/apply", method=RequestMethod.POST)
    @ResponseBody
    public String apply(SchoolAccountApply apply,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			
			return cmsSchoolService.applySchoolAccount(apply).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/applyQuery", method=RequestMethod.POST)
    @ResponseBody
    public String applyQuery(SchoolAccountApply apply,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			
			return cmsSchoolService.findApplySchool(apply).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/applyAudit", method=RequestMethod.POST)
    @ResponseBody
    public String applyAudit(SchoolAccountApply apply,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			
			return cmsSchoolService.auditSchool(apply).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/applyUnAudit", method=RequestMethod.POST)
    @ResponseBody
    public String applyUnAudit(SchoolAccountApply apply,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			
			return cmsSchoolService.unauditSchool(apply).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 更改学员状态：考勤状态：0-未记考勤；1-出勤；2-缺勤
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theoryStudent/state", method = RequestMethod.POST)
	@ResponseBody
	public String changeTheoryStudentState(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String theoryId = request.getParameter("theoryId");
			String studentIds = request.getParameter("studentIds");
			String state = request.getParameter("state");
			
			return  cmsSchoolService.changeTheoryStudentState(schoolId,theoryId,studentIds,state).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 安排学员--获取已导入学员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theoryStudent", method = RequestMethod.GET)
	@ResponseBody
	public String getTheoryStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String theoryId = request.getParameter("theoryId");
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String state = request.getParameter("state"); //考勤状态：0-未记考勤；1-出勤；2-缺勤
			String isImport = request.getParameter("isImport"); // 0-喱喱学员，1-驾校学员
			EnrollTheory theory= cmsSchoolService.getTheoryByTheoryId(theoryId);
			schoolId=Long.valueOf(theory.getSchoolId());
			return  cmsSchoolService.getTheoryStudent(schoolId,theoryId,pageNo,pageSize,state,isImport).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 安排学员--获取确认开课短信通知模板
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory/msgInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getTheoryMsgInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String state = request.getParameter("state"); //1-待上课；3-已取消'
			return  cmsSchoolService.getTheoryMsgInfo(state).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 更改课程状态--确认开课，取消开课--短信通知学员 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory/class", method = RequestMethod.POST)
	@ResponseBody
	public String changeTheoryClass(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String theoryId = request.getParameter("theoryId");
			String state = request.getParameter("state");
			EnrollTheory theory= cmsSchoolService.getTheoryByTheoryId(theoryId);
			schoolId=Long.valueOf(theory.getSchoolId());
			return  cmsSchoolService.changeTheoryClass(schoolId,theoryId,state).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 长考课--新建--根据手机号获取教练信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain/coach", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrainCoach(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String contactMobile = request.getParameter("contactMobile");
			logger.info("**********************************postTheory schoolId:" + schoolId);
			
			return  cmsSchoolService.getLongtrainCoach(schoolId,contactMobile).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 长考课--新建--获取车辆信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain/car", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrainCar(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String carNo = getParamStr(request, "carNo");
			logger.info("**********************************postTheory schoolId:" + schoolId);
			
			return  cmsSchoolService.getLongtrainCar(schoolId,carNo).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 长考课--新建课程
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain", method = RequestMethod.POST)
	@ResponseBody
	public String addLongtrain(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String contactMobile = request.getParameter("contactMobile");
			String carNo = getParamStr(request, "carNo");
			String classDate = request.getParameter("classDate");
			String classTime = request.getParameter("classTime");
			String classPlace = request.getParameter("classPlace");
			String carrys = request.getParameter("carrys");

			logger.info("**********************************postTheory schoolId:" + schoolId);
			
			return  cmsSchoolService.addLongtrain(schoolId,contactMobile,carNo,classDate,classTime,classPlace,carrys).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 长考课--获取课程
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrain(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			//User user = (User)session.getAttribute(Constant.USER_SESSION);
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			String cityId= request.getParameter("cityId");
			System.out.println("******************school:"+school);
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String state = request.getParameter("state");
			String dateBegin = request.getParameter("dateBegin");
			String dateEnd = request.getParameter("dateEnd");
			String ctimeBegin = request.getParameter("ctimeBegin");
			String ctimeEnd = request.getParameter("ctimeEnd");
			if(cityId==null&&schoolId==0){
				return cmsSchoolService.getLongtrain(schoolId,state,pageNo,pageSize,dateBegin,dateEnd,ctimeBegin,ctimeEnd).build();
			}else if(cityId!=null&&schoolId==0){
				SchoolBDTO dto = new SchoolBDTO();
				dto.setCityId(Integer.parseInt(cityId));
				List<School> schools =  cmsSchoolService.findAllSchool(dto);
				List<Integer> schoolids=new ArrayList();
				for(School s:schools){
					schoolids.add(s.getSchoolId().intValue());
				}
				return  cmsSchoolService.getLongtrain(schoolids,state,pageNo,pageSize,dateBegin,dateEnd,ctimeBegin,ctimeEnd).build();
			}else if(schoolId>0){
				return  cmsSchoolService.getLongtrain(schoolId,state,pageNo,pageSize,dateBegin,dateEnd,ctimeBegin,ctimeEnd).build();
			}
			return  cmsSchoolService.getLongtrain(schoolId,state,pageNo,pageSize,dateBegin,dateEnd,ctimeBegin,ctimeEnd).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 长考课--获取单个课程
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain/one", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrainOne(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			Long schoolId =  user.getSchoolId();
			String ltId = request.getParameter("ltId");
			
			return  cmsSchoolService.getLongtrainOne(schoolId,ltId).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 长考课--修改课程
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain/modify", method = RequestMethod.POST)
	@ResponseBody
	public String modifyLongtrain(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			Long schoolId =  user.getSchoolId();
			String ltId = request.getParameter("ltId");
			String contactMobile = request.getParameter("contactMobile");
			String carNo = getParamStr(request, "carNo");
			String classDate = request.getParameter("classDate");
			String classTime = request.getParameter("classTime");
			String classPlace = request.getParameter("classPlace");
			String carrys = request.getParameter("carrys");
			
			return  cmsSchoolService.modifyLongtrain(schoolId,ltId,contactMobile,carNo,classDate,classTime,classPlace,carrys).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 安排长考学员--导入学员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrainStudent", method = RequestMethod.POST)
	@ResponseBody
	public String addLongtrainStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			Long schoolId =  user.getSchoolId();
			String ltId = request.getParameter("ltId");
			String studentIds = request.getParameter("studentIds");
			
			return  cmsSchoolService.addLongtrainStudent(schoolId,ltId,studentIds).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 安排长考学员--获取已导入学员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrainStudent", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrainStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			//Long schoolId =  user.getSchoolId();
			String ltId = request.getParameter("ltId");
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String state = request.getParameter("state");
			String isImport = request.getParameter("isImport");
			
			return  cmsSchoolService.getLongtrainStudent(0L,ltId,pageNo,pageSize,state,isImport).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 获取理论课列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory", method = RequestMethod.GET)
	@ResponseBody
	public String getTheory(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			String cityId= request.getParameter("cityId");
			System.out.println("******************school:"+school);
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String state = request.getParameter("state");
			String dateBegin = request.getParameter("dateBegin");
			String dateEnd = request.getParameter("dateEnd");
			String ctimeBegin = request.getParameter("ctimeBegin");
			String ctimeEnd = request.getParameter("ctimeEnd");
			logger.info("**********************************getTheory schoolId:" + schoolId);
			
			
			if(cityId==null&&schoolId==0){
				return  cmsSchoolService.getTheoryBySchoolId(schoolId,state,pageNo,pageSize,dateBegin,dateEnd,ctimeBegin,ctimeEnd).build();
			}else if(cityId!=null&&schoolId==0){
				SchoolBDTO dto = new SchoolBDTO();
				dto.setCityId(Integer.parseInt(cityId));
				List<School> schools =  cmsSchoolService.findAllSchool(dto);
				List<Integer> schoolids=new ArrayList();
				for(School s:schools){
					schoolids.add(s.getSchoolId().intValue());
				}
				return  cmsSchoolService.getTheoryBySchoolId(schoolids,state,pageNo,pageSize,dateBegin,dateEnd,ctimeBegin,ctimeEnd).build();
			}else if(schoolId>0){
				return  cmsSchoolService.getTheoryBySchoolId(schoolId,state,pageNo,pageSize,dateBegin,dateEnd,ctimeBegin,ctimeEnd).build();
			}
			
			return  cmsSchoolService.getTheoryBySchoolId(schoolId,state,pageNo,pageSize,dateBegin,dateEnd,ctimeBegin,ctimeEnd).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	/**
	 * 获取理论课 单个
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory/one", method = RequestMethod.GET)
	@ResponseBody
	public String getTheoryOne(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			System.out.println("******************school:"+school);
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String theoryId = request.getParameter("theoryId");
			logger.info("**********************************getTheory schoolId:" + schoolId);
			EnrollTheory theory= cmsSchoolService.getTheoryByTheoryId(theoryId);
			schoolId=Long.valueOf(theory.getSchoolId());
			return  cmsSchoolService.getTheoryByTheoryId(schoolId,theoryId).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 添加理论课
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory", method = RequestMethod.POST)
	@ResponseBody
	public String addTheory(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			System.out.println("******************school:"+school);
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String classDate = request.getParameter("classDate");
			String classTime = request.getParameter("classTime");
			String className = request.getParameter("className");
			String cardDesc = request.getParameter("cardDesc");
			String contactName = request.getParameter("contactName");
			String classPlace = request.getParameter("classPlace");
			String contactMobile = request.getParameter("contactMobile");
			logger.info("**********************************postTheory schoolId:" + schoolId);

			return  cmsSchoolService.addTheory(schoolId,classDate,classTime,className,cardDesc,contactName,contactMobile,classPlace).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 修改理论课
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory/modify", method = RequestMethod.POST)
	@ResponseBody
	public String modifyTheory(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			Long schoolId =  0L;
			String school= request.getParameter("schoolId");
			System.out.println("******************school:"+school);
			try{
			schoolId=Long.parseLong(school);
			}catch(Exception ex){}
			String theoryId = request.getParameter("theoryId");
			String classDate = request.getParameter("classDate");
			String classTime = request.getParameter("classTime");
			String className = request.getParameter("className");
			String cardDesc = request.getParameter("cardDesc");
			String contactName = request.getParameter("contactName");
			String classPlace = request.getParameter("classPlace");
			String contactMobile = request.getParameter("contactMobile");

			return  cmsSchoolService.modifyTheory(theoryId,schoolId,classDate,classTime,className,cardDesc,contactName,contactMobile,classPlace).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	/**
	 * 班别导入学员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addPackageStudent", method=RequestMethod.POST)
    @ResponseBody
    public String addPackageStudent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String studentIds=request.getParameter("studentIds");
			String ttid=request.getParameter("ttid");
			int tid=Integer.parseInt(ttid);
			String args[]=studentIds.split(",");
			for(int i=0;i<args.length;i++){
				if(args[i].equals("")){
					continue;
				}
				int studentId=Integer.parseInt(args[i]);
				cmsSchoolService.addPackageStudent(studentId,tid);
			}
			return new  ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 删除学员关联班别
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deletePackageStudent", method=RequestMethod.POST)
    @ResponseBody
    public String deletePackageStudent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String studentIdStr=request.getParameter("studentId");
			int studentId=Integer.parseInt(studentIdStr);
			cmsSchoolService.addPackageStudent(studentId,0); //删除关联关系 
			return new  ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

}