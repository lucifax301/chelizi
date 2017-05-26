package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.redis.RedisUtil;
import com.lili.order.dao.mapper.CoachScoreMapper;
import com.lili.order.dao.po.CoachScorePo;
import com.lili.order.service.CoachScoreService;
import com.lili.order.vo.CoachScoreQuery;
import com.lili.order.vo.CoachScoreVo;

public class CoachScoreServiceImpl implements CoachScoreService {

	@Autowired
	CoachScoreMapper coachScoreMapper;
	@Autowired
	RedisUtil redisUtil;
	
	public void addCoachScore(CoachScoreVo coachScoreVo) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		coachScoreMapper.addCoachScore(po);
	}
	public void addCoachScoreList(List<CoachScoreVo> coachScoreVoList) throws Exception {
		List<CoachScorePo> poList=BeanCopy.copyList(coachScoreVoList,CoachScorePo.class,BeanCopy.COPYALL);
		coachScoreMapper.addCoachScoreList(poList);
	}
	public void delCoachScoreById(Long coachId) throws Exception {
		coachScoreMapper.delCoachScoreById(coachId);
	}
	public void delCoachScoreByIds(List<Long> ids) throws Exception {
		coachScoreMapper.delCoachScoreByIds(ids);
	}
	public void delCoachScoreByObj(CoachScoreVo coachScoreVo) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		coachScoreMapper.delCoachScoreByObj(po);
	}
	public void saveCoachScore(CoachScoreVo coachScoreVo) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		coachScoreMapper.saveCoachScore(po);
		String key=getKey(coachScoreVo.getCoachId());
		redisUtil.set(key, coachScoreVo, RedisExpireConstant.ORDERTIME.COACHCOMMENT);
	}
	public void saveCoachScoreList(List<CoachScoreVo> coachScoreVoList) throws Exception {
		List<CoachScorePo> poList=BeanCopy.copyList(coachScoreVoList,CoachScorePo.class,BeanCopy.COPYALL);
		coachScoreMapper.saveCoachScoreList(poList);
	}
	public int updateByCoachId(CoachScoreVo coachScoreVo,Long coachId) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		return coachScoreMapper.updateByCoachId(po,coachId);
	}
	public int updateByAcceptOrder(CoachScoreVo coachScoreVo,Long acceptOrder) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		return coachScoreMapper.updateByAcceptOrder(po,acceptOrder);
	}
	public int updateByRefuseOrder(CoachScoreVo coachScoreVo,Long refuseOrder) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		return coachScoreMapper.updateByRefuseOrder(po,refuseOrder);
	}
	public int updateByCancelOrder(CoachScoreVo coachScoreVo,Long cancelOrder) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		return coachScoreMapper.updateByCancelOrder(po,cancelOrder);
	}
	public int updateByOrderTotal(CoachScoreVo coachScoreVo,Long orderTotal) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		return coachScoreMapper.updateByOrderTotal(po,orderTotal);
	}
	public int updateByScoreTotal(CoachScoreVo coachScoreVo,Long scoreTotal) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		return coachScoreMapper.updateByScoreTotal(po,scoreTotal);
	}
	public int updateByCompany(CoachScoreVo coachScoreVo,Integer company) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		return coachScoreMapper.updateByCompany(po,company);
	}
	public int updateNotNullByObject(CoachScoreVo coachScoreVo,CoachScoreVo search) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		CoachScorePo searchPo=BeanCopy.copyAll(search,CoachScorePo.class);
		return coachScoreMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(CoachScoreVo coachScoreVo,CoachScoreVo search) throws Exception {
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		CoachScorePo searchPo=BeanCopy.copyAll(search,CoachScorePo.class);
		return coachScoreMapper.updateAllByObject(po,searchPo);
	}
	public String getKey(Long coachId){
		String key=this.getClass().getSimpleName()+"_"+coachId;
		return key;
	}
	public CoachScoreVo queryCoachScoreById(Long coachId,CoachScoreQuery coachScoreQuery) throws Exception {
		String key=getKey(coachId);
		CoachScoreVo vo=redisUtil.get(key);
		if(vo==null){
			String postSql=coachScoreQuery.getSqlPost();
			String sqlFileld=coachScoreQuery.getSqlField();
			if(postSql==null) {
				postSql="";
			}
			CoachScorePo po=coachScoreMapper.queryCoachScoreById(coachId,postSql,sqlFileld);
			if(po!=null){
				vo=BeanCopy.copyAll(po,CoachScoreVo.class);
				redisUtil.set(key, vo, RedisExpireConstant.ORDERTIME.COACHCOMMENT);
			}
		}
		return vo;
	}
	public List<CoachScoreVo> queryCoachScoreByIds(List<Long> ids,CoachScoreQuery coachScoreQuery) throws Exception {
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		for(Long id:ids){
			CoachScoreVo vo=queryCoachScoreById(id,coachScoreQuery);
			if(vo!=null){
				voList.add(vo);
			}
		}
		return voList;
	}
	public Map<Long,Integer> queryByCoachIds(List<Long> ids) throws Exception {
		Map<Long,Integer> map=new HashMap<Long,Integer>();
		for(Long id:ids){
			CoachScoreVo vo=queryCoachScoreById(id,new CoachScoreQuery());
			if(vo!=null){
				long comment=vo.getOrderTotal();
				if(comment>0){
					map.put(id, (int)(vo.getScoreTotal()/comment));
				} else {
					map.put(id, OrderConstant.COACHSCORE);
				}
			}
		}
		return map;
	}
	
	public List<CoachScoreVo> queryByObjectAnd(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		List<CoachScorePo> poList=coachScoreMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByObjectOr(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		List<CoachScorePo> poList=coachScoreMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByCoachId(Long coachId,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachScorePo> poList=coachScoreMapper.queryByCoachId(coachId,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByAcceptOrder(Long acceptOrder,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachScorePo> poList=coachScoreMapper.queryByAcceptOrder(acceptOrder,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByRefuseOrder(Long refuseOrder,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachScorePo> poList=coachScoreMapper.queryByRefuseOrder(refuseOrder,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByCancelOrder(Long cancelOrder,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachScorePo> poList=coachScoreMapper.queryByCancelOrder(cancelOrder,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByOrderTotal(Long orderTotal,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachScorePo> poList=coachScoreMapper.queryByOrderTotal(orderTotal,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByScoreTotal(Long scoreTotal,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachScorePo> poList=coachScoreMapper.queryByScoreTotal(scoreTotal,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByCompany(Integer company,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachScorePo> poList=coachScoreMapper.queryByCompany(company,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByNew0(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		List<CoachScorePo> poList=coachScoreMapper.queryByNew0(po,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByNew1(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		List<CoachScorePo> poList=coachScoreMapper.queryByNew1(po,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByNew2(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		List<CoachScorePo> poList=coachScoreMapper.queryByNew2(po,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByNew3(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		List<CoachScorePo> poList=coachScoreMapper.queryByNew3(po,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByNew4(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		List<CoachScorePo> poList=coachScoreMapper.queryByNew4(po,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByNew5(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		List<CoachScorePo> poList=coachScoreMapper.queryByNew5(po,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByNew6(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		List<CoachScorePo> poList=coachScoreMapper.queryByNew6(po,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachScoreVo> queryByNew7(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery) throws Exception {
		String postSql=coachScoreQuery.getSqlPost();
		String sqlFileld=coachScoreQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachScorePo po=BeanCopy.copyAll(coachScoreVo,CoachScorePo.class);
		List<CoachScorePo> poList=coachScoreMapper.queryByNew7(po,postSql,sqlFileld);
		List<CoachScoreVo> voList=new ArrayList<CoachScoreVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachScoreVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
