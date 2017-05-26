/**
 *
 */
package com.lili.pay.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Coach;
import com.lili.coach.dto.CoachAccount;
import com.lili.coach.dto.School;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.MoneyChange;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ResultCode;
import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderVo;
import com.lili.pay.dto.BrokerageCourse;
import com.lili.pay.dto.BrokerageEnroll;
import com.lili.pay.dto.PerformanceDto;
import com.lili.pay.dto.SchoolAccount;
import com.lili.pay.dto.UserMoneyDto;
import com.lili.pay.mapper.dao.BrokerageCourseMapper;
import com.lili.pay.mapper.dao.BrokerageEnrollMapper;
import com.lili.pay.mapper.dao.PerformanceDtoMapper;
import com.lili.pay.mapper.dao.SchoolAccountMapper;
import com.lili.pay.mapper.dao.UserMoneyDtoMapper;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;
import com.lili.school.dto.EnrollOrder;
import com.lili.school.manager.EnrollOrderManager;
import com.lili.student.dto.Student;
import com.lili.student.dto.StudentAccount;
import com.lili.student.manager.StudentManager;

/**
 * @author linbo 让pay服务直接加钱和业绩，不通过dubbo服务，以避免重入的问题
 */
public class MoneyManagerImpl implements MoneyManager {
	private Logger log = Logger.getLogger(MoneyManagerImpl.class);

	@Autowired
	private CoachManager coachManager;

	@Autowired
	private StudentManager studentManager;

	@Autowired
	private PerformanceDtoMapper performanceDtoMapper;
	@Autowired
	private UserMoneyDtoMapper userMoneyDtoMapper;
	@Autowired
	private SchoolAccountMapper schoolAccountMapper;
	@Autowired
	private BrokerageCourseMapper brokerageCourseMapper;
	@Autowired
	private BrokerageEnrollMapper brokerageEnrollMapper;
	@Autowired
	private OrderService orderService;
	@Autowired
	private EnrollOrderManager enrollOrderManager;

	@Override
	public void addMoneyLog(Long userId, UserType userType, String payWay,
			int leftMoney, Integer price, MoneyChange moneyChange,
			boolean isEarning, boolean isBalance, String remark,
			String orderId, String tranObject) {
		UserMoneyDto userMoneyDto = new UserMoneyDto();
		userMoneyDto.setChangevalue(price);
		userMoneyDto.setLeftvalue(leftMoney);
		userMoneyDto.setOperatetime(new Date());
		userMoneyDto.setOperatetype(moneyChange.getType());
		userMoneyDto.setIsEarning((byte) (isEarning ? 1 : 0));
		userMoneyDto.setIsBalance((byte) (isBalance ? 1 : 0));
		userMoneyDto.setPayway(payWay);
		userMoneyDto.setRemark(remark);
		userMoneyDto.setUserid(userId);
		userMoneyDto.setUsertype(userType.getValue());
		userMoneyDto.setOrderId(orderId);
		userMoneyDto.setTranObject(tranObject);

		userMoneyDtoMapper.insert(userMoneyDto);
	}

	@Override
	public void addMoneyLog(Long userId, UserType userType, String payWay,
			int leftMoney, Integer price, MoneyChange moneyChange,
			byte isEarning, byte isBalance, String remark, String orderId,
			String tranObject) {
		UserMoneyDto userMoneyDto = new UserMoneyDto();
		userMoneyDto.setChangevalue(price);
		userMoneyDto.setLeftvalue(leftMoney);
		userMoneyDto.setOperatetime(new Date());
		userMoneyDto.setOperatetype(moneyChange.getType());
		userMoneyDto.setIsEarning(isEarning);
		userMoneyDto.setIsBalance(isBalance);
		userMoneyDto.setPayway(payWay);
		userMoneyDto.setRemark(remark);
		userMoneyDto.setUserid(userId);
		userMoneyDto.setUsertype(userType.getValue());
		userMoneyDto.setOrderId(orderId);
		userMoneyDto.setTranObject(tranObject);

		userMoneyDtoMapper.insert(userMoneyDto);
	}

	@Override
	public void addPerformance(OrderVo orderVo) {
		PerformanceDto performanceDto = new PerformanceDto();
		performanceDto.setCoachId(orderVo.getCoachId());
		performanceDto.setCourse(orderVo.getCourseName());
		performanceDto.setDate(new Date());
		performanceDto.setOrderid(orderVo.getOrderId());
		performanceDto.setPerformance(orderVo.getPriceTotal());

		performanceDtoMapper.insert(performanceDto);
	}

	@Override
	public int addSchoolMoney(int schoolId, int money) {
		int lefeMoney = 0;
		SchoolAccount schoolAccount = schoolAccountMapper
				.selectByPrimaryKey(schoolId);
		if (null == schoolAccount) {
			if (money >= 0) {
				lefeMoney = money;

				schoolAccount = new SchoolAccount();
				schoolAccount.setPasswd("");
				schoolAccount.setMobile("");
				schoolAccount.setMoney(money);
				schoolAccount.setSchoolId(schoolId);
				schoolAccountMapper.insert(schoolAccount);
			} else {
				if (schoolId != 0) {
					return Integer.MIN_VALUE;
				} else { // 20160727 喱喱账户金额可以为负数
					schoolAccount = new SchoolAccount();
					schoolAccount.setPasswd("");
					schoolAccount.setMobile("");
					schoolAccount.setMoney(money);
					schoolAccount.setSchoolId(schoolId);
					schoolAccountMapper.insert(schoolAccount);
				}
			}
		} else {
			int curMoney = schoolAccount.getMoney() + money;
			if (curMoney >= 0) {
				lefeMoney = curMoney;

				schoolAccount.setMoney(curMoney);
				schoolAccountMapper.updateByPrimaryKey(schoolAccount);
			} else {
				if (schoolId != 0) {
					return Integer.MIN_VALUE;
				} else { // 20160727 喱喱账户金额可以为负数
					lefeMoney = curMoney;
					schoolAccount.setMoney(curMoney);
					schoolAccountMapper.updateByPrimaryKey(schoolAccount);
				}

			}
		}
		return lefeMoney;
	}

