package com.lili.order.service;

import java.util.List;
import java.util.Map;

import com.lili.order.vo.CoachScoreQuery;
import com.lili.order.vo.CoachScoreVo;

public interface CoachScoreService {

  public void addCoachScore(CoachScoreVo coachScoreVo)  throws Exception;
  public void addCoachScoreList(List<CoachScoreVo> coachScoreVoList)  throws Exception;
  public void delCoachScoreById(Long coachId)  throws Exception;
  public void delCoachScoreByIds(List<Long> ids)  throws Exception;
  public void delCoachScoreByObj(CoachScoreVo coachScoreVo)  throws Exception;
  public void saveCoachScore(CoachScoreVo coachScoreVo)  throws Exception;
  public void saveCoachScoreList(List<CoachScoreVo> coachScoreVoList)  throws Exception;
  public int updateByCoachId(CoachScoreVo coachScoreVo,Long coachId)  throws Exception;
  public int updateByAcceptOrder(CoachScoreVo coachScoreVo,Long acceptOrder)  throws Exception;
  public int updateByRefuseOrder(CoachScoreVo coachScoreVo,Long refuseOrder)  throws Exception;
  public int updateByCancelOrder(CoachScoreVo coachScoreVo,Long cancelOrder)  throws Exception;
  public int updateByOrderTotal(CoachScoreVo coachScoreVo,Long orderTotal)  throws Exception;
  public int updateByScoreTotal(CoachScoreVo coachScoreVo,Long scoreTotal)  throws Exception;
  public int updateByCompany(CoachScoreVo coachScoreVo,Integer company)  throws Exception;
  public int updateNotNullByObject(CoachScoreVo coachScoreVo,CoachScoreVo search)  throws Exception;
  public int updateAllByObject(CoachScoreVo coachScoreVo,CoachScoreVo search)  throws Exception;
  public CoachScoreVo queryCoachScoreById(Long coachId,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryCoachScoreByIds(List<Long> ids,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByObjectAnd(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByObjectOr(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByCoachId(Long coachId,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByAcceptOrder(Long acceptOrder,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByRefuseOrder(Long refuseOrder,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByCancelOrder(Long cancelOrder,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByOrderTotal(Long orderTotal,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByScoreTotal(Long scoreTotal,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByCompany(Integer company,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByNew0(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByNew1(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByNew2(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByNew3(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByNew4(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByNew5(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByNew6(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery)  throws Exception;
  public List<CoachScoreVo> queryByNew7(CoachScoreVo coachScoreVo,CoachScoreQuery coachScoreQuery)  throws Exception;
  public Map<Long,Integer> queryByCoachIds(List<Long> ids) throws Exception;

}
