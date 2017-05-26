package com.lili.student.service;

import java.util.Date;
import java.util.List;

import com.lili.common.util.Page;
import com.lili.common.vo.ReqResult;
import com.lili.student.dto.Student;
import com.lili.student.vo.RechargePlanVo;
import com.lili.student.vo.RechargeRecordVo;
import com.lili.student.vo.VipCompanyQuery;
import com.lili.student.vo.VipCompanyVo;
import com.lili.student.vo.VipCustomVo;


public interface RechargeService {
	/**
	 * 扫码部分： 获取大客户公司信息列表，用于用户选择自己的公司,可以根据城市等条件查询 
	 * 另外隐藏：我们需要先创建2个套餐，并将其id隐藏在页面中
	 * 注意：如果每个公司一个二维码，那么该接口也可以不使用，而将companyId隐藏其中
	 */
	public List<VipCompanyVo> getVipCompanyList(VipCompanyVo search,int pageSize, int pageIndex)  throws Exception;
	/**
	 * 计算大客户公司分页
	 * @param search
	 * @param pageSize
	 * @return
	 */
	public Page<VipCompanyVo> countVipCompany(VipCompanyVo search,int pageSize)  throws Exception;
	/**
	 * 扫码部分：大客户录入，有几种情况： 1.已是大客户学员 2.是已注册学员，但非vip 3.是未注册学员
	 * canOld: 是否可以将老客户标记为大客户
	 */
	public String updateVipCustom(VipCustomVo vipCustomVo) throws Exception;
	
	/**
	 * CMS修改学员信息同步修改大客户表信息
	 * @param vipCustomVo
	 * @param canOld
	 * @return
	 * @throws Exception
	 */
	public String addVipCustom(VipCustomVo vipCustomVo,boolean canOld) throws Exception;
	
	/**
	 * 通用修改方案
	 * @param plan
	 * @return
	 */
	public String modifyRechargePlan(RechargePlanVo plan,long muid) throws Exception;
	/**
	 * 新建大客户公司
	 * @param company
	 * @return
	 */
	public String createVipCompany(VipCompanyVo company,long cuid) throws Exception;
	/**
	 * 获取大客户公司下的所有员工的一页
	 */
	public List<Student> getCustomList(VipCustomVo search,Boolean vipBig,int pageSize, int pageIndex) throws Exception;
	/**
	 * 获取大客户公司下员工的分页
	 * @param search
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page<Student> countCustomList(VipCustomVo search,Boolean vipBig,int pageSize) throws Exception;
	/**
	 * 审核大客户员工通过
	 */
	public String passCustom(List<Long> studentIds,long muid) throws Exception;
	/**
	 * 审核大客户员工不通过
	 */
	public String refuseCustom(List<Long> studentIds,String reason,long muid)  throws Exception;
	/**
	 * 查看充值送套餐详情
	 */
	public RechargePlanVo getRechargePlan(Integer rcid) throws Exception;
	/**
	 * 添加套餐
	 * @param plan
	 * @return
	 */
	public String addRechargePlan(RechargePlanVo plan,long cuid) throws Exception;
	/**
	 * 激活充送方案
	 */
	public String activeRechargePlan(int rcid,long muid) throws Exception;
	/**
	 * 停用充送方案
	 */
	public String inactiveRechargePlan(int rcid,long muid)  throws Exception;
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
	public List<RechargeRecordVo> getRechargeRecord(Integer days,Date dstart,Date dend,Integer rcid,String mobile,String rcname,String company,Integer vtype,Integer vstate,int pageIndex,int pageSize,boolean manual,Integer utype) throws Exception;
	
	ReqResult  getRechargePlanByRcid(Integer rcid,String rcname,String company,Integer vtype,Integer vstate) throws Exception;
	/**
	 * 计算优惠充值分页
	 * @param days
	 * @param date
	 * @param rcid
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public Page<RechargeRecordVo> countRechargeRecord(Integer days,Date dstart,Date dend,Integer rcid,String mobile,String rcname,String company,Integer vtype,Integer vstate,int pageSize,boolean manual,Integer utype) throws Exception;
	
	/**
	 * 获取充送方案列表
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public List<RechargePlanVo> getRechargePlanList(RechargePlanVo search,int pageSize,int pangeIndex) throws Exception;
	/**
	 * 获取充送方案分页
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public Page<RechargePlanVo> countRechargePlanList(RechargePlanVo search,int pageSize) throws Exception; 
	/**
	 * 激活大客户公司
	 * @param coid
	 * @return
	 */
	public String activeVipCompany(List<Integer> coids,long muid) throws Exception;
	/**
	 * 停用大客户公司, 此时公司下已审核通过的员工如何处理？
	 * @param coid
	 * @return
	 */
	public String inactiveVipCompany(List<Integer> coids,long muid)  throws Exception;
	/**
	 * 修改大客户信息，其中状态不能修改。
	 * @param vipCompany
	 * @return
	 */
	public String modifyVipCompany(VipCompanyVo vipCompany,long muid) throws Exception;
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
	public RechargePlanVo getRechargePlanByUserId(long studentId) throws Exception;
	/**
	 * 增加手工调整学员余额的接口
	 * @param record
	 * @return
	 */
	public String addManualRechargeRecord(RechargeRecordVo record,long muid) throws Exception;
	/**
	 * 财务通过审核
	 * @param rrids
	 * @param muid
	 * @return
	 */
	public String passManualRechargeRecord(List<Integer> rrids,long muid) throws Exception; 
	/**
	 * 财务审核拒绝
	 * @param rrids
	 * @param muid
	 * @return
	 */
	public String refuseManualRechargeRecord(List<Integer> rrids,String reason,long muid)  throws Exception;
	/**
	 * 学员充值金额成功后，计算赠送金额,并生成记录
	 * 对于自动赠送金额，则立马进行赠送，并更新记录到审核通过
	 * @param studentId
	 * @param charge
	 * @return
	 */
	public String recharge(RechargeRecordVo record)  throws Exception;
	/**
	 * 审核通过充值送记录
	 * @param rrids
	 * @return
	 * @throws Exception
	 */
	public String passRechargeRecord(List<Integer> rrids,long muid) throws Exception;
	/**
	 * 审核拒绝充值送记录
	 * @param rrids
	 * @param reason
	 * @return
	 * @throws Exception
	 */
	public String refuseRechargeRecord(List<Integer> rrids,String reason,long muid) throws Exception;
	/**
	 * 客服新增不存在的大客户
	 * @param student
	 * @param cuid
	 * @return
	 */
	public String addVipCustom(Student student,long cuid) throws Exception;
	/**
	 * 给大客户员工添加或更新套餐，注意已审核的不可变更
	 * @param studentId
	 * @param rcid
	 * @return
	 * @throws Exception
	 */
	public String selectRechargePlan(String mobile,int rcid) throws Exception;
	/**
	 * 删除学员当前的优惠方案：仅包括大客户和非通用的普惠方案。
	 * 如果通用普惠方案需要删除，请停用对应的普惠方案即可。
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public String deleteRechargePlan(long studentId,long muid) throws Exception ;
	
	/**
	 * 查询学员是否参与充值送活动
	 * @param userId
	 * @return
	 */
	public Integer queryRechargeRecord(long userId);
	
	public List<VipCompanyVo> queryByNew0(VipCompanyVo vipCompanyVo, VipCompanyQuery vipCompanyQuery);
}
