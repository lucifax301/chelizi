package com.lili.common.vo;

public class ReqConstants {
	/**
	 * 令牌名称
	 */
	public static final String TOKEN="token";
	
	/**
	 * 环境信息：测试环境
	 */
	public static final String ENV_TEST = "test";
	/**
	 * 环境信息：生产环境
	 */
	public static final String ENV_PRODUCT = "product";
	
	/**
	 * 用户状态：正常
	 */
	public static final int USER_STATE_NORMAL = 0;
	/**
	 * 用户状态：临时锁定
	 */
	public static final int USER_STATE_LOCKED_TEMP = 1;
	/**
	 * 用户状态：永久锁定
	 */
	public static final int USER_STATE_LOCKED_FOREVER = 2;
	
	/**
	 * 用户性别：男
	 */
	public static final int USER_SEX_MAN = 1;
	/**
	 * 用户性别：女
	 */
	public static final int USER_SEX_WOMAN = 0;
	
	
	/**
	 * 用户类型：教练
	 */
	public static final int USER_TYPE_COACH = 1;
	/**
	 * 用户类型：学员
	 */
	public static final int USER_TYPE_STUDENT = 2;
	/**
	 * 用户类型：管理员
	 */
	public static final int USER_TYPE_ADMIN = 3;
	/**
	 * 用户类型：客服
	 */
	public static final int USER_TYPE_SERVICE = 4;
	/**
	 * 用户类型：驾校用户
	 */
	public static final int USER_TYPE_SCHOOL = 5;
	
	/**
	 * 用户名称：学员，默认名称
	 */
	public static final String USER_NAME_STUDENT_DEFAULT = "喱喱同学";
	
	
	/**
	 * 图片类型：用户头像
	 */
	public static final int PIC_TYPE_USER_HEADICON = 0;
	/**
	 * 图片类型：身份证正面
	 */
	public static final int PIC_TYPE_IDCARD_FRONT = 1;
	/**
	 * 图片类型：身份证背面
	 */
	public static final int PIC_TYPE_IDCARD_BACK = 2;
	/**
	 * 图片类型：用户驾驶证（驾照正本）
	 */
	public static final int PIC_TYPE_USER_DRIVING_LICENCE = 3;
	/**
	 * 图片类型：教练证
	 */
	public static final int PIC_TYPE_COACH_CARD = 4;
	/**
	 * 图片类型：车辆行驶证
	 */
	public static final int PIC_TYPE_CAR_DRIVING_LICENCE = 5;
	
	/**
	 * 图片类型：驾照副本
	 */
	public static final int PIC_TYPE_USER_DRIVING_LICENCE_COPY = 6;
	
	/**
	 * 图片类型：车辆行驶证副本
	 */
	public static final int PIC_TYPE_CAR_DRIVING_LICENCE_COPY = 7;
	
	/**
	 * 图片类型：车辆图片
	 */
	public static final int PIC_TYPE_CAR_PICTURE = 8;
	
	
	
	
	/**
	 * 教练状态：收车(休息，默认值)
	 */
	public static final int COACH_STATUS_OFF_WORK = 0;
	/**
	 * 教练状态：出车（听单中）
	 */
	public static final int COACH_STATUS_ON_WORK = 1;
	/**
	 * 教练状态：上课中
	 */
	public static final int COACH_STATUS_ON_CLASS = 2;
	/**
	 * 教练状态：上课准备
	 */
	public static final int COACH_STATUS_PREPARE_CLASS = 3;
	/**
	 * 教练状态：预约上课中
	 */
	public static final int COACH_BOOK_CLASS_IN = 4;
	/**
	 * 教练状态：预约上课准备
	 */
	public static final int COACH_BOOK_CLASS_PREPARE = 5;
	
	
	/**
	 * 学员状态：下课（休息）
	 */
	public static final int STUDENT_STATUS_OFF_CLASS = 0;
	/**
	 * 学员状态：现约上课中
	 */
	public static final int STUDENT_STATUS_ON_CLASS = 1;
	/**
	 * 学员状态：等待接单
	 */
	public static final int STUDENT_WAIT_ACCEPT_ORDER = 2;
	/**
	 * 学员状态：上课准备
	 */
	public static final int STUDNET_PREPARE_START_CLASS = 3;
	/**
	 * 学员状态：预约上课中
	 */
	public static final int STUDENT_BOOK_CLASS_IN = 4;
	/**
	 * 学员状态：预约准备上课
	 */
	public static final int STUDENT_BOOK_CLASS_PREPARE = 5;
	
	
	
