package com.lili.order.service;

import java.util.List;
import java.util.Date;
import com.lili.order.vo.OrderComplainVo;
import com.lili.order.vo.OrderComplainQuery;

public interface OrderComplainService {

  public void addOrderComplain(OrderComplainVo orderComplainVo)  throws Exception;
  public void addOrderComplainList(List<OrderComplainVo> orderComplainVoList)  throws Exception;
  public void delOrderComplainById(Integer cid)  throws Exception;
  public void delOrderComplainByIds(List<Integer> ids)  throws Exception;
  public void delOrderComplainByObj(OrderComplainVo orderComplainVo)  throws Exception;
  public void saveOrderComplain(OrderComplainVo orderComplainVo)  throws Exception;
  public void saveOrderComplainList(List<OrderComplainVo> orderComplainVoList)  throws Exception;
  public int updateByCid(OrderComplainVo orderComplainVo,Integer cid)  throws Exception;
  public int updateByOrderId(OrderComplainVo orderComplainVo,String orderId)  throws Exception;
  public int updateByUid(OrderComplainVo orderComplainVo,Long uid)  throws Exception;
  public int updateByUtype(OrderComplainVo orderComplainVo,Integer utype)  throws Exception;
  public int updateByComplain(OrderComplainVo orderComplainVo,String complain)  throws Exception;
  public int updateByComplainTime(OrderComplainVo orderComplainVo,Date complainTime)  throws Exception;
  public int updateByMobile(OrderComplainVo orderComplainVo,String mobile)  throws Exception;
  public int updateByPic(OrderComplainVo orderComplainVo,String pic)  throws Exception;
  public int updateByStaffId(OrderComplainVo orderComplainVo,Integer staffId)  throws Exception;
  public int updateByResult(OrderComplainVo orderComplainVo,String result)  throws Exception;
  public int updateByCstate(OrderComplainVo orderComplainVo,Integer cstate)  throws Exception;
  public int updateNotNullByObject(OrderComplainVo orderComplainVo,OrderComplainVo search)  throws Exception;
  public int updateAllByObject(OrderComplainVo orderComplainVo,OrderComplainVo search)  throws Exception;
  public OrderComplainVo queryOrderComplainById(Integer cid,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryOrderComplainByIds(List<Integer> ids,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByObjectAnd(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByObjectOr(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByCid(Integer cid,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByOrderId(String orderId,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByUid(Long uid,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByUtype(Integer utype,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByComplain(String complain,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByComplainTime(Date complainTime,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByMobile(String mobile,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByPic(String pic,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByStaffId(Integer staffId,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByResult(String result,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByCstate(Integer cstate,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByNew0(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByNew1(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByNew2(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByNew3(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByNew4(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByNew5(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByNew6(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery)  throws Exception;
  public List<OrderComplainVo> queryByNew7(OrderComplainVo orderComplainVo,OrderComplainQuery orderComplainQuery)  throws Exception;

}
