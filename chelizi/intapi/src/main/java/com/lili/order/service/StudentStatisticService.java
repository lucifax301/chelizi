package com.lili.order.service;

import com.lili.order.vo.StudentStatisticQuery;
import java.util.List;
import com.lili.order.vo.StudentStatisticVo;

public interface StudentStatisticService {

  public void addStudentStatistic(StudentStatisticVo studentstatisticVo)  throws Exception;
  public void addStudentStatisticList(List<StudentStatisticVo> studentstatisticVoList)  throws Exception;
  public void delStudentStatisticById(Integer ssid)  throws Exception;
  public void delStudentStatisticByIds(List<Integer> ids)  throws Exception;
  public void delStudentStatisticByObj(StudentStatisticVo studentstatisticVo)  throws Exception;
  public void saveStudentStatistic(StudentStatisticVo studentstatisticVo)  throws Exception;
  public void saveStudentStatisticList(List<StudentStatisticVo> studentstatisticVoList)  throws Exception;
  public int updateBySsid(StudentStatisticVo studentstatisticVo,Integer ssid)  throws Exception;
  public int updateByStudentId(StudentStatisticVo studentstatisticVo,Long studentId)  throws Exception;
  public int updateByCtid(StudentStatisticVo studentstatisticVo,Integer ctid)  throws Exception;
  public int updateByTotal(StudentStatisticVo studentstatisticVo,Integer total)  throws Exception;
  public int updateByScore(StudentStatisticVo studentstatisticVo,Integer score)  throws Exception;
  public int updateNotNullByObject(StudentStatisticVo studentstatisticVo,StudentStatisticVo search)  throws Exception;
  public int updateAllByObject(StudentStatisticVo studentstatisticVo,StudentStatisticVo search)  throws Exception;
  public StudentStatisticVo queryStudentStatisticById(Integer ssid,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryStudentStatisticByIds(List<Integer> ids,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByObjectAnd(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByObjectOr(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryBySsid(Integer ssid,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByStudentId(Long studentId,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByCtid(Integer ctid,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByTotal(Integer total,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByScore(Integer score,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByNew0(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByNew1(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByNew2(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByNew3(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByNew4(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByNew5(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByNew6(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery)  throws Exception;
  public List<StudentStatisticVo> queryByNew7(StudentStatisticVo studentstatisticVo,StudentStatisticQuery studentstatisticQuery)  throws Exception;

}