	/**
	 * 请求类型：用户注册
	 */
	public static final int REQ_TYPE_REGISTER = 1;
	/**
	 * 请求类型：找回密码
	 */
	public static final int REQ_TYPE_FIND_PASSWORD = 2;
	/**
	 * 请求类型：短信登录
	 */
	public static final int REQ_TYPE_LOGIN = 3;
	/**
	 * 请求类型：超级登录短信。如果未注册则直接注册成用户，并返回登录短信；如果已注册则直接返回登录短信；
	 */
	public static final int REQ_TYPE_SUPER_LOGIN = 4;
	
	
	/**
	 * 驾驶类型：C1 手动挡
	 */
	public static final int DRIVE_TYPE_C1 = 1;
	/**
	 * 驾驶类型：C2自动挡
	 */
	public static final int DRIVE_TYPE_C2 = 2;
	
	
	/**
	 * 车辆等级：普通车型
	 */
	public static final int CAR_LEVEL_TYPE_COMMON = 1;
	/**
	 * 车辆等级：舒适车型
	 */
	public static final int CAR_LEVEL_TYPE_COMFORT = 2;
	/**
	 * 车辆等级：考试车型
	 */
	public static final int CAR_LEVEL_TYPE_EXAM = 3;
	
	
	/**
	 * 课程类别：科目二
	 */
	public static final int COURSE_TYPE_TWO = 1;
	/**
	 * 课程类别：科目二（强化）
	 */
	public static final int COURSE_TYPE_TWO_PLUS = 2;
	/**
	 * 课程类别：科目三
	 */
	public static final int COURSE_TYPE_THREE = 3;
	/**
	 * 课程类别：科目三（强化）
	 */
	public static final int COURSE_TYPE_THREE_PLUS = 4;
	
	/**
	 * 新课程类别：科目二基础训练-C1
	 */
	public static final int COURSE_TYPE_C1_TWO_BASIC = 1;
	/**
	 * 新课程类别：科目二考场模拟-C1
	 */
	public static final int COURSE_TYPE_C1_TWO_EXAM_SIMULATION = 2;
	/**
	 * 新课程类别：科目二应试训练-C1
	 */
	public static final int COURSE_TYPE_C1_TWO_EXAM_TRAINING = 6;
	/**
	 * 新课程类别：科目三基础训练-C1
	 */
	public static final int COURSE_TYPE_C1_THREE_BASIC = 3;
	/**
	 * 新课程类别：科目三考场模拟-C1
	 */
	public static final int COURSE_TYPE_C1_THREE_EXAM_SIMULATION = 4;
	/**
	 * 新课程类别：科目三应试训练-C1
	 */
	public static final int COURSE_TYPE_C1_THREE_EXAM_TRAINING = 7;
	/**
	 * 新课程类别：陪驾-C1
	 */
	public static final int COURSE_TYPE_C1_PEIJIA = 5;
	/**
	 * 新课程类别：科目二基础训练-C2
	 */
	public static final int COURSE_TYPE_C2_TWO_BASIC = 11;
	/**
	 * 新课程类别：科目二考场模拟-C2
	 */
	public static final int COURSE_TYPE_C2_TWO_EXAM_SIMULATION = 12;
	/**
	 * 新课程类别：科目二应试训练-C2
	 */
	public static final int COURSE_TYPE_C2_TWO_EXAM_TRAINING = 16;
	/**
	 * 新课程类别：科目三基础训练-C2
	 */
	public static final int COURSE_TYPE_C2_THREE_BASIC = 13;
	/**
	 * 新课程类别：科目三考场模拟-C2
	 */
	public static final int COURSE_TYPE_C2_THREE_EXAM_SIMULATION = 14;
	/**
	 * 新课程类别：科目三应试训练-C2
	 */
	public static final int COURSE_TYPE_C2_THREE_EXAM_TRAINING = 17;
	/**
	 * 新课程类别：陪驾-C2
	 */
	public static final int COURSE_TYPE_C2_PEIJIA = 15;
	
	/**
	 * 科目类别：科目一
	 */
	public static final int SUBJECT_TYPE_ONE = 1;
	/**
	 * 科目类别：科目二
	 */
	public static final int SUBJECT_TYPE_TWO = 2;
	/**
	 * 科目类别：科目三
	 */
	public static final int SUBJECT_TYPE_THREE = 3;
	/**
	 * 科目类别：科目四
	 */
	public static final int SUBJECT_TYPE_FOUR = 4;
	/**
	 * 科目类别：陪驾
	 */
	public static final int SUBJECT_TYPE_PEIJIA = 5;
	
	
	/**
	 * 优惠劵类型：未使用
	 */
	public static final int COUPON_TYPE_UNUSED = 1;
	/**
	 * 优惠劵类型：已使用	
	 */
	public static final int COUPON_TYPE_USED = 2;
	/**
	 * 优惠劵类型：已过期
	 */
	public static final int COUPON_TYPE_EXPIRED = 3;
	
