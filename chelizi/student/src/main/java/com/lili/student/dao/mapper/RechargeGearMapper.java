package com.lili.student.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.student.dao.po.RechargeGearPo;

public interface RechargeGearMapper{

  public void addRechargeGear(RechargeGearPo rechargeGearPo);
  public void addRechargeGearList(List<RechargeGearPo> rechargeGearPoList);
  public void delRechargeGearById(Integer rgid);
  public void delRechargeGearByIds(List<Integer> ids);
  public void delRechargeGearByObj(RechargeGearPo rechargeGearPo);
  public void saveRechargeGear(RechargeGearPo rechargeGearPo);
  public void saveRechargeGearList(List<RechargeGearPo> rechargeGearPoList);
  public int updateByRgid(RechargeGearPo rechargeGearPo,Integer rgid);
  public int updateByRcid(RechargeGearPo rechargeGearPo,Integer rcid);
  public int updateByPmin(RechargeGearPo rechargeGearPo,Integer pmin);
  public int updateByPmax(RechargeGearPo rechargeGearPo,Integer pmax);
  public int updateByPercent(RechargeGearPo rechargeGearPo,Integer percent);
  public int updateByMoney(RechargeGearPo rechargeGearPo,Integer money);
  public int updateByVstate(RechargeGearPo rechargeGearPo,Integer vstate);
  public int updateByIsdel(RechargeGearPo rechargeGearPo,Integer isdel);
  public int updateByCuid(RechargeGearPo rechargeGearPo,Long cuid);
  public int updateByMuid(RechargeGearPo rechargeGearPo,Long muid);
  public int updateByCtime(RechargeGearPo rechargeGearPo,Date ctime);
  public int updateByMtime(RechargeGearPo rechargeGearPo,String mtime);
  public int updateNotNullByObject(RechargeGearPo rechargeGearPo,RechargeGearPo search);
  public int updateAllByObject(RechargeGearPo rechargeGearPo,RechargeGearPo search);
  public RechargeGearPo queryRechargeGearById(Integer rgid,String postSql,String queryFields);
  public List<RechargeGearPo> queryRechargeGearByIds(List<Integer> ids,String postSql,String queryFields);
  public List<RechargeGearPo> queryByObjectAnd(RechargeGearPo rechargeGearPo,String postSql,String queryFields);
  public Integer countByObject(RechargeGearPo rechargeGearPo,String postSql,String queryFields);
  public List<RechargeGearPo> queryByObjectOr(RechargeGearPo rechargeGearPo,String postSql,String queryFields);
  public List<RechargeGearPo> queryByRgid(Integer rgid,String postSql,String queryFields);
  public List<RechargeGearPo> queryByRcid(Integer rcid,String postSql,String queryFields);
  public List<RechargeGearPo> queryByPmin(Integer pmin,String postSql,String queryFields);
  public List<RechargeGearPo> queryByPmax(Integer pmax,String postSql,String queryFields);
  public List<RechargeGearPo> queryByPercent(Integer percent,String postSql,String queryFields);
  public List<RechargeGearPo> queryByMoney(Integer money,String postSql,String queryFields);
  public List<RechargeGearPo> queryByVstate(Integer vstate,String postSql,String queryFields);
  public List<RechargeGearPo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<RechargeGearPo> queryByCuid(Long cuid,String postSql,String queryFields);
  public List<RechargeGearPo> queryByMuid(Long muid,String postSql,String queryFields);
  public List<RechargeGearPo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<RechargeGearPo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<RechargeGearPo> queryByNew0(RechargeGearPo rechargeGearPo,String postSql,String queryFields);
  public List<RechargeGearPo> queryByNew1(RechargeGearPo rechargeGearPo,String postSql,String queryFields);
  public List<RechargeGearPo> queryByNew2(RechargeGearPo rechargeGearPo,String postSql,String queryFields);
  public List<RechargeGearPo> queryByNew3(RechargeGearPo rechargeGearPo,String postSql,String queryFields);
  public List<RechargeGearPo> queryByNew4(RechargeGearPo rechargeGearPo,String postSql,String queryFields);
  public List<RechargeGearPo> queryByNew5(RechargeGearPo rechargeGearPo,String postSql,String queryFields);
  public List<RechargeGearPo> queryByNew6(RechargeGearPo rechargeGearPo,String postSql,String queryFields);
  public List<RechargeGearPo> queryByNew7(RechargeGearPo rechargeGearPo,String postSql,String queryFields);

}
