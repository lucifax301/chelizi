package com.lili.order.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import com.lili.order.dao.po.OrderPo;
import com.lili.order.vo.OrderExtQuery;

public interface OrderMapper{

  public void addOrder(OrderPo orderPo);
  public void addOrderList(List<OrderPo> orderPoList);
  public void delOrderById(String orderId);
  public void delOrderByIds(List<String> ids);
  public void delOrderByObj(OrderPo orderPo);
  public void saveOrder(OrderPo orderPo);
  public void saveOrderList(List<OrderPo> orderPoList);
  public int updateByOrderId(OrderPo orderPo,String orderId);
  public int updateByCoachId(OrderPo orderPo,Long coachId);
  public int updateByCourseId(OrderPo orderPo,String courseId);
  public int updateByPrice(OrderPo orderPo,Integer price);
  public int updateByLearnAddr(OrderPo orderPo,String learnAddr);
  public int updateByStudentId(OrderPo orderPo,Long studentId);
  public int updateByDltype(OrderPo orderPo,Integer dltype);
  public int updateByLge(OrderPo orderPo,Double lge);
  public int updateByLae(OrderPo orderPo,Double lae);
  public int updateByStuAddr(OrderPo orderPo,String stuAddr);
  public int updateByPstart(OrderPo orderPo,Date pstart);
  public int updateByPend(OrderPo orderPo,Date pend);
  public int updateByClzNum(OrderPo orderPo,Integer clzNum);
  public int updateByOrderState(OrderPo orderPo,Integer orderState);
  public int updateByRstart(OrderPo orderPo,Date rstart);
  public int updateByRend(OrderPo orderPo,Date rend);
  public int updateByCstart(OrderPo orderPo,Date cstart);
  public int updateByCend(OrderPo orderPo,Date cend);
  public int updateByPayState(OrderPo orderPo,Integer payState);
  public int updateByNeedTrans(OrderPo orderPo,Integer needTrans);
  public int updateByTransState(OrderPo orderPo,Integer transState);
  public int updateByPayId(OrderPo orderPo,Integer payId);
  public int updateByGtime(OrderPo orderPo,Date gtime);
  public int updateByAtime(OrderPo orderPo,Date atime);
  public int updateByOtype(OrderPo orderPo,Integer otype);
  public int updateByCoorder(OrderPo orderPo,String coorder);
  public int updateByCcid(OrderPo orderPo,Integer ccid);
  public int updateByUnitPrice(OrderPo orderPo,Integer unitPrice);
  public int updateByTransPrice(OrderPo orderPo,Integer transPrice);
  public int updateByPlaceId(OrderPo orderPo,Integer placeId);
  public int updateByTransName(OrderPo orderPo,String transName);
  public int updateByCarId(OrderPo orderPo,Integer carId);
  public int updateByCarName(OrderPo orderPo,String carName);
  public int updateByCarImg(OrderPo orderPo,String carImg);
  public int updateByCarNo(OrderPo orderPo,String carNo);
  public int updateByInsId(OrderPo orderPo,Integer insId);
  public int updateByInsPrice(OrderPo orderPo,Integer insPrice);
  public int updateByInsName(OrderPo orderPo,String insName);
  public int updateByCoachName(OrderPo orderPo,String coachName);
  public int updateByCoachImg(OrderPo orderPo,String coachImg);
  public int updateByCoachMobile(OrderPo orderPo,String coachMobile);
  public int updateByCoachStar(OrderPo orderPo,Integer coachStar);
  public int updateByStuName(OrderPo orderPo,String stuName);
  public int updateByStuImg(OrderPo orderPo,String stuImg);
  public int updateByStuMobile(OrderPo orderPo,String stuMobile);
  public int updateByCourseName(OrderPo orderPo,String courseName);
  public int updateNotNullByObject(OrderPo orderPo,OrderPo search);
  public int updateAllByObject(OrderPo orderPo,OrderPo search);
  public OrderPo queryOrderById(String orderId,String postSql,String queryFields);
  public List<OrderPo> queryOrderByIds(List<String> ids,String postSql,String queryFields);
  public List<OrderPo> queryByObjectAnd(OrderPo orderPo,String postSql,String queryFields);
  
