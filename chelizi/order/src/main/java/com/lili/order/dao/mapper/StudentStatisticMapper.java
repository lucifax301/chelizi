package com.lili.order.dao.mapper;

import java.util.List;
import com.lili.order.dao.po.StudentStatisticPo;

public interface StudentStatisticMapper{

  public void addStudentStatistic(StudentStatisticPo studentstatisticPo);
  public void addStudentStatisticList(List<StudentStatisticPo> studentstatisticPoList);
  public void delStudentStatisticById(Integer ssid);
  public void delStudentStatisticByIds(List<Integer> ids);
  public void delStudentStatisticByObj(StudentStatisticPo studentstatisticPo);
  public void saveStudentStatistic(StudentStatisticPo studentstatisticPo);
  public void saveStudentStatisticList(List<StudentStatisticPo> studentstatisticPoList);
  public int updateBySsid(StudentStatisticPo studentstatisticPo,Integer ssid);
  public int updateByStudentId(StudentStatisticPo studentstatisticPo,Long studentId);
  public int updateByCtid(StudentStatisticPo studentstatisticPo,Integer ctid);
  public int updateByTotal(StudentStatisticPo studentstatisticPo,Integer total);
  public int updateByScore(StudentStatisticPo studentstatisticPo,Integer score);
  public int updateNotNullByObject(StudentStatisticPo studentstatisticPo,StudentStatisticPo search);
  public int updateAllByObject(StudentStatisticPo studentstatisticPo,StudentStatisticPo search);
  public StudentStatisticPo queryStudentStatisticById(Integer ssid,String postSql,String queryFields);
  public List<StudentStatisticPo> queryStudentStatisticByIds(List<Integer> ids,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByObjectAnd(StudentStatisticPo studentstatisticPo,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByObjectOr(StudentStatisticPo studentstatisticPo,String postSql,String queryFields);
  public List<StudentStatisticPo> queryBySsid(Integer ssid,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByStudentId(Long studentId,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByCtid(Integer ctid,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByTotal(Integer total,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByScore(Integer score,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByNew0(StudentStatisticPo studentstatisticPo,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByNew1(StudentStatisticPo studentstatisticPo,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByNew2(StudentStatisticPo studentstatisticPo,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByNew3(StudentStatisticPo studentstatisticPo,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByNew4(StudentStatisticPo studentstatisticPo,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByNew5(StudentStatisticPo studentstatisticPo,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByNew6(StudentStatisticPo studentstatisticPo,String postSql,String queryFields);
  public List<StudentStatisticPo> queryByNew7(StudentStatisticPo studentstatisticPo,String postSql,String queryFields);

}
