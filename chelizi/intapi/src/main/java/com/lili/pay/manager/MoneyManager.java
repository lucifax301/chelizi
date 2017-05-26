package com.lili.pay.manager;

import java.util.Date;
import java.util.List;

import com.lili.common.constant.MoneyChange;
import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.order.vo.OrderVo;
import com.lili.pay.dto.BrokerageCourse;
import com.lili.pay.dto.BrokerageEnroll;
import com.lili.pay.vo.PayVo;
import com.lili.school.dto.EnrollOrder;

public interface MoneyManager {

	/**
	 * 财务用户类型
	 * 
	 * @author yangpeng
	 *
	 */
	enum UserType {
		COACH((byte) 1, "[教练]"), STUDENT((byte) 2, "[学员]"), SCHOOL((byte) 3,
				"[驾校]"), lili((byte) 4, "[车厘子]"), WXCOACHSTU((byte) 5, "[微信学员]");

		private byte value;
		private String desc;

		UserType(byte value, String desc) {
			this.value = value;
			this.setDesc(desc);
		}

		public byte getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

	}

	/**
	 * 添加资金流水日志记录 </br> 对应数据表 t_u_money
	 * 
	 * @param userId
	 *            用户id
	 * @param userType
	 *            用户类型
	 * @param payWay
	 *            支付方式
	 * @param leftMoney
	 *            账户余额
	 * @param price
	 *            变更金额
	 * @param moneyChange
	 *            交易类型
	 * @param isEarning
	 *            是否收入：true-收入；false-支出；
	 * @param isBalance
	 *            是否属于余额：true-余额；false-非余额；
	 * @param remark
	 *            备注
	 * @param orderId
	 *            订单id
	 * @param tranObject
	 *            交易对象
	 */
	void addMoneyLog(Long userId, UserType userType, String payWay,
			int leftMoney, Integer price, MoneyChange moneyChange,
			boolean isEarning, boolean isBalance, String remark,
			String orderId, String tranObject);

	/**
	 * 添加资金流水日志记录（方式二） </br> 对应数据表 t_u_money
	 * 
	 * @param userId
	 *            用户id
	 * @param userType
	 *            用户类型
	 * @param payWay
	 *            支付方式
	 * @param leftMoney
	 *            账户余额
	 * @param price
	 *            变更金额
	 * @param moneyChange
	 *            交易类型
	 * @param isEarning
	 *            是否收入：0-支出；1-收入；2-代收款；（例如待结算的报名订单金额属于代收款。之所以需要代收款这个概念，是为了财务避税）
	 * @param isBalance
	 *            是否属于余额：0-非余额；1-余额；
	 * @param remark
	 *            备注
	 * @param orderId
	 *            订单id
	 * @param tranObject
	 *            交易对象
	 */
	void addMoneyLog(Long userId, UserType userType, String payWay,
			int leftMoney, Integer price, MoneyChange moneyChange,
			byte isEarning, byte isBalance, String remark, String orderId,
			String tranObject);

	/**
	 * 添加教练业绩流水日志 对应数据表 t_u_performance
	 * 
	 * @param orderVo
	 *            订单实体
	 */
	void addPerformance(OrderVo orderVo);

	/**
	 * 添加资金--驾校余额账户</br> 对应数据表t_s_schaccount （驾校余额账户不能为负数，避免超额提现等问题）
	 * 
	 * @param schoolId
	 *            驾校id。（schoolId=0时为喱喱余额账户；schoolId!=0时为普通驾校余额账户）
	 * @param money
	 *            变更的金额，可为正负，单位分
	 * @return 当前账户余额；余额不足时返回Integer.MIN_VALUE
	 */
	int addSchoolMoney(int schoolId, int money);

	/**
	 * 添加资金--学员余额账户 </br> 对应数据表t_u_stuaccount （学员余额账户不能为负数，避免超额提现等问题）
	 * 
	 * @param studentId
	 *            用户id
	 * @param money
	 *            变更的金额，可为正负，单位分
	 * @return 当前账户余额；余额不足时返回Integer.MIN_VALUE
	 */
	int addStudentMoney(long studentId, int money);

	/**
	 * 添加资金--教练余额账户</br> 对应数据表t_u_coachaccount （教练余额账户不能为负数，避免超额提现等问题）
	 * 
	 * @param coachId
	 *            用户id
	 * @param money
	 *            变更的金额，可为正负，单位分
	 * @return 当前账户余额；余额不足时返回Integer.MIN_VALUE
	 */
	int addCoachMoney(long coachId, int money);

	/**
	 * 添加资金和业绩--教练余额账户</br> 对应数据表t_u_coachaccount （教练余额账户不能为负数，避免超额提现等问题）
	 * 
	 * @param coachId
	 *            用户id
	 * @param performance
	 *            变更的业绩
	 * @param money
	 *            变更的金额，可为正负，单位分
	 * @return 当前账户余额 减账户时，只能见到零，而不会出现负数
	 */
	int addPerformanceAndMoney(Long coachId, int performance, int money);

	// ******************************************************************************

	/**
	 * 获取课时订单佣金模板
	 * 
	 * @param regionId
	 *            区域id
	 * @param courseTmpId
	 *            课程id
	 * @param dlType
	 *            准驾类型
	 * @param time
	 *            日期
	 * @return
	 */
	BrokerageCourse getBrokerageCourse(Integer regionId, Integer courseTmpId,
			Byte dlType, Date time);

