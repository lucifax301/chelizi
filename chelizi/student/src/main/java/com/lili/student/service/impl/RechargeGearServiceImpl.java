package com.lili.student.service.impl;

import java.util.List;
import java.util.Date;

import com.lili.common.util.BeanCopy;
import com.lili.student.vo.RechargeGearQuery;
import com.lili.student.vo.RechargeGearVo;
import com.lili.student.dao.mapper.RechargeGearMapper;
import com.lili.student.dao.po.RechargeGearPo;
import com.lili.student.service.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RechargeGearServiceImpl implements RechargeGearService {

	@Autowired
	RechargeGearMapper rechargeGearMapper;;
	public void addRechargeGear(RechargeGearVo rechargeGearVo) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		rechargeGearMapper.addRechargeGear(po);
	}
	public void addRechargeGearList(List<RechargeGearVo> rechargeGearVoList) throws Exception {
		List<RechargeGearPo> poList=BeanCopy.copyList(rechargeGearVoList,RechargeGearPo.class,BeanCopy.COPYALL);
		rechargeGearMapper.addRechargeGearList(poList);
	}
	public void delRechargeGearById(Integer rgid) throws Exception {
		rechargeGearMapper.delRechargeGearById(rgid);
	}
	public void delRechargeGearByIds(List<Integer> ids) throws Exception {
		rechargeGearMapper.delRechargeGearByIds(ids);
	}
	public void delRechargeGearByObj(RechargeGearVo rechargeGearVo) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		rechargeGearMapper.delRechargeGearByObj(po);
	}
	public void saveRechargeGear(RechargeGearVo rechargeGearVo) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		rechargeGearMapper.saveRechargeGear(po);
	}
	public void saveRechargeGearList(List<RechargeGearVo> rechargeGearVoList) throws Exception {
		List<RechargeGearPo> poList=BeanCopy.copyList(rechargeGearVoList,RechargeGearPo.class,BeanCopy.COPYALL);
		rechargeGearMapper.saveRechargeGearList(poList);
	}
	public int updateByRgid(RechargeGearVo rechargeGearVo,Integer rgid) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByRgid(po,rgid);
	}
	public int updateByRcid(RechargeGearVo rechargeGearVo,Integer rcid) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByRcid(po,rcid);
	}
	public int updateByPmin(RechargeGearVo rechargeGearVo,Integer pmin) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByPmin(po,pmin);
	}
	public int updateByPmax(RechargeGearVo rechargeGearVo,Integer pmax) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByPmax(po,pmax);
	}
	public int updateByPercent(RechargeGearVo rechargeGearVo,Integer percent) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByPercent(po,percent);
	}
	public int updateByMoney(RechargeGearVo rechargeGearVo,Integer money) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByMoney(po,money);
	}
	public int updateByVstate(RechargeGearVo rechargeGearVo,Integer vstate) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByVstate(po,vstate);
	}
	public int updateByIsdel(RechargeGearVo rechargeGearVo,Integer isdel) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(RechargeGearVo rechargeGearVo,Long cuid) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(RechargeGearVo rechargeGearVo,Long muid) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(RechargeGearVo rechargeGearVo,Date ctime) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(RechargeGearVo rechargeGearVo,String mtime) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		return rechargeGearMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(RechargeGearVo rechargeGearVo,RechargeGearVo search) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		RechargeGearPo searchPo=BeanCopy.copyAll(search,RechargeGearPo.class);
		return rechargeGearMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(RechargeGearVo rechargeGearVo,RechargeGearVo search) throws Exception {
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		RechargeGearPo searchPo=BeanCopy.copyAll(search,RechargeGearPo.class);
		return rechargeGearMapper.updateAllByObject(po,searchPo);
	}
	public RechargeGearVo queryRechargeGearById(Integer rgid) throws Exception {
		String postSql="";
		String sqlFileld="*";
		RechargeGearPo po=rechargeGearMapper.queryRechargeGearById(rgid,postSql,sqlFileld);
		RechargeGearVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,RechargeGearVo.class);
		}
		return vo;
	}
	public List<RechargeGearVo> queryRechargeGearByIds(List<Integer> ids,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryRechargeGearByIds(ids,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByObjectAnd(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		List<RechargeGearPo> poList=rechargeGearMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public Integer countByObject(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		Integer total=rechargeGearMapper.countByObject(po,postSql,sqlFileld);
		return total;
	}
	public List<RechargeGearVo> queryByObjectOr(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		List<RechargeGearPo> poList=rechargeGearMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByRgid(Integer rgid,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByRgid(rgid,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByRcid(Integer rcid,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByRcid(rcid,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByPmin(Integer pmin,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByPmin(pmin,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByPmax(Integer pmax,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByPmax(pmax,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByPercent(Integer percent,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByPercent(percent,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByMoney(Integer money,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByMoney(money,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByVstate(Integer vstate,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByVstate(vstate,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByIsdel(Integer isdel,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByCuid(Long cuid,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByMuid(Long muid,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByMuid(muid,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByCtime(Date ctime,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByMtime(String mtime,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RechargeGearPo> poList=rechargeGearMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByNew0(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		List<RechargeGearPo> poList=rechargeGearMapper.queryByNew0(po,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByNew1(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		List<RechargeGearPo> poList=rechargeGearMapper.queryByNew1(po,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByNew2(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		List<RechargeGearPo> poList=rechargeGearMapper.queryByNew2(po,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByNew3(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		List<RechargeGearPo> poList=rechargeGearMapper.queryByNew3(po,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByNew4(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		List<RechargeGearPo> poList=rechargeGearMapper.queryByNew4(po,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByNew5(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		List<RechargeGearPo> poList=rechargeGearMapper.queryByNew5(po,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByNew6(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		List<RechargeGearPo> poList=rechargeGearMapper.queryByNew6(po,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RechargeGearVo> queryByNew7(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery) throws Exception {
		String postSql=rechargeGearQuery.getSqlPost();
		String sqlFileld=rechargeGearQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RechargeGearPo po=BeanCopy.copyAll(rechargeGearVo,RechargeGearPo.class);
		List<RechargeGearPo> poList=rechargeGearMapper.queryByNew7(po,postSql,sqlFileld);
		List<RechargeGearVo> voList=new ArrayList<RechargeGearVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RechargeGearVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
