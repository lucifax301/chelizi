package com.lili.student.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.student.dto.Student;
import com.lili.student.model.StudentVo;
import com.lili.student.model.VipCustomExport;
import com.lili.student.model.VipCustomExportDetail;
import com.lili.student.vo.RechargeGearVo;
import com.lili.student.vo.RechargePlanVo;
import com.lili.student.vo.RechargeRecordVo;
import com.lili.student.vo.VipCompanyVo;
import com.lili.student.vo.VipCustomVo;
import com.lili.user.model.User;

/**
 * 大客户及大客户员工相关业务
 * @author hughes
 *
 */
public interface CMSVipService {


	/**
	 * 插入大客户学员数据
	 * @param file
	 * @param creator
	 * @return
	 * @throws Exception
	 */
	public  ResponseMessage upload(File file,String fileName,User user) throws Exception;
	
	/**
	 * 审核通过充值送记录
	 * @param rcids
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage passRechargeRecord(String rrids,long muid) throws Exception;

/**
	 * 审核拒绝充值送记录
	 * @param rcids
	 * @param reason
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage refuseRechargeRecord(String rcids,String reason, User user) throws Exception;
	
	/**
	 * 导出大客户学员Excel
	 * @return
	 */
	public ResponseMessage exportCustomer() throws Exception;
	
	/**
	 * 扫码部分：大客户录入，有几种情况： 1.已是大客户学员 2.是已注册学员，但非vip 3.是未注册学员
	 * canOld: 是否可以将老客户标记为大客户,我们这里写true
	 * @param user 
	 */
	public ResponseMessage addVipCustom(VipCustomVo vipCustomVo,boolean canOld, User user) throws Exception;
	

	/**
	 * 扫码部分：大客户录入，有几种情况： 1.已是大客户学员 2.是已注册学员，但非vip 3.是未注册学员
	 * canOld: 是否可以将老客户标记为大客户,我们这里写true
	 * @param user 
	 */
	public ResponseMessage addVipCustom(Student student, User user,long cuid) throws Exception;
	
	/**
	 * 新建大客户公司
	 * @param company
	 * @param user 
	 * @return
	 */
	public ResponseMessage createVipCompany(VipCompanyVo company, User user) throws Exception;
	

	/**
	 * 财务通过审核
	 * @param rrids
	 * @param muid
	 * @return
	 * @throws Exception 
	 */
	public ResponseMessage passManualRechargeRecord(String rrids,long muid) throws Exception;

	/**
	 * 财务审核拒绝
	 * @param rrids
	 * @param muid
	 * @return
	 */
	public ResponseMessage refuseManualRechargeRecord(String rrids,String reason,long muid) throws Exception;
	
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
	public ResponseMessage getRechargeRecord(Integer days,Date dstart,Date dend,Integer rcid,String mobile,String rcname,String company,Integer vtype,Integer vstate, int pageIndex,int pageSize,boolean manual,Integer utype) throws Exception;
	

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
	public List<com.lili.student.model.RechargeRecordVo> getExportRechargeRecord(Integer days,Date dstart,Date dend,Integer rcid,String mobile,String rcname,String company,Integer vtype,Integer vstate,int pageIndex,int pageSize,boolean manual) throws Exception;

/**
	 * 扫码部分： 获取大客户公司信息列表，用于用户选择自己的公司,可以根据城市等条件查询 
	 * 另外隐藏：我们需要先创建2个套餐，并将其id隐藏在页面中
	 * 注意：如果每个公司一个二维码，那么该接口也可以不使用，而将companyId隐藏其中
	 */

	public ResponseMessage getVipCompanyList(VipCompanyVo search,int pageSize, int pageIndex) throws Exception;
	
	
	public ResponseMessage getVipCompany(VipCompanyVo search) throws Exception;
	
	/**
	 * 计算大客户公司分页(第一页数据,只在获取第一页数据的时候查询次总数)
	 * @param search
	 * @param pageSize
	 * @return
	 */
	public ResponseMessage countVipCompany(VipCompanyVo search,int pageSize) throws Exception;
	
