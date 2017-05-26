package com.lili.student.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.StringUtil;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.Page;
import com.lili.common.vo.ResultCode;
import com.lili.finance.service.ICMSCouponService;
import com.lili.school.model.Region;
import com.lili.school.service.CMSRegionService;
import com.lili.school.service.CMSSchoolService;
import com.lili.student.dto.Student;
import com.lili.student.manager.ICmsVipManager;
import com.lili.student.manager.StudentManager;
import com.lili.student.model.StudentVo;
import com.lili.student.model.VipCustomExport;
import com.lili.student.model.VipCustomExportDetail;
import com.lili.student.service.CMSStudentService;
import com.lili.student.service.CMSVipService;
import com.lili.student.service.RechargeService;
import com.lili.student.vo.CouponList;
import com.lili.student.vo.RechargeGearVo;
import com.lili.student.vo.RechargePlanVo;
import com.lili.student.vo.RechargeRecordVo;
import com.lili.student.vo.VipCompanyVo;
import com.lili.student.vo.VipCustomVo;
import com.lili.user.model.User;
import com.lili.user.service.CMSUserService;

public class CMSVipServiceImpl implements CMSVipService{


	protected static final Logger access = Logger.getLogger("CMSVipServiceImpl");
	
	@Autowired
	RechargeService rechargeService;
	@Autowired
	CMSUserService cmsUserService;
	@Autowired
	CMSSchoolService cmsSchoolService;
	@Autowired
	CMSRegionService cmsRegionService;
	@Autowired
	CMSStudentService cmsStudentService;
	@Autowired
	StudentManager studentManager;
	
	@Autowired
	ICmsVipManager cmsVipManager;
	
	@Autowired
	ICMSCouponService cmsCouponService;

