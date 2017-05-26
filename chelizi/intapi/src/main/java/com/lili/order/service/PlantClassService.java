package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.PlantClassVo;
import com.lili.order.vo.PlantClassQuery;

public interface PlantClassService {

  public void addPlantClass(PlantClassVo plantClassVo)  throws Exception;
  public void addPlantClassList(List<PlantClassVo> plantClassVoList)  throws Exception;
  public void delPlantClassById(String orderId)  throws Exception;
  public void delPlantClassByIds(List<String> ids)  throws Exception;
  public void delPlantClassByObj(PlantClassVo plantClassVo)  throws Exception;
  public void savePlantClass(PlantClassVo plantClassVo)  throws Exception;
  public void savePlantClassList(List<PlantClassVo> plantClassVoList)  throws Exception;
  public int updateByOrderId(PlantClassVo plantClassVo,String orderId)  throws Exception;
  public int updateByCcid(PlantClassVo plantClassVo,Integer ccid)  throws Exception;
  public int updateByCoachId(PlantClassVo plantClassVo,Long coachId)  throws Exception;
  public int updateByStudentId(PlantClassVo plantClassVo,Long studentId)  throws Exception;
  public int updateByGtime(PlantClassVo plantClassVo,Date gtime)  throws Exception;
  public int updateByIsdel(PlantClassVo plantClassVo,Integer isdel)  throws Exception;
  public int updateNotNullByObject(PlantClassVo plantClassVo,PlantClassVo search)  throws Exception;
  public int updateAllByObject(PlantClassVo plantClassVo,PlantClassVo search)  throws Exception;
  public PlantClassVo queryPlantClassById(String orderId,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryPlantClassByIds(List<String> ids,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByObjectAnd(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByObjectOr(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByOrderId(String orderId,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByCcid(Integer ccid,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByCoachId(Long coachId,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByStudentId(Long studentId,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByGtime(Date gtime,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByIsdel(Integer isdel,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByNew0(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByNew1(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByNew2(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByNew3(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByNew4(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByNew5(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByNew6(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery)  throws Exception;
  public List<PlantClassVo> queryByNew7(PlantClassVo plantClassVo,PlantClassQuery plantClassQuery)  throws Exception;

}