	@Override
	public int addStudentMoney(long studentId, int money) {
		int leftMoney;
		StudentAccount studentAccount = userMoneyDtoMapper
				.getStudentMoney(studentId);
		if (studentAccount == null) {
			if (money >= 0) {
				leftMoney = money;

				studentAccount = new StudentAccount();
				studentAccount.setMoney(money);
				studentAccount.setStudentId(studentId);
				userMoneyDtoMapper.insertStudentAccount(studentAccount);
			} else {
				return Integer.MIN_VALUE;
			}
		} else {
			int curMoney = studentAccount.getMoney() + money;
			if (curMoney >= 0) {
				leftMoney = curMoney;

				studentAccount.setMoney(curMoney);
				userMoneyDtoMapper.updateStudentMoney(studentAccount);
			} else {
				return Integer.MIN_VALUE;
			}
		}
		return leftMoney;
	}

	@Override
	public int addCoachMoney(long coachId, int money) {
		int leftMoney;
		CoachAccount coachAccount = userMoneyDtoMapper.getCoachMoney(coachId);
		if (coachAccount == null) {
			if (money >= 0) {
				leftMoney = money;

				coachAccount = new CoachAccount();
				coachAccount.setMoney(money);
				coachAccount.setCoachId(coachId);
				userMoneyDtoMapper.insertCoachAccount(coachAccount);
			} else {
				return Integer.MIN_VALUE;
			}
		} else {
			int curMoney = coachAccount.getMoney() + money;
			if (curMoney >= 0) {
				leftMoney = curMoney;

				coachAccount.setMoney(curMoney);
				userMoneyDtoMapper.updateCoachAccount(coachAccount);
			} else {
				return Integer.MIN_VALUE;
			}
		}
		return leftMoney;
	}

	@Override
	public int addPerformanceAndMoney(Long coachId, int performance, int money) {
		CoachAccount coachAccount = userMoneyDtoMapper.getCoachMoney(coachId);
		if (coachAccount == null) {
			money = money > 0 ? money : 0;
			performance = performance > 0 ? performance : 0;
			coachAccount = new CoachAccount();
			coachAccount.setCoachId(coachId);
			coachAccount.setMoney(money);
			coachAccount.setPerformance(performance);
			coachAccount.setLastPerTime(new Date());
			userMoneyDtoMapper.insertCoachAccount(coachAccount);
			return money;
		} else {
			int newPerformance;
			// 产品需求：教练端只显示本月的业绩，所以这里业绩需要更新到本月
			if (TimeUtil.isSameMonth(coachAccount.getLastPerTime(), new Date())) {
				newPerformance = performance + coachAccount.getPerformance();
			} else {
				newPerformance = performance;
			}
			newPerformance = newPerformance > 0 ? newPerformance : 0;

			int newMoney = money + coachAccount.getMoney();
			newMoney = newMoney > 0 ? newMoney : 0;

			coachAccount.setLastPerTime(new Date());
			coachAccount.setPerformance(newPerformance);
			coachAccount.setMoney(newMoney);
			userMoneyDtoMapper.updateCoachAccount(coachAccount);
			return newMoney;
		}
	}

	@Override
	public BrokerageCourse getBrokerageCourse(Integer regionId,
			Integer courseTmpId, Byte dlType, Date time) {
		// 查询佣金比例
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int dayOfWeek = Calendar.DAY_OF_WEEK - 1; // ERROR! FIXME
		if (dayOfWeek == 0) {
			dayOfWeek = 7;// 0标示为周日,此时应该设置为7,重新表示为周日
		}
		BrokerageCourse brokerage = new BrokerageCourse();
		brokerage.setRegionId(regionId);
		brokerage.setCourseTmpId(courseTmpId);
		brokerage.setDltype(dlType);
		brokerage.setDateRule(String.valueOf(dayOfWeek));
		brokerage = brokerageCourseMapper.selectRightBrokerage(brokerage);

		return brokerage;
	}

	@Override
	public BrokerageEnroll getBrokerageEnroll(Integer regionid, Integer schoolId) {
		BrokerageEnroll brokerage = new BrokerageEnroll();
		brokerage.setRegionId(regionid);
		brokerage.setSchoolId(schoolId);
		brokerage = brokerageEnrollMapper.selectRightBrokerage(brokerage);
		return brokerage;
	}

	// ******************************************************************************

	@Override
	public void handleChargeMoneyFlow(PayVo payVo) {

		int payMoney = payVo.getPayValue();
		int discountMoney = payVo.getDiscountMoney();

		// 充值。注意充值不算收入！！
		if (payVo.getUserType() == OrderConstant.USETYPE.COACH) {
			// 如果是教练
			int leftMoney = addPerformanceAndMoney(payVo.getUserId(), 0,
					payMoney);
			addMoneyLog(payVo.getUserId(), UserType.COACH, payVo.getPayWay(),
					leftMoney, payMoney, MoneyChange.CHARGE,
					ReqConstants.MONEY_FLOW_WAIT,
					ReqConstants.MONEY_ACCOUNT_BALANCE, payVo.getRemark(),
					payVo.getPayOrderId(), UserType.lili.getDesc());

		} else if (payVo.getUserType() == OrderConstant.USETYPE.STUDENT) {
			// 学员充值,添加记录
			int leftMoney = addStudentMoney(payVo.getUserId(), payMoney);
			addMoneyLog(payVo.getUserId(), UserType.STUDENT, payVo.getPayWay(),
					leftMoney, payMoney, MoneyChange.CHARGE,
					ReqConstants.MONEY_FLOW_WAIT,
					ReqConstants.MONEY_ACCOUNT_BALANCE, payVo.getRemark(),
					payVo.getPayOrderId(), UserType.lili.getDesc());

			if (discountMoney > 0) {
				// 充值送现金 对学员是收入；对喱喱是费用
				// 大客户充值送金额在入口处确定
				// 学员账户记录
				leftMoney = addStudentMoney(payVo.getUserId(), discountMoney);
				addMoneyLog(payVo.getUserId(), UserType.STUDENT,
						payVo.getPayWay(), leftMoney, discountMoney,
						MoneyChange.CHARGE_DISCOUNT, true, true,
						MoneyChange.CHARGE_DISCOUNT.getDesc(),
						payVo.getPayOrderId(), UserType.lili.getDesc());

				// 喱喱钱包记录
				Student student = studentManager.getStudentInfo(payVo
						.getUserId());
				leftMoney = addSchoolMoney(0, -1 * discountMoney);
				addMoneyLog(0L, UserType.lili, PayWayType.SYSTEMPAY, leftMoney,
						-1 * discountMoney, MoneyChange.CHARGE_DISCOUNT, false,
						true, MoneyChange.CHARGE_DISCOUNT.getDesc(),
						payVo.getPayOrderId(), UserType.STUDENT.getDesc()
								+ student.getName());
			}
		}
	}

