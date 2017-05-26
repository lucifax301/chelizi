package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.BasePricePo;

public interface BasePriceMapper{

  public void addBasePrice(BasePricePo basePricePo);
  public void addBasePriceList(List<BasePricePo> basePricePoList);
  public void delBasePriceById(Integer bpid);
  public void delBasePriceByIds(List<Integer> ids);
  public void delBasePriceByObj(BasePricePo basePricePo);
  public void saveBasePrice(BasePricePo basePricePo);
  public void saveBasePriceList(List<BasePricePo> basePricePoList);
  public int updateByBpid(BasePricePo basePricePo,Integer bpid);
  public int updateByCityId(BasePricePo basePricePo,Integer cityId);
  public int updateByCourseId(BasePricePo basePricePo,Integer courseId);
  public int updateByColid(BasePricePo basePricePo,Integer colid);
  public int updateByPrice(BasePricePo basePricePo,Integer price);
  public int updateByAllowance(BasePricePo basePricePo,Integer allowance);
  public int updateByVerify(BasePricePo basePricePo,Integer verify);
  public int updateByIsdel(BasePricePo basePricePo,Integer isdel);
  public int updateByCuid(BasePricePo basePricePo,Integer cuid);
  public int updateByMuid(BasePricePo basePricePo,Integer muid);
  public int updateByCtime(BasePricePo basePricePo,Date ctime);
  public int updateByMtime(BasePricePo basePricePo,String mtime);
  public int updateNotNullByObject(BasePricePo basePricePo,BasePricePo search);
  public int updateAllByObject(BasePricePo basePricePo,BasePricePo search);
  public BasePricePo queryBasePriceById(Integer bpid,String postSql,String queryFields);
  public List<BasePricePo> queryBasePriceByIds(List<Integer> ids,String postSql,String queryFields);
  public List<BasePricePo> queryByObjectAnd(BasePricePo basePricePo,String postSql,String queryFields);
  public List<BasePricePo> queryByObjectOr(BasePricePo basePricePo,String postSql,String queryFields);
  public List<BasePricePo> queryByBpid(Integer bpid,String postSql,String queryFields);
  public List<BasePricePo> queryByCityId(Integer cityId,String postSql,String queryFields);
  public List<BasePricePo> queryByCourseId(Integer courseId,String postSql,String queryFields);
  public List<BasePricePo> queryByColid(Integer colid,String postSql,String queryFields);
  public List<BasePricePo> queryByPrice(Integer price,String postSql,String queryFields);
  public List<BasePricePo> queryByAllowance(Integer allowance,String postSql,String queryFields);
  public List<BasePricePo> queryByVerify(Integer verify,String postSql,String queryFields);
  public List<BasePricePo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<BasePricePo> queryByCuid(Integer cuid,String postSql,String queryFields);
  public List<BasePricePo> queryByMuid(Integer muid,String postSql,String queryFields);
  public List<BasePricePo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<BasePricePo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<BasePricePo> queryByNew0(BasePricePo basePricePo,String postSql,String queryFields);
  public List<BasePricePo> queryByNew1(BasePricePo basePricePo,String postSql,String queryFields);
  public List<BasePricePo> queryByNew2(BasePricePo basePricePo,String postSql,String queryFields);
  public List<BasePricePo> queryByNew3(BasePricePo basePricePo,String postSql,String queryFields);
  public List<BasePricePo> queryByNew4(BasePricePo basePricePo,String postSql,String queryFields);
  public List<BasePricePo> queryByNew5(BasePricePo basePricePo,String postSql,String queryFields);
  public List<BasePricePo> queryByNew6(BasePricePo basePricePo,String postSql,String queryFields);
  public List<BasePricePo> queryByNew7(BasePricePo basePricePo,String postSql,String queryFields);

}
