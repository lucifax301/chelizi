package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.CoachCommentMapper;
import com.lili.order.dao.po.CoachCommentPo;
import com.lili.order.service.CoachCommentService;
import com.lili.order.vo.CoachCommentQuery;
import com.lili.order.vo.CoachCommentVo;

public class CoachCommentServiceImpl implements CoachCommentService {

	@Autowired
	CoachCommentMapper coachcommentMapper;;
	public void addCoachComment(CoachCommentVo coachcommentVo) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		coachcommentMapper.addCoachComment(po);
	}
	public void addCoachCommentList(List<CoachCommentVo> coachcommentVoList) throws Exception {
		List<CoachCommentPo> poList=BeanCopy.copyList(coachcommentVoList,CoachCommentPo.class,BeanCopy.COPYALL);
		coachcommentMapper.addCoachCommentList(poList);
	}
	public void delCoachCommentById(Integer ccid) throws Exception {
		coachcommentMapper.delCoachCommentById(ccid);
	}
	public void delCoachCommentByIds(List<Integer> ids) throws Exception {
		coachcommentMapper.delCoachCommentByIds(ids);
	}
	public void delCoachCommentByObj(CoachCommentVo coachcommentVo) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		coachcommentMapper.delCoachCommentByObj(po);
	}
	public void saveCoachComment(CoachCommentVo coachcommentVo) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		coachcommentMapper.saveCoachComment(po);
	}
	public void saveCoachCommentList(List<CoachCommentVo> coachcommentVoList) throws Exception {
		List<CoachCommentPo> poList=BeanCopy.copyList(coachcommentVoList,CoachCommentPo.class,BeanCopy.COPYALL);
		coachcommentMapper.saveCoachCommentList(poList);
	}
	public int updateByCcid(CoachCommentVo coachcommentVo,Integer ccid) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		return coachcommentMapper.updateByCcid(po,ccid);
	}
	public int updateByStudentId(CoachCommentVo coachcommentVo,Long studentId) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		return coachcommentMapper.updateByStudentId(po,studentId);
	}
	public int updateByCoachId(CoachCommentVo coachcommentVo,Long coachId) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		return coachcommentMapper.updateByCoachId(po,coachId);
	}
	public int updateByOrderId(CoachCommentVo coachcommentVo,String orderId) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		return coachcommentMapper.updateByOrderId(po,orderId);
	}
	public int updateByCourseId(CoachCommentVo coachcommentVo,Integer courseId) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		return coachcommentMapper.updateByCourseId(po,courseId);
	}
	public int updateByScore(CoachCommentVo coachcommentVo,Integer score) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		return coachcommentMapper.updateByScore(po,score);
	}
	public int updateByCtid(CoachCommentVo coachcommentVo,String ctid) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		return coachcommentMapper.updateByCtid(po,ctid);
	}
	public int updateByOneWord(CoachCommentVo coachcommentVo,String oneWord) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		return coachcommentMapper.updateByOneWord(po,oneWord);
	}
	public int updateByCotime(CoachCommentVo coachcommentVo,Date cotime) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		return coachcommentMapper.updateByCotime(po,cotime);
	}
	public int updateNotNullByObject(CoachCommentVo coachcommentVo,CoachCommentVo search) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		CoachCommentPo searchPo=BeanCopy.copyAll(search,CoachCommentPo.class);
		return coachcommentMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(CoachCommentVo coachcommentVo,CoachCommentVo search) throws Exception {
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		CoachCommentPo searchPo=BeanCopy.copyAll(search,CoachCommentPo.class);
		return coachcommentMapper.updateAllByObject(po,searchPo);
	}
	public CoachCommentVo queryCoachCommentById(Integer ccid,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=coachcommentMapper.queryCoachCommentById(ccid,postSql,sqlFileld);
		CoachCommentVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,CoachCommentVo.class);
		}
		return vo;
	}
	public List<CoachCommentVo> queryCoachCommentByIds(List<Integer> ids,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachCommentPo> poList=coachcommentMapper.queryCoachCommentByIds(ids,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByObjectAnd(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		List<CoachCommentPo> poList=coachcommentMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByObjectOr(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		List<CoachCommentPo> poList=coachcommentMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByCcid(Integer ccid,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachCommentPo> poList=coachcommentMapper.queryByCcid(ccid,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByStudentId(Long studentId,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachCommentPo> poList=coachcommentMapper.queryByStudentId(studentId,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByCoachId(Long coachId,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachCommentPo> poList=coachcommentMapper.queryByCoachId(coachId,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByOrderId(String orderId,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachCommentPo> poList=coachcommentMapper.queryByOrderId(orderId,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByCourseId(Integer courseId,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachCommentPo> poList=coachcommentMapper.queryByCourseId(courseId,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByScore(Integer score,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachCommentPo> poList=coachcommentMapper.queryByScore(score,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByCtid(String ctid,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachCommentPo> poList=coachcommentMapper.queryByCtid(ctid,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByOneWord(String oneWord,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachCommentPo> poList=coachcommentMapper.queryByOneWord(oneWord,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByCotime(Date cotime,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachCommentPo> poList=coachcommentMapper.queryByCotime(cotime,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByNew0(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		List<CoachCommentPo> poList=coachcommentMapper.queryByNew0(po,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByNew1(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		List<CoachCommentPo> poList=coachcommentMapper.queryByNew1(po,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByNew2(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		List<CoachCommentPo> poList=coachcommentMapper.queryByNew2(po,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByNew3(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		List<CoachCommentPo> poList=coachcommentMapper.queryByNew3(po,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByNew4(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		List<CoachCommentPo> poList=coachcommentMapper.queryByNew4(po,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByNew5(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		List<CoachCommentPo> poList=coachcommentMapper.queryByNew5(po,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByNew6(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		List<CoachCommentPo> poList=coachcommentMapper.queryByNew6(po,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachCommentVo> queryByNew7(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery) throws Exception {
		String postSql=coachcommentQuery.getSqlPost();
		String sqlFileld=coachcommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachCommentPo po=BeanCopy.copyAll(coachcommentVo,CoachCommentPo.class);
		List<CoachCommentPo> poList=coachcommentMapper.queryByNew7(po,postSql,sqlFileld);
		List<CoachCommentVo> voList=new ArrayList<CoachCommentVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachCommentVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public int countByCoachId(Long coachId) throws Exception {
		String postSql="";
		String sqlFileld="";
		CoachCommentPo search=new CoachCommentPo();
		search.setCoachId(coachId);
		int number=coachcommentMapper.countByObjectAnd(search, postSql, sqlFileld);
		return number;
	}
}
