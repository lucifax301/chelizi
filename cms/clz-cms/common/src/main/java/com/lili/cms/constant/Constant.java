package com.lili.cms.constant;

public class Constant {

	public static final String INFO = "info";

	public static final Integer DEFAULT_PAGE_NO = 1;
	public static final Integer DEFAULT_PAGE_SIZE = 10;

	public static final String BLANK_STR = "";
	public static final String USER_SESSION = "user";
	public static final String SCHOOL_SESSION = "school";
	public static final String PERMISSION_IDURL_SESSION = "permission_idUrl";
	public static final String PERMISSION_URLID_SESSION = "permission_urlId";

	/**
	 * uuid,请求时候生成
	 */
	public static final String TOKEN_STR = "token";

	/**
	 * 喱喱端接口请求
	 */
	public static final int LILI_CHANNEL = 1;
	/**
	 * 驾校端接口请求
	 */
	public static final int JX_CHANNEL = 2;

	  public static final Integer DEPOSIT_UN_HANDLER = 0;//审核中
	  public static final Integer DEPOSIT_SUCCESS = 1;//提现成功
	  public static final Integer DEPOSIT_FAIL= 2;//提现失败
	  public static final Integer FINANCE_PASS = 3;//已确认
	  public static final Integer DEPOSIT_HANDLE = 4;//银行处理中
	  public static final Integer DEPOSIT_BANK_FAIL = 5;//银行处理失败

	  public static final Integer BONUS_INIT = 1;
	  public static final Integer BONUS_SURE= 2;
	  public static final Integer BONUS_GRANT = 3;
	  
	  public static final Integer BANK_YX = 1;
	  public static final Integer BANK_WX= 2;
	  
	  public static final Integer IS_USE = 0;
	  public static final Integer IS_DEL= 1;
	  
	public static final Integer BOUND_BANK_CARD_SUCCESS = 1;
	public static final Integer BOUND_BANK_CARD_FAIL = 2;

	public static final Integer COACH_USER_TYPE = 1;
	public static final Integer STUDENT_USER_TYPE = 2;

	//excel表
	public static final String  SHEET_BOUND_BANK_CARD = "银行卡绑定列表";
	public static final String  SHEET_BOUND_BANK_FILE_NAME = "银行卡绑定申请";
	public static final String  SHEET_ENROLL_ORDER = "报名订单记录";
	public static final String  SHEET_ENROLL_ORDER_HIS = "报名订单结款记录";
	public static final String  SHEET_ENROLL_ORDER_PAY = "结款记录";
	public static final String  SHEET_DEPOSIT_INFO = "用户提现记录";
	public static final String  SHEET_DEPOSIT_FILE_NAME = "提现记录";
	public static final String  SHEET_STUDENT_FILE_NAME = "学员记录";
	public static final String  SHEET_VIP_STUDENT_FILE_NAME = "大客户学员记录";
	public static final String  SHEET_COACH_FILE_NAME = "教练记录";
	public static final String  SHEET_ORDER_FILE_NAME = "订单记录";
	public static final String  SHEET_CAR_FILE_NAME = "车辆记录";
	public static final String  SHEET_FIELD_FILE_NAME = "训练场记录";
	public static final String  BONUS_FILE_NAME = "bonusTemplate";
	public static final String  BONUS_FILE_OUT_NAME = "奖金表模版";
	public static final String  VIP_STU_FILE_NAME = "VipCustomerTemplate";
	public static final String  VIP_STU_FILE_OUT_NAME = "大客户学员表模版";
	public static final String  SCHOOL_DATA_FILE_NAME = "schoolDataTemplate";
	public static final String  SCHOOL_DATA_FILE_OUT_NAME = "驾校数据导入模版";
	public static final String  SHEET_STUDENT_NAME = "studentTemplate";
	public static final String  SHEET_SCHOOL_DEPOSIT = "驾校提现列表";
	public static final String  SHEET_BALANCE_CHANGE = "余额变更记录";
	public static final String  SHEET_STUDENT_PROGRESS_FILE_NAME = "进度记录";
	public static final String  SHEET_PROGRESS_STUDENT_FILE_NAME = "进度学员记录";
	public static final String  SHEET_STUDENT_INSURANCE_FILE_NAME = "平安保险订单记录";
	 
	//日志类型
	public static final String  SYSTEM_ERROR = "系统异常！";
	public static final String  HAS_HANDLER = "该记录已处理！";
	public static final String  MONEY_OVER = "金额超限制！";


	public static final String  WEEK_SUM = "最近7天合计";
	public static final String  MONTH_SUM = "本月合计";


	//常量
	public static final String  DEPOSIT_BACK_MONEY = "提现回退金额";

	public static final String  BONUS_MONEY = "奖金";

	//下载
	public static final String  MERCHANT_CODE = "777290058110097"; //商户代码

	//币种
	public static final String CNY_CODE = "156";	//人民币代码

	public static final String BANK_CARD_TYPE = "01";	//银行卡
	public static final String BANK_BOOK_TYPE = "02";	//存折

	public static final String IDENTITY_CARD = "01";	//身份证

	public static final String BANK_CARD_TEST = "1"; //银行卡1分钱校验


	public static final String TIME_START = " 00:00:00";//开始时间
	public static final String TIME_END = " 23:59:59";//结束时间

	public static final String ERROR_REMARK = "<script>window.parent.Layer.alert({width:300,height:150,type:'msg',title: '奖金金额超过限制'});</script>";

	/**
	 * 驾校每周可提现次数
	 */
	public static final int SCHOOL_WITHDRAW_COUNT = 1;

}
