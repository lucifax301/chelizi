package com.lili.common.constant;

public class NoticeType {

	    /*
	     * 公告类别
	     */
	    public final static byte TYPE_0 = 0;         //今日重点播报
	    public final static byte TYPE_1 = 1;         //活动消息
	    public final static byte TYPE_2 = 2;         //订单消息 
	    public final static byte TYPE_3 = 3;         //指定喱喱头条
	    public final static byte TYPE_4 = 4;         //我的消息
	    public final static byte TYPE_5 = 5;         //系统消息
	    /*
	     * 消息状态
	     */
	    public final static byte ISDEL_0 = 0;             //已发布
	    public final static byte ISDEL_1 = 1;             //已删除
	    public final static byte ISDEL_2 = 2;             //草稿
	    /*
	     * 通知对象  app 2.1版本之前使用的状态 3-8  
	     */
	    public final static byte USERTYPE_0 = 0;             //全体用户
	    public final static byte USERTYPE_1 = 1;             //全体教练、指定教练
	    public final static byte USERTYPE_2 = 2;             //全体学员、指定学员
	    public final static byte USERTYPE_3 = 3;             //驾校教练
	    public final static byte USERTYPE_4 = 4;             //驾校学员
	    public final static byte USERTYPE_5 = 5;             //城市教练
	    public final static byte USERTYPE_6 = 6;             //城市学员
	    public final static byte USERTYPE_7 = 7;             //特约教练
	    public final static byte USERTYPE_8 = 8;             //普通教练
	    public final static byte USERTYPE_9 = 9;             //cms消息中心配置教练消息
	    public final static byte USERTYPE_10 = 10;           //cms消息中心配置学员消息
	    
	    /*
	     * 通知类型
	     */
	    public final static byte UTYPE_1 = 1;             //驾校学员
	    public final static byte UTYPE_2 = 2;             //喱喱学员
	    public final static byte UTYPE_3 = 3;             //特约教练
	    public final static byte UTYPE_4 = 4;             //普通教练
}