	/**
	 * 班次预约类型：现约
	 */
	public static final int BOOK_TYPE_NOW = 1;
	/**
	 * 班次预约类型：预约
	 */
	public static final int BOOK_TYPE_FUTURE = 3;
	
	/**
	 * 班次开放状态：开放
	 */
	public static final int CLASS_TYPE_ON	= 0;
	/**
	 * 班次开放状态：关闭
	 */
	public static final int CLASS_TYPE_OFF = 1;
	
	/**
	 * 教练每周可提现次数
	 */
	public static final int COACH_WITHDRAW_COUNT = 1;
	
	
	
	
	// ---------阶段，与数据库中的stepId对应----------------------
	/**
	 * 特定阶段：暂不报名
	 */
	public static final int PROG_STAGE_BEGIN_DELAY = -1;
	/**
	 * 特定阶段：报名（开始）
	 */
	public static final int PROG_STAGE_BEGIN =1;
	/**
	 * 特定阶段：支付
	 */
	public static final int PROG_STAGE_PAY =2;
	/**
	 * 特定阶段：个人信息
	 */
	public static final int PROG_STAGE_USERINFO =3;
	/**
	 * 特定阶段：邮寄
	 */
	public static final int PROG_STAGE_MAIL =4;
	/**
	 * 特定阶段：交表到驾校
	 */
	public static final int PROG_STAGE_TABLE_SCHOOL =5;
	/**
	 * 特定阶段：交表到车管所
	 */
	public static final int PROG_STAGE_TABLE_OFFICAIL =6;
	/**
	 * 特定阶段：理论课
	 */
	public static final int PROG_STAGE_THEORY =101;
	/**
	 * 特定阶段：模拟考试（目前深圳地区没有这个阶段）
	 */
	public static final int PROG_STAGE_THEORY_TEST =201;
	/**
	 * 特定阶段：科目一排队
	 */
	public static final int PROG_STAGE_SUBJECT_ONE_LINEUP =301;
	/**
	 * 特定阶段：科目一
	 */
	public static final int PROG_STAGE_SUBJECT_ONE =302;
	/**
	 * 特定阶段：科目二排队
	 */
	public static final int PROG_STAGE_SUBJECT_TWO_LINEUP =401;
	/**
	 * 特定阶段：科目二
	 */
	public static final int PROG_STAGE_SUBJECT_TWO =402;
	/**
	 * 特定阶段：长考（和理论课类似，没有排队）
	 */
	public static final int PROG_STAGE_SUBJECT_LONGTRAIN =501;
	/**
	 * 特定阶段：科目三排队
	 */
	public static final int PROG_STAGE_SUBJECT_THREE_LINEUP =601;
	/**
	 * 特定阶段：科目三
	 */
	public static final int PROG_STAGE_SUBJECT_THREE =602;
	/**
	 * 特定阶段：科目四排队
	 */
	public static final int PROG_STAGE_SUBJECT_FOUR_LINEUP =701;
	/**
	 * 特定阶段：科目四
	 */
	public static final int PROG_STAGE_SUBJECT_FOUR =702;
	/**
	 * 特定阶段：拿到驾照（结束）
	 */
	public static final int PROG_STAGE_END =801;
	
	// --------各阶段的状态	0未开始，1已提交，100成功，101失败,999未来将需要完成的步骤
	/**
	 * 阶段状态：未开始
	 */
	public static final byte STAGE_STATE_NONE =0;
	/**
	 * 阶段状态：已提交
	 */
	public static final byte STAGE_STATE_SUBMIT =1;
	/**
	 * 阶段状态：已成功
	 */
	public static final byte STAGE_STATE_SUCC =100;
	/**
	 * 阶段状态：已失败
	 */
	public static final byte STAGE_STATE_FAIL =101;


	//-------------- 报名订单状态
	/**
	 * 报名订单状态:0-已取消
	 */
	public static final byte ENROLL_ORDER_CANCEL = 0;
	/**
	 * 报名订单状态:1-已下单
	 */
	public static final byte ENROLL_ORDER_BOOKED = 1;
	/**
	 * 报名订单状态:2-未结款
	 */
	public static final byte ENROLL_ORDER_CHECKOUT_WAIT = 2;
	/**
	 * 报名订单状态:3-已结款
	 */
	public static final byte ENROLL_ORDER_CHECKOUT_DONE = 3;
	/**
	 * 报名订单状态:4-退款中
	 */
	public static final byte ENROLL_ORDER_REFUNDING = 4;
	/**
	 * 报名订单状态:5-已退款
	 */
	public static final byte ENROLL_ORDER_REFUNDED = 5;
	/**
	 * 报名订单状态:6-退款失败
	 */
	public static final byte ENROLL_ORDER_REFUND_FAILED = 6;

