package com.lili.order.dao.mapper;

import java.util.List;
import com.lili.order.dao.po.CoachScorePo;

public interface CoachScoreMapper{

  public void addCoachScore(CoachScorePo coachScorePo);
  public void addCoachScoreList(List<CoachScorePo> coachScorePoList);
  public void delCoachScoreById(Long coachId);
  public void delCoachScoreByIds(List<Long> ids);
  public void delCoachScoreByObj(CoachScorePo coachScorePo);
  public void saveCoachScore(CoachScorePo coachScorePo);
  public void saveCoachScoreList(List<CoachScorePo> coachScorePoList);
  public int updateByCoachId(CoachScorePo coachScorePo,Long coachId);
  public int updateByAcceptOrder(CoachScorePo coachScorePo,Long acceptOrder);
  public int updateByRefuseOrder(CoachScorePo coachScorePo,Long refuseOrder);
  public int updateByCancelOrder(CoachScorePo coachScorePo,Long cancelOrder);
  public int updateByOrderTotal(CoachScorePo coachScorePo,Long orderTotal);
  public int updateByScoreTotal(CoachScorePo coachScorePo,Long scoreTotal);
  public int updateByCompany(CoachScorePo coachScorePo,Integer company);
  public int updateNotNullByObject(CoachScorePo coachScorePo,CoachScorePo search);
  public int updateAllByObject(CoachScorePo coachScorePo,CoachScorePo search);
  public CoachScorePo queryCoachScoreById(Long coachId,String postSql,String queryFields);
  public List<CoachScorePo> queryCoachScoreByIds(List<Long> ids,String postSql,String queryFields);
  public List<CoachScorePo> queryByObjectAnd(CoachScorePo coachScorePo,String postSql,String queryFields);
  public List<CoachScorePo> queryByObjectOr(CoachScorePo coachScorePo,String postSql,String queryFields);
  public List<CoachScorePo> queryByCoachId(Long coachId,String postSql,String queryFields);
  public List<CoachScorePo> queryByAcceptOrder(Long acceptOrder,String postSql,String queryFields);
  public List<CoachScorePo> queryByRefuseOrder(Long refuseOrder,String postSql,String queryFields);
  public List<CoachScorePo> queryByCancelOrder(Long cancelOrder,String postSql,String queryFields);
  public List<CoachScorePo> queryByOrderTotal(Long orderTotal,String postSql,String queryFields);
  public List<CoachScorePo> queryByScoreTotal(Long scoreTotal,String postSql,String queryFields);
  public List<CoachScorePo> queryByCompany(Integer company,String postSql,String queryFields);
  public List<CoachScorePo> queryByNew0(CoachScorePo coachScorePo,String postSql,String queryFields);
  public List<CoachScorePo> queryByNew1(CoachScorePo coachScorePo,String postSql,String queryFields);
  public List<CoachScorePo> queryByNew2(CoachScorePo coachScorePo,String postSql,String queryFields);
  public List<CoachScorePo> queryByNew3(CoachScorePo coachScorePo,String postSql,String queryFields);
  public List<CoachScorePo> queryByNew4(CoachScorePo coachScorePo,String postSql,String queryFields);
  public List<CoachScorePo> queryByNew5(CoachScorePo coachScorePo,String postSql,String queryFields);
  public List<CoachScorePo> queryByNew6(CoachScorePo coachScorePo,String postSql,String queryFields);
  public List<CoachScorePo> queryByNew7(CoachScorePo coachScorePo,String postSql,String queryFields);

}
