package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.CommonPriceMapper;
import com.lili.order.dao.po.CommonPricePo;
import com.lili.order.service.CommonPriceService;
import com.lili.order.vo.CommonPriceQuery;
import com.lili.order.vo.CommonPriceVo;
@Service
public class CommonPriceServiceImpl implements CommonPriceService {

	@Autowired
	CommonPriceMapper commonPriceMapper;;
	public void addCommonPrice(CommonPriceVo commonPriceVo) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		commonPriceMapper.addCommonPrice(po);
	}
	public void addCommonPriceList(List<CommonPriceVo> commonPriceVoList) throws Exception {
		List<CommonPricePo> poList=BeanCopy.copyList(commonPriceVoList,CommonPricePo.class,BeanCopy.COPYALL);
		commonPriceMapper.addCommonPriceList(poList);
	}
	public void delCommonPriceById(Integer upid) throws Exception {
		commonPriceMapper.delCommonPriceById(upid);
	}
	public void delCommonPriceByIds(List<Integer> ids) throws Exception {
		commonPriceMapper.delCommonPriceByIds(ids);
	}
	public void delCommonPriceByObj(CommonPriceVo commonPriceVo) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		commonPriceMapper.delCommonPriceByObj(po);
	}
	public void saveCommonPrice(CommonPriceVo commonPriceVo) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		commonPriceMapper.saveCommonPrice(po);
	}
	public void saveCommonPriceList(List<CommonPriceVo> commonPriceVoList) throws Exception {
		List<CommonPricePo> poList=BeanCopy.copyList(commonPriceVoList,CommonPricePo.class,BeanCopy.COPYALL);
		commonPriceMapper.saveCommonPriceList(poList);
	}
	public int updateByUpid(CommonPriceVo commonPriceVo,Integer upid) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByUpid(po,upid);
	}
	public int updateByCityId(CommonPriceVo commonPriceVo,Integer cityId) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByCityId(po,cityId);
	}
	public int updateByCourseId(CommonPriceVo commonPriceVo,Integer courseId) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByCourseId(po,courseId);
	}
	public int updateByColid(CommonPriceVo commonPriceVo,Integer colid) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByColid(po,colid);
	}
	public int updateByCalid(CommonPriceVo commonPriceVo,Integer calid) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByCalid(po,calid);
	}
	public int updateByDftype(CommonPriceVo commonPriceVo,Integer dftype) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByDftype(po,dftype);
	}
	public int updateByTstart(CommonPriceVo commonPriceVo,String tstart) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByTstart(po,tstart);
	}
	public int updateByTend(CommonPriceVo commonPriceVo,String tend) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByTend(po,tend);
	}
	public int updateByPrice(CommonPriceVo commonPriceVo,Integer price) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByPrice(po,price);
	}
	public int updateByAllowance(CommonPriceVo commonPriceVo,Integer allowance) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByAllowance(po,allowance);
	}
	public int updateByVerify(CommonPriceVo commonPriceVo,Integer verify) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByVerify(po,verify);
	}
	public int updateByIsdel(CommonPriceVo commonPriceVo,Integer isdel) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(CommonPriceVo commonPriceVo,Integer cuid) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(CommonPriceVo commonPriceVo,Integer muid) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(CommonPriceVo commonPriceVo,Date ctime) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(CommonPriceVo commonPriceVo,String mtime) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		return commonPriceMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(CommonPriceVo commonPriceVo,CommonPriceVo search) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		CommonPricePo searchPo=BeanCopy.copyAll(search,CommonPricePo.class);
		return commonPriceMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(CommonPriceVo commonPriceVo,CommonPriceVo search) throws Exception {
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		CommonPricePo searchPo=BeanCopy.copyAll(search,CommonPricePo.class);
		return commonPriceMapper.updateAllByObject(po,searchPo);
	}
	public CommonPriceVo queryCommonPriceById(Integer upid,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=commonPriceMapper.queryCommonPriceById(upid,postSql,sqlFileld);
		CommonPriceVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,CommonPriceVo.class);
		}
		return vo;
	}
	public List<CommonPriceVo> queryCommonPriceByIds(List<Integer> ids,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryCommonPriceByIds(ids,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByObjectAnd(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		List<CommonPricePo> poList=commonPriceMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByObjectOr(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		List<CommonPricePo> poList=commonPriceMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByUpid(Integer upid,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByUpid(upid,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByCityId(Integer cityId,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByCityId(cityId,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByCourseId(Integer courseId,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByCourseId(courseId,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByColid(Integer colid,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByColid(colid,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByCalid(Integer calid,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByCalid(calid,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByDftype(Integer dftype,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByDftype(dftype,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByTstart(String tstart,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByTstart(tstart,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByTend(String tend,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByTend(tend,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByPrice(Integer price,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByPrice(price,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByAllowance(Integer allowance,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByAllowance(allowance,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByVerify(Integer verify,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByVerify(verify,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByIsdel(Integer isdel,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByCuid(Integer cuid,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByMuid(Integer muid,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByMuid(muid,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByCtime(Date ctime,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByMtime(String mtime,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CommonPricePo> poList=commonPriceMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByNew0(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		List<CommonPricePo> poList=commonPriceMapper.queryByNew0(po,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByNew1(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		List<CommonPricePo> poList=commonPriceMapper.queryByNew1(po,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByNew2(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		List<CommonPricePo> poList=commonPriceMapper.queryByNew2(po,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByNew3(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		List<CommonPricePo> poList=commonPriceMapper.queryByNew3(po,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByNew4(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		List<CommonPricePo> poList=commonPriceMapper.queryByNew4(po,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByNew5(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		List<CommonPricePo> poList=commonPriceMapper.queryByNew5(po,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByNew6(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		List<CommonPricePo> poList=commonPriceMapper.queryByNew6(po,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CommonPriceVo> queryByNew7(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery) throws Exception {
		String postSql=commonPriceQuery.getSqlPost();
		String sqlFileld=commonPriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CommonPricePo po=BeanCopy.copyAll(commonPriceVo,CommonPricePo.class);
		List<CommonPricePo> poList=commonPriceMapper.queryByNew7(po,postSql,sqlFileld);
		List<CommonPriceVo> voList=new ArrayList<CommonPriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CommonPriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
