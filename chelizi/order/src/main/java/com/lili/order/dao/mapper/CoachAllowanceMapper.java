package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.CoachAllowancePo;

public interface CoachAllowanceMapper{

  public void addCoachAllowance(CoachAllowancePo coachAllowancePo);
  public void addCoachAllowanceList(List<CoachAllowancePo> coachAllowancePoList);
  public void delCoachAllowanceById(Integer caid);
  public void delCoachAllowanceByIds(List<Integer> ids);
  public void delCoachAllowanceByObj(CoachAllowancePo coachAllowancePo);
  public void saveCoachAllowance(CoachAllowancePo coachAllowancePo);
  public void saveCoachAllowanceList(List<CoachAllowancePo> coachAllowancePoList);
  public int updateByCaid(CoachAllowancePo coachAllowancePo,Integer caid);
  public int updateByCoachId(CoachAllowancePo coachAllowancePo,Long coachId);
  public int updateByOrderId(CoachAllowancePo coachAllowancePo,String orderId);
  public int updateByAllowance(CoachAllowancePo coachAllowancePo,Integer allowance);
  public int updateByAstate(CoachAllowancePo coachAllowancePo,Integer astate);
  public int updateByAtime(CoachAllowancePo coachAllowancePo,Date atime);
  public int updateNotNullByObject(CoachAllowancePo coachAllowancePo,CoachAllowancePo search);
  public int updateAllByObject(CoachAllowancePo coachAllowancePo,CoachAllowancePo search);
  public CoachAllowancePo queryCoachAllowanceById(Integer caid,String postSql,String queryFields);
  public List<CoachAllowancePo> queryCoachAllowanceByIds(List<Integer> ids,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByObjectAnd(CoachAllowancePo coachAllowancePo,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByObjectOr(CoachAllowancePo coachAllowancePo,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByCaid(Integer caid,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByCoachId(Long coachId,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByOrderId(String orderId,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByAllowance(Integer allowance,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByAstate(Integer astate,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByAtime(Date atime,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByNew0(CoachAllowancePo coachAllowancePo,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByNew1(CoachAllowancePo coachAllowancePo,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByNew2(CoachAllowancePo coachAllowancePo,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByNew3(CoachAllowancePo coachAllowancePo,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByNew4(CoachAllowancePo coachAllowancePo,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByNew5(CoachAllowancePo coachAllowancePo,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByNew6(CoachAllowancePo coachAllowancePo,String postSql,String queryFields);
  public List<CoachAllowancePo> queryByNew7(CoachAllowancePo coachAllowancePo,String postSql,String queryFields);

}