	/**
	 * 获取大客户公司分页数据(第一页之后的数据)
	 */
	public ResponseMessage getCustomList(VipCustomVo search,int pageSize, int pageIndex,Boolean vipBig) throws Exception;
	/**
	 * 导出大客户公司用户数据
	 */
	public List<StudentVo> getExportCustomList(VipCustomVo search) throws Exception;
	
	/**
	 * 获取大客户公司下员工的分页
	 * @param search
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage countCustomList(VipCustomVo search,int pageSize) throws Exception;
	
	/**
	 * 审核大客户员工通过
	 */
	public ResponseMessage passCustom(String studentIds, User user) throws Exception;


	/**
	 * 审核大客户员工不通过
	 */
	public ResponseMessage refuseCustom(String studentIds,String reason, User user) throws Exception;
	

	/**
	 * 激活充送方案
	 */
	public ResponseMessage activeRechargePlan(String ids, User user) throws Exception;


	/**
	 * 停用充送方案
	 */
	public ResponseMessage inactiveRechargePlan(String ids, User user) throws Exception;
	
	/**
	 * 查看充值送套餐详情
	 * @param user 
	 */
	public ResponseMessage getRechargePlan(Integer rcid, User user) throws Exception;
	

	/**
	 * 添加套餐
	 * @param plan
	 * @param user 
	 * @return
	 */
	public ResponseMessage addRechargePlan(RechargePlanVo plan, List<RechargeGearVo> rechargeGearList, User user) throws Exception;
	
	/**
	 * 激活大客户公司
	 * @param coid
	 * @return
	 */
	public ResponseMessage activeVipCompany(String coids, User user) throws Exception;

/**
	 * 停用大客户公司, 此时公司下已审核通过的员工如何处理？
	 * @param coid
	 * @return
	 */
	public ResponseMessage inactiveVipCompany(String coids, User user) throws Exception;

/**
	 * 修改大客户信息，其中状态不能修改。
	 * @param vipCompany
	 * @return
	 */
	public ResponseMessage modifyVipCompany(VipCompanyVo vipCompany, User user) throws Exception;
	

/**
	 * 获取充送方案列表
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage getRechargePlanList(RechargePlanVo search,int pageSize,int pangeIndex) throws Exception;


	/**
	 * 获取充送方案分页
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage countRechargePlanList(RechargePlanVo search,int pageSize) throws Exception;

	/**
	 * 增加手工调整学员余额的接口
	 * @param record
	 * @return
	 */
	public ResponseMessage addRechargeRecord(RechargeRecordVo vo,User user) throws Exception;
	

	/**
	 * 通用修改方案
	 * @param plan
	 * @return
	 */
	public ResponseMessage modifyRechargePlan(RechargePlanVo plan,List<RechargeGearVo> rechargeGearList,User user) throws Exception;

	List<com.lili.student.model.VipCompanyVo> getExportVipCompanyList(
			VipCompanyVo search, int pageSize, int pageIndex) throws Exception;

	public ResponseMessage customDelete(VipCustomVo vo,User user) throws Exception;

	/**
	 * 确认提交
	 * @param vipCustomExport
	 * @param id 
	 * @return
	 */
	public ResponseMessage submit(VipCustomExport vipCustomExport, long id);

	/**
	 * 放弃提交
	 * @param vipCustomExport
	 * @return
	 */
	public ResponseMessage giveUp(VipCustomExport vipCustomExport);

	/**
	 * 导入主表查询
	 * @param vo
	 * @return
	 */
	public String customExport(VipCustomExport vo);

	/**
	 * 导入明细查询
	 * @param vo
	 * @return
	 */
	public String customExportDetail(VipCustomExportDetail vo);

	public ResponseMessage addBatchVipCustom(String studentIds, String mobiles, User currentUser);
	
}
