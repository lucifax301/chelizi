package com.lili.exam.manager;

import java.util.Date;
import java.util.List;

import com.lili.common.util.Page;
import com.lili.common.vo.ReqResult;
import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.exam.dto.ExamPlacePayOrder;
import com.lili.pay.vo.PayVo;

/**
 * 考场订单管理
 * @author yangpeng
 *
 */
public interface ExamPlaceOrderManager {

	/**
	 * 生成约考场订单
	 * @param userId
	 * @param userType
	 * @param classId	排班id。多个用逗号,隔开
	 * @param drtype	准驾车型。1-C1;2-C2
	 * @return			如果是多个排班一起提交，则返回对应的多个订单号
	 */
	ReqResult addExamPlaceOrder(String userId, String userType, String classId,
			String drtype,String carNo);
	
	/**
	 * 提供车模式
	 * @param userId
	 * @param userType
	 * @param classId
	 * @param drtype
	 * @param carNo
	 * @return
	 */
	ReqResult addCarModelExamPlaceOrder(String userId, String userType, String classId,
			String drtype,String placeId);

	/**
	 * 取消排班订单
	 * @param userId
	 * @param userType
	 * @param orderId	订单id。多个用逗号,隔开
	 * @return
	 */
	ReqResult cancelExamPlaceClassOrder(String userId, String userType,
			String orderId);
	
	/**
	 * 根据订单id获取订单
	 * @param orderId 订单id。多个用逗号,隔开
	 * @return
	 */
	List<ExamPlaceOrder> getExamPlaceOrder(String orderId);

	/**
	 * 标记支付状态
	 * @param payVo
	 * @param stageStateSucc
	 * @param endTime 
	 */
	void postPayState(PayVo payVo, byte stageStateSucc, Date endTime);
	
	void postPayState(String orderId, String payWay,
			byte stageStateSucc, Date endTime);
	
	/**
	 * 更改订单状态
	 * @param orderId	订单id
	 * @param state		订单状态
	 */
	void postOrderState(String orderId, byte state, String remark);
	
	/**
	 * 分页获取排班订单
	 * @param pdate			日期。格式：2016-09-12。查询全部则传空。查询时间段则用逗号分隔
	 * @param state			订单状态。0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'。查询全部则传空。
	 * @param placeId		考场id。查询全部则传空。
	 * @param coachName		模糊搜索：教练姓名
	 * @param schoolName	模糊搜索：驾校名称
	 * @param carNo			模糊搜索：车牌号
	 * @param coachMobile	模糊搜索：教练手机号
	 * @param orderId		模糊搜索：订单号
	 * @param pageNo		分页：第几页
	 * @param pageSize		分页：每页数量
	 * @return
	 */
	Page<ExamPlaceOrder> getOrders(String pdate,String pstart, String state, String drtype, String placeId, String classId,String coachName, String schoolName,
			String carNo, String coachMobile, String orderId, String pageNo, String pageSize);

	/**
	 * 根据教练id分页获取我的约考场订单
	 * @param userId
	 * @param userType
	 * @param state
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<ExamPlaceOrder> getMyExamPlaceOrder(String userId, String userType,
			String state, String pageNo, String pageSize);

	/**
	 * 预支付排班订单（如果金额是0，则直接支付成功；否则客户端调用通用支付接口进行支付）
	 * @param userId
	 * @param userType
	 * @param orderId	订单id。多个用逗号,隔开
	 */
	ReqResult payExamPlaceClassOrder(String userId, String userType,
			String orderId);
	
	
	/**
	 * 更新订单
	 * @param record
	 */
	void updateExamPlaceOrder(ExamPlaceOrder record);
	
	/**
	 * 获取考场收益
	 * @param placeId
	 * @return
	 */
	List <Integer> getExamPlaceIncome(Integer placeId);
	
	/**
	 * 校验验证码
	 * @param placeId
	 * @param code
	 * @return
	 */
	ReqResult validCode(Integer placeId,Integer code);
	
	/**
	 * 获取考场订单数
	 * @param placeId
	 * @return
	 */
	List <Integer> getExamPlaceOrder(Integer placeId);
	
	public void postPayState(ExamPlacePayOrder payVo, byte stageStateSucc, Date endTime);
	
	public List<ExamPlacePayOrder> getUnpayOrder();
	
	public void expireOrder(ExamPlacePayOrder order);
	
	public void confirmOrder(ExamPlacePayOrder order);
	
	public Page<ExamPlacePayOrder> getPayOrder(ExamPlacePayOrder p,String pageNo,String pageSize);
}
















