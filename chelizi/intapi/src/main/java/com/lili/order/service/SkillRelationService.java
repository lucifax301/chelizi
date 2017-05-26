package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.SkillRelationQuery;
import com.lili.order.vo.SkillRelationVo;

public interface SkillRelationService {

  public void addSkillRelation(SkillRelationVo skillRelationVo)  throws Exception;
  public void addSkillRelationList(List<SkillRelationVo> skillRelationVoList)  throws Exception;
  public void delSkillRelationById(Integer sid)  throws Exception;
  public void delSkillRelationByIds(List<Integer> ids)  throws Exception;
  public void delSkillRelationByObj(SkillRelationVo skillRelationVo)  throws Exception;
  public void saveSkillRelation(SkillRelationVo skillRelationVo)  throws Exception;
  public void saveSkillRelationList(List<SkillRelationVo> skillRelationVoList)  throws Exception;
  public int updateBySid(SkillRelationVo skillRelationVo,Integer sid)  throws Exception;
  public int updateByCtid(SkillRelationVo skillRelationVo,Integer ctid)  throws Exception;
  public int updateByCityId(SkillRelationVo skillRelationVo,Integer cityId)  throws Exception;
  public int updateByCourseId(SkillRelationVo skillRelationVo,Integer courseId)  throws Exception;
  public int updateBySubjectId(SkillRelationVo skillRelationVo,Integer subjectId)  throws Exception;
  public int updateByIsdel(SkillRelationVo skillRelationVo,Integer isdel)  throws Exception;
  public int updateByCuid(SkillRelationVo skillRelationVo,Integer cuid)  throws Exception;
  public int updateByMuid(SkillRelationVo skillRelationVo,Integer muid)  throws Exception;
  public int updateByCtime(SkillRelationVo skillRelationVo,Date ctime)  throws Exception;
  public int updateByMtime(SkillRelationVo skillRelationVo,String mtime)  throws Exception;
  public int updateNotNullByObject(SkillRelationVo skillRelationVo,SkillRelationVo search)  throws Exception;
  public int updateAllByObject(SkillRelationVo skillRelationVo,SkillRelationVo search)  throws Exception;
  public SkillRelationVo querySkillRelationById(Integer sid,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> querySkillRelationByIds(List<Integer> ids,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByObjectAnd(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByObjectOr(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryBySid(Integer sid,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByCtid(Integer ctid,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByCityId(Integer cityId,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByCourseId(Integer courseId,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryBySubjectId(Integer subjectId,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByIsdel(Integer isdel,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByCuid(Integer cuid,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByMuid(Integer muid,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByCtime(Date ctime,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByMtime(String mtime,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByNew0(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByNew1(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByNew2(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByNew3(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByNew4(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByNew5(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByNew6(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery)  throws Exception;
  public List<SkillRelationVo> queryByNew7(SkillRelationVo skillRelationVo,SkillRelationQuery skillRelationQuery)  throws Exception;

}
