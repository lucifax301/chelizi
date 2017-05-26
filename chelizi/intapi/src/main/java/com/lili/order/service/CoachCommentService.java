package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.CoachCommentQuery;
import com.lili.order.vo.CoachCommentVo;

public interface CoachCommentService {

  public void addCoachComment(CoachCommentVo coachcommentVo)  throws Exception;
  public void addCoachCommentList(List<CoachCommentVo> coachcommentVoList)  throws Exception;
  public void delCoachCommentById(Integer ccid)  throws Exception;
  public void delCoachCommentByIds(List<Integer> ids)  throws Exception;
  public void delCoachCommentByObj(CoachCommentVo coachcommentVo)  throws Exception;
  public void saveCoachComment(CoachCommentVo coachcommentVo)  throws Exception;
  public void saveCoachCommentList(List<CoachCommentVo> coachcommentVoList)  throws Exception;
  public int updateByCcid(CoachCommentVo coachcommentVo,Integer ccid)  throws Exception;
  public int updateByStudentId(CoachCommentVo coachcommentVo,Long studentId)  throws Exception;
  public int updateByCoachId(CoachCommentVo coachcommentVo,Long coachId)  throws Exception;
  public int updateByOrderId(CoachCommentVo coachcommentVo,String orderId)  throws Exception;
  public int updateByCourseId(CoachCommentVo coachcommentVo,Integer courseId)  throws Exception;
  public int updateByScore(CoachCommentVo coachcommentVo,Integer score)  throws Exception;
  public int updateByCtid(CoachCommentVo coachcommentVo,String ctid)  throws Exception;
  public int updateByOneWord(CoachCommentVo coachcommentVo,String oneWord)  throws Exception;
  public int updateByCotime(CoachCommentVo coachcommentVo,Date cotime)  throws Exception;
  public int updateNotNullByObject(CoachCommentVo coachcommentVo,CoachCommentVo search)  throws Exception;
  public int updateAllByObject(CoachCommentVo coachcommentVo,CoachCommentVo search)  throws Exception;
  public CoachCommentVo queryCoachCommentById(Integer ccid,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryCoachCommentByIds(List<Integer> ids,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByObjectAnd(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByObjectOr(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByCcid(Integer ccid,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByStudentId(Long studentId,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByCoachId(Long coachId,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByOrderId(String orderId,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByCourseId(Integer courseId,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByScore(Integer score,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByCtid(String ctid,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByOneWord(String oneWord,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByCotime(Date cotime,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByNew0(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByNew1(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByNew2(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByNew3(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByNew4(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByNew5(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByNew6(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery)  throws Exception;
  public List<CoachCommentVo> queryByNew7(CoachCommentVo coachcommentVo,CoachCommentQuery coachcommentQuery)  throws Exception;
  public int countByCoachId(Long coachId) throws Exception;
}
