package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.StuCommentPo;

public interface StuCommentMapper{

  public void addStuComment(StuCommentPo stuCommentPo);
  public void addStuCommentList(List<StuCommentPo> stuCommentPoList);
  public void delStuCommentById(Integer scid);
  public void delStuCommentByIds(List<Integer> ids);
  public void delStuCommentByObj(StuCommentPo stuCommentPo);
  public void saveStuComment(StuCommentPo stuCommentPo);
  public void saveStuCommentList(List<StuCommentPo> stuCommentPoList);
  public int updateByScid(StuCommentPo stuCommentPo,Integer scid);
  public int updateByStudentId(StuCommentPo stuCommentPo,Long studentId);
  public int updateByCoachId(StuCommentPo stuCommentPo,Long coachId);
  public int updateByOrderId(StuCommentPo stuCommentPo,String orderId);
  public int updateByCourseId(StuCommentPo stuCommentPo,Integer courseId);
  public int updateByCtid(StuCommentPo stuCommentPo,Integer ctid);
  public int updateByScore(StuCommentPo stuCommentPo,Integer score);
  public int updateByOneWord(StuCommentPo stuCommentPo,String oneWord);
  public int updateByCotime(StuCommentPo stuCommentPo,Date cotime);
  public int updateNotNullByObject(StuCommentPo stuCommentPo,StuCommentPo search);
  public int updateAllByObject(StuCommentPo stuCommentPo,StuCommentPo search);
  public StuCommentPo queryStuCommentById(Integer scid,String postSql,String queryFields);
  public List<StuCommentPo> queryStuCommentByIds(List<Integer> ids,String postSql,String queryFields);
  public List<StuCommentPo> queryByObjectAnd(StuCommentPo stuCommentPo,String postSql,String queryFields);
  public List<StuCommentPo> queryByObjectOr(StuCommentPo stuCommentPo,String postSql,String queryFields);
  public List<StuCommentPo> queryByScid(Integer scid,String postSql,String queryFields);
  public List<StuCommentPo> queryByStudentId(Long studentId,String postSql,String queryFields);
  public List<StuCommentPo> queryByCoachId(Long coachId,String postSql,String queryFields);
  public List<StuCommentPo> queryByOrderId(String orderId,String postSql,String queryFields);
  public List<StuCommentPo> queryByCourseId(Integer courseId,String postSql,String queryFields);
  public List<StuCommentPo> queryByCtid(Integer ctid,String postSql,String queryFields);
  public List<StuCommentPo> queryByScore(Integer score,String postSql,String queryFields);
  public List<StuCommentPo> queryByOneWord(String oneWord,String postSql,String queryFields);
  public List<StuCommentPo> queryByCotime(Date cotime,String postSql,String queryFields);
  public List<StuCommentPo> queryByNew0(List<Integer> ctids,String postSql,long studentId);
  public List<StuCommentPo> queryByNew1(StuCommentPo stuCommentPo,String postSql,String queryFields);
  public List<StuCommentPo> queryByNew2(StuCommentPo stuCommentPo,String postSql,String queryFields);
  public List<StuCommentPo> queryByNew3(StuCommentPo stuCommentPo,String postSql,String queryFields);
  public List<StuCommentPo> queryByNew4(StuCommentPo stuCommentPo,String postSql,String queryFields);
  public List<StuCommentPo> queryByNew5(StuCommentPo stuCommentPo,String postSql,String queryFields);
  public List<StuCommentPo> queryByNew6(StuCommentPo stuCommentPo,String postSql,String queryFields);
  public List<StuCommentPo> queryByNew7(StuCommentPo stuCommentPo,String postSql,String queryFields);

}
