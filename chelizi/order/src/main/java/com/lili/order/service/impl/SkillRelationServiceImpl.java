package com.lili.order.service.impl;

import java.util.List;
import java.util.Date;

import com.lili.common.util.BeanCopy;
import com.lili.order.vo.SkillRelationQuery;
import com.lili.order.vo.SkillRelationVo;
import com.lili.order.dao.mapper.SkillRelationMapper;
import com.lili.order.dao.po.SkillRelationPo;
import com.lili.order.service.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SkillRelationServiceImpl implements SkillRelationService {

	@Autowired
	SkillRelationMapper skillRelationMapper;;
	public void addSkillRelation(SkillRelationVo skillRelationVo) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		skillRelationMapper.addSkillRelation(po);
	}
	public void addSkillRelationList(List<SkillRelationVo> skillRelationVoList) throws Exception {
		List<SkillRelationPo> poList=BeanCopy.copyList(skillRelationVoList,SkillRelationPo.class,BeanCopy.COPYALL);
		skillRelationMapper.addSkillRelationList(poList);
	}
	public void delSkillRelationById(Integer sid) throws Exception {
		skillRelationMapper.delSkillRelationById(sid);
	}
	public void delSkillRelationByIds(List<Integer> ids) throws Exception {
		skillRelationMapper.delSkillRelationByIds(ids);
	}
	public void delSkillRelationByObj(SkillRelationVo skillRelationVo) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		skillRelationMapper.delSkillRelationByObj(po);
	}
	public void saveSkillRelation(SkillRelationVo skillRelationVo) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		skillRelationMapper.saveSkillRelation(po);
	}
	public void saveSkillRelationList(List<SkillRelationVo> skillRelationVoList) throws Exception {
		List<SkillRelationPo> poList=BeanCopy.copyList(skillRelationVoList,SkillRelationPo.class,BeanCopy.COPYALL);
		skillRelationMapper.saveSkillRelationList(poList);
	}
	public int updateBySid(SkillRelationVo skillRelationVo,Integer sid) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		return skillRelationMapper.updateBySid(po,sid);
	}
	public int updateByCtid(SkillRelationVo skillRelationVo,Integer ctid) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		return skillRelationMapper.updateByCtid(po,ctid);
	}
	public int updateByCityId(SkillRelationVo skillRelationVo,Integer cityId) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		return skillRelationMapper.updateByCityId(po,cityId);
	}
	public int updateByCourseId(SkillRelationVo skillRelationVo,Integer courseId) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		return skillRelationMapper.updateByCourseId(po,courseId);
	}
	public int updateBySubjectId(SkillRelationVo skillRelationVo,Integer subjectId) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		return skillRelationMapper.updateBySubjectId(po,subjectId);
	}
	public int updateByIsdel(SkillRelationVo skillRelationVo,Integer isdel) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		return skillRelationMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(SkillRelationVo skillRelationVo,Integer cuid) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		return skillRelationMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(SkillRelationVo skillRelationVo,Integer muid) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		return skillRelationMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(SkillRelationVo skillRelationVo,Date ctime) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		return skillRelationMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(SkillRelationVo skillRelationVo,String mtime) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		return skillRelationMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(SkillRelationVo skillRelationVo,SkillRelationVo search) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		SkillRelationPo searchPo=BeanCopy.copyAll(search,SkillRelationPo.class);
		return skillRelationMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(SkillRelationVo skillRelationVo,SkillRelationVo search) throws Exception {
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		SkillRelationPo searchPo=BeanCopy.copyAll(search,SkillRelationPo.class);
		return skillRelationMapper.updateAllByObject(po,searchPo);
	}
	public SkillRelationVo querySkillRelationById(Integer sid,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=skillRelationMapper.querySkillRelationById(sid,postSql,sqlFileld);
		SkillRelationVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,SkillRelationVo.class);
		}
		return vo;
	}
	public List<SkillRelationVo> querySkillRelationByIds(List<Integer> ids,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.querySkillRelationByIds(ids,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByObjectAnd(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		List<SkillRelationPo> poList=skillRelationMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByObjectOr(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		List<SkillRelationPo> poList=skillRelationMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryBySid(Integer sid,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.queryBySid(sid,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByCtid(Integer ctid,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.queryByCtid(ctid,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByCityId(Integer cityId,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.queryByCityId(cityId,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByCourseId(Integer courseId,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.queryByCourseId(courseId,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryBySubjectId(Integer subjectId,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.queryBySubjectId(subjectId,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByIsdel(Integer isdel,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByCuid(Integer cuid,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByMuid(Integer muid,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.queryByMuid(muid,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByCtime(Date ctime,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByMtime(String mtime,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<SkillRelationPo> poList=skillRelationMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByNew0(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		List<SkillRelationPo> poList=skillRelationMapper.queryByNew0(po,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByNew1(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		List<SkillRelationPo> poList=skillRelationMapper.queryByNew1(po,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByNew2(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		List<SkillRelationPo> poList=skillRelationMapper.queryByNew2(po,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByNew3(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		List<SkillRelationPo> poList=skillRelationMapper.queryByNew3(po,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByNew4(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		List<SkillRelationPo> poList=skillRelationMapper.queryByNew4(po,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByNew5(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		List<SkillRelationPo> poList=skillRelationMapper.queryByNew5(po,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByNew6(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		List<SkillRelationPo> poList=skillRelationMapper.queryByNew6(po,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<SkillRelationVo> queryByNew7(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery) throws Exception {
		String postSql=skillRelationQuery.getSqlPost();
		String sqlFileld=skillRelationQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		SkillRelationPo po=BeanCopy.copyAll(skillRelationVo,SkillRelationPo.class);
		List<SkillRelationPo> poList=skillRelationMapper.queryByNew7(po,postSql,sqlFileld);
		List<SkillRelationVo> voList=new ArrayList<SkillRelationVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,SkillRelationVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
