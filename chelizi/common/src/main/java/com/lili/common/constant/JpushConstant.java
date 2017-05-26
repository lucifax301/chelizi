package com.lili.common.constant;

public class JpushConstant {
	public static String SOUNDPREFIX="sound";
	public static String SOUNDPOST=".wav";
	
	public class RMQTAG {
		public static final String  JPUSH2STU ="stu_jpush";
		public static final String  JPUSH2COACH  ="coach_jpush";
	}
	public class OPERATE {
		//order push 1-199
		public static final String STUACCEPTORDER="1";
		public static final String STUCANCELORDER="2";
		public static final String STUCLASSNEAR="3";//上课前75分钟提醒
		public static final String STUCLASSIN="4";
		public static final String STUCLASSOUT="5";
		public static final String STUREFUSEORDER="6";
		public static final String STUCLASSNEARFIVE="7";
		public static final String STUAUTOCANCEL="8";
		public static final String STUMISSCLASS="9";
		public static final String STUPAYENROLL="10"; //支付报名费
		public static final String STUENROLLSUC="11"; //报名成功
		public static final String STUCOURSEONO="12"; //模拟考试达标，可约考科目一，模拟考
		public static final String STUCOURSEONEYK="13"; //科目一成功约考
		public static final String STUCOURSETWOYK="14"; //科目二成功约考
		public static final String STUCOURSETHREEYK="15"; //科目三成功约考
		public static final String STUCOURSEFOURYK="16"; //科目四成功约考
		public static final String STUCLASSXYBEGIN="17"; //现约下单成功
		public static final String STUCLASSYYBEGIN="18"; //预约下单成功
		public static final String STUUNAPPORDER="19"; //有未评价订单
		public static final String STUSHARE="20"; //推荐分享
		
		public static final String BBS_STU_REPLY="21"; //社区回复
		public static final String BBS_STU_PRAISE="22"; //社区点赞
		public static final String BBS_DO_REALEASE="23"; //发帖失败
		public static final String BBS_IS_DELETE="24"; //帖子被屏蔽
		
		
		public static final String COACHHASORDER="101";
		public static final String COACHBOOKORDER="102";
		public static final String COACHCANCELORDER="103";
		public static final String COACHCLASSNEAR="104";
		public static final String COACHCLASSIN="105";
		public static final String COACHCLASSOUT="106";
		public static final String COACHPREPARE="107";
		public static final String COACHAUTOCANCEL="108";
		public static final String COACHNOBOOKED="109";
		public static final String COACHNOBOOKED15="110";
		public static final String COACHCLASSOFF="111"; //收车
		public static final String COACHCLASSON="112"; //出车
		
		public static final String COACHCHECKPASS="113"; //注册教练审核通过推送
		public static final String COACHCHECKUNPASS="114"; //注册教练审核不通过审核
		public static final String COACHWECHATACCESS="115"; //接受上课
		public static final String COACHWECHATREJECT="116"; //拒绝上课
		public static final String COACHWECHATCANCEL="117"; //取消上课
		public static final String COACHWECHATCLASS="118"; //全部上课
		public static final String COACHWECHATCLASSUN="119"; //部分未接受上课
		public static final String COACHWECHATENROLL="120"; //报名意向
		public static final String COACHWECHATENROLLSUC="121"; //报名成功

		
		public static final String STUHAVENEWCOUPON="202";
		
		public static final String COACHRECVPAY="301";
		
		//coupon push 302
		public static final String STUOBTAINNEWCOUPON="302";
		//大客户员工审核通过推送消息
		public static final String VIPCUSTOM_VERIFY_PASS="310";
		//大客户充送记录审核通过推送消息
		public static final String RECHARGERECORD_VERIFY_PASS="311";
		
		/**
		 * 教练接受学员自主排班订单推送
		 */
		public static final String STU_CLASS="401";
		/**
		 * 学员接受自主排班找不到匹配教练的推送
		 */
		public static final String STU_CLASS_NO_COACH="402";
		/**
		 * 学员接受自主排班教练已接单的推送
		 */
		public static final String STU_CLASS_ACCEPT="403";
		/**
		 * 用户被后台锁定的推送
		 */
		public static final String USER_LOCK="404";
		
		/**
		 * 学员接受自主排班教练已接单的推送
		 */
		public static final String STU_CLASS_REJECT="405";
		
		/**
		 * 约考场订单被延期
		 */
		public static final String ORDER_DELAY="450";
		/**
		 * 约考场订单被关闭
		 */
		public static final String ORDER_CLOSE="451";
		/**
		 * 约考场订单练考时间将到
		 */
		public static final String ORDER_NEAR="452";
		
		public static final String MESSAGE="501"; //CMS配置消息通知
		public static final String TOPMESSAGE="502"; //CMS配置头条
		
		/**
		 * 系统播报
		 */
		public static final String BROADCAST = "901";
		
	}
	public class EXTRAFIELD {
		public static final String OPERATE="operate";
		public static final String ORDERID="orderId";
		public static final String GENTYPE="gentype";
		public static final String PREPARE="prepare";
		public static final String PAYSTATE="payState";
		public static final String COACHCLASSID="ccid";
		public static final String TIMESTAMP="jpushStamp";
	}
	public class GENTYPE {
		public static final String AUTOGEN="auto";
		public static final String MANUALGEN="manual";
	}
	public class PREFIX {
		public static final String PUSHOBJECT="QuartzJPush_";
	}
}
