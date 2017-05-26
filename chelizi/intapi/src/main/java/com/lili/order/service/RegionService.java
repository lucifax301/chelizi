package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.RegionQuery;
import com.lili.order.vo.RegionVo;

public interface RegionService {

  public void addRegion(RegionVo regionVo)  throws Exception;
  public void addRegionList(List<RegionVo> regionVoList)  throws Exception;
  public void delRegionById(Integer rid)  throws Exception;
  public void delRegionByIds(List<Integer> ids)  throws Exception;
  public void delRegionByObj(RegionVo regionVo)  throws Exception;
  public void saveRegion(RegionVo regionVo)  throws Exception;
  public void saveRegionList(List<RegionVo> regionVoList)  throws Exception;
  public int updateByRid(RegionVo regionVo,Integer rid)  throws Exception;
  public int updateByRegion(RegionVo regionVo,String region)  throws Exception;
  public int updateByRlevel(RegionVo regionVo,Integer rlevel)  throws Exception;
  public int updateByPid(RegionVo regionVo,Integer pid)  throws Exception;
  public int updateByIsdel(RegionVo regionVo,Integer isdel)  throws Exception;
  public int updateByCuid(RegionVo regionVo,Integer cuid)  throws Exception;
  public int updateByMuid(RegionVo regionVo,Integer muid)  throws Exception;
  public int updateByCtime(RegionVo regionVo,Date ctime)  throws Exception;
  public int updateByMtime(RegionVo regionVo,String mtime)  throws Exception;
  public int updateNotNullByObject(RegionVo regionVo,RegionVo search)  throws Exception;
  public int updateAllByObject(RegionVo regionVo,RegionVo search)  throws Exception;
  public RegionVo queryRegionById(Integer rid,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryRegionByIds(List<Integer> ids,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByObjectAnd(RegionVo regionVo,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByObjectOr(RegionVo regionVo,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByRid(Integer rid,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByRegion(String region,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByRlevel(Integer rlevel,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByPid(Integer pid,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByIsdel(Integer isdel,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByCuid(Integer cuid,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByMuid(Integer muid,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByCtime(Date ctime,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByMtime(String mtime,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByNew0(RegionVo regionVo,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByNew1(RegionVo regionVo,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByNew2(RegionVo regionVo,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByNew3(RegionVo regionVo,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByNew4(RegionVo regionVo,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByNew5(RegionVo regionVo,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByNew6(RegionVo regionVo,RegionQuery regionQuery)  throws Exception;
  public List<RegionVo> queryByNew7(RegionVo regionVo,RegionQuery regionQuery)  throws Exception;

}
