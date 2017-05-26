package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.CoachAllowanceVo;
import com.lili.order.vo.CoachAllowanceQuery;

public interface CoachAllowanceService {

  public void addCoachAllowance(CoachAllowanceVo coachAllowanceVo)  throws Exception;
  public void addCoachAllowanceList(List<CoachAllowanceVo> coachAllowanceVoList)  throws Exception;
  public void delCoachAllowanceById(Integer caid)  throws Exception;
  public void delCoachAllowanceByIds(List<Integer> ids)  throws Exception;
  public void delCoachAllowanceByObj(CoachAllowanceVo coachAllowanceVo)  throws Exception;
  public void saveCoachAllowance(CoachAllowanceVo coachAllowanceVo)  throws Exception;
  public void saveCoachAllowanceList(List<CoachAllowanceVo> coachAllowanceVoList)  throws Exception;
  public int updateByCaid(CoachAllowanceVo coachAllowanceVo,Integer caid)  throws Exception;
  public int updateByCoachId(CoachAllowanceVo coachAllowanceVo,Long coachId)  throws Exception;
  public int updateByOrderId(CoachAllowanceVo coachAllowanceVo,String orderId)  throws Exception;
  public int updateByAllowance(CoachAllowanceVo coachAllowanceVo,Integer allowance)  throws Exception;
  public int updateByAstate(CoachAllowanceVo coachAllowanceVo,Integer astate)  throws Exception;
  public int updateByAtime(CoachAllowanceVo coachAllowanceVo,Date atime)  throws Exception;
  public int updateNotNullByObject(CoachAllowanceVo coachAllowanceVo,CoachAllowanceVo search)  throws Exception;
  public int updateAllByObject(CoachAllowanceVo coachAllowanceVo,CoachAllowanceVo search)  throws Exception;
  public CoachAllowanceVo queryCoachAllowanceById(Integer caid,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryCoachAllowanceByIds(List<Integer> ids,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByObjectAnd(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByObjectOr(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByCaid(Integer caid,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByCoachId(Long coachId,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByOrderId(String orderId,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByAllowance(Integer allowance,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByAstate(Integer astate,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByAtime(Date atime,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByNew0(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByNew1(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByNew2(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByNew3(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByNew4(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByNew5(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByNew6(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;
  public List<CoachAllowanceVo> queryByNew7(CoachAllowanceVo coachAllowanceVo,CoachAllowanceQuery coachAllowanceQuery)  throws Exception;

}
