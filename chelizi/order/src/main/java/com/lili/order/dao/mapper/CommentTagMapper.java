package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.CommentTagPo;

public interface CommentTagMapper{

  public void addCommentTag(CommentTagPo commentTagPo);
  public void addCommentTagList(List<CommentTagPo> commentTagPoList);
  public void delCommentTagById(Integer ctid);
  public void delCommentTagByIds(List<Integer> ids);
  public void delCommentTagByObj(CommentTagPo commentTagPo);
  public void saveCommentTag(CommentTagPo commentTagPo);
  public void saveCommentTagList(List<CommentTagPo> commentTagPoList);
  public int updateByCtid(CommentTagPo commentTagPo,Integer ctid);
  public int updateByTag(CommentTagPo commentTagPo,String tag);
  public int updateByCourseId(CommentTagPo commentTagPo,Integer courseId);
  public int updateByType(CommentTagPo commentTagPo,Integer type);
  public int updateByGoodBad(CommentTagPo commentTagPo,Integer goodBad);
  public int updateByIsdel(CommentTagPo commentTagPo,Integer isdel);
  public int updateByCuid(CommentTagPo commentTagPo,Integer cuid);
  public int updateByMuid(CommentTagPo commentTagPo,Integer muid);
  public int updateByCtime(CommentTagPo commentTagPo,Date ctime);
  public int updateByMtime(CommentTagPo commentTagPo,String mtime);
  public int updateNotNullByObject(CommentTagPo commentTagPo,CommentTagPo search);
  public int updateAllByObject(CommentTagPo commentTagPo,CommentTagPo search);
  public CommentTagPo queryCommentTagById(Integer ctid,String postSql,String queryFields);
  public List<CommentTagPo> queryCommentTagByIds(List<Integer> ids,String postSql,String queryFields);
  public List<CommentTagPo> queryByObjectAnd(CommentTagPo commentTagPo,String postSql,String queryFields);
  public List<CommentTagPo> queryByObjectOr(CommentTagPo commentTagPo,String postSql,String queryFields);
  public List<CommentTagPo> queryByCtid(Integer ctid,String postSql,String queryFields);
  public List<CommentTagPo> queryByTag(String tag,String postSql,String queryFields);
  public List<CommentTagPo> queryByCourseId(Integer courseId,String postSql,String queryFields);
  public List<CommentTagPo> queryByType(Integer type,String postSql,String queryFields);
  public List<CommentTagPo> queryByGoodBad(Integer goodBad,String postSql,String queryFields);
  public List<CommentTagPo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<CommentTagPo> queryByCuid(Integer cuid,String postSql,String queryFields);
  public List<CommentTagPo> queryByMuid(Integer muid,String postSql,String queryFields);
  public List<CommentTagPo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<CommentTagPo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<CommentTagPo> queryByNew0(CommentTagPo commentTagPo,String postSql,String queryFields);
  public List<CommentTagPo> queryByNew1(CommentTagPo commentTagPo,String postSql,String queryFields);
  public List<CommentTagPo> queryByNew2(CommentTagPo commentTagPo,String postSql,String queryFields);
  public List<CommentTagPo> queryByNew3(CommentTagPo commentTagPo,String postSql,String queryFields);
  public List<CommentTagPo> queryByNew4(CommentTagPo commentTagPo,String postSql,String queryFields);
  public List<CommentTagPo> queryByNew5(CommentTagPo commentTagPo,String postSql,String queryFields);
  public List<CommentTagPo> queryByNew6(CommentTagPo commentTagPo,String postSql,String queryFields);
  public List<CommentTagPo> queryByNew7(CommentTagPo commentTagPo,String postSql,String queryFields);

}
