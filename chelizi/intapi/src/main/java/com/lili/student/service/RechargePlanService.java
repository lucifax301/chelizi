package com.lili.student.service;

import java.util.List;
import com.lili.student.vo.RechargePlanVo;
import java.util.Date;
import com.lili.student.vo.RechargePlanQuery;

public interface RechargePlanService {

  public void addRechargePlan(RechargePlanVo rechargePlanVo)  throws Exception;
  public void addRechargePlanList(List<RechargePlanVo> rechargePlanVoList)  throws Exception;
  public void delRechargePlanById(Integer rcid)  throws Exception;
  public void delRechargePlanByIds(List<Integer> ids)  throws Exception;
  public void delRechargePlanByObj(RechargePlanVo rechargePlanVo)  throws Exception;
  public void saveRechargePlan(RechargePlanVo rechargePlanVo)  throws Exception;
  public void saveRechargePlanList(List<RechargePlanVo> rechargePlanVoList)  throws Exception;
  public int updateByRcid(RechargePlanVo rechargePlanVo,Integer rcid)  throws Exception;
  public int updateByRcname(RechargePlanVo rechargePlanVo,String rcname)  throws Exception;
  public int updateByVtype(RechargePlanVo rechargePlanVo,Integer vtype)  throws Exception;
  public int updateByTstart(RechargePlanVo rechargePlanVo,Date tstart)  throws Exception;
  public int updateByTend(RechargePlanVo rechargePlanVo,Date tend)  throws Exception;
  public int updateByActive(RechargePlanVo rechargePlanVo,Integer active)  throws Exception;
  public int updateByEnroll(RechargePlanVo rechargePlanVo,Integer enroll)  throws Exception;
  public int updateByCityId(RechargePlanVo rechargePlanVo,String cityId)  throws Exception;
  public int updateByCityName(RechargePlanVo rechargePlanVo,String cityName)  throws Exception;
  public int updateByCommon(RechargePlanVo rechargePlanVo,Integer common)  throws Exception;
  public int updateByNeedVerify(RechargePlanVo rechargePlanVo,Integer needVerify)  throws Exception;
  public int updateByRegUse(RechargePlanVo rechargePlanVo,Integer regUse)  throws Exception;
  public int updateByAuto(RechargePlanVo rechargePlanVo,Integer auto)  throws Exception;
  public int updateByMaxTimes(RechargePlanVo rechargePlanVo,Integer maxTimes)  throws Exception;
  public int updateByIndepent(RechargePlanVo rechargePlanVo,Integer indepent)  throws Exception;
  public int updateByJpush(RechargePlanVo rechargePlanVo,String jpush)  throws Exception;
  public int updateByEms(RechargePlanVo rechargePlanVo,String ems)  throws Exception;
  public int updateByRejpush(RechargePlanVo rechargePlanVo,String rejpush)  throws Exception;
  public int updateByReems(RechargePlanVo rechargePlanVo,String reems)  throws Exception;
  public int updateByCouponTemplate(RechargePlanVo rechargePlanVo,String couponTemplate)  throws Exception;
  public int updateByCouponNumber(RechargePlanVo rechargePlanVo,String couponNumber)  throws Exception;
  public int updateByAgreement(RechargePlanVo rechargePlanVo,String agreement)  throws Exception;
  public int updateByVstate(RechargePlanVo rechargePlanVo,Integer vstate)  throws Exception;
  public int updateByReason(RechargePlanVo rechargePlanVo,String reason)  throws Exception;
  public int updateByIsdel(RechargePlanVo rechargePlanVo,Integer isdel)  throws Exception;
  public int updateByCuid(RechargePlanVo rechargePlanVo,Long cuid)  throws Exception;
  public int updateByMuid(RechargePlanVo rechargePlanVo,Long muid)  throws Exception;
  public int updateByCtime(RechargePlanVo rechargePlanVo,Date ctime)  throws Exception;
  public int updateByMtime(RechargePlanVo rechargePlanVo,String mtime)  throws Exception;
  public int updateNotNullByObject(RechargePlanVo rechargePlanVo,RechargePlanVo search)  throws Exception;
  public int updateAllByObject(RechargePlanVo rechargePlanVo,RechargePlanVo search)  throws Exception;
  public RechargePlanVo queryRechargePlanById(Integer rcid)  throws Exception;
  public List<RechargePlanVo> queryRechargePlanByIds(List<Integer> ids,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByObjectAnd(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public Integer countByObject(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByObjectOr(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByRcid(Integer rcid,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByRcname(String rcname,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByVtype(Integer vtype,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByTstart(Date tstart,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByTend(Date tend,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByActive(Integer active,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByEnroll(Integer enroll,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByCityId(String cityId,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByCityName(String cityName,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByCommon(Integer common,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByNeedVerify(Integer needVerify,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByRegUse(Integer regUse,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByAuto(Integer auto,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByMaxTimes(Integer maxTimes,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByIndepent(Integer indepent,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByJpush(String jpush,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByEms(String ems,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByRejpush(String rejpush,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByReems(String reems,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByCouponTemplate(String couponTemplate,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByCouponNumber(String couponNumber,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByAgreement(String agreement,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByVstate(Integer vstate,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByReason(String reason,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByIsdel(Integer isdel,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByCuid(Long cuid,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByMuid(Long muid,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByCtime(Date ctime,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByMtime(String mtime,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByNew0(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByNew1(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByNew2(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByNew3(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByNew4(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByNew5(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByNew6(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;
  public List<RechargePlanVo> queryByNew7(RechargePlanVo rechargePlanVo,RechargePlanQuery rechargePlanQuery)  throws Exception;

}
