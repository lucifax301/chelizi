package com.lili.student.service;

import java.util.List;
import java.util.Date;
import com.lili.student.vo.VipCustomVo;
import com.lili.student.vo.VipCustomQuery;

public interface VipCustomService {

  public void addVipCustom(VipCustomVo vipCustomvo)  throws Exception;
  public void addVipCustomList(List<VipCustomVo> vipCustomvoList)  throws Exception;
  public void delVipCustomById(Long studentId)  throws Exception;
  public void delVipCustomByIds(List<Long> ids)  throws Exception;
  public void delVipCustomByObj(VipCustomVo vipCustomvo)  throws Exception;
  public void saveVipCustom(VipCustomVo vipCustomvo)  throws Exception;
  public void saveVipCustomList(List<VipCustomVo> vipCustomvoList)  throws Exception;
  public int updateByStudentId(VipCustomVo vipCustomvo,Long studentId)  throws Exception;
  public int updateByMobile(VipCustomVo vipCustomvo,String mobile)  throws Exception;
  public int updateByCname(VipCustomVo vipCustomvo,String cname)  throws Exception;
  public int updateByCoid(VipCustomVo vipCustomvo,Integer coid)  throws Exception;
  public int updateByCid(VipCustomVo vipCustomvo,String cid)  throws Exception;
  public int updateByRcid(VipCustomVo vipCustomvo,Integer rcid)  throws Exception;
  public int updateByRcname(VipCustomVo vipCustomvo,String rcname)  throws Exception;
  public int updateByCoupon(VipCustomVo vipCustomvo,String coupon)  throws Exception;
  public int updateByCouponLack(VipCustomVo vipCustomvo,String couponLack)  throws Exception;
  public int updateByVstate(VipCustomVo vipCustomvo,Integer vstate)  throws Exception;
  public int updateByReason(VipCustomVo vipCustomvo,String reason)  throws Exception;
  public int updateByIsdel(VipCustomVo vipCustomvo,Integer isdel)  throws Exception;
  public int updateByCuid(VipCustomVo vipCustomvo,Long cuid)  throws Exception;
  public int updateByMuid(VipCustomVo vipCustomvo,Long muid)  throws Exception;
  public int updateByCtime(VipCustomVo vipCustomvo,Date ctime)  throws Exception;
  public int updateByMtime(VipCustomVo vipCustomvo,String mtime)  throws Exception;
  public int updateNotNullByObject(VipCustomVo vipCustomvo,VipCustomVo search)  throws Exception;
  public int updateAllByObject(VipCustomVo vipCustomvo,VipCustomVo search)  throws Exception;
  public VipCustomVo queryVipCustomById(Long studentId)  throws Exception;
  public List<VipCustomVo> queryVipCustomByIds(List<Long> ids,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByObjectAnd(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;
  public Integer countByObject(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByObjectOr(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByStudentId(Long studentId,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByMobile(String mobile,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByCname(String cname,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByCoid(Integer coid,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByCid(String cid,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByRcid(Integer rcid,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByRcname(String rcname,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByCoupon(String coupon,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByCouponLack(String couponLack,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByVstate(Integer vstate,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByReason(String reason,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByIsdel(Integer isdel,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByCuid(Long cuid,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByMuid(Long muid,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByCtime(Date ctime,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByMtime(String mtime,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByNew0(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByNew1(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByNew2(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByNew3(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByNew4(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByNew5(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByNew6(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;
  public List<VipCustomVo> queryByNew7(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery)  throws Exception;

}
