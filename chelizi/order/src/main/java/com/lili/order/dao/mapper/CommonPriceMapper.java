package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.CommonPricePo;

public interface CommonPriceMapper{

  public void addCommonPrice(CommonPricePo commonPricePo);
  public void addCommonPriceList(List<CommonPricePo> commonPricePoList);
  public void delCommonPriceById(Integer upid);
  public void delCommonPriceByIds(List<Integer> ids);
  public void delCommonPriceByObj(CommonPricePo commonPricePo);
  public void saveCommonPrice(CommonPricePo commonPricePo);
  public void saveCommonPriceList(List<CommonPricePo> commonPricePoList);
  public int updateByUpid(CommonPricePo commonPricePo,Integer upid);
  public int updateByCityId(CommonPricePo commonPricePo,Integer cityId);
  public int updateByCourseId(CommonPricePo commonPricePo,Integer courseId);
  public int updateByColid(CommonPricePo commonPricePo,Integer colid);
  public int updateByCalid(CommonPricePo commonPricePo,Integer calid);
  public int updateByDftype(CommonPricePo commonPricePo,Integer dftype);
  public int updateByTstart(CommonPricePo commonPricePo,String tstart);
  public int updateByTend(CommonPricePo commonPricePo,String tend);
  public int updateByPrice(CommonPricePo commonPricePo,Integer price);
  public int updateByAllowance(CommonPricePo commonPricePo,Integer allowance);
  public int updateByVerify(CommonPricePo commonPricePo,Integer verify);
  public int updateByIsdel(CommonPricePo commonPricePo,Integer isdel);
  public int updateByCuid(CommonPricePo commonPricePo,Integer cuid);
  public int updateByMuid(CommonPricePo commonPricePo,Integer muid);
  public int updateByCtime(CommonPricePo commonPricePo,Date ctime);
  public int updateByMtime(CommonPricePo commonPricePo,String mtime);
  public int updateNotNullByObject(CommonPricePo commonPricePo,CommonPricePo search);
  public int updateAllByObject(CommonPricePo commonPricePo,CommonPricePo search);
  public CommonPricePo queryCommonPriceById(Integer upid,String postSql,String queryFields);
  public List<CommonPricePo> queryCommonPriceByIds(List<Integer> ids,String postSql,String queryFields);
  public List<CommonPricePo> queryByObjectAnd(CommonPricePo commonPricePo,String postSql,String queryFields);
  public List<CommonPricePo> queryByObjectOr(CommonPricePo commonPricePo,String postSql,String queryFields);
  public List<CommonPricePo> queryByUpid(Integer upid,String postSql,String queryFields);
  public List<CommonPricePo> queryByCityId(Integer cityId,String postSql,String queryFields);
  public List<CommonPricePo> queryByCourseId(Integer courseId,String postSql,String queryFields);
  public List<CommonPricePo> queryByColid(Integer colid,String postSql,String queryFields);
  public List<CommonPricePo> queryByCalid(Integer calid,String postSql,String queryFields);
  public List<CommonPricePo> queryByDftype(Integer dftype,String postSql,String queryFields);
  public List<CommonPricePo> queryByTstart(String tstart,String postSql,String queryFields);
  public List<CommonPricePo> queryByTend(String tend,String postSql,String queryFields);
  public List<CommonPricePo> queryByPrice(Integer price,String postSql,String queryFields);
  public List<CommonPricePo> queryByAllowance(Integer allowance,String postSql,String queryFields);
  public List<CommonPricePo> queryByVerify(Integer verify,String postSql,String queryFields);
  public List<CommonPricePo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<CommonPricePo> queryByCuid(Integer cuid,String postSql,String queryFields);
  public List<CommonPricePo> queryByMuid(Integer muid,String postSql,String queryFields);
  public List<CommonPricePo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<CommonPricePo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<CommonPricePo> queryByNew0(CommonPricePo commonPricePo,String postSql,String queryFields);
  public List<CommonPricePo> queryByNew1(CommonPricePo commonPricePo,String postSql,String queryFields);
  public List<CommonPricePo> queryByNew2(CommonPricePo commonPricePo,String postSql,String queryFields);
  public List<CommonPricePo> queryByNew3(CommonPricePo commonPricePo,String postSql,String queryFields);
  public List<CommonPricePo> queryByNew4(CommonPricePo commonPricePo,String postSql,String queryFields);
  public List<CommonPricePo> queryByNew5(CommonPricePo commonPricePo,String postSql,String queryFields);
  public List<CommonPricePo> queryByNew6(CommonPricePo commonPricePo,String postSql,String queryFields);
  public List<CommonPricePo> queryByNew7(CommonPricePo commonPricePo,String postSql,String queryFields);

}
