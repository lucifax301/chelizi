package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.CoachLevelMapper;
import com.lili.order.dao.po.CoachLevelPo;
import com.lili.order.service.CoachLevelService;
import com.lili.order.vo.CoachLevelQuery;
import com.lili.order.vo.CoachLevelVo;

public class CoachLevelServiceImpl implements CoachLevelService {

	@Autowired
	CoachLevelMapper coachLevelMapper;;
	public void addCoachLevel(CoachLevelVo coachLevelVo) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		coachLevelMapper.addCoachLevel(po);
	}
	public void addCoachLevelList(List<CoachLevelVo> coachLevelVoList) throws Exception {
		List<CoachLevelPo> poList=BeanCopy.copyList(coachLevelVoList,CoachLevelPo.class,BeanCopy.COPYALL);
		coachLevelMapper.addCoachLevelList(poList);
	}
	public void delCoachLevelById(Integer colid) throws Exception {
		coachLevelMapper.delCoachLevelById(colid);
	}
	public void delCoachLevelByIds(List<Integer> ids) throws Exception {
		coachLevelMapper.delCoachLevelByIds(ids);
	}
	public void delCoachLevelByObj(CoachLevelVo coachLevelVo) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		coachLevelMapper.delCoachLevelByObj(po);
	}
	public void saveCoachLevel(CoachLevelVo coachLevelVo) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		coachLevelMapper.saveCoachLevel(po);
	}
	public void saveCoachLevelList(List<CoachLevelVo> coachLevelVoList) throws Exception {
		List<CoachLevelPo> poList=BeanCopy.copyList(coachLevelVoList,CoachLevelPo.class,BeanCopy.COPYALL);
		coachLevelMapper.saveCoachLevelList(poList);
	}
	public int updateByColid(CoachLevelVo coachLevelVo,Integer colid) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		return coachLevelMapper.updateByColid(po,colid);
	}
	public int updateByName(CoachLevelVo coachLevelVo,String name) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		return coachLevelMapper.updateByName(po,name);
	}
	public int updateByPrate(CoachLevelVo coachLevelVo,Integer prate) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		return coachLevelMapper.updateByPrate(po,prate);
	}
	public int updateByIsdel(CoachLevelVo coachLevelVo,Integer isdel) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		return coachLevelMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(CoachLevelVo coachLevelVo,Integer cuid) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		return coachLevelMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(CoachLevelVo coachLevelVo,Integer muid) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		return coachLevelMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(CoachLevelVo coachLevelVo,Date ctime) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		return coachLevelMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(CoachLevelVo coachLevelVo,String mtime) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		return coachLevelMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(CoachLevelVo coachLevelVo,CoachLevelVo search) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		CoachLevelPo searchPo=BeanCopy.copyAll(search,CoachLevelPo.class);
		return coachLevelMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(CoachLevelVo coachLevelVo,CoachLevelVo search) throws Exception {
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		CoachLevelPo searchPo=BeanCopy.copyAll(search,CoachLevelPo.class);
		return coachLevelMapper.updateAllByObject(po,searchPo);
	}
	public CoachLevelVo queryCoachLevelById(Integer colid,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=coachLevelMapper.queryCoachLevelById(colid,postSql,sqlFileld);
		CoachLevelVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,CoachLevelVo.class);
		}
		return vo;
	}
	public List<CoachLevelVo> queryCoachLevelByIds(List<Integer> ids,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachLevelPo> poList=coachLevelMapper.queryCoachLevelByIds(ids,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByObjectAnd(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		List<CoachLevelPo> poList=coachLevelMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByObjectOr(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		List<CoachLevelPo> poList=coachLevelMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByColid(Integer colid,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachLevelPo> poList=coachLevelMapper.queryByColid(colid,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByName(String name,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachLevelPo> poList=coachLevelMapper.queryByName(name,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByPrate(Integer prate,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachLevelPo> poList=coachLevelMapper.queryByPrate(prate,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByIsdel(Integer isdel,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachLevelPo> poList=coachLevelMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByCuid(Integer cuid,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachLevelPo> poList=coachLevelMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByMuid(Integer muid,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachLevelPo> poList=coachLevelMapper.queryByMuid(muid,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByCtime(Date ctime,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachLevelPo> poList=coachLevelMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByMtime(String mtime,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachLevelPo> poList=coachLevelMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByNew0(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		List<CoachLevelPo> poList=coachLevelMapper.queryByNew0(po,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByNew1(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		List<CoachLevelPo> poList=coachLevelMapper.queryByNew1(po,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByNew2(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		List<CoachLevelPo> poList=coachLevelMapper.queryByNew2(po,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByNew3(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		List<CoachLevelPo> poList=coachLevelMapper.queryByNew3(po,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByNew4(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		List<CoachLevelPo> poList=coachLevelMapper.queryByNew4(po,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByNew5(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		List<CoachLevelPo> poList=coachLevelMapper.queryByNew5(po,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByNew6(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		List<CoachLevelPo> poList=coachLevelMapper.queryByNew6(po,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachLevelVo> queryByNew7(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery) throws Exception {
		String postSql=coachLevelQuery.getSqlPost();
		String sqlFileld=coachLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachLevelPo po=BeanCopy.copyAll(coachLevelVo,CoachLevelPo.class);
		List<CoachLevelPo> poList=coachLevelMapper.queryByNew7(po,postSql,sqlFileld);
		List<CoachLevelVo> voList=new ArrayList<CoachLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
