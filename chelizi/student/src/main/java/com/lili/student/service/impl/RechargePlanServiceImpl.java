package com.lili.student.service.impl;

import java.util.List;

import com.lili.common.util.BeanCopy;
import com.lili.student.vo.RechargePlanVo;
import java.util.Date;
import com.lili.student.vo.RechargePlanQuery;
import com.lili.student.dao.mapper.RechargePlanMapper;
import com.lili.student.dao.po.RechargePlanPo;
import com.lili.student.service.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RechargePlanServiceImpl implements RechargePlanService {

	@Autowired
	RechargePlanMapper rechargePlanMapper;;
	public void addRechargePlan(RechargePlanVo rechargePlanVo) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		rechargePlanMapper.addRechargePlan(po);
	}
	public void addRechargePlanList(List<RechargePlanVo> rechargePlanVoList) throws Exception {
		List<RechargePlanPo> poList=BeanCopy.copyList(rechargePlanVoList,RechargePlanPo.class,BeanCopy.COPYALL);
		rechargePlanMapper.addRechargePlanList(poList);
	}
	public void delRechargePlanById(Integer rcid) throws Exception {
		rechargePlanMapper.delRechargePlanById(rcid);
	}
	public void delRechargePlanByIds(List<Integer> ids) throws Exception {
		rechargePlanMapper.delRechargePlanByIds(ids);
	}
	public void delRechargePlanByObj(RechargePlanVo rechargePlanVo) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		rechargePlanMapper.delRechargePlanByObj(po);
	}
	public void saveRechargePlan(RechargePlanVo rechargePlanVo) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		rechargePlanMapper.saveRechargePlan(po);
	}
	public void saveRechargePlanList(List<RechargePlanVo> rechargePlanVoList) throws Exception {
		List<RechargePlanPo> poList=BeanCopy.copyList(rechargePlanVoList,RechargePlanPo.class,BeanCopy.COPYALL);
		rechargePlanMapper.saveRechargePlanList(poList);
	}
	public int updateByRcid(RechargePlanVo rechargePlanVo,Integer rcid) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByRcid(po,rcid);
	}
	public int updateByRcname(RechargePlanVo rechargePlanVo,String rcname) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByRcname(po,rcname);
	}
	public int updateByVtype(RechargePlanVo rechargePlanVo,Integer vtype) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByVtype(po,vtype);
	}
	public int updateByTstart(RechargePlanVo rechargePlanVo,Date tstart) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByTstart(po,tstart);
	}
	public int updateByTend(RechargePlanVo rechargePlanVo,Date tend) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByTend(po,tend);
	}
	public int updateByActive(RechargePlanVo rechargePlanVo,Integer active) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByActive(po,active);
	}
	public int updateByEnroll(RechargePlanVo rechargePlanVo,Integer enroll) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByEnroll(po,enroll);
	}
	public int updateByCityId(RechargePlanVo rechargePlanVo,String cityId) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByCityId(po,cityId);
	}
	public int updateByCityName(RechargePlanVo rechargePlanVo,String cityName) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByCityName(po,cityName);
	}
	public int updateByCommon(RechargePlanVo rechargePlanVo,Integer common) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByCommon(po,common);
	}
	public int updateByNeedVerify(RechargePlanVo rechargePlanVo,Integer needVerify) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByNeedVerify(po,needVerify);
	}
	public int updateByRegUse(RechargePlanVo rechargePlanVo,Integer regUse) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByRegUse(po,regUse);
	}
	public int updateByAuto(RechargePlanVo rechargePlanVo,Integer auto) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByAuto(po,auto);
	}
	public int updateByMaxTimes(RechargePlanVo rechargePlanVo,Integer maxTimes) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByMaxTimes(po,maxTimes);
	}
	public int updateByIndepent(RechargePlanVo rechargePlanVo,Integer indepent) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByIndepent(po,indepent);
	}
	public int updateByJpush(RechargePlanVo rechargePlanVo,String jpush) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByJpush(po,jpush);
	}
	public int updateByEms(RechargePlanVo rechargePlanVo,String ems) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByEms(po,ems);
	}
	public int updateByRejpush(RechargePlanVo rechargePlanVo,String rejpush) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByRejpush(po,rejpush);
	}
	public int updateByReems(RechargePlanVo rechargePlanVo,String reems) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByReems(po,reems);
	}
	public int updateByCouponTemplate(RechargePlanVo rechargePlanVo,String couponTemplate) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByCouponTemplate(po,couponTemplate);
	}
	public int updateByCouponNumber(RechargePlanVo rechargePlanVo,String couponNumber) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByCouponNumber(po,couponNumber);
	}
	public int updateByAgreement(RechargePlanVo rechargePlanVo,String agreement) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByAgreement(po,agreement);
	}
	public int updateByVstate(RechargePlanVo rechargePlanVo,Integer vstate) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByVstate(po,vstate);
	}
	public int updateByReason(RechargePlanVo rechargePlanVo,String reason) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByReason(po,reason);
	}
	public int updateByIsdel(RechargePlanVo rechargePlanVo,Integer isdel) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(RechargePlanVo rechargePlanVo,Long cuid) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(RechargePlanVo rechargePlanVo,Long muid) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(RechargePlanVo rechargePlanVo,Date ctime) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(RechargePlanVo rechargePlanVo,String mtime) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		return rechargePlanMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(RechargePlanVo rechargePlanVo,RechargePlanVo search) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		RechargePlanPo searchPo=BeanCopy.copyAll(search,RechargePlanPo.class);
		return rechargePlanMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(RechargePlanVo rechargePlanVo,RechargePlanVo search) throws Exception {
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		RechargePlanPo searchPo=BeanCopy.copyAll(search,RechargePlanPo.class);
		return rechargePlanMapper.updateAllByObject(po,searchPo);
	}
	public RechargePlanVo queryRechargePlanById(Integer rcid) throws Exception {
		String postSql="";
		String sqlFileld="*";
		RechargePlanPo po=rechargePlanMapper.queryRechargePlanById(rcid,postSql,sqlFileld);
		RechargePlanVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,RechargePlanVo.class);
		}
		return vo;
	}
	public List<RechargePlanVo> queryRechargePlanByIds(List<Integer> ids,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryRechargePlanByIds(ids,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByObjectAnd(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		List<RechargePlanPo> poList=rechargePlanMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public Integer countByObject(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		Integer total=rechargePlanMapper.countByObject(po,postSql,sqlFileld);
		return total;
	}
	public List<RechargePlanVo> queryByObjectOr(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		List<RechargePlanPo> poList=rechargePlanMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByRcid(Integer rcid,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByRcid(rcid,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByRcname(String rcname,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByRcname(rcname,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByVtype(Integer vtype,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByVtype(vtype,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByTstart(Date tstart,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByTstart(tstart,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByTend(Date tend,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByTend(tend,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByActive(Integer active,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByActive(active,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByEnroll(Integer enroll,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByEnroll(enroll,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByCityId(String cityId,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByCityId(cityId,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByCityName(String cityName,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByCityName(cityName,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByCommon(Integer common,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByCommon(common,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByNeedVerify(Integer needVerify,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByNeedVerify(needVerify,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByRegUse(Integer regUse,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByRegUse(regUse,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByAuto(Integer auto,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByAuto(auto,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByMaxTimes(Integer maxTimes,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByMaxTimes(maxTimes,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByIndepent(Integer indepent,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByIndepent(indepent,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByJpush(String jpush,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByJpush(jpush,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByEms(String ems,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByEms(ems,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByRejpush(String rejpush,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByRejpush(rejpush,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByReems(String reems,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByReems(reems,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByCouponTemplate(String couponTemplate,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByCouponTemplate(couponTemplate,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByCouponNumber(String couponNumber,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByCouponNumber(couponNumber,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByAgreement(String agreement,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByAgreement(agreement,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByVstate(Integer vstate,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByVstate(vstate,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByReason(String reason,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByReason(reason,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByIsdel(Integer isdel,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByCuid(Long cuid,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByMuid(Long muid,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByMuid(muid,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByCtime(Date ctime,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByMtime(String mtime,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargePlanPo> poList=rechargePlanMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByNew0(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		List<RechargePlanPo> poList=rechargePlanMapper.queryByNew0(po,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByNew1(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		List<RechargePlanPo> poList=rechargePlanMapper.queryByNew1(po,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByNew2(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		List<RechargePlanPo> poList=rechargePlanMapper.queryByNew2(po,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByNew3(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		List<RechargePlanPo> poList=rechargePlanMapper.queryByNew3(po,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByNew4(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		List<RechargePlanPo> poList=rechargePlanMapper.queryByNew4(po,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByNew5(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		List<RechargePlanPo> poList=rechargePlanMapper.queryByNew5(po,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByNew6(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		List<RechargePlanPo> poList=rechargePlanMapper.queryByNew6(po,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargePlanVo> queryByNew7(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery) throws Exception {
		String postSql=rechargePlanQuery.getSqlPost();
		String sqlFileld=rechargePlanQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargePlanPo po=BeanCopy.copyAll(rechargePlanVo,RechargePlanPo.class);
		List<RechargePlanPo> poList=rechargePlanMapper.queryByNew7(po,postSql,sqlFileld);
		List<RechargePlanVo> voList=new ArrayList<RechargePlanVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargePlanVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
