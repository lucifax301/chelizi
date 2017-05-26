package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.BasePriceVo;
import com.lili.order.vo.BasePriceQuery;

public interface BasePriceService {

  public void addBasePrice(BasePriceVo basePriceVo)  throws Exception;
  public void addBasePriceList(List<BasePriceVo> basePriceVoList)  throws Exception;
  public void delBasePriceById(Integer bpid)  throws Exception;
  public void delBasePriceByIds(List<Integer> ids)  throws Exception;
  public void delBasePriceByObj(BasePriceVo basePriceVo)  throws Exception;
  public void saveBasePrice(BasePriceVo basePriceVo)  throws Exception;
  public void saveBasePriceList(List<BasePriceVo> basePriceVoList)  throws Exception;
  public int updateByBpid(BasePriceVo basePriceVo,Integer bpid)  throws Exception;
  public int updateByCityId(BasePriceVo basePriceVo,Integer cityId)  throws Exception;
  public int updateByCourseId(BasePriceVo basePriceVo,Integer courseId)  throws Exception;
  public int updateByColid(BasePriceVo basePriceVo,Integer colid)  throws Exception;
  public int updateByPrice(BasePriceVo basePriceVo,Integer price)  throws Exception;
  public int updateByAllowance(BasePriceVo basePriceVo,Integer allowance)  throws Exception;
  public int updateByVerify(BasePriceVo basePriceVo,Integer verify)  throws Exception;
  public int updateByIsdel(BasePriceVo basePriceVo,Integer isdel)  throws Exception;
  public int updateByCuid(BasePriceVo basePriceVo,Integer cuid)  throws Exception;
  public int updateByMuid(BasePriceVo basePriceVo,Integer muid)  throws Exception;
  public int updateByCtime(BasePriceVo basePriceVo,Date ctime)  throws Exception;
  public int updateByMtime(BasePriceVo basePriceVo,String mtime)  throws Exception;
  public int updateNotNullByObject(BasePriceVo basePriceVo,BasePriceVo search)  throws Exception;
  public int updateAllByObject(BasePriceVo basePriceVo,BasePriceVo search)  throws Exception;
  public BasePriceVo queryBasePriceById(Integer bpid,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryBasePriceByIds(List<Integer> ids,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByObjectAnd(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByObjectOr(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByBpid(Integer bpid,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByCityId(Integer cityId,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByCourseId(Integer courseId,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByColid(Integer colid,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByPrice(Integer price,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByAllowance(Integer allowance,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByVerify(Integer verify,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByIsdel(Integer isdel,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByCuid(Integer cuid,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByMuid(Integer muid,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByCtime(Date ctime,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByMtime(String mtime,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByNew0(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByNew1(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByNew2(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByNew3(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByNew4(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByNew5(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByNew6(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery)  throws Exception;
  public List<BasePriceVo> queryByNew7(BasePriceVo basePriceVo,BasePriceQuery basePriceQuery)  throws Exception;

}
