package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.CarLevelPo;

public interface CarLevelMapper{

  public void addCarLevel(CarLevelPo carLevelPo);
  public void addCarLevelList(List<CarLevelPo> carLevelPoList);
  public void delCarLevelById(Integer calid);
  public void delCarLevelByIds(List<Integer> ids);
  public void delCarLevelByObj(CarLevelPo carLevelPo);
  public void saveCarLevel(CarLevelPo carLevelPo);
  public void saveCarLevelList(List<CarLevelPo> carLevelPoList);
  public int updateByCalid(CarLevelPo carLevelPo,Integer calid);
  public int updateByName(CarLevelPo carLevelPo,String name);
  public int updateByPrate(CarLevelPo carLevelPo,Integer prate);
  public int updateByIsdel(CarLevelPo carLevelPo,Integer isdel);
  public int updateByCuid(CarLevelPo carLevelPo,Integer cuid);
  public int updateByMuid(CarLevelPo carLevelPo,Integer muid);
  public int updateByCtime(CarLevelPo carLevelPo,Date ctime);
  public int updateByMtime(CarLevelPo carLevelPo,String mtime);
  public int updateNotNullByObject(CarLevelPo carLevelPo,CarLevelPo search);
  public int updateAllByObject(CarLevelPo carLevelPo,CarLevelPo search);
  public CarLevelPo queryCarLevelById(Integer calid,String postSql,String queryFields);
  public List<CarLevelPo> queryCarLevelByIds(List<Integer> ids,String postSql,String queryFields);
  public List<CarLevelPo> queryByObjectAnd(CarLevelPo carLevelPo,String postSql,String queryFields);
  public List<CarLevelPo> queryByObjectOr(CarLevelPo carLevelPo,String postSql,String queryFields);
  public List<CarLevelPo> queryByCalid(Integer calid,String postSql,String queryFields);
  public List<CarLevelPo> queryByName(String name,String postSql,String queryFields);
  public List<CarLevelPo> queryByPrate(Integer prate,String postSql,String queryFields);
  public List<CarLevelPo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<CarLevelPo> queryByCuid(Integer cuid,String postSql,String queryFields);
  public List<CarLevelPo> queryByMuid(Integer muid,String postSql,String queryFields);
  public List<CarLevelPo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<CarLevelPo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<CarLevelPo> queryByNew0(CarLevelPo carLevelPo,String postSql,String queryFields);
  public List<CarLevelPo> queryByNew1(CarLevelPo carLevelPo,String postSql,String queryFields);
  public List<CarLevelPo> queryByNew2(CarLevelPo carLevelPo,String postSql,String queryFields);
  public List<CarLevelPo> queryByNew3(CarLevelPo carLevelPo,String postSql,String queryFields);
  public List<CarLevelPo> queryByNew4(CarLevelPo carLevelPo,String postSql,String queryFields);
  public List<CarLevelPo> queryByNew5(CarLevelPo carLevelPo,String postSql,String queryFields);
  public List<CarLevelPo> queryByNew6(CarLevelPo carLevelPo,String postSql,String queryFields);
  public List<CarLevelPo> queryByNew7(CarLevelPo carLevelPo,String postSql,String queryFields);

}
