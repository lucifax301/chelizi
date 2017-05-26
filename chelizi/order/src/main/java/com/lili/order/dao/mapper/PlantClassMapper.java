package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.PlantClassPo;

public interface PlantClassMapper{

  public void addPlantClass(PlantClassPo plantClasspo);
  public void addPlantClassList(List<PlantClassPo> plantClasspoList);
  public void delPlantClassById(String orderId);
  public void delPlantClassByIds(List<String> ids);
  public void delPlantClassByObj(PlantClassPo plantClasspo);
  public void savePlantClass(PlantClassPo plantClasspo);
  public void savePlantClassList(List<PlantClassPo> plantClasspoList);
  public int updateByOrderId(PlantClassPo plantClasspo,String orderId);
  public int updateByCcid(PlantClassPo plantClasspo,Integer ccid);
  public int updateByCoachId(PlantClassPo plantClasspo,Long coachId);
  public int updateByStudentId(PlantClassPo plantClasspo,Long studentId);
  public int updateByGtime(PlantClassPo plantClasspo,Date gtime);
  public int updateByStuName(PlantClassPo plantClasspo,String stuName);
  public int updateByStuImg(PlantClassPo plantClasspo,String stuImg);
  public int updateByStuMobile(PlantClassPo plantClasspo,String stuMobile);
  public int updateByIsdel(PlantClassPo plantClasspo,Integer isdel);
  public int updateNotNullByObject(PlantClassPo plantClasspo,PlantClassPo search);
  public int updateAllByObject(PlantClassPo plantClasspo,PlantClassPo search);
  public PlantClassPo queryPlantClassById(String orderId,String postSql,String queryFields);
  public List<PlantClassPo> queryPlantClassByIds(List<String> ids,String postSql,String queryFields);
  public List<PlantClassPo> queryByObjectAnd(PlantClassPo plantClasspo,String postSql,String queryFields);
  public List<PlantClassPo> queryByObjectOr(PlantClassPo plantClasspo,String postSql,String queryFields);
  public List<PlantClassPo> queryByOrderId(String orderId,String postSql,String queryFields);
  public List<PlantClassPo> queryByCcid(Integer ccid,String postSql,String queryFields);
  public List<PlantClassPo> queryByCoachId(Long coachId,String postSql,String queryFields);
  public List<PlantClassPo> queryByStudentId(Long studentId,String postSql,String queryFields);
  public List<PlantClassPo> queryByGtime(Date gtime,String postSql,String queryFields);
  public List<PlantClassPo> queryByStuName(String stuName,String postSql,String queryFields);
  public List<PlantClassPo> queryByStuImg(String stuImg,String postSql,String queryFields);
  public List<PlantClassPo> queryByStuMobile(String stuMobile,String postSql,String queryFields);
  public List<PlantClassPo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<PlantClassPo> queryByNew0(PlantClassPo plantClasspo,String postSql,String queryFields);
  public List<PlantClassPo> queryByNew1(PlantClassPo plantClasspo,String postSql,String queryFields);
  public List<PlantClassPo> queryByNew2(PlantClassPo plantClasspo,String postSql,String queryFields);
  public List<PlantClassPo> queryByNew3(PlantClassPo plantClasspo,String postSql,String queryFields);
  public List<PlantClassPo> queryByNew4(PlantClassPo plantClasspo,String postSql,String queryFields);
  public List<PlantClassPo> queryByNew5(PlantClassPo plantClasspo,String postSql,String queryFields);
  public List<PlantClassPo> queryByNew6(PlantClassPo plantClasspo,String postSql,String queryFields);
  public List<PlantClassPo> queryByNew7(PlantClassPo plantClasspo,String postSql,String queryFields);

}
