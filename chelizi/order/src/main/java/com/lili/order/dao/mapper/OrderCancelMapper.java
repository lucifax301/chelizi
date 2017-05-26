package com.lili.order.dao.mapper;

import java.util.List;
import java.util.Date;
import com.lili.order.dao.po.OrderCancelPo;

public interface OrderCancelMapper{

  public void addOrderCancel(OrderCancelPo orderCancelPo);
  public void addOrderCancelList(List<OrderCancelPo> orderCancelPoList);
  public void delOrderCancelById(String orderId);
  public void delOrderCancelByIds(List<String> ids);
  public void delOrderCancelByObj(OrderCancelPo orderCancelPo);
  public void saveOrderCancel(OrderCancelPo orderCancelPo);
  public void saveOrderCancelList(List<OrderCancelPo> orderCancelPoList);
  public int updateByOrderId(OrderCancelPo orderCancelPo,String orderId);
  public int updateByUcancel(OrderCancelPo orderCancelPo,Integer ucancel);
  public int updateByUduty(OrderCancelPo orderCancelPo,Integer uduty);
  public int updateByRetype(OrderCancelPo orderCancelPo,Integer retype);
  public int updateByReseaon(OrderCancelPo orderCancelPo,String reseaon);
  public int updateByCltime(OrderCancelPo orderCancelPo,Date cltime);
  public int updateByPstate(OrderCancelPo orderCancelPo,Integer pstate);
  public int updateNotNullByObject(OrderCancelPo orderCancelPo,OrderCancelPo search);
  public int updateAllByObject(OrderCancelPo orderCancelPo,OrderCancelPo search);
  public OrderCancelPo queryOrderCancelById(String orderId,String postSql,String queryFields);
  public List<OrderCancelPo> queryOrderCancelByIds(List<String> ids,String postSql,String queryFields);
  public List<OrderCancelPo> queryByObjectAnd(OrderCancelPo orderCancelPo,String postSql,String queryFields);
  public List<OrderCancelPo> queryByObjectOr(OrderCancelPo orderCancelPo,String postSql,String queryFields);
  public List<OrderCancelPo> queryByOrderId(String orderId,String postSql,String queryFields);
  public List<OrderCancelPo> queryByUcancel(Integer ucancel,String postSql,String queryFields);
  public List<OrderCancelPo> queryByUduty(Integer uduty,String postSql,String queryFields);
  public List<OrderCancelPo> queryByRetype(Integer retype,String postSql,String queryFields);
  public List<OrderCancelPo> queryByReseaon(String reseaon,String postSql,String queryFields);
  public List<OrderCancelPo> queryByCltime(Date cltime,String postSql,String queryFields);
  public List<OrderCancelPo> queryByPstate(Integer pstate,String postSql,String queryFields);
  public List<OrderCancelPo> queryByNew0(OrderCancelPo orderCancelPo,String postSql,String queryFields);
  public List<OrderCancelPo> queryByNew1(OrderCancelPo orderCancelPo,String postSql,String queryFields);
  public List<OrderCancelPo> queryByNew2(OrderCancelPo orderCancelPo,String postSql,String queryFields);
  public List<OrderCancelPo> queryByNew3(OrderCancelPo orderCancelPo,String postSql,String queryFields);
  public List<OrderCancelPo> queryByNew4(OrderCancelPo orderCancelPo,String postSql,String queryFields);
  public List<OrderCancelPo> queryByNew5(OrderCancelPo orderCancelPo,String postSql,String queryFields);
  public List<OrderCancelPo> queryByNew6(OrderCancelPo orderCancelPo,String postSql,String queryFields);
  public List<OrderCancelPo> queryByNew7(OrderCancelPo orderCancelPo,String postSql,String queryFields);

}
