package com.lili.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.order.dao.mapper.TimeRateMapper;
import com.lili.order.dao.po.TimeRatePo;
import com.lili.order.service.TimeRateService;
import com.lili.order.vo.TimeRateQuery;
import com.lili.order.vo.TimeRateVo;

public class TimeRateServiceImpl implements TimeRateService {

	@Autowired
	TimeRateMapper timeRateMapper;;
	public void addTimeRate(TimeRateVo timeRateVo) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		timeRateMapper.addTimeRate(po);
	}
	public void addTimeRateList(List<TimeRateVo> timeRateVoList) throws Exception {
		List<TimeRatePo> poList=BeanCopy.copyList(timeRateVoList,TimeRatePo.class,BeanCopy.COPYALL);
		timeRateMapper.addTimeRateList(poList);
	}
	public void delTimeRateById(Integer tpid) throws Exception {
		timeRateMapper.delTimeRateById(tpid);
	}
	public void delTimeRateByIds(List<Integer> ids) throws Exception {
		timeRateMapper.delTimeRateByIds(ids);
	}
	public void delTimeRateByObj(TimeRateVo timeRateVo) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		timeRateMapper.delTimeRateByObj(po);
	}
	public void saveTimeRate(TimeRateVo timeRateVo) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		timeRateMapper.saveTimeRate(po);
	}
	public void saveTimeRateList(List<TimeRateVo> timeRateVoList) throws Exception {
		List<TimeRatePo> poList=BeanCopy.copyList(timeRateVoList,TimeRatePo.class,BeanCopy.COPYALL);
		timeRateMapper.saveTimeRateList(poList);
	}
	public int updateByTpid(TimeRateVo timeRateVo,Integer tpid) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByTpid(po,tpid);
	}
	public int updateByPtype(TimeRateVo timeRateVo,Integer ptype) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByPtype(po,ptype);
	}
	public int updateByTitle(TimeRateVo timeRateVo,String title) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByTitle(po,title);
	}
	public int updateByYint(TimeRateVo timeRateVo,Integer yint) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByYint(po,yint);
	}
	public int updateByMint(TimeRateVo timeRateVo,Integer mint) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByMint(po,mint);
	}
	public int updateByWstart(TimeRateVo timeRateVo,Integer wstart) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByWstart(po,wstart);
	}
	public int updateByWend(TimeRateVo timeRateVo,Integer wend) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByWend(po,wend);
	}
	public int updateByDstart(TimeRateVo timeRateVo,Integer dstart) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByDstart(po,dstart);
	}
	public int updateByDend(TimeRateVo timeRateVo,Integer dend) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByDend(po,dend);
	}
	public int updateByHstart(TimeRateVo timeRateVo,Integer hstart) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByHstart(po,hstart);
	}
	public int updateByHend(TimeRateVo timeRateVo,Integer hend) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByHend(po,hend);
	}
	public int updateByPrate(TimeRateVo timeRateVo,Integer prate) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByPrate(po,prate);
	}
	public int updateByVerify(TimeRateVo timeRateVo,Integer verify) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByVerify(po,verify);
	}
	public int updateByIsdel(TimeRateVo timeRateVo,Integer isdel) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(TimeRateVo timeRateVo,Integer cuid) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(TimeRateVo timeRateVo,Integer muid) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(TimeRateVo timeRateVo,Date ctime) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(TimeRateVo timeRateVo,String mtime) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		return timeRateMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(TimeRateVo timeRateVo,TimeRateVo search) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		TimeRatePo searchPo=BeanCopy.copyAll(search,TimeRatePo.class);
		return timeRateMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(TimeRateVo timeRateVo,TimeRateVo search) throws Exception {
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		TimeRatePo searchPo=BeanCopy.copyAll(search,TimeRatePo.class);
		return timeRateMapper.updateAllByObject(po,searchPo);
	}
	public TimeRateVo queryTimeRateById(Integer tpid,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=timeRateMapper.queryTimeRateById(tpid,postSql,sqlFileld);
		TimeRateVo vo=BeanCopy.copyAll(po,TimeRateVo.class);
		return vo;
	}
	public List<TimeRateVo> queryTimeRateByIds(List<Integer> ids,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryTimeRateByIds(ids,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByObjectAnd(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		List<TimeRatePo> poList=timeRateMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByObjectOr(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		List<TimeRatePo> poList=timeRateMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByTpid(Integer tpid,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByTpid(tpid,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByPtype(Integer ptype,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByPtype(ptype,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByTitle(String title,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByTitle(title,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByYint(Integer yint,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByYint(yint,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByMint(Integer mint,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByMint(mint,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByWstart(Integer wstart,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByWstart(wstart,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByWend(Integer wend,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByWend(wend,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByDstart(Integer dstart,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByDstart(dstart,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByDend(Integer dend,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByDend(dend,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByHstart(Integer hstart,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByHstart(hstart,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByHend(Integer hend,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByHend(hend,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByPrate(Integer prate,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByPrate(prate,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByVerify(Integer verify,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByVerify(verify,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByIsdel(Integer isdel,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByCuid(Integer cuid,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByMuid(Integer muid,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByMuid(muid,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByCtime(Date ctime,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByMtime(String mtime,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<TimeRatePo> poList=timeRateMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByDate(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		List<TimeRatePo> poList=timeRateMapper.queryByNew0(po,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByWeek(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		List<TimeRatePo> poList=timeRateMapper.queryByNew1(po,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByNew2(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		List<TimeRatePo> poList=timeRateMapper.queryByNew2(po,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByNew3(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		List<TimeRatePo> poList=timeRateMapper.queryByNew3(po,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByNew4(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		List<TimeRatePo> poList=timeRateMapper.queryByNew4(po,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByNew5(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		List<TimeRatePo> poList=timeRateMapper.queryByNew5(po,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByNew6(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		List<TimeRatePo> poList=timeRateMapper.queryByNew6(po,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}
	public List<TimeRateVo> queryByNew7(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery) throws Exception {
		String postSql=timeRateQuery.getSqlPost();
		String sqlFileld=timeRateQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		TimeRatePo po=BeanCopy.copyAll(timeRateVo,TimeRatePo.class);
		List<TimeRatePo> poList=timeRateMapper.queryByNew7(po,postSql,sqlFileld);
		List<TimeRateVo> voList=BeanCopy.copyList(poList,TimeRateVo.class,BeanCopy.COPYALL);
		return voList;
	}

}
