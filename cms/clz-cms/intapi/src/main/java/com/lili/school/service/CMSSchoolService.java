package com.lili.school.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.lili.cms.entity.ResponseMessage;
import com.lili.common.util.Page;
import com.lili.finance.vo.SchDeposit;
import com.lili.finance.vo.UserMoneyVo;
import com.lili.log.model.LogCommon;
import com.lili.school.dto.EnrollLongtrainStudent;
import com.lili.school.dto.EnrollTheory;
import com.lili.school.dto.EnrollTheoryStudent;
import com.lili.school.model.SchAccount;
import com.lili.school.model.School;
import com.lili.school.model.SchoolAccountApply;
import com.lili.school.model.SchoolBDTO;
import com.lili.school.model.SchoolExtend;
import com.lili.school.model.WechatEnrollPackage;
import com.lili.school.model.WechatEnrollPackageBDTO;

public interface CMSSchoolService {
	
	public  String findSchoolNameById(long schoolId) throws Exception;
	
	public  ResponseMessage findSchoolList(SchoolBDTO dto) throws Exception;
	
	public  ResponseMessage findSchoolArray(SchoolBDTO dto) throws Exception;
	
	public  ResponseMessage applyAccount(SchAccount schAccount, SchoolExtend schoolExtend) throws Exception;
	
	public  String queryAccount(SchoolExtend schoolExtend, String ymDate) throws Exception;
	
	public String deposit(Long schoolId,String money, String passwd) throws Exception ;
	
	public String queryDepositList(SchoolExtend schoolExtend) throws Exception ;
	
	public String queryDeposit(SchoolExtend schoolExtend) throws Exception ;
	
	public School findSchoolById(long schoolId) throws Exception ;
	
	public ResponseMessage queryPasswd(SchAccount dto) throws Exception ;
	
	public ResponseMessage alertPasswd(SchAccount dto) throws Exception ;
	
	public ResponseMessage alertMobile(SchAccount dto) throws Exception ;
	
	public ResponseMessage one(String mobile, String reqType) throws Exception ;

	public ResponseMessage verify(String mobile, String codeInput, Long schoolId) throws Exception ;

	public ResponseMessage depositVal(SchoolExtend schoolExtend) throws Exception ;

	public ResponseMessage closeRemark(SchoolExtend schoolExtend) throws Exception ;

	public List<SchDeposit> downLoadExcel(SchDeposit schdeposit) throws Exception ;

	public SchAccount getSchoolIdMoney(long schoolId) throws Exception ;

	public void addMoneyBack(int money, long schoolId) throws Exception ;

	public ResponseMessage queryBurse(SchoolExtend dto) throws Exception ;

	public ResponseMessage checkPass(String checker, String remark, String id, String schoolNos)throws Exception ;

	public ResponseMessage agreeChange(String checker, String remark, String id, String schoolNos)throws Exception ;

	public ResponseMessage checkReject(String checker, String remark, String id)throws Exception ;

	public ResponseMessage refuseChange(String checker, String remark, String id)throws Exception ;

	public ResponseMessage addSchool(LogCommon logCommon, SchoolBDTO dto)throws Exception ;

	public ResponseMessage updateSchool(LogCommon logCommon, SchoolBDTO dto)throws Exception ;
	
	public abstract Long querySchoolMoney()throws Exception ;
	
	public abstract Long queryLiliWalletMoney()throws Exception ;

	public ResponseMessage touchBalance(UserMoneyVo userMoneyVo)throws Exception ;

	public ResponseMessage accountBalance(UserMoneyVo userMoneyVo)throws Exception ;

	public ResponseMessage costDetail(UserMoneyVo userMoneyVo)throws Exception ;

	public ResponseMessage getTheoryBySchoolId(Long schoolId, String state, String pageNo, String pageSize, String dateBegin,
			String dateEnd,String createBegin,String createEnd)throws Exception ;
	
	public ResponseMessage getTheoryBySchoolId(List<Integer> schoolId, String state, String pageNo, String pageSize, String dateBegin,
			String dateEnd,String createBegin,String createEnd)throws Exception ;

	public ResponseMessage addTheory(Long schoolId, String classDate, String classTime, String className,
			String cardDesc, String contactName, String contactMobile, String classPlace)throws Exception ;

	public ResponseMessage modifyTheory(String theoryId, Long schoolId, String classDate, String classTime,
			String className, String cardDesc, String contactName, String contactMobile, String classPlace)throws Exception ;

	public ResponseMessage addTheoryStudent(Long schoolId, String theoryId, String studentIds)throws Exception ;

	public ResponseMessage deleteTheoryStudent(Long schoolId, String theoryId, String studentIds)throws Exception ;

	public ResponseMessage getTheoryStudent(Long schoolId, String theoryId, String pageNo, String pageSize,
			String state, String isImport)throws Exception ;

	public ResponseMessage getTheoryMsgInfo(String state)throws Exception ;

