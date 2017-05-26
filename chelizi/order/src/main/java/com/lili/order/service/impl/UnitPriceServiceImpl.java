package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.order.dao.mapper.CommonPriceMapper;
import com.lili.order.dao.mapper.UnitPriceMapper;
import com.lili.order.dao.po.CommonPricePo;
import com.lili.order.dao.po.UnitPricePo;
import com.lili.order.service.UnitPriceService;
import com.lili.order.vo.UnitPriceQuery;
import com.lili.order.vo.UnitPriceVo;

public class UnitPriceServiceImpl implements UnitPriceService {
	private Logger log=Logger.getLogger(UnitPriceServiceImpl.class);
	
	@Autowired
	UnitPriceMapper unitPriceMapper;
	@Autowired
	CommonPriceMapper commonPriceMapper;
	
	@Autowired
	RedisUtil redisUtil;
	
	public void addUnitPrice(UnitPriceVo unitPriceVo) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		unitPriceMapper.addUnitPrice(po);
	}
	public void addUnitPriceList(List<UnitPriceVo> unitPriceVoList) throws Exception {
		List<UnitPricePo> poList=BeanCopy.copyList(unitPriceVoList,UnitPricePo.class,BeanCopy.COPYALL);
		unitPriceMapper.addUnitPriceList(poList);
	}
	public void delUnitPriceById(Integer upid) throws Exception {
		unitPriceMapper.delUnitPriceById(upid);
	}
	public void delUnitPriceByIds(List<Integer> ids) throws Exception {
		unitPriceMapper.delUnitPriceByIds(ids);
	}
	public void delUnitPriceByObj(UnitPriceVo unitPriceVo) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		unitPriceMapper.delUnitPriceByObj(po);
	}
	public void saveUnitPrice(UnitPriceVo unitPriceVo) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		unitPriceMapper.saveUnitPrice(po);
	}
	public void saveUnitPriceList(List<UnitPriceVo> unitPriceVoList) throws Exception {
		List<UnitPricePo> poList=BeanCopy.copyList(unitPriceVoList,UnitPricePo.class,BeanCopy.COPYALL);
		unitPriceMapper.saveUnitPriceList(poList);
	}
	public int updateByUpid(UnitPriceVo unitPriceVo,Integer upid) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByUpid(po,upid);
	}
	public int updateByCityId(UnitPriceVo unitPriceVo,Integer cityId) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByCityId(po,cityId);
	}
	public int updateByCourseId(UnitPriceVo unitPriceVo,Integer courseId) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByCourseId(po,courseId);
	}
	public int updateByColid(UnitPriceVo unitPriceVo,Integer colid) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByColid(po,colid);
	}
	public int updateByCalid(UnitPriceVo unitPriceVo,Integer calid) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByCalid(po,calid);
	}
	public int updateByDftype(UnitPriceVo unitPriceVo,Integer dftype) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByDftype(po,dftype);
	}
	public int updateByTstart(UnitPriceVo unitPriceVo,Date tstart) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByTstart(po,tstart);
	}
	public int updateByTend(UnitPriceVo unitPriceVo,Date tend) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByTend(po,tend);
	}
	public int updateByPrice(UnitPriceVo unitPriceVo,Integer price) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByPrice(po,price);
	}
	public int updateByAllowance(UnitPriceVo unitPriceVo,Integer allowance) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByAllowance(po,allowance);
	}
	public int updateByVerify(UnitPriceVo unitPriceVo,Integer verify) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByVerify(po,verify);
	}
	public int updateByIsdel(UnitPriceVo unitPriceVo,Integer isdel) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(UnitPriceVo unitPriceVo,Integer cuid) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(UnitPriceVo unitPriceVo,Integer muid) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(UnitPriceVo unitPriceVo,Date ctime) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(UnitPriceVo unitPriceVo,String mtime) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		return unitPriceMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(UnitPriceVo unitPriceVo,UnitPriceVo search) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		UnitPricePo searchPo=BeanCopy.copyAll(search,UnitPricePo.class);
		return unitPriceMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(UnitPriceVo unitPriceVo,UnitPriceVo search) throws Exception {
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		UnitPricePo searchPo=BeanCopy.copyAll(search,UnitPricePo.class);
		return unitPriceMapper.updateAllByObject(po,searchPo);
	}
	public UnitPriceVo queryUnitPriceById(Integer upid,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=unitPriceMapper.queryUnitPriceById(upid,postSql,sqlFileld);
		UnitPriceVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,UnitPriceVo.class);
		}
		return vo;
	}
	public List<UnitPriceVo> queryUnitPriceByIds(List<Integer> ids,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryUnitPriceByIds(ids,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByObjectAnd(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		List<UnitPricePo> poList=unitPriceMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByObjectOr(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		List<UnitPricePo> poList=unitPriceMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByUpid(Integer upid,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByUpid(upid,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByCityId(Integer cityId,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByCityId(cityId,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByCourseId(Integer courseId,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByCourseId(courseId,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByColid(Integer colid,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByColid(colid,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByCalid(Integer calid,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByCalid(calid,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByDftype(Integer dftype,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByDftype(dftype,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByTstart(Date tstart,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByTstart(tstart,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByTend(Date tend,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByTend(tend,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByPrice(Integer price,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByPrice(price,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByAllowance(Integer allowance,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByAllowance(allowance,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByVerify(Integer verify,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByVerify(verify,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByIsdel(Integer isdel,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByCuid(Integer cuid,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByMuid(Integer muid,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByMuid(muid,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByCtime(Date ctime,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByMtime(String mtime,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<UnitPricePo> poList=unitPriceMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryBetween(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		List<UnitPricePo> poList=unitPriceMapper.queryByNew0(po,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByNew1(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		List<UnitPricePo> poList=unitPriceMapper.queryByNew1(po,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByNew2(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		List<UnitPricePo> poList=unitPriceMapper.queryByNew2(po,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByNew3(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		List<UnitPricePo> poList=unitPriceMapper.queryByNew3(po,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByNew4(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		List<UnitPricePo> poList=unitPriceMapper.queryByNew4(po,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByNew5(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		List<UnitPricePo> poList=unitPriceMapper.queryByNew5(po,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByNew6(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		List<UnitPricePo> poList=unitPriceMapper.queryByNew6(po,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<UnitPriceVo> queryByNew7(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery) throws Exception {
		String postSql=unitPriceQuery.getSqlPost();
		String sqlFileld=unitPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		UnitPricePo po=BeanCopy.copyAll(unitPriceVo,UnitPricePo.class);
		List<UnitPricePo> poList=unitPriceMapper.queryByNew7(po,postSql,sqlFileld);
		List<UnitPriceVo> voList=new ArrayList<UnitPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,UnitPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

	public int getCoachPrice(Date start, int cityId, int courseId, int colid,
			int calid,int dftype) {
		String key=null;
		Integer price=null;
		try {
			Date exact = DateUtil.getExactHour(start);
			key = this.getClass().getSimpleName() + "_"
					+ TimeUtil.getDateFormat(exact).replaceAll(" ", "-") + "_"
					+ cityId + "_" + courseId + "_" + colid + "_" + calid+"_"+dftype;
			price = redisUtil.get(key);
			if (price == null) {
				UnitPriceVo search = new UnitPriceVo();
				search.setCityId(cityId);
				search.setCourseId(courseId);
				search.setColid(colid);
				search.setCalid(calid);
				search.setTstart(exact);
				search.setDftype(dftype);
				List<UnitPriceVo> vos = this.queryBetween(search, new UnitPriceQuery());
				if(null != vos && vos.size()>0){
					UnitPriceVo vo = this
							.queryBetween(search, new UnitPriceQuery()).get(0);
					price = vo.getPrice();
					if (price == null || price <= 0) {
						log.error(price + " getCoachPrice 0,so set to default="
								+ key);
						price = OrderConstant.defaultPrice;
					} else {
						redisUtil.set(key, price,RedisExpireConstant.ORDERTIME.UNITPRICE);
					}
				}else{
					price = OrderConstant.defaultPrice;
				}
			}
		} catch (Exception e) {
			log.error(key + " getCoachPrice exception:" + e.getMessage(), e);
			price = OrderConstant.defaultPrice;
		}
		return price;
	}
	public int getCommonCoachPrice(Date start, int cityId, int courseId, int colid,
			int calid,int dftype) {
		String key=null;
		Integer price=null;
		try {
			int year=DateUtil.getYear(start);
			int month=DateUtil.getMonth(start);
			int day=DateUtil.getDay(start);
			int hour=DateUtil.getHour(start);
			int week=DateUtil.getWeek(start);
			String time=year+"-"+month+"-"+day+"-"+hour;
			key = this.getClass().getSimpleName() + "_"
					+ time + "_"
					+ cityId + "_" + courseId + "_" + colid + "_" + calid+"_"+dftype;
			price = redisUtil.get(key);
			if (price == null) {
				CommonPricePo search = new CommonPricePo();
				search.setCityId(cityId);
				search.setCourseId(courseId);
				search.setColid(colid);
				search.setCalid(calid);
				search.setDftype(dftype);
				search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				search.setVerify(OrderConstant.VERIFY.PASS);
				List<CommonPricePo> pos = commonPriceMapper.queryByObjectAnd(search, "order by length(tstart) desc", "");
				if(null != pos && pos.size()>0){
					for(int i=0;i<pos.size();i++) {
						String[] tstart=pos.get(i).getTstart().split("-");
						String[] tend=pos.get(i).getTend().split("-");
						if(tstart.length==4 &&
								year>=Integer.valueOf(tstart[0]) && year<=Integer.valueOf(tend[0]) 
								&& month >=Integer.valueOf(tstart[1]) && month<=Integer.valueOf(tend[1]) 
								&& day>=Integer.valueOf(tstart[2]) && day<=Integer.valueOf(tend[2])
								&& hour>=Integer.valueOf(tstart[3]) && hour<=Integer.valueOf(tend[3])) {
								price=pos.get(i).getPrice();
								//长度最大优先
								break;
						} else if (tstart.length==3 && 
								month >=Integer.valueOf(tstart[0]) && month<=Integer.valueOf(tend[0]) 
								&& day>=Integer.valueOf(tstart[1]) && day<=Integer.valueOf(tend[1])
								&& hour>=Integer.valueOf(tstart[2]) && hour<=Integer.valueOf(tend[2])) {
								price=pos.get(i).getPrice();
								//长度最大优先
								break;
						} else if (tstart.length==2 &&
								week >=Integer.valueOf(tstart[0]) && week <=Integer.valueOf(tend[0]) 
								&& hour>=Integer.valueOf(tstart[1]) && hour<=Integer.valueOf(tend[1])) {
								price=pos.get(i).getPrice();
								//长度最大优先
								break;
						} 
					}
					if (price == null || price <= 0) {
						log.error(price + " getCommonCoachPrice 0,so set to default="
								+ key);
						price = OrderConstant.defaultPrice;
					} else {
						log.info(key + " from db getCommonCoachPrice price=" + price);
						redisUtil.set(key, price,RedisExpireConstant.ORDERTIME.UNITPRICE);
					}
				}else{
					price = OrderConstant.defaultPrice;
					log.error(key + " getCommonCoachPrice has null price，so use default=" + price);
				}
			} else {
//				log.debug(key + " redis getCommonCoachPrice price=" + price);
			}
		} catch (Exception e) {
			log.error(key + " getCommonCoachPrice exception:" + e.getMessage(), e);
			price = OrderConstant.defaultPrice;
		}
		return price;
	}
}
