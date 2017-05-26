package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.BasePriceMapper;
import com.lili.order.dao.po.BasePricePo;
import com.lili.order.service.BasePriceService;
import com.lili.order.vo.BasePriceQuery;
import com.lili.order.vo.BasePriceVo;

public class BasePriceServiceImpl implements BasePriceService {

	@Autowired
	BasePriceMapper basePriceMapper;;
	public void addBasePrice(BasePriceVo basePriceVo) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		basePriceMapper.addBasePrice(po);
	}
	public void addBasePriceList(List<BasePriceVo> basePriceVoList) throws Exception {
		List<BasePricePo> poList=BeanCopy.copyList(basePriceVoList,BasePricePo.class,BeanCopy.COPYALL);
		basePriceMapper.addBasePriceList(poList);
	}
	public void delBasePriceById(Integer bpid) throws Exception {
		basePriceMapper.delBasePriceById(bpid);
	}
	public void delBasePriceByIds(List<Integer> ids) throws Exception {
		basePriceMapper.delBasePriceByIds(ids);
	}
	public void delBasePriceByObj(BasePriceVo basePriceVo) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		basePriceMapper.delBasePriceByObj(po);
	}
	public void saveBasePrice(BasePriceVo basePriceVo) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		basePriceMapper.saveBasePrice(po);
	}
	public void saveBasePriceList(List<BasePriceVo> basePriceVoList) throws Exception {
		List<BasePricePo> poList=BeanCopy.copyList(basePriceVoList,BasePricePo.class,BeanCopy.COPYALL);
		basePriceMapper.saveBasePriceList(poList);
	}
	public int updateByBpid(BasePriceVo basePriceVo,Integer bpid) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByBpid(po,bpid);
	}
	public int updateByCityId(BasePriceVo basePriceVo,Integer cityId) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByCityId(po,cityId);
	}
	public int updateByCourseId(BasePriceVo basePriceVo,Integer courseId) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByCourseId(po,courseId);
	}
	public int updateByColid(BasePriceVo basePriceVo,Integer colid) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByColid(po,colid);
	}
	public int updateByPrice(BasePriceVo basePriceVo,Integer price) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByPrice(po,price);
	}
	public int updateByAllowance(BasePriceVo basePriceVo,Integer allowance) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByAllowance(po,allowance);
	}
	public int updateByVerify(BasePriceVo basePriceVo,Integer verify) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByVerify(po,verify);
	}
	public int updateByIsdel(BasePriceVo basePriceVo,Integer isdel) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(BasePriceVo basePriceVo,Integer cuid) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(BasePriceVo basePriceVo,Integer muid) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(BasePriceVo basePriceVo,Date ctime) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(BasePriceVo basePriceVo,String mtime) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		return basePriceMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(BasePriceVo basePriceVo,BasePriceVo search) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		BasePricePo searchPo=BeanCopy.copyAll(search,BasePricePo.class);
		return basePriceMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(BasePriceVo basePriceVo,BasePriceVo search) throws Exception {
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		BasePricePo searchPo=BeanCopy.copyAll(search,BasePricePo.class);
		return basePriceMapper.updateAllByObject(po,searchPo);
	}
	public BasePriceVo queryBasePriceById(Integer bpid,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=basePriceMapper.queryBasePriceById(bpid,postSql,sqlFileld);
		BasePriceVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,BasePriceVo.class);
		}
		return vo;
	}
	public List<BasePriceVo> queryBasePriceByIds(List<Integer> ids,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryBasePriceByIds(ids,postSql,sqlFileld);
		List<BasePriceVo> voList=new ArrayList<BasePriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<BasePriceVo> queryByObjectAnd(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		List<BasePricePo> poList=basePriceMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<BasePriceVo> voList=new ArrayList<BasePriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<BasePriceVo> queryByObjectOr(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		List<BasePricePo> poList=basePriceMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<BasePriceVo> voList=new ArrayList<BasePriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<BasePriceVo> queryByBpid(Integer bpid,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByBpid(bpid,postSql,sqlFileld);
		List<BasePriceVo> voList=new ArrayList<BasePriceVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<BasePriceVo> queryByCityId(Integer cityId,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByCityId(cityId,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByCourseId(Integer courseId,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByCourseId(courseId,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByColid(Integer colid,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByColid(colid,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByPrice(Integer price,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByPrice(price,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByAllowance(Integer allowance,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByAllowance(allowance,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByVerify(Integer verify,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByVerify(verify,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByIsdel(Integer isdel,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByCuid(Integer cuid,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByMuid(Integer muid,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByMuid(muid,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByCtime(Date ctime,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByMtime(String mtime,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<BasePricePo> poList=basePriceMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByNew0(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		List<BasePricePo> poList=basePriceMapper.queryByNew0(po,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByNew1(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		List<BasePricePo> poList=basePriceMapper.queryByNew1(po,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByNew2(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		List<BasePricePo> poList=basePriceMapper.queryByNew2(po,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByNew3(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		List<BasePricePo> poList=basePriceMapper.queryByNew3(po,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByNew4(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		List<BasePricePo> poList=basePriceMapper.queryByNew4(po,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByNew5(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		List<BasePricePo> poList=basePriceMapper.queryByNew5(po,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByNew6(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		List<BasePricePo> poList=basePriceMapper.queryByNew6(po,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<BasePriceVo> queryByNew7(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery) throws Exception {
		String postSql=basePriceQuery.getSqlPost();
		String sqlFileld=basePriceQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		BasePricePo po=BeanCopy.copyAll(basePriceVo,BasePricePo.class);
		List<BasePricePo> poList=basePriceMapper.queryByNew7(po,postSql,sqlFileld);
		List<BasePriceVo> voList=BeanCopy.copyList(poList,BasePriceVo.class,BeanCopy.COPYALL);
		return voList;
	}

}
