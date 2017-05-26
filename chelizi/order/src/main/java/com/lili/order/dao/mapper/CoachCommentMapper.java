package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.CoachCommentPo;

public interface CoachCommentMapper{

  public void addCoachComment(CoachCommentPo coachcommentPo);
  public void addCoachCommentList(List<CoachCommentPo> coachcommentPoList);
  public void delCoachCommentById(Integer ccid);
  public void delCoachCommentByIds(List<Integer> ids);
  public void delCoachCommentByObj(CoachCommentPo coachcommentPo);
  public void saveCoachComment(CoachCommentPo coachcommentPo);
  public void saveCoachCommentList(List<CoachCommentPo> coachcommentPoList);
  public int updateByCcid(CoachCommentPo coachcommentPo,Integer ccid);
  public int updateByStudentId(CoachCommentPo coachcommentPo,Long studentId);
  public int updateByCoachId(CoachCommentPo coachcommentPo,Long coachId);
  public int updateByOrderId(CoachCommentPo coachcommentPo,String orderId);
  public int updateByCourseId(CoachCommentPo coachcommentPo,Integer courseId);
  public int updateByScore(CoachCommentPo coachcommentPo,Integer score);
  public int updateByCtid(CoachCommentPo coachcommentPo,String ctid);
  public int updateByOneWord(CoachCommentPo coachcommentPo,String oneWord);
  public int updateByCotime(CoachCommentPo coachcommentPo,Date cotime);
  public int updateNotNullByObject(CoachCommentPo coachcommentPo,CoachCommentPo search);
  public int updateAllByObject(CoachCommentPo coachcommentPo,CoachCommentPo search);
  public CoachCommentPo queryCoachCommentById(Integer ccid,String postSql,String queryFields);
  public List<CoachCommentPo> queryCoachCommentByIds(List<Integer> ids,String postSql,String queryFields);
  public List<CoachCommentPo> queryByObjectAnd(CoachCommentPo coachcommentPo,String postSql,String queryFields);
  public List<CoachCommentPo> queryByObjectOr(CoachCommentPo coachcommentPo,String postSql,String queryFields);
  public List<CoachCommentPo> queryByCcid(Integer ccid,String postSql,String queryFields);
  public List<CoachCommentPo> queryByStudentId(Long studentId,String postSql,String queryFields);
  public List<CoachCommentPo> queryByCoachId(Long coachId,String postSql,String queryFields);
  public List<CoachCommentPo> queryByOrderId(String orderId,String postSql,String queryFields);
  public List<CoachCommentPo> queryByCourseId(Integer courseId,String postSql,String queryFields);
  public List<CoachCommentPo> queryByScore(Integer score,String postSql,String queryFields);
  public List<CoachCommentPo> queryByCtid(String ctid,String postSql,String queryFields);
  public List<CoachCommentPo> queryByOneWord(String oneWord,String postSql,String queryFields);
  public List<CoachCommentPo> queryByCotime(Date cotime,String postSql,String queryFields);
  public List<CoachCommentPo> queryByNew0(CoachCommentPo coachcommentPo,String postSql,String queryFields);
  public List<CoachCommentPo> queryByNew1(CoachCommentPo coachcommentPo,String postSql,String queryFields);
  public List<CoachCommentPo> queryByNew2(CoachCommentPo coachcommentPo,String postSql,String queryFields);
  public List<CoachCommentPo> queryByNew3(CoachCommentPo coachcommentPo,String postSql,String queryFields);
  public List<CoachCommentPo> queryByNew4(CoachCommentPo coachcommentPo,String postSql,String queryFields);
  public List<CoachCommentPo> queryByNew5(CoachCommentPo coachcommentPo,String postSql,String queryFields);
  public List<CoachCommentPo> queryByNew6(CoachCommentPo coachcommentPo,String postSql,String queryFields);
  public List<CoachCommentPo> queryByNew7(CoachCommentPo coachcommentPo,String postSql,String queryFields);
  public int countByObjectAnd(CoachCommentPo coachcommentPo,String postSql,String queryFields);
}
