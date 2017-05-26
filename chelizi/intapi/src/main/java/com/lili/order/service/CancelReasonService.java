package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.CancelReasonQuery;
import com.lili.order.vo.CancelReasonVo;

public interface CancelReasonService {

  public void addCancelReason(CancelReasonVo cancelReasonVo)  throws Exception;
  public void addCancelReasonList(List<CancelReasonVo> cancelReasonVoList)  throws Exception;
  public void delCancelReasonById(Integer crid)  throws Exception;
  public void delCancelReasonByIds(List<Integer> ids)  throws Exception;
  public void delCancelReasonByObj(CancelReasonVo cancelReasonVo)  throws Exception;
  public void saveCancelReason(CancelReasonVo cancelReasonVo)  throws Exception;
  public void saveCancelReasonList(List<CancelReasonVo> cancelReasonVoList)  throws Exception;
  public int updateByCrid(CancelReasonVo cancelReasonVo,Integer crid)  throws Exception;
  public int updateByReason(CancelReasonVo cancelReasonVo,String reason)  throws Exception;
  public int updateByUtype(CancelReasonVo cancelReasonVo,Integer utype)  throws Exception;
  public int updateByIsdel(CancelReasonVo cancelReasonVo,Integer isdel)  throws Exception;
  public int updateByCuid(CancelReasonVo cancelReasonVo,Integer cuid)  throws Exception;
  public int updateByMuid(CancelReasonVo cancelReasonVo,Integer muid)  throws Exception;
  public int updateByCtime(CancelReasonVo cancelReasonVo,Date ctime)  throws Exception;
  public int updateByMtime(CancelReasonVo cancelReasonVo,String mtime)  throws Exception;
  public int updateNotNullByObject(CancelReasonVo cancelReasonVo,CancelReasonVo search)  throws Exception;
  public int updateAllByObject(CancelReasonVo cancelReasonVo,CancelReasonVo search)  throws Exception;
  public CancelReasonVo queryCancelReasonById(Integer crid,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryCancelReasonByIds(List<Integer> ids,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByObjectAnd(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByObjectOr(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByCrid(Integer crid,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByReason(String reason,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByUtype(Integer utype,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByIsdel(Integer isdel,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByCuid(Integer cuid,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByMuid(Integer muid,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByCtime(Date ctime,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByMtime(String mtime,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByNew0(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByNew1(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByNew2(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByNew3(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByNew4(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByNew5(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByNew6(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery)  throws Exception;
  public List<CancelReasonVo> queryByNew7(CancelReasonVo cancelReasonVo,CancelReasonQuery cancelReasonQuery)  throws Exception;

}
