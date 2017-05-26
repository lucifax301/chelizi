package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.StudentStatisticMapper;
import com.lili.order.dao.po.StudentStatisticPo;
import com.lili.order.service.StudentStatisticService;
import com.lili.order.vo.StudentStatisticQuery;
import com.lili.order.vo.StudentStatisticVo;

public class StudentStatisticServiceImpl implements StudentStatisticService {

	@Autowired
	StudentStatisticMapper studentstatisticMapper;;
	public void addStudentStatistic(StudentStatisticVo studentstatisticVo) throws Exception {
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		studentstatisticMapper.addStudentStatistic(po);
	}
	public void addStudentStatisticList(List<StudentStatisticVo> studentstatisticVoList) throws Exception {
		List<StudentStatisticPo> poList=BeanCopy.copyList(studentstatisticVoList,StudentStatisticPo.class,BeanCopy.COPYALL);
		studentstatisticMapper.addStudentStatisticList(poList);
	}
	public void delStudentStatisticById(Integer ssid) throws Exception {
		studentstatisticMapper.delStudentStatisticById(ssid);
	}
	public void delStudentStatisticByIds(List<Integer> ids) throws Exception {
		studentstatisticMapper.delStudentStatisticByIds(ids);
	}
	public void delStudentStatisticByObj(StudentStatisticVo studentstatisticVo) throws Exception {
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		studentstatisticMapper.delStudentStatisticByObj(po);
	}
	public void saveStudentStatistic(StudentStatisticVo studentstatisticVo) throws Exception {
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		studentstatisticMapper.saveStudentStatistic(po);
	}
	public void saveStudentStatisticList(List<StudentStatisticVo> studentstatisticVoList) throws Exception {
		List<StudentStatisticPo> poList=BeanCopy.copyList(studentstatisticVoList,StudentStatisticPo.class,BeanCopy.COPYALL);
		studentstatisticMapper.saveStudentStatisticList(poList);
	}
	public int updateBySsid(StudentStatisticVo studentstatisticVo,Integer ssid) throws Exception {
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		return studentstatisticMapper.updateBySsid(po,ssid);
	}
	public int updateByStudentId(StudentStatisticVo studentstatisticVo,Long studentId) throws Exception {
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		return studentstatisticMapper.updateByStudentId(po,studentId);
	}
	public int updateByCtid(StudentStatisticVo studentstatisticVo,Integer ctid) throws Exception {
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		return studentstatisticMapper.updateByCtid(po,ctid);
	}
	public int updateByTotal(StudentStatisticVo studentstatisticVo,Integer total) throws Exception {
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		return studentstatisticMapper.updateByTotal(po,total);
	}
	public int updateByScore(StudentStatisticVo studentstatisticVo,Integer score) throws Exception {
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		return studentstatisticMapper.updateByScore(po,score);
	}
	public int updateNotNullByObject(StudentStatisticVo studentstatisticVo,StudentStatisticVo search) throws Exception {
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		StudentStatisticPo searchPo=BeanCopy.copyAll(search,StudentStatisticPo.class);
		return studentstatisticMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(StudentStatisticVo studentstatisticVo,StudentStatisticVo search) throws Exception {
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		StudentStatisticPo searchPo=BeanCopy.copyAll(search,StudentStatisticPo.class);
		return studentstatisticMapper.updateAllByObject(po,searchPo);
	}
	public StudentStatisticVo queryStudentStatisticById(Integer ssid,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=studentstatisticMapper.queryStudentStatisticById(ssid,postSql,sqlFileld);
		StudentStatisticVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,StudentStatisticVo.class);
		}
		return vo;
	}
	public List<StudentStatisticVo> queryStudentStatisticByIds(List<Integer> ids,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StudentStatisticPo> poList=studentstatisticMapper.queryStudentStatisticByIds(ids,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByObjectAnd(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByObjectOr(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryBySsid(Integer ssid,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StudentStatisticPo> poList=studentstatisticMapper.queryBySsid(ssid,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByStudentId(Long studentId,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByStudentId(studentId,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByCtid(Integer ctid,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByCtid(ctid,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByTotal(Integer total,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByTotal(total,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByScore(Integer score,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByScore(score,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByNew0(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByNew0(po,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByNew1(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByNew1(po,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByNew2(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByNew2(po,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByNew3(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByNew3(po,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByNew4(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByNew4(po,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByNew5(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByNew5(po,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByNew6(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByNew6(po,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<StudentStatisticVo> queryByNew7(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery) throws Exception {
		String postSql=studentstatisticQuery.getSqlPost();
		String sqlFileld=studentstatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StudentStatisticPo po=BeanCopy.copyAll(studentstatisticVo,StudentStatisticPo.class);
		List<StudentStatisticPo> poList=studentstatisticMapper.queryByNew7(po,postSql,sqlFileld);
		List<StudentStatisticVo> voList=new ArrayList<StudentStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,StudentStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
