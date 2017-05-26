package com.lili.student.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.student.dao.po.VipCustomPo;

public interface VipCustomMapper{

  public void addVipCustom(VipCustomPo vipCustomPo);
  public void addVipCustomList(List<VipCustomPo> vipCustomPoList);
  public void delVipCustomById(Long studentId);
  public void delVipCustomByIds(List<Long> ids);
  public void delVipCustomByObj(VipCustomPo vipCustomPo);
  public void saveVipCustom(VipCustomPo vipCustomPo);
  public void saveVipCustomList(List<VipCustomPo> vipCustomPoList);
  public int updateByStudentId(VipCustomPo vipCustomPo,Long studentId);
  public int updateByMobile(VipCustomPo vipCustomPo,String mobile);
  public int updateByCname(VipCustomPo vipCustomPo,String cname);
  public int updateByCoid(VipCustomPo vipCustomPo,Integer coid);
  public int updateByCid(VipCustomPo vipCustomPo,String cid);
  public int updateByRcid(VipCustomPo vipCustomPo,Integer rcid);
  public int updateByRcname(VipCustomPo vipCustomPo,String rcname);
  public int updateByCoupon(VipCustomPo vipCustomPo,String coupon);
  public int updateByCouponLack(VipCustomPo vipCustomPo,String couponLack);
  public int updateByVstate(VipCustomPo vipCustomPo,Integer vstate);
  public int updateByReason(VipCustomPo vipCustomPo,String reason);
  public int updateByIsdel(VipCustomPo vipCustomPo,Integer isdel);
  public int updateByCuid(VipCustomPo vipCustomPo,Long cuid);
  public int updateByMuid(VipCustomPo vipCustomPo,Long muid);
  public int updateByCtime(VipCustomPo vipCustomPo,Date ctime);
  public int updateByMtime(VipCustomPo vipCustomPo,String mtime);
  public int updateNotNullByObject(VipCustomPo vipCustomPo,VipCustomPo search);
  public int updateAllByObject(VipCustomPo vipCustomPo,VipCustomPo search);
  public VipCustomPo queryVipCustomById(Long studentId,String postSql,String queryFields);
  public List<VipCustomPo> queryVipCustomByIds(List<Long> ids,String postSql,String queryFields);
  public List<VipCustomPo> queryByObjectAnd(VipCustomPo vipCustomPo,String postSql,String queryFields);
  public Integer countByObject(VipCustomPo vipCustomPo,String postSql,String queryFields);
  public List<VipCustomPo> queryByObjectOr(VipCustomPo vipCustomPo,String postSql,String queryFields);
  public List<VipCustomPo> queryByStudentId(Long studentId,String postSql,String queryFields);
  public List<VipCustomPo> queryByMobile(String mobile,String postSql,String queryFields);
  public List<VipCustomPo> queryByCname(String cname,String postSql,String queryFields);
  public List<VipCustomPo> queryByCoid(Integer coid,String postSql,String queryFields);
  public List<VipCustomPo> queryByCid(String cid,String postSql,String queryFields);
  public List<VipCustomPo> queryByRcid(Integer rcid,String postSql,String queryFields);
  public List<VipCustomPo> queryByRcname(String rcname,String postSql,String queryFields);
  public List<VipCustomPo> queryByCoupon(String coupon,String postSql,String queryFields);
  public List<VipCustomPo> queryByCouponLack(String couponLack,String postSql,String queryFields);
  public List<VipCustomPo> queryByVstate(Integer vstate,String postSql,String queryFields);
  public List<VipCustomPo> queryByReason(String reason,String postSql,String queryFields);
  public List<VipCustomPo> queryByIsdel(Integer isdel,String postSql,String queryFields);
  public List<VipCustomPo> queryByCuid(Long cuid,String postSql,String queryFields);
  public List<VipCustomPo> queryByMuid(Long muid,String postSql,String queryFields);
  public List<VipCustomPo> queryByCtime(Date ctime,String postSql,String queryFields);
  public List<VipCustomPo> queryByMtime(String mtime,String postSql,String queryFields);
  public List<VipCustomPo> queryByNew0(VipCustomPo vipCustomPo,String postSql,String queryFields);
  public List<VipCustomPo> queryByNew1(VipCustomPo vipCustomPo,String postSql,String queryFields);
  public List<VipCustomPo> queryByNew2(VipCustomPo vipCustomPo,String postSql,String queryFields);
  public List<VipCustomPo> queryByNew3(VipCustomPo vipCustomPo,String postSql,String queryFields);
  public List<VipCustomPo> queryByNew4(VipCustomPo vipCustomPo,String postSql,String queryFields);
  public List<VipCustomPo> queryByNew5(VipCustomPo vipCustomPo,String postSql,String queryFields);
  public List<VipCustomPo> queryByNew6(VipCustomPo vipCustomPo,String postSql,String queryFields);
  public List<VipCustomPo> queryByNew7(VipCustomPo vipCustomPo,String postSql,String queryFields);

}
