package com.lili.student.service;

import java.util.List;
import java.util.Date;
import com.lili.student.vo.RechargeGearQuery;
import com.lili.student.vo.RechargeGearVo;

public interface RechargeGearService {

  public void addRechargeGear(RechargeGearVo rechargeGearVo)  throws Exception;
  public void addRechargeGearList(List<RechargeGearVo> rechargeGearVoList)  throws Exception;
  public void delRechargeGearById(Integer rgid)  throws Exception;
  public void delRechargeGearByIds(List<Integer> ids)  throws Exception;
  public void delRechargeGearByObj(RechargeGearVo rechargeGearVo)  throws Exception;
  public void saveRechargeGear(RechargeGearVo rechargeGearVo)  throws Exception;
  public void saveRechargeGearList(List<RechargeGearVo> rechargeGearVoList)  throws Exception;
  public int updateByRgid(RechargeGearVo rechargeGearVo,Integer rgid)  throws Exception;
  public int updateByRcid(RechargeGearVo rechargeGearVo,Integer rcid)  throws Exception;
  public int updateByPmin(RechargeGearVo rechargeGearVo,Integer pmin)  throws Exception;
  public int updateByPmax(RechargeGearVo rechargeGearVo,Integer pmax)  throws Exception;
  public int updateByPercent(RechargeGearVo rechargeGearVo,Integer percent)  throws Exception;
  public int updateByMoney(RechargeGearVo rechargeGearVo,Integer money)  throws Exception;
  public int updateByVstate(RechargeGearVo rechargeGearVo,Integer vstate)  throws Exception;
  public int updateByIsdel(RechargeGearVo rechargeGearVo,Integer isdel)  throws Exception;
  public int updateByCuid(RechargeGearVo rechargeGearVo,Long cuid)  throws Exception;
  public int updateByMuid(RechargeGearVo rechargeGearVo,Long muid)  throws Exception;
  public int updateByCtime(RechargeGearVo rechargeGearVo,Date ctime)  throws Exception;
  public int updateByMtime(RechargeGearVo rechargeGearVo,String mtime)  throws Exception;
  public int updateNotNullByObject(RechargeGearVo rechargeGearVo,RechargeGearVo search)  throws Exception;
  public int updateAllByObject(RechargeGearVo rechargeGearVo,RechargeGearVo search)  throws Exception;
  public RechargeGearVo queryRechargeGearById(Integer rgid)  throws Exception;
  public List<RechargeGearVo> queryRechargeGearByIds(List<Integer> ids,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByObjectAnd(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public Integer countByObject(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByObjectOr(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByRgid(Integer rgid,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByRcid(Integer rcid,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByPmin(Integer pmin,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByPmax(Integer pmax,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByPercent(Integer percent,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByMoney(Integer money,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByVstate(Integer vstate,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByIsdel(Integer isdel,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByCuid(Long cuid,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByMuid(Long muid,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByCtime(Date ctime,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByMtime(String mtime,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByNew0(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByNew1(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByNew2(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByNew3(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByNew4(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByNew5(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByNew6(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;
  public List<RechargeGearVo> queryByNew7(RechargeGearVo rechargeGearVo,RechargeGearQuery rechargeGearQuery)  throws Exception;

}
