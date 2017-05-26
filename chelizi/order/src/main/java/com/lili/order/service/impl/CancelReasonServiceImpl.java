package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.CancelReasonMapper;
import com.lili.order.dao.po.CancelReasonPo;
import com.lili.order.service.CancelReasonService;
import com.lili.order.vo.CancelReasonQuery;
import com.lili.order.vo.CancelReasonVo;

public class CancelReasonServiceImpl implements CancelReasonService {

	@Autowired
	CancelReasonMapper cancelReasonMapper;;
	public void addCancelReason(CancelReasonVo cancelReasonVo) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		cancelReasonMapper.addCancelReason(po);
	}
	public void addCancelReasonList(List<CancelReasonVo> cancelReasonVoList) throws Exception {
		List<CancelReasonPo> poList=BeanCopy.copyList(cancelReasonVoList,CancelReasonPo.class,BeanCopy.COPYALL);
		cancelReasonMapper.addCancelReasonList(poList);
	}
	public void delCancelReasonById(Integer crid) throws Exception {
		cancelReasonMapper.delCancelReasonById(crid);
	}
	public void delCancelReasonByIds(List<Integer> ids) throws Exception {
		cancelReasonMapper.delCancelReasonByIds(ids);
	}
	public void delCancelReasonByObj(CancelReasonVo cancelReasonVo) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		cancelReasonMapper.delCancelReasonByObj(po);
	}
	public void saveCancelReason(CancelReasonVo cancelReasonVo) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		cancelReasonMapper.saveCancelReason(po);
	}
	public void saveCancelReasonList(List<CancelReasonVo> cancelReasonVoList) throws Exception {
		List<CancelReasonPo> poList=BeanCopy.copyList(cancelReasonVoList,CancelReasonPo.class,BeanCopy.COPYALL);
		cancelReasonMapper.saveCancelReasonList(poList);
	}
	public int updateByCrid(CancelReasonVo cancelReasonVo,Integer crid) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		return cancelReasonMapper.updateByCrid(po,crid);
	}
	public int updateByReason(CancelReasonVo cancelReasonVo,String reason) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		return cancelReasonMapper.updateByReason(po,reason);
	}
	public int updateByUtype(CancelReasonVo cancelReasonVo,Integer utype) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		return cancelReasonMapper.updateByUtype(po,utype);
	}
	public int updateByIsdel(CancelReasonVo cancelReasonVo,Integer isdel) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		return cancelReasonMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(CancelReasonVo cancelReasonVo,Integer cuid) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		return cancelReasonMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(CancelReasonVo cancelReasonVo,Integer muid) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		return cancelReasonMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(CancelReasonVo cancelReasonVo,Date ctime) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		return cancelReasonMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(CancelReasonVo cancelReasonVo,String mtime) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		return cancelReasonMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(CancelReasonVo cancelReasonVo,CancelReasonVo search) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		CancelReasonPo searchPo=BeanCopy.copyAll(search,CancelReasonPo.class);
		return cancelReasonMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(CancelReasonVo cancelReasonVo,CancelReasonVo search) throws Exception {
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		CancelReasonPo searchPo=BeanCopy.copyAll(search,CancelReasonPo.class);
		return cancelReasonMapper.updateAllByObject(po,searchPo);
	}
	public CancelReasonVo queryCancelReasonById(Integer crid,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=cancelReasonMapper.queryCancelReasonById(crid,postSql,sqlFileld);
		CancelReasonVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,CancelReasonVo.class);
		}
		return vo;
	}
	public List<CancelReasonVo> queryCancelReasonByIds(List<Integer> ids,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CancelReasonPo> poList=cancelReasonMapper.queryCancelReasonByIds(ids,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByObjectAnd(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		List<CancelReasonPo> poList=cancelReasonMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByObjectOr(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		List<CancelReasonPo> poList=cancelReasonMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByCrid(Integer crid,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CancelReasonPo> poList=cancelReasonMapper.queryByCrid(crid,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByReason(String reason,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CancelReasonPo> poList=cancelReasonMapper.queryByReason(reason,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByUtype(Integer utype,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CancelReasonPo> poList=cancelReasonMapper.queryByUtype(utype,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByIsdel(Integer isdel,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CancelReasonPo> poList=cancelReasonMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByCuid(Integer cuid,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CancelReasonPo> poList=cancelReasonMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByMuid(Integer muid,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CancelReasonPo> poList=cancelReasonMapper.queryByMuid(muid,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByCtime(Date ctime,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CancelReasonPo> poList=cancelReasonMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByMtime(String mtime,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CancelReasonPo> poList=cancelReasonMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByNew0(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		List<CancelReasonPo> poList=cancelReasonMapper.queryByNew0(po,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByNew1(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		List<CancelReasonPo> poList=cancelReasonMapper.queryByNew1(po,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByNew2(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		List<CancelReasonPo> poList=cancelReasonMapper.queryByNew2(po,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByNew3(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		List<CancelReasonPo> poList=cancelReasonMapper.queryByNew3(po,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByNew4(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		List<CancelReasonPo> poList=cancelReasonMapper.queryByNew4(po,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByNew5(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		List<CancelReasonPo> poList=cancelReasonMapper.queryByNew5(po,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByNew6(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		List<CancelReasonPo> poList=cancelReasonMapper.queryByNew6(po,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CancelReasonVo> queryByNew7(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery) throws Exception {
		String postSql=cancelReasonQuery.getSqlPost();
		String sqlFileld=cancelReasonQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CancelReasonPo po=BeanCopy.copyAll(cancelReasonVo,CancelReasonPo.class);
		List<CancelReasonPo> poList=cancelReasonMapper.queryByNew7(po,postSql,sqlFileld);
		List<CancelReasonVo> voList=new ArrayList<CancelReasonVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CancelReasonVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
