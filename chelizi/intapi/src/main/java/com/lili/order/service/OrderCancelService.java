package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.OrderCancelQuery;
import com.lili.order.vo.OrderCancelVo;

public interface OrderCancelService {

  public void addOrderCancel(OrderCancelVo orderCancelVo)  throws Exception;
  public void addOrderCancelList(List<OrderCancelVo> orderCancelVoList)  throws Exception;
  public void delOrderCancelById(String orderId)  throws Exception;
  public void delOrderCancelByIds(List<String> ids)  throws Exception;
  public void delOrderCancelByObj(OrderCancelVo orderCancelVo)  throws Exception;
  public void saveOrderCancel(OrderCancelVo orderCancelVo)  throws Exception;
  public void saveOrderCancelList(List<OrderCancelVo> orderCancelVoList)  throws Exception;
  public int updateByOrderId(OrderCancelVo orderCancelVo,String orderId)  throws Exception;
  public int updateByUcancel(OrderCancelVo orderCancelVo,Integer ucancel)  throws Exception;
  public int updateByUduty(OrderCancelVo orderCancelVo,Integer uduty)  throws Exception;
  public int updateByRetype(OrderCancelVo orderCancelVo,Integer retype)  throws Exception;
  public int updateByReseaon(OrderCancelVo orderCancelVo,String reseaon)  throws Exception;
  public int updateByCltime(OrderCancelVo orderCancelVo,Date cltime)  throws Exception;
  public int updateByPstate(OrderCancelVo orderCancelVo,Integer pstate)  throws Exception;
  public int updateNotNullByObject(OrderCancelVo orderCancelVo,OrderCancelVo search)  throws Exception;
  public int updateAllByObject(OrderCancelVo orderCancelVo,OrderCancelVo search)  throws Exception;
  public OrderCancelVo queryOrderCancelById(String orderId,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryOrderCancelByIds(List<String> ids,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByObjectAnd(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByObjectOr(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByOrderId(String orderId,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByUcancel(Integer ucancel,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByUduty(Integer uduty,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByRetype(Integer retype,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByReseaon(String reseaon,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByCltime(Date cltime,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByPstate(Integer pstate,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByNew0(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByNew1(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByNew2(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByNew3(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByNew4(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByNew5(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByNew6(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery)  throws Exception;
  public List<OrderCancelVo> queryByNew7(OrderCancelVo orderCancelVo,OrderCancelQuery orderCancelQuery)  throws Exception;

}
