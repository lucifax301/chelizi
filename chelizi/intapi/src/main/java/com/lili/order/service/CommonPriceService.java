package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.CommonPriceVo;
import com.lili.order.vo.CommonPriceQuery;

public interface CommonPriceService {

  public void addCommonPrice(CommonPriceVo commonPriceVo)  throws Exception;
  public void addCommonPriceList(List<CommonPriceVo> commonPriceVoList)  throws Exception;
  public void delCommonPriceById(Integer upid)  throws Exception;
  public void delCommonPriceByIds(List<Integer> ids)  throws Exception;
  public void delCommonPriceByObj(CommonPriceVo commonPriceVo)  throws Exception;
  public void saveCommonPrice(CommonPriceVo commonPriceVo)  throws Exception;
  public void saveCommonPriceList(List<CommonPriceVo> commonPriceVoList)  throws Exception;
  public int updateByUpid(CommonPriceVo commonPriceVo,Integer upid)  throws Exception;
  public int updateByCityId(CommonPriceVo commonPriceVo,Integer cityId)  throws Exception;
  public int updateByCourseId(CommonPriceVo commonPriceVo,Integer courseId)  throws Exception;
  public int updateByColid(CommonPriceVo commonPriceVo,Integer colid)  throws Exception;
  public int updateByCalid(CommonPriceVo commonPriceVo,Integer calid)  throws Exception;
  public int updateByDftype(CommonPriceVo commonPriceVo,Integer dftype)  throws Exception;
  public int updateByTstart(CommonPriceVo commonPriceVo,String tstart)  throws Exception;
  public int updateByTend(CommonPriceVo commonPriceVo,String tend)  throws Exception;
  public int updateByPrice(CommonPriceVo commonPriceVo,Integer price)  throws Exception;
  public int updateByAllowance(CommonPriceVo commonPriceVo,Integer allowance)  throws Exception;
  public int updateByVerify(CommonPriceVo commonPriceVo,Integer verify)  throws Exception;
  public int updateByIsdel(CommonPriceVo commonPriceVo,Integer isdel)  throws Exception;
  public int updateByCuid(CommonPriceVo commonPriceVo,Integer cuid)  throws Exception;
  public int updateByMuid(CommonPriceVo commonPriceVo,Integer muid)  throws Exception;
  public int updateByCtime(CommonPriceVo commonPriceVo,Date ctime)  throws Exception;
  public int updateByMtime(CommonPriceVo commonPriceVo,String mtime)  throws Exception;
  public int updateNotNullByObject(CommonPriceVo commonPriceVo,CommonPriceVo search)  throws Exception;
  public int updateAllByObject(CommonPriceVo commonPriceVo,CommonPriceVo search)  throws Exception;
  public CommonPriceVo queryCommonPriceById(Integer upid,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryCommonPriceByIds(List<Integer> ids,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByObjectAnd(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByObjectOr(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByUpid(Integer upid,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByCityId(Integer cityId,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByCourseId(Integer courseId,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByColid(Integer colid,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByCalid(Integer calid,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByDftype(Integer dftype,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByTstart(String tstart,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByTend(String tend,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByPrice(Integer price,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByAllowance(Integer allowance,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByVerify(Integer verify,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByIsdel(Integer isdel,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByCuid(Integer cuid,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByMuid(Integer muid,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByCtime(Date ctime,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByMtime(String mtime,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByNew0(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByNew1(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByNew2(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByNew3(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByNew4(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByNew5(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByNew6(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery)  throws Exception;
  public List<CommonPriceVo> queryByNew7(CommonPriceVo commonPriceVo,CommonPriceQuery commonPriceQuery)  throws Exception;

}
