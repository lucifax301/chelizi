package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.VersionUtil;
import com.lili.order.dao.mapper.StuCommentMapper;
import com.lili.order.dao.po.StuCommentPo;
import com.lili.order.service.CommentTagService;
import com.lili.order.service.StuCommentService;
import com.lili.order.vo.CommentTagQuery;
import com.lili.order.vo.CommentTagVo;
import com.lili.order.vo.StuCommentQuery;
import com.lili.order.vo.StuCommentVo;
import com.lili.order.vo.StuOrderCommentVo;

public class StuCommentServiceImpl implements StuCommentService {
	private Logger log = Logger.getLogger(StuCommentServiceImpl.class);
			
	@Autowired
	StuCommentMapper stuCommentMapper;
	@Autowired
	CommentTagService commentTagService;
	
	public void addStuComment(StuCommentVo stuCommentVo) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		stuCommentMapper.addStuComment(po);
	}
	public void addStuCommentList(List<StuCommentVo> stuCommentVoList) throws Exception {
		List<StuCommentPo> poList=BeanCopy.copyList(stuCommentVoList,StuCommentPo.class,BeanCopy.COPYALL);
		stuCommentMapper.addStuCommentList(poList);
	}
	public void delStuCommentById(Integer id) throws Exception {
		stuCommentMapper.delStuCommentById(id);
	}
	public void delStuCommentByIds(List<Integer> ids) throws Exception {
		stuCommentMapper.delStuCommentByIds(ids);
	}
	public void delStuCommentByObj(StuCommentVo stuCommentVo) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		stuCommentMapper.delStuCommentByObj(po);
	}
	public void saveStuComment(StuCommentVo stuCommentVo) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		stuCommentMapper.saveStuComment(po);
	}
	public void saveStuCommentList(List<StuCommentVo> stuCommentVoList) throws Exception {
		List<StuCommentPo> poList=BeanCopy.copyList(stuCommentVoList,StuCommentPo.class,BeanCopy.COPYALL);
		stuCommentMapper.saveStuCommentList(poList);
	}
	public int updateByScid(StuCommentVo stuCommentVo,Integer id) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		return stuCommentMapper.updateByScid(po,id);
	}
	public int updateByStudentId(StuCommentVo stuCommentVo,Long studentId) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		return stuCommentMapper.updateByStudentId(po,studentId);
	}
	public int updateByCoachId(StuCommentVo stuCommentVo,Long coachId) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		return stuCommentMapper.updateByCoachId(po,coachId);
	}
	public int updateByOrderId(StuCommentVo stuCommentVo,String orderId) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		return stuCommentMapper.updateByOrderId(po,orderId);
	}
	public int updateByCourseId(StuCommentVo stuCommentVo,Integer courseId) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		return stuCommentMapper.updateByCourseId(po,courseId);
	}
	public int updateByCtid(StuCommentVo stuCommentVo,Integer ctid) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		return stuCommentMapper.updateByCtid(po,ctid);
	}
	public int updateByScore(StuCommentVo stuCommentVo,Integer score) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		return stuCommentMapper.updateByScore(po,score);
	}
	public int updateByOneWord(StuCommentVo stuCommentVo,String oneWord) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		return stuCommentMapper.updateByOneWord(po,oneWord);
	}
	public int updateByCotime(StuCommentVo stuCommentVo,Date cotime) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		return stuCommentMapper.updateByCotime(po,cotime);
	}
	public int updateNotNullByObject(StuCommentVo stuCommentVo,StuCommentVo search) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		StuCommentPo searchPo=BeanCopy.copyAll(search,StuCommentPo.class);
		return stuCommentMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(StuCommentVo stuCommentVo,StuCommentVo search) throws Exception {
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		StuCommentPo searchPo=BeanCopy.copyAll(search,StuCommentPo.class);
		return stuCommentMapper.updateAllByObject(po,searchPo);
	}
	public StuCommentVo queryStuCommentById(Integer id,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StuCommentPo po=stuCommentMapper.queryStuCommentById(id,postSql,sqlFileld);
		StuCommentVo vo=BeanCopy.copyAll(po,StuCommentVo.class);
		return vo;
	}
	public List<StuCommentVo> queryStuCommentByIds(List<Integer> ids,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StuCommentPo> poList=stuCommentMapper.queryStuCommentByIds(ids,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByObjectAnd(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		List<StuCommentPo> poList=stuCommentMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByObjectOr(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		List<StuCommentPo> poList=stuCommentMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByScid(Integer id,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StuCommentPo> poList=stuCommentMapper.queryByScid(id,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByStudentId(Long studentId,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StuCommentPo> poList=stuCommentMapper.queryByStudentId(studentId,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByCoachId(Long coachId,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StuCommentPo> poList=stuCommentMapper.queryByCoachId(coachId,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public StuOrderCommentVo queryByOrderId(String orderId,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StuCommentPo> history=stuCommentMapper.queryByOrderId(orderId,postSql,sqlFileld);
		CommentTagQuery ctq=new CommentTagQuery();
		ctq.setPageSize(500);
		List<CommentTagVo> tag=commentTagService.queryByType(OrderConstant.USETYPE.STUDENT, ctq);
		Map<Integer,String> tagMap=new HashMap<Integer,String>(); 
		for(CommentTagVo one:tag) {
			tagMap.put(one.getCtid(), one.getTag());
		}
		Set<String> idSet=new HashSet<String>();
		StuOrderCommentVo one =null;
		for(int i=0;i<history.size();i++){
			if(idSet.contains(history.get(i).getOrderId())){
				one.getCtids().add(history.get(i).getCtid());
				one.getTags().add(tagMap.get(history.get(i).getCtid()));
				one.getScores().add(history.get(i).getScore());
			} else {
				one=new  StuOrderCommentVo();
				BeanCopy.copyAll(history.get(i), one);
				List<Integer> ctid=new ArrayList<Integer>();
				ctid.add(history.get(i).getCtid());
				one.setCtids(ctid);
				List<Integer> score=new ArrayList<Integer>();
				score.add(history.get(i).getScore());
				one.setScores(score);
				List<String> tags=new ArrayList<String>();
				tags.add(tagMap.get(history.get(i).getCtid()));
				one.setTags(tags);
				idSet.add(history.get(i).getOrderId());
			}
		}
		return one;
	}
	public List<StuCommentVo> queryByCourseId(Integer courseId,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StuCommentPo> poList=stuCommentMapper.queryByCourseId(courseId,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByCtid(Integer ctid,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StuCommentPo> poList=stuCommentMapper.queryByCtid(ctid,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByScore(Integer score,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StuCommentPo> poList=stuCommentMapper.queryByScore(score,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByOneWord(String oneWord,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StuCommentPo> poList=stuCommentMapper.queryByOneWord(oneWord,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByCotime(Date cotime,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<StuCommentPo> poList=stuCommentMapper.queryByCotime(cotime,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByTags(List<Integer> ctids,Long studentId) throws Exception {
		StuCommentQuery stuCommentQuery=new StuCommentQuery();
		stuCommentQuery.setorderBy(" order by cotime desc, ctid asc");
		stuCommentQuery.setGroupBy(" group by ctid ");
		stuCommentQuery.setPageSize(ctids.size());
		String postSql=stuCommentQuery.getSqlPost();
		List<StuCommentPo> poList=stuCommentMapper.queryByNew0(ctids,postSql,studentId);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByNew1(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		List<StuCommentPo> poList=stuCommentMapper.queryByNew1(po,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByNew2(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		List<StuCommentPo> poList=stuCommentMapper.queryByNew2(po,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByNew3(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		List<StuCommentPo> poList=stuCommentMapper.queryByNew3(po,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByNew4(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		List<StuCommentPo> poList=stuCommentMapper.queryByNew4(po,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByNew5(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		List<StuCommentPo> poList=stuCommentMapper.queryByNew5(po,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByNew6(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		List<StuCommentPo> poList=stuCommentMapper.queryByNew6(po,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<StuCommentVo> queryByNew7(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery) throws Exception {
		String postSql=stuCommentQuery.getSqlPost();
		String sqlFileld=stuCommentQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		StuCommentPo po=BeanCopy.copyAll(stuCommentVo,StuCommentPo.class);
		List<StuCommentPo> poList=stuCommentMapper.queryByNew7(po,postSql,sqlFileld);
		List<StuCommentVo> voList=BeanCopy.copyList(poList,StuCommentVo.class,BeanCopy.COPYALL);
		return voList;
	}
	
	public List<StuOrderCommentVo> getStuCommentList(long studentId,int pageIndex,int pageSize,Integer courseId,Integer subjectId,String v){
		List<StuOrderCommentVo> result=new ArrayList<StuOrderCommentVo>();
		try {
			CommentTagQuery ctq=new CommentTagQuery();
			ctq.setPageSize(500);
			List<CommentTagVo> tag=commentTagService.queryByType(OrderConstant.USETYPE.STUDENT, ctq);
			Map<Integer,String> tagMap=new HashMap<Integer,String>(); 
			for(CommentTagVo one:tag) {
				tagMap.put(one.getCtid(), one.getTag());
			}
			StuCommentQuery query=new StuCommentQuery();
			query.setPageIndex(pageIndex);
			query.setPageSize(pageSize);
			if((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.8.0") > 0))) { //低于1.8.1版本不显示标签
				List<CommentTagVo> tag3=commentTagService.queryByType(OrderConstant.USETYPE.MANGER, ctq);
				for(CommentTagVo one3 : tag3) {
					tagMap.put(one3.getCtid(), one3.getTag());
				}
				query.setGroupBy("and score !=1 group by order_id");
			}
			else {
				query.setGroupBy("group by order_id");
			}
			
			query.setorderBy("order by cotime desc");
			StuCommentVo search=new StuCommentVo();
			search.setStudentId(studentId);			
			search.setCourseId(courseId);
			search.setSubjectId(subjectId);
			List<StuCommentVo> history=queryByObjectAnd(search, query);
			if(history !=null && !history.isEmpty()){
				String orderIn="and order_id in (";
				int i=0;
				for(;i<history.size()-1;i++){
					orderIn +="'"+history.get(i).getOrderId()+"',";
				}
				orderIn +="'"+history.get(i).getOrderId()+"')";
				query=new StuCommentQuery();
				query.setGroupBy(orderIn);
				query.setPageIndex(1);
				query.setPageSize(500);
				query.setorderBy("order by cotime desc, ctid asc");
				history=this.queryByStudentId(studentId, query);
				Set<String> idSet=new HashSet<String>();
				StuOrderCommentVo one=null;
				for(i=0;i<history.size();i++){
					if(idSet.contains(history.get(i).getOrderId())){
						one.getCtids().add(history.get(i).getCtid());
						one.getTags().add(tagMap.get(history.get(i).getCtid()));
						one.getScores().add(history.get(i).getScore());
					} else {
						if(one!=null){
							result.add(one);
						}
						one=new  StuOrderCommentVo();
						BeanCopy.copyAll(history.get(i), one);
						List<Integer> ctid=new ArrayList<Integer>();
						ctid.add(history.get(i).getCtid());
						one.setCtids(ctid);
						List<Integer> score=new ArrayList<Integer>();
						score.add(history.get(i).getScore());
						one.setScores(score);
						List<String> tags=new ArrayList<String>();
						tags.add(tagMap.get(history.get(i).getCtid()));
						one.setTags(tags);
						idSet.add(history.get(i).getOrderId());
					}
				}
				if(one!=null && !result.contains(one)){
					result.add(one);
				}
			}
		}catch(Exception e){
			result=null;
			log.error("getStuCommentList Exception:"+e.getMessage(),e);
		}
		return result;
	}
}