	/**
	 * 获取报名订单佣金模版
	 * 
	 * @param regionid
	 *            区域id
	 * @param schoolId
	 *            驾校id
	 * @return
	 */
	BrokerageEnroll getBrokerageEnroll(Integer regionid, Integer schoolId);

	// ******************************************************************************

	/**
	 * 资金结算--学员或教练充值
	 * 
	 * @param payVo
	 */
	void handleChargeMoneyFlow(PayVo payVo);

	/**
	 * 资金结算--学员支付课时订单，并自动结算给驾校
	 * 
	 * @param orderVo
	 *            订单实体
	 * @param oldPayState
	 *            支付状态
	 * @param payType
	 *            支付类型
	 */
	void handleOrderMoneyFlow(OrderVo orderVo, int oldPayState, String payType);

	/**
	 * 资金结算--自动支付课时费，并自动结算给驾校</br> 全额抵扣课时费订单，虽然没有支付流程，但也需要增加结算记录
	 * 
	 * @param orderVo
	 */
	void handleOrderHasPayedMoneyFlow(OrderVo orderVo);

	/**
	 * 资金结算--客服挂起课时订单，并自动结算给驾校
	 * 
	 * @param orderVo
	 *            订单实体
	 */
	void handleOrderHandUpMoneyFlow(OrderVo orderVo);

	/**
	 * 资金结算--课时费退款
	 * 
	 * @param orderVo
	 *            订单实体
	 * @param refundMoney
	 *            退款金额
	 */
	void handleOrderRefundMoneyFlow(OrderVo orderVo, int refundMoney);

	/**
	 * 资金结算--喱喱给教练发奖金
	 * 
	 * @param coachId
	 * @param performance
	 *            业绩
	 * @param money
	 *            奖金额度
	 * @return
	 */
	int handlePayCoachBonus(Long coachId, int performance, Integer money);

	// ******************************************************************************

	/**
	 * 资金结算--学员支付报名订单
	 * 
	 * @param payVo
	 *            支付对象
	 */
	void handleSignupMoneyFlow(PayVo payVo);
	
	/**
	 * 资金结算--学员驾校点评支付报名订单
	 * 
	 * @param payVo
	 *            支付对象
	 */
	void handleWxSignupMoneyFlow(PayVo payVo);
	/**
	 * 资金结算--
	 * 
	 * @param payVo
	 *            支付对象
	 */
	void handleInsuranceMoneyFlow(PayVo payVo);

	/**
	 * 资金结算--结算报名费给驾校</br> eg.驾校有收入1998，有费用150.8；喱喱减余额1998，加收入150.8.</br>
	 * 特别注意：这里喱喱减去的是余额，不算费用 ！！财务税费角度考虑
	 * 
	 * @param enrollOrder
	 *            报名订单实体
	 */
	void handleCheckoutEnrollMoneyFlow(EnrollOrder enrollOrder);

	// ******************************************************************************

	/**
	 * 资金结算--记录教练支付约考场费用的流水
	 * 
	 * @param payVo
	 */
	void handleExamPlaceMoneyFlow(PayVo payVo);

	/**
	 * 资金结算--教练取消约考场订单自动退款
	 * 
	 * @param order
	 *            订单
	 * @param ratio
	 *            退款比例
	 */
	void handleExamPlaceRefund(ExamPlaceOrder order, Double ratio);

	/**
	 * 资金结算--记录教练支付约考场费用的流水
	 * 
	 * @param orders
	 */
	void handleExamPlaceMoneyFlow(List<ExamPlaceOrder> orders);
	
	
	/**
	 *  处理订单完成返金额
	 */
	void handleExamPlaceReturnAward(ExamPlaceOrder order);

	// ******************************************************************************

	/**
	 * 调整学员金额 FIXME 资金可能有问题 财务确认教练余额变更
	 * 
	 * @param payVo
	 * @return
	 */
	String handleAdjustStudent(PayVo payVo);

	/**
	 * 调整教练金额 FIXME 资金可能有问题 财务确认教练余额变更
	 * 
	 * @param payVo
	 * @return
	 */
	String handleAdjustCoach(PayVo payVo);

	/**
	 * 查询记录
	 * 
	 * @param payVo
	 * @return
	 */
	int isExitMoneyRecord(PayVo payVo);

	// ******************************************************************************

	/**
	 * 驾培云调用--驾校充值
	 * @param userId	用户id	驾校id
	 * @param userType	用户类型	驾校
	 * @param price		充值金额	单位分
	 * @param payWay	充值方式
	 * @param orderId	充值订单
	 * @param remark	备注
	 * @return	账户余额
	 */
	int handleSchoolRecharge(String userId, String userType, String price,
			String payWay, String orderId, String remark);

	/**
	 * 驾培云调用--设置约考场限额
	 * @param userId
	 * @param userType
	 * @param price
	 * @return
	 */
	Integer setExamPlaceMax(String userId, String userType, String price);

	/**
	 * 驾培云调用--获取约考场限额
	 * @param userId
	 * @param userType
	 * @return
	 */
	Integer getExamPlaceMax(String userId, String userType);

}



































