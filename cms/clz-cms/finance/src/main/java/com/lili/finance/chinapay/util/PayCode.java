package com.lili.finance.chinapay.util;
public class PayCode {
	
	 //请求响应码(responseCode)表
	  public static final String RESPONSE_CODE_SUCCESS= "0000";//提交成功
	  public static final String RESPONSE_CODE_ERRORA= "0100";//商户提交的字段长度、格式错误
	  public static final String RESPONSE_CODE_ERRORB= "0101";//商户验签错误
	  public static final String RESPONSE_CODE_ERRORC= "0102";//手续费计算出错
	  public static final String RESPONSE_CODE_ERRORD= "0103";//商户备付金帐户金额不足
	  public static final String RESPONSE_CODE_ERRORE= "0104";//操作拒绝
	  public static final String RESPONSE_CODE_ERRORF= "0105";//重复交易
	  
	  //交易状态码（stat）表
	  public static final String STAT_CODE_SUCCESS= "s";//交易成功
	  public static final String STAT_CODE_ACCESS= "2";//交易已接受
	  public static final String STAT_CODE_SURE= "3";//财务已确认
	  public static final String STAT_CODE_HANDLE= "4";//财务处理中
	  public static final String STAT_CODE_SEND= "5";//已发往银行
	  public static final String STAT_CODE_BACK= "6";//银行已退单
	  public static final String STAT_CODE_SUBMIT= "7";//重汇已提交
	  public static final String STAT_CODE_RESEND= "8";//重汇已发送
	  public static final String STAT_CODE_REFAIL= "9";//重汇已退单

}
