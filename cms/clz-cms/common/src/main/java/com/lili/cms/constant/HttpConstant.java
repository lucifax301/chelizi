package com.lili.cms.constant;

public class HttpConstant {


	/**
	 * 请求正确码
	 */
	public static final int SUCCESS_CODE = 0;


	/**
	 * 该操作需要登录
	 */
	public static final int NEED_LOGIN_COCE = 1;
	
	/**
	 * 您没有该项操作的权利
	 */
	public static final int NO_AUTH_COCE = 2;
	
	/**
	 * 请求错误码
	 */
	public static final int ERROR_CODE = 600;

	/**
	 * 请求正确码
	 */
	public static final String SUCCESS_MSG = "请求正确";


	/**
	 * 前端提示而不是弹出的错误码
	 */
	public static final int ERROR_TIP_CODE = 601;
	/**
	 * 车辆：车牌号或车辆行驶证号存在错误码
	 */
	
	public static final int CARNO_EXIST_COCE = 602;
	/**
	 * 教练已绑定该车
	 */
	public static final int COACH_CAR_EXIST_COCE = 603;
	
	/**
	 * 更新失败
	 */
	public static final int UPDATE_FAILED_COCE = 611;

	/**
	 * 插入失败
	 */
	public static final int INSERT_FAILED_COCE = 612;

	/**
	 * 查询失败
	 */
	public static final int QUERY_FAILED_COCE = 613;

	/**
	 * 删除失败
	 */
	public static final int DELETE_FAILED_COCE = 614;

	/**
	 * 没有操作权限
	 */
	public static final int PERMISSION_FAILED_COCE = 620;

	/**
	 * Excel导出失败
	 */
	public static final int EXPORT_EXCEL_FAILED_COCE = 630;

	/**
	 * 请求参数异常
	 */
	public static final int REQUEST_PARAMS_ERROR_COCE = 699;
	
	/**
	 * 金额超出限制！
	 */
	public static final int MONEY_OVER_LIMIT_COCE = 701;
	
	/**
	 * 该记录状态不对，不能进行操作
	 */
	public static final int STATUS_IS_NOT_ALLOW_COCE = 702;
}

