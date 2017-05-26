package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.CommentTagQuery;
import com.lili.order.vo.CommentTagVo;

public interface CommentTagService {

  public void addCommentTag(CommentTagVo commentTagVo)  throws Exception;
  public void addCommentTagList(List<CommentTagVo> commentTagVoList)  throws Exception;
  public void delCommentTagById(Integer ctid)  throws Exception;
  public void delCommentTagByIds(List<Integer> ids)  throws Exception;
  public void delCommentTagByObj(CommentTagVo commentTagVo)  throws Exception;
  public void saveCommentTag(CommentTagVo commentTagVo)  throws Exception;
  public void saveCommentTagList(List<CommentTagVo> commentTagVoList)  throws Exception;
  public int updateByCtid(CommentTagVo commentTagVo,Integer ctid)  throws Exception;
  public int updateByTag(CommentTagVo commentTagVo,String tag)  throws Exception;
  public int updateByCourseId(CommentTagVo commentTagVo,Integer courseId)  throws Exception;
  public int updateByType(CommentTagVo commentTagVo,Integer type)  throws Exception;
  public int updateByGoodBad(CommentTagVo commentTagVo,Integer goodBad)  throws Exception;
  public int updateByIsdel(CommentTagVo commentTagVo,Integer isdel)  throws Exception;
  public int updateByCuid(CommentTagVo commentTagVo,Integer cuid)  throws Exception;
  public int updateByMuid(CommentTagVo commentTagVo,Integer muid)  throws Exception;
  public int updateByCtime(CommentTagVo commentTagVo,Date ctime)  throws Exception;
  public int updateByMtime(CommentTagVo commentTagVo,String mtime)  throws Exception;
  public int updateNotNullByObject(CommentTagVo commentTagVo,CommentTagVo search)  throws Exception;
  public int updateAllByObject(CommentTagVo commentTagVo,CommentTagVo search)  throws Exception;
  public CommentTagVo queryCommentTagById(Integer ctid,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryCommentTagByIds(List<Integer> ids,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByObjectAnd(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByObjectOr(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByCtid(Integer ctid,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByTag(String tag,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByCourseId(Integer courseId,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByType(Integer type,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByGoodBad(Integer goodBad,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByIsdel(Integer isdel,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByCuid(Integer cuid,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByMuid(Integer muid,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByCtime(Date ctime,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByMtime(String mtime,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByNew0(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByNew1(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByNew2(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByNew3(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByNew4(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByNew5(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByNew6(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery)  throws Exception;
  public List<CommentTagVo> queryByNew7(CommentTagVo commentTagVo,CommentTagQuery commentTagQuery)  throws Exception;

}