	//----------特殊操作
	/**
	 * 特殊操作：未删除
	 */
	public static final byte OPERATE_DELETE_NO =0;
	/**
	 * 特殊操作：已删除
	 */
	public static final byte OPERATE_DELETE =1;
	
	/**
	 * 特殊操作：不显示
	 */
	public static final byte OPERATE_SHOW_NO = 0;
	/**
	 * 特殊操作：显示
	 */
	public static final byte OPERATE_SHOW_YES = 1;
	
	
	//--------短信类型-------------
	/**
	 * 短信类型：通知教练实时订单
	 */
	public static final int SHORT_MESSAGE_TYPE_INSTANT_ORDER = 1;
	/**
	 * 短信类型：通知学员已支付报名费
	 */
	public static final int SHORT_MESSAGE_TYPE_ENROLL_PAYMENT = 2;
	/**
	 * 短信类型：已报名的学员反馈给客服
	 */
	public static final int SHORT_MESSAGE_TYPE_ENROLL_FEEDBACK = 3;
	/**
	 * 短信类型：通知学员收到的优惠劵信息
	 */
	public static final int SHORT_MESSAGE_TYPE_ENROLL_COUPON = 4;
	/**
	 * 短信类型：通知学员已支付报名费，并送礼品
	 */
	public static final int SHORT_MESSAGE_TYPE_ENROLL_PAYMENT_GIFT = 5;
	/**
	 * 短信类型：开理论课
	 */
	public static final int SHORT_MESSAGE_TYPE_THEORY_ON = 6;
	/**
	 * 短信类型：关理论课
	 */
	public static final int SHORT_MESSAGE_TYPE_THEORY_OFF = 7;
	/**
	 * 短信类型：开长考课
	 */
	public static final int SHORT_MESSAGE_TYPE_LONGTRAIN_ON = 8;
	/**
	 * 短信类型：关长考课
	 */
	public static final int SHORT_MESSAGE_TYPE_LONGTRAIN_OFF = 9;
	
	//----------审核状态---------------
	/**
	 * 审核状态：未审核
	 */
	public static final int CHECK_STATE_INIT = 0;
	/**
	 * 审核状态：审核中
	 */
	public static final int CHECK_STATE_PROCESS = 1;
	/**
	 * 审核状态：已审核通过
	 */
	public static final int CHECK_STATE_SUCCESS = 2;
	/**
	 * 审核状态：审核失败
	 */
	public static final int CHECK_STATE_FAIL = 3;
	/**
	 * 审核状态：已过期
	 */
	public static final int CHECK_STATE_OUTDATE = 4;
	/**
	 * 审核状态：已吊销
	 */
	public static final int CHECK_STATE_DEACTIVE = 5;
	
	/**
	 * 审核类型：身份证
	 */
	public static final int CHECK_TYPE_IDCARD = 0;
	/**
	 * 审核类型：驾驶证
	 */
	public static final int CHECK_TYPE_DRIVECARD = 1;
	
	public static final Byte MY_COACH_TYPE_SYSTEM =0;
	
	
	/**
	 * 理论课状态：已开课，待上课
	 */
	public static final int THEORY_CLASS_OPEN = 1;
	/**
	 * 理论课状态：已上课
	 */
	public static final int THEORY_CLASS_ON = 2;
	/**
	 * 理论课状态：已取消
	 */
	public static final int THEORY_CLASS_CANCEL = 3;
	/**
	 * 学员出勤情况：1-出勤
	 */
	public static final Byte THEORY_STU_PRESENCE = 1;
	/**
	 * 学员出勤情况：2-缺勤
	 */
	public static final Byte THEORY_STU_ABSENCE = 2;
	

	/**
	 * 资金结算-账户类型：0-非余额账户（第三方如支付宝、微信等）
	 * 对应t_u_money isBalance字段
	 */
	public static final byte MONEY_ACCOUNT_BALANCE_NOT = 0;
	/**
	 * 资金结算-账户类型：1-余额账户（余额计入虚拟账户）
	 * 对应t_u_money isBalance字段
	 */
	public static final byte MONEY_ACCOUNT_BALANCE = 1;

	/**
	 * 资金结算-金额流向：0-流出，支出、费用
	 * 对应t_u_money isEarning字段
	 */
	public static final byte MONEY_FLOW_OUT = 0;
	/**
	 * 资金结算-金额流向：1-流入，收入
	 * 对应t_u_money isEarning字段
	 */
	public static final byte MONEY_FLOW_IN = 1;
	/**
	 * 资金结算-金额流向：2-流入，代收款（不算收入！）
	 * 对应t_u_money isEarning字段
	 */
	public static final byte MONEY_FLOW_WAIT = 2;
	
}






















