package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.CommentTagMapper;
import com.lili.order.dao.po.CommentTagPo;
import com.lili.order.service.CommentTagService;
import com.lili.order.vo.CommentTagQuery;
import com.lili.order.vo.CommentTagVo;

public class CommentTagServiceImpl implements CommentTagService {

	@Autowired
	CommentTagMapper commentTagMapper;;
	public void addCommentTag(CommentTagVo commentTagVo) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		commentTagMapper.addCommentTag(po);
	}
	public void addCommentTagList(List<CommentTagVo> commentTagVoList) throws Exception {
		List<CommentTagPo> poList=BeanCopy.copyList(commentTagVoList,CommentTagPo.class,BeanCopy.COPYALL);
		commentTagMapper.addCommentTagList(poList);
	}
	public void delCommentTagById(Integer ctid) throws Exception {
		commentTagMapper.delCommentTagById(ctid);
	}
	public void delCommentTagByIds(List<Integer> ids) throws Exception {
		commentTagMapper.delCommentTagByIds(ids);
	}
	public void delCommentTagByObj(CommentTagVo commentTagVo) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		commentTagMapper.delCommentTagByObj(po);
	}
	public void saveCommentTag(CommentTagVo commentTagVo) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		commentTagMapper.saveCommentTag(po);
	}
	public void saveCommentTagList(List<CommentTagVo> commentTagVoList) throws Exception {
		List<CommentTagPo> poList=BeanCopy.copyList(commentTagVoList,CommentTagPo.class,BeanCopy.COPYALL);
		commentTagMapper.saveCommentTagList(poList);
	}
	public int updateByCtid(CommentTagVo commentTagVo,Integer ctid) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		return commentTagMapper.updateByCtid(po,ctid);
	}
	public int updateByTag(CommentTagVo commentTagVo,String tag) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		return commentTagMapper.updateByTag(po,tag);
	}
	public int updateByCourseId(CommentTagVo commentTagVo,Integer courseId) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		return commentTagMapper.updateByCourseId(po,courseId);
	}
	public int updateByType(CommentTagVo commentTagVo,Integer type) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		return commentTagMapper.updateByType(po,type);
	}
	public int updateByGoodBad(CommentTagVo commentTagVo,Integer goodBad) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		return commentTagMapper.updateByGoodBad(po,goodBad);
	}
	public int updateByIsdel(CommentTagVo commentTagVo,Integer isdel) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		return commentTagMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(CommentTagVo commentTagVo,Integer cuid) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		return commentTagMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(CommentTagVo commentTagVo,Integer muid) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		return commentTagMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(CommentTagVo commentTagVo,Date ctime) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		return commentTagMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(CommentTagVo commentTagVo,String mtime) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		return commentTagMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(CommentTagVo commentTagVo,CommentTagVo search) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		CommentTagPo searchPo=BeanCopy.copyAll(search,CommentTagPo.class);
		return commentTagMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(CommentTagVo commentTagVo,CommentTagVo search) throws Exception {
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		CommentTagPo searchPo=BeanCopy.copyAll(search,CommentTagPo.class);
		return commentTagMapper.updateAllByObject(po,searchPo);
	}
	public CommentTagVo queryCommentTagById(Integer ctid,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=commentTagMapper.queryCommentTagById(ctid,postSql,sqlFileld);
		CommentTagVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,CommentTagVo.class);
		}
		return vo;
	}
	public List<CommentTagVo> queryCommentTagByIds(List<Integer> ids,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryCommentTagByIds(ids,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByObjectAnd(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		List<CommentTagPo> poList=commentTagMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByObjectOr(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		List<CommentTagPo> poList=commentTagMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByCtid(Integer ctid,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryByCtid(ctid,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByTag(String tag,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryByTag(tag,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByCourseId(Integer courseId,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryByCourseId(courseId,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByType(Integer type,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryByType(type,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByGoodBad(Integer goodBad,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryByGoodBad(goodBad,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByIsdel(Integer isdel,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByCuid(Integer cuid,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByMuid(Integer muid,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryByMuid(muid,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByCtime(Date ctime,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByMtime(String mtime,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommentTagPo> poList=commentTagMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByNew0(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		List<CommentTagPo> poList=commentTagMapper.queryByNew0(po,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByNew1(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		List<CommentTagPo> poList=commentTagMapper.queryByNew1(po,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByNew2(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		List<CommentTagPo> poList=commentTagMapper.queryByNew2(po,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByNew3(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		List<CommentTagPo> poList=commentTagMapper.queryByNew3(po,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByNew4(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		List<CommentTagPo> poList=commentTagMapper.queryByNew4(po,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByNew5(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		List<CommentTagPo> poList=commentTagMapper.queryByNew5(po,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByNew6(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		List<CommentTagPo> poList=commentTagMapper.queryByNew6(po,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommentTagVo> queryByNew7(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery) throws Exception {
		String postSql=commentTagQuery.getSqlPost();
		String sqlFileld=commentTagQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommentTagPo po=BeanCopy.copyAll(commentTagVo,CommentTagPo.class);
		List<CommentTagPo> poList=commentTagMapper.queryByNew7(po,postSql,sqlFileld);
		List<CommentTagVo> voList=new ArrayList<CommentTagVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommentTagVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
