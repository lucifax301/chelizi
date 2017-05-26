package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.CoachAllowanceMapper;
import com.lili.order.dao.po.CoachAllowancePo;
import com.lili.order.service.CoachAllowanceService;
import com.lili.order.vo.CoachAllowanceQuery;
import com.lili.order.vo.CoachAllowanceVo;

public class CoachAllowanceServiceImpl implements CoachAllowanceService {

	@Autowired
	CoachAllowanceMapper coachAllowanceMapper;;
	public void addCoachAllowance(CoachAllowanceVo coachAllowanceVo) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		coachAllowanceMapper.addCoachAllowance(po);
	}
	public void addCoachAllowanceList(List<CoachAllowanceVo> coachAllowanceVoList) throws Exception {
		List<CoachAllowancePo> poList=BeanCopy.copyList(coachAllowanceVoList,CoachAllowancePo.class,BeanCopy.COPYALL);
		coachAllowanceMapper.addCoachAllowanceList(poList);
	}
	public void delCoachAllowanceById(Integer caid) throws Exception {
		coachAllowanceMapper.delCoachAllowanceById(caid);
	}
	public void delCoachAllowanceByIds(List<Integer> ids) throws Exception {
		coachAllowanceMapper.delCoachAllowanceByIds(ids);
	}
	public void delCoachAllowanceByObj(CoachAllowanceVo coachAllowanceVo) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		coachAllowanceMapper.delCoachAllowanceByObj(po);
	}
	public void saveCoachAllowance(CoachAllowanceVo coachAllowanceVo) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		coachAllowanceMapper.saveCoachAllowance(po);
	}
	public void saveCoachAllowanceList(List<CoachAllowanceVo> coachAllowanceVoList) throws Exception {
		List<CoachAllowancePo> poList=BeanCopy.copyList(coachAllowanceVoList,CoachAllowancePo.class,BeanCopy.COPYALL);
		coachAllowanceMapper.saveCoachAllowanceList(poList);
	}
	public int updateByCaid(CoachAllowanceVo coachAllowanceVo,Integer caid) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		return coachAllowanceMapper.updateByCaid(po,caid);
	}
	public int updateByCoachId(CoachAllowanceVo coachAllowanceVo,Long coachId) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		return coachAllowanceMapper.updateByCoachId(po,coachId);
	}
	public int updateByOrderId(CoachAllowanceVo coachAllowanceVo,String orderId) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		return coachAllowanceMapper.updateByOrderId(po,orderId);
	}
	public int updateByAllowance(CoachAllowanceVo coachAllowanceVo,Integer allowance) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		return coachAllowanceMapper.updateByAllowance(po,allowance);
	}
	public int updateByAstate(CoachAllowanceVo coachAllowanceVo,Integer astate) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		return coachAllowanceMapper.updateByAstate(po,astate);
	}
	public int updateByAtime(CoachAllowanceVo coachAllowanceVo,Date atime) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		return coachAllowanceMapper.updateByAtime(po,atime);
	}
	public int updateNotNullByObject(CoachAllowanceVo coachAllowanceVo,CoachAllowanceVo search) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		CoachAllowancePo searchPo=BeanCopy.copyAll(search,CoachAllowancePo.class);
		return coachAllowanceMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(CoachAllowanceVo coachAllowanceVo,CoachAllowanceVo search) throws Exception {
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		CoachAllowancePo searchPo=BeanCopy.copyAll(search,CoachAllowancePo.class);
		return coachAllowanceMapper.updateAllByObject(po,searchPo);
	}
	public CoachAllowanceVo queryCoachAllowanceById(Integer caid,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=coachAllowanceMapper.queryCoachAllowanceById(caid,postSql,sqlFileld);
		CoachAllowanceVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,CoachAllowanceVo.class);
		}
		return vo;
	}
	public List<CoachAllowanceVo> queryCoachAllowanceByIds(List<Integer> ids,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryCoachAllowanceByIds(ids,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByObjectAnd(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByObjectOr(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByCaid(Integer caid,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByCaid(caid,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByCoachId(Long coachId,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByCoachId(coachId,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByOrderId(String orderId,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByOrderId(orderId,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByAllowance(Integer allowance,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByAllowance(allowance,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByAstate(Integer astate,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByAstate(astate,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByAtime(Date atime,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByAtime(atime,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByNew0(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByNew0(po,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByNew1(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByNew1(po,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByNew2(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByNew2(po,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByNew3(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByNew3(po,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByNew4(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByNew4(po,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByNew5(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByNew5(po,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByNew6(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByNew6(po,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachAllowanceVo> queryByNew7(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery) throws Exception {
		String postSql=coachAllowanceQuery.getSqlPost();
		String sqlFileld=coachAllowanceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachAllowancePo po=BeanCopy.copyAll(coachAllowanceVo,CoachAllowancePo.class);
		List<CoachAllowancePo> poList=coachAllowanceMapper.queryByNew7(po,postSql,sqlFileld);
		List<CoachAllowanceVo> voList=new ArrayList<CoachAllowanceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachAllowanceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
