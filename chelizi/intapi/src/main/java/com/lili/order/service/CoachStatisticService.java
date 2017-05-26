package com.lili.order.service;

import java.util.Date;
import java.util.List;
import com.lili.order.vo.CoachStatisticQuery;
import com.lili.order.vo.CoachStatisticVo;

public interface CoachStatisticService {

  public void addCoachStatistic(CoachStatisticVo coachStatisticVo)  throws Exception;
  public void addCoachStatisticList(List<CoachStatisticVo> coachStatisticVoList)  throws Exception;
  public void delCoachStatisticById(Integer csid)  throws Exception;
  public void delCoachStatisticByIds(List<Integer> ids)  throws Exception;
  public void delCoachStatisticByObj(CoachStatisticVo coachStatisticVo)  throws Exception;
  public void saveCoachStatistic(CoachStatisticVo coachStatisticVo)  throws Exception;
  public void saveCoachStatisticList(List<CoachStatisticVo> coachStatisticVoList)  throws Exception;
  public int updateByCsid(CoachStatisticVo coachStatisticVo,Integer csid)  throws Exception;
  public int updateByCoachId(CoachStatisticVo coachStatisticVo,Long coachId)  throws Exception;
  public int updateByCurrDate(CoachStatisticVo coachStatisticVo,Integer currDate)  throws Exception;
  public int updateByOrderAccept(CoachStatisticVo coachStatisticVo,Integer orderAccept)  throws Exception;
  public int updateByOrderRefuse(CoachStatisticVo coachStatisticVo,Integer orderRefuse)  throws Exception;
  public int updateByOrderCancel(CoachStatisticVo coachStatisticVo,Integer orderCancel)  throws Exception;
  public int updateByOrderComment(CoachStatisticVo coachStatisticVo,Integer orderComment)  throws Exception;
  public int updateByOrderMoney(CoachStatisticVo coachStatisticVo,Integer orderMoney)  throws Exception;
  public int updateByCommentScore(CoachStatisticVo coachStatisticVo,Integer commentScore)  throws Exception;
  public int updateNotNullByObject(CoachStatisticVo coachStatisticVo,CoachStatisticVo search)  throws Exception;
  public int updateAllByObject(CoachStatisticVo coachStatisticVo,CoachStatisticVo search)  throws Exception;
  public CoachStatisticVo queryCoachStatisticById(Integer csid,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryCoachStatisticByIds(List<Integer> ids,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByObjectAnd(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByObjectOr(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByCsid(Integer csid,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByCoachId(Long coachId,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByCurrDate(Integer currDate,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByOrderAccept(Integer orderAccept,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByOrderRefuse(Integer orderRefuse,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByOrderCancel(Integer orderCancel,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByOrderComment(Integer orderComment,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByOrderMoney(Integer orderMoney,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByCommentScore(Integer commentScore,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByNew0(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByNew1(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByNew2(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByNew3(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByNew4(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByNew5(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByNew6(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public List<CoachStatisticVo> queryByNew7(CoachStatisticVo coachStatisticVo,CoachStatisticQuery coachStatisticQuery)  throws Exception;
  public CoachStatisticVo getCoachStatistc(long coachId,String date) throws Exception; 
  	/**
	 * 根据日期和教练ID获取日期当天的奖金
	 * @param date
	 * @param coachId
	 */
  public int getCoachBonusByDate(String date, Long coachId);
}