	@Override
	public ResponseMessage createVipCompany(VipCompanyVo company, User user) throws Exception {
		company.setCuid(user.getId());
		company.setMuid(user.getId());
		String code = rechargeService.createVipCompany(company, user.getId());
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public List<StudentVo> getExportCustomList(VipCustomVo search) throws Exception {
		List<Student> customList = rechargeService.getCustomList(search,true, 1000,1);

		List<StudentVo> vos = new ArrayList<StudentVo>();
		if(customList != null && customList.size() > 0){
			try {
				for(Student student : customList){
					StudentVo vo = new StudentVo();
					vo.setStudentId(student.getStudentId());
					vo.setName(student.getName());
					vo.setPhoneNum(student.getPhoneNum());
					vo.setSex(student.getSex());
					vo.setApplyCarType(student.getApplyCarType());
					vo.setVipPackageId(student.getVipPackageId());
					vo.setIdNumber(student.getIdNumber());
					vo.setApplyexam(student.getApplyexam());
					vo.setApplystate(student.getApplystate());
					vo.setRcname(student.getRcname());
					vo.setFlowNo(student.getFlowNo());
					vo.setState(student.getState());
					vo.setSchoolName(cmsSchoolService.findSchoolNameById(student.getSchoolId()));
					vo.setVstate(student.getVstate());
					Region region = cmsRegionService.findRegion(student.getCityId());
					if(region != null){
						vo.setCityName(region.getRegion());
					}
					vos.add(vo);
				}
			} catch (Exception e) {
				access.error("||| exception happends when getCustomList : " + e.getMessage());
			}
		}
		
		
		return vos;
	}
	
	
	@Override
	public ResponseMessage getCustomList(VipCustomVo search, int pageSize,
			int pageIndex,Boolean vipBig) throws Exception {
		Page<Student> countCustomList = rechargeService.countCustomList(search,vipBig, pageSize);
		List<Student> customList = rechargeService.getCustomList(search,vipBig, pageSize,pageIndex);
		int total = countCustomList.getTotal();

		List<StudentVo> vos = new ArrayList<StudentVo>();
		if(customList != null && customList.size() > 0){
			try {
				for(Student student : customList){
					StudentVo vo = new StudentVo();
					vo.setStudentId(student.getStudentId());
					vo.setName(student.getName());
					vo.setPhoneNum(student.getPhoneNum());
					vo.setSex(student.getSex());
					vo.setApplyCarType(student.getApplyCarType());
					vo.setVipPackageId(student.getVipPackageId());
					vo.setIdNumber(student.getIdNumber());
					vo.setApplyexam(student.getApplyexam());
					vo.setApplystate(student.getApplystate());
					vo.setRcname(student.getRcname());
					vo.setFlowNo(student.getFlowNo());
					vo.setState(student.getState());
					vo.setVipId(student.getVipId());
					vo.setCtime(student.getCtime());
					vo.setCid(student.getCid());
					vo.setCompany(student.getCompany());
					vo.setSchoolName(cmsSchoolService.findSchoolNameById(student.getSchoolId()));
					vo.setVstate(student.getVstate());
					vo.setEnrollCity(student.getEnrollCity());
					vos.add(vo);
				}
			} catch (Exception e) {
				access.error("||| exception happends when getCustomList : " + e.getMessage());
			}
		}
		
		PagedResult<StudentVo> result = new PagedResult<StudentVo>();
        result.setPageNo(pageIndex);
        result.setPageSize(pageSize);
        result.setDataList(vos);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
		
		return new ResponseMessage().addPagedResult(result);
	}

	@Override
	public ResponseMessage passCustom(String studentIds, User user) throws Exception {
		String[] idArr = studentIds.split(",");
		List<Long> idList = new LinkedList<Long>();
		if(idArr != null && idArr.length > 0){
			for(String id : idArr){
				idList.add(Long.valueOf(id));
			}
		}
		
		String code = rechargeService.passCustom(idList, user.getId());
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage refuseCustom(String studentIds, String reason, User user) throws Exception {
		String[] idArr = studentIds.split(",");
		List<Long> idList = new LinkedList<Long>();
		if(idArr != null && idArr.length > 0){
			for(String id : idArr){
				idList.add(Long.valueOf(id));
			}
		}
		
		String code = rechargeService.refuseCustom(idList,reason, user.getId());
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage activeRechargePlan(String rcid, User user) throws Exception {

		String[] idArr = rcid.split(",");
		if(idArr != null && idArr.length > 0){
			for(String id : idArr){
				rechargeService.activeRechargePlan(Integer.valueOf(id), user.getId());
			}
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage inactiveRechargePlan(String rcid, User user) throws Exception {
		String[] idArr = rcid.split(",");
		if(idArr != null && idArr.length > 0){
			for(String id : idArr){
				rechargeService.inactiveRechargePlan(Integer.valueOf(id), user.getId());
			}
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage getRechargePlan(Integer rcid, User user) throws Exception {
		RechargePlanVo vo = rechargeService.getRechargePlan(rcid);
		if(vo != null){
			try {
				User updateUser = cmsUserService.findById(vo.getMuid());
				User createUser = cmsUserService.findById(vo.getCuid());
				vo.setCreateUser(createUser.getUserName());
				vo.setUpdateUser(updateUser.getUserName());
			} catch (Exception e) {
			}
			return new ResponseMessage().addPagedResult(vo);
		}else {
			return new ResponseMessage();
		}
	}

	@Override
	public ResponseMessage addRechargePlan(RechargePlanVo plan,List<RechargeGearVo> rechargeGearList, User user) throws Exception {
		String code = null;
		try {
			//解析rechargeGearList
			List<RechargeGearVo> newRechargeGearList = new ArrayList<RechargeGearVo>();
			StringBuffer couponName = null;
			StringBuffer couponId  = null;
			StringBuffer couponNum  = null;
			CouponList couponInfo = null;
			String couponNameOne = null;
			for (RechargeGearVo rechargeGearVo : rechargeGearList) {
				List<CouponList> couponList = rechargeGearVo.getCouponList();
				couponId = new StringBuffer();
				couponNum = new StringBuffer();
				couponName = new StringBuffer();
				for (int i = 0; i < couponList.size(); i ++) {
					couponInfo = couponList.get(i);
					couponNameOne = cmsCouponService.queryCouponName(couponInfo.getCouponId());
					couponName.append(couponNameOne);
					couponId.append(couponInfo.getCouponId());
					couponNum.append(couponInfo.getCouponNum());
					if (i != couponList.size() -1) {
						couponId.append("|");
						couponNum.append("|");
						couponName.append("|");
					}
					rechargeGearVo.setCouponId(couponId.toString());
					rechargeGearVo.setCouponNum(couponNum.toString());
					rechargeGearVo.setCouponName(couponName.toString());
				}
				newRechargeGearList.add(rechargeGearVo);
			}
			
			if (plan.getRercid() != null && !"".equals(plan.getRercid())) {
				plan.setIsExitRercid(1);
			}
			if (plan.getCouponTemplate() != null && !"".equals(plan.getCouponTemplate())) {
				plan.setCouponNumber("1");
			}
			plan.setRechargeGearList(rechargeGearList);
			code = rechargeService.addRechargePlan(plan, user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage activeVipCompany(String coids, User user) throws Exception {
		String[] idArr = coids.split(",");
		List<Integer> idList = new LinkedList<Integer>();
		if(idArr != null && idArr.length > 0){
			for(String id : idArr){
				idList.add(Integer.valueOf(id));
			}
		}
		
		String code = rechargeService.activeVipCompany(idList, user.getId());
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage inactiveVipCompany(String coids, User user) throws Exception {
		String[] idArr = coids.split(",");
		List<Integer> idList = new LinkedList<Integer>();
		if(idArr != null && idArr.length > 0){
			for(String id : idArr){
				idList.add(Integer.valueOf(id));
			}
		}
		
		String code = rechargeService.inactiveVipCompany(idList, user.getId());
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage modifyVipCompany(VipCompanyVo vipCompany, User user) throws Exception {
		String code = rechargeService.modifyVipCompany(vipCompany, user.getId());
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage getVipCompanyList(VipCompanyVo search, int pageSize,
			int pageIndex) throws Exception {
		List<VipCompanyVo> vipCompanyList = rechargeService.getVipCompanyList(search, pageSize,pageIndex);
		Page<VipCompanyVo> countVipCompany = rechargeService.countVipCompany(search, pageSize);
		List<com.lili.student.model.VipCompanyVo> vos = new ArrayList<com.lili.student.model.VipCompanyVo>();
		int total = countVipCompany.getTotal();
		if(vipCompanyList != null && vipCompanyList.size() > 0){
			for(VipCompanyVo vo : vipCompanyList){
				com.lili.student.model.VipCompanyVo _vo = new com.lili.student.model.VipCompanyVo(); 
				try {
					BeanCopy.copyAll(vo, _vo);
					User updateUser = cmsUserService.findById(vo.getMuid());
					User createUser = cmsUserService.findById(vo.getCuid());
					_vo.setCreateUser(createUser.getUserName());
					_vo.setUpdateUser(updateUser.getUserName());
					vos.add(_vo);
				} catch (Exception e) {
					access.error("||| exception happends : " + e.getMessage());
					vos.add(_vo);
				}
			}
		}

		PagedResult<com.lili.student.model.VipCompanyVo> result = new PagedResult<com.lili.student.model.VipCompanyVo>();
        result.setPageNo(pageIndex);
        result.setPageSize(pageSize);
        result.setDataList(vos);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);

		
		return new ResponseMessage().addPagedResult(result);
	}
	

	@Override
	public List<com.lili.student.model.VipCompanyVo> getExportVipCompanyList(VipCompanyVo search, int pageSize,
			int pageIndex) throws Exception {
		List<VipCompanyVo> vipCompanyList = rechargeService.getVipCompanyList(search, pageSize,pageIndex);
		List<com.lili.student.model.VipCompanyVo> vos = new ArrayList<com.lili.student.model.VipCompanyVo>();
		if(vipCompanyList != null && vipCompanyList.size() > 0){
			for(VipCompanyVo vo : vipCompanyList){
				com.lili.student.model.VipCompanyVo _vo = new com.lili.student.model.VipCompanyVo(); 
				try {
					BeanCopy.copyAll(vo, _vo);
					User updateUser = cmsUserService.findById(vo.getMuid());
					User createUser = cmsUserService.findById(vo.getCuid());
					_vo.setCreateUser(createUser.getUserName());
					_vo.setUpdateUser(updateUser.getUserName());
					vos.add(_vo);
				} catch (Exception e) {
					access.error("||| exception happends : " + e.getMessage());
					vos.add(_vo);
				}
			}
		}


		
		return vos;
	}

	@Override
	public ResponseMessage countVipCompany(VipCompanyVo search, int pageSize) throws Exception {
		return new ResponseMessage().addPagedResult(rechargeService.countVipCompany(search, pageSize));
	}

	@Override
	public ResponseMessage countCustomList(VipCustomVo search, int pageSize)
			throws Exception {
		return new ResponseMessage().addPagedResult(rechargeService.countCustomList(search,true, pageSize));
	}

	@Override
	public ResponseMessage getRechargePlanList(RechargePlanVo search,
			int pageSize, int pangeIndex) throws Exception {
		
		List<RechargePlanVo> rechargePlanList = rechargeService.getRechargePlanList(search, pageSize,pangeIndex);
		Page<RechargePlanVo> countRechargePlanList = rechargeService.countRechargePlanList(search, pageSize);
		List<com.lili.student.model.RechargePlanVo> vos = new ArrayList<com.lili.student.model.RechargePlanVo>();
		int total = countRechargePlanList.getTotal();
		if(rechargePlanList != null && rechargePlanList.size() > 0){
			for(RechargePlanVo vo : rechargePlanList){
				com.lili.student.model.RechargePlanVo _vo = new com.lili.student.model.RechargePlanVo();
				try {
					BeanCopy.copyAll(vo, _vo);
					User updateUser = cmsUserService.findById(vo.getMuid());
					User createUser = cmsUserService.findById(vo.getCuid());
					_vo.setCreateUser(createUser.getUserName());
					_vo.setUpdateUser(updateUser.getUserName());
					vos.add(_vo);
				} catch (Exception e) {
					access.error("||| exception happends : " + e.getMessage());
					vos.add(_vo);
				}
			}
		}

		PagedResult<com.lili.student.model.RechargePlanVo> result = new PagedResult<com.lili.student.model.RechargePlanVo>();
        result.setPageNo(pangeIndex);
        result.setPageSize(pageSize);
        result.setDataList(vos);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
		
		return new ResponseMessage().addPagedResult(result);
	}

	@Override
	public ResponseMessage countRechargePlanList(RechargePlanVo search,
			int pageSize) throws Exception {
		return new ResponseMessage().addPagedResult(rechargeService.countRechargePlanList(search, pageSize));
	}

	@Override
	public ResponseMessage getRechargeRecord(Integer days,Date dstart,Date dend,Integer rcid,String mobile,String rcname,String company,Integer vtype,Integer vstate, int pageIndex,int pageSize,boolean manual,Integer utype) throws Exception {
		
		int total = rechargeService.countRechargeRecord(days, dstart,dend, rcid, mobile,rcname,company,vtype, vstate, pageSize,manual,utype).getTotal();
 		List<RechargeRecordVo> rechargeRecord = rechargeService.getRechargeRecord(days, dstart,dend, rcid, mobile,rcname,company,vtype,vstate, pageIndex, pageSize,manual,utype);
		List<com.lili.student.model.RechargeRecordVo> vos = new ArrayList<com.lili.student.model.RechargeRecordVo>();
		if(rechargeRecord != null && rechargeRecord.size() > 0){
			for(RechargeRecordVo vo : rechargeRecord){
				com.lili.student.model.RechargeRecordVo _vo = new com.lili.student.model.RechargeRecordVo();
				try {
					BeanCopy.copyAll(vo, _vo);
					User updateUser = cmsUserService.findById(vo.getMuid());
					User createUser = cmsUserService.findById(vo.getCuid());
					_vo.setCreateUser(createUser.getUserName());
					_vo.setCreateUser(updateUser.getUserName());
					vos.add(_vo);
				} catch (Exception e) {
					access.error("||| exception happends : " + e.getMessage());
					vos.add(_vo);
				}
			}
		}
		

		PagedResult<com.lili.student.model.RechargeRecordVo> result = new PagedResult<com.lili.student.model.RechargeRecordVo>();
        result.setPageNo(pageIndex);
        result.setPageSize(pageSize);
        result.setDataList(vos);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
		
		return new ResponseMessage().addPagedResult(result);
	}


	@Override
	public ResponseMessage addVipCustom(VipCustomVo vipCustomVo, boolean canOld, User user) throws Exception {
		vipCustomVo.setCuid(user.getId());
		vipCustomVo.setMuid(user.getId());
		String code = rechargeService.addVipCustom(vipCustomVo, canOld);
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage exportCustomer() {
		return null;
	}

	@Override
	public ResponseMessage addRechargeRecord(RechargeRecordVo vo,User user) throws Exception {
		access.info("**************************************** vo : " + vo.getRecharge());
		vo.setCtime(DateUtil.now());
		vo.setCuid(user.getId());
		vo.setMuid(user.getId());
		String code = rechargeService.addManualRechargeRecord(vo, user.getId());
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage modifyRechargePlan(RechargePlanVo plan,List<RechargeGearVo> rechargeGearList, User user) throws Exception {

		List<RechargeGearVo> newRechargeGearList = new ArrayList<RechargeGearVo>();
		StringBuffer couponName = null;
		StringBuffer couponId  = null;
		StringBuffer couponNum  = null;
		CouponList couponInfo = null;
		String couponNameOne = null;
		for (RechargeGearVo rechargeGearVo : rechargeGearList) {
			List<CouponList> couponList = rechargeGearVo.getCouponList();
			couponId = new StringBuffer();
			couponNum = new StringBuffer();
			couponName = new StringBuffer();
			if (couponList != null && couponList.size() > 0) {
				for (int i = 0; i < couponList.size(); i ++) {
					couponInfo = couponList.get(i);
					couponNameOne = cmsCouponService.queryCouponName(couponInfo.getCouponId());
					couponName.append(couponNameOne);
					couponId.append(couponInfo.getCouponId());
					couponNum.append(couponInfo.getCouponNum());
					if (i != couponList.size() -1) {
						couponId.append("|");
						couponNum.append("|");
						couponName.append("|");
					}
					rechargeGearVo.setCouponId(couponId.toString());
					rechargeGearVo.setCouponNum(couponNum.toString());
					rechargeGearVo.setCouponName(couponName.toString());
				}
			}
			else {
				rechargeGearVo.setCouponId(null);
				rechargeGearVo.setCouponNum(null);
				rechargeGearVo.setCouponName(null);
			}
			newRechargeGearList.add(rechargeGearVo);
		}
		
		if (plan.getRercid() != null && !"".equals(plan.getRercid())) {
			plan.setIsExitRercid(1);
		}
		if (plan.getCouponTemplate() != null && !"".equals(plan.getCouponTemplate())) {
			plan.setCouponNumber("1");
		}
		plan.setRechargeGearList(newRechargeGearList);
		String code = rechargeService.modifyRechargePlan(plan, user.getId());
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage passRechargeRecord(String rcids,long muid) throws Exception {
		String[] idArr = rcids.split(",");
		List<Integer> idList = new LinkedList<Integer>();
		if(idArr != null && idArr.length > 0){
			for(String id : idArr){
				idList.add(Integer.valueOf(id));
			}
		}
		
		String code = rechargeService.passRechargeRecord(idList,muid);
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}

	}

	@Override
	public ResponseMessage refuseRechargeRecord(String rcids, String reason,User user)
			throws Exception {
		String[] idArr = rcids.split(",");
		List<Integer> idList = new LinkedList<Integer>();
		if(idArr != null && idArr.length > 0){
			for(String id : idArr){
				idList.add(Integer.valueOf(id));
			}
		}
		
		String code = rechargeService.refuseRechargeRecord(idList,reason, user.getId());
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}

	}

	@Override
	public ResponseMessage getVipCompany(VipCompanyVo search) throws Exception {
		List<VipCompanyVo> vipCompanyList = rechargeService.getVipCompanyList(search,1,1);

        if(vipCompanyList != null && vipCompanyList.size() > 0)
        	return new ResponseMessage().addPagedResult(vipCompanyList.get(0));
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage addVipCustom(Student student, User user,long cuid) throws Exception {
		String code = rechargeService.addVipCustom(student, cuid);
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}
	
	@Override
	public ResponseMessage addBatchVipCustom(String studentIds, String mobiles, User currentUser) {
		
		try {
			String[] studentId = studentIds.split(",");
			String[] mobile = mobiles.split(",");
			if(mobile.length != studentId.length){
				return new ResponseMessage(MessageCode.MSG_REQUEST);
			}
			VipCustomVo vo = null;
			List<String> msgList = new ArrayList<String>();
			if(studentId != null && studentId.length > 0){
				for(int i =0; i< studentId.length; i++){
					vo = new VipCustomVo();
					vo.setStudentId(Long.parseLong(studentId[i]));
					vo.setMobile(mobile[i]);
					String code = rechargeService.addVipCustom(vo, false);
					if(!ResultCode.ERRORCODE.SUCCESS.equals(code)){
						msgList.add("添加已有学员：" + mobile[i] + " 错误码:" + code + "<br/>");
						continue;
					}
				}
			}
			
			StringBuffer errorMsg = new StringBuffer();
			if(msgList.size() > 0){
				for(String msg : msgList){
					errorMsg.append(msg);
				}
				return new ResponseMessage(errorMsg.toString());
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseMessage(0, MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage upload(File file,String fileName,User user) throws Exception {

		// 解析文件
		//创建Excel工作簿对象	
		access.info("||| upload now");
		Workbook wb = null; 
		boolean isE2007 = false;
		  DecimalFormat df = new DecimalFormat("0");
		long userId = user.getId();
		//List<Student> vos = new ArrayList<Student>();
		List<String> msgList = new ArrayList<String>();
		if (fileName .endsWith("xlsx"))  {
			isE2007  = true;  
		}
		FileInputStream fis = null;
		Integer exportId = 0 ;
		Integer sum = 0 ;
		Integer validSum = 0 ;
		//根据文件格式(2003或者2007)来初始化  
		try {
			fis = new FileInputStream(file);
			if(isE2007)  {
				wb = new XSSFWorkbook(fis);  
			}
			else  {
				wb = new HSSFWorkbook(fis);  
			}

			Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
			Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器 
			
			List<VipCustomExportDetail> vipDetailList = new ArrayList<VipCustomExportDetail>();
			VipCustomExportDetail vipCustomExportDetail = null;
			VipCustomExport vipCustomExport = new VipCustomExport();
			vipCustomExport.setFileName(file.getName());
			vipCustomExport.setCreator(user.getAccount());
			
			while (rows.hasNext()) {  
				Row row = rows.next();  //获得行数据  
				if (row.getRowNum() >= 2) {
                    if(row.getRowNum() == 2){
                    	//插入主表
            			cmsVipManager.insertVipCustomExport(vipCustomExport );
            			VipCustomExport vipInfo = cmsVipManager.queryVipCustomExport(vipCustomExport);
            			exportId = vipInfo.getId();
                    }
					access.info("Row #" + row.getRowNum());  //获得行号从0开始  
					Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  

					String name = null;
					String sex = null;
					String city = null;
					String idNumber = null;
					String phoneNum = null;
					String rcname = null;
					String companyName = null;
					String cid = null;
					while (cells.hasNext()) {
						Cell cell = cells.next();  
						access.info("Cell #" + cell.getColumnIndex());  
						switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
				          case HSSFCell.CELL_TYPE_NUMERIC:  
				              	if(cell.getColumnIndex() == 0){
									name = String.valueOf(df.format(cell.getNumericCellValue()));
								}else if(cell.getColumnIndex() == 1){
									sex = String.valueOf(df.format(cell.getNumericCellValue()));
								}else if(cell.getColumnIndex() == 2){
									city = String.valueOf(df.format(cell.getNumericCellValue()));
								}else if(cell.getColumnIndex() == 3){
									idNumber = String.valueOf(df.format(cell.getNumericCellValue()));
								}else if(cell.getColumnIndex() == 4){
									phoneNum = String.valueOf(df.format(cell.getNumericCellValue()));
								}else if(cell.getColumnIndex() == 5){
									rcname = String.valueOf(df.format(cell.getNumericCellValue()));
								}else if(cell.getColumnIndex() == 6){
									companyName = String.valueOf(df.format(cell.getNumericCellValue()));
								} else if(cell.getColumnIndex() == 7){
									cid = String.valueOf(df.format(cell.getNumericCellValue()));
								}
				              break;  
				          case HSSFCell.CELL_TYPE_STRING:  
				        		if(cell.getColumnIndex() == 0){
									name = cell.getStringCellValue();
								}else if(cell.getColumnIndex() == 1){
									sex = cell.getStringCellValue();
								}else if(cell.getColumnIndex() == 2){
									city = cell.getStringCellValue();
								}else if(cell.getColumnIndex() == 3){
									idNumber = cell.getStringCellValue();
								}else if(cell.getColumnIndex() == 4){
									phoneNum = cell.getStringCellValue();
								}else if(cell.getColumnIndex() == 5){
									rcname = cell.getStringCellValue();
								}else if(cell.getColumnIndex() == 6){
									companyName = cell.getStringCellValue();
								} else if(cell.getColumnIndex() == 7){
									cid = cell.getStringCellValue();
								}
				              break;  
				          case HSSFCell.CELL_TYPE_BOOLEAN:  
				              break;  
				          case HSSFCell.CELL_TYPE_FORMULA:  // 公式
				              break;  
				          default:  
				          break;  
				          }  
				      }


						if(StringUtil.isNullString(phoneNum) || StringUtil.isNullString(rcname) || StringUtil.isNullString(companyName)){
							return new ResponseMessage(" 第 " + (row.getRowNum() + 1) + " 行 手机号|冲送方案名|大客户全称 都不能为空");
						}
						

						Region region = cmsRegionService.findByName(city);
						

						/**
						 * 获取大客户信息
						 */
						VipCompanyVo search = new VipCompanyVo();
						search.setCompany(companyName);
						List<VipCompanyVo> vipCompanyList = rechargeService.getVipCompanyList(search,1,1);
						Integer coid = null;
						Integer rcid = null;
						if(vipCompanyList != null && vipCompanyList.size() > 0){
							coid = vipCompanyList.get(0).getCoid();
							if(coid == 4){ //普惠的限制导入
								return new ResponseMessage(MessageCode.EXCEL_EXPORT_PH);
							}
						}else {
							access.error(" 通过公司全称:" + companyName + " 未能找到对应大客户");
							continue;
						}

						/**
						 * 获取充值送方案
						 */
						RechargePlanVo rechargePlanVo = new RechargePlanVo();
						rechargePlanVo.setRcname(rcname);
						List<RechargePlanVo> rechargePlanList = rechargeService.getRechargePlanList(rechargePlanVo, 1,1);
						if(rechargePlanList != null && rechargePlanList.size() > 0){
							RechargePlanVo _vo = rechargePlanList.get(0);
							rcid = _vo.getRcid();
						}else {
							msgList.add(" 通过充值送方案名:" + rcname + " 未能找到对应数据");
							continue;
						}

						/**
						 * 新增学员之前先查看学员是否存在,不存在则插入,存在则跳过这步
						 */
						com.lili.student.model.Student student = cmsStudentService.findByPhone(phoneNum);
						Student _student = new Student();

						if(student == null){
							_student.setName(name);
							_student.setSex((byte)("女".equals(sex)?0:1));
							if(region != null){
								_student.setCityId(region.getRid());
							}else {
								msgList.add("为根据城市名：" + city + " 找到对应城市<br/>");
								continue;
							}
							_student.setPhoneNum(phoneNum);
							_student.setIdNumber(idNumber);
							_student.setVipId(coid);
							_student.setVipPackageId(rcid+"");
							
							vipCustomExportDetail = new VipCustomExportDetail();
							vipCustomExportDetail.setCname(name);
							vipCustomExportDetail.setSex(("女".equals(sex)?0:1));
							vipCustomExportDetail.setMobile(phoneNum);
							vipCustomExportDetail.setIdNumber(idNumber);
							vipCustomExportDetail.setCity(region.getRid());
							vipCustomExportDetail.setRcname(rcname);
							vipCustomExportDetail.setCityName(city);
							vipCustomExportDetail.setCompany(companyName);
							vipCustomExportDetail.setExportId(exportId);
							vipCustomExportDetail.setCoid(coid);
							vipCustomExportDetail.setRcid(rcid);
							vipCustomExportDetail.setCid(cid);
							vipDetailList.add(vipCustomExportDetail);
							
							
							/*String code = rechargeService.addVipCustom(_student, userId);
							if(!ResultCode.ERRORCODE.SUCCESS.equals(code)){
								msgList.add("插入新学员：" + name + " 错误码:" + code + "<br/>");
								continue;
							}*/
							
						}else {

							VipCustomVo vo = new VipCustomVo();
							vo.setStudentId(student.getStudentId());
							vo.setCoid(coid);
							vo.setRcid(rcid);
							vo.setCuid(userId);
							vo.setCname(name);
							
							
							
							student.setName(name);
							student.setIdNumber(idNumber);
							if(region != null){
								student.setCityId(region.getRid());
							}else {
								msgList.add("为根据城市名：" + city + " 找到对应城市<br/>");
								continue;
							}
							
							vipCustomExportDetail = new VipCustomExportDetail();
							vipCustomExportDetail.setCname(name);
							vipCustomExportDetail.setSex(("女".equals(sex)?0:1));
							vipCustomExportDetail.setMobile(phoneNum);
							vipCustomExportDetail.setIdNumber(idNumber);
							vipCustomExportDetail.setCity(region.getRid());
							vipCustomExportDetail.setRcname(rcname);
							vipCustomExportDetail.setCompany(companyName);
							vipCustomExportDetail.setExportId(exportId);
							vipCustomExportDetail.setCoid(coid);
							vipCustomExportDetail.setCityName(city);
							vipCustomExportDetail.setRcid(rcid);
							vipCustomExportDetail.setCid(cid);
							vipDetailList.add(vipCustomExportDetail);
						}
						
				}
			}
			sum = vipDetailList.size();
			validSum = vipDetailList.size();
			vipCustomExport.setSum(vipDetailList.size());
			vipCustomExport.setValidSum(vipDetailList.size());
			vipCustomExport.setId(exportId);
			cmsVipManager.updateVipCustomExport(vipCustomExport);
			cmsVipManager.insertVipCustomExportDetail(vipDetailList );
			
			
		
		} catch (Exception e) {
		      return new ResponseMessage("错误:" + e.getMessage());
		}
        finally {
        	try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		StringBuffer errorMsg = new StringBuffer();
		if(msgList.size() > 0){
			for(String msg : msgList){
				errorMsg.append(msg);
			}

			return new ResponseMessage(errorMsg.toString());
		}
		
		return new ResponseMessage()
				.addResult("sum",sum)
				.addResult("validSum", validSum)
				.addResult("id", exportId);
	}

	@Override
	public List<com.lili.student.model.RechargeRecordVo> getExportRechargeRecord(
			Integer days, Date dstart, Date dend, Integer rcid, String mobile,
			String rcname, String company, Integer vtype,Integer vstate, int pageIndex,
			int pageSize, boolean manual) throws Exception {
		
		List<RechargeRecordVo> rechargeRecord = rechargeService.getRechargeRecord(days, dstart,dend, rcid, mobile,rcname,company,vtype,vstate,1, 1000,manual,null);
		List<com.lili.student.model.RechargeRecordVo> vos = new ArrayList<com.lili.student.model.RechargeRecordVo>();
		if(rechargeRecord != null && rechargeRecord.size() > 0){
			for(RechargeRecordVo vo : rechargeRecord){
				com.lili.student.model.RechargeRecordVo _vo = new com.lili.student.model.RechargeRecordVo();
				try {
					BeanCopy.copyAll(vo, _vo);
					User updateUser = cmsUserService.findById(vo.getMuid());
					User createUser = cmsUserService.findById(vo.getCuid());
					_vo.setCreateUser(createUser.getUserName());
					_vo.setCreateUser(updateUser.getUserName());
					vos.add(_vo);
				} catch (Exception e) {
					access.error("||| exception happends : " + e.getMessage());
					vos.add(_vo);
				}
			}
		}
		
		return vos;
	}

	@Override
	public ResponseMessage passManualRechargeRecord(String rrids, long muid) throws Exception {
		String[] idArr = rrids.split(",");
		List<Integer> idList = new LinkedList<Integer>();
		if(idArr != null && idArr.length > 0){
			for(String id : idArr){
				idList.add(Integer.valueOf(id));
			}
		}
		
		String code = rechargeService.passManualRechargeRecord(idList,muid);
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage refuseManualRechargeRecord(String rrids,
			String reason, long muid) throws Exception {
		String[] idArr = rrids.split(",");
		List<Integer> idList = new LinkedList<Integer>();
		if(idArr != null && idArr.length > 0){
			for(String id : idArr){
				idList.add(Integer.valueOf(id));
			}
		}
		
		String code = rechargeService.refuseManualRechargeRecord(idList,reason,muid);
		if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
			return new ResponseMessage();
		}else {
			return new ResponseMessage("错误码 : " + code +  ResultCode.getCodeInfo(code));
		}
	}

	@Override
	public ResponseMessage customDelete(VipCustomVo vo, User user) throws Exception {
		try {
			rechargeService.deleteRechargePlan(vo.getStudentId(), user.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage submit(VipCustomExport vipCustomExport, long id) {
		try {
			Student _student = new Student();
			List<String> msgList = new ArrayList<String>();
			List<VipCustomExportDetail> vipCustomDetailList = cmsVipManager.queryVipCustomDetailList(vipCustomExport);
			for (VipCustomExportDetail vipCustomExportDetail : vipCustomDetailList) {
				_student.setName(vipCustomExportDetail.getCname());
				_student.setSex(vipCustomExportDetail.getSex().byteValue());
				_student.setCityId(vipCustomExportDetail.getCity());
				_student.setPhoneNum(vipCustomExportDetail.getMobile());
				_student.setIdNumber(vipCustomExportDetail.getIdNumber());
				_student.setVipId(vipCustomExportDetail.getCoid());
				_student.setVipPackageId(String.valueOf(vipCustomExportDetail.getRcid()));
				_student.setCid(vipCustomExportDetail.getCid());
				
				String code = rechargeService.addVipCustom(_student, Long.valueOf(id));
				if(!ResultCode.ERRORCODE.SUCCESS.equals(code)){
					msgList.add("插入新学员：" + vipCustomExportDetail.getCname() + " 错误码:" + code + "<br/>");
					continue;
				}
			}
			cmsVipManager.submit(vipCustomExport);
			
			StringBuffer errorMsg = new StringBuffer();
			if(msgList.size() > 0){
				for(String msg : msgList){
					errorMsg.append(msg);
				}

				return new ResponseMessage(errorMsg.toString());
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}

		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage giveUp(VipCustomExport vipCustomExport) {
		try {
			cmsVipManager.giveUp(vipCustomExport);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}

		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public String customExport(VipCustomExport vo) {
		String resp =null;
		try {
			PagedResult<VipCustomExport>  vipList = cmsVipManager.queryVipCustomExportList(vo);
			resp = new ResponseMessage() .addResult("code", 0)
					.addResult("pageData", vipList)
					.build();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public String customExportDetail(VipCustomExportDetail vo) {
		String resp =null;
		try {
			PagedResult<VipCustomExportDetail>  vipList = cmsVipManager.queryVipCustomExportDetailList(vo);
			resp = new ResponseMessage() .addResult("code", 0)
					.addResult("pageData", vipList)
					.build();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	
	
}
