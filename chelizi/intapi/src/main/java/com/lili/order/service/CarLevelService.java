package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.CarLevelQuery;
import com.lili.order.vo.CarLevelVo;

public interface CarLevelService {

  public void addCarLevel(CarLevelVo carLevelVo)  throws Exception;
  public void addCarLevelList(List<CarLevelVo> carLevelVoList)  throws Exception;
  public void delCarLevelById(Integer calid)  throws Exception;
  public void delCarLevelByIds(List<Integer> ids)  throws Exception;
  public void delCarLevelByObj(CarLevelVo carLevelVo)  throws Exception;
  public void saveCarLevel(CarLevelVo carLevelVo)  throws Exception;
  public void saveCarLevelList(List<CarLevelVo> carLevelVoList)  throws Exception;
  public int updateByCalid(CarLevelVo carLevelVo,Integer calid)  throws Exception;
  public int updateByName(CarLevelVo carLevelVo,String name)  throws Exception;
  public int updateByPrate(CarLevelVo carLevelVo,Integer prate)  throws Exception;
  public int updateByIsdel(CarLevelVo carLevelVo,Integer isdel)  throws Exception;
  public int updateByCuid(CarLevelVo carLevelVo,Integer cuid)  throws Exception;
  public int updateByMuid(CarLevelVo carLevelVo,Integer muid)  throws Exception;
  public int updateByCtime(CarLevelVo carLevelVo,Date ctime)  throws Exception;
  public int updateByMtime(CarLevelVo carLevelVo,String mtime)  throws Exception;
  public int updateNotNullByObject(CarLevelVo carLevelVo,CarLevelVo search)  throws Exception;
  public int updateAllByObject(CarLevelVo carLevelVo,CarLevelVo search)  throws Exception;
  public CarLevelVo queryCarLevelById(Integer calid,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryCarLevelByIds(List<Integer> ids,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByObjectAnd(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByObjectOr(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByCalid(Integer calid,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByName(String name,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByPrate(Integer prate,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByIsdel(Integer isdel,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByCuid(Integer cuid,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByMuid(Integer muid,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByCtime(Date ctime,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByMtime(String mtime,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByNew0(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByNew1(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByNew2(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByNew3(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByNew4(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByNew5(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByNew6(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery)  throws Exception;
  public List<CarLevelVo> queryByNew7(CarLevelVo carLevelVo,CarLevelQuery carLevelQuery)  throws Exception;

}