  public List<OrderPo> queryByObjectAndNew(OrderPo orderPo,String postSql,String queryFields);
  
  
  public List<OrderPo> queryByObjectOr(OrderPo orderPo,String postSql,String queryFields);
  public List<OrderPo> queryByOrderId(String orderId,String postSql,String queryFields);
  public List<OrderPo> queryByCoachId(Long coachId,String postSql,String queryFields);
  public List<OrderPo> queryByCourseId(String courseId,String postSql,String queryFields);
  public List<OrderPo> queryByPrice(Integer price,String postSql,String queryFields);
  public List<OrderPo> queryByLearnAddr(String learnAddr,String postSql,String queryFields);
  public List<OrderPo> queryByStudentId(Long studentId,String postSql,String queryFields);
  public List<OrderPo> queryByDltype(Integer dltype,String postSql,String queryFields);
  public List<OrderPo> queryByLge(Double lge,String postSql,String queryFields);
  public List<OrderPo> queryByLae(Double lae,String postSql,String queryFields);
  public List<OrderPo> queryByStuAddr(String stuAddr,String postSql,String queryFields);
  public List<OrderPo> queryByPstart(Date pstart,String postSql,String queryFields);
  public List<OrderPo> queryByPend(Date pend,String postSql,String queryFields);
  public List<OrderPo> queryByClzNum(Integer clzNum,String postSql,String queryFields);
  public List<OrderPo> queryByOrderState(Integer orderState,String postSql,String queryFields);
  public List<OrderPo> queryByRstart(Date rstart,String postSql,String queryFields);
  public List<OrderPo> queryByRend(Date rend,String postSql,String queryFields);
  public List<OrderPo> queryByCstart(Date cstart,String postSql,String queryFields);
  public List<OrderPo> queryByCend(Date cend,String postSql,String queryFields);
  public List<OrderPo> queryByPayState(Integer payState,String postSql,String queryFields);
  public List<OrderPo> queryByNeedTrans(Integer needTrans,String postSql,String queryFields);
  public List<OrderPo> queryByTransState(Integer transState,String postSql,String queryFields);
  public List<OrderPo> queryByPayId(Integer payId,String postSql,String queryFields);
  public List<OrderPo> queryByGtime(Date gtime,String postSql,String queryFields);
  public List<OrderPo> queryByAtime(Date atime,String postSql,String queryFields);
  public List<OrderPo> queryByOtype(Integer otype,String postSql,String queryFields);
  public List<OrderPo> queryByCoorder(String coorder,String postSql,String queryFields);
  public List<OrderPo> queryByCcid(Integer ccid,String postSql,String queryFields);
  public List<OrderPo> queryByUnitPrice(Integer unitPrice,String postSql,String queryFields);
  public List<OrderPo> queryByTransPrice(Integer transPrice,String postSql,String queryFields);
  public List<OrderPo> queryByPlaceId(Integer placeId,String postSql,String queryFields);
  public List<OrderPo> queryByTransName(String transName,String postSql,String queryFields);
  public List<OrderPo> queryByCarId(Integer carId,String postSql,String queryFields);
  public List<OrderPo> queryByCarName(String carName,String postSql,String queryFields);
  public List<OrderPo> queryByCarImg(String carImg,String postSql,String queryFields);
  public List<OrderPo> queryByCarNo(String carNo,String postSql,String queryFields);
  public List<OrderPo> queryByInsId(Integer insId,String postSql,String queryFields);
  public List<OrderPo> queryByInsPrice(Integer insPrice,String postSql,String queryFields);
  public List<OrderPo> queryByInsName(String insName,String postSql,String queryFields);
  public List<OrderPo> queryByCoachName(String coachName,String postSql,String queryFields);
  public List<OrderPo> queryByCoachImg(String coachImg,String postSql,String queryFields);
  public List<OrderPo> queryByCoachMobile(String coachMobile,String postSql,String queryFields);
  public List<OrderPo> queryByCoachStar(Integer coachStar,String postSql,String queryFields);
  public List<OrderPo> queryByStuName(String stuName,String postSql,String queryFields);
  public List<OrderPo> queryByStuImg(String stuImg,String postSql,String queryFields);
  public List<OrderPo> queryByStuMobile(String stuMobile,String postSql,String queryFields);
  public List<OrderPo> queryByCourseName(String courseName,String postSql,String queryFields);
  public List<OrderPo> queryByNew0(OrderPo orderPo,String postSql,String queryFields);
  public List<OrderPo> queryByNew1(OrderPo orderPo,String postSql,String queryFields);
  public List<OrderPo> queryByNew2(OrderPo orderPo,String postSql,String queryFields);
  public List<OrderPo> queryByNew3(OrderPo orderPo,String postSql,String queryFields);
  public List<OrderPo> queryByNew4(OrderPo orderPo,String postSql,String queryFields);
  public List<OrderPo> queryByNew5(OrderPo orderPo,String postSql,String queryFields);
  public List<OrderPo> queryByNew6(OrderPo orderPo,String postSql,String queryFields);
  public List<OrderPo> queryByNew7(OrderPo orderPo,String postSql,String queryFields);
  public List<OrderPo> queryByObject(OrderPo orderPo,OrderExtQuery orderQuery);
  public List<OrderPo> searchBmClass(@Param("studentId")int studentId, @Param("coachId")Integer coachId);
}
