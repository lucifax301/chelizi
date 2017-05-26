package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.UnitPricePo;

public interface UnitPriceMapper{

  public void addUnitPrice(UnitPricePo unitPricePo);
  public void addUnitPriceList(List<UnitPricePo> unitPricePoList);
  public void delUnitPriceById(Integer upid);
  public void delUnitPriceByIds(List<Integer> ids);
  public void delUnitPriceByObj(UnitPricePo unitPricePo);
  public void saveUnitPrice(UnitPricePo unitPricePo);
  public void saveUnitPriceList(List<UnitPricePo> unitPricePoList);
  public int updateByUpid(UnitPricePo unitPricePo,Integer upid);
  public int updateByCityId(UnitPricePo unitPricePo,Integer cityId);
  public int updateByCourseId(UnitPricePo unitPricePo,Integer courseId);
  public int updateByColid(UnitPricePo unitPricePo,Integer colid);
  public int updateByCalid(UnitPricePo unitPricePo,Integer calid);
  public int updateByDftype(UnitPricePo unitPricePo,Integer dftype);
  public int updateByTstart(UnitPricePo unitPricePo,Date tstart);
  public int updateByTend(UnitPricePo unitPricePo,Date tend);
  public int updateByPrice(UnitPricePo unitPricePo,Integer price);
  public int updateByAllowance(UnitPricePo unitPricePo,Integer allowance);
  public int updateByVerify(UnitPricePo unitPricePo,Integer verify);
  public int updateByIsdel(UnitPricePo unitPricePo,Integer isdel);
  public int updateByCuid(UnitPricePo unitPricePo,Integer cuid);
  public int updateByMuid(UnitPricePo unitPricePo,Integer muid);
  public int updateByCtime(UnitPricePo unitPricePo,Date ctime);
  public int updateByMtime(UnitPricePo unitPricePo,String mtime);
  public int updateNotNullByObject(UnitPricePo unitPricePo,UnitPricePo search);
  public int updateAllByObject(UnitPricePo unitPricePo,UnitPricePo search);
  public UnitPricePo queryUnitPriceById(Integer upid,String postSql,String queryFields);
  public List<UnitPricePo> queryUnitPriceByIds(List<Integer> ids,String postSql,String queryFields);
  public List<UnitPricePo> queryByObjectAnd(UnitPricePo unitPricePo,String postSql,String queryFields);
  public List<UnitPricePo> queryByObjectOr(UnitPricePo unitPricePo,String postSql,String queryFields);
  public List<UnitPricePo> queryByUpid(Integer upid,String postSql,String queryFields);
  public List<UnitPricePo> queryByCityId(Integer cityId,String postSql,String queryFields);
  public List<UnitPricePo> queryByCourseId(Integer courseId,String postSql,String queryFields);
  public List<UnitPricePo> queryByColid(Integer colid,String postSql,String queryFields);
  public List<UnitPricePo> queryByCalid(Integer calid,String postSql,String queryFields);
  public List<UnitPricePo> queryByDftype(Integer dftype,String postSql,String queryFields);
  public List<UnitPricePo> queryByTstart(Date tstart,String postSql,String queryFields);
  public List<UnitPricePo> queryByTend(Date tend,String postSql,String queryFields);
  public List<UnitPricePo> queryByPrice(Integer price,String postSql,String queryFields);
  public List<UnitPricePo> queryByAllowance(Integer allowance,String postSql,String queryFields);
  public List<UnitPricePo> queryByVerify(Integer verify,String postSql,String queryFields);
  public List<UnitPricePo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<UnitPricePo> queryByCuid(Integer cuid,String postSql,String queryFields);
  public List<UnitPricePo> queryByMuid(Integer muid,String postSql,String queryFields);
  public List<UnitPricePo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<UnitPricePo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<UnitPricePo> queryByNew0(UnitPricePo unitPricePo,String postSql,String queryFields);
  public List<UnitPricePo> queryByNew1(UnitPricePo unitPricePo,String postSql,String queryFields);
  public List<UnitPricePo> queryByNew2(UnitPricePo unitPricePo,String postSql,String queryFields);
  public List<UnitPricePo> queryByNew3(UnitPricePo unitPricePo,String postSql,String queryFields);
  public List<UnitPricePo> queryByNew4(UnitPricePo unitPricePo,String postSql,String queryFields);
  public List<UnitPricePo> queryByNew5(UnitPricePo unitPricePo,String postSql,String queryFields);
  public List<UnitPricePo> queryByNew6(UnitPricePo unitPricePo,String postSql,String queryFields);
  public List<UnitPricePo> queryByNew7(UnitPricePo unitPricePo,String postSql,String queryFields);

}