	@Override
	public void handleOrderMoneyFlow(OrderVo orderVo, int oldPayState,
			String payType) {
		// 检查结算状态
		if ((null != orderVo.getCheckoutState())
				&& (orderVo.getCheckoutState() == OrderConstant.CHECKOUTSTATE.HAS_CHECKOUT)) {
			return;
		}

		long studentId = orderVo.getStudentId();
		long coachId = orderVo.getCoachId();
		String orderId = orderVo.getOrderId();
		int totalPay = orderVo.getPayTotal();// 这个是实际支付金额！！！
		int discount = orderVo.getCouponTotal();// 这个是优惠金额！！！
		int priceTotal = orderVo.getPriceTotal();// 这个是总金额！！！

		Coach coach = coachManager.getCoachInfo(coachId);

		int leftMoney;
		String remark = orderVo.getCourseName() + "|"
				+ MoneyChange.CLASS_FEE.getDesc();

		// 1.学员加费用流水。支付操作已在上一步完成。
		leftMoney = addStudentMoney(studentId, 0);
		addMoneyLog(studentId, UserType.STUDENT, payType, leftMoney,
				0 - totalPay, MoneyChange.CLASS_FEE, false,
				payType.equals("balance"), remark, orderId,
				UserType.lili.getDesc());

		if (oldPayState == OrderConstant.PAYSTATE.HAND_UP) {// 支付挂起订单

			// 挂起课程的金额，不算收入，算减少的支出。等同于还款给车厘子。
			// 2.1 喱喱加余额
			leftMoney = addSchoolMoney(0, priceTotal); // 这里注意加的是总额！！如果有优惠券后面再减去
			// 2.2 喱喱加余额作为减费用流水
			addMoneyLog(0L, UserType.lili, payType, leftMoney, priceTotal,
					MoneyChange.CLASS_ORDER_HANGUP_FEE,
					ReqConstants.MONEY_FLOW_OUT,
					ReqConstants.MONEY_ACCOUNT_BALANCE, remark, orderId,
					UserType.STUDENT.getDesc() + orderVo.getStuName());

		} else if (oldPayState == OrderConstant.PAYSTATE.WAITPAY) {// 支付正常订单

			// 3.1 教练加业绩 教练无账户流水
			// 增加业绩总数
			addPerformanceAndMoney(orderVo.getCoachId(), priceTotal, 0);
			// 增加业绩流水
			addPerformance(orderVo);

			// 4.1 查询佣金比例
			BrokerageCourse brokerage = getBrokerageCourse(coach.getCityId(),
					Integer.valueOf(orderVo.getCourseId()),
					(byte) (orderVo.getDltype().intValue()),
					orderVo.getPayTime());
			int brMoney = 0;
			if (null != brokerage) {
				brMoney = calculateBrokerage(priceTotal,
						brokerage.getBrokerageType(), brokerage.getBrokerage());
			} else {
				// 2016/六月/2 这里可能需要记录没找到佣金模板 //20160712默认佣金比例15%
				brMoney = calculateBrokerage(priceTotal, 1, 15);
			}
			// 注意需要从教练端获取所属的驾校
			Integer schoolId = coach.getSchoolId();
			// 驾校的金额账户变动
			// 4.2 给驾校添加收入，记录收入流水
			leftMoney = addSchoolMoney(schoolId, priceTotal);
			addMoneyLog((long) schoolId, UserType.SCHOOL, payType, leftMoney,
					priceTotal, MoneyChange.CLASS_FEE, true, true, remark,
					orderId, UserType.lili.getDesc());
			// 4.3 给驾校减去佣金，记录佣金流水
			leftMoney = addSchoolMoney(schoolId, -1 * brMoney);
			addMoneyLog((long) schoolId, UserType.SCHOOL, payType, leftMoney,
					-1 * brMoney, MoneyChange.CLASS_BROKERAGE, false, true,
					remark, orderId, UserType.lili.getDesc());

			School school = coachManager.getSchool(schoolId);

			// 喱喱平台账户的金额账户变动
			// 5.1 喱喱佣金收入，记录佣金流水
			leftMoney = addSchoolMoney(0, brMoney);
			addMoneyLog(0L, UserType.lili, payType, leftMoney, brMoney,
					MoneyChange.CLASS_BROKERAGE, true, true, remark, orderId,
					UserType.SCHOOL.getDesc() + school.getName());

		}

		// 6.1 给喱喱平台账户减去优惠券支出 订单号|优惠券
		if (discount > 0) {
			leftMoney = addSchoolMoney(0, -1 * discount);
			addMoneyLog(0L, UserType.lili, PayWayType.COUPON, leftMoney, -1
					* discount, MoneyChange.COUPON_FEE, false, true, remark,
					orderId + "|" + orderVo.getCoupon(),
					UserType.STUDENT.getDesc() + orderVo.getStuName());
		}

		// 设置结算状态
		try {
			orderVo.setCheckoutState(OrderConstant.CHECKOUTSTATE.HAS_CHECKOUT);
			orderService.updateByOrderId(orderVo, orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private int calculateBrokerage(int total, int type, int brokerage) {
		int brMoney = 0;
		// 计算这一次交易所需要的佣金
		if (type == 1) {
			brMoney = total * brokerage / 100;
		} else if (type == 2) {
			brMoney = brokerage;
		} else {
			brMoney = 0;
		}
		return brMoney;
	}

	@Override
	public void handleOrderHasPayedMoneyFlow(OrderVo orderVo) {
		// 全额抵扣订单也需要添加资金流水
		// 1.检查订单状态
		if ((null != orderVo.getCheckoutState())
				&& (orderVo.getCheckoutState() == OrderConstant.CHECKOUTSTATE.HAS_CHECKOUT)) {
			return;
		}
		long studentId = orderVo.getStudentId();
		long coachId = orderVo.getCoachId();
		String orderId = orderVo.getOrderId();
		int totalPay = orderVo.getPayTotal(); // 实际支付 0
		int discount = orderVo.getCouponTotal(); // 优惠券抵扣 170
		int priceTotal = orderVo.getPriceTotal(); // 总金额 170
		Coach coach = coachManager.getCoachInfo(coachId);

		int leftMoney;
		String remark = orderVo.getCourseName() + "|"
				+ MoneyChange.CLASS_FEE.getDesc(); // +"|优惠券全额抵扣"
		// 1.学员加费用流水。优惠券全额抵扣，费用为0
		leftMoney = addStudentMoney(studentId, 0);
		addMoneyLog(studentId, UserType.STUDENT, PayWayType.COUPON, leftMoney,
				0, MoneyChange.CLASS_FEE, false, false, remark, orderId,
				UserType.lili.getDesc());

		// 3.1 教练加业绩 教练无账户流水
		// 增加业绩总数
		addPerformanceAndMoney(orderVo.getCoachId(), priceTotal, 0);
		// 增加业绩流水
		addPerformance(orderVo);

		// 4.1 查询佣金比例
		BrokerageCourse brokerage = getBrokerageCourse(coach.getCityId(),
				Integer.valueOf(orderVo.getCourseId()),
				(byte) (orderVo.getDltype().intValue()), orderVo.getPayTime());
		int brMoney = 0;
		if (null != brokerage) {
			brMoney = calculateBrokerage(priceTotal,
					brokerage.getBrokerageType(), brokerage.getBrokerage());
		} else {
			// 2016/六月/2 这里可能需要记录没找到佣金模板 //20160712默认佣金比例15%
			brMoney = calculateBrokerage(priceTotal, 1, 15);
		}
		// 注意需要从教练端获取所属的驾校
		Integer schoolId = coach.getSchoolId();
		// 驾校的金额账户变动
		// 4.2 给驾校添加收入，记录收入流水
		leftMoney = addSchoolMoney(schoolId, priceTotal);
		addMoneyLog((long) schoolId, UserType.SCHOOL, PayWayType.COUPON,
				leftMoney, priceTotal, MoneyChange.CLASS_FEE, true, true,
				remark, orderId, UserType.lili.getDesc());
		// 4.3 给驾校减去佣金，记录佣金流水
		leftMoney = addSchoolMoney(schoolId, -1 * brMoney);
		addMoneyLog((long) schoolId, UserType.SCHOOL, PayWayType.COUPON,
				leftMoney, -1 * brMoney, MoneyChange.CLASS_BROKERAGE, false,
				true, remark, orderId, UserType.lili.getDesc());

		School school = coachManager.getSchool(schoolId);

		// 喱喱平台账户的金额账户变动
		// 5.1 喱喱佣金收入，记录佣金流水
		leftMoney = addSchoolMoney(0, brMoney);
		addMoneyLog(0L, UserType.lili, PayWayType.COUPON, leftMoney, brMoney,
				MoneyChange.CLASS_BROKERAGE, true, true, remark, orderId,
				UserType.SCHOOL.getDesc() + school.getName());

		// 6.1 给喱喱平台账户减去优惠券支出 订单号|优惠券
		if (discount > 0) {
			leftMoney = addSchoolMoney(0, -1 * discount);
			addMoneyLog(0L, UserType.lili, PayWayType.COUPON, leftMoney, -1
					* discount, MoneyChange.COUPON_FEE, false, true, remark,
					orderId + "|" + orderVo.getCoupon(),
					UserType.STUDENT.getDesc() + orderVo.getStuName());
		}

		// 设置结算状态
		try {
			orderVo.setCheckoutState(OrderConstant.CHECKOUTSTATE.HAS_CHECKOUT);
			orderService.updateByOrderId(orderVo, orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void handleOrderHandUpMoneyFlow(OrderVo orderVo) {
		// TODO 20161103考虑异常情况，如客户端攻击，应添加分布式锁
		// 检查订单状态，避免重复挂起
		if (orderVo.getPayState() == OrderConstant.PAYSTATE.HAND_UP) {
			return;
		}

		String orderId = orderVo.getOrderId();
		int totalPay = orderVo.getPayTotal();// 这个是实际支付金额！！！
		int discount = orderVo.getCouponTotal();// 这个是优惠金额！！！
		int priceTotal = orderVo.getPriceTotal();// 这个是总金额！！！

		Coach coach = coachManager.getCoachInfo(orderVo.getCoachId());

		String payType = PayWayType.SYSTEMPAY; // 喱喱的支付记为系统支付
		String remark = "挂起订单";
		// 1.1 增加教练业绩总额
		addPerformanceAndMoney(orderVo.getCoachId(), priceTotal, 0);
		// 1.2 增加教练业绩流水
		addPerformance(orderVo);

		// 2.1 查询课程佣金比例
		BrokerageCourse brokerage = getBrokerageCourse(coach.getCityId(),
				Integer.valueOf(orderVo.getCourseId()),
				(byte) (orderVo.getDltype().intValue()), new Date());
		int brMoney = 0;
		if (null != brokerage) {
			brMoney = calculateBrokerage(priceTotal,
					brokerage.getBrokerageType(), brokerage.getBrokerage());
		} else {
			// 2016/六月/2 这里可能需要记录没找到佣金模板 //20160712默认佣金比例15%
			brMoney = calculateBrokerage(priceTotal, 1, 15);
		}

		int leftMoney;
		Integer schoolId = coach.getSchoolId();
		// 3.1 增加驾校收入总额，增加驾校收入流水
		leftMoney = addSchoolMoney(schoolId, priceTotal);
		addMoneyLog((long) schoolId, UserType.SCHOOL, payType, leftMoney,
				priceTotal, MoneyChange.CLASS_FEE, true, true, remark, orderId,
				UserType.lili.getDesc());
		// 3.2 减去驾校佣金，增加佣金流水
		leftMoney = addSchoolMoney(schoolId, -1 * brMoney);
		addMoneyLog((long) schoolId, UserType.SCHOOL, payType, leftMoney, -1
				* brMoney, MoneyChange.CLASS_BROKERAGE, false, true, remark,
				orderId, UserType.lili.getDesc());

		School school = coachManager.getSchool(schoolId);
		// 4.1 喱喱减余额
		leftMoney = addSchoolMoney(0, -1 * priceTotal);
		addMoneyLog(0L, UserType.lili, payType, leftMoney, -1 * priceTotal,
				MoneyChange.CLASS_ORDER_HANGUP, false, true, remark, orderId,
				UserType.SCHOOL.getDesc() + school.getName());
		// 4.2 喱喱加佣金收入
		leftMoney = addSchoolMoney(0, brMoney);
		addMoneyLog(0L, UserType.lili, payType, leftMoney, brMoney,
				MoneyChange.CLASS_BROKERAGE, true, true, remark, orderId,
				UserType.SCHOOL.getDesc() + school.getName());

		// 设置订单为已挂起
		try {
			orderVo.setPayState(OrderConstant.PAYSTATE.HAND_UP);
			orderService.updateByOrderId(orderVo, orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleOrderRefundMoneyFlow(OrderVo orderVo, int refundMoney) {
		String orderId = orderVo.getOrderId();
		// TODO 20161103
		// 补充结算规则，需要根据是否已结算到驾校（1）调整喱喱余额和收入；（2）调整教练的业绩；===>因为支付时已自动结算，所以这里只有已结算一种情况
		// （1）学员减去费用（加余额）
		Long studentId = orderVo.getStudentId();
		Integer schoolId = orderVo.getSchoolId();
		int leftMoney = addStudentMoney(studentId, refundMoney);
		// （2）学员退费日志
		addMoneyLog(orderVo.getStudentId(), UserType.STUDENT, "system",
				leftMoney, refundMoney, MoneyChange.CLASS_REFUND, false, true,
				"课时费退款", orderId, "喱喱钱包");
		// （3）驾校减去收入（减余额）
		leftMoney = addSchoolMoney(schoolId, 0 - refundMoney);
		// （4）驾校退款日志
		addMoneyLog((long) schoolId, UserType.SCHOOL, "system", leftMoney,
				0 - refundMoney, MoneyChange.CLASS_REFUND, false, true,
				"课时费退款", orderId, "[学员]" + orderVo.getStuName());
		// （5）计算已经结算的佣金
		BrokerageCourse brokerage = getBrokerageCourse(orderVo.getCityId(),
				Integer.valueOf(orderVo.getCourseId()),
				(byte) (orderVo.getDltype().intValue()), orderVo.getPayTime());
		int brMoney = 0;
		if (null != brokerage) {
			brMoney = calculateBrokerage(orderVo.getPriceTotal(),
					brokerage.getBrokerageType(), brokerage.getBrokerage());
		} else {
			// 2016/六月/2 这里可能需要记录没找到佣金模板 //20160712默认佣金比例15%
			brMoney = calculateBrokerage(orderVo.getPriceTotal(), 1, 15);
		}
		// （6）喱喱收入减去佣金
		leftMoney = addSchoolMoney(0, 0 - brMoney);
		addMoneyLog(0L, UserType.lili, "system", leftMoney, 0 - brMoney,
				MoneyChange.CLASS_REFUND_BROKERAGE, true, true, "课时费佣金退款",
				orderId, "[驾校]" + orderVo.getSchoolId());
		// （7）驾校费用减去佣金
		leftMoney = addSchoolMoney(schoolId, brMoney);
		addMoneyLog((long) schoolId, UserType.lili, "system", leftMoney,
				brMoney, MoneyChange.CLASS_REFUND_BROKERAGE, false, true,
				"课时费佣金退款", orderId, "[车厘子]");
		//	（8）教练减去相应的业绩
		addPerformanceAndMoney(orderVo.getCoachId(), 0 - orderVo.getPriceTotal() , 0); //FIXME 需要确认下是否之前没有这个动作

	}

	@Override
	public int handlePayCoachBonus(Long coachId, int performance, Integer money) {
		// （1）给教练加余额，作为收入
		int leftMoney = addPerformanceAndMoney(coachId, 0, money);
		// （2）记录流水
		String orderId = StringUtil.getOrderId();
		addMoneyLog(coachId, UserType.COACH, PayWayType.BALANCE, leftMoney,
				money, MoneyChange.AWARD, ReqConstants.MONEY_FLOW_IN,
				ReqConstants.MONEY_ACCOUNT_BALANCE,
				MoneyChange.AWARD.getDesc(), orderId, "喱喱钱包");
		// （3）喱喱账户扣余额，作为费用
		leftMoney = addSchoolMoney(0, -1 * money);
		addMoneyLog(0L, UserType.lili, PayWayType.BALANCE, leftMoney, -1
				* money, MoneyChange.AWARD, ReqConstants.MONEY_FLOW_OUT,
				ReqConstants.MONEY_ACCOUNT_BALANCE,
				MoneyChange.AWARD.getDesc(), orderId, UserType.COACH.getDesc()
						+ coachId);

		return 0;
	}

	// ******************************************************************************

	@Override
	public void handleSignupMoneyFlow(PayVo payVo) {
		int payValue = payVo.getPayValue(); // 实际支付金额
		int discoutMoney = payVo.getDiscountMoney(); // 优惠券减免金额
		int priceTotal = payValue + discoutMoney; // 总金额

		// 给学员钱包也加入记录,获取学员钱包余额。减余额动作已经在上一步完成。
		int leftMoney = addStudentMoney(payVo.getUserId(), 0);
		addMoneyLog(payVo.getUserId(), UserType.STUDENT, payVo.getPayWay(),
				leftMoney, -1 * payValue, MoneyChange.SIGNUP_FEE, false, payVo
						.getPayWay().equals(PayWayType.BALANCE),
				payVo.getRemark(), payVo.getPayOrderId(),
				UserType.lili.getDesc());

		// 给喱喱平台账户添加报名费。这是一笔代收款，余额增加，但不算收入！！
		Student student = studentManager.getStudentInfo(payVo.getUserId());
		leftMoney = addSchoolMoney(0, priceTotal); // 20160805注意这里加的是支付总金额，不是优惠后的金额，因为后面会再减去优惠的金额。
		addMoneyLog(0L, UserType.lili, payVo.getPayWay(), leftMoney,
				priceTotal, MoneyChange.SIGNUP_FEE,
				ReqConstants.MONEY_FLOW_WAIT,
				ReqConstants.MONEY_ACCOUNT_BALANCE, payVo.getRemark(),
				payVo.getPayOrderId(),
				UserType.STUDENT.getDesc() + student.getName());

		if (discoutMoney > 0) {
			// 喱喱平台账户记录优惠券的支出
			leftMoney = addSchoolMoney(0, 0 - discoutMoney);
			addMoneyLog(0L, UserType.lili, PayWayType.COUPON, leftMoney,
					0 - discoutMoney, MoneyChange.COUPON_FEE, false, true,
					payVo.getRemark(), payVo.getPayOrderId(),
					UserType.STUDENT.getDesc() + student.getName());
		}
	}
	
	/**
	 * 驾校点评报名支付资金记录
	 * 没有优惠券
	 */
	@Override
	public void handleWxSignupMoneyFlow(PayVo payVo) {
		int payValue= payVo.getPayValue(); // 实际支付金额
		// 给学员钱包也加入记录,获取学员钱包余额。减余额动作已经在上一步完成。
		int leftMoney = addStudentMoney(payVo.getUserId(), 0);
		addMoneyLog(payVo.getUserId(), UserType.STUDENT, payVo.getPayWay(),
				leftMoney, -1 * payValue, MoneyChange.SIGNUP_WX_FEE, false, payVo
						.getPayWay().equals(PayWayType.BALANCE),
				payVo.getRemark(), payVo.getPayOrderId(),
				UserType.STUDENT.getDesc());

		// 给喱喱平台账户添加报名费。这是一笔代收款，余额增加，但不算收入！！
		Student student = studentManager.getStudentInfo(payVo.getUserId());
		leftMoney = addSchoolMoney(0, payValue);
		addMoneyLog(0L, UserType.lili, payVo.getPayWay(), leftMoney,
				payValue, MoneyChange.SIGNUP_WX_FEE,
				ReqConstants.MONEY_FLOW_WAIT,
				ReqConstants.MONEY_ACCOUNT_BALANCE, payVo.getRemark(),
				payVo.getPayOrderId(),
				UserType.STUDENT.getDesc() + student.getName());

	}
	
	/**
	 * 平安保险费用
	 */
	@Override
	public void handleInsuranceMoneyFlow(PayVo payVo) {
		int payValue= payVo.getPayValue(); // 实际支付金额
		// 给学员钱包也加入记录,获取学员钱包余额。减余额动作已经在上一步完成。
		int leftMoney = addStudentMoney(payVo.getUserId(), 0);
		addMoneyLog(payVo.getUserId(), UserType.STUDENT, payVo.getPayWay(),
				leftMoney, -1 * payValue, MoneyChange.INSURANCE_FEE, false, payVo
						.getPayWay().equals(PayWayType.BALANCE),
				payVo.getRemark(), payVo.getPayOrderId(),
				UserType.STUDENT.getDesc());

		// 给喱喱平台账户添加报名费。这是一笔代收款，余额增加，但不算收入！！
		Student student = studentManager.getStudentInfo(payVo.getUserId());
		leftMoney = addSchoolMoney(0, payValue);
		addMoneyLog(0L, UserType.lili, payVo.getPayWay(), leftMoney,
				payValue, MoneyChange.INSURANCE_FEE,
				ReqConstants.MONEY_FLOW_WAIT,
				ReqConstants.MONEY_ACCOUNT_BALANCE, payVo.getRemark(),
				payVo.getPayOrderId(),
				UserType.STUDENT.getDesc() + student.getName());

	}

	@Override
	public void handleCheckoutEnrollMoneyFlow(EnrollOrder enrollOrder) {
		// TODO 20161103考虑异常情况，如客户端攻击，应添加分布式锁
		// 检查结算状态
		if ((null != enrollOrder.getOrderState())
				&& (enrollOrder.getOrderState() == ReqConstants.ENROLL_ORDER_CHECKOUT_DONE)) {
			return;
		}
		Student student = studentManager.getStudentInfo(enrollOrder
				.getStudentId());
		// 如果还没有给学员分配驾校，则不能结算报名费
		if (null == student.getSchoolId()) {
			log.debug("handleCheckoutEnrollMoneyFlow student has no schoolId! student:"
					+ student.getStudentId());
			return;
		}
		int schoolId = student.getSchoolId();

		String orderId = enrollOrder.getOrderId();

		int totalPay = enrollOrder.getPrice();
		// 查询佣金比例
		BrokerageEnroll brokerage = getBrokerageEnroll(enrollOrder.getCityId(),
				schoolId);// 查询指定驾校的佣金比例
		int brMoney = 0;
		if (null != brokerage) {
			brMoney = calculateBrokerage(totalPay,
					brokerage.getBrokerageType(), brokerage.getBrokerage());
		} else {
			// 如果驾校佣金没有配置，则查询相关城市的默认佣金，驾校id=0代表默认
			brokerage = getBrokerageEnroll(enrollOrder.getCityId(), 0);
			if (null != brokerage) {
				brMoney = calculateBrokerage(totalPay,
						brokerage.getBrokerageType(), brokerage.getBrokerage());
			} else {
				// 如果默认城市默认佣金也没有配置，则设置默认佣金为15080
				// brMoney = calculateBrokerage(totalPay, 1, 10);
				brMoney = 15080;
			}
		}

		int leftMoney;
		String payType = PayWayType.BALANCE; // 结算方式都是余额，是否标记为余额，还是system？FIXME

		// 1.1给驾校加余额收入
		leftMoney = addSchoolMoney(schoolId, totalPay);
		addMoneyLog((long) schoolId, UserType.SCHOOL, payType, leftMoney,
				totalPay, MoneyChange.SIGNUP_SETTLEMENT,
				ReqConstants.MONEY_FLOW_IN, ReqConstants.MONEY_ACCOUNT_BALANCE,
				MoneyChange.SIGNUP_SETTLEMENT.getDesc(), orderId,
				UserType.lili.getDesc());
		// 1.2给驾校减去佣金，费用
		leftMoney = addSchoolMoney(schoolId, -1 * brMoney);
		addMoneyLog((long) schoolId, UserType.SCHOOL, payType, leftMoney, -1
				* brMoney, MoneyChange.SIGNUP_BROKERAGE,
				ReqConstants.MONEY_FLOW_OUT,
				ReqConstants.MONEY_ACCOUNT_BALANCE,
				MoneyChange.SIGNUP_BROKERAGE.getDesc(), orderId,
				UserType.lili.getDesc());

		// 2.1 喱喱余额减报名费 这里喱喱减去的是余额，不算费用！！
		leftMoney = addSchoolMoney(0, -1 * totalPay);
		addMoneyLog(0L, UserType.lili, payType, leftMoney, -1 * totalPay,
				MoneyChange.SIGNUP_SETTLEMENT, ReqConstants.MONEY_FLOW_WAIT,
				ReqConstants.MONEY_ACCOUNT_BALANCE,
				MoneyChange.SIGNUP_SETTLEMENT.getDesc(), orderId,
				UserType.SCHOOL.getDesc() + enrollOrder.getSchoolName());
		// 2.2 佣金收入
		leftMoney = addSchoolMoney(0, brMoney);
		addMoneyLog(0L, UserType.lili, payType, leftMoney, brMoney,
				MoneyChange.SIGNUP_BROKERAGE, ReqConstants.MONEY_FLOW_IN,
				ReqConstants.MONEY_ACCOUNT_BALANCE,
				MoneyChange.SIGNUP_BROKERAGE.getDesc(), orderId,
				UserType.SCHOOL.getDesc() + enrollOrder.getSchoolName());

		// 设置结算状态
		enrollOrder.setOrderState(ReqConstants.ENROLL_ORDER_CHECKOUT_DONE);
		// 记录结算佣金
		enrollOrder.setBrokerage(brMoney);
		enrollOrderManager.updateEnrollOrder(enrollOrder);
	}

	// ******************************************************************************

	@Override
	public void handleExamPlaceMoneyFlow(PayVo payVo) {
		int payValue = payVo.getPayValue(); // 实际支付金额
		int discoutMoney = payVo.getDiscountMoney(); // 优惠券减免金额
		int priceTotal = payValue + discoutMoney; // 总金额

		// 教练支付记录 对教练来讲，是费用增加
		int leftMoneyCoach = addPerformanceAndMoney(payVo.getUserId(), 0, 0); // 已经支付过了!
		addMoneyLog(payVo.getUserId(), UserType.COACH, payVo.getPayWay(),
				leftMoneyCoach, -1 * payValue, MoneyChange.EXAMPLACE_FEE,
				false, payVo.getPayWay().equals(PayWayType.BALANCE),
				payVo.getRemark(), payVo.getPayOrderId(),
				UserType.lili.getDesc());

		Coach coach = coachManager.getCoachInfo(payVo.getUserId());
		// 喱喱收入记录 对喱喱来讲，是一笔代收款
		int leftMoney = addSchoolMoney(0, priceTotal); // 20160805注意这里加的是支付总金额，不是优惠后的金额，因为后面会再减去优惠的金额。
		addMoneyLog(0L, UserType.lili, payVo.getPayWay(), leftMoney,
				priceTotal, MoneyChange.EXAMPLACE_FEE,
				ReqConstants.MONEY_FLOW_WAIT,
				ReqConstants.MONEY_ACCOUNT_BALANCE, payVo.getRemark(),
				payVo.getPayOrderId(),
				UserType.COACH.getDesc() + coach.getName());
		if (discoutMoney > 0) {
			// 喱喱平台账户记录优惠券的支出
			leftMoney = addSchoolMoney(0, 0 - discoutMoney);
			addMoneyLog(0L,
					UserType.lili,
					PayWayType.COUPON,
					leftMoney,
					0 - discoutMoney,
					MoneyChange.COUPON_FEE,
					ReqConstants.MONEY_FLOW_OUT, // 20161023
													// 优惠券支出算是喱喱的费用？？暂时算，但是应该算在考场的头上
					ReqConstants.MONEY_ACCOUNT_BALANCE, payVo.getRemark(),
					payVo.getPayOrderId(),
					UserType.COACH.getDesc() + coach.getName());
		}

	}

	@Override
	public void handleExamPlaceRefund(ExamPlaceOrder order, Double ratio) {
		long coachId = order.getCoachId();
		int refund = (int) (order.getPayTotal() * ratio);
		int discoutMoney = order.getCouponTotal();
		String orderId = order.getOrderId();
		// 退款对教练来讲，是减少了费用。
		int leftMoney = addCoachMoney(coachId, refund);
		addMoneyLog(order.getCoachId(), UserType.COACH, PayWayType.BALANCE,
				leftMoney, refund, MoneyChange.EXAMPLACE_REFUND,
				ReqConstants.MONEY_FLOW_OUT,
				ReqConstants.MONEY_ACCOUNT_BALANCE,
				MoneyChange.EXAMPLACE_REFUND.getDesc(), orderId, "喱喱钱包");

		// 对喱喱来讲，是代收款的变更
		leftMoney = addSchoolMoney(0, 0 - refund);
		addMoneyLog(0L, UserType.lili, "system", leftMoney, 0 - refund,
				MoneyChange.EXAMPLACE_REFUND, ReqConstants.MONEY_FLOW_WAIT,
				ReqConstants.MONEY_ACCOUNT_BALANCE,
				MoneyChange.EXAMPLACE_REFUND.getDesc(), orderId,
				"[教练]" + order.getCoachName());

		if (discoutMoney > 0) {
			// 喱喱平台账户记录优惠券的支出 退回优惠券支出
			leftMoney = addSchoolMoney(0, discoutMoney);
			addMoneyLog(0L,
					UserType.lili,
					PayWayType.COUPON,
					leftMoney,
					discoutMoney,
					MoneyChange.COUPON_FEE,
					ReqConstants.MONEY_FLOW_OUT, // 20161023
													// 优惠券支出算是喱喱的费用？？暂时算，但是应该算在考场的头上
					ReqConstants.MONEY_ACCOUNT_BALANCE,
					MoneyChange.COUPON_FEE.getDesc(), order.getOrderId(),
					UserType.COACH.getDesc() + order.getCoachName());
		}

	}

	@Override
	public void handleExamPlaceMoneyFlow(List<ExamPlaceOrder> orders) {
		for (ExamPlaceOrder order : orders) {
			int payValue = order.getPayTotal(); // 实际支付金额
			int discoutMoney = order.getCouponTotal(); // 优惠券减免金额
			int priceTotal = payValue + discoutMoney; // 总金额

			String payWay = order.getPayWay() == null ? PayWayType.AUTOPAY
					: order.getPayWay();
			// 教练支付记录 对教练来讲，是费用增加
			int leftMoneyCoach = addPerformanceAndMoney(order.getCoachId(), 0,
					0); // 已经支付过了!
			addMoneyLog(order.getCoachId(), UserType.COACH, payWay,
					leftMoneyCoach, -1 * payValue, MoneyChange.EXAMPLACE_FEE,
					false, payWay.equals(PayWayType.BALANCE),
					MoneyChange.EXAMPLACE_FEE.getDesc(), order.getOrderId(),
					UserType.lili.getDesc());

			// 喱喱收入记录 对喱喱来讲，是一笔代收款
			int leftMoney = addSchoolMoney(0, priceTotal); // 20160805注意这里加的是支付总金额，不是优惠后的金额，因为后面会再减去优惠的金额。
			addMoneyLog(0L, UserType.lili, payWay, leftMoney, priceTotal,
					MoneyChange.EXAMPLACE_FEE, ReqConstants.MONEY_FLOW_WAIT,
					ReqConstants.MONEY_ACCOUNT_BALANCE,
					MoneyChange.EXAMPLACE_FEE.getDesc(), order.getOrderId(),
					UserType.COACH.getDesc() + order.getCoachName());
			if (discoutMoney > 0) {
				// 喱喱平台账户记录优惠券的支出
				leftMoney = addSchoolMoney(0, 0 - discoutMoney);
				addMoneyLog(0L,
						UserType.lili,
						PayWayType.COUPON,
						leftMoney,
						0 - discoutMoney,
						MoneyChange.COUPON_FEE,
						ReqConstants.MONEY_FLOW_OUT, // 20161023
														// 优惠券支出算是喱喱的费用？？暂时算，但是应该算在考场的头上
						ReqConstants.MONEY_ACCOUNT_BALANCE,
						MoneyChange.COUPON_FEE.getDesc(), order.getOrderId(),
						UserType.COACH.getDesc() + order.getCoachName());
			}

		}

	}

	// ******************************************************************************

	@Override
	public String handleAdjustStudent(PayVo payVo) {
// 20161107 这个直接调整有问题，需要产品重新明确。先关闭这个功能。
		int payMoney = payVo.getPayValue();
		int discountMoney = payVo.getDiscountMoney();
		// 参数判断
		if (payVo.getUserType() != OrderConstant.USETYPE.STUDENT
				|| discountMoney == 0) {
			log.error(payVo
					+ " adjust student money incorrect userType or discount=0.");
			return ResultCode.ERRORCODE.PARAMERROR;
		}
		// 学员账户记录
		int leftMoney = adjustStudentMoney(payVo.getUserId(), discountMoney);
		addMoneyLog(payVo.getUserId(), UserType.STUDENT, payVo.getPayWay(),
				leftMoney, payMoney, MoneyChange.CHARGE_DISCOUNT, true, true,
				payVo.getRemark(), payVo.getPayOrderId(), "喱喱钱包");
		return ResultCode.ERRORCODE.SUCCESS;
	}

	public int adjustStudentMoney(long studentId, int adjustMoney) {

		StudentAccount studentAccount = userMoneyDtoMapper
				.getStudentMoney(studentId);
		if (studentAccount == null) {
			studentAccount = new StudentAccount();
			studentAccount.setMoney(adjustMoney);
			studentAccount.setStudentId(studentId);
			userMoneyDtoMapper.insertStudentAccount(studentAccount);
		} else {
			studentAccount.setMoney(studentAccount.getMoney() + adjustMoney);
			userMoneyDtoMapper.updateStudentMoney(studentAccount);
		}
		if (studentAccount.getMoney() < 0) {
			log.error(studentAccount + " now is below zero after adjustMoney="
					+ adjustMoney);
		}
		return studentAccount.getMoney();
	}

	@Override
	public String handleAdjustCoach(PayVo payVo) {

		int payMoney = payVo.getPayValue();
		int discountMoney = payVo.getDiscountMoney();
		// 参数判断
		if (payVo.getUserType() != OrderConstant.USETYPE.COACH
				|| discountMoney == 0) {
			log.error(payVo
					+ " adjust COACH money incorrect userType or discount=0.");
			return ResultCode.ERRORCODE.PARAMERROR;
		}
		// 教练账户记录
		int leftMoney = addPerformanceAndMoney(payVo.getUserId(), 0,
				discountMoney);
		addMoneyLog(payVo.getUserId(), UserType.COACH, payVo.getPayWay(),
				leftMoney, payMoney, MoneyChange.CHARGE_DISCOUNT, true, true,
				payVo.getRemark(), payVo.getPayOrderId(), "喱喱钱包");
		return ResultCode.ERRORCODE.SUCCESS;
	}

	@Override
	public int isExitMoneyRecord(PayVo payVo) {
		return userMoneyDtoMapper.queryIsExitOrderId(payVo.getPayOrderId());
	}

	
	// ******************************************************************************
	
	@Override
	public int handleSchoolRecharge(String userId, String userType,
			String price, String payWay,String orderId,String remark) {
		int leftMoney = 0;
		try {
			Integer schoolId = Integer.parseInt(userId);
			Integer money = Integer.parseInt(price);
			//驾校账户充值
			leftMoney = addSchoolMoney(schoolId, money);
			//记录充值记录
			addMoneyLog(schoolId.longValue(),
					UserType.SCHOOL,
					payWay,
					leftMoney,
					money,
					MoneyChange.CHARGE,
					ReqConstants.MONEY_FLOW_WAIT, // 注意：充值不算支出或收入
					ReqConstants.MONEY_ACCOUNT_BALANCE,
					MoneyChange.CHARGE.getDesc() + remark, 
					orderId,
					UserType.SCHOOL.getDesc());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leftMoney;
	}

	@Override
	public Integer setExamPlaceMax(String userId, String userType, String price) {
		try {
			Integer schoolId = Integer.parseInt(userId);
			Integer money = Integer.parseInt(price);
			
			SchoolAccount schoolAccount = schoolAccountMapper
					.selectByPrimaryKey(schoolId);
			if (null == schoolAccount) {
				return null;
			} else {
				schoolAccount.setExamPlaceMax(money);
				schoolAccountMapper.updateByPrimaryKey(schoolAccount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer getExamPlaceMax(String userId, String userType) {
		Integer schoolId = Integer.parseInt(userId);
		SchoolAccount schoolAccount = schoolAccountMapper
				.selectByPrimaryKey(schoolId);
		if (null == schoolAccount) {
			return null;
		} else {
			return schoolAccount.getExamPlaceMax();
		}
	}

	@Override
	public void handleExamPlaceReturnAward(ExamPlaceOrder order) {
		// （1）给教练加余额，作为收入
		int refundTotal = order.getRefundTotal();
		long coachId = order.getCoachId();
		int leftMoney = addCoachMoney(coachId, refundTotal);
		// （2）记录流水
		String orderId = StringUtil.getOrderId();

		addMoneyLog(coachId, UserType.COACH, PayWayType.BALANCE, leftMoney,
				refundTotal, MoneyChange.EXAMPLACE_RETURNAWARD, ReqConstants.MONEY_FLOW_IN,
				ReqConstants.MONEY_ACCOUNT_BALANCE,
				MoneyChange.EXAMPLACE_RETURNAWARD.getDesc(), orderId, "喱喱钱包");
		// （3）喱喱账户扣余额，作为费用
		leftMoney = addSchoolMoney(0, -1 * refundTotal);
		addMoneyLog(0L, UserType.lili, "system", leftMoney, -1
				* refundTotal, MoneyChange.EXAMPLACE_RETURNAWARD, ReqConstants.MONEY_FLOW_OUT,
				ReqConstants.MONEY_ACCOUNT_BALANCE,
				MoneyChange.EXAMPLACE_RETURNAWARD.getDesc(), orderId, UserType.COACH.getDesc()
						+ coachId);
	}
	

}




































