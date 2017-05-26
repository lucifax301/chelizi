package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.OrderCancelMapper;
import com.lili.order.dao.po.OrderCancelPo;
import com.lili.order.service.OrderCancelService;
import com.lili.order.vo.OrderCancelQuery;
import com.lili.order.vo.OrderCancelVo;

public class OrderCancelServiceImpl implements OrderCancelService {

	@Autowired
	OrderCancelMapper orderCancelMapper;;
	public void addOrderCancel(OrderCancelVo orderCancelVo) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		orderCancelMapper.addOrderCancel(po);
	}
	public void addOrderCancelList(List<OrderCancelVo> orderCancelVoList) throws Exception {
		List<OrderCancelPo> poList=BeanCopy.copyList(orderCancelVoList,OrderCancelPo.class,BeanCopy.COPYALL);
		orderCancelMapper.addOrderCancelList(poList);
	}
	public void delOrderCancelById(String orderId) throws Exception {
		orderCancelMapper.delOrderCancelById(orderId);
	}
	public void delOrderCancelByIds(List<String> ids) throws Exception {
		orderCancelMapper.delOrderCancelByIds(ids);
	}
	public void delOrderCancelByObj(OrderCancelVo orderCancelVo) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		orderCancelMapper.delOrderCancelByObj(po);
	}
	public void saveOrderCancel(OrderCancelVo orderCancelVo) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		orderCancelMapper.saveOrderCancel(po);
	}
	public void saveOrderCancelList(List<OrderCancelVo> orderCancelVoList) throws Exception {
		List<OrderCancelPo> poList=BeanCopy.copyList(orderCancelVoList,OrderCancelPo.class,BeanCopy.COPYALL);
		orderCancelMapper.saveOrderCancelList(poList);
	}
	public int updateByOrderId(OrderCancelVo orderCancelVo,String orderId) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		return orderCancelMapper.updateByOrderId(po,orderId);
	}
	public int updateByUcancel(OrderCancelVo orderCancelVo,Integer ucancel) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		return orderCancelMapper.updateByUcancel(po,ucancel);
	}
	public int updateByUduty(OrderCancelVo orderCancelVo,Integer uduty) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		return orderCancelMapper.updateByUduty(po,uduty);
	}
	public int updateByRetype(OrderCancelVo orderCancelVo,Integer retype) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		return orderCancelMapper.updateByRetype(po,retype);
	}
	public int updateByReseaon(OrderCancelVo orderCancelVo,String reseaon) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		return orderCancelMapper.updateByReseaon(po,reseaon);
	}
	public int updateByCltime(OrderCancelVo orderCancelVo,Date cltime) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		return orderCancelMapper.updateByCltime(po,cltime);
	}
	public int updateByPstate(OrderCancelVo orderCancelVo,Integer pstate) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		return orderCancelMapper.updateByPstate(po,pstate);
	}
	public int updateNotNullByObject(OrderCancelVo orderCancelVo,OrderCancelVo search) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		OrderCancelPo searchPo=BeanCopy.copyAll(search,OrderCancelPo.class);
		return orderCancelMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(OrderCancelVo orderCancelVo,OrderCancelVo search) throws Exception {
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		OrderCancelPo searchPo=BeanCopy.copyAll(search,OrderCancelPo.class);
		return orderCancelMapper.updateAllByObject(po,searchPo);
	}
	public OrderCancelVo queryOrderCancelById(String orderId,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=orderCancelMapper.queryOrderCancelById(orderId,postSql,sqlFileld);
		OrderCancelVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,OrderCancelVo.class);
		}
		return vo;
	}
	public List<OrderCancelVo> queryOrderCancelByIds(List<String> ids,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderCancelPo> poList=orderCancelMapper.queryOrderCancelByIds(ids,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByObjectAnd(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		List<OrderCancelPo> poList=orderCancelMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByObjectOr(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		List<OrderCancelPo> poList=orderCancelMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByOrderId(String orderId,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderCancelPo> poList=orderCancelMapper.queryByOrderId(orderId,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByUcancel(Integer ucancel,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderCancelPo> poList=orderCancelMapper.queryByUcancel(ucancel,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByUduty(Integer uduty,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderCancelPo> poList=orderCancelMapper.queryByUduty(uduty,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByRetype(Integer retype,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderCancelPo> poList=orderCancelMapper.queryByRetype(retype,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByReseaon(String reseaon,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderCancelPo> poList=orderCancelMapper.queryByReseaon(reseaon,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByCltime(Date cltime,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderCancelPo> poList=orderCancelMapper.queryByCltime(cltime,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByPstate(Integer pstate,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderCancelPo> poList=orderCancelMapper.queryByPstate(pstate,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByNew0(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		List<OrderCancelPo> poList=orderCancelMapper.queryByNew0(po,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByNew1(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		List<OrderCancelPo> poList=orderCancelMapper.queryByNew1(po,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByNew2(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		List<OrderCancelPo> poList=orderCancelMapper.queryByNew2(po,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByNew3(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		List<OrderCancelPo> poList=orderCancelMapper.queryByNew3(po,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByNew4(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		List<OrderCancelPo> poList=orderCancelMapper.queryByNew4(po,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByNew5(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		List<OrderCancelPo> poList=orderCancelMapper.queryByNew5(po,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByNew6(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		List<OrderCancelPo> poList=orderCancelMapper.queryByNew6(po,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderCancelVo> queryByNew7(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery) throws Exception {
		String postSql=orderCancelQuery.getSqlPost();
		String sqlFileld=orderCancelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderCancelPo po=BeanCopy.copyAll(orderCancelVo,OrderCancelPo.class);
		List<OrderCancelPo> poList=orderCancelMapper.queryByNew7(po,postSql,sqlFileld);
		List<OrderCancelVo> voList=new ArrayList<OrderCancelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderCancelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
