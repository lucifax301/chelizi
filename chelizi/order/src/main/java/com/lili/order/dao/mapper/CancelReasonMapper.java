package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.CancelReasonPo;

public interface CancelReasonMapper{

  public void addCancelReason(CancelReasonPo cancelReasonPo);
  public void addCancelReasonList(List<CancelReasonPo> cancelReasonPoList);
  public void delCancelReasonById(Integer crid);
  public void delCancelReasonByIds(List<Integer> ids);
  public void delCancelReasonByObj(CancelReasonPo cancelReasonPo);
  public void saveCancelReason(CancelReasonPo cancelReasonPo);
  public void saveCancelReasonList(List<CancelReasonPo> cancelReasonPoList);
  public int updateByCrid(CancelReasonPo cancelReasonPo,Integer crid);
  public int updateByReason(CancelReasonPo cancelReasonPo,String reason);
  public int updateByUtype(CancelReasonPo cancelReasonPo,Integer utype);
  public int updateByIsdel(CancelReasonPo cancelReasonPo,Integer isdel);
  public int updateByCuid(CancelReasonPo cancelReasonPo,Integer cuid);
  public int updateByMuid(CancelReasonPo cancelReasonPo,Integer muid);
  public int updateByCtime(CancelReasonPo cancelReasonPo,Date ctime);
  public int updateByMtime(CancelReasonPo cancelReasonPo,String mtime);
  public int updateNotNullByObject(CancelReasonPo cancelReasonPo,CancelReasonPo search);
  public int updateAllByObject(CancelReasonPo cancelReasonPo,CancelReasonPo search);
  public CancelReasonPo queryCancelReasonById(Integer crid,String postSql,String queryFields);
  public List<CancelReasonPo> queryCancelReasonByIds(List<Integer> ids,String postSql,String queryFields);
  public List<CancelReasonPo> queryByObjectAnd(CancelReasonPo cancelReasonPo,String postSql,String queryFields);
  public List<CancelReasonPo> queryByObjectOr(CancelReasonPo cancelReasonPo,String postSql,String queryFields);
  public List<CancelReasonPo> queryByCrid(Integer crid,String postSql,String queryFields);
  public List<CancelReasonPo> queryByReason(String reason,String postSql,String queryFields);
  public List<CancelReasonPo> queryByUtype(Integer utype,String postSql,String queryFields);
  public List<CancelReasonPo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<CancelReasonPo> queryByCuid(Integer cuid,String postSql,String queryFields);
  public List<CancelReasonPo> queryByMuid(Integer muid,String postSql,String queryFields);
  public List<CancelReasonPo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<CancelReasonPo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<CancelReasonPo> queryByNew0(CancelReasonPo cancelReasonPo,String postSql,String queryFields);
  public List<CancelReasonPo> queryByNew1(CancelReasonPo cancelReasonPo,String postSql,String queryFields);
  public List<CancelReasonPo> queryByNew2(CancelReasonPo cancelReasonPo,String postSql,String queryFields);
  public List<CancelReasonPo> queryByNew3(CancelReasonPo cancelReasonPo,String postSql,String queryFields);
  public List<CancelReasonPo> queryByNew4(CancelReasonPo cancelReasonPo,String postSql,String queryFields);
  public List<CancelReasonPo> queryByNew5(CancelReasonPo cancelReasonPo,String postSql,String queryFields);
  public List<CancelReasonPo> queryByNew6(CancelReasonPo cancelReasonPo,String postSql,String queryFields);
  public List<CancelReasonPo> queryByNew7(CancelReasonPo cancelReasonPo,String postSql,String queryFields);

}
