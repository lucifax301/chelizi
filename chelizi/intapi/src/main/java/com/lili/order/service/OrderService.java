package com.lili.order.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lili.common.vo.ReqResult;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.vo.OrderExtQuery;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.order.vo.WaitOrderVo;

public interface OrderService {

	public void addOrder(OrderVo orderVo) throws Exception;

	public void addOrderList(List<OrderVo> orderVoList) throws Exception;

	public void delOrderById(String orderId) throws Exception;

	public void delOrderByIds(List<String> ids) throws Exception;

	public void delOrderByObj(OrderVo orderVo) throws Exception;

	public void saveOrder(OrderVo orderVo) throws Exception;

	public void saveOrderList(List<OrderVo> orderVoList) throws Exception;

	public int updateByOrderId(OrderVo orderVo, String orderId)
			throws Exception;

	public int updateByCoachId(OrderVo orderVo, Long coachId) throws Exception;

	public int updateByCourseId(OrderVo orderVo, String courseId)
			throws Exception;

	public int updateByPrice(OrderVo orderVo, Integer price) throws Exception;

	public int updateByLearnAddr(OrderVo orderVo, String learnAddr)
			throws Exception;

	public int updateByStudentId(OrderVo orderVo, Long studentId)
			throws Exception;

	public int updateByDltype(OrderVo orderVo, Integer dltype) throws Exception;

	public int updateByLge(OrderVo orderVo, Double lge) throws Exception;

	public int updateByLae(OrderVo orderVo, Double lae) throws Exception;

	public int updateByStuAddr(OrderVo orderVo, String stuAddr)
			throws Exception;

	public int updateByPstart(OrderVo orderVo, Date pstart) throws Exception;

	public int updateByPend(OrderVo orderVo, Date pend) throws Exception;

	public int updateByClzNum(OrderVo orderVo, Integer clzNum) throws Exception;

	public int updateByOrderState(OrderVo orderVo, Integer orderState)
			throws Exception;

	public int updateByRstart(OrderVo orderVo, Date rstart) throws Exception;

	public int updateByRend(OrderVo orderVo, Date rend) throws Exception;

	public int updateByCstart(OrderVo orderVo, Date cstart) throws Exception;

	public int updateByCend(OrderVo orderVo, Date cend) throws Exception;

	public int updateByPayState(OrderVo orderVo, Integer payState)
			throws Exception;

	public int updateByNeedTrans(OrderVo orderVo, Integer needTrans)
			throws Exception;

	public int updateByTransState(OrderVo orderVo, Integer transState)
			throws Exception;

	public int updateByPayId(OrderVo orderVo, Integer payId) throws Exception;

	public int updateByGtime(OrderVo orderVo, Date gtime) throws Exception;

	public int updateByAtime(OrderVo orderVo, Date atime) throws Exception;

	public int updateByOtype(OrderVo orderVo, Integer otype) throws Exception;

	public int updateByCoorder(OrderVo orderVo, String coorder)
			throws Exception;
  public int updateByCcid(OrderVo orderVo,Integer ccid)  throws Exception;
	public int updateNotNullByObject(OrderVo orderVo, OrderVo search)
			throws Exception;

	public int updateAllByObject(OrderVo orderVo, OrderVo search)
			throws Exception;

	public OrderVo queryOrderById(String orderId, OrderQuery orderQuery)
			throws Exception;

  public List<OrderVo> queryOrderByIds(List<String> ids,OrderQuery orderQuery)  throws Exception;

	public List<OrderVo> queryByObjectAnd(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;
	
	public List<OrderVo> queryByObjectAndNew(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByObjectOr(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByOrderId(String orderId, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByCoachId(Long coachId, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByCourseId(String courseId, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByPrice(Integer price, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByLearnAddr(String learnAddr,
			OrderQuery orderQuery) throws Exception;

	public List<OrderVo> queryByStudentId(Long studentId, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByDltype(Integer dltype, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByLge(Double lge, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByLae(Double lae, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByStuAddr(String stuAddr, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByPstart(Date pstart, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByPend(Date pend, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByClzNum(Integer clzNum, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByOrderState(Integer orderState,
			OrderQuery orderQuery) throws Exception;

	public List<OrderVo> queryByRstart(Date rstart, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByRend(Date rend, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByCstart(Date cstart, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByCend(Date cend, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByPayState(Integer payState, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByNeedTrans(Integer needTrans,
			OrderQuery orderQuery) throws Exception;

	public List<OrderVo> queryByTransState(Integer transState,
			OrderQuery orderQuery) throws Exception;

	public List<OrderVo> queryByPayId(Integer payId, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByGtime(Date gtime, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByAtime(Date atime, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByOtype(Integer otype, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByCoorder(String coorder, OrderQuery orderQuery)
			throws Exception;
  public List<OrderVo> queryByCcid(Integer ccid,OrderQuery orderQuery)  throws Exception;
	public List<OrderVo> queryByPstart(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByRend(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByNew2(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByNew3(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByNew4(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByNew5(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByNew6(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;

	public List<OrderVo> queryByNew7(OrderVo orderVo, OrderQuery orderQuery)
			throws Exception;

	public int getCoachPrice(Date pstart, Date pend, int cityId, String courseId,
			int colid, int calid) throws Exception;

	public String cancelOrder(String orderId, Integer retype, String reseaon,
			Integer ucancel, String tokenId,int type) throws Exception;
	public String bookOrder(OrderVo orderVo) throws Exception;
	/**
	 * 创建由学员自主排班生成的预约订单
	 * @param orderVo
	 * @return
	 * @throws Exception
	 */
	public ReqResult bookStudentOrder(OrderVo orderVo) throws Exception;
	public long getTimeleft(String orderId) throws Exception;
	public ReqResult missClass(String orderId);
	public List<OrderVo> queryByObject(OrderVo orderVo, OrderExtQuery orderQuery) throws Exception;
	public WaitOrderVo getCoachWait(long coachId,WaitOrderVo normalOrder) throws Exception;
	public WaitOrderVo getStudentWait(long studentId,WaitOrderVo normalOrder) throws Exception;
	public OrderVo getStuLastBooked(long studentId) throws Exception;
	public String handlePlaceNow(OrderVo orderVo) throws Exception;
	public String handleAccept(OrderVo orderVo) throws Exception;
	public OrderVo getCoachLastBooked(long coachId) throws Exception;
	public String stuCommentCoach(String coachId, String studentId,String orderId, String score, String tagId, String oneWord, String anonymity) throws Exception;
	public String coachCommentStu(String coachId, String studentId,String orderId, Map<Integer, Integer> scores, String oneWord) throws Exception;

	/**
	 * 挂起订单
	 * @param orderId
	 * @return
     */
	ReqResult handUpOrder(String orderId);
	/**
	 * cms取消订单
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String cmsCancelOrder(String orderId,long userId) throws Exception;
	
	public List<OrderVo> searchBmClass(int studentId, Integer coachId)
			throws Exception;
	
	ReqResult saveInsuranceOrder(InsuranceOrder insuranceOrder);
	
	ReqResult searchInsuranceById(String userId);
	
	InsuranceOrder searchInsuranceByOrderId(String insuranceId);
	
    public void updatePayState(String insuranceId,byte payState,String payWay);
	
}
