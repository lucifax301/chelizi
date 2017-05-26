package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.PlantClassMapper;
import com.lili.order.dao.po.PlantClassPo;
import com.lili.order.service.PlantClassService;
import com.lili.order.vo.PlantClassQuery;
import com.lili.order.vo.PlantClassVo;

public class PlantClassServiceImpl implements PlantClassService {

	@Autowired
	PlantClassMapper plantClassMapper;;
	public void addPlantClass(PlantClassVo plantClassVo) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		plantClassMapper.addPlantClass(po);
	}
	public void addPlantClassList(List<PlantClassVo> plantClassVoList) throws Exception {
		List<PlantClassPo> poList=BeanCopy.copyList(plantClassVoList,PlantClassPo.class,BeanCopy.COPYALL);
		plantClassMapper.addPlantClassList(poList);
	}
	public void delPlantClassById(String orderId) throws Exception {
		plantClassMapper.delPlantClassById(orderId);
	}
	public void delPlantClassByIds(List<String> ids) throws Exception {
		plantClassMapper.delPlantClassByIds(ids);
	}
	public void delPlantClassByObj(PlantClassVo plantClassVo) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		plantClassMapper.delPlantClassByObj(po);
	}
	public void savePlantClass(PlantClassVo plantClassVo) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		plantClassMapper.savePlantClass(po);
	}
	public void savePlantClassList(List<PlantClassVo> plantClassVoList) throws Exception {
		List<PlantClassPo> poList=BeanCopy.copyList(plantClassVoList,PlantClassPo.class,BeanCopy.COPYALL);
		plantClassMapper.savePlantClassList(poList);
	}
	public int updateByOrderId(PlantClassVo plantClassVo,String orderId) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		return plantClassMapper.updateByOrderId(po,orderId);
	}
	public int updateByCcid(PlantClassVo plantClassVo,Integer ccid) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		return plantClassMapper.updateByCcid(po,ccid);
	}
	public int updateByCoachId(PlantClassVo plantClassVo,Long coachId) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		return plantClassMapper.updateByCoachId(po,coachId);
	}
	public int updateByStudentId(PlantClassVo plantClassVo,Long studentId) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		return plantClassMapper.updateByStudentId(po,studentId);
	}
	public int updateByGtime(PlantClassVo plantClassVo,Date gtime) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		return plantClassMapper.updateByGtime(po,gtime);
	}
	public int updateByStuName(PlantClassVo plantClassVo,String stuName) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		return plantClassMapper.updateByStuName(po,stuName);
	}
	public int updateByStuImg(PlantClassVo plantClassVo,String stuImg) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		return plantClassMapper.updateByStuImg(po,stuImg);
	}
	public int updateByStuMobile(PlantClassVo plantClassVo,String stuMobile) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		return plantClassMapper.updateByStuMobile(po,stuMobile);
	}
	public int updateByIsdel(PlantClassVo plantClassVo,Integer isdel) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		return plantClassMapper.updateByIsdel(po,isdel);
	}
	public int updateNotNullByObject(PlantClassVo plantClassVo,PlantClassVo search) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		PlantClassPo searchPo=BeanCopy.copyAll(search,PlantClassPo.class);
		return plantClassMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(PlantClassVo plantClassVo,PlantClassVo search) throws Exception {
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		PlantClassPo searchPo=BeanCopy.copyAll(search,PlantClassPo.class);
		return plantClassMapper.updateAllByObject(po,searchPo);
	}
	public PlantClassVo queryPlantClassById(String orderId,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=plantClassMapper.queryPlantClassById(orderId,postSql,sqlFileld);
		PlantClassVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,PlantClassVo.class);
		}
		return vo;
	}
	public List<PlantClassVo> queryPlantClassByIds(List<String> ids,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<PlantClassPo> poList=plantClassMapper.queryPlantClassByIds(ids,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByObjectAnd(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		List<PlantClassPo> poList=plantClassMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByObjectOr(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		List<PlantClassPo> poList=plantClassMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByOrderId(String orderId,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<PlantClassPo> poList=plantClassMapper.queryByOrderId(orderId,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByCcid(Integer ccid,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<PlantClassPo> poList=plantClassMapper.queryByCcid(ccid,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByCoachId(Long coachId,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<PlantClassPo> poList=plantClassMapper.queryByCoachId(coachId,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByStudentId(Long studentId,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<PlantClassPo> poList=plantClassMapper.queryByStudentId(studentId,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByGtime(Date gtime,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<PlantClassPo> poList=plantClassMapper.queryByGtime(gtime,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByStuName(String stuName,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<PlantClassPo> poList=plantClassMapper.queryByStuName(stuName,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByStuImg(String stuImg,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<PlantClassPo> poList=plantClassMapper.queryByStuImg(stuImg,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByStuMobile(String stuMobile,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<PlantClassPo> poList=plantClassMapper.queryByStuMobile(stuMobile,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByIsdel(Integer isdel,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<PlantClassPo> poList=plantClassMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByNew0(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		List<PlantClassPo> poList=plantClassMapper.queryByNew0(po,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByNew1(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		List<PlantClassPo> poList=plantClassMapper.queryByNew1(po,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByNew2(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		List<PlantClassPo> poList=plantClassMapper.queryByNew2(po,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByNew3(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		List<PlantClassPo> poList=plantClassMapper.queryByNew3(po,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByNew4(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		List<PlantClassPo> poList=plantClassMapper.queryByNew4(po,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByNew5(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		List<PlantClassPo> poList=plantClassMapper.queryByNew5(po,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByNew6(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		List<PlantClassPo> poList=plantClassMapper.queryByNew6(po,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<PlantClassVo> queryByNew7(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery) throws Exception {
		String postSql=plantClassQuery.getSqlPost();
		String sqlFileld=plantClassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		PlantClassPo po=BeanCopy.copyAll(plantClassVo,PlantClassPo.class);
		List<PlantClassPo> poList=plantClassMapper.queryByNew7(po,postSql,sqlFileld);
		List<PlantClassVo> voList=new ArrayList<PlantClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,PlantClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
