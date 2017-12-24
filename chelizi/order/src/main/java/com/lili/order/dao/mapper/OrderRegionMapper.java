package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.RegionPo;

public interface OrderRegionMapper{

  public void addRegion(RegionPo regionPo);
  public void addRegionList(List<RegionPo> regionPoList);
  public void delRegionById(Integer rid);
  public void delRegionByIds(List<Integer> ids);
  public void delRegionByObj(RegionPo regionPo);
  public void saveRegion(RegionPo regionPo);
  public void saveRegionList(List<RegionPo> regionPoList);
  public int updateByRid(RegionPo regionPo,Integer rid);
  public int updateByRegion(RegionPo regionPo,String region);
  public int updateByRlevel(RegionPo regionPo,Integer rlevel);
  public int updateByPid(RegionPo regionPo,Integer pid);
  public int updateByIsdel(RegionPo regionPo,Integer isdel);
  public int updateByCuid(RegionPo regionPo,Integer cuid);
  public int updateByMuid(RegionPo regionPo,Integer muid);
  public int updateByCtime(RegionPo regionPo,Date ctime);
  public int updateByMtime(RegionPo regionPo,String mtime);
  public int updateNotNullByObject(RegionPo regionPo,RegionPo search);
  public int updateAllByObject(RegionPo regionPo,RegionPo search);
  public RegionPo queryRegionById(Integer rid,String postSql,String queryFields);
  public List<RegionPo> queryRegionByIds(List<Integer> ids,String postSql,String queryFields);
  public List<RegionPo> queryByObjectAnd(RegionPo regionPo,String postSql,String queryFields);
  public List<RegionPo> queryByObjectOr(RegionPo regionPo,String postSql,String queryFields);
  public List<RegionPo> queryByRid(Integer rid,String postSql,String queryFields);
  public List<RegionPo> queryByRegion(String region,String postSql,String queryFields);
  public List<RegionPo> queryByRlevel(Integer rlevel,String postSql,String queryFields);
  public List<RegionPo> queryByPid(Integer pid,String postSql,String queryFields);
  public List<RegionPo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<RegionPo> queryByCuid(Integer cuid,String postSql,String queryFields);
  public List<RegionPo> queryByMuid(Integer muid,String postSql,String queryFields);
  public List<RegionPo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<RegionPo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<RegionPo> queryByNew0(RegionPo regionPo,String postSql,String queryFields);
  public List<RegionPo> queryByNew1(RegionPo regionPo,String postSql,String queryFields);
  public List<RegionPo> queryByNew2(RegionPo regionPo,String postSql,String queryFields);
  public List<RegionPo> queryByNew3(RegionPo regionPo,String postSql,String queryFields);
  public List<RegionPo> queryByNew4(RegionPo regionPo,String postSql,String queryFields);
  public List<RegionPo> queryByNew5(RegionPo regionPo,String postSql,String queryFields);
  public List<RegionPo> queryByNew6(RegionPo regionPo,String postSql,String queryFields);
  public List<RegionPo> queryByNew7(RegionPo regionPo,String postSql,String queryFields);

}
