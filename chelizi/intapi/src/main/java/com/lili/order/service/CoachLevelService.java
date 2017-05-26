package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.CoachLevelQuery;
import com.lili.order.vo.CoachLevelVo;

public interface CoachLevelService {

  public void addCoachLevel(CoachLevelVo coachLevelVo)  throws Exception;
  public void addCoachLevelList(List<CoachLevelVo> coachLevelVoList)  throws Exception;
  public void delCoachLevelById(Integer colid)  throws Exception;
  public void delCoachLevelByIds(List<Integer> ids)  throws Exception;
  public void delCoachLevelByObj(CoachLevelVo coachLevelVo)  throws Exception;
  public void saveCoachLevel(CoachLevelVo coachLevelVo)  throws Exception;
  public void saveCoachLevelList(List<CoachLevelVo> coachLevelVoList)  throws Exception;
  public int updateByColid(CoachLevelVo coachLevelVo,Integer colid)  throws Exception;
  public int updateByName(CoachLevelVo coachLevelVo,String name)  throws Exception;
  public int updateByPrate(CoachLevelVo coachLevelVo,Integer prate)  throws Exception;
  public int updateByIsdel(CoachLevelVo coachLevelVo,Integer isdel)  throws Exception;
  public int updateByCuid(CoachLevelVo coachLevelVo,Integer cuid)  throws Exception;
  public int updateByMuid(CoachLevelVo coachLevelVo,Integer muid)  throws Exception;
  public int updateByCtime(CoachLevelVo coachLevelVo,Date ctime)  throws Exception;
  public int updateByMtime(CoachLevelVo coachLevelVo,String mtime)  throws Exception;
  public int updateNotNullByObject(CoachLevelVo coachLevelVo,CoachLevelVo search)  throws Exception;
  public int updateAllByObject(CoachLevelVo coachLevelVo,CoachLevelVo search)  throws Exception;
  public CoachLevelVo queryCoachLevelById(Integer colid,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryCoachLevelByIds(List<Integer> ids,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByObjectAnd(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByObjectOr(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByColid(Integer colid,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByName(String name,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByPrate(Integer prate,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByIsdel(Integer isdel,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByCuid(Integer cuid,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByMuid(Integer muid,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByCtime(Date ctime,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByMtime(String mtime,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByNew0(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByNew1(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByNew2(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByNew3(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByNew4(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByNew5(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByNew6(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery)  throws Exception;
  public List<CoachLevelVo> queryByNew7(CoachLevelVo coachLevelVo,CoachLevelQuery coachLevelQuery)  throws Exception;

}
