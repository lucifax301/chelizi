package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.TimeRatePo;

public interface TimeRateMapper{

  public void addTimeRate(TimeRatePo timeRatePo);
  public void addTimeRateList(List<TimeRatePo> timeRatePoList);
  public void delTimeRateById(Integer tpid);
  public void delTimeRateByIds(List<Integer> ids);
  public void delTimeRateByObj(TimeRatePo timeRatePo);
  public void saveTimeRate(TimeRatePo timeRatePo);
  public void saveTimeRateList(List<TimeRatePo> timeRatePoList);
  public int updateByTpid(TimeRatePo timeRatePo,Integer tpid);
  public int updateByPtype(TimeRatePo timeRatePo,Integer ptype);
  public int updateByTitle(TimeRatePo timeRatePo,String title);
  public int updateByYint(TimeRatePo timeRatePo,Integer yint);
  public int updateByMint(TimeRatePo timeRatePo,Integer mint);
  public int updateByWstart(TimeRatePo timeRatePo,Integer wstart);
  public int updateByWend(TimeRatePo timeRatePo,Integer wend);
  public int updateByDstart(TimeRatePo timeRatePo,Integer dstart);
  public int updateByDend(TimeRatePo timeRatePo,Integer dend);
  public int updateByHstart(TimeRatePo timeRatePo,Integer hstart);
  public int updateByHend(TimeRatePo timeRatePo,Integer hend);
  public int updateByPrate(TimeRatePo timeRatePo,Integer prate);
  public int updateByVerify(TimeRatePo timeRatePo,Integer verify);
  public int updateByIsdel(TimeRatePo timeRatePo,Integer isdel);
  public int updateByCuid(TimeRatePo timeRatePo,Integer cuid);
  public int updateByMuid(TimeRatePo timeRatePo,Integer muid);
  public int updateByCtime(TimeRatePo timeRatePo,Date ctime);
  public int updateByMtime(TimeRatePo timeRatePo,String mtime);
  public int updateNotNullByObject(TimeRatePo timeRatePo,TimeRatePo search);
  public int updateAllByObject(TimeRatePo timeRatePo,TimeRatePo search);
  public TimeRatePo queryTimeRateById(Integer tpid,String postSql,String queryFields);
  public List<TimeRatePo> queryTimeRateByIds(List<Integer> ids,String postSql,String queryFields);
  public List<TimeRatePo> queryByObjectAnd(TimeRatePo timeRatePo,String postSql,String queryFields);
  public List<TimeRatePo> queryByObjectOr(TimeRatePo timeRatePo,String postSql,String queryFields);
  public List<TimeRatePo> queryByTpid(Integer tpid,String postSql,String queryFields);
  public List<TimeRatePo> queryByPtype(Integer ptype,String postSql,String queryFields);
  public List<TimeRatePo> queryByTitle(String title,String postSql,String queryFields);
  public List<TimeRatePo> queryByYint(Integer yint,String postSql,String queryFields);
  public List<TimeRatePo> queryByMint(Integer mint,String postSql,String queryFields);
  public List<TimeRatePo> queryByWstart(Integer wstart,String postSql,String queryFields);
  public List<TimeRatePo> queryByWend(Integer wend,String postSql,String queryFields);
  public List<TimeRatePo> queryByDstart(Integer dstart,String postSql,String queryFields);
  public List<TimeRatePo> queryByDend(Integer dend,String postSql,String queryFields);
  public List<TimeRatePo> queryByHstart(Integer hstart,String postSql,String queryFields);
  public List<TimeRatePo> queryByHend(Integer hend,String postSql,String queryFields);
  public List<TimeRatePo> queryByPrate(Integer prate,String postSql,String queryFields);
  public List<TimeRatePo> queryByVerify(Integer verify,String postSql,String queryFields);
  public List<TimeRatePo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<TimeRatePo> queryByCuid(Integer cuid,String postSql,String queryFields);
  public List<TimeRatePo> queryByMuid(Integer muid,String postSql,String queryFields);
  public List<TimeRatePo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<TimeRatePo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<TimeRatePo> queryByNew0(TimeRatePo timeRatePo,String postSql,String queryFields);
  public List<TimeRatePo> queryByNew1(TimeRatePo timeRatePo,String postSql,String queryFields);
  public List<TimeRatePo> queryByNew2(TimeRatePo timeRatePo,String postSql,String queryFields);
  public List<TimeRatePo> queryByNew3(TimeRatePo timeRatePo,String postSql,String queryFields);
  public List<TimeRatePo> queryByNew4(TimeRatePo timeRatePo,String postSql,String queryFields);
  public List<TimeRatePo> queryByNew5(TimeRatePo timeRatePo,String postSql,String queryFields);
  public List<TimeRatePo> queryByNew6(TimeRatePo timeRatePo,String postSql,String queryFields);
  public List<TimeRatePo> queryByNew7(TimeRatePo timeRatePo,String postSql,String queryFields);

}
