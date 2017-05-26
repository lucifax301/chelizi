package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.OrderComplainMapper;
import com.lili.order.dao.po.OrderComplainPo;
import com.lili.order.service.OrderComplainService;
import com.lili.order.vo.OrderComplainQuery;
import com.lili.order.vo.OrderComplainVo;

public class OrderComplainServiceImpl implements OrderComplainService {

	@Autowired
	OrderComplainMapper orderComplainMapper;;
	public void addOrderComplain(OrderComplainVo orderComplainVo) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		orderComplainMapper.addOrderComplain(po);
	}
	public void addOrderComplainList(List<OrderComplainVo> orderComplainVoList) throws Exception {
		List<OrderComplainPo> poList=BeanCopy.copyList(orderComplainVoList,OrderComplainPo.class,BeanCopy.COPYALL);
		orderComplainMapper.addOrderComplainList(poList);
	}
	public void delOrderComplainById(Integer cid) throws Exception {
		orderComplainMapper.delOrderComplainById(cid);
	}
	public void delOrderComplainByIds(List<Integer> ids) throws Exception {
		orderComplainMapper.delOrderComplainByIds(ids);
	}
	public void delOrderComplainByObj(OrderComplainVo orderComplainVo) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		orderComplainMapper.delOrderComplainByObj(po);
	}
	public void saveOrderComplain(OrderComplainVo orderComplainVo) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		orderComplainMapper.saveOrderComplain(po);
	}
	public void saveOrderComplainList(List<OrderComplainVo> orderComplainVoList) throws Exception {
		List<OrderComplainPo> poList=BeanCopy.copyList(orderComplainVoList,OrderComplainPo.class,BeanCopy.COPYALL);
		orderComplainMapper.saveOrderComplainList(poList);
	}
	public int updateByCid(OrderComplainVo orderComplainVo,Integer cid) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByCid(po,cid);
	}
	public int updateByOrderId(OrderComplainVo orderComplainVo,String orderId) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByOrderId(po,orderId);
	}
	public int updateByUid(OrderComplainVo orderComplainVo,Long uid) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByUid(po,uid);
	}
	public int updateByUtype(OrderComplainVo orderComplainVo,Integer utype) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByUtype(po,utype);
	}
	public int updateByComplain(OrderComplainVo orderComplainVo,String complain) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByComplain(po,complain);
	}
	public int updateByComplainTime(OrderComplainVo orderComplainVo,Date complainTime) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByComplainTime(po,complainTime);
	}
	public int updateByMobile(OrderComplainVo orderComplainVo,String mobile) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByMobile(po,mobile);
	}
	public int updateByPic(OrderComplainVo orderComplainVo,String pic) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByPic(po,pic);
	}
	public int updateByStaffId(OrderComplainVo orderComplainVo,Integer staffId) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByStaffId(po,staffId);
	}
	public int updateByResult(OrderComplainVo orderComplainVo,String result) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByResult(po,result);
	}
	public int updateByCstate(OrderComplainVo orderComplainVo,Integer cstate) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		return orderComplainMapper.updateByCstate(po,cstate);
	}
	public int updateNotNullByObject(OrderComplainVo orderComplainVo,OrderComplainVo search) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		OrderComplainPo searchPo=BeanCopy.copyAll(search,OrderComplainPo.class);
		return orderComplainMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(OrderComplainVo orderComplainVo,OrderComplainVo search) throws Exception {
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		OrderComplainPo searchPo=BeanCopy.copyAll(search,OrderComplainPo.class);
		return orderComplainMapper.updateAllByObject(po,searchPo);
	}
	public OrderComplainVo queryOrderComplainById(Integer cid,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=orderComplainMapper.queryOrderComplainById(cid,postSql,sqlFileld);
		OrderComplainVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,OrderComplainVo.class);
		}
		return vo;
	}
	public List<OrderComplainVo> queryOrderComplainByIds(List<Integer> ids,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryOrderComplainByIds(ids,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByObjectAnd(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		List<OrderComplainPo> poList=orderComplainMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByObjectOr(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		List<OrderComplainPo> poList=orderComplainMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByCid(Integer cid,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByCid(cid,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByOrderId(String orderId,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByOrderId(orderId,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByUid(Long uid,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByUid(uid,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByUtype(Integer utype,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByUtype(utype,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByComplain(String complain,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByComplain(complain,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByComplainTime(Date complainTime,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByComplainTime(complainTime,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByMobile(String mobile,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByMobile(mobile,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByPic(String pic,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByPic(pic,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByStaffId(Integer staffId,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByStaffId(staffId,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByResult(String result,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByResult(result,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByCstate(Integer cstate,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<OrderComplainPo> poList=orderComplainMapper.queryByCstate(cstate,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByNew0(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		List<OrderComplainPo> poList=orderComplainMapper.queryByNew0(po,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByNew1(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		List<OrderComplainPo> poList=orderComplainMapper.queryByNew1(po,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByNew2(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		List<OrderComplainPo> poList=orderComplainMapper.queryByNew2(po,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByNew3(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		List<OrderComplainPo> poList=orderComplainMapper.queryByNew3(po,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByNew4(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		List<OrderComplainPo> poList=orderComplainMapper.queryByNew4(po,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByNew5(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		List<OrderComplainPo> poList=orderComplainMapper.queryByNew5(po,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByNew6(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		List<OrderComplainPo> poList=orderComplainMapper.queryByNew6(po,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<OrderComplainVo> queryByNew7(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery) throws Exception {
		String postSql=orderComplainQuery.getSqlPost();
		String sqlFileld=orderComplainQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		OrderComplainPo po=BeanCopy.copyAll(orderComplainVo,OrderComplainPo.class);
		List<OrderComplainPo> poList=orderComplainMapper.queryByNew7(po,postSql,sqlFileld);
		List<OrderComplainVo> voList=new ArrayList<OrderComplainVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,OrderComplainVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
