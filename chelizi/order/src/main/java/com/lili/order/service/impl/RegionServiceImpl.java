package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.RegionMapper;
import com.lili.order.dao.po.RegionPo;
import com.lili.order.service.RegionService;
import com.lili.order.vo.RegionQuery;
import com.lili.order.vo.RegionVo;

public class RegionServiceImpl implements RegionService {

	@Autowired
	RegionMapper regionMapper;;
	public void addRegion(RegionVo regionVo) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		regionMapper.addRegion(po);
	}
	public void addRegionList(List<RegionVo> regionVoList) throws Exception {
		List<RegionPo> poList=BeanCopy.copyList(regionVoList,RegionPo.class,BeanCopy.COPYALL);
		regionMapper.addRegionList(poList);
	}
	public void delRegionById(Integer rid) throws Exception {
		regionMapper.delRegionById(rid);
	}
	public void delRegionByIds(List<Integer> ids) throws Exception {
		regionMapper.delRegionByIds(ids);
	}
	public void delRegionByObj(RegionVo regionVo) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		regionMapper.delRegionByObj(po);
	}
	public void saveRegion(RegionVo regionVo) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		regionMapper.saveRegion(po);
	}
	public void saveRegionList(List<RegionVo> regionVoList) throws Exception {
		List<RegionPo> poList=BeanCopy.copyList(regionVoList,RegionPo.class,BeanCopy.COPYALL);
		regionMapper.saveRegionList(poList);
	}
	public int updateByRid(RegionVo regionVo,Integer rid) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		return regionMapper.updateByRid(po,rid);
	}
	public int updateByRegion(RegionVo regionVo,String region) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		return regionMapper.updateByRegion(po,region);
	}
	public int updateByRlevel(RegionVo regionVo,Integer rlevel) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		return regionMapper.updateByRlevel(po,rlevel);
	}
	public int updateByPid(RegionVo regionVo,Integer pid) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		return regionMapper.updateByPid(po,pid);
	}
	public int updateByIsdel(RegionVo regionVo,Integer isdel) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		return regionMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(RegionVo regionVo,Integer cuid) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		return regionMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(RegionVo regionVo,Integer muid) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		return regionMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(RegionVo regionVo,Date ctime) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		return regionMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(RegionVo regionVo,String mtime) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		return regionMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(RegionVo regionVo,RegionVo search) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		RegionPo searchPo=BeanCopy.copyAll(search,RegionPo.class);
		return regionMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(RegionVo regionVo,RegionVo search) throws Exception {
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		RegionPo searchPo=BeanCopy.copyAll(search,RegionPo.class);
		return regionMapper.updateAllByObject(po,searchPo);
	}
	public RegionVo queryRegionById(Integer rid,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=regionMapper.queryRegionById(rid,postSql,sqlFileld);
		RegionVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,RegionVo.class);
		}
		return vo;
	}
	public List<RegionVo> queryRegionByIds(List<Integer> ids,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RegionPo> poList=regionMapper.queryRegionByIds(ids,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByObjectAnd(RegionVo regionVo,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		List<RegionPo> poList=regionMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByObjectOr(RegionVo regionVo,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		List<RegionPo> poList=regionMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByRid(Integer rid,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RegionPo> poList=regionMapper.queryByRid(rid,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByRegion(String region,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RegionPo> poList=regionMapper.queryByRegion(region,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByRlevel(Integer rlevel,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RegionPo> poList=regionMapper.queryByRlevel(rlevel,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByPid(Integer pid,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RegionPo> poList=regionMapper.queryByPid(pid,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByIsdel(Integer isdel,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RegionPo> poList=regionMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByCuid(Integer cuid,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RegionPo> poList=regionMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByMuid(Integer muid,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RegionPo> poList=regionMapper.queryByMuid(muid,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByCtime(Date ctime,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RegionPo> poList=regionMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByMtime(String mtime,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<RegionPo> poList=regionMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByNew0(RegionVo regionVo,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		List<RegionPo> poList=regionMapper.queryByNew0(po,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByNew1(RegionVo regionVo,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		List<RegionPo> poList=regionMapper.queryByNew1(po,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByNew2(RegionVo regionVo,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		List<RegionPo> poList=regionMapper.queryByNew2(po,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByNew3(RegionVo regionVo,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		List<RegionPo> poList=regionMapper.queryByNew3(po,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByNew4(RegionVo regionVo,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		List<RegionPo> poList=regionMapper.queryByNew4(po,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByNew5(RegionVo regionVo,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		List<RegionPo> poList=regionMapper.queryByNew5(po,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByNew6(RegionVo regionVo,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		List<RegionPo> poList=regionMapper.queryByNew6(po,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<RegionVo> queryByNew7(RegionVo regionVo,RegionQuery regionQuery) throws Exception {
		String postSql=regionQuery.getSqlPost();
		String sqlFileld=regionQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		RegionPo po=BeanCopy.copyAll(regionVo,RegionPo.class);
		List<RegionPo> poList=regionMapper.queryByNew7(po,postSql,sqlFileld);
		List<RegionVo> voList=new ArrayList<RegionVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,RegionVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
