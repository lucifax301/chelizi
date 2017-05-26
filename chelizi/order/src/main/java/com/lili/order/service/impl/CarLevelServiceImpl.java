package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.CarLevelMapper;
import com.lili.order.dao.po.CarLevelPo;
import com.lili.order.service.CarLevelService;
import com.lili.order.vo.CarLevelQuery;
import com.lili.order.vo.CarLevelVo;

public class CarLevelServiceImpl implements CarLevelService {

	@Autowired
	CarLevelMapper carLevelMapper;;
	public void addCarLevel(CarLevelVo carLevelVo) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		carLevelMapper.addCarLevel(po);
	}
	public void addCarLevelList(List<CarLevelVo> carLevelVoList) throws Exception {
		List<CarLevelPo> poList=BeanCopy.copyList(carLevelVoList,CarLevelPo.class,BeanCopy.COPYALL);
		carLevelMapper.addCarLevelList(poList);
	}
	public void delCarLevelById(Integer calid) throws Exception {
		carLevelMapper.delCarLevelById(calid);
	}
	public void delCarLevelByIds(List<Integer> ids) throws Exception {
		carLevelMapper.delCarLevelByIds(ids);
	}
	public void delCarLevelByObj(CarLevelVo carLevelVo) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		carLevelMapper.delCarLevelByObj(po);
	}
	public void saveCarLevel(CarLevelVo carLevelVo) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		carLevelMapper.saveCarLevel(po);
	}
	public void saveCarLevelList(List<CarLevelVo> carLevelVoList) throws Exception {
		List<CarLevelPo> poList=BeanCopy.copyList(carLevelVoList,CarLevelPo.class,BeanCopy.COPYALL);
		carLevelMapper.saveCarLevelList(poList);
	}
	public int updateByCalid(CarLevelVo carLevelVo,Integer calid) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		return carLevelMapper.updateByCalid(po,calid);
	}
	public int updateByName(CarLevelVo carLevelVo,String name) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		return carLevelMapper.updateByName(po,name);
	}
	public int updateByPrate(CarLevelVo carLevelVo,Integer prate) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		return carLevelMapper.updateByPrate(po,prate);
	}
	public int updateByIsdel(CarLevelVo carLevelVo,Integer isdel) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		return carLevelMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(CarLevelVo carLevelVo,Integer cuid) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		return carLevelMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(CarLevelVo carLevelVo,Integer muid) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		return carLevelMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(CarLevelVo carLevelVo,Date ctime) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		return carLevelMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(CarLevelVo carLevelVo,String mtime) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		return carLevelMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(CarLevelVo carLevelVo,CarLevelVo search) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		CarLevelPo searchPo=BeanCopy.copyAll(search,CarLevelPo.class);
		return carLevelMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(CarLevelVo carLevelVo,CarLevelVo search) throws Exception {
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		CarLevelPo searchPo=BeanCopy.copyAll(search,CarLevelPo.class);
		return carLevelMapper.updateAllByObject(po,searchPo);
	}
	public CarLevelVo queryCarLevelById(Integer calid,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=carLevelMapper.queryCarLevelById(calid,postSql,sqlFileld);
		CarLevelVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,CarLevelVo.class);
		}
		return vo;
	}
	public List<CarLevelVo> queryCarLevelByIds(List<Integer> ids,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CarLevelPo> poList=carLevelMapper.queryCarLevelByIds(ids,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByObjectAnd(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		List<CarLevelPo> poList=carLevelMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByObjectOr(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		List<CarLevelPo> poList=carLevelMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByCalid(Integer calid,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CarLevelPo> poList=carLevelMapper.queryByCalid(calid,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByName(String name,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CarLevelPo> poList=carLevelMapper.queryByName(name,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByPrate(Integer prate,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CarLevelPo> poList=carLevelMapper.queryByPrate(prate,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByIsdel(Integer isdel,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CarLevelPo> poList=carLevelMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByCuid(Integer cuid,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CarLevelPo> poList=carLevelMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByMuid(Integer muid,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CarLevelPo> poList=carLevelMapper.queryByMuid(muid,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByCtime(Date ctime,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CarLevelPo> poList=carLevelMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByMtime(String mtime,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CarLevelPo> poList=carLevelMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByNew0(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		List<CarLevelPo> poList=carLevelMapper.queryByNew0(po,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByNew1(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		List<CarLevelPo> poList=carLevelMapper.queryByNew1(po,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByNew2(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		List<CarLevelPo> poList=carLevelMapper.queryByNew2(po,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByNew3(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		List<CarLevelPo> poList=carLevelMapper.queryByNew3(po,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByNew4(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		List<CarLevelPo> poList=carLevelMapper.queryByNew4(po,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByNew5(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		List<CarLevelPo> poList=carLevelMapper.queryByNew5(po,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByNew6(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		List<CarLevelPo> poList=carLevelMapper.queryByNew6(po,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CarLevelVo> queryByNew7(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery) throws Exception {
		String postSql=carLevelQuery.getSqlPost();
		String sqlFileld=carLevelQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CarLevelPo po=BeanCopy.copyAll(carLevelVo,CarLevelPo.class);
		List<CarLevelPo> poList=carLevelMapper.queryByNew7(po,postSql,sqlFileld);
		List<CarLevelVo> voList=new ArrayList<CarLevelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CarLevelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
