package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.TimeRateQuery;
import com.lili.order.vo.TimeRateVo;

public interface TimeRateService {

  public void addTimeRate(TimeRateVo timeRateVo)  throws Exception;
  public void addTimeRateList(List<TimeRateVo> timeRateVoList)  throws Exception;
  public void delTimeRateById(Integer tpid)  throws Exception;
  public void delTimeRateByIds(List<Integer> ids)  throws Exception;
  public void delTimeRateByObj(TimeRateVo timeRateVo)  throws Exception;
  public void saveTimeRate(TimeRateVo timeRateVo)  throws Exception;
  public void saveTimeRateList(List<TimeRateVo> timeRateVoList)  throws Exception;
  public int updateByTpid(TimeRateVo timeRateVo,Integer tpid)  throws Exception;
  public int updateByPtype(TimeRateVo timeRateVo,Integer ptype)  throws Exception;
  public int updateByTitle(TimeRateVo timeRateVo,String title)  throws Exception;
  public int updateByYint(TimeRateVo timeRateVo,Integer yint)  throws Exception;
  public int updateByMint(TimeRateVo timeRateVo,Integer mint)  throws Exception;
  public int updateByWstart(TimeRateVo timeRateVo,Integer wstart)  throws Exception;
  public int updateByWend(TimeRateVo timeRateVo,Integer wend)  throws Exception;
  public int updateByDstart(TimeRateVo timeRateVo,Integer dstart)  throws Exception;
  public int updateByDend(TimeRateVo timeRateVo,Integer dend)  throws Exception;
  public int updateByHstart(TimeRateVo timeRateVo,Integer hstart)  throws Exception;
  public int updateByHend(TimeRateVo timeRateVo,Integer hend)  throws Exception;
  public int updateByPrate(TimeRateVo timeRateVo,Integer prate)  throws Exception;
  public int updateByVerify(TimeRateVo timeRateVo,Integer verify)  throws Exception;
  public int updateByIsdel(TimeRateVo timeRateVo,Integer isdel)  throws Exception;
  public int updateByCuid(TimeRateVo timeRateVo,Integer cuid)  throws Exception;
  public int updateByMuid(TimeRateVo timeRateVo,Integer muid)  throws Exception;
  public int updateByCtime(TimeRateVo timeRateVo,Date ctime)  throws Exception;
  public int updateByMtime(TimeRateVo timeRateVo,String mtime)  throws Exception;
  public int updateNotNullByObject(TimeRateVo timeRateVo,TimeRateVo search)  throws Exception;
  public int updateAllByObject(TimeRateVo timeRateVo,TimeRateVo search)  throws Exception;
  public TimeRateVo queryTimeRateById(Integer tpid,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryTimeRateByIds(List<Integer> ids,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByObjectAnd(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByObjectOr(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByTpid(Integer tpid,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByPtype(Integer ptype,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByTitle(String title,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByYint(Integer yint,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByMint(Integer mint,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByWstart(Integer wstart,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByWend(Integer wend,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByDstart(Integer dstart,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByDend(Integer dend,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByHstart(Integer hstart,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByHend(Integer hend,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByPrate(Integer prate,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByVerify(Integer verify,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByIsdel(Integer isdel,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByCuid(Integer cuid,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByMuid(Integer muid,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByCtime(Date ctime,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByMtime(String mtime,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByDate(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByWeek(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByNew2(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByNew3(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByNew4(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByNew5(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByNew6(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery)  throws Exception;
  public List<TimeRateVo> queryByNew7(TimeRateVo timeRateVo,TimeRateQuery timeRateQuery)  throws Exception;

}
