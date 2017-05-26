package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.UnitPriceVo;
import com.lili.order.vo.UnitPriceQuery;

public interface UnitPriceService {

  public void addUnitPrice(UnitPriceVo unitPriceVo)  throws Exception;
  public void addUnitPriceList(List<UnitPriceVo> unitPriceVoList)  throws Exception;
  public void delUnitPriceById(Integer upid)  throws Exception;
  public void delUnitPriceByIds(List<Integer> ids)  throws Exception;
  public void delUnitPriceByObj(UnitPriceVo unitPriceVo)  throws Exception;
  public void saveUnitPrice(UnitPriceVo unitPriceVo)  throws Exception;
  public void saveUnitPriceList(List<UnitPriceVo> unitPriceVoList)  throws Exception;
  public int updateByUpid(UnitPriceVo unitPriceVo,Integer upid)  throws Exception;
  public int updateByCityId(UnitPriceVo unitPriceVo,Integer cityId)  throws Exception;
  public int updateByCourseId(UnitPriceVo unitPriceVo,Integer courseId)  throws Exception;
  public int updateByColid(UnitPriceVo unitPriceVo,Integer colid)  throws Exception;
  public int updateByCalid(UnitPriceVo unitPriceVo,Integer calid)  throws Exception;
  public int updateByDftype(UnitPriceVo unitPriceVo,Integer dftype)  throws Exception;
  public int updateByTstart(UnitPriceVo unitPriceVo,Date tstart)  throws Exception;
  public int updateByTend(UnitPriceVo unitPriceVo,Date tend)  throws Exception;
  public int updateByPrice(UnitPriceVo unitPriceVo,Integer price)  throws Exception;
  public int updateByAllowance(UnitPriceVo unitPriceVo,Integer allowance)  throws Exception;
  public int updateByVerify(UnitPriceVo unitPriceVo,Integer verify)  throws Exception;
  public int updateByIsdel(UnitPriceVo unitPriceVo,Integer isdel)  throws Exception;
  public int updateByCuid(UnitPriceVo unitPriceVo,Integer cuid)  throws Exception;
  public int updateByMuid(UnitPriceVo unitPriceVo,Integer muid)  throws Exception;
  public int updateByCtime(UnitPriceVo unitPriceVo,Date ctime)  throws Exception;
  public int updateByMtime(UnitPriceVo unitPriceVo,String mtime)  throws Exception;
  public int updateNotNullByObject(UnitPriceVo unitPriceVo,UnitPriceVo search)  throws Exception;
  public int updateAllByObject(UnitPriceVo unitPriceVo,UnitPriceVo search)  throws Exception;
  public UnitPriceVo queryUnitPriceById(Integer upid,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryUnitPriceByIds(List<Integer> ids,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByObjectAnd(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByObjectOr(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByUpid(Integer upid,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByCityId(Integer cityId,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByCourseId(Integer courseId,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByColid(Integer colid,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByCalid(Integer calid,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByDftype(Integer dftype,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByTstart(Date tstart,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByTend(Date tend,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByPrice(Integer price,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByAllowance(Integer allowance,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByVerify(Integer verify,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByIsdel(Integer isdel,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByCuid(Integer cuid,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByMuid(Integer muid,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByCtime(Date ctime,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByMtime(String mtime,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryBetween(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByNew1(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByNew2(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByNew3(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByNew4(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByNew5(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByNew6(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery)  throws Exception;
  public List<UnitPriceVo> queryByNew7(UnitPriceVo unitPriceVo,UnitPriceQuery unitPriceQuery)  throws Exception;
  public int getCoachPrice(Date start, int cityId, int courseId, int colid,int calid,int dftype);
  public int getCommonCoachPrice(Date start, int cityId, int courseId, int colid,int calid,int dftype);

}
