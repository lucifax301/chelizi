package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.SkillRelationPo;

public interface SkillRelationMapper{

  public void addSkillRelation(SkillRelationPo skillRelationPo);
  public void addSkillRelationList(List<SkillRelationPo> skillRelationPoList);
  public void delSkillRelationById(Integer sid);
  public void delSkillRelationByIds(List<Integer> ids);
  public void delSkillRelationByObj(SkillRelationPo skillRelationPo);
  public void saveSkillRelation(SkillRelationPo skillRelationPo);
  public void saveSkillRelationList(List<SkillRelationPo> skillRelationPoList);
  public int updateBySid(SkillRelationPo skillRelationPo,Integer sid);
  public int updateByCtid(SkillRelationPo skillRelationPo,Integer ctid);
  public int updateByCityId(SkillRelationPo skillRelationPo,Integer cityId);
  public int updateByCourseId(SkillRelationPo skillRelationPo,Integer courseId);
  public int updateBySubjectId(SkillRelationPo skillRelationPo,Integer subjectId);
  public int updateByIsdel(SkillRelationPo skillRelationPo,Integer isdel);
  public int updateByCuid(SkillRelationPo skillRelationPo,Integer cuid);
  public int updateByMuid(SkillRelationPo skillRelationPo,Integer muid);
  public int updateByCtime(SkillRelationPo skillRelationPo,Date ctime);
  public int updateByMtime(SkillRelationPo skillRelationPo,String mtime);
  public int updateNotNullByObject(SkillRelationPo skillRelationPo,SkillRelationPo search);
  public int updateAllByObject(SkillRelationPo skillRelationPo,SkillRelationPo search);
  public SkillRelationPo querySkillRelationById(Integer sid,String postSql,String queryFields);
  public List<SkillRelationPo> querySkillRelationByIds(List<Integer> ids,String postSql,String queryFields);
  public List<SkillRelationPo> queryByObjectAnd(SkillRelationPo skillRelationPo,String postSql,String queryFields);
  public List<SkillRelationPo> queryByObjectOr(SkillRelationPo skillRelationPo,String postSql,String queryFields);
  public List<SkillRelationPo> queryBySid(Integer sid,String postSql,String queryFields);
  public List<SkillRelationPo> queryByCtid(Integer ctid,String postSql,String queryFields);
  public List<SkillRelationPo> queryByCityId(Integer cityId,String postSql,String queryFields);
  public List<SkillRelationPo> queryByCourseId(Integer courseId,String postSql,String queryFields);
  public List<SkillRelationPo> queryBySubjectId(Integer subjectId,String postSql,String queryFields);
  public List<SkillRelationPo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<SkillRelationPo> queryByCuid(Integer cuid,String postSql,String queryFields);
  public List<SkillRelationPo> queryByMuid(Integer muid,String postSql,String queryFields);
  public List<SkillRelationPo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<SkillRelationPo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<SkillRelationPo> queryByNew0(SkillRelationPo skillRelationPo,String postSql,String queryFields);
  public List<SkillRelationPo> queryByNew1(SkillRelationPo skillRelationPo,String postSql,String queryFields);
  public List<SkillRelationPo> queryByNew2(SkillRelationPo skillRelationPo,String postSql,String queryFields);
  public List<SkillRelationPo> queryByNew3(SkillRelationPo skillRelationPo,String postSql,String queryFields);
  public List<SkillRelationPo> queryByNew4(SkillRelationPo skillRelationPo,String postSql,String queryFields);
  public List<SkillRelationPo> queryByNew5(SkillRelationPo skillRelationPo,String postSql,String queryFields);
  public List<SkillRelationPo> queryByNew6(SkillRelationPo skillRelationPo,String postSql,String queryFields);
  public List<SkillRelationPo> queryByNew7(SkillRelationPo skillRelationPo,String postSql,String queryFields);

}
