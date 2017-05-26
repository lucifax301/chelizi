package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.OrderComplainPo;

public interface OrderComplainMapper{

  public void addOrderComplain(OrderComplainPo orderComplainPo);
  public void addOrderComplainList(List<OrderComplainPo> orderComplainPoList);
  public void delOrderComplainById(Integer cid);
  public void delOrderComplainByIds(List<Integer> ids);
  public void delOrderComplainByObj(OrderComplainPo orderComplainPo);
  public void saveOrderComplain(OrderComplainPo orderComplainPo);
  public void saveOrderComplainList(List<OrderComplainPo> orderComplainPoList);
  public int updateByCid(OrderComplainPo orderComplainPo,Integer cid);
  public int updateByOrderId(OrderComplainPo orderComplainPo,String orderId);
  public int updateByUid(OrderComplainPo orderComplainPo,Long uid);
  public int updateByUtype(OrderComplainPo orderComplainPo,Integer utype);
  public int updateByComplain(OrderComplainPo orderComplainPo,String complain);
  public int updateByComplainTime(OrderComplainPo orderComplainPo,Date complainTime);
  public int updateByMobile(OrderComplainPo orderComplainPo,String mobile);
  public int updateByPic(OrderComplainPo orderComplainPo,String pic);
  public int updateByStaffId(OrderComplainPo orderComplainPo,Integer staffId);
  public int updateByResult(OrderComplainPo orderComplainPo,String result);
  public int updateByCstate(OrderComplainPo orderComplainPo,Integer cstate);
  public int updateNotNullByObject(OrderComplainPo orderComplainPo,OrderComplainPo search);
  public int updateAllByObject(OrderComplainPo orderComplainPo,OrderComplainPo search);
  public OrderComplainPo queryOrderComplainById(Integer cid,String postSql,String queryFields);
  public List<OrderComplainPo> queryOrderComplainByIds(List<Integer> ids,String postSql,String queryFields);
  public List<OrderComplainPo> queryByObjectAnd(OrderComplainPo orderComplainPo,String postSql,String queryFields);
  public List<OrderComplainPo> queryByObjectOr(OrderComplainPo orderComplainPo,String postSql,String queryFields);
  public List<OrderComplainPo> queryByCid(Integer cid,String postSql,String queryFields);
  public List<OrderComplainPo> queryByOrderId(String orderId,String postSql,String queryFields);
  public List<OrderComplainPo> queryByUid(Long uid,String postSql,String queryFields);
  public List<OrderComplainPo> queryByUtype(Integer utype,String postSql,String queryFields);
  public List<OrderComplainPo> queryByComplain(String complain,String postSql,String queryFields);
  public List<OrderComplainPo> queryByComplainTime(Date complainTime,String postSql,String queryFields);
  public List<OrderComplainPo> queryByMobile(String mobile,String postSql,String queryFields);
  public List<OrderComplainPo> queryByPic(String pic,String postSql,String queryFields);
  public List<OrderComplainPo> queryByStaffId(Integer staffId,String postSql,String queryFields);
  public List<OrderComplainPo> queryByResult(String result,String postSql,String queryFields);
  public List<OrderComplainPo> queryByCstate(Integer cstate,String postSql,String queryFields);
  public List<OrderComplainPo> queryByNew0(OrderComplainPo orderComplainPo,String postSql,String queryFields);
  public List<OrderComplainPo> queryByNew1(OrderComplainPo orderComplainPo,String postSql,String queryFields);
  public List<OrderComplainPo> queryByNew2(OrderComplainPo orderComplainPo,String postSql,String queryFields);
  public List<OrderComplainPo> queryByNew3(OrderComplainPo orderComplainPo,String postSql,String queryFields);
  public List<OrderComplainPo> queryByNew4(OrderComplainPo orderComplainPo,String postSql,String queryFields);
  public List<OrderComplainPo> queryByNew5(OrderComplainPo orderComplainPo,String postSql,String queryFields);
  public List<OrderComplainPo> queryByNew6(OrderComplainPo orderComplainPo,String postSql,String queryFields);
  public List<OrderComplainPo> queryByNew7(OrderComplainPo orderComplainPo,String postSql,String queryFields);

}
