package com.lili.order.dao.mapper;

import java.util.Date;
import java.util.List;
import com.lili.order.dao.po.CoachStatisticPo;

public interface CoachStatisticMapper{

  public void addCoachStatistic(CoachStatisticPo coachStatisticPo);
  public void addCoachStatisticList(List<CoachStatisticPo> coachStatisticPoList);
  public void delCoachStatisticById(Integer csid);
  public void delCoachStatisticByIds(List<Integer> ids);
  public void delCoachStatisticByObj(CoachStatisticPo coachStatisticPo);
  public void saveCoachStatistic(CoachStatisticPo coachStatisticPo);
  public void saveCoachStatisticList(List<CoachStatisticPo> coachStatisticPoList);
  public int updateByCsid(CoachStatisticPo coachStatisticPo,Integer csid);
  public int updateByCoachId(CoachStatisticPo coachStatisticPo,Long coachId);
  public int updateByCurrDate(CoachStatisticPo coachStatisticPo,Integer currDate);
  public int updateByOrderAccept(CoachStatisticPo coachStatisticPo,Integer orderAccept);
  public int updateByOrderRefuse(CoachStatisticPo coachStatisticPo,Integer orderRefuse);
  public int updateByOrderCancel(CoachStatisticPo coachStatisticPo,Integer orderCancel);
  public int updateByOrderComment(CoachStatisticPo coachStatisticPo,Integer orderComment);
  public int updateByOrderMoney(CoachStatisticPo coachStatisticPo,Integer orderMoney);
  public int updateByCommentScore(CoachStatisticPo coachStatisticPo,Integer commentScore);
  public int updateNotNullByObject(CoachStatisticPo coachStatisticPo,CoachStatisticPo search);
  public int updateAllByObject(CoachStatisticPo coachStatisticPo,CoachStatisticPo search);
  public CoachStatisticPo queryCoachStatisticById(Integer csid,String postSql,String queryFields);
  public List<CoachStatisticPo> queryCoachStatisticByIds(List<Integer> ids,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByObjectAnd(CoachStatisticPo coachStatisticPo,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByObjectOr(CoachStatisticPo coachStatisticPo,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByCsid(Integer csid,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByCoachId(Long coachId,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByCurrDate(Integer currDate,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByOrderAccept(Integer orderAccept,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByOrderRefuse(Integer orderRefuse,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByOrderCancel(Integer orderCancel,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByOrderComment(Integer orderComment,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByOrderMoney(Integer orderMoney,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByCommentScore(Integer commentScore,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByNew0(CoachStatisticPo coachStatisticPo,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByNew1(CoachStatisticPo coachStatisticPo,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByNew2(CoachStatisticPo coachStatisticPo,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByNew3(CoachStatisticPo coachStatisticPo,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByNew4(CoachStatisticPo coachStatisticPo,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByNew5(CoachStatisticPo coachStatisticPo,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByNew6(CoachStatisticPo coachStatisticPo,String postSql,String queryFields);
  public List<CoachStatisticPo> queryByNew7(CoachStatisticPo coachStatisticPo,String postSql,String queryFields);
	/**
	 * 根据日期和教练ID获取日期当天的奖金
	 * @param date
	 * @param coachId
	 */
  public int getCoachBonusByDate(String date, Long coachId);

}
