package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.CoachLevelPo;

public interface CoachLevelMapper{

  public void addCoachLevel(CoachLevelPo coachLevelPo);
  public void addCoachLevelList(List<CoachLevelPo> coachLevelPoList);
  public void delCoachLevelById(Integer colid);
  public void delCoachLevelByIds(List<Integer> ids);
  public void delCoachLevelByObj(CoachLevelPo coachLevelPo);
  public void saveCoachLevel(CoachLevelPo coachLevelPo);
  public void saveCoachLevelList(List<CoachLevelPo> coachLevelPoList);
  public int updateByColid(CoachLevelPo coachLevelPo,Integer colid);
  public int updateByName(CoachLevelPo coachLevelPo,String name);
  public int updateByPrate(CoachLevelPo coachLevelPo,Integer prate);
  public int updateByIsdel(CoachLevelPo coachLevelPo,Integer isdel);
  public int updateByCuid(CoachLevelPo coachLevelPo,Integer cuid);
  public int updateByMuid(CoachLevelPo coachLevelPo,Integer muid);
  public int updateByCtime(CoachLevelPo coachLevelPo,Date ctime);
  public int updateByMtime(CoachLevelPo coachLevelPo,String mtime);
  public int updateNotNullByObject(CoachLevelPo coachLevelPo,CoachLevelPo search);
  public int updateAllByObject(CoachLevelPo coachLevelPo,CoachLevelPo search);
  public CoachLevelPo queryCoachLevelById(Integer colid,String postSql,String queryFields);
  public List<CoachLevelPo> queryCoachLevelByIds(List<Integer> ids,String postSql,String queryFields);
  public List<CoachLevelPo> queryByObjectAnd(CoachLevelPo coachLevelPo,String postSql,String queryFields);
  public List<CoachLevelPo> queryByObjectOr(CoachLevelPo coachLevelPo,String postSql,String queryFields);
  public List<CoachLevelPo> queryByColid(Integer colid,String postSql,String queryFields);
  public List<CoachLevelPo> queryByName(String name,String postSql,String queryFields);
  public List<CoachLevelPo> queryByPrate(Integer prate,String postSql,String queryFields);
  public List<CoachLevelPo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<CoachLevelPo> queryByCuid(Integer cuid,String postSql,String queryFields);
  public List<CoachLevelPo> queryByMuid(Integer muid,String postSql,String queryFields);
  public List<CoachLevelPo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<CoachLevelPo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<CoachLevelPo> queryByNew0(CoachLevelPo coachLevelPo,String postSql,String queryFields);
  public List<CoachLevelPo> queryByNew1(CoachLevelPo coachLevelPo,String postSql,String queryFields);
  public List<CoachLevelPo> queryByNew2(CoachLevelPo coachLevelPo,String postSql,String queryFields);
  public List<CoachLevelPo> queryByNew3(CoachLevelPo coachLevelPo,String postSql,String queryFields);
  public List<CoachLevelPo> queryByNew4(CoachLevelPo coachLevelPo,String postSql,String queryFields);
  public List<CoachLevelPo> queryByNew5(CoachLevelPo coachLevelPo,String postSql,String queryFields);
  public List<CoachLevelPo> queryByNew6(CoachLevelPo coachLevelPo,String postSql,String queryFields);
  public List<CoachLevelPo> queryByNew7(CoachLevelPo coachLevelPo,String postSql,String queryFields);

}
