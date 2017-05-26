package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.order.dao.mapper.CoachStatisticMapper;
import com.lili.order.dao.po.CoachStatisticPo;
import com.lili.order.service.CoachStatisticService;
import com.lili.order.vo.CoachStatisticQuery;
import com.lili.order.vo.CoachStatisticVo;

public class CoachStatisticServiceImpl implements CoachStatisticService {

	@Autowired
	CoachStatisticMapper coachStatisticMapper;;
	public void addCoachStatistic(CoachStatisticVo coachStatisticVo) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		coachStatisticMapper.addCoachStatistic(po);
	}
	public void addCoachStatisticList(List<CoachStatisticVo> coachStatisticVoList) throws Exception {
		List<CoachStatisticPo> poList=BeanCopy.copyList(coachStatisticVoList,CoachStatisticPo.class,BeanCopy.COPYALL);
		coachStatisticMapper.addCoachStatisticList(poList);
	}
	public void delCoachStatisticById(Integer csid) throws Exception {
		coachStatisticMapper.delCoachStatisticById(csid);
	}
	public void delCoachStatisticByIds(List<Integer> ids) throws Exception {
		coachStatisticMapper.delCoachStatisticByIds(ids);
	}
	public void delCoachStatisticByObj(CoachStatisticVo coachStatisticVo) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		coachStatisticMapper.delCoachStatisticByObj(po);
	}
	public void saveCoachStatistic(CoachStatisticVo coachStatisticVo) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		coachStatisticMapper.saveCoachStatistic(po);
	}
	public void saveCoachStatisticList(List<CoachStatisticVo> coachStatisticVoList) throws Exception {
		List<CoachStatisticPo> poList=BeanCopy.copyList(coachStatisticVoList,CoachStatisticPo.class,BeanCopy.COPYALL);
		coachStatisticMapper.saveCoachStatisticList(poList);
	}
	public int updateByCsid(CoachStatisticVo coachStatisticVo,Integer csid) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		return coachStatisticMapper.updateByCsid(po,csid);
	}
	public int updateByCoachId(CoachStatisticVo coachStatisticVo,Long coachId) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		return coachStatisticMapper.updateByCoachId(po,coachId);
	}
	public int updateByCurrDate(CoachStatisticVo coachStatisticVo,Integer currDate) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		return coachStatisticMapper.updateByCurrDate(po,currDate);
	}
	public int updateByOrderAccept(CoachStatisticVo coachStatisticVo,Integer orderAccept) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		return coachStatisticMapper.updateByOrderAccept(po,orderAccept);
	}
	public int updateByOrderRefuse(CoachStatisticVo coachStatisticVo,Integer orderRefuse) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		return coachStatisticMapper.updateByOrderRefuse(po,orderRefuse);
	}
	public int updateByOrderCancel(CoachStatisticVo coachStatisticVo,Integer orderCancel) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		return coachStatisticMapper.updateByOrderCancel(po,orderCancel);
	}
	public int updateByOrderComment(CoachStatisticVo coachStatisticVo,Integer orderComment) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		return coachStatisticMapper.updateByOrderComment(po,orderComment);
	}
	public int updateByOrderMoney(CoachStatisticVo coachStatisticVo,Integer orderMoney) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		return coachStatisticMapper.updateByOrderMoney(po,orderMoney);
	}
	public int updateByCommentScore(CoachStatisticVo coachStatisticVo,Integer commentScore) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		return coachStatisticMapper.updateByCommentScore(po,commentScore);
	}
	public int updateNotNullByObject(CoachStatisticVo coachStatisticVo,CoachStatisticVo search) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		CoachStatisticPo searchPo=BeanCopy.copyAll(search,CoachStatisticPo.class);
		return coachStatisticMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(CoachStatisticVo coachStatisticVo,CoachStatisticVo search) throws Exception {
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		CoachStatisticPo searchPo=BeanCopy.copyAll(search,CoachStatisticPo.class);
		return coachStatisticMapper.updateAllByObject(po,searchPo);
	}
	public CoachStatisticVo queryCoachStatisticById(Integer csid,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=coachStatisticMapper.queryCoachStatisticById(csid,postSql,sqlFileld);
		CoachStatisticVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,CoachStatisticVo.class);
		}
		return vo;
	}
	public List<CoachStatisticVo> queryCoachStatisticByIds(List<Integer> ids,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachStatisticPo> poList=coachStatisticMapper.queryCoachStatisticByIds(ids,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByObjectAnd(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByObjectOr(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByCsid(Integer csid,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByCsid(csid,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByCoachId(Long coachId,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByCoachId(coachId,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByCurrDate(Integer currDate,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByCurrDate(currDate,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByOrderAccept(Integer orderAccept,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByOrderAccept(orderAccept,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByOrderRefuse(Integer orderRefuse,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByOrderRefuse(orderRefuse,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByOrderCancel(Integer orderCancel,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByOrderCancel(orderCancel,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByOrderComment(Integer orderComment,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByOrderComment(orderComment,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByOrderMoney(Integer orderMoney,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByOrderMoney(orderMoney,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByCommentScore(Integer commentScore,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByCommentScore(commentScore,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByNew0(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByNew0(po,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByNew1(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByNew1(po,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByNew2(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByNew2(po,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByNew3(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByNew3(po,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByNew4(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByNew4(po,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByNew5(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByNew5(po,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByNew6(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByNew6(po,postSql,sqlFileld);
		List<CoachStatisticVo> voList=new ArrayList<CoachStatisticVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachStatisticVo> queryByNew7(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery) throws Exception {
		String postSql=coachStatisticQuery.getSqlPost();
		String sqlFileld=coachStatisticQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachStatisticPo po=BeanCopy.copyAll(coachStatisticVo,CoachStatisticPo.class);
		List<CoachStatisticPo> poList=coachStatisticMapper.queryByNew7(po,postSql,sqlFileld);
		List<CoachStatisticVo> voList=BeanCopy.copyList(poList,CoachStatisticVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public CoachStatisticVo getCoachStatistc(long coachId,String date) throws Exception {
		CoachStatisticVo coachStatisticVo=new CoachStatisticVo();
		coachStatisticVo.setCoachId(coachId);
		if(StringUtil.isNotNullAndNotEmpty(date)){
			coachStatisticVo.setCurrDate(Integer.valueOf(date));
		} else {
			coachStatisticVo.setCurrDate(Integer.valueOf(TimeUtil.getDateFormat(new Date(), "yyyyMMdd")));
		}
		List<CoachStatisticVo> list=queryByObjectAnd(coachStatisticVo,new CoachStatisticQuery());
		if(list!=null && !list.isEmpty()){
			coachStatisticVo=list.get(0);
		}
		return coachStatisticVo;
	}
	public int getCoachBonusByDate(String date, Long coachId) {
		int sum = 0;
		if (StringUtil.isNotNullAndNotEmpty(date)){
			sum = coachStatisticMapper.getCoachBonusByDate(date, coachId);
		} else {
			sum = coachStatisticMapper.getCoachBonusByDate(TimeUtil.getDateFormat(new Date(), "yyyyMMdd"), coachId);
		}
		return sum;
	}

}