	public ResponseMessage changeTheoryClass(Long schoolId, String theoryId, String state)throws Exception ;

	public ResponseMessage changeTheoryStudentState(Long schoolId, String theoryId, String studentIds, String state)throws Exception ;

	public ResponseMessage getTheoryByTheoryId(Long schoolId, String theoryId)throws Exception ;
	
	public EnrollTheory getTheoryByTheoryId(String theoryId);

	public ResponseMessage getLongtrainCoach(Long schoolId, String contactMobile)throws Exception ;

	public ResponseMessage getLongtrainCar(Long schoolId, String carNo)throws Exception ;

	public ResponseMessage addLongtrain(Long schoolId, String contactMobile, String carNo, String classDate,
			String classTime, String classPlace, String carrys) throws Exception ;

	public ResponseMessage getLongtrain(Long schoolId, String state, String pageNo, String pageSize, String dateBegin,
			String dateEnd,String createBegin,String createEnd) throws Exception ;
	
	public ResponseMessage getLongtrain(List<Integer> schoolIds, String state, String pageNo, String pageSize, String dateBegin,
			String dateEnd,String createBegin,String createEnd) throws Exception ;

	public ResponseMessage getLongtrainOne(Long schoolId, String ltId) throws Exception ;

	public ResponseMessage modifyLongtrain(Long schoolId, String ltId, String contactMobile, String carNo,
			String classDate, String classTime, String classPlace, String carrys) throws Exception ;

	public ResponseMessage addLongtrainStudent(Long schoolId, String ltId, String studentIds) throws Exception ;

	public ResponseMessage deleteLongtrainStudent(Long schoolId, String ltId, String studentIds) throws Exception ;

	public ResponseMessage getLongtrainMsgInfo(String state) throws Exception ;

	public ResponseMessage changeLongtrainClass(Long schoolId, String ltId, String state) throws Exception ;

	public ResponseMessage changeLongtrainStudentState(Long schoolId, String ltId, String studentIds, String state) throws Exception ;

	public ResponseMessage getLongtrainStudent(Long schoolId, String ltId, String pageNo, String pageSize, String state,
			String isImport) throws Exception ;

	public ResponseMessage upload(File outfile, String tagFileName, Long schoolId, String creator, String type) throws Exception ;
	
	public ResponseMessage uploadNew(byte[] b, String tagFileName, Long schoolId, String creator, String type) throws Exception ;

	public ResponseMessage changeUpdateState(Long schoolId, String uuid, String state,String userName) throws Exception ;

	public ResponseMessage getUpdateTables(Long schoolId, String type, String pageNo, String pageSize, String dateBegin,
			String dateEnd) throws Exception ;

	public ResponseMessage getUpdateTablesOne(Long schoolId, String type, String pageNo, String pageSize,
			String tableNo) throws Exception ;

	public ResponseMessage getUpdateTablesOneInfo(Long schoolId, String tableNo) throws Exception ;

	public Long findSchoolIdByName(String schoolName) throws Exception;


	public Page<EnrollTheoryStudent> getTheoryStudentPage(Long schoolId, String theoryId, String pageNo,
			String pageSize, String state, String isImport) throws Exception ;

	public Page<EnrollLongtrainStudent> getLongtrainStudentPage(Long schoolId, String ltId, String pageNo,
			String pageSize, String state, String isImport) throws Exception ;

	public ResponseMessage getUpdateResult(Long schoolId, String uuid) throws Exception ;
	
	public ResponseMessage findWxSchoolList(SchoolBDTO school) throws Exception;
	
	public ResponseMessage findWxSchoolById(int schoolId) throws Exception;

	public ResponseMessage addWxSchool(SchoolBDTO dto) throws Exception ;

	public ResponseMessage updateWxSchool(SchoolBDTO dto) throws Exception ;
	
	public  ResponseMessage findPackageList(WechatEnrollPackageBDTO wBdto) throws Exception;
	
	public  WechatEnrollPackage findPackageById(String ttid) throws Exception;
	
	public ResponseMessage addPackage(WechatEnrollPackageBDTO dto) throws Exception ;

	public ResponseMessage updatePackage(WechatEnrollPackageBDTO dto) throws Exception ;
	
	public ResponseMessage updatePackageState(WechatEnrollPackageBDTO dto,String ttids);
	
	public ResponseMessage addPackageStudent(int studentId,int ttid) throws Exception ;
	
	/*
	 * add 2016-08-22
	 */
	public ResponseMessage applySchoolAccount(SchoolAccountApply apply) throws Exception;
	
	public ResponseMessage findApplySchool(SchoolAccountApply apply) throws Exception;
	
	public ResponseMessage auditSchool(SchoolAccountApply apply) throws Exception;
	
	public ResponseMessage unauditSchool(SchoolAccountApply apply) throws Exception;

	public List<School> findAllSchool(SchoolBDTO dto) throws Exception;
}
