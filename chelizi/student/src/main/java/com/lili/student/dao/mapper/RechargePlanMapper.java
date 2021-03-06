package com.lili.student.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.student.dao.po.RechargePlanPo;

public interface RechargePlanMapper{

  public void addRechargePlan(RechargePlanPo rechargePlanPo);
  public void addRechargePlanList(List<RechargePlanPo> rechargePlanPoList);
  public void delRechargePlanById(Integer rcid);
  public void delRechargePlanByIds(List<Integer> ids);
  public void delRechargePlanByObj(RechargePlanPo rechargePlanPo);
  public void saveRechargePlan(RechargePlanPo rechargePlanPo);
  public void saveRechargePlanList(List<RechargePlanPo> rechargePlanPoList);
  public int updateByRcid(RechargePlanPo rechargePlanPo,Integer rcid);
  public int updateByRcname(RechargePlanPo rechargePlanPo,String rcname);
  public int updateByVtype(RechargePlanPo rechargePlanPo,Integer vtype);
  public int updateByTstart(RechargePlanPo rechargePlanPo,Date tstart);
  public int updateByTend(RechargePlanPo rechargePlanPo,Date tend);
  public int updateByActive(RechargePlanPo rechargePlanPo,Integer active);
  public int updateByEnroll(RechargePlanPo rechargePlanPo,Integer enroll);
  public int updateByCityId(RechargePlanPo rechargePlanPo,String cityId);
  public int updateByCityName(RechargePlanPo rechargePlanPo,String cityName);
  public int updateByCommon(RechargePlanPo rechargePlanPo,Integer common);
  public int updateByNeedVerify(RechargePlanPo rechargePlanPo,Integer needVerify);
  public int updateByRegUse(RechargePlanPo rechargePlanPo,Integer regUse);
  public int updateByAuto(RechargePlanPo rechargePlanPo,Integer auto);
  public int updateByMaxTimes(RechargePlanPo rechargePlanPo,Integer maxTimes);
  public int updateByIndepent(RechargePlanPo rechargePlanPo,Integer indepent);
  public int updateByJpush(RechargePlanPo rechargePlanPo,String jpush);
  public int updateByEms(RechargePlanPo rechargePlanPo,String ems);
  public int updateByRejpush(RechargePlanPo rechargePlanPo,String rejpush);
  public int updateByReems(RechargePlanPo rechargePlanPo,String reems);
  public int updateByCouponTemplate(RechargePlanPo rechargePlanPo,String couponTemplate);
  public int updateByCouponNumber(RechargePlanPo rechargePlanPo,String couponNumber);
  public int updateByAgreement(RechargePlanPo rechargePlanPo,String agreement);
  public int updateByVstate(RechargePlanPo rechargePlanPo,Integer vstate);
  public int updateByReason(RechargePlanPo rechargePlanPo,String reason);
  public int updateByIsdel(RechargePlanPo rechargePlanPo,Integer isdel);
  public int updateByCuid(RechargePlanPo rechargePlanPo,Long cuid);
  public int updateByMuid(RechargePlanPo rechargePlanPo,Long muid);
  public int updateByCtime(RechargePlanPo rechargePlanPo,Date ctime);
  public int updateByMtime(RechargePlanPo rechargePlanPo,String mtime);
  public int updateNotNullByObject(RechargePlanPo rechargePlanPo,RechargePlanPo search);
  public int updateAllByObject(RechargePlanPo rechargePlanPo,RechargePlanPo search);
  public RechargePlanPo queryRechargePlanById(Integer rcid,String postSql,String queryFields);
  public List<RechargePlanPo> queryRechargePlanByIds(List<Integer> ids,String postSql,String queryFields);
  public List<RechargePlanPo> queryByObjectAnd(RechargePlanPo rechargePlanPo,String postSql,String queryFields);
  public Integer countByObject(RechargePlanPo rechargePlanPo,String postSql,String queryFields);
  public List<RechargePlanPo> queryByObjectOr(RechargePlanPo rechargePlanPo,String postSql,String queryFields);
  public List<RechargePlanPo> queryByRcid(Integer rcid,String postSql,String queryFields);
  public List<RechargePlanPo> queryByRcname(String rcname,String postSql,String queryFields);
  public List<RechargePlanPo> queryByVtype(Integer vtype,String postSql,String queryFields);
  public List<RechargePlanPo> queryByTstart(Date tstart,String postSql,String queryFields);
  public List<RechargePlanPo> queryByTend(Date tend,String postSql,String queryFields);
  public List<RechargePlanPo> queryByActive(Integer active,String postSql,String queryFields);
  public List<RechargePlanPo> queryByEnroll(Integer enroll,String postSql,String queryFields);
  public List<RechargePlanPo> queryByCityId(String cityId,String postSql,String queryFields);
  public List<RechargePlanPo> queryByCityName(String cityName,String postSql,String queryFields);
  public List<RechargePlanPo> queryByCommon(Integer common,String postSql,String queryFields);
  public List<RechargePlanPo> queryByNeedVerify(Integer needVerify,String postSql,String queryFields);
  public List<RechargePlanPo> queryByRegUse(Integer regUse,String postSql,String queryFields);
  public List<RechargePlanPo> queryByAuto(Integer auto,String postSql,String queryFields);
  public List<RechargePlanPo> queryByMaxTimes(Integer maxTimes,String postSql,String queryFields);
  public List<RechargePlanPo> queryByIndepent(Integer indepent,String postSql,String queryFields);
  public List<RechargePlanPo> queryByJpush(String jpush,String postSql,String queryFields);
  public List<RechargePlanPo> queryByEms(String ems,String postSql,String queryFields);
  public List<RechargePlanPo> queryByRejpush(String rejpush,String postSql,String queryFields);
  public List<RechargePlanPo> queryByReems(String reems,String postSql,String queryFields);
  public List<RechargePlanPo> queryByCouponTemplate(String couponTemplate,String postSql,String queryFields);
  public List<RechargePlanPo> queryByCouponNumber(String couponNumber,String postSql,String queryFields);
  public List<RechargePlanPo> queryByAgreement(String agreement,String postSql,String queryFields);
  public List<RechargePlanPo> queryByVstate(Integer vstate,String postSql,String queryFields);
  public List<RechargePlanPo> queryByReason(String reason,String postSql,String queryFields);
  public List<RechargePlanPo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<RechargePlanPo> queryByCuid(Long cuid,String postSql,String queryFields);
  public List<RechargePlanPo> queryByMuid(Long muid,String postSql,String queryFields);
  public List<RechargePlanPo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<RechargePlanPo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<RechargePlanPo> queryByNew0(RechargePlanPo rechargePlanPo,String postSql,String queryFields);
  public List<RechargePlanPo> queryByNew1(RechargePlanPo rechargePlanPo,String postSql,String queryFields);
  public List<RechargePlanPo> queryByNew2(RechargePlanPo rechargePlanPo,String postSql,String queryFields);
  public List<RechargePlanPo> queryByNew3(RechargePlanPo rechargePlanPo,String postSql,String queryFields);
  public List<RechargePlanPo> queryByNew4(RechargePlanPo rechargePlanPo,String postSql,String queryFields);
  public List<RechargePlanPo> queryByNew5(RechargePlanPo rechargePlanPo,String postSql,String queryFields);
  public List<RechargePlanPo> queryByNew6(RechargePlanPo rechargePlanPo,String postSql,String queryFields);
  public List<RechargePlanPo> queryByNew7(RechargePlanPo rechargePlanPo,String postSql,String queryFields);

}
