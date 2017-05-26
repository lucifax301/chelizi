package com.lili.order.service;

import java.util.Date;
import java.util.List;

import com.lili.order.vo.StuCommentQuery;
import com.lili.order.vo.StuCommentVo;
import com.lili.order.vo.StuOrderCommentVo;

public interface StuCommentService {

  public void addStuComment(StuCommentVo stuCommentVo)  throws Exception;
  public void addStuCommentList(List<StuCommentVo> stuCommentVoList)  throws Exception;
  public void delStuCommentById(Integer scid)  throws Exception;
  public void delStuCommentByIds(List<Integer> ids)  throws Exception;
  public void delStuCommentByObj(StuCommentVo stuCommentVo)  throws Exception;
  public void saveStuComment(StuCommentVo stuCommentVo)  throws Exception;
  public void saveStuCommentList(List<StuCommentVo> stuCommentVoList)  throws Exception;
  public int updateByScid(StuCommentVo stuCommentVo,Integer scid)  throws Exception;
  public int updateByStudentId(StuCommentVo stuCommentVo,Long studentId)  throws Exception;
  public int updateByCoachId(StuCommentVo stuCommentVo,Long coachId)  throws Exception;
  public int updateByOrderId(StuCommentVo stuCommentVo,String orderId)  throws Exception;
  public int updateByCourseId(StuCommentVo stuCommentVo,Integer courseId)  throws Exception;
  public int updateByCtid(StuCommentVo stuCommentVo,Integer ctid)  throws Exception;
  public int updateByScore(StuCommentVo stuCommentVo,Integer score)  throws Exception;
  public int updateByOneWord(StuCommentVo stuCommentVo,String oneWord)  throws Exception;
  public int updateByCotime(StuCommentVo stuCommentVo,Date cotime)  throws Exception;
  public int updateNotNullByObject(StuCommentVo stuCommentVo,StuCommentVo search)  throws Exception;
  public int updateAllByObject(StuCommentVo stuCommentVo,StuCommentVo search)  throws Exception;
  public StuCommentVo queryStuCommentById(Integer scid,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryStuCommentByIds(List<Integer> ids,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByObjectAnd(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByObjectOr(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByScid(Integer scid,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByStudentId(Long studentId,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByCoachId(Long coachId,StuCommentQuery stuCommentQuery)  throws Exception;
  public StuOrderCommentVo queryByOrderId(String orderId,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByCourseId(Integer courseId,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByCtid(Integer ctid,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByScore(Integer score,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByOneWord(String oneWord,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByCotime(Date cotime,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByTags(List<Integer> ctids,Long studentId) throws Exception;
  public List<StuCommentVo> queryByNew1(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByNew2(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByNew3(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByNew4(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByNew5(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByNew6(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuCommentVo> queryByNew7(StuCommentVo stuCommentVo,StuCommentQuery stuCommentQuery)  throws Exception;
  public List<StuOrderCommentVo> getStuCommentList(long studentId,int pageIndex,int pageSize,Integer courseId,Integer subjectId, String v);
}
