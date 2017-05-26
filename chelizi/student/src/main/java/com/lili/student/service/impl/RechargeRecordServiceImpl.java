package com.lili.student.service.impl;

import java.util.List;
import java.util.Date;

import com.lili.common.util.BeanCopy;
import com.lili.student.vo.RechargeRecordQuery;
import com.lili.student.vo.RechargeRecordVo;
import com.lili.student.dao.mapper.RechargeRecordMapper;
import com.lili.student.dao.po.RechargeRecordPo;
import com.lili.student.service.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RechargeRecordServiceImpl implements RechargeRecordService {

	@Autowired
	RechargeRecordMapper rechargerecordMapper;;
	public void addRechargeRecord(RechargeRecordVo rechargerecordVo) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		rechargerecordMapper.addRechargeRecord(po);
	}
	public void addRechargeRecordList(List<RechargeRecordVo> rechargerecordVoList) throws Exception {
		List<RechargeRecordPo> poList=BeanCopy.copyList(rechargerecordVoList,RechargeRecordPo.class,BeanCopy.COPYALL);
		rechargerecordMapper.addRechargeRecordList(poList);
	}
	public void delRechargeRecordById(Integer rrid) throws Exception {
		rechargerecordMapper.delRechargeRecordById(rrid);
	}
	public void delRechargeRecordByIds(List<Integer> ids) throws Exception {
		rechargerecordMapper.delRechargeRecordByIds(ids);
	}
	public void delRechargeRecordByObj(RechargeRecordVo rechargerecordVo) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		rechargerecordMapper.delRechargeRecordByObj(po);
	}
	public void saveRechargeRecord(RechargeRecordVo rechargerecordVo) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		rechargerecordMapper.saveRechargeRecord(po);
	}
	public void saveRechargeRecordList(List<RechargeRecordVo> rechargerecordVoList) throws Exception {
		List<RechargeRecordPo> poList=BeanCopy.copyList(rechargerecordVoList,RechargeRecordPo.class,BeanCopy.COPYALL);
		rechargerecordMapper.saveRechargeRecordList(poList);
	}
	public int updateByRrid(RechargeRecordVo rechargerecordVo,Integer rrid) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByRrid(po,rrid);
	}
	public int updateByStudentId(RechargeRecordVo rechargerecordVo,Long studentId) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByStudentId(po,studentId);
	}
	public int updateByName(RechargeRecordVo rechargerecordVo,String name) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByName(po,name);
	}
	public int updateByMobile(RechargeRecordVo rechargerecordVo,String mobile) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByMobile(po,mobile);
	}
	public int updateByCompany(RechargeRecordVo rechargerecordVo,String company) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByCompany(po,company);
	}
	public int updateByVtype(RechargeRecordVo rechargerecordVo,Integer vtype) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByVtype(po,vtype);
	}
	public int updateByCharge(RechargeRecordVo rechargerecordVo,Integer charge) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByCharge(po,charge);
	}
	public int updateByRecharge(RechargeRecordVo rechargerecordVo,Integer recharge) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByRecharge(po,recharge);
	}
	public int updateByWaterId(RechargeRecordVo rechargerecordVo,String waterId) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByWaterId(po,waterId);
	}
	public int updateByRcid(RechargeRecordVo rechargerecordVo,Integer rcid) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByRcid(po,rcid);
	}
	public int updateByRcname(RechargeRecordVo rechargerecordVo,String rcname) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByRcname(po,rcname);
	}
	public int updateByPayWay(RechargeRecordVo rechargerecordVo,String payWay) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByPayWay(po,payWay);
	}
	public int updateByPayTime(RechargeRecordVo rechargerecordVo,Date payTime) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByPayTime(po,payTime);
	}
	public int updateByGetTime(RechargeRecordVo rechargerecordVo,Date getTime) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByGetTime(po,getTime);
	}
	public int updateByVstate(RechargeRecordVo rechargerecordVo,Integer vstate) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByVstate(po,vstate);
	}
	public int updateByReason(RechargeRecordVo rechargerecordVo,String reason) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByReason(po,reason);
	}
	public int updateByIsdel(RechargeRecordVo rechargerecordVo,Integer isdel) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(RechargeRecordVo rechargerecordVo,Long cuid) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(RechargeRecordVo rechargerecordVo,Long muid) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(RechargeRecordVo rechargerecordVo,Date ctime) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(RechargeRecordVo rechargerecordVo,String mtime) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		return rechargerecordMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(RechargeRecordVo rechargerecordVo,RechargeRecordVo search) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		RechargeRecordPo searchPo=BeanCopy.copyAll(search,RechargeRecordPo.class);
		return rechargerecordMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(RechargeRecordVo rechargerecordVo,RechargeRecordVo search) throws Exception {
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		RechargeRecordPo searchPo=BeanCopy.copyAll(search,RechargeRecordPo.class);
		return rechargerecordMapper.updateAllByObject(po,searchPo);
	}
	public RechargeRecordVo queryRechargeRecordById(Integer rrid) throws Exception {
		String postSql="";
		String sqlFileld="*";
		RechargeRecordPo po=rechargerecordMapper.queryRechargeRecordById(rrid,postSql,sqlFileld);
		RechargeRecordVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,RechargeRecordVo.class);
		}
		return vo;
	}
	public List<RechargeRecordVo> queryRechargeRecordByIds(List<Integer> ids,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryRechargeRecordByIds(ids,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByObjectAnd(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public Integer countByObject(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		Integer total=rechargerecordMapper.countByObject(po,postSql,sqlFileld);
		return total;
	}
	public List<RechargeRecordVo> queryByObjectOr(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByRrid(Integer rrid,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByRrid(rrid,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByStudentId(Long studentId,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByStudentId(studentId,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByName(String name,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByName(name,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByMobile(String mobile,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByMobile(mobile,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByCompany(String company,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByCompany(company,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByVtype(Integer vtype,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByVtype(vtype,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByCharge(Integer charge,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByCharge(charge,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByRecharge(Integer recharge,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByRecharge(recharge,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByWaterId(String waterId,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByWaterId(waterId,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByRcid(Integer rcid,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByRcid(rcid,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByRcname(String rcname,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByRcname(rcname,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByPayWay(String payWay,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByPayWay(payWay,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByPayTime(Date payTime,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByPayTime(payTime,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByGetTime(Date getTime,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByGetTime(getTime,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByVstate(Integer vstate,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByVstate(vstate,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByReason(String reason,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByReason(reason,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByIsdel(Integer isdel,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByCuid(Long cuid,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByMuid(Long muid,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByMuid(muid,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByCtime(Date ctime,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByMtime(String mtime,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByNew0(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByNew0(po,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByNew1(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByNew1(po,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByNew2(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByNew2(po,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByNew3(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByNew3(po,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByNew4(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByNew4(po,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByNew5(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByNew5(po,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByNew6(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByNew6(po,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeRecordVo> queryByNew7(RechargeRecordVo rechargerecordVo,RechargeRecordQuery rechargerecordQuery) throws Exception {
		String postSql=rechargerecordQuery.getSqlPost();
		String sqlFileld=rechargerecordQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeRecordPo po=BeanCopy.copyAll(rechargerecordVo,RechargeRecordPo.class);
		List<RechargeRecordPo> poList=rechargerecordMapper.queryByNew7(po,postSql,sqlFileld);
		List<RechargeRecordVo> voList=new ArrayList<RechargeRecordVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeRecordVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
