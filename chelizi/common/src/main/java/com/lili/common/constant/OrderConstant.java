package com.lili.common.constant;

public class OrderConstant {
	//一节课时长,单位是分钟
	public static final int clztime=60;
	public static final int COACHSCORE=80;
	public static final int defaultPrice=18000;
	
	public class ORDERSTATE {
		//已取消
		public static final int CANCELD=0;
		//已下单
		public static final int GIVEORDER=1;
		//已接单
		public static final int ACCEPTORDER=2;
		//上课中
		public static final int INCLASS=3;
		//已完成
		public static final int COMPLETE=4;
		//教练已评价,加1。4跟6可操作
		public static final int COACHCOMMENT=5;
		//学生已评价，加2。4跟5可操作
		public static final int STUCOMMENT=6;
		//双方已评价
		public static final int ALLCOMMENT=7;
		//已拒单
		public static final int REFUSEORDER=9;
		//缺课
		public static final int MISSCLASS=10;
		//关闭（CMS后台操作）
		public static final int CLOSED=11;
		
	}
	public class PAYSTATE {
		//未支付
		public static final int WAITPAY=0;
		//已支付
		public static final int HASPAY=1;
		//支付失败
		public static final int PAYERROR=2;
		//9代表老学员自动支付
		public static final int AUTOPAYE=9;

		//订单挂起
		public static final int HAND_UP = 10;
		//订单退款中
		public static final int ON_REFUND = 11;
		//订单已退款
		public static final int HAS_REFUND = 12;
	}

	public class CHECKOUTSTATE{
		public static final int WAIT_CHECKOUT=0;
		public static final int HAS_CHECKOUT=1;

	}
	
	public class OTYPE {
		//现约单
		public static final int NOWORDER=1;
		//续课单
		public static final int CONTINUEORDER=2;
		//预约单
		public static final int BOOKORDER=3;
		//报名订单
		public static final int ENROLL_ORDER=11;
		//报考订单
		public static final int EXAM_ORDER=12;
		
	}
	//取消原因类型
	public class RETYPE {
		//教练拒单
		public static final int COACHREFUSE=0;
		//学生未应答前取消
		public static final int STUCANCEL=0;		
	}
	public class USETYPE {
		public static final int COACH=1;
		public static final int STUDENT=2;
		public static final int MANGER=3;
	}
	public class RMQTAG {
		public static final String  ACCEPTORDER="accept_order";
		public static final String  REFUSEORDER="refuse_order";
		public static final String  CANCELORDER="cancel_order";
		public static final String  COMMITORDER="commit_order";
		public static final String  COMMENTCOACH="comment_coach";
		public static final String  COMMENTSTU="comment_student";
		public static final String  COACHSCROE="coach_score";
		public static final String  LOGACCESS="log_access";
		public static final String  STUDENT_REGISTER="rmq_student_register_value";
		public static final String  STUDENT_ENROLL_PAID="rmq_school_value";
		public static final String  STUDENT_ENROLL_PAY="rmq_pay_value";
		public static final String  STUDENT_FLOW_OUT="rmq_school_flow_value";
		public static final String  BBS_TOPIC_SEND="rmq_bbs_value";
		public static final String  BBS_UPDATE_USER_INFO="rmq_student_user_value";
	}
	public class ISDEL {
		public static final int DELETE=1;
		public static final int NOTDELETE=0;
	}
	public class VERIFY {
		//等待审核
		public static final int WAIT=0;
		//审核通过
		public static final int PASS=1;
		//审核拒绝
		public static final int REFUSE=2;
		//审核通过但未生效
		public static final int NOTIN=8;
		//审核通过但已过期
		public static final int EXPIRE=9;
	}
	public class ACTIVE {
		public static final int ACTIVE=1;
		public static final int INACTIVE=0;
	}
	
}
